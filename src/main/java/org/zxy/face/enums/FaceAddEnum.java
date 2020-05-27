package org.zxy.face.enums;

import lombok.Getter;

@Getter
public enum FaceAddEnum {

    SUCCESS(0, "成功"),

    IMAGE_ERROR(1, "图像错误：格式错误、不存在人脸、人脸数不准确"),

    REQUEST_ERROR(2, "调用方法不正确"),
    ;

    private Integer code;

    private String message;

    FaceAddEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
