package com.baojinsuo.service;

import com.baojinsuo.controller.request.PasswordResetModel;
import com.baojinsuo.Security.UserContext;
import com.baojinsuo.controller.request.LoginModel;
import com.baojinsuo.controller.request.PasswordChangeModel;
import com.baojinsuo.controller.request.RegisterModel;
import com.baojinsuo.model.Customer;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * Created by bresai on 2016/10/3.
 */
public interface CustomerService extends UserDetailsService {

    void checkDuplicate(String type, String value) throws NoSuchMethodException;

    Customer register(RegisterModel model);

    Map login(LoginModel model, HttpServletRequest request);

    void changePassword(PasswordChangeModel model, UserContext customer, HttpServletRequest request);

    void resetPassword(PasswordResetModel model);
}
