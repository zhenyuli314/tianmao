package com.lzy.tianmao.util;

public class Result {
    private static final int SUCCESS_CODE=0;
    private static final int FAIL_CODE=1;

    private int code;
    private String message;
    private Object data;

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success(){
        return new Result(SUCCESS_CODE,null,null);
    }
    public static Result success(Object data){
        return new Result(SUCCESS_CODE,null,data);
    }
    public static Result fail(String message){
        return new Result(FAIL_CODE,message,null);
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

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
