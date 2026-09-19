package starwu.lite.metadata.entity.distributed.transaction;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import starwu.lite.metadata.entity.dao.base.BaseEntity;
import starwu.lite.metadata.enums.distributed.transaction.CallTimeType;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("starwu_lite_transaction")
public class Transaction extends BaseEntity {

    private static final long serialVersionUID = -1318569395364745466L;
    private String requestCode; // 编号
    private Long userId;
    private String sourceUrl;
    private String targetUrl;
    @TableField(
            typeHandler = Fastjson2TypeHandler.class,
            jdbcType = JdbcType.VARCHAR
    )
    @JSONField(jsonDirect = true)
    private JSONObject params;
    private ResponseResult result;
    private ErrorCodeType errCode;
    private String errMsg; // 返回信息
    private CallTimeType callTimeType;
    private Date CallTime;
    private Integer CallCount;
    private Date createTime;
}

