package lb.util;

/**
 * @author 李斌
 */
public class Result {
    private int code;

    private String msg;

    private Object data;

    private Result(int code, String msg, Object data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public static Result success(){
        return new Result(0, "操作成功！",null);
    }
    public static Result success(Object data){
        return new Result(0, "操作成功！",data);
    }

    public static Result error(){
        return new Result(1, "操作失败！",null);
    }

    public static Result error(String msg){
        return new Result(1, msg,null);
    }

    public static Result error(int code, String msg){
        return new Result(code, msg,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
