package com.zj.examsystem.utils.response;

public enum ResponseCode implements StatusCode {
    SUCCESS(200, ""),
    FAIL(999, "");
    //    ABNORMAL(500, "系统异常，请稍后再试"),
    //    ACCESS_DENIED(401, "匿名用户访问资源，请登录重试"),
    //    NO_AUTHORIZATION(403, "无访问权限，请联系管理员授予权限"),
    //    NOT_FOUND(404, "无法找到资源"),
    //    INVALID_TOKEN(1000, "访问令牌不合法");

    private final Integer code;

    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                '}';
    }
}
