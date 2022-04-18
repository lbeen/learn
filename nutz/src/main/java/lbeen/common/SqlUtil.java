package lbeen.common;

import org.nutz.dao.SqlManager;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.impl.sql.NutSql;
import org.nutz.dao.sql.Sql;

/**
 * @author 李斌
 */
public class SqlUtil {

    public static Sql getSql(Class<?> clazz, String sqlId) {
        String path = clazz.getPackage().getName().replace("lbeen", "sqls");
        path = path.substring(0, path.lastIndexOf("."));
        path = path.replace(".", "/");
        SqlManager sqlManager = new FileSqlManager(path);
        return new NutSql(sqlManager.get(sqlId));
    }


}
