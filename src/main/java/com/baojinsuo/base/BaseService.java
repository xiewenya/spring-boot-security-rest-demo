package com.baojinsuo.base;

/**
 * Created by bresai on 2016/10/3.
 */
public interface BaseService<T extends BaseModel> {
    void copyProperties(T target, Object source) throws NoSuchFieldException;
}
