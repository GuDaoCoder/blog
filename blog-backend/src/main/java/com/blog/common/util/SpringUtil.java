package com.blog.common.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author zouzhangpeng
 * @desc
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    /**
     * -- GETTER -- 获取applicationContext
     * @return org.springframework.context.ApplicationContext
     */
    @Getter
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 通过name获取 Bean
     * @param name String
     * @return java.lang.Object
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过注解获取bean
     * @param clazz
     * @return Map<String,Object>
     **/
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clazz) {
        return getApplicationContext().getBeansWithAnnotation(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
