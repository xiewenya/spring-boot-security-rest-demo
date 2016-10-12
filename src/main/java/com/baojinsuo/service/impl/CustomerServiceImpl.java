package com.baojinsuo.service.impl;

import com.baojinsuo.Security.UserContext;
import com.baojinsuo.base.BaseServiceImpl;
import com.baojinsuo.common.TokenUtils;
import com.baojinsuo.controller.MessageProperties;
import com.baojinsuo.controller.request.PasswordResetModel;
import com.baojinsuo.controller.request.RegisterModel;
import com.baojinsuo.dao.CustomerDao;
import com.baojinsuo.service.CustomerService;
import com.baojinsuo.base.ResponseCode;
import com.baojinsuo.common.SafeUtils;
import com.baojinsuo.common.exception.BaseException;
import com.baojinsuo.controller.request.LoginModel;
import com.baojinsuo.controller.request.PasswordChangeModel;
import com.baojinsuo.model.Customer;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.capitalize;

// TODO: 2016/10/10 add cache functions
@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> implements CustomerService {

    @Autowired
	private CustomerDao customerDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Customer customer = customerDao.getByPrincipal(username);
		return new UserContext(customer);
	}

    @Override
    public void checkDuplicate(String type, String value){
        try {
            Method method = customerDao.getClass().getMethod("getBy" + capitalize(type), String.class);
            if (SafeUtils.isNotEmpty(value) &&
                    customerDao.checkExists(method.invoke(customerDao, value), false)) {
                throw new BaseException(ResponseCode.duplicated.getCode(), MessageProperties.get("message." + type) + ResponseCode.duplicated.getDesc());
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
            throwBaseException(e);
        }
    }

    @Override
    public Customer register(RegisterModel model) {
        checkDuplicate("username", model.getUsername());
        checkDuplicate("mobile", model.getMobile());

        String[] roles = {"ROLE_USER"};

        Customer customer = new Customer();
        copyProperties(customer, model);

        customer.setRoles(roles);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return customerDao.save(customer);
    }

    @Override
    public Map login(LoginModel model, HttpServletRequest request) {
        String principal = model.getPrincipal();

        // Perform the authentication

        Authentication authentication = authentication(principal, model.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-authentication so we can generate token
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);
        Device device = DeviceUtils.getCurrentDevice(request);
        return packToken(tokenUtils.generateToken(userDetails, device));
    }

    @Override
    public void changePassword(PasswordChangeModel model, UserContext userContext, HttpServletRequest request) {
        // Perform the authentication
        Customer customer = userContext.getCustomer();
        // since TokenFilter do not verified password, we need to authenticate again with old password
        Authentication authentication = authentication(customer.getPrincipal(), model.getOldPassword());
        if (! authentication.isAuthenticated()){
            throw new BaseException(ResponseCode.login_failed, HttpStatus.UNAUTHORIZED);
        }
        customer.setPassword(passwordEncoder.encode(model.getNewPassword()));
        customerDao.save(customer);
    }

    @Override
    public void resetPassword(PasswordResetModel model) {

    }

    private Authentication authentication(String principal, String password){
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(principal, password)
        );
    }

    @SuppressWarnings("unchecked")
    private Map packToken(String token) {
        Map tokenMap = new HashMap();
        tokenMap.put("token", token);
        return tokenMap;
    }


}
