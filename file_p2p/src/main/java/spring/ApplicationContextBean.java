package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * by yidi on 4/19/19
 */
public class ApplicationContextBean {
    public static ApplicationContext getContext() {
        return new ClassPathXmlApplicationContext("classpath:spring-config.xml");
    }
}
