package lb;

import lb.mvc.Mvcs;
import lb.mvc.util.MethodExecuteInfo;
import lb.util.Lang;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author 李斌
 */
public class lbeenServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        StartOption option = new StartOption();
        option.setIocPackage(getInitParameter("iocpackage"));
        option.setDbConfigPath(getInitParameter("dbconfigpath"));
        option.setEntityPackage(getInitParameter("entityPackage"));
        option.setExceptionHandlerClazz(getInitParameter("exceptionhandler"));
        option.setResponseHandlerClazz(getInitParameter("responsehandler"));
        option.setLoginPage(getInitParameter("loginpage"));
        try {
            StartUp.start(option);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.replaceFirst(this.getServletContext().getContextPath(), "");
        if ("/".equals(uri)){
            uri = "/index";
        }
        MethodExecuteInfo methodExecuteInfo = Mvcs.paserUrl(uri);
        if (methodExecuteInfo != null) {
            if (methodExecuteInfo.getLogin() != null && request.getSession().getAttribute("user") == null) {
                request.getSession().setAttribute("url", uri);
                response.setContentType("text/html;charset=UTF-8");
                Mvcs.handleResponse(null, request, response, Mvcs.getLOGINPAGE());
            } else {
                Mvcs.putSession(request.getSession());
                methodExecuteInfo.setParamMap(request.getParameterMap());
                request.setCharacterEncoding("UTF-8");
                try {
                    Object result = Mvcs.executeMethd(methodExecuteInfo, request);
                    if ("json".equals(methodExecuteInfo.getReturnType())) {
                        response.setContentType("application/json; charset=utf-8");
                        PrintWriter out = null;
                        try {
                            out = response.getWriter();
                            out.write(Lang.toJson(result));
                        } finally {
                            if (out != null) {
                                out.close();
                            }
                        }
                    } else {
                        response.setContentType("text/html;charset=UTF-8");
                        Mvcs.handleResponse(result, request, response, methodExecuteInfo.getReturnType());
                    }
                } catch (Exception e) {
                    Mvcs.handleException(request, response, e, methodExecuteInfo.getReturnType());
                } finally {
                    Mvcs.removeSession();
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
