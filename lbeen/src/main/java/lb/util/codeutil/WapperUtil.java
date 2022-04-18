package lb.util.codeutil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * 生成装饰类工具
 */
public class WapperUtil {

    public static void getWapperCode(Class<?> clazz, String className, String path) throws IOException {
        File writeFile = new File(path);
        if (!writeFile.exists()) {
            writeFile.createNewFile();
        }
        try (FileWriter write = new FileWriter(writeFile)) {

            Method[] methods = clazz.getDeclaredMethods();

            StringBuilder sb = new StringBuilder();
            for (Method method : methods) {
                //注解@Override
                sb.append("\n@Override\n");
                //权限
                int modifiers = method.getModifiers();
                String modifier = Modifier.toString(modifiers);
                sb.append(modifier.split(" ")[0]);
                //返回值类型
                Class<?> returnType = method.getReturnType();
                sb.append(" ").append(returnType.getSimpleName());
                //方法名
                sb.append(" ").append(method.getName()).append("(");
                //方法参数
                Parameter[] params = method.getParameters();
                if (params.length > 0) {
                    for (Parameter param : params) {
                        sb.append(param.getType().getSimpleName()).append(" ");
                        sb.append(param.getName()).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(")");
                //异常
                Class<?>[] exceptions = method.getExceptionTypes();
                if (exceptions.length > 0) {
                    sb.append(" throws ");
                    for (Class<?> exception : exceptions) {
                        sb.append(exception.getSimpleName()).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append("{\n");
                //调用内部本类方法
                //是否有返回参数
                if (!"void".equals(returnType.getSimpleName())) {
                    sb.append("return ");
                }
                //方法名
                sb.append(className).append(".").append(method.getName()).append("(");
                //方法参数
                if (params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        sb.append("arg").append(i).append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                }
                sb.append(");\n}");
            }

            //写入文件
            write.write(sb.toString());
        }
    }

}
