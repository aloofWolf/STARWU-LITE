package starwu.lite.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class SpringUtil implements ApplicationContextAware, PriorityOrdered, BeanPostProcessor {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> cls) {
        return applicationContext.getBean(cls);
    }

    @Override
    public int getOrder() {
        return 0;
    }

    /*@Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // ====== 重点：系统未完全启动，Bean还没加载，这里已经拿到上下文 ======
        System.out.println("极早期获取上下文：" + applicationContext);

        // 存入全局静态工具类
        SpringUtil.applicationContext = applicationContext;
    }*/
}
