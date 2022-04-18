package lbeen.sys.log.service;

import lbeen.common.BaseService;
import lbeen.sys.log.bean.XtglLog;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;

/**
 * @author 李斌
 */
@IocBean(args = {"refer:dao"})
public class LogService extends BaseService<XtglLog> {
    public LogService(Dao dao){
        super(dao);
    }

    public String saveLog(Exception e){
        XtglLog log = new XtglLog();
        log.setId(R.UU16());
        log.setJlzt("1");
        log.setContent(e.getCause().toString());
        _insert(log);
        return log.getBh();
    }
}
