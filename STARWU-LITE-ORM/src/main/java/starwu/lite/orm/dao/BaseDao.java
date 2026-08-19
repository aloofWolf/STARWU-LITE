package starwu.lite.orm.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import starwu.lite.metadata.bean.org.page.BasePageRequest;
import starwu.lite.metadata.bean.org.page.BasePageResponse;
import starwu.lite.metadata.entity.orm.base.BaseEntity;
import starwu.lite.util.SpringUtil;

public interface BaseDao<T extends BaseEntity> extends BaseMapper<T> {

    @SuppressWarnings("unchecked")
    default T getById(Long id) {
        BaseDaoExt ext = SpringUtil.getBean(BaseDaoExt.class);
        return (T) ext.getById(this, id);
    }

    default BasePageResponse<T> getPage(BasePageRequest basePageRequest, QueryWrapper<T> wrapper) {
        IPage<T> page = new Page<>(basePageRequest.getPageNum(), basePageRequest.getSizeNum());
        page = this.selectPage(page, wrapper);
        return new BasePageResponse<T>(page);
    }
}
