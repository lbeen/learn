package lb.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应执行类
 *
 * @author 李斌
 */
public interface ResponseHandler {
    /**
     * 执行响应
     */
    void handle(Object result, HttpServletRequest request,
                HttpServletResponse response, String page)
            throws IOException;
}
