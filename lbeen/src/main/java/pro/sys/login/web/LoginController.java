package pro.sys.login.web;

import lb.dao.util.Record;
import lb.ioc.annotation.bean.Controller;
import lb.ioc.annotation.field.Inject;
import lb.mvc.Mvcs;
import lb.mvc.annotation.At;
import lb.mvc.annotation.Login;
import lb.mvc.annotation.Ok;
import lb.mvc.annotation.Param;
import lb.util.Result;
import lb.util.SessionUser;
import pro.sys.login.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 李斌
 */
@Controller
@At("/")
public class LoginController {
    @Inject
    private UserService userService;

    @At
    @Ok("index.html")
    @Login()
    public Object index(){
        Record map = new Record();
        map.put("user", Mvcs.getUserInfo());
        return map;
    }

    @At
    @Ok("login/login.html")
    public void login(){}

    @At
    @Ok("json")
    public Result loginin(@Param("yhm") String yhm, @Param("mm")String mm, HttpServletRequest request){
        SessionUser user = userService.checkUser(yhm,mm);
        if (user != null){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            Object obj = session.getAttribute("url");
            return Result.success(obj == null ? "/index" : obj.toString());
        }
        return Result.error("用户名或者密码错误！");
    }

    @At
    @Ok("json")
    public Result loginout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return Result.error();
    }

    @At
    @Ok("login/xgmm.html")
    public Object xgmm(){
        Record map = new Record();
        map.put("user", Mvcs.getUserInfo());
        return map;
    }
}
