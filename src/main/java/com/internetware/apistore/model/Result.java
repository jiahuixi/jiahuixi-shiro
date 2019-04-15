package com.internetware.apistore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @ Desc
 * @ Author YoungSmith
 * @ Date 17-9-7 下午10:08
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private static Result result(int code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result success(Object data) {
        return success(ResultTypeEnum.SUCCESS.msg, data);
    }

    public static Result success(String msg, Object data) {
        return result(ResultTypeEnum.SUCCESS.code, msg, data);
    }

    public static Result success() {
        return success(null);
    }

    public static Result fail(ResultTypeEnum rte, String data) {
        return new Result(rte.code, rte.msg, data);
    }

    public static Result fail(ResultTypeEnum rte, Object data) {
        return new Result(rte.code, rte.msg, data);
    }

    public static Result fail(ResultTypeEnum rte) {
        return new Result(rte.code, rte.msg);
    }

    public static Result fail(String msg) {
        return new Result(ResultTypeEnum.DEFAULT_ERROR.code, msg);
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

    public boolean successed() {
        return code == ResultTypeEnum.SUCCESS.code;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResultTypeEnum.SUCCESS.code == this.code;
    }

    @JsonIgnore
    public boolean isFailed() {
        return !isSuccess();
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static enum ResultTypeEnum {
        SUCCESS(0, "success"),
        DEFAULT_ERROR(1, ""),
        // 服务器内部错误 50××××
        //数据校验错误  500100

        //数据库错误  500200
        PERSISTENT_ERROR(500201, "持久化错误"),

        //HttpClient 异常
        HTTP_CLIENT_EXCEPTION(500301, "请求异常"),

        //500900 task 异常
        TASK_NOT_EXISTED(500401, "任务的taskId 不存在"),
        TASK_HAD_OPEN(500402, "任务已经开启"),
        TASK_HAD_CLOSE(500403, "任务已经关闭"),
        TASK_SCHEDUL_ERROR(500404, "任务调度异常"),

        //api 异常
        API_NOT_EXISTED(500501, "api 不存在"),
        API_EXISTED(500502, "api 已经存在"),
        // 其他异常
        LOGIN_ERROR(500901, "登录异常");


        private int code;
        private String msg;

        ResultTypeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
