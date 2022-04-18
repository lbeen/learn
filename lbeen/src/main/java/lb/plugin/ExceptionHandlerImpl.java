package lb.plugin;

import lb.mvc.ExceptionHandler;
import lb.util.Lang;
import lb.util.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常处理
 *
 * @author 李斌
 */
public class ExceptionHandlerImpl implements ExceptionHandler {

    @Override
    public void handlePage(HttpServletRequest request, HttpServletResponse response,
                           Exception e) throws IOException {
        handle(response, e);
    }

    @Override
    public void handleJson(HttpServletRequest request, HttpServletResponse response,
                           Exception e) throws IOException {
        handle(response, e);
    }

    private void handle(HttpServletResponse response, Exception e) throws IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(Lang.toJson(Result.error(e.getCause().toString())));
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
