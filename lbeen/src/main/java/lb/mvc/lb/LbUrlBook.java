package lb.mvc.lb;

import lb.mvc.UrlBook;
import lb.mvc.annotation.MvcAnnotionPaser;
import lb.mvc.util.MethodExecuteInfo;
import lb.mvc.util.MethodInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 访问路径与类，方法映射信息
 *
 * @author 李斌
 */
public class LbUrlBook implements UrlBook {
    /**
     * controller类与url映射信息
     * <br>url->iocBeanname
     */
    private Map<String, Class<?>> controllerMap = new HashMap<>();
    /**
     * controllerl类方法与url映射信息
     * <br>class->(url->方法)
     */
    private Map<Class<?>, Map<String, MethodInfo>> methodMap = new HashMap<>();

    @Override
    public void init() {
        MvcAnnotionPaser paser = new MvcAnnotionPaser(this);
        paser.paser();
    }

    public void addControllerInfo(String controllerUrl, Class<?> clazz) {
        controllerMap.put(controllerUrl, clazz);
    }

    public void addMethodInfo(Class<?> clazz, Map<String, MethodInfo> methodInfos) {
        methodMap.put(clazz, methodInfos);
    }

    /**
     * 解析url获取执行信息
     */
    @Override
    public MethodExecuteInfo paserUrl(String url) {
        String controllerUrl;
        for (Map.Entry<String, Class<?>> entry : controllerMap.entrySet()) {
            controllerUrl = entry.getKey();
            int index = url.indexOf(controllerUrl);
            if (index == 0) {
                url = url.substring(controllerUrl.length());
                Class<?> clazz = entry.getValue();
                return paserMethod(url, clazz, methodMap.get(clazz));
            }

        }
        return null;
    }

    private MethodExecuteInfo paserMethod(String url, Class<?> clazz, Map<String, MethodInfo> thisMethodMap) {
        String[] arr = url.split("\\?");
        String methodUrl = arr[0];
        for (Map.Entry<String, MethodInfo> entry : thisMethodMap.entrySet()) {
            if (methodUrl.equals(entry.getKey())) {
                MethodExecuteInfo methodExecuteInfo = new MethodExecuteInfo();
                methodExecuteInfo.setClazz(clazz);
                MethodInfo methodInfo = entry.getValue();
                methodExecuteInfo.setMethod(methodInfo.getMethod());
                methodExecuteInfo.setParamInfos(methodInfo.getParamInfos());
                methodExecuteInfo.setReturnType(methodInfo.getReturnType());
                methodExecuteInfo.setLogin(methodInfo.getLogin());
                return methodExecuteInfo;
            }
        }
        return null;
    }
}
