package pro.common;

import lb.Logger;
import lb.dao.util.SqlReader;

import java.util.HashMap;
import java.util.Map;

/**
 * sql管理器
 * @author 李斌
 */
public class SqlManager {
    private static Logger logger = Logger.getLogger(SqlManager.class);
    /**
     * sql缓存池
     */
    private static Map<Class,Map<String,String>> SQLPOOL;

    static {
        //缓存时初始化sql缓存池
        if (!logger.isDebugEnabled()){
            SQLPOOL = new HashMap<>();
        }
    }

    /**
     * 根据类和sqlid获取sql
     * @param clazz
     * @param sqlId
     * @return
     */
    public static String getSql(Class clazz,String sqlId){
        return logger.isDebugEnabled() ? getSqlNoCache(clazz, sqlId) : getSqlWithCache(clazz, sqlId);

    }

    /**
     * 根据类和sqlid获取sql（无缓存时）
     * @param clazz
     * @param sqlId
     * @return
     */
    private static String getSqlNoCache(Class clazz,String sqlId){
        String path = clazz.getPackage().getName().replace("pro.", "sqls.");
        path = path.substring(0,path.lastIndexOf(".")).replace(".","/");
        return new SqlReader().getSql(sqlId,"/" + path);
    }

    /**
     * 根据类和sqlid获取sql（有缓存时）
     * @param clazz
     * @param sqlId
     * @return
     */
    private static String getSqlWithCache(Class clazz,String sqlId){
        Map<String,String> thisSqlPool = SQLPOOL.get(clazz);
        if (thisSqlPool == null){
            String path = clazz.getPackage().getName().replace("pro.", "sqls.");
            path = path.substring(0,path.lastIndexOf(".")).replace(".","/");
            thisSqlPool = new SqlReader().getSqls("/" + path);
            SQLPOOL.put(clazz, thisSqlPool);
        }
        return thisSqlPool.get(sqlId);
    }
}
