package com.baojinsuo.controller;

import com.baojinsuo.Security.UserContext;
import com.baojinsuo.base.BaseController;
import com.baojinsuo.base.ResponseCode;
import com.baojinsuo.controller.request.LoginModel;
import com.baojinsuo.controller.request.PasswordChangeModel;
import com.baojinsuo.controller.request.PasswordResetModel;
import com.baojinsuo.controller.request.RegisterModel;
import com.baojinsuo.model.Customer;
import com.baojinsuo.service.CustomerService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

import static com.baojinsuo.common.ResponseUtils.restResponse;

/**
 * Created by bresai on 2016/10/2.
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController extends BaseController<Customer>{

    private final CustomerService customerService;

    @Value("${baojinsuo.token.header}")
    private String tokenHeader;

    @Autowired
    public AuthController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody RegisterModel model,
                                   HttpServletRequest request){
        customerService.register(model);
        return restResponse(ResponseCode.success);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@Valid @RequestBody LoginModel model,
                                HttpServletRequest request) {
        Map token = customerService.login(model, request);
        return restResponse(token, ResponseCode.success);
    }

    /**
     * 验证token是否有效
     * @param request
     * @return
     */
    @RequestMapping(value = "/token/check", method = RequestMethod.POST)
    public ResponseEntity tokenCheck(HttpServletRequest request) {
        return restResponse(ResponseCode.success);
    }

    //by using @AuthenticationPrincipal annotation the UserContext is automatically mapped
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public ResponseEntity changePassword(@Valid @RequestBody PasswordChangeModel model,
                                         @AuthenticationPrincipal UserContext customer,
                                         HttpServletRequest request) {
        customerService.changePassword(model, customer, request);
        return restResponse(ResponseCode.success);
    }

    @RequestMapping(value = "/password/reset/token", method = RequestMethod.POST)
    public ResponseEntity getResetPasswordToken(@RequestParam String mobile,
                                        HttpServletRequest request) {
//        customerService.resetPassword(mobile);
        return restResponse(ResponseCode.success);
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetModel model,
                                        HttpServletRequest request) {
        customerService.resetPassword(model);
        return restResponse(ResponseCode.success);
    }



}
