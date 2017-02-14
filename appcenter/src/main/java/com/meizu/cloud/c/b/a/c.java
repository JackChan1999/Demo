package com.meizu.cloud.c.b.a;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class c {
    public static HashMap<String, a> a;
    public static HashMap<String, Method> b;
    public static HashMap<String, Field> c;
    private final String d;

    private static class a {
        private static final c a = new c();
    }

    private c() {
        this.d = "ReflectionCache";
        a = new HashMap();
        b = new HashMap();
        c = new HashMap();
    }

    public static final c a() {
        return a.a;
    }

    private void a(String key, a classInfo) {
        a.put(key, classInfo);
    }

    private a b(String key) {
        return (a) a.get(key);
    }

    public Class<?> a(String className) throws ClassNotFoundException {
        return a(className, Boolean.valueOf(true));
    }

    public Class<?> a(String className, Boolean isCached) throws ClassNotFoundException {
        if (!isCached.booleanValue()) {
            return Class.forName(className);
        }
        a classInfoFromCache = b(className);
        if (classInfoFromCache != null) {
            return classInfoFromCache.a;
        }
        Class<?> c = Class.forName(className);
        a(className, new a(c, className));
        return c;
    }

    public Method a(Class<?> objClass, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        Boolean hasClassInfo;
        a classInfoFromCache = b(objClass.getName());
        if (classInfoFromCache != null) {
            hasClassInfo = Boolean.valueOf(true);
        } else {
            hasClassInfo = Boolean.valueOf(false);
        }
        String methodKey = methodName;
        for (Class<?> c : parameterTypes) {
            methodKey = methodKey + c.toString();
        }
        if (hasClassInfo.booleanValue()) {
            Method methodFromCache = classInfoFromCache.a(methodKey);
            if (methodFromCache != null) {
                return methodFromCache;
            }
            Method method = objClass.getMethod(methodName, parameterTypes);
            classInfoFromCache.a(methodKey, method);
            return method;
        }
        Method cacheMethod = (Method) b.get(methodKey);
        if (cacheMethod != null) {
            return cacheMethod;
        }
        method = objClass.getMethod(methodName, parameterTypes);
        b.put(methodKey, method);
        return method;
    }

    public Field a(Class<?> objClass, String fieldName) throws NoSuchFieldException {
        Boolean hasClassInfo;
        a classInfoFromCache = b(objClass.getName());
        if (classInfoFromCache != null) {
            hasClassInfo = Boolean.valueOf(true);
        } else {
            hasClassInfo = Boolean.valueOf(false);
        }
        if (hasClassInfo.booleanValue()) {
            Field fieldFromCache = classInfoFromCache.b(fieldName);
            if (fieldFromCache != null) {
                return fieldFromCache;
            }
            Field field = objClass.getField(fieldName);
            classInfoFromCache.a(fieldName, field);
            return field;
        }
        Field cachedField = (Field) c.get(fieldName);
        if (cachedField != null) {
            return cachedField;
        }
        field = objClass.getField(fieldName);
        c.put(fieldName, field);
        return field;
    }

    public Field b(Class<?> objClass, String fieldName) throws NoSuchFieldException {
        Boolean hasClassInfo;
        a classInfoFromCache = b(objClass.getName());
        if (classInfoFromCache != null) {
            hasClassInfo = Boolean.valueOf(true);
        } else {
            hasClassInfo = Boolean.valueOf(false);
        }
        if (hasClassInfo.booleanValue()) {
            Field fieldFromCache = classInfoFromCache.b(fieldName);
            if (fieldFromCache != null) {
                return fieldFromCache;
            }
            Field field = objClass.getDeclaredField(fieldName);
            classInfoFromCache.a(fieldName, field);
            return field;
        }
        Field cachedField = (Field) c.get(fieldName);
        if (cachedField != null) {
            return cachedField;
        }
        field = objClass.getDeclaredField(fieldName);
        c.put(fieldName, field);
        return field;
    }
}
