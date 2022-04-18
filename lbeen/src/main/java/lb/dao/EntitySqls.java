package lb.dao;

import lb.dao.lb.OracleEntitySql;
import lb.transaction.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取实体操作sql方法类
 *
 * @author 李斌
 */
public abstract class EntitySqls {
    private static Map<Class<?>, EntitySql<?>> ENTITYSQL_POOL = new HashMap<>();

    private static <T> EntitySql getEntitySql(Class<T> clazz) {
        return ENTITYSQL_POOL.computeIfAbsent(clazz, k -> new OracleEntitySql<>(ORs.getOrInfo(clazz)));
    }

    /**
     * 通过主键查询对象sql对象
     */
    public static <T> Sql select(Class<T> clazz, Object id) {
        return getEntitySql(clazz).select(id);
    }

    /**
     * 通过多个主键查询对象sql对象
     */
    public static <T> Sql selects(Class<T> clazz, List<Object> ids) {
        return getEntitySql(clazz).selects(ids);
    }

    /**
     * 通过条件查询对象sql对象
     */
    public static <T> Sql selects(Class<T> clazz, Where where) {
        return getEntitySql(clazz).selects(where);
    }

    /**
     * 插入一个对象sql
     */
    public static <T> Sql insert(Class<T> clazz, T t, Transaction tran) throws Exception {
        return getEntitySql(clazz).insert(t, tran);
    }

    /**
     * 插入多个对象sql
     */
    public static <T> Sql inserts(Class<T> clazz, List<T> ts, Transaction tran) throws Exception {
        return getEntitySql(clazz).inserts(ts, tran);
    }

    /**
     * 通过主键删除对象sql
     */
    public static <T> Sql delete(Class<T> clazz, Object id) throws Exception {
        return getEntitySql(clazz).delete(id);
    }

    /**
     * 通过多个主键删除对象sql
     */
    public static <T> Sql deletes(Class<T> clazz, List<Object> ids) throws Exception {
        return getEntitySql(clazz).deletes(ids);
    }

    /**
     * 通过条件删除对象sql
     */
    public static <T> Sql deletes(Class<T> clazz, Where where) throws Exception {
        return getEntitySql(clazz).deletes(where);
    }

    /**
     * 更新对象sql
     *
     * @param IgnoreNull 是否忽略null
     */
    public static <T> Sql update(Class<T> clazz, T t, boolean IgnoreNull) throws Exception {
        return getEntitySql(clazz).update(t, IgnoreNull);
    }

    /**
     * 更新多个对象sql
     *
     * @param IgnoreNull 是否忽略null
     */
    public static <T> Sql updates(Class<T> clazz, List<T> ts, boolean IgnoreNull) throws Exception {
        return getEntitySql(clazz).updates(ts, IgnoreNull);
    }
}
