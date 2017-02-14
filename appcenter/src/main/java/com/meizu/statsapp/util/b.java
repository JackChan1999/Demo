package com.meizu.statsapp.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class b {
    private static Map<String, Object> a = new HashMap();
    private static Map<String, Class<?>> b = new HashMap();

    private static Object a(Class<?> objClass, Object obj, String methodName, Object[] params) throws Exception {
        String key = objClass.getName() + methodName;
        Object temp;
        if (params == null || params.length == 0) {
            temp = a.get(key);
            if (temp != null && (temp instanceof Method)) {
                return ((Method) temp).invoke(obj, new Object[0]);
            }
            Method method = objClass.getMethod(methodName, new Class[0]);
            method.setAccessible(true);
            a.put(key, method);
            return method.invoke(obj, new Object[0]);
        }
        Class<?>[] paramsClass = a(params);
        if (paramsClass != null) {
            for (Class<?> c : paramsClass) {
                key = key + c.getName();
            }
        }
        temp = a.get(key);
        if (temp != null && (temp instanceof Method)) {
            return ((Method) temp).invoke(obj, params);
        }
        method = objClass.getMethod(methodName, paramsClass);
        method.setAccessible(true);
        a.put(key, method);
        return method.invoke(obj, params);
    }

    public static Object a(String className, String methodName, Object[] params) throws Exception {
        Class<?> objClass;
        if (b.get(className) != null) {
            objClass = (Class) b.get(className);
        } else {
            objClass = Class.forName(className);
            b.put(className, objClass);
        }
        return a(objClass, objClass, methodName, params);
    }

    private static Class<?>[] a(Object[] params) {
        Class<?>[] paramsType = new Class[params.length];
        for (int i = 0; i < paramsType.length; i++) {
            paramsType[i] = params[i].getClass();
        }
        return paramsType;
    }
}
