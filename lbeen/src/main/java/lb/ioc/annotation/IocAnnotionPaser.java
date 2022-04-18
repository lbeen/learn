package lb.ioc.annotation;

import lb.ioc.BeanType;
import lb.ioc.Ioc;
import lb.ioc.Mirror;
import lb.ioc.annotation.bean.Controller;
import lb.ioc.annotation.bean.IocBean;
import lb.ioc.annotation.bean.Service;
import lb.ioc.annotation.field.Inject;
import lb.util.ClassUtil;
import lb.util.Strings;

import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * iocBean注解解析器
 *
 * @author 李斌
 */
public class IocAnnotionPaser implements Consumer<Class<?>> {
    private Ioc ioc;

    public IocAnnotionPaser(Ioc ioc) {
        this.ioc = ioc;
    }

    /**
     * 解析类型
     *
     * @param clazz 类型
     */
    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInterface()) {
            Controller controller = clazz.getAnnotation(Controller.class);
            if (controller != null) {
                paserBean(clazz, controller.value(), controller.single(), BeanType.CONTROLLER);
                return;
            }
            Service service = clazz.getAnnotation(Service.class);
            if (service != null) {
                paserBean(clazz, service.value(), service.single(), BeanType.SERVICE);
                return;
            }
            IocBean iocBean = clazz.getAnnotation(IocBean.class);
            if (iocBean != null) {
                paserBean(clazz, iocBean.value(), iocBean.single(), BeanType.DEFAULT);
            }
        }
    }

    /**
     * 解析bean注解
     *
     * @param clazz Class
     */
    private void paserBean(Class<?> clazz, String beanName, boolean single, BeanType type) {
        if (Strings.isBlank(beanName)) {
            beanName = ClassUtil.getLowcaseFirstClassName(clazz);
        }
        Mirror mirror = ioc.creatMirror();
        mirror.setBeanName(beanName);
        mirror.setClazz(clazz);
        mirror.setSingle(single);
        mirror.setType(type);
        paserField(mirror);
        ioc.addMirror(mirror);
    }


    /**
     * 解析自动注入
     *
     * @param mirror 类信息
     */
    private void paserField(Mirror mirror) {
        Class<?> clazz = mirror.getClazz();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Inject inject = field.getAnnotation(Inject.class);
            if (inject != null) {
                String fieldName = field.getName();
                mirror.addInjectFields(fieldName);
            }
        }
    }
}
