var ioc = {
    conf : {
        type : "org.nutz.ioc.impl.PropertiesProxy",
        fields : {
            paths : ["config/db.properties"]
        }
    },

    dao: {
        type: "org.nutz.dao.impl.NutDao",
        args: [{refer: "dataSource"}]
    },
    dataSource: {
        type: "com.alibaba.druid.pool.DruidDataSource",
        events: {
            depose: 'close'
        },
        fields: {
            url: "jdbc:oracle:thin:@192.168.1.110:1521:orcl",
            username: "test",
            password: "123",
            maxWait: 15000,
            defaultAutoCommit: false
        }
    }

    /*
     <bean id="redisManager" class="org.crazycake.shiro.RedisManager">

     <property name="host" value="${redisManager.host}"/>

     <property name="port" value="${redisManager.port}"/>

     <property name="expire" value="${redisManager.timeout}"/>

     <!-- optional properties:

     <property name="timeout" value="10000"/>

     <property name="password" value="123456"/>

     -->

     </bean>
     */
};