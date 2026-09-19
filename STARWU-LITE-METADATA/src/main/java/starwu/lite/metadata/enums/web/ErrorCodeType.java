package starwu.lite.metadata.enums.web;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import starwu.lite.metadata.enumPlus.EnumApi;

@Getter
public enum ErrorCodeType implements EnumApi<Integer> {

	SYSTEM_EXCEPTION(1, 1001, "系统异常"), 
	BUSINESS_EXCEPTION(2, 1002, "业务异常"), 
	PARAM_VALIDATION_EXCEPTION(3, 1003,"参数校验异常"), 
	PERMISSION_VALIDATION_EXCEPTION(4, 1004,"权限校验异常"), 
	SESSION_TIMEOUT_EXCEPTION(5, 1005, "SESSION过期异常"),
	NO_HANDLER_FOUND_EXCEPTION(6, 1006, "404异常");

	private Integer id;

	@JsonValue
	@EnumValue
	private int code;

	@SuppressWarnings("unused")
	private String name;

	private ErrorCodeType(int id, int code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}
}
