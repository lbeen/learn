package lb;

import lb.dao.ORs;
import lb.datasource.DataSources;
import lb.ioc.Iocs;
import lb.mvc.ExceptionHandler;
import lb.mvc.Mvcs;
import lb.mvc.ResponseHandler;
import lb.transaction.Transations;

/**
 * 启动类
 *
 * @author 李斌
 */
abstract class StartUp {
    /**
     * 启动
     *
     * @param option 启动参数
     */
    static void start(StartOption option) throws Exception {
        Iocs.InitIoc(option.getIocPackage());
        DataSources.initDataSource(option.getDbConfigPath());
        Transations.initTransactionpool();
        ORs.init(option.getEntityPackage());
        Class<? extends ResponseHandler> responseHandlerClazz =
                (Class<? extends ResponseHandler>) Class.forName(option.getResponseHandlerClazz());
        Class<? extends ExceptionHandler> exceptionHandlerClazz =
                (Class<? extends ExceptionHandler>) Class.forName(option.getExceptionHandlerClazz());
        Mvcs.initMvc(option.getLoginPage(), responseHandlerClazz, exceptionHandlerClazz);
    }
}
