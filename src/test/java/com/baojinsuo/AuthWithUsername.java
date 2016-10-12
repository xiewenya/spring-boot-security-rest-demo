package com.baojinsuo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.baojinsuo.controller.request.LoginModel;
import com.baojinsuo.controller.request.PasswordChangeModel;
import com.baojinsuo.controller.request.RegisterModel;
import com.baojinsuo.dao.CustomerDao;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthWithUsername extends BaseTest{

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private String username = "xuewenyao";
    @Test
    public void testA_RegisterWithUsername() throws Exception {
        RegisterModel model = new RegisterModel(username, null, "11111111");
        entity = getEntity(model);
        ResponseEntity<String> response = template.postForEntity("/auth/register", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        // username already registered
        response = template.postForEntity("/auth/register", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
        assertThat(getBody(response).get("code")).isEqualTo("duplicated");
    }

    @Test
    public void testB_LoginWithWrongUsername() throws IOException {
        LoginModel model = new LoginModel("xuewenya", null, "11111111");
        entity = getEntity(model);
        ResponseEntity<String> response = template.postForEntity("/auth/login", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
        Map<String, Object> body = getBody(response);
        assertThat(getCode(body)).isEqualToIgnoringCase("login_failed");
    }

    @Test
    public void testC_LoginWithWrongPassword() throws IOException {
        LoginModel model = new LoginModel(username, null, "22222222");
        entity = getEntity(model);
        ResponseEntity<String> response = template.postForEntity("/auth/login", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
        Map<String, Object> body = getBody(response);
        assertThat(getCode(body)).isEqualToIgnoringCase("login_failed");
    }

    @Test
    public void testD_LoginWithUsername() throws IOException {
        LoginModel model = new LoginModel(username, null, "11111111");
        entity = getEntity(model);
        ResponseEntity<String> response = template.postForEntity("/auth/login", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Map body = getBody(response);

        logger.info(getData(body).get("token").toString());
        assertThat(getData(body).get("token").toString()).isNotNull();
        token = getData(body).get("token").toString();
    }

    @Test
    public void testE_ChangePasswordWithWrongOldPassword() throws JsonProcessingException {
        PasswordChangeModel model = new PasswordChangeModel("11111122", "22222222");
        entity = getEntity(model);
        ResponseEntity<String> response = template.postForEntity("/auth/password/change", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void testF_ChangePassword() throws JsonProcessingException {
        String newPassowrd = "22222222";
        PasswordChangeModel model = new PasswordChangeModel("11111111", newPassowrd);
        entity = getEntity(model);
        String encryptedOldPassword = customerDao.getByUsername(username).getPassword();
        ResponseEntity<String> response = template.postForEntity("/auth/password/change", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(customerDao.getByUsername(username).getPassword()).isNotEqualTo(encryptedOldPassword);

        LoginModel loginModel = new LoginModel(username, null, newPassowrd);
        entity = getEntity(loginModel);
        response = template.postForEntity("/auth/login", entity, String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

    }

//    @Test
//    public void testResetPassword
//
//    @Test
//    public void testChangePasswordWithWrongPassword() throws JsonProcessingException {
//
//        PasswordChangeModel model = new PasswordChangeModel("111111A11", "13817802488");
//        entity = getEntity(model);
//        ResponseEntity<String> response = template.postForEntity("/auth/password/change", entity, String.class);
//
//        assertThat(response.getStatusCodeValue()).isEqualTo(200);
//    }

}
