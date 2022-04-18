package pro.sys.log.service;

import lb.ioc.annotation.bean.Service;
import pro.common.BaseService;
import pro.sys.log.bean.XtglLog;

/**
 * @author 李斌
 */
@Service
public class LogService extends BaseService<XtglLog>{
    public LogService(){
        super(XtglLog.class);
    }

    public String saveLog(Exception e){
        XtglLog log = new XtglLog();
        log.setBaseInfo();
        log.setContent(e.getCause().toString());
        save(log);
        return log.getBh();
    }
}
