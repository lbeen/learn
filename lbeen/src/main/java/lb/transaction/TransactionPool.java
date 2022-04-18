package lb.transaction;

import java.util.Stack;

/**
 * 事务池
 *
 * @author 李斌
 */
public class TransactionPool {
    /**
     * 事务栈容器
     */
    private ThreadLocal<Stack<Transaction>> trans = new ThreadLocal<>();
    /**
     * 数据源
     */
    private TransactionFactory factory;

    TransactionPool(TransactionFactory factory) {
        this.factory = factory;
        this.factory.setTransactionPool(this);
    }

    /**
     * 获得一个事务，不打开事务
     */
    public Transaction getTransaction() throws Exception {
        Stack<Transaction> tranStack = trans.get();
        if (tranStack != null) {
            return tranStack.peek();
        }
        return factory.creat();
    }

    /**
     * 获得一个事务，当事务级别不等于NO时自动开启事务
     *
     * @param type 事务
     */
    synchronized Transaction getTransaction(TransactionType type) {
        try {
            Transaction tran;
            if (type.equals(TransactionType.OPEN)) {
                tran = beginTransaction();
            } else if (type.equals(TransactionType.OPENNEW)) {
                tran = beginNewTransaction();
            } else {
                tran = getTransaction();
            }
            factory.unlockConnection();
            return tran;
        } catch (Throwable e) {
            factory.unlockAndCloseConnection();
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除当前事务，如果只有一个事务就从ThreadLocal移除
     */
    public synchronized void removeTransaction() {
        Stack<Transaction> tranStack = trans.get();
        if (tranStack != null) {
            if (tranStack.size() == 1) {
                trans.remove();
            } else {
                tranStack.pop();
            }
        }
    }

    public synchronized void addToThreadLocal(Transaction tran) {
        Stack<Transaction> tranStack = trans.get();
        if (tranStack == null) {
            tranStack = new Stack<>();
            trans.set(tranStack);
        }
        tranStack.push(tran);
    }

    /**
     * 开启一个事务，当前线程有事务就返回当前线程最新的事务否则新建一个事务栈
     */
    private Transaction beginTransaction() throws Exception {
        Stack<Transaction> tranStack = trans.get();
        if (tranStack == null) {
            return createTransactionAndBegin();
        }
        return tranStack.peek();
    }

    /**
     * 开启一个事务，无论当前线程有无事务都会新建一个事务放回
     */
    private Transaction beginNewTransaction() throws Exception {
        Stack<Transaction> tranStack = trans.get();
        if (tranStack == null) {
            return createTransactionAndBegin();
        }
        Transaction tran = factory.creat();
        tran.beginTransaction();
        tranStack.push(tran);
        return tran;
    }

    /**
     * 创建一个事务栈
     */
    private Transaction createTransactionAndBegin() throws Exception {
        Transaction tran = factory.creat();
        tran.beginTransaction();
        return tran;
    }

}
