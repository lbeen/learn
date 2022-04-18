package lb.transaction.annotation;

import lb.transaction.TransactionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事务注解
 *
 * @author 李斌
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tran {
    /**
     * 事务类型
     */
    TransactionType type() default TransactionType.OPEN;
}
