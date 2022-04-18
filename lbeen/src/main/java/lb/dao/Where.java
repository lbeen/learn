package lb.dao;

import java.util.Map;

/**
 * sql条件接口
 *
 * @author 李斌
 */
public interface Where {
    /**
     * 获取条件sql
     */
    String sql();

    /**
     * 获取条件参数
     */
    Map<String, Object> params();

    /**
     * 添加条件表达式
     */
    Where andExpression(String expression, String column, Object var);

    /**
     * 添加条件表达式
     */
    Where andExpression(String expression, Map<String, Object> params);

    /**
     * 添加条件表达式
     */
    Where andExpression(String expression);

    /**
     * 添加条件
     */
    Where and(String colum, String op, Object val);

    /**
     * 添加等于条件
     */
    Where andEqual(String colum, Object val);

    /**
     * 添加大于条件
     */
    Where andGt(String colum, Object val);

    /**
     * 添加小于条件
     */
    Where andLt(String colum, Object val);

    /**
     * 添加不为空条件
     */
    Where andIsNull(String colum);

    /**
     * 添加排序条件
     */
    Where asc(String colum);

    /**
     * 添加倒序条件
     */
    Where desc(String colum);

    /**
     * 添加分组条件
     */
    Where group(String colum);
}
