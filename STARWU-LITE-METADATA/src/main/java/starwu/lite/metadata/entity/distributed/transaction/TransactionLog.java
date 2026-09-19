package starwu.lite.metadata.entity.distributed.transaction;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.Fastjson2TypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import starwu.lite.metadata.entity.dao.base.BaseEntity;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("starwu_lite_transaction_log")
public class TransactionLog extends BaseEntity {

    private Long transactionId;
    private String targetUrl;
    @TableField(
            typeHandler = Fastjson2TypeHandler.class,
            jdbcType = JdbcType.VARCHAR
    )
    private JSONObject params;
    private ResponseResult result;
    private ErrorCodeType errCode;
    private String errMsg; // 返回信息
    private Date CallTime;



}
