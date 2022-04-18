package lb.dao.util;

import lb.dao.ORInfo;
import lb.dao.ORs;
import lb.util.ClassUtil;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 数据结果集操作工具类
 *
 * @author 李斌
 */
public abstract class RsUtil {
    /**
     * 将结果集封装成对象，通过set方法
     */
    public static <T> T rs2Obj(ResultSet rs, Class<T> clazz) throws Exception {
        ORInfo<T> orInfo = ORs.getOrInfo(clazz);
        if (orInfo != null) {
            return rs2Entity(rs, orInfo);
        }
        if (rs.next()) {
            Field[] fieldArr = clazz.getDeclaredFields();
            List<Field> fields = new ArrayList<>();
            Collections.addAll(fields, fieldArr);
            T t = clazz.newInstance();
            ResultSetMetaData rsmd = rs.getMetaData();
            columnFor:
            for (int i = 1, len = rsmd.getColumnCount(); i <= len; i++) {
                String column = rsmd.getColumnLabel(i);
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (fieldName.equalsIgnoreCase(column)) {
                        RsMenthodExcuter<T> excuter = RsMenthodExcuter.set(column, clazz, ClassUtil.getSet(field), field.getType());
                        excuter.setVar(t, rs);
                        continue columnFor;
                    }
                }
            }
            return t;
        }
        return null;
    }

    /**
     * 将结果集封装成对象，通过set方法
     */
    public static <T> List<T> rs2Objs(ResultSet rs, Class<T> clazz) throws Exception {
        ORInfo<T> orInfo = ORs.getOrInfo(clazz);
        if (orInfo != null) {
            return rs2Entitys(rs, orInfo);
        }

        List<RsMenthodExcuter<T>> excuters = new ArrayList<>();
        List<T> ts = new ArrayList<>();
        T t;
        int count = 0;
        while (rs.next()) {
            if (count == 0) {
                ResultSetMetaData rsmd = rs.getMetaData();
                Field[] fieldArr = clazz.getDeclaredFields();
                List<Field> fields = new ArrayList<>();
                Collections.addAll(fields, fieldArr);
                columnFor:
                for (int i = 1, len = rsmd.getColumnCount(); i <= len; i++) {
                    String column = rsmd.getColumnLabel(i);
                    for (Field field : fields) {
                        if (field.getName().equalsIgnoreCase(column)) {
                            RsMenthodExcuter<T> excuter = RsMenthodExcuter.set(column, clazz, ClassUtil.getSet(field), field.getType());
                            excuters.add(excuter);
                            continue columnFor;
                        }
                    }
                }
            }

            t = clazz.newInstance();
            for (RsMenthodExcuter<T> excuter : excuters) {
                excuter.setVar(t, rs);
            }
            ts.add(t);
            count++;
        }
        return ts;
    }

    /**
     * 获取结果集的第一个结果
     */
    public static <T> Object getFirstField(ResultSet rs, Class<T> type) throws SQLException {
        if (rs == null) {
            return null;
        }
        if (rs.next()) {
            if (type == null || Object.class.equals(type)) {
                return rs.getObject(1);
            }
            if (String.class.equals(type)) {
                return rs.getString(1);
            }
            if (Date.class.equals(type)) {
                return rs.getTimestamp(1);
            }
        }
        return null;
    }

    /**
     * 将结果集封装成对象
     */
    private static <T> T rs2Entity(ResultSet rs, ORInfo<T> orInfo) throws Exception {
        if (rs.next()) {
            Class<T> clazz = orInfo.getClazz();
            Map<String, ORInfo<T>.Column> columInfos = orInfo.getColumInfos();

            T t = clazz.newInstance();
            for (Map.Entry<String, ORInfo<T>.Column> entry : columInfos.entrySet()) {
                ORInfo.Column column = entry.getValue();
                RsMenthodExcuter<T> excuter = RsMenthodExcuter.set(entry.getKey(), clazz, column.getSet(), column.getParamClass());
                excuter.setVar(t, rs);
            }
            return t;
        }
        return null;
    }

    /**
     * 将结果集封装成对象list
     */
    private static <T> List<T> rs2Entitys(ResultSet rs, ORInfo<T> orInfo) throws Exception {
        Class<T> clazz = orInfo.getClazz();
        Map<String, ORInfo<T>.Column> columInfos = orInfo.getColumInfos();

        List<RsMenthodExcuter<T>> excuters = new ArrayList<>();
        List<T> ts = new ArrayList<>();
        T t;
        int count = 0;
        while (rs.next()) {
            if (count == 0) {
                for (Map.Entry<String, ORInfo<T>.Column> entry : columInfos.entrySet()) {
                    ORInfo.Column column = entry.getValue();
                    RsMenthodExcuter<T> excuter = RsMenthodExcuter.set(entry.getKey(), clazz, column.getSet(), column.getParamClass());
                    excuters.add(excuter);
                }
            }
            t = clazz.newInstance();
            for (RsMenthodExcuter<T> excuter : excuters) {
                excuter.setVar(t, rs);
            }
            ts.add(t);
            count++;
        }

        return ts;
    }

    /**
     * 将结果集封装成Maps
     */
    public static List<Record> rs2Maps(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<String> columns = new ArrayList<>();
        for (int i = 1, len = rsmd.getColumnCount(); i <= len; i++) {
            columns.add(rsmd.getColumnLabel(i));
        }
        List<Record> records = new ArrayList<>();
        while (rs.next()) {
            Record record = new Record();
            for (String column : columns) {
                record.put(column, rs.getObject(column));
            }
            records.add(record);
        }
        return records;
    }

    /**
     * 将结果集封装成map
     */
    public static Record rs2Map(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        Record record = new Record();
        if (rs.next()) {
            for (int i = 1, len = rsmd.getColumnCount(); i <= len; i++) {
                String column = rsmd.getColumnLabel(i);
                record.put(column, rs.getObject(column));
            }
        }
        return record;
    }
}
