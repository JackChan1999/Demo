package com.meizu.f;

import android.os.Build.VERSION;
import android.util.ArrayMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class a {
    private static Map<String, Class> a;
    private static Map<String, Method> b;

    static {
        if (VERSION.SDK_INT >= 19) {
            a = new ArrayMap();
            b = new ArrayMap();
            return;
        }
        a = new HashMap();
        b = new HashMap();
    }

    public static Object a(Object obj, String methodName, Object[] params) throws Exception {
        return a(obj.getClass(), obj, methodName, params);
    }

    private static Object a(Class<?> objClass, Object obj, String methodName, Object[] params) throws Exception {
        Object invoke;
        Method method;
        if (params == null || params.length == 0) {
            synchronized (b) {
                method = (Method) b.get(methodName);
                if (method == null) {
                    method = objClass.getMethod(methodName, new Class[0]);
                    method.setAccessible(true);
                    b.put(methodName, method);
                }
                invoke = method.invoke(obj, new Object[0]);
            }
        } else {
            synchronized (b) {
                Class[] clazzes = a(params);
                StringBuilder sb = new StringBuilder(objClass.getName());
                sb.append('#');
                sb.append(methodName);
                sb.append(a(clazzes));
                sb.append("#bestmatch");
                String fullName = sb.toString();
                method = (Method) b.get(fullName);
                if (method == null) {
                    method = objClass.getMethod(methodName, clazzes);
                    method.setAccessible(true);
                    b.put(fullName, method);
                }
                invoke = method.invoke(obj, params);
            }
        }
        return invoke;
    }

    public static Object a(String className, String methodName, Object[] params) throws Exception {
        Class objClass = (Class) a.get(className);
        if (objClass == null) {
            objClass = Class.forName(className);
            a.put(className, objClass);
        }
        return a(objClass, (Object) objClass, methodName, params);
    }

    public static Object a(Object obj, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        return a(obj.getClass(), obj, methodName, paramsTypes, params);
    }

    private static Object a(Class<?> objClass, Object obj, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        Object invoke;
        Method method;
        if (params == null || params.length == 0) {
            synchronized (b) {
                method = (Method) b.get(methodName);
                if (method == null) {
                    method = objClass.getMethod(methodName, new Class[0]);
                    method.setAccessible(true);
                    b.put(methodName, method);
                }
                invoke = method.invoke(obj, new Object[0]);
            }
        } else {
            synchronized (b) {
                Class[] clazzes = paramsTypes;
                StringBuilder sb = new StringBuilder(objClass.getName());
                sb.append('#');
                sb.append(methodName);
                sb.append(a(clazzes));
                sb.append("#bestmatch");
                String fullName = sb.toString();
                method = (Method) b.get(fullName);
                if (method == null) {
                    method = objClass.getMethod(methodName, paramsTypes);
                    method.setAccessible(true);
                    b.put(fullName, method);
                }
                invoke = method.invoke(obj, params);
            }
        }
        return invoke;
    }

    public static Object a(String className, String methodName, Class<?>[] paramsTypes, Object[] params) throws Exception {
        Class<?> objClass = (Class) a.get(className);
        if (objClass == null) {
            objClass = Class.forName(className);
            a.put(className, objClass);
        }
        return a(objClass, objClass, methodName, paramsTypes, params);
    }

    private static Class<?>[] a(Object[] params) {
        Class<?>[] paramsType = new Class[params.length];
        for (int i = 0; i < paramsType.length; i++) {
            paramsType[i] = params[i].getClass();
        }
        return paramsType;
    }

    private static String a(Class<?>... clazzes) {
        StringBuilder sb = new StringBuilder("(");
        boolean first = true;
        for (Class<?> clazz : clazzes) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            if (clazz != null) {
                sb.append(clazz.getCanonicalName());
            } else {
                sb.append("null");
            }
        }
        sb.append(")");
        return sb.toString();
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
        Class<?> objClass = (Class) a.get(className);
        if (objClass == null) {
            try {
                objClass = Class.forName(className);
                a.put(className, objClass);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("className not found");
            }
        }
        return b(objClass, objClass, fieldName);
    }
}
