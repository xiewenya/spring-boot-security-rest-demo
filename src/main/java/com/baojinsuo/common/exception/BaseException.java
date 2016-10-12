package com.baojinsuo.common.exception;

import com.baojinsuo.base.ResponseCode;
import org.springframework.http.HttpStatus;

/**
 * Created by bresai on 16/6/30.
 */
public class BaseException extends RuntimeException {

    private String code;
    private String message;

    private HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


    public BaseException() {
    }

    public BaseException(String code, String message) {
        this(code, message, HttpStatus.BAD_REQUEST);
    }

    public BaseException(ResponseCode code) {
        this(code, HttpStatus.BAD_REQUEST);
    }

    public BaseException(ResponseCode code, HttpStatus status) {
        this(code.getCode(), code.getDesc(), status);
    }

    public BaseException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
