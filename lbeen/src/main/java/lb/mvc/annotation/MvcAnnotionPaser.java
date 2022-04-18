package lb.mvc.annotation;

import lb.ioc.BeanType;
import lb.ioc.Iocs;
import lb.ioc.Mirror;
import lb.mvc.UrlBook;
import lb.mvc.util.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mvc注解解析器
 *
 * @author 李斌
 */
public class MvcAnnotionPaser {
    private UrlBook urlBook;

    public MvcAnnotionPaser(UrlBook urlBook) {
        this.urlBook = urlBook;
    }

    public void paser() {
        List<Mirror> IocMirrors = Iocs.getMirrors();
        for (Mirror mirror : IocMirrors) {
            if (BeanType.CONTROLLER.equals(mirror.getType())) {
                Class<?> clazz = mirror.getClazz();
                At at = clazz.getAnnotation(At.class);
                if (at != null) {
                    urlBook.addControllerInfo(at.value(), clazz);
                    urlBook.addMethodInfo(clazz, paserMethods(clazz));
                }
            }
        }
    }

    /**
     * 解析方法
     */
    private Map<String, MethodInfo> paserMethods(Class<?> clazz) {
        Map<String, MethodInfo> methodInfos = new HashMap<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            At at = method.getAnnotation(At.class);
            if (at != null) {
                MethodInfo methodInfo = new MethodInfo(method);
                methodInfo.setMethodReturnType(method.getReturnType());
                Ok ok = method.getAnnotation(Ok.class);
                methodInfo.setReturnType(ok == null ? "json" : ok.value());
                Parameter[] parameters = method.getParameters();
                paserParameters(parameters, methodInfo);
                Login login = method.getAnnotation(Login.class);
                if (login != null) {
                    methodInfo.setLogin(login.value());
                }
                String methodUrl = "".equals(at.value()) ? method.getName() : at.value();
                methodInfos.put(methodUrl, methodInfo);
            }
        }
        return methodInfos;
    }

    /**
     * 解析方法参数
     */
    private void paserParameters(Parameter[] parameters, MethodInfo methodInfo) {
        for (Parameter parameter : parameters) {
            Param param = parameter.getAnnotation(Param.class);
            if (param != null) {
                methodInfo.addParamInfo(parameter.getName(), parameter.getType(), param.value());
            } else {
                methodInfo.addParamInfo(parameter.getName(), parameter.getType(), null);
            }
        }
    }


}
