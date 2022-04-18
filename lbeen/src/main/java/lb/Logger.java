package lb;

/**
 * 日志类
 *
 * @author 李斌
 */
public class Logger {
    private org.apache.log4j.Logger logger;

    public static Logger getLogger(Class clazz) {
        return new Logger(clazz);
    }

    private Logger(Class clazz) {
        this.logger = org.apache.log4j.Logger.getLogger(clazz);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    public void debug(Object msg) {
        logger.debug(msg);
    }

    public void debug(Object msg, Throwable t) {
        logger.debug(msg, t);
    }

    public void info(Object msg) {
        logger.info(msg);
    }

    public void info(Object msg, Throwable t) {
        logger.info(msg, t);
    }

    public void error(Object msg) {
        logger.error(msg);
    }

    public void error(Object msg, Throwable t) {
        logger.error(msg, t);
    }

}
