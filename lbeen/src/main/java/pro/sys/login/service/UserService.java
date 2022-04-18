package pro.sys.login.service;

import lb.ioc.annotation.bean.Service;
import pro.common.BaseService;
import lb.util.SessionUser;
import pro.sys.login.bean.UserInfo;

/**
 * @author 李斌
 */
@Service
public class UserService extends BaseService<UserInfo> {
    public UserService(){
        super(UserInfo.class);
    }

    public SessionUser checkUser(String yhm, String mm){
        return get(SessionUser.class,"GET_SESSIONUSERINFO",
                sql -> sql.addParam("yhm", yhm).addParam("mm", mm));
    }


}
