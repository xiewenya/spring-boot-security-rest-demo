package com.baojinsuo.common;

import com.baojinsuo.base.BaseModel;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ReflectUtils {

    /**
     * 得到指定类型的指定位置的泛型实参
     *
     * @param clazz
     * @param index
     * @param <T>
     * @return
     */
    public static <T> Class<T> findParametrisedType(Class<?> clazz, int index) {
        Type parametrisedType = clazz.getGenericSuperclass();
        //CGLUB subclass target object(泛型在父类上)
        if (!(parametrisedType instanceof ParameterizedType)) {
            parametrisedType = clazz.getSuperclass().getGenericSuperclass();
        }
        if (!(parametrisedType instanceof ParameterizedType)) {
            return null;
        }
        Type[] actualTypeArguments = ((ParameterizedType) parametrisedType).getActualTypeArguments();
        if (actualTypeArguments == null || actualTypeArguments.length == 0) {
            return null;
        }
        return (Class<T>) actualTypeArguments[index];
    }

    @SuppressWarnings("unchecked")
    public static <T> String getGenericName(Class<T> clazz, Integer index)
    {
        return ((Class<T>) ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments()[index]).getSimpleName();
    }

    public static String getClassName(Class clazz){
        return clazz.getSimpleName();
    }

    public static Field getField(Object source, String name) {
        try {
            Field field = source.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    public static <T extends BaseModel> Field[] getDeclaredFields(T target) {
        return target.getClass().getDeclaredFields();
    }
}