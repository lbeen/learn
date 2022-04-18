package lbeen.sys.module.web;

import lbeen.common.Result;
import lbeen.sys.module.service.ModuleService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @author 李斌
 */
@IocBean
@At("/module/")
public class ModuleController {
    @Inject
    private ModuleService moduleService;

    @At
    @Ok("json")
    @RequiresUser
    public Result getModules(){
        return moduleService.getModules();
    }
}
