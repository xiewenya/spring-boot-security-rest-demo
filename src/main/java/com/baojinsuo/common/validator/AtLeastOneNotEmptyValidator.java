package com.baojinsuo.common.validator;

import com.baojinsuo.common.SafeUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by bresai on 2016/10/3.
 */
public class AtLeastOneNotEmptyValidator implements
        ConstraintValidator<AtLeastOneNotEmpty, Object> {

    private String firstParameter;
    private String secondParameter;

    @Override
    public void initialize(final AtLeastOneNotEmpty constraintAnnotation) {
        firstParameter = constraintAnnotation.first();
        secondParameter = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            final Object firstObj = BeanUtils.getProperty(value, firstParameter);
            final Object secondObj = BeanUtils.getProperty(value, secondParameter);
            return ! bothNull(firstObj, secondObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean bothNull(Object object1, Object object2){
        return SafeUtils.isNull(object1) && SafeUtils.isNull(object2);
    }
}

