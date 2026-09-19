package starwu.lite.metadata.enums.distributed.session;

import com.baomidou.mybatisplus.annotation.EnumValue;
import starwu.lite.metadata.enumPlus.EnumApi;

public enum LoginKickOutType implements EnumApi<Integer> {

    USER(0, "以用户维度踢出"),
    USER_AND_DEVICE(1, "以用户+登录设备类型维度踢出"),
    NONE(2, "不踢出");

    @EnumValue
    private Integer id;

    private String name;

    private LoginKickOutType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}

