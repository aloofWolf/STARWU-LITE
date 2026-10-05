package starwu.lite.dao.scan;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.dao.core.SlowSqlLogDao;
import starwu.lite.dao.dblog.dao.DbUpdateLogDao;
import starwu.lite.dao.dblog.factory.CanalDblogFactory;
import starwu.lite.dao.dblog.impl.CanalDblogHandleDefaultImpl;
import starwu.lite.dao.dblog.subcribe.CanalSubscribeSubscribeCore;
import starwu.lite.dao.interceptor.DaoPageQueryInterceptor;
import starwu.lite.dao.interceptor.MetaDataObjectHandler;
import starwu.lite.dao.interceptor.sql.DaoSqlInterceptor;
import starwu.lite.dao.interceptor.sql.SlowSqlHandleDeaultImpl;
import starwu.lite.dao.interceptor.sql.SlowSqlHnadleCore;
import starwu.lite.dao.metadata.MetadataCore;
import starwu.lite.dao.redis.cooperation.RedisCooperationCore;
import starwu.lite.dao.redis.dataConsistency.factory.CanalRedisFactory;
import starwu.lite.dao.redis.dataConsistency.impl.CanalRedisDefaultHandleImpl;
import starwu.lite.dao.redis.dataConsistency.subscribe.CanalRedisSubscribeCore;
import starwu.lite.util.SpringUtil;

@Configuration
@Import({CanalDblogFactory.class,
        CanalDblogHandleDefaultImpl.class,
        CanalSubscribeSubscribeCore.class,
        DaoSqlInterceptor.class,
        SlowSqlHandleDeaultImpl.class,
        SlowSqlHnadleCore.class,
        DaoPageQueryInterceptor.class,
        MetaDataObjectHandler.class,
        MetadataCore.class,
        RedisCooperationCore.class,
        CanalRedisFactory.class,
        CanalRedisDefaultHandleImpl.class,
        CanalRedisSubscribeCore.class,
        SpringUtil.class
})
public class DaoBeanScan {

    @Bean
    public MapperFactoryBean<SlowSqlLogDao> slowSqlLogDao(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<SlowSqlLogDao> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(SlowSqlLogDao.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<DbUpdateLogDao> dbUpdateLogDao(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<DbUpdateLogDao> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(DbUpdateLogDao.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
