package lb.transaction;

import lb.dao.Sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 事务接口
 * @author 李斌
 */
public interface Transaction {
    void init(Connection connection, TransactionPool transactionPool);
    /**
     * 打开事务
     */
    void beginTransaction() throws SQLException;
    /**
     * 增加原子数
     */
    void addAtoms();

    /**
     * 提交当前事务
     */
    void commit() throws SQLException;

    /**
     * 回滚当前事务
     */
    void rollback() throws SQLException;

    /**
     * 关闭当前事务（关闭连接）
     */
    void close();
    /**
     * 弱关闭当前事务（开启事务时不关闭，未开事务时关闭）
     */
    void weakclose();

    /**
     * 执行sql,有参数，有返回结果集
     * @param sql sql语句对象
     * @param callBack 结果集操作
     */
    <T> T exec(Sql sql, RsCallback<T> callBack)
            throws Exception;

    /**
     * 执行sql,无返回结果集，无参数
     */
    void exec(Sql sql)throws Exception;

    default void close(PreparedStatement ps, ResultSet rs){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (ps != null){
            try {
                ps.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
