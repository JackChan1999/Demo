package com.meizu.cloud.c.b.a;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class b {
    public static Object a(Object obj, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        return a(obj.getClass(), obj, methodName, paramsTypes, params);
    }

    private static Object a(Class<?> objClass, Object obj, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        if (params == null || params.length == 0) {
            Method method = c.a().a(objClass, methodName, new Class[0]);
            method.setAccessible(true);
            return method.invoke(obj, new Object[0]);
        }
        method = c.a().a(objClass, methodName, paramsTypes);
        method.setAccessible(true);
        return method.invoke(obj, params);
    }

    public static Object a(Object desObj, Class<?> desClass, String fieldName) throws NoSuchFieldException {
        if (desObj == null || desClass == null || fieldName == null) {
            throw new IllegalArgumentException("parameter can not be null!");
        }
        try {
            Field field = c.a().b(desClass, fieldName);
            field.setAccessible(true);
            return field.get(desObj);
        } catch (Exception e) {
            throw new NoSuchFieldException(fieldName);
        }
    }

    public static Object a(Object desObj, String fieldName) throws NoSuchFieldException {
        if (desObj != null && fieldName != null) {
            return b(desObj, desObj.getClass(), fieldName);
        }
        throw new IllegalArgumentException("parameter can not be null!");
    }

    private static Object b(Object desObj, Class<?> rootClass, String fieldName) throws NoSuchFieldException {
        Class<?> desClass = rootClass;
        while (desClass != null) {
            try {
                return a(desObj, desClass, fieldName);
            } catch (NoSuchFieldException e) {
                try {
                    desClass = desClass.getSuperclass();
                } catch (Exception e2) {
                    desClass = null;
                }
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    public static Object a(String className, String fieldName) throws NoSuchFieldException {
        if (className == null || fieldName == null) {
            throw new IllegalArgumentException("parameter can not be null!");
        }
        try {
            Class<?> objClass = c.a().a(className);
            return b(objClass, objClass, fieldName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("className not found");
        }
    }
}
