package lb.dao;

import lb.dao.util.Record;

import java.util.List;

/**
 * dao接口
 *
 * @author 李斌
 */
public interface Dao {
    /**
     * 获取一条记录
     */
    <T> T get(Class<T> clazz, Object id);

    /**
     * 获取记录list
     */
    <T> List<T> gets(Class<T> clazz, List<Object> ids);

    /**
     * 获取记录list
     */
    <T> List<T> gets(Class<T> clazz, Where where);

    /**
     * 插入一条记录
     */
    <T> void insert(Class<T> clazz, T t);

    /**
     * 插入多条记录
     */
    <T> void inserts(Class<T> clazz, List<T> ts);

    /**
     * 删除一条记录
     */
    <T> void delete(Class<T> clazz, Object id);

    /**
     * 删除多条记录
     */
    <T> void deletes(Class<T> clazz, List<Object> ids);

    /**
     * 带条件删除记录
     */
    <T> void deletes(Class<T> clazz, Where where);

    /**
     * 更新一条记录
     */
    <T> void update(Class<T> clazz, T t, boolean IgnoreNull);

    /**
     * 更新多条条记录
     */
    <T> void updates(Class<T> clazz, List<T> ts, boolean IgnoreNull);

    /**
     * 通过sql获取一个对象
     */
    <T> T get(Sql sql, Class<T> clazz);

    /**
     * 通过sql获取多个个对象
     */
    <T> List<T> gets(Sql sql, Class<T> clazz);

    /**
     * 查询sql返回第一个字段值
     */
    Object getFirstField(Sql sql);

    /**
     * 查询sql返回Record
     */
    Record queryRecord(Sql sql);

    /**
     * 查询sql返回Records
     */
    List<Record> queryRecords(Sql sql);
}
