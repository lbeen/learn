package lbeen.sys.module.service;

import lbeen.common.BaseService;
import lbeen.common.Result;
import lbeen.common.Tree;
import lbeen.sys.module.bean.Module;
import org.apache.shiro.SecurityUtils;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 李斌
 */
@IocBean(args = {"refer:dao"})
public class ModuleService extends BaseService<Module> {
    public ModuleService(Dao dao) {
        super(dao);
    }

    public Result getModules(){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        //fcda8b11-8955-43af-b9c1-5291a05af69c
        HttpSession session = Mvcs.getHttpSession();
        Mvcs.getReq().getRemoteHost();
        List<Record> records = queryRecords("GET_MODULES");
        Tree<Record> tree = new Tree<>(records,(r1, r2) -> Strings.equals(r1.getString("SJBH"), r2.getString("ID")));
        return Result.success(tree.topNodes());
    }
}
