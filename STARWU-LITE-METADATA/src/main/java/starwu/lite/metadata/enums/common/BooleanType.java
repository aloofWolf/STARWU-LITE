package starwu.lite.metadata.enums.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import starwu.lite.metadata.enumPlus.EnumApi;

@Getter
public enum BooleanType implements EnumApi<Integer> {
    TRUE(0, true, "是"), FALSE(1, false, "否");

    @EnumValue
    @JsonValue
    private Integer id;

    private boolean flag;

    private String name;

    private BooleanType(int id, boolean flag, String name) {
        this.id = id;
        this.flag = flag;
        this.name = name;
    }

}
