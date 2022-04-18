package lb.dao.util;

import java.util.LinkedHashMap;

/**
 * 记录容器
 *
 * @author 李斌
 */
public class Record extends LinkedHashMap<String, Object> {
    @Override
    public Object put(String key, Object value) {
        return super.put(key.toLowerCase(), value);
    }

    public String getString(String key) {
        if (key == null) {
            return null;
        }
        Object val = super.get(key.toLowerCase());
        return val == null ? null : val.toString();
    }

    public int getInt(String key) {
        if (key == null) {
            return -1;
        }
        Object val = super.get(key.toLowerCase());
        return val == null ? -1 : Integer.parseInt(val.toString());
    }
}
