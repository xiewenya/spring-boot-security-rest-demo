package com.baojinsuo.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by bresai on 2016/10/3.
 */
@Constraint(validatedBy = AtLeastOneNotEmptyValidator.class)
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
public @interface AtLeastOneNotEmpty {
    String message() default "{error.atLeastOneNotNull}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * @return The first field
     */
    String first();

    /**
     * @return The second field
     */
    String second();
}