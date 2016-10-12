package com.baojinsuo.common;

import com.baojinsuo.base.ResponseCode;
import com.baojinsuo.common.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by bresai on 2016/10/3.
 */
public class ResponseUtils {

    public static ResponseEntity restResponse(String code, String message, HttpStatus status){
        return new ResponseEntity<>(
                new CodeMessageJson(code, message), status);
    }

    public static ResponseEntity restResponse(ResponseCode code){
        return new ResponseEntity<>(
                new CodeMessageJson(code), HttpStatus.OK);
    }

    public static ResponseEntity restResponse(ResponseCode code, HttpStatus status){
        return new ResponseEntity<>(
                new CodeMessageJson(code), status);
    }

    public static ResponseEntity restResponse(Object data, ResponseCode code){
        return restResponse(data, code.getCode(), code.getDesc(), HttpStatus.OK);
    }

    public static ResponseEntity restResponse(Object data, ResponseCode code, HttpStatus status){
        return restResponse(data, code.getCode(), code.getDesc(), status);
    }

    public static ResponseEntity restResponse(Object data, String code, String message, HttpStatus status){
        return new ResponseEntity<>(
                new CodeMessageJson(data,code, message), status);
    }

    public static ResponseEntity restResponse(Object data, Object page, String code, String message, HttpStatus status){

        return new ResponseEntity<>(
                new CodeMessageJson(data, page, code, message), status);
    }

    public static ResponseEntity restResponse(BaseException exception){
        return new ResponseEntity<>(
                new CodeMessageJson(exception.getCode(), exception.getMessage()), exception.getStatus());
    }

}

