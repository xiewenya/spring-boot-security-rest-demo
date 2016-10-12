package com.baojinsuo.controller.request;

import com.baojinsuo.base.BaseRequestBean;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by bresai on 2016/10/10.
 */
public class PasswordChangeModel extends BaseRequestBean {
    @NotNull
    @Length(min = 6, max = 20, message="{error.password.length}")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)$", message = "{error.password}")
    private String oldPassword;

    @NotNull
    @Length(min = 6, max = 20, message="{error.password.length}")
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$)$", message = "{error.password}")
    private String newPassword;

    public PasswordChangeModel(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public PasswordChangeModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
