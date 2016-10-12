package com.baojinsuo.dao;

import com.baojinsuo.base.BaseDao;
import com.baojinsuo.model.Customer;

/** This is domain DAO to access users. Kinda fake here. */

public interface CustomerDao extends BaseDao<Customer> {

    Customer getByPrincipal(String principal);

    public Customer getByUsername(String username);

    Customer getByMobile(String mobile);
}
