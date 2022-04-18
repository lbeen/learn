package lb.util;

/**
 * 字符串工具类
 *
 * @author 李斌
 */
public class Strings {
    private static String EMPTY = "";

    /**
     * 判断字符串是否为空
     *
     * @param value 字符串
     * @return true：空串，null
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * 判断字符串是否为空，nullweikong
     *
     * @param value 字符串
     * @return false：空串，null
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
     * 清除字符串空格制表符回车符等
     *
     * @param value 字符串
     * @return null返回空字符串
     */
    public static String clearBlank(String value) {
        if (value == null) {
            return EMPTY;
        }
        return value.replaceAll("\\s", EMPTY);
    }

    /**
     * 从字符串尾截取字符串
     *
     * @param value   字符串
     * @param leftIdx 截取左边角标
     */
    public static String right(String value, int leftIdx) {
        if (value == null) {
            return null;
        }
        if (value.isEmpty() || leftIdx < 0 || leftIdx >= value.length()) {
            return EMPTY;
        }
        return value.substring(leftIdx, value.length());
    }

    public static String upcaseFirst(String value) {
        if (isBlank(value)) {
            return "";
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }
}
