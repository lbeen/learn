package lb.dao.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Date;

/**
 * 实体字段值执行器（通过set，get方法）
 *
 * @author 李斌
 */
public class RsMenthodExcuter<T> {
    /**
     * 数据库字段名
     */
    private String column;
    /**
     * 方法（）set或get
     */
    private Method method;
    /**
     * 字段类型
     */
    private ParamType paramType;

    private RsMenthodExcuter() {
    }

    /**
     * 获取get方法执行器
     *
     * @param column    数据库字段名
     * @param clazz     类
     * @param get       get方法名
     * @param paramType 字段类型
     */
    public static <T> RsMenthodExcuter<T> get(String column, Class<T> clazz, String get, Class paramType)
            throws NoSuchMethodException {
        RsMenthodExcuter<T> excuter = new RsMenthodExcuter<>();
        excuter.column = column;
        excuter.method = clazz.getMethod(get);
        excuter.paramType = ParamType.getType(paramType);
        return excuter;
    }

    /**
     * 获取set方法执行器
     *
     * @param column    数据库字段名
     * @param clazz     类
     * @param set       get方法名
     * @param paramType 字段类型
     */
    public static <T> RsMenthodExcuter<T> set(String column, Class<T> clazz, String set, Class paramType)
            throws NoSuchMethodException {
        RsMenthodExcuter<T> excuter = new RsMenthodExcuter<>();
        excuter.column = column;
        excuter.method = clazz.getMethod(set, paramType);
        excuter.paramType = ParamType.getType(paramType);
        return excuter;
    }

    /**
     * 给实体设置
     *
     * @param t  实体
     * @param rs 查询结果集
     */
    void setVar(T t, ResultSet rs) throws Exception {
        Object var;
        switch (paramType) {
            case DATE:
                var = rs.getTimestamp(column);
                break;
            default:
                var = rs.getObject(column);
        }
        if (var != null) {
            method.invoke(t, var);
        }
    }

    /**
     * 字段类型
     */
    private enum ParamType {
        DATE, OTHER;

        static ParamType getType(Class type) {
            if (Date.class.equals(type)) {
                return DATE;
            } else {
                return OTHER;
            }
        }
    }
}
