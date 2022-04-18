package lb.plugin;

import lb.mvc.ResponseHandler;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.misc.BeetlUtil;
import org.beetl.core.resource.WebAppResourceLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * beet响应处理插件
 *
 * @author 李斌
 */
public class BeetlResponseHandler implements ResponseHandler {
    private GroupTemplate groupTemplate;

    public BeetlResponseHandler() {
        try {
            WebAppResourceLoader resourceLoader =
                    new WebAppResourceLoader(BeetlUtil.getWebRoot() + "/WEB-INF/views/");
            Configuration cfg = Configuration.defaultConfiguration();
            groupTemplate = new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            throw new RuntimeException("beetl加载失败！");
        }
    }


    @Override
    public void handle(Object result, HttpServletRequest request,
                       HttpServletResponse response, String page)
            throws IOException {
        Template t = groupTemplate.getTemplate(page);
        t.binding("obj", result);
        t.binding("base", request.getContextPath());
        t.renderTo(response.getWriter());
    }
}
