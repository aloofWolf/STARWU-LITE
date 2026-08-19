package starwu.lite.canal.listener;

import com.alibaba.otter.canal.client.CanalConnector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import starwu.lite.metadata.exception.AsyncException;

import javax.annotation.PostConstruct;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class TCPlistenerStart {

    private final TcpListener tcpListener;

    @PostConstruct
    public void start(){
      //  tcpListener.start();
    }
}
