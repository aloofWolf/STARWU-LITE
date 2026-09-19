package starwu.lite.metadata.entity.dao.base.slowSql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import starwu.lite.metadata.entity.dao.base.BaseEntity;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("starwu_lite_slowsql_log")
public class SlowSqlLog extends BaseEntity {

    private static final long serialVersionUID = -2726756795120371714L;

    private String requestCode; // 请求code

    private Long userId; // 用户id

    private String userName; // 用户名称

    private Date startTime; // sql开始执行时间

    private Date endTime; // sql执行结束时间

    private Long consumTime; // sql执行消耗时间

    private String slowSql; // sql

    private Integer containerId; // 服务端容器id

    private Long threadId; // 服务端线程id
}
