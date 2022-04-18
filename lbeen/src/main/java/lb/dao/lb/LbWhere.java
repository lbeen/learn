package lb.dao.lb;

import lb.dao.Where;

import java.util.HashMap;
import java.util.Map;

/**
 * sql条件接口实现
 *
 * @author 李斌
 */
public class LbWhere implements Where {
    private StringBuilder sql = new StringBuilder();

    private Map<String, Object> params = new HashMap<>();

    @Override
    public Map<String, Object> params() {
        return params;
    }

    @Override
    public String sql() {
        return sql.toString();
    }

    @Override
    public LbWhere andExpression(String expression, String column, Object var) {
        if (sql.length() == 0) {
            sql.append(" WHERE ").append(expression);
        } else {
            sql.append(" AND").append(expression);
        }
        if (column != null) {
            params.put(column, var);
        }
        return this;
    }

    @Override
    public LbWhere andExpression(String expression, Map<String, Object> params) {
        if (sql.length() == 0) {
            sql.append(" WHERE ").append(expression);
        } else {
            sql.append(" AND").append(expression);
        }
        sql.append(expression);
        if (params != null) {
            this.params.putAll(params);
        }
        return this;
    }

    @Override
    public LbWhere andExpression(String expression) {
        return andExpression(expression, null, null);
    }

    @Override
    public LbWhere and(String colum, String op, Object val) {
        if (val == null) {
            return andIsNull(colum);
        }
        if (sql.length() == 0) {
            sql.append(" WHERE ");
        } else {
            sql.append(" AND ");
        }
        sql.append(" ").append(colum).append(op).append("@").append(colum);
        params.put(colum, val);
        return this;
    }

    @Override
    public LbWhere andEqual(String colum, Object val) {
        return and(colum, "=", val);
    }

    @Override
    public LbWhere andGt(String colum, Object val) {
        return and(colum, ">", val);
    }

    @Override
    public LbWhere andLt(String colum, Object val) {
        return and(colum, "<", val);
    }

    @Override
    public LbWhere andIsNull(String colum) {
        if (sql.length() == 0) {
            sql.append(" WHERE");
        } else {
            sql.append(" AND ");
        }
        sql.append(colum).append(" IS NULL");
        return this;
    }

    @Override
    public LbWhere asc(String colum) {
        sql.append(" ORDER BY ").append(colum);
        return this;
    }

    @Override
    public LbWhere desc(String colum) {
        sql.append(" ORDER BY ").append(colum).append(" DESC");
        return this;
    }

    @Override
    public Where group(String colum) {
        sql.append(" GROUP BY ").append(colum).append(" DESC");
        return this;
    }
}
