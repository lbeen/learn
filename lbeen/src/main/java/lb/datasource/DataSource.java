package lb.datasource;

import java.sql.Connection;

/**
 * 数据源接口
 *
 * @author 李斌
 */
public interface DataSource {
    /**
     * 初始化数据源
     *
     * @param dbConfigPath 数据库配置文件
     */
    void init(String dbConfigPath);

    /**
     * 从连接器获取一个connection
     *
     * @return 数据库链接
     */
    Connection getConnection();

    /**
     * 将链接还给连接池
     */
    void closeConnection(Connection con);
}
