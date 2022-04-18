package lb.mvc.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * controller方法信息
 *
 * @author 李斌
 */
public class MethodInfo {
    /**
     * 方法
     */
    private Method method;

    /**
     * 方法返回类型
     */
    private Class<?> methodReturnType;

    /**
     * 方法参数类型
     */
    private List<ParamInfo> paramInfos = new ArrayList<>();

    /**
     * 方法url返回页面或类型
     */
    private String returnType;

    /**
     * 方法权限，null：不需要权限，""：登陆
     */
    private String login;

    public MethodInfo(Method method) {
        this.method = method;
    }

    public void setMethodReturnType(Class<?> methodReturnType) {
        this.methodReturnType = methodReturnType;
    }

    public void addParamInfo(String name, Class<?> type, String urlParamName) {
        paramInfos.add(new ParamInfo(name, type, urlParamName));
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Method getMethod() {
        return method;
    }

    public List<ParamInfo> getParamInfos() {
        return paramInfos;
    }

    public String getReturnType() {
        return returnType;
    }

    public Class<?> getMethodReturnType() {
        return methodReturnType;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    /**
     * 方法参数信息
     */
    public class ParamInfo {
        private String name;

        private Class<?> type;

        private String urlParamName;

        private ParamInfo(String name, Class<?> type, String urlParamName) {
            this.name = name;
            this.type = type;
            this.urlParamName = urlParamName;
        }

        public String getName() {
            return name;
        }

        public Class<?> getType() {
            return type;
        }

        public String getUrlParamName() {
            return urlParamName;
        }

    }
}
