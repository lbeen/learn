package lb.transaction;

import lb.transaction.lb.LbTransaction;

/**
 * 事务工具类
 *
 * @author 李斌
 */
public abstract class Transations {
    /**
     * 事务池
     */
    private static TransactionPool TRANSACTIONPOOL;

    /**
     * 初始化事务池
     */
    public static void initTransactionpool() {
        TransactionFactory factory = new TransactionFactory(LbTransaction.class);
        TRANSACTIONPOOL = new TransactionPool(factory);
    }

    /**
     * 获取一个事务
     */
    public static Transaction getTransaction() throws Exception {
        return TRANSACTIONPOOL.getTransaction();
    }

    /**
     * 获取一个不同类型的事务
     */
    public static Transaction getTransaction(TransactionType type) throws Exception {
        return TRANSACTIONPOOL.getTransaction(type);
    }
}
