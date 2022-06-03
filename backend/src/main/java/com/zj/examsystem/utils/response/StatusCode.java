package com.zj.examsystem.utils.response;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public interface StatusCode extends Serializable {
    // 序列化只显示code
    @JsonValue
    Integer code();

    String message();
}
