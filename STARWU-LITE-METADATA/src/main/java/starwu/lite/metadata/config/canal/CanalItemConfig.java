package starwu.lite.metadata.config.canal;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class CanalItemConfig {

    private String tcpHost = ""; // canal的host

    private int tcpPort = 0; // canal的端口
}
