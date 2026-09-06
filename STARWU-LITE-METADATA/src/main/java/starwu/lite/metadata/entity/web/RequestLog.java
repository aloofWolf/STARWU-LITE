package starwu.lite.metadata.entity.web;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.metadata.enums.web.ResponseResult;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("starwu_lite_request_log")
public class RequestLog extends BaseEntity {

	private static final long serialVersionUID = -2726756795120371714L;

	private String code; // 编号
	
	private Long userId; // 用户id

	private String userName; // 用户名称

	private Date startTime; // 请求开始时间

	private Date endTime; // 请求结束时间

	private Long consumTime; // 请求消耗时间

	private String clientType; // 客户端设备类型

	private String osType; // 客户端操作系统类型

	private String clientIp; // 客户端ip

	private Integer clientPort; // 客户端port

	private String requestMethod; // 请求方式

	private String url; // 请求url

	private String requestParam; // 请求参数

	private String responseParam; // 响应参数

	private Integer containerId; // 服务端容器id

	private Long threadId; // 服务端线程id

	private ResponseResult result; // 请求状态

	private String errMsg; // 请求失败错误信息

}
