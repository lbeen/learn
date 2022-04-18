package lb.transaction;

import lb.datasource.DataSources;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务工厂
 *
 * @author 李斌
 */
class TransactionFactory {
    /**
     * 连接ThreadLocal，事务创建过程中缓存连接，便于异常时关闭
     */
    private ThreadLocal<Connection> lockcon = new ThreadLocal<>();

    private Class<? extends Transaction> tranClass;

    private TransactionPool transactionPool;

    /**
     * 初始化事务工厂
     */
    TransactionFactory(Class<? extends Transaction> tranClass) {
        this.tranClass = tranClass;
    }

    void setTransactionPool(TransactionPool transactionPool) {
        this.transactionPool = transactionPool;
    }

    /**
     * 获取一个事务
     */
    Transaction creat() throws Exception {
        Connection con = DataSources.getConnection();
        lockcon.set(con);
        Transaction tran = tranClass.newInstance();
        tran.init(con, transactionPool);
        return tran;
    }

    /**
     * 解锁缓存的链接
     */
    void unlockConnection() {
        lockcon.remove();
    }

    /**
     * 解锁并关闭缓存的链接
     */
    void unlockAndCloseConnection() {
        try {
            Connection con = lockcon.get();
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            lockcon.remove();
        }
    }
}
