package lb.datasource;

import lb.datasource.lb.LbDataSource;

import java.sql.Connection;

/**
 * 数据源对外工具类
 *
 * @author 李斌
 */
public class DataSources {
    /**
     * 数据源
     */
    private static DataSource DATASOURCE;

    /**
     * 初始化数据源
     */
    public static void initDataSource(String dbConfigPath)
            throws Exception {
        DATASOURCE = new LbDataSource();
        DATASOURCE.init(dbConfigPath);
    }

    /**
     * 从数据源获取一个连接
     */
    public static Connection getConnection() {
        return DATASOURCE.getConnection();
    }
}
