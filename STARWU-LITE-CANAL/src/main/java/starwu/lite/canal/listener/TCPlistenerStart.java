package starwu.lite.canal.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Slf4j
public class TCPlistenerStart {

    private final TcpListener tcpListener;

    @PostConstruct
    public void start(){
       tcpListener.start();
    }
}
