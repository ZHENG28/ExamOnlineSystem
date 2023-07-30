package com.zj.examsystem.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponseEntity<T> {
    private Boolean success;

    private StatusCode code;

    private String message;

    private T data;

    // 防止实例化
    //    private BaseResponseEntity() {}

    public static <T> BaseResponseEntity<T> ok(String message, T data) {
        return new BaseResponseEntity<>(true, ResponseCode.SUCCESS, message, data);
    }

    public static <T> BaseResponseEntity<T> error(StatusCode status, String message) {
        return new BaseResponseEntity<>(false, status, message, null);
    }
}
