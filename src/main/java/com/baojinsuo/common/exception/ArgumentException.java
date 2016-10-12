package com.baojinsuo.common.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by bresai on 16/6/30.
 */
public class ArgumentException extends BaseException {
    public ArgumentException(String message) {
        super("argument_error", message, HttpStatus.BAD_REQUEST);
    }
}
