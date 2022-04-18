package lb.dao.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 李斌
 */
public abstract class SqlUtil {
    /**
     * 解析sql
     *
     * @param sql sql
     */
    public static String paserSql(String sql, List<String> columns) {
        if (!Pattern.matches(".*@+.*", sql)) {
            return sql;
        }
        Matcher matcher = Pattern.compile("(?<=@)(.+?)(?=(\\s|\\)|$|%))").matcher(sql);
        while (matcher.find()) {
            String place = matcher.group();
            sql = sql.replaceFirst("@" + place, "?");
            columns.add(place);
        }
        return sql;
    }

    /**
     * 解析sql
     */
    public static String paserSql(String sql, List<Object> params, Map<String, Object> paramsMap) {
        if (!Pattern.matches(".*@+.*", sql)) {
            return sql;
        }
        Matcher matcher = Pattern.compile("(?<=@)(.+?)(?=(\\s|\\)|$|%))").matcher(sql);
        while (matcher.find()) {
            String place = matcher.group();
            sql = sql.replaceFirst("@" + place, "?");
            params.add(paramsMap.get(place));
        }
        return sql;
    }

    /**
     * 解析sql
     */
    public static String paserSqlWithValue(String sql, Map<String, Object> paramsMap) {
        if (!Pattern.matches(".*@+.*", sql)) {
            return sql;
        }
        Matcher matcher = Pattern.compile("(?<=@)(.+?)(?=(\\s|\\)|$|%))").matcher(sql);
        while (matcher.find()) {
            String place = matcher.group();
            sql = sql.replaceFirst("@" + place, paramsMap.get(place).toString());
        }
        return sql;
    }
}
