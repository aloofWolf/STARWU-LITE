package starwu.lite.metadata.entity.dao.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Lone Wolf
 * @ClassName: BaseEntity
 * @Description: 实体父类
 * @date 2019年9月10日
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 2172790422703698161L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

}
