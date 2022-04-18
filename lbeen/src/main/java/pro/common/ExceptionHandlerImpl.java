package pro.common;

import lb.ioc.Iocs;
import lb.mvc.ExceptionHandler;
import lb.util.Lang;
import lb.util.Result;
import pro.sys.log.service.LogService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
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
            LogService logService = (LogService)Iocs.getBean(LogService.class);
            String logBh = logService.saveLog(e);
            out.write(Lang.toJson(Result.error("错误编号：" + logBh)));
        } catch (IOException ioe){
            throw ioe;
        } catch (Exception e1){
            throw new RuntimeException(e1);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
