package starwu.lite.metadata.enums.web;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import starwu.lite.metadata.enumPlus.EnumApi;

@Getter
public enum ResponseResult implements EnumApi<Integer> {

	SUCCESS(0, "成功"), FAIL(1, "失败");

	@EnumValue
	private Integer id;

	private String name;

	private ResponseResult(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
