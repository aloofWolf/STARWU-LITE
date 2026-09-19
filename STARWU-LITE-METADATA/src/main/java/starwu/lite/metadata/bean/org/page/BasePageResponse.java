package starwu.lite.metadata.bean.org.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

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
