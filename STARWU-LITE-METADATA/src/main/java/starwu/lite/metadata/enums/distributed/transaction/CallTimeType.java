package starwu.lite.metadata.enums.distributed.transaction;

import com.baomidou.mybatisplus.annotation.EnumValue;
import starwu.lite.metadata.enumPlus.EnumApi;

public enum CallTimeType implements EnumApi<Integer> {

    IMMEDIATELY(0, "立即调用"), SCHEDULED(1, "等下次定时任务调用"),NONE(2, "不在调用");

    @EnumValue
    private Integer id;

    private String name;

    private CallTimeType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }
}
