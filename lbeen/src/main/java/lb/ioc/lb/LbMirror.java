package lb.ioc.lb;

import lb.ioc.BeanType;
import lb.ioc.Mirror;

import java.util.ArrayList;
import java.util.List;

/**
 * mirror实现
 *
 * @author 李斌
 */
public class LbMirror<T> implements Mirror<T> {
    /**
     * beanName
     */
    private String beanName;
    /**
     * Class
     */
    private Class<T> clazz;
    /**
     * 是否单列
     */
    private boolean single;
    /**
     * 类变量map，保存变量与iocbean关系
     * <li>k：变量名</li>
     * <li>v：ioc beanname</li>
     */
    private List<String> injectFields = new ArrayList<>();
    /**
     * bean类型
     */
    private BeanType type;
    /**
     * 单例bean
     */
    private T t;

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public Class<T> getClazz() {
        return clazz;
    }

    @Override
    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean isSingle() {
        return single;
    }

    @Override
    public void setSingle(boolean single) {
        this.single = single;
    }

    @Override
    public List<String> getInjectFields() {
        return injectFields;
    }

    @Override
    public void addInjectFields(String field) {
        injectFields.add(field);
    }

    @Override
    public BeanType getType() {
        return type;
    }

    @Override
    public void setType(BeanType type) {
        this.type = type;
    }

    @Override
    public T getSingleBean() {
        return t;
    }

    @Override
    public void setSimgleBean(T t) {
        if (single) {
            this.t = t;
        }
    }
}
