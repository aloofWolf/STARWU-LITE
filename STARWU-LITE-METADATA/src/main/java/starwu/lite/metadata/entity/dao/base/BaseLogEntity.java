package starwu.lite.metadata.entity.dao.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BaseLogEntity extends BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    private String createRequestLogCode;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateRequestLogCode;


}
