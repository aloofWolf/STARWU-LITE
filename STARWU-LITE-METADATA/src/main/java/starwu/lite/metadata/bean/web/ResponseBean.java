package starwu.lite.metadata.bean.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import starwu.lite.metadata.enums.web.ErrorCodeType;
import starwu.lite.metadata.enums.web.ResponseResult;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean implements Serializable {

	private static final long serialVersionUID = 8096365334124260698L;

	private ResponseResult result;
	
	private ErrorCodeType errCode;

	private String errMsg; // 返回信息

	private Object data; // 返回数据

	public static ResponseBean success(Object data) {
		return ResponseBean.builder().result(ResponseResult.SUCCESS).data(data).build();
	}

	public static ResponseBean error(ErrorCodeType errCode,String errMsg) {
		return ResponseBean.builder().result(ResponseResult.FAIL).errCode(errCode).errMsg(errMsg).build();
	}

}
