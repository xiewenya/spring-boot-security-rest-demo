package com.baojinsuo.base;

import com.baojinsuo.common.exception.ArgumentException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by bresai on 16/5/25.
 */

public class BaseController<T extends Serializable> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final Validator validator;
    protected final ObjectMapper mapper = new ObjectMapper();

    public BaseController() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected Map model2Map(JsonNode node) {
        return mapper.convertValue(node, Map.class);
    }

    protected List model2List(JsonNode node) {
        return mapper.convertValue(node, List.class);
    }

    private <T> T map2Model(Map<String, Object> map, Class<T> clazz) {
        return mapper.convertValue(map, clazz);
    }

    protected <T> void runRequestValidation(Map<String, Object> map, Class<T> clazz) {

        Set<ConstraintViolation<T>> constraintViolations =
                validator.validate(map2Model(map, clazz));

        if (!constraintViolations.isEmpty()) {
            String errorMsg = "";
            for (ConstraintViolation<T> error : constraintViolations) {
                Path path = error.getPropertyPath();
                for (Path.Node node : path) {
                    errorMsg = errorMsg + " Column: " + node.getName()
                            + " Value: " + error.getInvalidValue();
                }
                errorMsg = errorMsg + " " + error.getMessage() + "\n";
            }
            throw new ArgumentException(errorMsg);
        }
    }
}
