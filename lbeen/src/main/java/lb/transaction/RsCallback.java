package lb.transaction;

import java.sql.ResultSet;

/**
 * 查询结果操作接口
 *
 * @author 李斌
 */
@FunctionalInterface
public interface RsCallback<T> {
    /**
     * 查询结果操作方法
     */
    T handle(ResultSet rs) throws Exception;
}
