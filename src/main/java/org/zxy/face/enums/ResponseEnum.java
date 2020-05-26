package org.zxy.face.enums;

import lombok.Getter;

@Getter
public enum  ResponseEnum {

    SUCCESS(0, "成功"),

    ERROR(-1, "服务器异常"),

    USER_EXIST(1, "用户已存在"),

    PARAM_ERROR(2, "输入参数错误"),

    USER_NOT_EXIST(3, "用户不存在"),

    USER_NOT_LOGIN(4, "用户未登录"),

    USERNAME_OR_PASSWORD_ERROR(5, "用户名或密码错误"),

    API_NOT_EXIST(10, "api不存在"),

    PERSON_EXIST(6, "用户id已存在"),

    PERSON_ID_OR_NAME_ERROR(7, "用户id或name错误"),
    ;


    private Integer code;

    private String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
