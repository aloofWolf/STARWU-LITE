package starwu.lite.metadata.config.orm;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.orm")
public class OrmConfig {

    private Integer cachePeriod = 30; // 缓存有效期

    private Integer sqlExecTimeOut = 5; // sql执行超时时间

}
