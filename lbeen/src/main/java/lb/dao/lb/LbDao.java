package lb.dao.lb;

import lb.dao.Dao;
import lb.dao.EntitySqls;
import lb.dao.Sql;
import lb.dao.Where;
import lb.dao.util.Record;
import lb.dao.util.RsUtil;
import lb.transaction.Transaction;
import lb.transaction.Transations;

import java.util.List;

/**
 * dao实现
 *
 * @author 李斌
 */
public class LbDao implements Dao {
    /**
     * 获取一条记录
     */
    @Override
    public <T> T get(Class<T> clazz, Object id) {
        if (id == null) {
            return null;
        }
        Sql sql = EntitySqls.select(clazz, id);
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, rs -> RsUtil.rs2Obj(rs, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取记录list
     */
    @Override
    public <T> List<T> gets(Class<T> clazz, List<Object> ids) {
        Sql sql = EntitySqls.selects(clazz, ids);
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, rs -> RsUtil.rs2Objs(rs, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取记录list
     */
    @Override
    public <T> List<T> gets(Class<T> clazz, Where where) {
        Sql sql = EntitySqls.selects(clazz, where);
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, rs -> RsUtil.rs2Objs(rs, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入一条记录
     */
    @Override
    public <T> void insert(Class<T> clazz, T t) {
        try {
            Transaction tran = Transations.getTransaction();
            Sql sql = EntitySqls.insert(clazz, t, tran);
            tran.exec(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入多条记录
     */
    @Override
    public <T> void inserts(Class<T> clazz, List<T> ts) {
        try {
            Transaction tran = Transations.getTransaction();
            tran.exec(EntitySqls.inserts(clazz, ts, tran));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除一条记录
     */
    @Override
    public <T> void delete(Class<T> clazz, Object id) {
        try {
            Transaction tran = Transations.getTransaction();
            tran.exec(EntitySqls.delete(clazz, id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除多条记录
     */
    @Override
    public <T> void deletes(Class<T> clazz, List<Object> ids) {
        try {
            Transaction tran = Transations.getTransaction();
            tran.exec(EntitySqls.deletes(clazz, ids));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 带条件删除记录
     */
    @Override
    public <T> void deletes(Class<T> clazz, Where where) {
        try {
            Transaction tran = Transations.getTransaction();
            tran.exec(EntitySqls.deletes(clazz, where));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新一条记录
     */
    @Override
    public <T> void update(Class<T> clazz, T t, boolean IgnoreNull) {
        try {
            Transaction tran = Transations.getTransaction();
            Sql sql = EntitySqls.update(clazz, t, IgnoreNull);
            tran.exec(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新多条条记录
     */
    @Override
    public <T> void updates(Class<T> clazz, List<T> ts, boolean IgnoreNull) {
        try {
            Transaction tran = Transations.getTransaction();
            Sql sql = EntitySqls.updates(clazz, ts, IgnoreNull);
            tran.exec(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过sql获取一个对象
     */
    @Override
    public <T> T get(Sql sql, Class<T> clazz) {
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, rs -> RsUtil.rs2Obj(rs, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 通过sql获取多个个对象
     */
    @Override
    public <T> List<T> gets(Sql sql, Class<T> clazz) {
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, rs -> RsUtil.rs2Objs(rs, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询sql返回第一个字段值
     */
    @Override
    public Object getFirstField(Sql sql) {
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, rs -> RsUtil.getFirstField(rs, null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询sql返回Records
     */
    @Override
    public Record queryRecord(Sql sql) {
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, RsUtil::rs2Map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询sql返回Record
     */
    @Override
    public List<Record> queryRecords(Sql sql) {
        try {
            Transaction tran = Transations.getTransaction();
            return tran.exec(sql, RsUtil::rs2Maps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
