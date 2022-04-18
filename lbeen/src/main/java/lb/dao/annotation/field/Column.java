package lb.dao.annotation.field;

import lb.dao.annotation.bean.Table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据库列名注解
 *
 * @author 李斌
 * @see Table
 * @see Id
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 数据库列名，value为空表示数据库列名与对象属性相同
     */
    String value() default "";
}
