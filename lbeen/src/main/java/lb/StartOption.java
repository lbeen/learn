package lb;

/**
 * 启动参数
 *
 * @author 李斌
 */
class StartOption {
    /**
     * ioc容器扫描的包
     */
    private String iocPackage;
    /**
     * 数据库配置文件
     */
    private String dbConfigPath;
    /**
     * dao实体扫描包
     */
    private String entityPackage;
    /**
     * 响应执行类
     */
    private String responseHandlerClazz;
    /**
     * 发生异常时的响应执行类
     */
    private String exceptionHandlerClazz;
    /**
     * 登录页面路径
     */
    private String loginPage;

    String getIocPackage() {
        return iocPackage;
    }

    void setIocPackage(String iocPackage) {
        this.iocPackage = iocPackage;
    }

    String getDbConfigPath() {
        return dbConfigPath;
    }

    void setDbConfigPath(String dbConfigPath) {
        this.dbConfigPath = dbConfigPath;
    }

    String getEntityPackage() {
        return entityPackage;
    }

    void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    String getResponseHandlerClazz() {
        return responseHandlerClazz;
    }

    void setResponseHandlerClazz(String responseHandlerClazz) {
        this.responseHandlerClazz = responseHandlerClazz;
    }

    String getExceptionHandlerClazz() {
        return exceptionHandlerClazz;
    }

    void setExceptionHandlerClazz(String exceptionHandlerClazz) {
        this.exceptionHandlerClazz = exceptionHandlerClazz;
    }

    String getLoginPage() {
        return loginPage;
    }

    void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
}
