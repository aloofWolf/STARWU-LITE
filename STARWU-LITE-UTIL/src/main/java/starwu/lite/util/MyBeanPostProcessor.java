package starwu.lite.util;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyBeanPostProcessor implements BeanPostProcessor {
}
