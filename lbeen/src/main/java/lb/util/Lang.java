package lb.util;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * 一些公用方法
 *
 * @author 李斌
 */
public abstract class Lang {
    /**
     * 判断集合数组是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        return obj.getClass().isArray() && Array.getLength(obj) == 0;
    }

    /**
     * 获取一个guid字符串
     */
    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 对象转json
     */
    public static String toJson(Object obj) {
        if (obj == null) {
            return "{}";
        }
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return JSONObject.fromObject(obj, jsonConfig).toString();
    }
}
