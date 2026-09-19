package starwu.lite.metadata.config.dao;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.dao")
public class DaoConfig {

    private Integer cachePeriod = 30; // 缓存有效期

    private Integer sqlExecTimeOut = 10000; // sql执行超时时间

}
