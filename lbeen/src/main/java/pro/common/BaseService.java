package pro.common;

import lb.dao.Dao;
import lb.dao.ORs;
import lb.dao.Sql;
import lb.dao.util.Record;

import java.util.List;
import java.util.function.Consumer;

/**
 * 基类service
 *
 * @author 李斌
 */
public abstract class BaseService<T extends BaseBean> {
    private Class<T> clazz;
    private Dao dao;

    public BaseService(Class<T> clazz) {
        this.clazz = clazz;
        dao = ORs.dao();
    }

    public Dao dao() {
        return dao;
    }

    public <OtherT> OtherT get(Class<OtherT> clazz, String sqlId, Consumer<Sql> sqlConsumer) {
        return dao.get(creatSql(sqlId, sqlConsumer), clazz);
    }

    public void save(T t) {
        dao.insert(clazz, t);
    }

    public List<Record> queryRecords(String sqlId) {
        return dao.queryRecords(creatSql(sqlId, null));
    }

    private Sql creatSql(String sqlId, Consumer<Sql> sqlConsumer) {
        Sql sql = ORs.sql(SqlManager.getSql(this.getClass(), sqlId));
        if (sqlConsumer != null) {
            sqlConsumer.accept(sql);
        }
        return sql;
    }

}
