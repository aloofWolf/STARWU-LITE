package starwu.lite.metadata.entity.distributed.transaction;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("transaction")
public class TransactionLog extends BaseEntity {

    private Long transactionId;
    private String targetUrl;
    private JSONObject params;
    private ResponseResult result;
    private ErrorCodeType errCode;
    private String errMsg; // 返回信息
    private Date CallTime;



}
