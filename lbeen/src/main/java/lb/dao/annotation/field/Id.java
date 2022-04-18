package lb.dao.annotation.field;

import lb.dao.annotation.bean.Table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 主键标识
 *
 * @author 李斌
 * @see Table
 * @see Column
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    /**
     * 数据库列名，value为空表示数据库列名与对象属性相同
     */
    String value() default "";

    /**
     * 主键是否是数据库自动增长字段，默认为TRUE：自增长
     */
    boolean auto() default false;
}
