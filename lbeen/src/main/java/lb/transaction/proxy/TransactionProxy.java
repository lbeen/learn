package lb.transaction.proxy;

import lb.transaction.Transaction;
import lb.transaction.TransactionType;
import lb.transaction.Transations;
import lb.transaction.annotation.Tran;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 事务代理
 * @author 李斌
 */
public class TransactionProxy implements MethodInterceptor{
    @Override
    public Object intercept(Object object, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        Tran tranA = method.getAnnotation(Tran.class);
        if (tranA == null){
            return noTranIntercept(object,args,proxy);
        }
        switch (tranA.type()){
            case OPEN:
                return tranIntercept(object,args,proxy);
            case OPENNEW:
                return newTranIntercept(object,args,proxy);
            default:
                return noTranIntercept(object,args,proxy);
        }
    }

    /**
     * 无事务代理
     */
    private Object noTranIntercept(Object object, Object[] args,
                                   MethodProxy proxy) throws Throwable{
        return proxy.invokeSuper( object, args);
    }

    /**
     * 事务代理
     */
    private Object tranIntercept(Object object, Object[] args,
                                  MethodProxy proxy) throws Throwable{
        Transaction tran = null;
        try {
            tran = Transations.getTransaction(TransactionType.OPEN);
            Object result = proxy.invokeSuper( object, args);
            tran.commit();
            return result;
        } catch (Throwable e) {
            tranRollback(tran);
            throw e;
        } finally {
            tranClose(tran);
        }
    }

    /**
     * 强制开启事务代理
     */
    private Object newTranIntercept(Object object, Object[] args,
                                MethodProxy proxy) throws Throwable{
        Transaction tran = null;
        try {
            tran = Transations.getTransaction(TransactionType.OPENNEW);
            Object result = proxy.invokeSuper( object, args);
            tran.commit();
            return result;
        } catch (Throwable e) {
            tranRollback(tran);
            throw e;
        } finally {
            tranClose(tran);
        }
    }

    /**
     * 回滚事务
     */
    private void tranRollback(Transaction tran) throws Throwable {
        if (tran != null){
            tran.rollback();
        }
    }

    /**
     * 关闭事务
     */
    private void tranClose(Transaction tran){
        if (tran != null){
            tran.close();
        }
    }
}
