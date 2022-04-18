package lb.ioc.annotation.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IocBean注解
 *
 * @author 李斌
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IocBean {
    /**
     * Ioc容器中beanname，不配置为类名首字母小写
     */
    String value() default "";

    /**
     * 是否为是单列，默认为单列
     */
    boolean single() default true;
}
