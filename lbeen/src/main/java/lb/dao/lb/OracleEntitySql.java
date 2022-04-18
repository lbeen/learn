package lb.dao.lb;

import lb.dao.EntitySql;
import lb.dao.ORInfo;
import lb.dao.ORs;
import lb.dao.Sql;
import lb.dao.Where;
import lb.dao.util.RsUtil;
import lb.dao.util.SqlUtil;
import lb.transaction.Transaction;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实体对象操作sql（oracle）
 *
 * @author 李斌
 */
public class OracleEntitySql<T> implements EntitySql<T> {

    private Class<T> clazz;

    private String table;

    private ORInfo<T>.Key key;

    private Map<String, ORInfo<T>.Column> columInfos;

    /**
     * 解析映射信息
     */
    public OracleEntitySql(ORInfo<T> orInfo) {
        clazz = orInfo.getClazz();
        table = orInfo.getTable();
        key = orInfo.getKey();
        columInfos = orInfo.getColumInfos();
    }

    @Override
    public Sql select(Object id) {
        Sql sql = ORs.sql("SELECT * FROM " + table + " WHERE " + key.getColum() + "=?");
        return sql.addParam(id);
    }

    @Override
    public Sql selects(List<Object> ids) {
        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(table).append(" WHERE ").append(key.getColum()).append(" IN (");
        for (int i = 0, len = ids.size(); i < len; i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1).append(")");
        Sql sql = ORs.sql(sb.toString());
        return sql.addParams(ids);
    }

    @Override
    public Sql selects(Where where) {
        return ORs.sql("SELECT * FROM " + table).appendCondition(where);
    }

    @Override
    public Sql insert(T t, Transaction tran) throws Exception {
        StringBuilder sb = new StringBuilder();
        List<Object> params = new ArrayList<>();
        singleInsert(sb, params, t, tran);
        return ORs.sql(sb.toString()).addParams(params);
    }

    @Override
    public Sql inserts(List<T> ts, Transaction tran) throws Exception {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = new StringBuilder("BEGIN ");
        for (T t : ts) {
            singleInsert(sb, params, t, tran);
            sb.append(";");
        }
        sb.append("END;");
        return ORs.sql(sb.toString()).addParams(params);
    }

    @Override
    public Sql update(T t, boolean ignoreNull) throws Exception {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        singleUpdate(sb, params, t, ignoreNull);
        return ORs.sql(sb.toString()).addParams(params);
    }

    @Override
    public Sql updates(List<T> ts, boolean ignoreNull) throws Exception {
        List<Object> params = new ArrayList<>();
        StringBuilder sb = new StringBuilder("BEGIN ");
        for (T t : ts) {
            singleUpdate(sb, params, t, ignoreNull);
            sb.append(";");
        }
        return ORs.sql(sb.append("END;").toString()).addParams(params);
    }

    @Override
    public Sql delete(Object id) throws Exception {
        String sqlStr = "DELETE FROM " + table + " WHERE " + key.getColum() + "=?";
        return ORs.sql(sqlStr).addParam(id);
    }

    @Override
    public Sql deletes(List<Object> ids) throws Exception {
        StringBuilder sb = new StringBuilder("DELETE FROM ");
        sb.append(table).append(" WHERE ").append(key.getColum()).append(" IN (");
        for (int i = 0, len = ids.size(); i < len; i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        Sql sql = ORs.sql(sb.toString());
        return sql.addParams(ids);
    }

    @Override
    public Sql deletes(Where where) throws Exception {
        return ORs.sql("DELETE FROM " + table).appendCondition(where);
    }

    /**
     * 获取单个实体插入sql
     */
    private void singleInsert(StringBuilder sb, List<Object> params, T t, Transaction tran) throws Exception {
        sb.append("INSERT INTO ").append(table).append("(");
        StringBuilder valuesBuilder = new StringBuilder();
        Method method;
        for (Map.Entry<String, ORInfo<T>.Column> entry : columInfos.entrySet()) {
            ORInfo<T>.Column colum = entry.getValue();
            method = clazz.getMethod(colum.getGet());
            Object val = method.invoke(t);
            if (val != null) {
                sb.append(entry.getKey()).append(",");
                valuesBuilder.append("?,");
                params.add(val);
            } else if (colum.getVar() != null) {
                List<String> columns = new ArrayList<>();
                String var = SqlUtil.paserSql(colum.getVar(), columns);
                sb.append(entry.getKey()).append(",");
                valuesBuilder.append(var).append(",");
                for (String column : columns) {
                    params.add(clazz.getMethod(columInfos.get(column).getGet()).invoke(t));
                }
            } else if (colum.getEntityVar() != null) {
                List<String> columns = new ArrayList<>();
                String entityVar = SqlUtil.paserSql(colum.getEntityVar(), columns);
                Sql sql = ORs.sql(entityVar);
                List<Object> columnParams = new ArrayList<>();
                for (String column : columns) {
                    columnParams.add(clazz.getMethod(columInfos.get(column).getGet()).invoke(t));
                }
                sql.addParams(columnParams);
                Object value = tran.exec(sql, rs -> RsUtil.getFirstField(rs, colum.getParamClass()));
                clazz.getMethod(colum.getSet(), colum.getParamClass()).invoke(t, value);
                sb.append(entry.getKey()).append(",");
                valuesBuilder.append("?,");
                params.add(value);
            }
        }
        method = clazz.getMethod(key.getGet());
        params.add(method.invoke(t));
        sb.append(key.getColum()).append(") VALUES (").append(valuesBuilder).append("?)");
    }

    /**
     * 获取单个实体更新sql
     */
    private void singleUpdate(StringBuilder sb, List<Object> params, T t, boolean ignoreNull) throws Exception {
        Method method;
        sb.append("UPDATE ").append(table).append(" SET ");
        for (Map.Entry<String, ORInfo<T>.Column> entry : columInfos.entrySet()) {
            method = clazz.getMethod(entry.getValue().getGet());
            Object val = method.invoke(t);
            if (val != null) {
                sb.append(entry.getKey()).append("=?,");
                params.add(val);
            } else if (!ignoreNull) {
                sb.append(entry.getKey()).append("=NULL,");
            }
        }
        sb.deleteCharAt(sb.length() - 1).append(" WHERE ").append(key.getColum()).append("=?");
        params.add(clazz.getMethod(key.getGet()).invoke(t));
    }
}
