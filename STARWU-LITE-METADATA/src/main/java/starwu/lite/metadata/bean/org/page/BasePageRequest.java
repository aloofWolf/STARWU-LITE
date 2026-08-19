package starwu.lite.metadata.bean.org.page;

/**
 * @author Lone Wolf
 * @ClassName: BasePageRequest
 * @Description: 分页请求的request
 * @date 2019年9月10日
 */
public class BasePageRequest {

    private Integer pageNum; // 查询第几页

    private Integer sizeNum; // 每页展示条数

    public Integer getPageNum() {
        if (this.pageNum == null) {
            return 1;
        }
        return pageNum;
    }

    public Integer getSizeNum() {
        if (this.sizeNum == null) {
            return 10;
        }
        return sizeNum;
    }


}
