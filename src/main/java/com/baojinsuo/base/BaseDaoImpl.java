package com.baojinsuo.base;

import com.baojinsuo.common.ReflectUtils;
import com.baojinsuo.common.SafeUtils;
import com.baojinsuo.common.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by bresai on 2016/10/2.
 */
public class BaseDaoImpl<T extends BaseModel> implements BaseDao<T> {
    protected final BaseRepository<T> repository;

    @Autowired
    public BaseDaoImpl(BaseRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T findOne(@NotNull Long id) {
        T object = (T) repository.findOne(id);
        checkExists(object);
        return object;
    }

    @Override
    public boolean checkExists(Object object){
        return checkExists(object, true);
    }

    @Override
    public boolean checkExists(Object object, Boolean isThrow){
        if (SafeUtils.isNull(object) && isThrow)
            throw new BaseException(
                    "object_not_found",
                    ReflectUtils.getGenericName(this.getClass(), 0) + "not found",
                    HttpStatus.BAD_REQUEST
            );
        else return SafeUtils.isNotNull(object);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T save(T model) {
        return repository.save(model);
    }
}
