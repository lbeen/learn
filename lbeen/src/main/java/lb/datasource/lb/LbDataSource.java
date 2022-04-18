package lb.datasource.lb;

import lb.datasource.Connection;
import lb.datasource.DataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源
 *
 * @author 李斌
 */
public class LbDataSource implements DataSource {
    /**
     * 数据库连接池
     * <br>k 数据库链接
     * <br>v 当前线程，没用的链接为"0"
     */
    private Map<java.sql.Connection, Object> connectionPool = new ConcurrentHashMap<>();
    /**
     * 连接池管理器
     */
    private ConnectionPoolManager manager;

    /**
     * 初始化数据源
     *
     * @param dbConfigPath 数据库配置文件
     */
    @Override
    public void init(String dbConfigPath) {
        manager = new ConnectionPoolManager();
        manager.initManager(dbConfigPath, connectionPool);
    }

    /**
     * 从连接器获取一个connection
     *
     * @return 数据库链接
     */
    @Override
    public synchronized java.sql.Connection getConnection() {
        return new Connection(manager.getConnectionFromPool(), this);
    }

    @Override
    public void closeConnection(java.sql.Connection connection) {
        manager.releaseConection(connection);
    }
}
