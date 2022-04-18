package lb.datasource;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * java.sql.Connection装饰者类 <br>
 * 重写close方法：关闭返回连接池，不关闭数据库链接
 *
 * @author 李斌
 */
public class Connection implements java.sql.Connection {
    /**
     * 内部维护的java.sql.Connection对象
     */
    private java.sql.Connection sqlConnection;
    /**
     * 指向创建此对象的数据源管理器
     * <br>close时调用管理器的方法放回连接池
     */
    private DataSource dataSource;

    /**
     * 构造方法包内权限，不对包外开放，只由manager创建
     */
    public Connection(java.sql.Connection sqlConnection, DataSource dataSource) {
        this.sqlConnection = sqlConnection;
        this.dataSource = dataSource;
    }

    /**
     * 重写close方法，关闭返回连接池，不关闭数据库链接
     */
    public void close() throws SQLException {
        dataSource.closeConnection(sqlConnection);
    }

    public void setReadOnly(boolean arg0) throws SQLException {
        sqlConnection.setReadOnly(arg0);
    }

    public boolean isReadOnly() throws SQLException {
        return sqlConnection.isReadOnly();
    }

    public void abort(Executor arg0) throws SQLException {
        sqlConnection.abort(arg0);
    }

    public boolean isClosed() throws SQLException {
        return sqlConnection.isClosed();
    }

    public boolean isValid(int arg0) throws SQLException {
        return sqlConnection.isValid(arg0);
    }

    public void clearWarnings() throws SQLException {
        sqlConnection.clearWarnings();
    }

    public void commit() throws SQLException {
        sqlConnection.commit();
    }

    public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
        return sqlConnection.createArrayOf(arg0, arg1);
    }

    public Blob createBlob() throws SQLException {
        return sqlConnection.createBlob();
    }

    public Clob createClob() throws SQLException {
        return sqlConnection.createClob();
    }

    public NClob createNClob() throws SQLException {
        return sqlConnection.createNClob();
    }

    public SQLXML createSQLXML() throws SQLException {
        return sqlConnection.createSQLXML();
    }

    public Statement createStatement(int arg0, int arg1, int arg2)
            throws SQLException {
        return sqlConnection.createStatement(arg0, arg1, arg2);
    }

    public Statement createStatement(int arg0, int arg1) throws SQLException {
        return sqlConnection.createStatement(arg0, arg1);
    }

    public Statement createStatement() throws SQLException {
        return sqlConnection.createStatement();
    }

    public Struct createStruct(String arg0, Object[] arg1) throws SQLException {
        return sqlConnection.createStruct(arg0, arg1);
    }

    public boolean getAutoCommit() throws SQLException {
        return sqlConnection.getAutoCommit();
    }

    public String getCatalog() throws SQLException {
        return sqlConnection.getCatalog();
    }

    public String getClientInfo(String arg0) throws SQLException {
        return sqlConnection.getClientInfo(arg0);
    }

    public Properties getClientInfo() throws SQLException {
        return sqlConnection.getClientInfo();
    }

    public int getHoldability() throws SQLException {
        return sqlConnection.getHoldability();
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        return sqlConnection.getMetaData();
    }

    public int getNetworkTimeout() throws SQLException {
        return sqlConnection.getNetworkTimeout();
    }

    public String getSchema() throws SQLException {
        return sqlConnection.getSchema();
    }

    public int getTransactionIsolation() throws SQLException {
        return sqlConnection.getTransactionIsolation();
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return sqlConnection.getTypeMap();
    }

    public SQLWarning getWarnings() throws SQLException {
        return sqlConnection.getWarnings();
    }

    public String nativeSQL(String arg0) throws SQLException {
        return sqlConnection.nativeSQL(arg0);
    }

    public CallableStatement prepareCall(String arg0, int arg1, int arg2,
                                         int arg3) throws SQLException {
        return sqlConnection.prepareCall(arg0, arg1, arg2, arg3);
    }

    public CallableStatement prepareCall(String arg0, int arg1, int arg2)
            throws SQLException {
        return sqlConnection.prepareCall(arg0, arg1, arg2);
    }

    public CallableStatement prepareCall(String arg0) throws SQLException {
        return sqlConnection.prepareCall(arg0);
    }

    public PreparedStatement prepareStatement(String arg0, String[] arg1)
            throws SQLException {
        return sqlConnection.prepareStatement(arg0, arg1);
    }

    public PreparedStatement prepareStatement(String arg0, int[] arg1)
            throws SQLException {
        return sqlConnection.prepareStatement(arg0, arg1);
    }

    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
            throws SQLException {
        return sqlConnection.prepareStatement(arg0, arg1, arg2);
    }

    public PreparedStatement prepareStatement(String arg0) throws SQLException {
        return sqlConnection.prepareStatement(arg0);
    }

    public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
                                              int arg3) throws SQLException {
        return sqlConnection.prepareStatement(arg0, arg1, arg2, arg3);
    }

    public PreparedStatement prepareStatement(String arg0, int arg1)
            throws SQLException {
        return sqlConnection.prepareStatement(arg0, arg1);
    }

    public void releaseSavepoint(Savepoint arg0) throws SQLException {
        sqlConnection.releaseSavepoint(arg0);
    }

    public void rollback() throws SQLException {
        sqlConnection.rollback();
    }

    public void rollback(Savepoint arg0) throws SQLException {
        sqlConnection.rollback(arg0);
    }

    public void setAutoCommit(boolean arg0) throws SQLException {
        sqlConnection.setAutoCommit(arg0);
    }

    public void setCatalog(String arg0) throws SQLException {
        sqlConnection.setCatalog(arg0);
    }

    public void setClientInfo(Properties arg0) throws SQLClientInfoException {
        sqlConnection.setClientInfo(arg0);
    }

    public void setClientInfo(String arg0, String arg1)
            throws SQLClientInfoException {
        sqlConnection.setClientInfo(arg0, arg1);
    }

    public void setHoldability(int arg0) throws SQLException {
        sqlConnection.setHoldability(arg0);
    }

    public void setNetworkTimeout(Executor arg0, int arg1) throws SQLException {
        sqlConnection.setNetworkTimeout(arg0, arg1);
    }

    public Savepoint setSavepoint() throws SQLException {
        return sqlConnection.setSavepoint();
    }

    public Savepoint setSavepoint(String arg0) throws SQLException {
        return sqlConnection.setSavepoint(arg0);
    }

    public void setSchema(String arg0) throws SQLException {
        sqlConnection.setSchema(arg0);
    }

    public void setTransactionIsolation(int arg0) throws SQLException {
        sqlConnection.setTransactionIsolation(arg0);
    }

    public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException {
        sqlConnection.setTypeMap(arg0);
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return sqlConnection.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return sqlConnection.isWrapperFor(iface);
    }
}
