package lbeen.sys.user.service;


import lbeen.common.BaseService;
import lbeen.sys.user.bean.UserInfo;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.sql.Criteria;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import java.util.List;

/**
 * @author 李斌
 */
@IocBean(args = {"refer:dao"})
public class UserService extends BaseService<UserInfo> {
    public UserService(Dao dao) {
        super(dao);
    }

    public UserInfo getUser(String yhm, String mm) {
        Criteria cri = Cnd.cri();
        cri.where().andEquals("YHM", yhm);
        cri.where().andEquals("MM", mm);
        List<UserInfo> userInfos = query(cri);
        if (Lang.isEmpty(userInfos)) {
            return null;
        }
        return userInfos.get(0);
    }
}
