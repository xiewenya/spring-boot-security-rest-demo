package com.baojinsuo.base;

import com.baojinsuo.common.exception.ArgumentException;
import com.baojinsuo.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

import static com.baojinsuo.common.ResponseUtils.restResponse;

/**
 * Created by bresai on 2016/10/9.
 */
@ControllerAdvice
public class ErrorController {

    private final MessageSource messageSource;

    @Autowired
    public ErrorController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 所有业务上的错误返回
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Object handleBaseException(HttpServletRequest request, BaseException e) {
        e.printStackTrace();
        return restResponse(
                e.getCode(),
                e.getMessage(),
                e.getStatus()
        );
    }

    /**
     * 所有spring security抛出的异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(AuthenticationException.class)
    public Object handleException(HttpServletRequest request, AuthenticationException e) {
        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException){
            return restResponse(ResponseCode.login_failed, HttpStatus.UNAUTHORIZED);
        }
        e.printStackTrace();
        return restResponse("error", e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 所有输入参数错误异常
     * @param e
     * @return
     */
    @ExceptionHandler(ArgumentException.class)
    public Object handleException(HttpServletRequest request, ArgumentException e) {
        e.printStackTrace();
        return restResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationError(HttpServletRequest request, MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        String messages =  processFieldErrors(fieldErrors);
        return restResponse(ResponseCode.validation_code_not_correct.getCode(), messages, HttpStatus.BAD_REQUEST);
    }

    private String processFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.size() > 0 ? resolveLocalizedErrorMessage(fieldErrors.get(0)): "";
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        return messageSource.getMessage(fieldError, currentLocale);
    }
}
