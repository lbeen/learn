package lb.transaction.proxy;

import net.sf.cglib.proxy.Enhancer;

/**
 * 事务代理工厂
 * @author 李斌
 */
public class TransactionProxyFactory {
    private static Enhancer ENHANCER;

    public static void init(){
        ENHANCER = new Enhancer();
        ENHANCER.setCallback(new TransactionProxy());
    }

    /**
     * 创建对象
     */
    public static <T> T create(Class<T> subClass){
        ENHANCER.setSuperclass(subClass);
        return (T) ENHANCER.create();
    }
}
