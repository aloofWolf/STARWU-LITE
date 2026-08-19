package starwu.lite.metadata.bean.canal;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import starwu.lite.metadata.enums.canal.DbUpdateType;

import java.util.Date;

@Data
public class CanalDataBean {

    private String databaseName; // 数据库名称

    private String tableName; // 表名

    private Date executeTime; // 执行时间

    private DbUpdateType type; // 变更类型

    private JSONObject before; // 变更前

    private JSONObject afer; // 变更后
}
