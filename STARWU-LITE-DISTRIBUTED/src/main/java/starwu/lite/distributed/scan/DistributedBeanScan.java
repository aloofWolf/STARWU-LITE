package starwu.lite.distributed.scan;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.distributed.session.core.SessionCore;
import starwu.lite.distributed.session.dao.SessionDao;
import starwu.lite.distributed.transaction.call.factory.TransactionRemoteCallFactory;
import starwu.lite.distributed.transaction.canal.CanalTransactionSubscribeCore;
import starwu.lite.distributed.transaction.core.TransactionCore;
import starwu.lite.distributed.transaction.dao.TransactionDao;
import starwu.lite.distributed.transaction.dao.TransactionLogDao;
import starwu.lite.distributed.transaction.scheduledTask.ScheduledTask;

@Configuration
@Import({SessionCore.class,
        SessionDao.class,
        TransactionRemoteCallFactory.class,
        CanalTransactionSubscribeCore.class,
        TransactionCore.class,
        ScheduledTask.class
})
public class DistributedBeanScan {

    @Bean
    public MapperFactoryBean<TransactionDao> transactionDao(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TransactionDao> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(TransactionDao.class); //直接指定Mapper接口Class
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

    @Bean
    public MapperFactoryBean<TransactionLogDao> transactionLogDao(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<TransactionLogDao> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(TransactionLogDao.class); //直接指定Mapper接口Class
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }
}
