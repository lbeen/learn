package lb.mvc.util;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 方法执行信息
 *
 * @author 李斌
 */
public class MethodExecuteInfo<T> {
    private Class<T> clazz;

    private Method method;

    private Class<?> methodReturnType;

    private Map<String, String[]> paramMap;

    private List<MethodInfo.ParamInfo> paramInfos;

    private String returnType;
    /**
     * 方法权限，null：不需要权限，""：登陆
     */
    private String login;

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?> getMethodReturnType() {
        return methodReturnType;
    }

    public void setMethodReturnType(Class<?> methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

    public Map<String, String[]> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String[]> paramMap) {
        this.paramMap = paramMap;
    }

    public List<MethodInfo.ParamInfo> getParamInfos() {
        return paramInfos;
    }

    public void setParamInfos(List<MethodInfo.ParamInfo> paramInfos) {
        this.paramInfos = paramInfos;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
}
