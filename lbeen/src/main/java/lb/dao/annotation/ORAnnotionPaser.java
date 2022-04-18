package lb.dao.annotation;

import lb.Logger;
import lb.dao.ORBook;
import lb.dao.ORInfo;
import lb.dao.annotation.bean.Table;
import lb.dao.annotation.field.Column;
import lb.dao.annotation.field.EntityVar;
import lb.dao.annotation.field.Id;
import lb.dao.annotation.field.Var;
import lb.util.ClassUtil;
import lb.util.Strings;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 数据库注解解析类
 *
 * @author 李斌
 */
public class ORAnnotionPaser implements Consumer<Class<?>> {
    private static Logger logger = Logger.getLogger(ORAnnotionPaser.class);
    /**
     * or映射信息
     */
    private ORBook orBook;

    public ORAnnotionPaser(ORBook orBook) {
        this.orBook = orBook;
    }

    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInterface()) {
            paserOR(clazz);
        }
    }

    /**
     * 解析注解
     *
     * @param clazz 类
     */
    private void paserOR(Class<?> clazz) {
        Table table = clazz.getAnnotation(Table.class);
        if (table != null) {
            String tableName = table.value();
            if (Strings.isBlank(tableName)) {
                tableName = clazz.getSimpleName();
            }
            ORInfo<?> orInfo = new ORInfo<>(clazz, tableName.toUpperCase());
            paserColumn(orInfo, clazz);
            orBook.addOrInfo(orInfo);
            if (logger.isDebugEnabled()) {
                logger.info("解析实体：" + clazz.getName() + ":" + orInfo);
            }
        }
    }

    /**
     * 解析字段注解
     */
    private void paserColumn(ORInfo<?> orInfo, Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class superClass = clazz;
        while (!superClass.equals(Object.class)) {
            Collections.addAll(fields, superClass.getDeclaredFields());
            superClass = superClass.getSuperclass();
        }
        int keyCount = 0;
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            Class<?> type = field.getType();
            if (column != null) {
                String fieldName = field.getName();
                String columnName = column.value();
                if (Strings.isBlank(columnName)) {
                    columnName = fieldName;
                }
                ORInfo.Column orColumn = orInfo.addColumInfo(columnName.toUpperCase(),
                        ClassUtil.getGet(field), ClassUtil.getSet(field), type);
                Var var = field.getAnnotation(Var.class);
                if (var != null) {
                    orColumn.setVar(var.value());
                }
                EntityVar entityVar = field.getAnnotation(EntityVar.class);
                if (entityVar != null) {
                    orColumn.setEntityVar(entityVar.value());
                }
            }

            if (keyCount == 0) {
                Id id = field.getAnnotation(Id.class);
                if (id != null) {
                    String fieldName = field.getName();
                    String columnName = id.value();
                    if (Strings.isBlank(columnName)) {
                        columnName = fieldName;
                    }
                    orInfo.setKey(columnName.toUpperCase(), ClassUtil.getGet(field), ClassUtil.getSet(field), id.auto(), type);
                    keyCount++;
                }
            }
        }
    }
}
