package starwu.lite.metadata.config.canal;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
@ConfigurationProperties(prefix = "starwu.lite.metadata.config.canal")
public class CanalConfig {

    private boolean tcpEnabled = false; // 是否开启tcp模式从canal拉取数据

    private List<CanalItemConfig> tcpHosts = new ArrayList<>();; // canal的host

    private String destination = ""; // 监听数据库的实例名

    private String tcpUsername = ""; // canal的用户名

    private String tcpPassword = ""; // canal的密码

    private String subscribe; // 订阅的库和表 默认所有

    private int batchSize = 1; // 一次性从canal拉取多少条数据

    private long timeOut = 3; // 从canal拉取数据的超时时间

    private String handleAsyncThreadName = "default";

    private String notifyDataAsyncThreadName = "default";
}
