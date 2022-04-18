package lb.mvc;

import lb.mvc.lb.LbUrlBook;
import lb.mvc.util.MethodExecuteInfo;
import lb.util.SessionUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * mvc方法类
 *
 * @author 李斌
 */
public abstract class Mvcs {
    /**
     * 链接与类方法映射信息
     */
    private static UrlBook URLBOOK;
    /**
     * 响应执行类
     */
    private static ResponseHandler RESPONSEHANDLER;
    /**
     * 发生异常时的响应执行类
     */
    private static ExceptionHandler EXCEPTIONHANDLER;
    /**
     * 方法执行器
     */
    private static MethodExecuter METHODEXECUTER = new MethodExecuter();
    /**
     * 登录页面路径
     */
    private static String LOGINPAGE;
    /**
     * Session
     */
    private static ThreadLocal<HttpSession> SESSIONS = new ThreadLocal<>();

    /**
     * 初始化mvc
     */
    public static void initMvc(String loginpage, Class<? extends ResponseHandler> responseHandlerclazz,
                               Class<? extends ExceptionHandler> exceptionHandlerClazz) throws Exception {
        LOGINPAGE = loginpage;
        URLBOOK = new LbUrlBook();
        URLBOOK.init();
        RESPONSEHANDLER = responseHandlerclazz.newInstance();
        EXCEPTIONHANDLER = exceptionHandlerClazz.newInstance();
    }

    /**
     * 解析url
     */
    public static MethodExecuteInfo paserUrl(String url) {
        return URLBOOK.paserUrl(url);
    }

    /**
     * 处理响应
     */
    public static void handleResponse(Object result, HttpServletRequest request,
                                      HttpServletResponse response, String page) throws IOException {
        RESPONSEHANDLER.handle(result, request, response, page);
    }

    /**
     * 处理异常响应
     */
    public static void handleException(HttpServletRequest request, HttpServletResponse response,
                                       Exception e, String returnType) throws IOException {
        if ("json".equals(returnType)) {
            response.setContentType("application/json; charset=utf-8");
            EXCEPTIONHANDLER.handleJson(request, response, e);
        } else {
            response.setContentType("text/html;charset=UTF-8");
            EXCEPTIONHANDLER.handlePage(request, response, e);
        }
    }

    /**
     * 获取登录页面
     */
    public static String getLOGINPAGE() {
        return LOGINPAGE;
    }

    /**
     * 执行方法返回结果
     */
    public static Object executeMethd(MethodExecuteInfo methodExecuteInfo,
                                      HttpServletRequest request) throws Exception {
        return METHODEXECUTER.execute(methodExecuteInfo, request);
    }

    /**
     * 添加session
     */
    public static void putSession(HttpSession session) {
        SESSIONS.set(session);
    }

    /**
     * 添加移除
     */
    public static void removeSession() {
        SESSIONS.remove();
    }

    /**
     * 获取登录页面
     */
    public static SessionUser getUserInfo() {
        return (SessionUser) SESSIONS.get().getAttribute("user");
    }
}
