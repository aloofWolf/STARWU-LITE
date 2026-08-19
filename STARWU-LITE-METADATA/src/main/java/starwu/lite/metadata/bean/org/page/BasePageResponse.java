package starwu.lite.metadata.bean.org.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @param <T>
 * @author Lone Wolf
 * @ClassName: BasePageResponse
 * @Description: 分页请求的response
 * @date 2019年9月10日
 */
@Data
public class BasePageResponse<T> {

    private long pageNum; // 查询第几页

    private long sizeNum; // 每页展示条数

    private long totalNum; // 总条数

    private List<T> details; // 查询明细

    public BasePageResponse(IPage<T> page) {
        this.pageNum = page.getCurrent();
        this.sizeNum = page.getSize();
        this.totalNum = page.getTotal();
        this.details = page.getRecords();
    }

}
