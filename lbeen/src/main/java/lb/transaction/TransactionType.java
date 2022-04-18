package lb.transaction;

/**
 * @author 李斌
 */
public enum TransactionType {
    /**
     * 没有事务打开事务，有事务使用当前事务
     */
    OPEN,
    /**
     * 无论何时都打开一个新的事务
     */
    OPENNEW,
    /**
     * 不使用事务
     */
    NO
}
