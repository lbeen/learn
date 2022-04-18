package lb.dao.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件读取sql
 *
 * @author 李斌
 */
public class SqlReader {
    /**
     * key识别开始符号
     */
    private String keyBegin;
    /**
     * key识别结束符号
     */
    private String keyEnd;
    /**
     * 文件后缀
     */
    private String suffix;

    public SqlReader() {
        this.keyBegin = "/*";
        this.keyEnd = "*/";
        this.suffix = ".sql";
    }

    /**
     * 获取文件中指定key的sql
     */
    public String getSql(String sqlId, String path) {
        path = SqlReader.class.getResource(path).getPath();
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles(f -> f.getName().endsWith(suffix) && f.length() > 0);
            if (files == null) {
                return null;
            }
            for (File f : files) {
                String sql = getSql(sqlId, f);
                if (sql != null) {
                    return sql;
                }
            }
            return null;
        } else {
            return getSql(sqlId, file);
        }
    }

    /**
     * 获取文件中指定key的sql
     */
    private String getSql(String sqlId, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sql = new StringBuilder();
            boolean findKey = false;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(keyBegin) && line.endsWith(keyEnd)) {
                    if (findKey) {
                        break;
                    }
                    findKey = line.substring(keyBegin.length(), line.lastIndexOf(keyEnd)).trim().equals(sqlId);
                } else if (findKey) {
                    line = line.trim();
                    if (line.length() > 0) {
                        sql.append(line);
                    }
                }
            }
            if (findKey && sql.length() > 0) {
                return sql.toString();
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取文件中指定所有sql
     */
    public Map<String, String> getSqls(String path) {
        path = SqlReader.class.getResource(path).getPath();
        File file = new File(path);
        if (file.isDirectory()) {
            Map<String, String> sqlPool = new HashMap<>();
            File[] files = file.listFiles(f -> f.getName().endsWith(suffix) && f.length() > 0);
            if (files != null) {
                for (File f : files) {
                    sqlPool.putAll(getSqls(f));
                }
            }
            return sqlPool;
        } else {
            return getSqls(file);
        }
    }

    /**
     * 获取文件中指定所有的sql
     */
    private Map<String, String> getSqls(File file) {
        Map<String, String> sqlPool = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String sqlKey = null;
            StringBuilder sql = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (line.startsWith(keyBegin) && line.endsWith(keyEnd)) {
                    if (sqlKey != null && sql.length() > 0) {
                        sqlPool.put(sqlKey, sql.toString());
                    }
                    sql.setLength(0);
                    sqlKey = line.substring(keyBegin.length(), line.lastIndexOf(keyEnd)).trim();
                    if (sqlKey.length() == 0) {
                        sqlKey = null;
                    }
                } else {
                    if (sqlKey != null && (line = line.trim()).length() > 0) {
                        sql.append(line);
                    }
                }
            }
            if (sqlKey != null && sql.length() > 0) {
                sqlPool.put(sqlKey, sql.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlPool;
    }
}
