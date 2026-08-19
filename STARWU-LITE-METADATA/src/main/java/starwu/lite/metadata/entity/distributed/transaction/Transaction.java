package starwu.lite.metadata.entity.distributed.transaction;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.metadata.enums.distributed.transaction.CallTimeType;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("transaction")
public class Transaction extends BaseEntity {

    private static final long serialVersionUID = -1318569395364745466L;
    private String code; // 编号
    private Long userId;
    private String sourceUrl;
    private String targetUrl;
    private JSONObject params;
    private ResponseResult result;
    private ErrorCodeType errCode;
    private String errMsg; // 返回信息
    private CallTimeType callTimeType;
    private Date CallTime;
    private Integer CallCount;
    private Date createTime;
}

