package org.zxy.face.exceptions;

import io.lettuce.core.RedisException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zxy.face.VO.ResponseVO;
import org.zxy.face.enums.ResponseEnum;

@ControllerAdvice
public class RunTimeExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVO notValidExceptionHandler() {
        return ResponseVO.error(ResponseEnum.PARAM_ERROR);
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVO userLoginExceptionHandler() {
        return ResponseVO.error(ResponseEnum.USER_NOT_LOGIN);
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseVO apiExceptionHandler() {
        return ResponseVO.error(ResponseEnum.API_NOT_EXIST);
    }

    @ExceptionHandler(JSONException.class)
    @ResponseBody
    public ResponseVO jsonExceptionHandler() {
        return ResponseVO.error(ResponseEnum.API_NOT_EXIST);
    }

    @ExceptionHandler(RedisException.class)
    @ResponseBody
    public ResponseVO redisExceptionHandler() {
        return ResponseVO.error(ResponseEnum.REDIS_ERROR);
    }
}
