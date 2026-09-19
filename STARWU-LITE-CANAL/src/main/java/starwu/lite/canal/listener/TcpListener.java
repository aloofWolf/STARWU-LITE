package starwu.lite.canal.listener;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import starwu.lite.canal.notify.CanalSubject;
import starwu.lite.manage.async.threadPool.AsyncThreadPool;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.config.canal.CanalConfig;
import starwu.lite.metadata.config.canal.CanalItemConfig;
import starwu.lite.metadata.enums.canal.DbUpdateType;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class TcpListener {

    private final CanalConfig config;

    private final CanalSubject subject;

    private final AsyncThreadPool asyncThreadPool;

    private ThreadPoolTaskExecutor executor;

    @PostConstruct
    public void start(){
        if(config.isTcpEnabled()){
            log.info("开始监听canal");
        }else{
            return;
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                // 线程要执行的逻辑
                executor = asyncThreadPool.getThreadPoolTaskExecutor(config.getHandleAsyncThreadName());
                CanalConnector connector = getConnector();
                listenerData(connector);
                connector.disconnect();
            }
        };
        thread.start();
    }


    private CanalConnector getConnector(){
        try{
            List<CanalItemConfig> tcpHosts = config.getTcpHosts();
            List<InetSocketAddress> list = new ArrayList();
            InetSocketAddress address = null;
            for(CanalItemConfig item : tcpHosts){
                address = new InetSocketAddress(item.getTcpHost(),item.getTcpPort());
                list.add(address);
            }
            CanalConnector connector = CanalConnectors.newClusterConnector(
                    list,
                    config.getDestination(),
                    config.getTcpUsername(),
                    config.getTcpPassword()
            );
            connector.connect();
            connector.subscribe(config.getSubscribe());
            connector.rollback();
            return connector;
        }catch(Throwable e){
            log.error("TcpListener getConnector error:{}",e);
        }
        return null;


    }

    private void  listenerData(CanalConnector connector){
        while (true){
            try{
                Message message = connector.getWithoutAck(config.getBatchSize(), config.getTimeOut(), TimeUnit.SECONDS);
                long batchId = message.getId();
                List<CanalEntry.Entry> entries = message.getEntries();
                if (batchId == -1 || entries.isEmpty()) {
                    Thread.sleep(1000);
                    continue;
                }
                executor.execute(() -> {
                    processData(entries);
                });
                connector.ack(batchId);
            }catch(Exception e){
                log.error("TcpListener listenerData error:{}",e);
                continue;
            }
        }

    }

    private void processData(List<CanalEntry.Entry> list){
        for (CanalEntry.Entry entry : list) {
            if(StringUtils.isEmpty(entry.getHeader().getSchemaName()) || StringUtils.isEmpty(entry.getHeader().getTableName())){
                continue;
            }
            try{
                CanalDataBean bean = buildCanalDataBean(entry);
                log.debug("bean:{}", JSONObject.toJSONString(bean));
                subject.notify(bean);
            }catch(Exception e){
                log.error("TcpListener processData error:{}",e);
                continue;
            }
        }
    }

    @SneakyThrows
    private CanalDataBean buildCanalDataBean(CanalEntry.Entry entry){
        CanalDataBean bean = new CanalDataBean();
        CanalEntry.RowChange rowChange  = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
        List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();
        bean.setDatabaseName(entry.getHeader().getSchemaName());
        bean.setTableName(entry.getHeader().getTableName());
        bean.setExecuteTime(new Date(entry.getHeader().getExecuteTime()));
        bean.setType(DbUpdateType.getDbUpdateType(entry.getHeader().getEventType().name()));
        bean.setBefore(processColumns(rowDataList.get(0).getBeforeColumnsList()));
        bean.setAfer(processColumns(rowDataList.get(0).getAfterColumnsList()));

        return bean;
    }

    private JSONObject processColumns(List<CanalEntry.Column> columns){
        JSONObject json = new JSONObject();
        for (CanalEntry.Column column : columns) {
            json.put(column.getName(), column.getValue());
        }
        return json;
    }
}
