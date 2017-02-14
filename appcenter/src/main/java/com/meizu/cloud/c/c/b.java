package com.meizu.cloud.c.c;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class b {
    private static Object a(Class<?> objClass, Object obj, String methodName, Object[] params) throws Exception {
        if (params == null || params.length == 0) {
            Method method = objClass.getMethod(methodName, new Class[0]);
            method.setAccessible(true);
            return method.invoke(obj, new Object[0]);
        }
        method = objClass.getMethod(methodName, a(params));
        method.setAccessible(true);
        return method.invoke(obj, params);
    }

    public static Object a(String className, String methodName, Object[] params) throws Exception {
        Class objClass = Class.forName(className);
        return a(objClass, (Object) objClass, methodName, params);
    }

    private static Object a(Class<?> objClass, Object obj, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        if (params == null || params.length == 0) {
            Method method = objClass.getMethod(methodName, new Class[0]);
            method.setAccessible(true);
            return method.invoke(obj, new Object[0]);
        }
        method = objClass.getMethod(methodName, paramsTypes);
        method.setAccessible(true);
        return method.invoke(obj, params);
    }

    public static Object a(String className, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        Class<?> objClass = Class.forName(className);
        return a(objClass, objClass, methodName, paramsTypes, params);
    }

    private static Class<?>[] a(Object[] params) {
        Class<?>[] paramsType = new Class[params.length];
        for (int i = 0; i < paramsType.length; i++) {
            paramsType[i] = params[i].getClass();
        }
        return paramsType;
    }

    public static Object a(Object desObj, Class<?> desClass, String fieldName) throws NoSuchFieldException {
        if (desObj == null || desClass == null || fieldName == null) {
            throw new IllegalArgumentException("parameter can not be null!");
        }
        try {
            Field field = desClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(desObj);
        } catch (Exception e) {
            throw new NoSuchFieldException(fieldName);
        }
    }

    private static Object b(Object desObj, Class<?> rootClass, String fieldName) throws NoSuchFieldException {
        Class desClass = rootClass;
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
            Class<?> objClass = Class.forName(className);
            return b(objClass, objClass, fieldName);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("className not found");
        }
    }
}
