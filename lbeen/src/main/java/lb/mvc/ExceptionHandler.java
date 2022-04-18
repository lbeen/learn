package lb.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 发生异常时的响应执行类
 *
 * @author 李斌
 */
public interface ExceptionHandler {
    /**
     * 执行响应
     */
    void handlePage(HttpServletRequest request, HttpServletResponse response,
                    Exception e) throws IOException;

    /**
     * 执行响应
     */
    void handleJson(HttpServletRequest request, HttpServletResponse response,
                    Exception e) throws IOException;
}
