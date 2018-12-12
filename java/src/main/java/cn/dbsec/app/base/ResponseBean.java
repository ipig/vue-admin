package cn.dbsec.app.base;

public class ResponseBean {

    // http 状态码
    private int code;

    // 返回信息
    private String message;

    // 返回的数据
    private Object data;

    private ResponseBean(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseBean success(Object data) {
        return new ResponseBean(20000, "ok", data);
    }

    public static ResponseBean error(String msg) {
        return new ResponseBean(-1, msg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
