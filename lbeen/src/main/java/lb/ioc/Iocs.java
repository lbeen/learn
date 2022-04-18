package lb.ioc;

import lb.ioc.lb.LbIoc;

import java.util.List;

/**
 * ioc工具类
 *
 * @author 李斌
 */
public class Iocs {
    /**
     * ioc容器
     */
    private static Ioc IOC;

    /**
     * 初始化ioc容器
     */
    public static void InitIoc(String scanPackage)
            throws Exception {
        IOC = new LbIoc();
        IOC.init(scanPackage);
    }

    /**
     * 从ioc容器获取一个对象
     */
    public static Object getBean(String beanName) throws Exception {
        return IOC.getBean(beanName);
    }

    /**
     * 从ioc容器获取一个对象
     */
    public static <T> T getBean(Class<T> clazz) throws Exception {
        return IOC.getBean(clazz);
    }

    /**
     * 从ioc容器获取所有类解析信息
     */
    public static List<Mirror> getMirrors() {
        return IOC.getMirrors();
    }

}
