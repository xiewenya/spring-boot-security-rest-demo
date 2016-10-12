package com.baojinsuo.controller.request;

import com.baojinsuo.base.BaseRequestBean;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by bresai on 2016/10/10.
 */
public class PasswordResetModel extends BaseRequestBean {

    @NotNull
    @Length(min = 6, max = 20, message="{error.password.length}")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)$", message = "{error.password}")
    private String password;

    public PasswordResetModel() {
    }

    public PasswordResetModel(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
