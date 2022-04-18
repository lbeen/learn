package lb.mvc;

import lb.ioc.Iocs;
import lb.mvc.util.MethodExecuteInfo;
import lb.mvc.util.MethodInfo;
import lb.util.Strings;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方法执行器
 *
 * @author 李斌
 */
class MethodExecuter {
    /**
     * 执行方法
     *
     * @param methodExecuteInfo 方法执行信息
     * @param request           请求
     * @return 执行结果
     * @throws Exception 方法执行异常
     */
    Object execute(MethodExecuteInfo methodExecuteInfo, HttpServletRequest request) throws Exception {
        Class<?> clazz = methodExecuteInfo.getClazz();
        Object object = Iocs.getBean(clazz);
        Method method = methodExecuteInfo.getMethod();
        List<MethodInfo.ParamInfo> methodParams = methodExecuteInfo.getParamInfos();
        Map<String, String[]> webParams = methodExecuteInfo.getParamMap();
        int methodParamsSize = methodParams.size();
        if (methodParamsSize == 0 || webParams.size() == 0) {
            Object[] paramValus = new Object[methodParamsSize];
            for (int i = 0; i < methodParamsSize; i++) {
                paramValus[i] = null;
            }
            return method.invoke(object, paramValus);
        } else {
            Object[] paramValus = new Object[methodParamsSize];
            for (int i = 0; i < methodParamsSize; i++) {
                MethodInfo.ParamInfo paramInfo = methodParams.get(i);
                String urlParam = paramInfo.getUrlParamName();
                if (urlParam == null) {
                    if (paramInfo.getType().equals(ServletRequest.class) ||
                            paramInfo.getType().equals(HttpServletRequest.class)) {
                        paramValus[i] = request;
                    } else if (paramInfo.getType().equals(HttpSession.class)) {
                        paramValus[i] = request.getSession();
                    } else {
                        paramValus[i] = null;
                    }
                } else if (paramInfo.getType().equals(String.class)) {
                    paramValus[i] = getStrVal(urlParam, webParams);
                } else if (paramInfo.getType().equals(Map.class)) {
                    paramValus[i] = getMapVal(urlParam, webParams);
                } else if (paramInfo.getType().equals(boolean.class)) {
                    paramValus[i] = getBoolrenVal(urlParam, webParams);
                } else if (paramInfo.getType().equals(Date.class)) {
                    paramValus[i] = getDateVal(urlParam, webParams);
                } else {
                    paramValus[i] = getEntityVal(urlParam, webParams, paramInfo.getType());
                }
            }
            return method.invoke(object, paramValus);
        }
    }

    /**
     * 获取方法参数匹配信息
     *
     * @param urlParam  映射信息
     * @param webParams 请求参数
     * @return 参数map
     */
    private Map<String, String> getMapVal(String urlParam, Map<String, String[]> webParams) {
        Map<String, String> map = new HashMap<>();
        if (".".equals(urlParam)) {
            for (Map.Entry<String, String[]> entry : webParams.entrySet()) {
                map.put(entry.getKey(), entry.getValue()[0]);
            }
        } else {
            String[] arr = urlParam.split(".");
            if (arr.length > 1) {
                String name = arr[0] + ".";
                for (Map.Entry<String, String[]> entry : webParams.entrySet()) {
                    String webParamName = entry.getKey();
                    String key = webParamName.replaceFirst(name, "");
                    if (!key.equals(webParamName)) {
                        map.put(key, entry.getValue()[0]);
                    }
                }
            }
        }
        return map;
    }

    /**
     * 根据请求参数与配置生产实体
     *
     * @param urlParam    映射信息
     * @param webParams   请求参数
     * @param entityClass 实体类
     * @return 实体类对象
     */
    private Object getEntityVal(String urlParam, Map<String, String[]> webParams,
                                Class<?> entityClass) throws Exception {
        Object obj = entityClass.newInstance();
        BeanUtils.populate(obj, getMapVal(urlParam, webParams));
        return obj;
    }

    /**
     * 获取 boolean
     */
    private boolean getBoolrenVal(String urlParam, Map<String, String[]> webParams) {
        String[] arr = webParams.get(urlParam);
        return arr != null && "true".equals(arr[0]);
    }

    /**
     * 获取 string
     */
    private String getStrVal(String urlParam, Map<String, String[]> webParams) {
        String[] arr = webParams.get(urlParam);
        return arr == null ? null : arr[0];
    }

    /**
     * 获取 日期类型
     */
    private Date getDateVal(String urlParam, Map<String, String[]> webParams) {
        String[] arr = webParams.get(urlParam);
        if (arr == null) {
            return null;
        }
        String param = arr[0];
        if (Strings.isBlank(param)) {
            return null;
        }
        DateFormat dateFormat;
        if (param.contains("-")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        } else if (param.contains("/")) {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        } else {
            return null;
        }

        try {
            return dateFormat.parse(param);
        } catch (Exception e) {
            return null;
        }
    }
}
