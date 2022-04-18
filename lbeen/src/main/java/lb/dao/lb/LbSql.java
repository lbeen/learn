package lb.dao.lb;

import lb.Logger;
import lb.dao.Sql;
import lb.dao.Where;
import lb.dao.util.SqlUtil;
import lb.util.Lang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql实现
 *
 * @author 李斌
 */
public class LbSql implements Sql {
    private static Logger logger = Logger.getLogger(LbSql.class);
    /**
     * sql
     */
    private String sql;
    /**
     * 参数
     */
    private List<Object> params;
    /**
     * 占位符顺序
     */
    private Map<String, Object> paramsMap;

    public LbSql(String sql) {
        this.sql = sql;
        this.params = new ArrayList<>();
        this.paramsMap = new HashMap<>();
    }

    /**
     * 设置参数list
     */
    @Override
    public LbSql addParams(List<Object> params) {
        this.params.addAll(params);
        return this;
    }

    /**
     * 按参数list顺序设置一个参数
     */
    @Override
    public LbSql addParam(Object object) {
        this.params.add(object);
        return this;
    }

    /**
     * 设置参数Map
     */
    @Override
    public LbSql addParams(Map<String, Object> paramsMap) {
        this.paramsMap.putAll(paramsMap);
        return this;
    }

    /**
     * 设置参数Map中一个参数
     */
    @Override
    public LbSql addParam(String name, Object param) {
        this.paramsMap.put(name, param);
        return this;
    }

    /**
     * 在$condition位置替换条件sql
     */
    @Override
    public LbSql setCondition(Where where) {
        sql = sql.replace("$condition", where.sql());
        addParams(where.params());
        return this;
    }

    /**
     * 在结尾替换条件sql
     */
    @Override
    public LbSql appendCondition(Where where) {
        sql += where.sql();
        addParams(where.params());
        return this;
    }

    /**
     * 获取设置完参数的PreparedStatement
     */
    @Override
    public PreparedStatement getPreparedStatement(Connection con)
            throws SQLException {
        PreparedStatement ps = con.prepareStatement(SqlUtil.paserSql(sql, params, paramsMap));
        if (!Lang.isEmpty(params)) {
            for (int i = 0, size = params.size(); i < size; i++) {
                ps.setObject(i + 1, params.get(i));
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug(SqlUtil.paserSqlWithValue(sql, paramsMap));
        }
        return ps;
    }

    @Override
    public String toString() {
        return sql;
    }
}
