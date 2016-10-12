package com.baojinsuo.controller.request;

import com.baojinsuo.base.BaseRequestBean;
import com.baojinsuo.common.SafeUtils;
import com.baojinsuo.common.validator.AtLeastOneNotEmpty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by bresai on 2016/10/9.
 */

@AtLeastOneNotEmpty(first = "username", second = "mobile")
public class LoginModel extends BaseRequestBean {

    @Length(min = 3, max = 30)
    private String username;

    private String mobile;

    @NotNull
    @Length(min = 6, max = 20, message="{error.password.length}")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)$", message = "{error.password}")
    private String password;

    public LoginModel() {
    }

    public LoginModel(String username, String mobile, String password) {
        this.username = username;
        this.mobile = mobile;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getMobile() {
        return mobile;
    }

    public String getPassword() {
        return password;
    }

    public String getPrincipal(){
        return SafeUtils.isNotNull(getUsername())? getUsername() : getMobile();
    }
}
