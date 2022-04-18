package lb.dao;

import lb.dao.lb.LbDao;
import lb.dao.lb.LbSql;
import lb.dao.lb.LbWhere;

/**
 * OR包对外接口
 *
 * @author 李斌
 */
public abstract class ORs {
    /**
     * 实体数据库映射信息
     */
    private static ORBook ORBOOK;

    /**
     * 初始化
     */
    public static void init(String scanPackage) {
        ORBOOK = new ORBook();
        ORBOOK.init(scanPackage);
    }

    /**
     * 根据类获取数据库表映射信息
     */
    public static <T> ORInfo<T> getOrInfo(Class<T> clazz) {
        return ORBOOK.getOrInfo(clazz);
    }

    /**
     * 根据类创建dao对象
     */
    public static Dao dao() {
        return new LbDao();
    }

    /**
     * 跟据sql创建一个sql对象
     */
    public static Sql sql(String sqlStr) {
        return new LbSql(sqlStr);
    }

    /**
     * 新建一个sql条件对象
     */
    public static Where where() {
        return new LbWhere();
    }
}
