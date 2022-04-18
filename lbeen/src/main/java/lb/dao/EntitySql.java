package lb.dao;

import lb.transaction.Transaction;

import java.util.List;

/**
 * 实体对象操作sql
 *
 * @author 李斌
 */
public interface EntitySql<T> {
    /**
     * 通过主键查询对象sql对象
     */
    Sql select(Object id);

    /**
     * 通过多个主键查询对象sql对象
     */
    Sql selects(List<Object> ids);

    /**
     * 通过条件查询对象sql对象
     */
    Sql selects(Where where);

    /**
     * 插入一个对象sql
     */
    Sql insert(T t, Transaction tran) throws Exception;

    /**
     * 插入多个对象sql
     */
    Sql inserts(List<T> ts, Transaction tran) throws Exception;

    /**
     * 更新对象sql
     *
     * @param IgnoreNull 是否忽略null
     */
    Sql update(T t, boolean IgnoreNull) throws Exception;

    /**
     * 更新多个对象sql
     *
     * @param IgnoreNull 是否忽略null
     */
    Sql updates(List<T> ts, boolean IgnoreNull) throws Exception;

    /**
     * 通过主键删除对象sql
     */
    Sql delete(Object id) throws Exception;

    /**
     * 通过多个主键删除对象sql
     */
    Sql deletes(List<Object> ids) throws Exception;

    /**
     * 通过条件删除对象sql
     */
    Sql deletes(Where where) throws Exception;
}
