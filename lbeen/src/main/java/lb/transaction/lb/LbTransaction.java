package lb.transaction.lb;

import lb.dao.Sql;
import lb.transaction.RsCallback;
import lb.transaction.Transaction;
import lb.transaction.TransactionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 事务实现
 *
 * @author 李斌
 */
public class LbTransaction implements Transaction {
    private Connection connection;
    /**
     * 原子数，当原子数为0时便是没打开事务
     */
    private int atoms = 0;

    private TransactionPool transactionPool;

    @Override
    public void init(Connection connection, TransactionPool transactionPool) {
        this.connection = connection;
        this.transactionPool = transactionPool;
    }

    /**
     * 打开事务
     */
    @Override
    public void beginTransaction() throws SQLException {
        if (atoms == 0) {
            connection.setAutoCommit(false);
            atoms++;
            transactionPool.addToThreadLocal(this);
        }
    }

    /**
     * 增加原子数
     */
    @Override
    public void addAtoms() {
        atoms++;
    }

    /**
     * 提交当前事务
     */
    @Override
    public void commit() throws SQLException {
        if (atoms == 1) {
            connection.commit();
        } else if (atoms > 1) {
            atoms--;
        }
    }

    /**
     * 回滚当前事务
     */
    @Override
    public void rollback() throws SQLException {
        if (atoms > 0) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭当前事务（关闭连接）
     */
    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        transactionPool.removeTransaction();
    }

    /**
     * 弱关闭当前事务（开启事务时不关闭，未开事务时关闭）
     */
    @Override
    public void weakclose() {
        if (atoms == 0) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            transactionPool.removeTransaction();
        }
    }

    /**
     * 执行sql有返回结果集
     *
     * @param sql      sql语句对象
     * @param callBack 结果集操作
     */
    @Override
    public <T> T exec(Sql sql, RsCallback<T> callBack)
            throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = sql.getPreparedStatement(connection);
            ps.execute();
            if (callBack != null) {
                rs = ps.getResultSet();
                return callBack.handle(rs);
            }
            return null;
        } finally {
            this.weakclose();
            close(ps, rs);
        }
    }

    /**
     * 执行sql,无返回结果集，无参数
     */
    @Override
    public void exec(Sql sql) throws Exception {
        exec(sql, null);
    }
}
