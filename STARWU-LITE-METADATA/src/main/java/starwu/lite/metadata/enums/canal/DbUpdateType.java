package starwu.lite.metadata.enums.canal;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import starwu.lite.metadata.enumPlus.EnumApi;

@Getter
public enum DbUpdateType implements EnumApi<Integer> {
	INSERT(0, "新增"), UPDATE(1, "更新"), DELETE(2, "删除");

	@EnumValue
	private int id;
	@JsonValue
	private String name;

	DbUpdateType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId(){
		return this.id;
	}
	public static DbUpdateType getDbUpdateType(String desc) {
		if ("INSERT".equals(desc)) {
			return DbUpdateType.INSERT;
		} else if ("UPDATE".equals(desc)) {
			return DbUpdateType.UPDATE;
		} else if ("DELETE".equals(desc)) {
			return DbUpdateType.DELETE;
		} else {
			return null;
		}
	}
}
