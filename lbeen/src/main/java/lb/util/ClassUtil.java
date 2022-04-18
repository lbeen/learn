package lb.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.function.Consumer;

/**
 * Class工具类
 *
 * @author 李斌
 */
public class ClassUtil {
    /**
     * 扫描包下的类
     */
    public static void scanClass(String scanPackage, Consumer<Class<?>> paser) {
        String scanDirPath;
        if (Strings.isBlank(scanPackage)) {
            scanPackage = "";
            scanDirPath = "/";
        } else {
            scanPackage += ".";
            scanDirPath = "/" + scanPackage.replace(".", "/");
        }
        scanDirPath = ClassUtil.class.getResource(scanDirPath).getPath();
        try {
            scanClassFile(new File(scanDirPath), scanPackage, paser);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 扫描配置包下面的所有类全称
     *
     * @param scanDir     扫描包装换的文件路径
     * @param scanPackage 配置的扫描包
     */
    private static void scanClassFile(File scanDir, String scanPackage, Consumer<Class<?>> paser)
            throws ClassNotFoundException {
        //扫描class文件，不包括内部类
        File[] classFiles = scanDir.listFiles(file ->
                file.getName().endsWith(".class") && !file.getName().contains("$")
                        && file.length() > 0);
        if (classFiles != null) {
            for (File classFile : classFiles) {
                Class<?> clazz = Class.forName(scanPackage + classFile.getName().replace(".class", ""));
                paser.accept(clazz);
            }
        }
        //递归扫描包文件夹
        File[] dirs = scanDir.listFiles(File::isDirectory);
        if (dirs != null) {
            for (File dir : dirs) {
                scanClassFile(dir, scanPackage + dir.getName() + ".", paser);
            }
        }
    }

    /**
     * 获取类首字母小写
     */
    public static String getLowcaseFirstClassName(Class<?> clazz) {
        String className = clazz.getSimpleName();
        return className.substring(0, 1).toLowerCase() + Strings.right(className, 1);
    }

    /**
     * 获取类字段get方法名称
     */
    public static String getGet(Field field) {
        Class<?> type = field.getType();
        String fieldName = field.getName();
        if (type.equals(boolean.class) || type.equals(Boolean.class)) {
            return "is" + Strings.upcaseFirst(fieldName);
        }
        return "get" + Strings.upcaseFirst(fieldName);
    }

    /**
     * 获取类字段set方法名称
     */
    public static String getSet(Field field) {
        return "set" + Strings.upcaseFirst(field.getName());
    }
}
