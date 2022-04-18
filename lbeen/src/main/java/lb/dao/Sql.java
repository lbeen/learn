package lb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 李斌
 */
public interface Sql {
    /**
     * 设置参数list
     */
    Sql addParams(List<Object> params);

    /**
     * 按参数list顺序设置一个参数
     */
    Sql addParam(Object object);

    /**
     * 设置参数Map
     */
    Sql addParams(Map<String, Object> paramMap);

    /**
     * 设置参数Map中一个参数
     */
    Sql addParam(String place, Object param);

    /**
     * 在$condition位置替换条件sql
     */
    Sql setCondition(Where where);

    /**
     * 在结尾添加条件sql
     */
    Sql appendCondition(Where where);

    /**
     * 获取设置完参数的PreparedStatement
     */
    PreparedStatement getPreparedStatement(Connection con) throws SQLException;
}
