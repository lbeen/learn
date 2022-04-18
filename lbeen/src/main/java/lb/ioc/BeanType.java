package lb.ioc;

/**
 * bean类型
 *
 * @author 李斌
 */
public enum BeanType {
    /**
     * 控制器bean
     */
    CONTROLLER,
    /**
     * 服务层bean
     */
    SERVICE,
    /**
     * 数据层bean
     */
    DAO,

    DEFAULT
}
