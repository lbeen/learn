package pro.sys.module.service;

import lb.dao.util.Record;
import lb.ioc.annotation.bean.Service;
import lb.util.Strings;
import lb.util.Tree;
import pro.common.BaseService;
import lb.util.Result;
import pro.sys.module.bean.Module;

import java.util.List;

/**
 * @author 李斌
 */
@Service
public class ModuleService extends BaseService<Module>{
    public ModuleService(){
        super(Module.class);
    }

    public Result getModules(){
        List<Record> records = queryRecords("GET_MODULES");
        Tree<Record> tree = new Tree<>(records,(r1, r2) -> {
            String sjbh1 = r1.getString("SJBH");
            if (Strings.isBlank(sjbh1)){
                return false;
            }
            return sjbh1.equals(r2.getString("ID"));
        });
        return Result.success(tree.topNodes());
    }
}
