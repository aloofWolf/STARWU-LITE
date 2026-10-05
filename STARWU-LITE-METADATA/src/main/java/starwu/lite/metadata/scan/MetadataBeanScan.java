package starwu.lite.metadata.scan;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.metadata.config.canal.CanalConfig;
import starwu.lite.metadata.config.canal.CanalItemConfig;
import starwu.lite.metadata.config.dao.DaoConfig;
import starwu.lite.metadata.config.distributed.session.SessionConfig;
import starwu.lite.metadata.config.distributed.session.SessionExcludeUrlConfig;
import starwu.lite.metadata.config.manage.async.AsyncConfig;
import starwu.lite.metadata.config.manage.async.AsyncItemConfig;
import starwu.lite.metadata.config.web.SwaggerConfig;
import starwu.lite.metadata.config.web.WebConfig;
import starwu.lite.metadata.config.web.WebExcludeRecordLogConfig;
import starwu.lite.metadata.config.web.WebExcludeUnifyRespConfig;
import starwu.lite.util.SpringUtil;

@Configuration
@Import({CanalConfig.class,
        CanalItemConfig.class,
        DaoConfig.class,
        SessionConfig.class,
        SessionExcludeUrlConfig.class,
        AsyncConfig.class,
        AsyncItemConfig.class,
        SwaggerConfig.class,
        WebConfig.class,
        WebExcludeRecordLogConfig.class,
        WebExcludeUnifyRespConfig.class,
        SpringUtil.class})
public class MetadataBeanScan {
}
