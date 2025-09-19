package cn.lazylhxzzy.save_upload.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T> {
    private Integer statusCode;
    private String msg;
    private T data;
    private Result(){}
    public static <T> Result<T> build(T data){
        Result<T> result = new Result<>();
        result.setData(data);
        return result;
    }
    public static <T> Result<T> build(Integer statusCode, String msg, T data){
        Result<T> result = build(data);
        result.setStatusCode(statusCode);
        result.setMsg(msg);
        return result;
    }
    public static <T> Result<T> build(T data, ResultCodeEnum resultCodeEnum){
        Result<T> result = build(data);
        result.setStatusCode(resultCodeEnum.getStatusCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }
    public static <T> Result<T> build(Integer statusCode, String msg){
        return build(statusCode, msg, null);
    }

    public static <T> Result<T> isOk(T data){
        return build(data, ResultCodeEnum.SUCCESS);
    }
    public static <T> Result<T> isFail(T data){
        return build(data, ResultCodeEnum.GLOBAL_ERROR);
    }

    @Override
    public String toString() {
        return "Result{" +
                "statusCode=" + statusCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
