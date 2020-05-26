package org.zxy.face.VO;

import lombok.Data;
import org.zxy.face.enums.ResponseEnum;

@Data
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {

    private Integer code;

    private String message;

    private T data;

    private ResponseVO(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
    }

    private ResponseVO(ResponseEnum responseEnum, T data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public static ResponseVO success() {
        return new ResponseVO(ResponseEnum.SUCCESS);
    }

    public static <T> ResponseVO success(T data) {
        return new ResponseVO<>(ResponseEnum.SUCCESS, data);
    }

    public static ResponseVO error(ResponseEnum responseEnum) {
        return new ResponseVO(responseEnum);
    }
}
