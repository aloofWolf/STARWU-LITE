package starwu.lite.canal.scan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.canal.listener.TcpListener;
import starwu.lite.canal.notify.CanalSubject;
import starwu.lite.util.SpringUtil;

@Configuration
@Import({TcpListener.class,
        CanalSubject.class,
        SpringUtil.class})
public class CanalBeanScan {
}
