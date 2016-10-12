package com.baojinsuo.base;

import java.util.List;

/**
 * Created by bresai on 2016/10/2.
 */
public interface BaseDao<T extends BaseModel> {

    T findOne(Long var1);

    boolean checkExists(Object object);

    boolean checkExists(Object object, Boolean isThrow);

    List<T> findAll();

    T save(T model);
}
