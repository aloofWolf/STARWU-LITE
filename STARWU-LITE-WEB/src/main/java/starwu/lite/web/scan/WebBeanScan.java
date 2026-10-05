package starwu.lite.web.scan;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import starwu.lite.util.SpringUtil;
import starwu.lite.web.interceptor.Interceptors;
import starwu.lite.web.interceptor.outer.SessionInterceptor;
import starwu.lite.web.interceptor.outer.ThreadLocalInterceptor;
import starwu.lite.web.log.dao.RequestLogDao;
import starwu.lite.web.log.filter.RequestFilter;
import starwu.lite.web.log.handle.LogHandleCore;
import starwu.lite.web.log.handle.LogHandleDefaultImpl;
import starwu.lite.web.log.interceptor.RequestLogInterceptor;
import starwu.lite.web.log.interceptor.ResponseLogInterceptor;
import starwu.lite.web.multipart.MultipartInterceptor;
import starwu.lite.web.response.ErrorCloseConfig;
import starwu.lite.web.response.ResponseExceptionsInterceptor;
import starwu.lite.web.response.ResponseUnifyInterceptor;
import starwu.lite.web.swagger.Swagger;

@Configuration
@Import({SessionInterceptor.class,
        ThreadLocalInterceptor.class,
        Interceptors.class,
        RequestFilter.class,
        LogHandleCore.class,
        LogHandleDefaultImpl.class,
        RequestLogInterceptor.class,
        ResponseLogInterceptor.class,
        MultipartInterceptor.class,
        ErrorCloseConfig.class,
        ResponseExceptionsInterceptor.class,
        ResponseUnifyInterceptor.class,
        Swagger.class,
        SpringUtil.class})
public class WebBeanScan {

    @Bean
    public MapperFactoryBean<RequestLogDao> requestLogDao(SqlSessionFactory sqlSessionFactory) {
        MapperFactoryBean<RequestLogDao> factoryBean = new MapperFactoryBean<>();
        factoryBean.setMapperInterface(RequestLogDao.class);
        factoryBean.setSqlSessionFactory(sqlSessionFactory);
        return factoryBean;
    }

}
