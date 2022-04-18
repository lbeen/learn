package lb.ioc;

import java.util.List;

/**
 * @author 李斌
 */
public interface Mirror<T> {
    String getBeanName();

    void setBeanName(String beanName);

    Class<T> getClazz();

    void setClazz(Class<T> clazz);

    boolean isSingle();

    void setSingle(boolean isSingle);

    List<String> getInjectFields();

    void addInjectFields(String field);

    void setType(BeanType type);

    BeanType getType();

    T getSingleBean();

    void setSimgleBean(T t);
}
