package lb.datasource.lb;

import lb.Logger;
import lb.util.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取数据库配置文件类
 *
 * @author 李斌
 */
abstract class DataSourceProperties {
    private static Logger logger = Logger.getLogger(DataSourceProperties.class);
    /**
     * 数据库驱动类名
     */
    static String DRIVERCLASS_NAME;
    /**
     * 数据库连接地址
     */
    static String JDBC_URL;
    /**
     * 数据库用户名
     */
    static String USERNAME;
    /**
     * 数据库密码
     */
    static String PASSWORD;
    /**
     * 初始化连接数
     */
    static int INIT_SIZT = 2;
    /**
     * 最大连接数
     */
    static int MAX_SIZE = 20;
    /**
     * 最大空闲连接数
     */
    static int MAX_FREE_SIZE = 2;
    /**
     * 每次增长连接数
     */
    static int RISE_SIZE = 2;

    /**
     * 读取配置
     *
     * @param dbConfigPath 配置文件路径
     */
    static void readerProperties(String dbConfigPath) {
        Properties pro;
        InputStream in = null;
        try {
            in = DataSourceProperties.class.getResourceAsStream(dbConfigPath);
            pro = new Properties();
            pro.load(in);
        } catch (IOException e) {
            logger.error("配置文件读取失败", e);
            throw new RuntimeException(dbConfigPath + "配置文件读取失败");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String driverClassName = pro.getProperty("driverClassName");
        if (!Strings.isBlank(driverClassName)) {
            DRIVERCLASS_NAME = driverClassName;
        } else {
            logger.error("配置文件driverClassName未配置");
            throw new RuntimeException(dbConfigPath + "配置文件driverClassName未配置");
        }

        String jdbcUrl = pro.getProperty("jdbcUrl");
        if (!Strings.isBlank(jdbcUrl)) {
            JDBC_URL = jdbcUrl;
        } else {
            logger.error("配置文件jdbcUrl未配置");
            throw new RuntimeException(dbConfigPath + "配置文件jdbcUrl未配置");
        }

        String username = pro.getProperty("username");
        if (!Strings.isBlank(username)) {
            USERNAME = username;
        } else {
            logger.error("配置文件username未配置");
            throw new RuntimeException(dbConfigPath + "配置文件username未配置");
        }

        String password = pro.getProperty("password");
        if (!Strings.isBlank(password)) {
            PASSWORD = password;
        } else {
            logger.error("配置文件password未配置");
            throw new RuntimeException(dbConfigPath + "配置文件password未配置");
        }

        String initSize = pro.getProperty("initSize");
        if (Strings.isBlank(initSize)) {
            INIT_SIZT = Integer.parseInt(initSize);
        }

        String riseSize = pro.getProperty("riseSize");
        if (Strings.isBlank(riseSize)) {
            RISE_SIZE = Integer.parseInt(riseSize);
        }

        String maxFreeSize = pro.getProperty("maxFreeSize");
        if (Strings.isBlank(maxFreeSize)) {
            MAX_FREE_SIZE = Integer.parseInt(maxFreeSize);
        }

        String maxSize = pro.getProperty("maxSize");
        if (Strings.isBlank(maxSize)) {
            int maxSizeInt = Integer.parseInt(maxSize);
            if (maxSizeInt < INIT_SIZT) {
                logger.error("axSize必须大于等于initSize");
                throw new RuntimeException("maxSize必须大于等于initSize");
            }
            MAX_SIZE = maxSizeInt;
        }
    }
}
