package starwu.lite.canal.scan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.canal.listener.TcpListener;
import starwu.lite.canal.notify.CanalSubject;

@Configuration
@Import({TcpListener.class,
        CanalSubject.class})
public class CanalBeanScan {
}
