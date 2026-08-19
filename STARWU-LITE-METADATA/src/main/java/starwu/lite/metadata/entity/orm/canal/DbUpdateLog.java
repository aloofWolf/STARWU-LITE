package starwu.lite.metadata.entity.orm.canal;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import starwu.lite.metadata.entity.orm.base.BaseEntity;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("starwu_db_update_log")
public class DbUpdateLog extends BaseEntity {

    private static final long serialVersionUID = -1318569395364768462L;
    private String databaseName; // 数据库名称

    private String tableName; // 表名

    private Long pkValue; // id

    private Date executeTime; // 执行时间

    private String type; // 变更类型

    @TableField(
            typeHandler = FastjsonTypeHandler.class,
            jdbcType = JdbcType.VARCHAR
    )
    private JSONObject befores; // 变更前

    @TableField(
            typeHandler = FastjsonTypeHandler.class,
            jdbcType = JdbcType.VARCHAR
    )
    private JSONObject afters; // 变更后

    private Date createTime;
}

