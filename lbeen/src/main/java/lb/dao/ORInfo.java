package lb.dao;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据表和对象映射信息
 *
 * @author 李斌
 */
public class ORInfo<T> {
    /**
     * 实体类
     */
    private Class<T> clazz;
    /**
     * 实体映射表
     */
    private String table;
    /**
     * 实体类字段与表字段信息
     */
    private Map<String, Column> columInfos = new LinkedHashMap<>();
    /**
     * 实体类主键
     */
    private Key key;

    public ORInfo(Class<T> clazz, String table) {
        this.clazz = clazz;
        this.table = table;
    }

    public void setKey(String colum, String get, String set, boolean auto, Class<?> paramClass) {
        this.key = new Key(colum, get, set, auto, paramClass);
    }

    public Column addColumInfo(String colum, String get, String set, Class<?> paramClass) {
        Column column = new Column(get, set, paramClass);
        columInfos.put(colum, column);
        return column;
    }

    public Map<String, Column> getColumInfos() {
        return columInfos;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public String getTable() {
        return table;
    }

    public Key getKey() {
        return key;
    }

    /**
     * 实体类字段与表字段信息
     */
    public class Column {
        /**
         * 字段get方法
         */
        private String get;
        /**
         * 字段set方法
         */
        private String set;
        /**
         * 字段类型
         */
        private Class<?> paramClass;
        /**
         * 字段默认值插入时不更新实体
         */
        private String var;
        /**
         * 字段默认值插入时更新实体
         */
        private String entityVar;

        Column(String get, String set, Class<?> paramClass) {
            this.get = get;
            this.set = set;
            this.paramClass = paramClass;
        }

        public String getGet() {
            return get;
        }

        public String getSet() {
            return set;
        }

        public Class<?> getParamClass() {
            return paramClass;
        }

        public String getVar() {
            return var;
        }

        public void setVar(String var) {
            this.var = var;
        }

        public String getEntityVar() {
            return entityVar;
        }

        public void setEntityVar(String entityVar) {
            this.entityVar = entityVar;
        }
    }

    /**
     * 主键信息
     */
    public class Key {
        /**
         * 主键信息
         */
        private String colum;
        /**
         * 字段get方法
         */
        private String get;
        /**
         * 字段set方法
         */
        private String set;
        /**
         * 是否自动增长
         */
        private boolean auto;
        /**
         * 字段类型
         */
        private Class<?> paramClass;

        Key(String colum, String get, String set, boolean auto, Class<?> paramClass) {
            this.colum = colum;
            this.get = get;
            this.set = set;
            this.auto = auto;
            this.paramClass = paramClass;
        }

        public String getColum() {
            return colum;
        }

        public String getGet() {
            return get;
        }

        public String getSet() {
            return set;
        }

        public boolean isAuto() {
            return auto;
        }

        public Class<?> getParamClass() {
            return paramClass;
        }
    }

    @Override
    public String toString() {
        return "ORInfo{" +
                "table='" + table + '\'' +
                ", columInfos=" + columInfos +
                ", key=" + key +
                '}';
    }
}
