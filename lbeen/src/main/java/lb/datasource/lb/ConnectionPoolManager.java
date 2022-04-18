package lb.datasource.lb;

import lb.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 数据源管理器
 *
 * @author 李斌
 */
class ConnectionPoolManager extends TimerTask {
    private static Logger logger = Logger.getLogger(ConnectionPoolManager.class);
    /**
     * 连接池，指向数据源连接池
     */
    private Map<Connection, Object> connectionPool;

    /**
     * 初始化数据源管理器
     *
     * @param dbConfigPath   配置文件路径
     * @param connectionPool 数据源连接池
     */
    void initManager(String dbConfigPath, Map<Connection, Object> connectionPool) {
        this.connectionPool = connectionPool;
        initConnectionPool(dbConfigPath);
        //定时管理数据库连接池
        Timer timer = new Timer();
        timer.schedule(this, new Date(), 1000 * 10);
    }

    /**
     * 向连接池初始化连接
     *
     * @param dbConfigPath 数据库配置文件
     */
    private void initConnectionPool(String dbConfigPath) {
        DataSourceProperties.readerProperties(dbConfigPath);
        try {
            Class.forName(DataSourceProperties.DRIVERCLASS_NAME);
        } catch (ClassNotFoundException e) {
            logger.error("数据库驱动加载失败", e);
            throw new RuntimeException("数据库驱动加载失败");
        }
        //初始化连接池
        for (int i = 0; i < DataSourceProperties.INIT_SIZT; i++) {
            connectionPool.put(getConnectionFromDB(), "0");
        }
    }

    /**
     * 与数据库建立一个新连接
     *
     * @return 数据库连接
     */
    private Connection getConnectionFromDB() {
        try {
            return DriverManager.getConnection(DataSourceProperties.JDBC_URL,
                    DataSourceProperties.USERNAME, DataSourceProperties.PASSWORD);
        } catch (SQLException e) {
            logger.error("数据库链接获取失败", e);
            throw new RuntimeException("数据库链接获取失败");
        }
    }

    /**
     * 关闭一个数据库连接
     *
     * @param connection 数据库连接
     */
    private void closeConnectioToDB(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("关闭数据库链接失败", e);
                throw new RuntimeException("关闭数据库链接失败");
            }
        }
    }

    /**
     * 监测连接池，连接不够向数据库获取连接，关闭多余闲置连接
     */
    @Override
    public void run() {
        List<Connection> freeConnections = getFreeConnections();
        int freeSize = freeConnections.size();
        if (freeSize == 0) {
            increaseConnection();
        } else if (freeSize > DataSourceProperties.MAX_FREE_SIZE) {
            decreaseConnection(freeConnections);
        }
    }

    /**
     * 获取所有空闲链接
     *
     * @return 空闲链接list
     */
    private List<Connection> getFreeConnections() {
        List<Connection> freeConnections = new ArrayList<>();
        for (Entry<Connection, Object> entry : connectionPool.entrySet()) {
            if ("0".equals(entry.getValue())) {
                freeConnections.add(entry.getKey());
            }
        }
        return freeConnections;
    }

    /**
     * 增加数据库连接
     */
    private void increaseConnection() {
        int allCount = connectionPool.size();
        for (int i = 0; i < DataSourceProperties.RISE_SIZE &&
                allCount <= DataSourceProperties.MAX_SIZE; i++, allCount++) {
            connectionPool.put(getConnectionFromDB(), "0");
        }
    }

    /**
     * 关闭多余连接
     *
     * @param freeConnections 空闲连接list
     */
    private void decreaseConnection(List<Connection> freeConnections) {
        for (int i = 0; freeConnections.size() > DataSourceProperties.INIT_SIZT; ) {
            Connection con = freeConnections.get(i);
            closeConnectioToDB(con);
            connectionPool.remove(freeConnections.get(i));
            freeConnections.remove(i);
        }
    }

    /**
     * 从链接池获取链接
     *
     * @return 数据库连接
     */
    Connection getConnectionFromPool() {
        Connection con;
        List<Connection> freeConnections = getFreeConnections();

        if (freeConnections.size() > 0) {
            //链接池有空闲链接直接从链接池获取
            con = freeConnections.get(0);
            connectionPool.put(con, Thread.currentThread());
            return con;
        } else {
            ///链接池没有空闲链接，从数据库获取新连接并返回一个链接
            for (int i = 0; i < 3; i++) {
                run();
                freeConnections = getFreeConnections();
                if (freeConnections.size() > 0) {
                    con = freeConnections.get(0);
                    connectionPool.put(con, Thread.currentThread());
                    return con;
                } else {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            logger.error("链接池链接已用完，请增大连接池最大连接数");
            throw new RuntimeException("链接池链接已用完，请增大连接池最大连接数");
        }
    }

    /**
     * 将链接还给链接池,直接用Connection.close()方法
     *
     * @param con 数据库链接
     */
    void releaseConection(Connection con) {
        connectionPool.put(con, "0");
    }
}
