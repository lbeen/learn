package lb.dao;

import lb.dao.annotation.ORAnnotionPaser;
import lb.util.ClassUtil;

import java.util.HashMap;

/**
 * or映射池
 *
 * @author 李斌
 */
public class ORBook {
    /**
     * or映射池Map
     */
    private ClassORInfoMap classORInfoMap = new ClassORInfoMap();

    /**
     * 初始化or映射池Map
     *
     * @param scanPackage 扫描包
     */
    public void init(String scanPackage) {
        ClassUtil.scanClass(scanPackage, new ORAnnotionPaser(this));
    }

    /**
     * 添加映射信息
     */
    public <T> void addOrInfo(ORInfo<T> orInfo) {
        classORInfoMap.add(orInfo);
    }

    /**
     * 获取映射信息
     */
    <T> ORInfo<T> getOrInfo(Class<T> clazz) {
        return classORInfoMap.get(clazz);
    }

    /**
     * or映射池Map
     */
    private class ClassORInfoMap extends HashMap<Class, ORInfo> {
        <T> void add(ORInfo<T> orInfo) {
            super.put(orInfo.getClazz(), orInfo);
        }

        <T> ORInfo<T> get(Class<T> clazz) {
            return super.get(clazz);
        }
    }
}
