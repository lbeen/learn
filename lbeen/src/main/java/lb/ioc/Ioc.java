package lb.ioc;

import java.util.List;

/**
 * ioc容器
 *
 * @author 李斌
 */
public interface Ioc {
    /**
     * 初始化IOC
     *
     * @param scanPackage 扫描的包目录
     */
    void init(String scanPackage);

    /**
     * 以beanname从ioc容器获取一个对象
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 以Class从ioc容器获取一个对象
     */
    <T> T getBean(Class<T> clazz) throws Exception;

    <T> void addMirror(Mirror<T> mirror);

    List<Mirror> getMirrors();

    Mirror creatMirror();
}
