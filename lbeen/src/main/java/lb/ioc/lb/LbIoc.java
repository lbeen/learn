package lb.ioc.lb;

import lb.ioc.Ioc;
import lb.ioc.Mirror;
import lb.ioc.annotation.IocAnnotionPaser;
import lb.transaction.proxy.TransactionProxyFactory;
import lb.util.ClassUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ioc容器
 *
 * @author 李斌
 */
public class LbIoc implements Ioc {
    /**
     * beanname->CLass
     */
    private Map<String, Class<?>> nameClassMap = new HashMap<>();
    /**
     * Class->Mirror
     */
    private ClassMirrorMap classMirrorMap = new ClassMirrorMap();

    /**
     * 初始化IOC
     *
     * @param scanPackage 扫描的包目录
     */
    @Override
    public void init(String scanPackage) {
        TransactionProxyFactory.init();
        ClassUtil.scanClass(scanPackage, new IocAnnotionPaser(this));
    }

    @Override
    public <T> T getBean(Class<T> clazz) throws Exception {
        Mirror<T> mirror = classMirrorMap.get(clazz);
        if (mirror == null) {
            throw new Exception("没有找到" + clazz.getName() + "的镜像信息!");
        }
        T t = mirror.getSingleBean();
        if (t == null) {
            //创建对象
            switch (mirror.getType()) {
                case SERVICE:
                    t = TransactionProxyFactory.create(clazz);
                    break;
                default:
                    t = clazz.newInstance();
                    break;

            }

            //自动注入
            for (String fieldName : mirror.getInjectFields()) {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(t, getBean(field.getType()));
            }
            mirror.setSimgleBean(t);
        }
        return t;
    }

    /**
     * 从ioc容器获取一个对象
     */
    @Override
    public Object getBean(String beanName) throws Exception {
        Class<?> clazz = nameClassMap.get(beanName);
        if (clazz == null) {
            throw new Exception("没有找到" + beanName + "的class信息!");
        }
        return getBean(clazz);
    }

    @Override
    public <T> void addMirror(Mirror<T> mirror) {
        nameClassMap.put(mirror.getBeanName(), mirror.getClazz());
        classMirrorMap.add(mirror);
    }

    @Override
    public List<Mirror> getMirrors() {
        return classMirrorMap.getMirrors();
    }

    @Override
    public Mirror creatMirror() {
        return new LbMirror();
    }


    private class ClassMirrorMap extends HashMap<Class, Mirror> {
        <T> void add(Mirror<T> mirror) {
            super.put(mirror.getClazz(), mirror);
        }

        <T> Mirror<T> get(Class<T> clazz) {
            return super.get(clazz);
        }

        List<Mirror> getMirrors() {
            List<Mirror> mirrors = new ArrayList<>();
            mirrors.addAll(super.values());
            return mirrors;
        }
    }
}
