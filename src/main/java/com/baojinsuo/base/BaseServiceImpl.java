package com.baojinsuo.base;

import com.baojinsuo.common.ReflectUtils;
import com.baojinsuo.common.SafeUtils;
import com.baojinsuo.common.exception.BaseException;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by bresai on 2016/10/3.
 */
public class BaseServiceImpl<T extends BaseModel> implements BaseService<T> {

    @Override
    public void copyProperties(T target, Object source) {
        try {
            Field[] targetFields = ReflectUtils.getDeclaredFields(target);

            for (Field field : targetFields){
                Field sourceField = ReflectUtils.getField(source, field.getName());
                if (SafeUtils.isNotNull(sourceField) && SafeUtils.isNotNull(sourceField.get(source))){
                    BeanUtils.setProperty(target,field.getName(),sourceField.get(source));
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throwBaseException(e);
        }

    }

    public void throwBaseException(Exception e) {
        throw new BaseException(e.getClass().getSimpleName(), e.getMessage());
    }
}
