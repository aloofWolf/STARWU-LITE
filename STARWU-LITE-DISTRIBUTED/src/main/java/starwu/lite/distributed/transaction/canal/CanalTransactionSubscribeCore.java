package starwu.lite.distributed.transaction.canal;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import starwu.lite.distributed.transaction.core.TransactionCore;
import starwu.lite.metadata.bean.canal.CanalDataBean;
import starwu.lite.metadata.bean.web.ResponseBean;
import starwu.lite.canal.notify.CanalObserverApi;
import starwu.lite.distributed.transaction.call.api.TransactionRemoteCallApi;
import starwu.lite.distributed.transaction.call.factory.TransactionRemoteCallFactory;
import starwu.lite.metadata.entity.distributed.transaction.Transaction;
import starwu.lite.metadata.enums.canal.DbUpdateType;
import starwu.lite.metadata.enums.distributed.transaction.CallTimeType;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;
import starwu.lite.util.StringUtil;

@RequiredArgsConstructor
@Component
public class CanalTransactionSubscribeCore implements CanalObserverApi {

    private final TransactionRemoteCallFactory factory;
    private final TransactionCore transactionCore;
    @Override
    public boolean isReceived(CanalDataBean canalDataBean) {
        if("starwu_lite_transaction".equals(canalDataBean.getTableName())){
            DbUpdateType operatorType = canalDataBean.getType();
            if (DbUpdateType.INSERT.equals(operatorType) || DbUpdateType.UPDATE.equals(operatorType)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void received(CanalDataBean canalDataBean) {

        JSONObject json = canalDataBean.getAfer();
        Transaction transaction = json.toJavaObject(Transaction.class, JSONReader.Feature.SupportSmartMatch);
        if(CallTimeType.IMMEDIATELY.equals(transaction.getCallTimeType())){
            TransactionRemoteCallApi api = factory.get(transaction.getTargetUrl());
            if(api == null){
                transactionCore.failHandle(transaction,api, ErrorCodeType.SYSTEM_EXCEPTION, StringUtil.appendWithUnSafe("未找到url:",transaction.getTargetUrl(),"对应的实现类"));
                return;
            }
            ResponseBean responseBean;
            try{
                responseBean = api.call(transaction.getParams());
            } catch (Exception e) {
                transactionCore.failHandle(transaction,api, ErrorCodeType.SYSTEM_EXCEPTION,e.getMessage());
                return;
            }

            if(ResponseResult.SUCCESS.equals(responseBean.getResult())){
                transactionCore.successHandle(transaction,api);
            }else{
                transactionCore.failHandle(transaction,api,responseBean.getErrCode(),responseBean.getErrMsg());
            }
        }
    }
}

