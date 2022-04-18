package lbeen.sys.user.web;

import lbeen.common.Result;
import lbeen.sys.user.bean.UserInfo;
import lbeen.sys.user.core.MyToken;
import lbeen.sys.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

/**
 * @author 李斌
 */
@IocBean
@At("/")
public class UserController {
    private static final String BASE_URL = "beetl:user/";
    @Inject
    private UserService userService;

    @At("")
    @Ok(BASE_URL + "index.html")
    @RequiresUser
    public Object root() {
        return index();
    }

    @At
    @Ok(BASE_URL + "index.html")
    @RequiresUser
    public Object index() {
        Record record = new Record();
        record.put("ip", Mvcs.getReq().getLocalAddr());
        record.put("port", Mvcs.getReq().getLocalPort());
        record.put("user", SecurityUtils.getSubject().getPrincipal());
        return record;
    }


    @At("user/login")
    @Ok(BASE_URL + "login.html")
    public Object login() {
        Record record = new Record();
        record.put("ip", Mvcs.getReq().getLocalAddr());
        record.put("port", Mvcs.getReq().getLocalPort());
        return record;
    }

    @At
    @Ok("json")
    @POST
    public Object login(@Param("yhm") String yhm, @Param("mm") String mm) {
        if (Strings.isBlank(yhm) || Strings.isBlank(mm)) {
            return Result.error("用户名密码不能为空");
        }
        UserInfo user = userService.getUser(yhm, mm);
        if (user == null) {
            return Result.error("用户名密码错误");
        }
        MyToken token = new MyToken(user);
        SecurityUtils.getSubject().login(token);
        return Result.success();
    }

    @At
    @Ok(BASE_URL + "communion.html")
    @RequiresUser
    public Object communion() {
        Record record = new Record();
        record.put("user", SecurityUtils.getSubject().getPrincipal());
        return record;
    }
}
