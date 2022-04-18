package lb.mvc;

import lb.mvc.util.MethodExecuteInfo;
import lb.mvc.util.MethodInfo;

import java.util.Map;

/**
 * url与controller方法映射信息
 *
 * @author 李斌
 */
public interface UrlBook {
    void init();

    /**
     * 解析url得到方法执行信息
     */
    MethodExecuteInfo paserUrl(String url);

    /**
     * 加入controller映射信息
     */
    void addControllerInfo(String controllerUrl, Class<?> clazz);

    /**
     * 加入controller方法映射信息
     */
    void addMethodInfo(Class<?> clazz, Map<String, MethodInfo> methodInfos);
}
