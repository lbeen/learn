package pro.sys.module.web;

import lb.ioc.annotation.bean.Controller;
import lb.ioc.annotation.field.Inject;
import lb.mvc.annotation.At;
import lb.mvc.annotation.Ok;
import lb.util.Result;
import pro.sys.module.service.ModuleService;

/**
 * @author 李斌
 */
@Controller
@At("/module/")
public class ModuleController {
    @Inject
    private ModuleService moduleService;

    @At
    @Ok("json")
    public Result getModules(){
        return moduleService.getModules();
    }
}
