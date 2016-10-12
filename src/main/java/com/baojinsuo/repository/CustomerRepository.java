package com.baojinsuo.repository;

import com.baojinsuo.base.BaseRepository;
import com.baojinsuo.model.Customer;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by bresai on 2016/10/2.
 */

@Transactional
public interface CustomerRepository extends BaseRepository<Customer> {
    Customer findByUsername(String username);

    Customer findByMobile(String mobile);
}
