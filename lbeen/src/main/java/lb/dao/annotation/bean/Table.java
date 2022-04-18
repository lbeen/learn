package lb.dao.annotation.bean;

import lb.dao.annotation.field.Column;
import lb.dao.annotation.field.Id;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库表名,有此注解表示为数据库实体对象
 *
 * @author 李斌
 * @see Column
 * @see Id
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 数据库表名,不配置表示类名就是表名
     */
    String value() default "";
}
