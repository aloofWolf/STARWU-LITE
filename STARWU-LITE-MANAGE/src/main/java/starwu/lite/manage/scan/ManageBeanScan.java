package starwu.lite.manage.scan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.manage.async.threadPool.AsyncDefaultConfig;
import starwu.lite.manage.async.threadPool.AsyncExceptionHandle;
import starwu.lite.manage.async.threadPool.AsyncThreadPool;
import starwu.lite.manage.threadLocal.ThreadLocalCore;
import starwu.lite.util.SpringUtil;

@Configuration
@Import({AsyncDefaultConfig.class,
        AsyncExceptionHandle.class,
        AsyncThreadPool.class,
        ThreadLocalCore.class,
        SpringUtil.class
})
public class ManageBeanScan {
}
