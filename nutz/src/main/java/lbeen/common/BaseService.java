package lbeen.common;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.sql.callback.QueryRecordCallback;
import org.nutz.dao.sql.Sql;
import org.nutz.service.EntityService;

import java.util.List;

/**
 * 基类service
 *
 * @author 李斌
 */
public abstract class BaseService<T extends BaseBean> extends EntityService<T>{
    private Sql getSql(String sqlId){
        return SqlUtil.getSql(this.getClass(), sqlId);
    }

    public BaseService(Dao dao) {
        super(dao);
    }

    public List<Record> queryRecords(String sqlId){
        Sql sql = getSql(sqlId);
        sql.setCallback(new QueryRecordCallback());
        dao().execute(sql);
        @SuppressWarnings("unchecked")
        List<Record> records = (List<Record>)sql.getResult();
        return records;
    }
}
