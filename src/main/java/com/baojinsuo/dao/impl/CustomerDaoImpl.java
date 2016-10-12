package com.baojinsuo.dao.impl;

import com.baojinsuo.base.BaseDaoImpl;
import com.baojinsuo.common.SafeUtils;
import com.baojinsuo.dao.CustomerDao;
import com.baojinsuo.model.Customer;
import com.baojinsuo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by bresai on 2016/10/2.
 */
@Component
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerDaoImpl(CustomerRepository customerRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getByPrincipal(String principal){
        Customer customer = getByUsername(principal);
        customer = SafeUtils.isNotNull(customer)? customer : getByMobile(principal);
        if (SafeUtils.isNull(customer)){
            throw new UsernameNotFoundException("not_found");
        }
        return customer;
    }

    @Override
    public Customer getByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public Customer getByMobile(String mobile) {
        return customerRepository.findByMobile(mobile);
    }
}
