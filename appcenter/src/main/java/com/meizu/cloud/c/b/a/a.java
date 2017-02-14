package com.meizu.cloud.c.b.a;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class a {
    Class<?> a;
    HashMap<String, Method> b = new HashMap();
    HashMap<String, Field> c = new HashMap();

    public a(Class<?> clazz, String className) {
        this.a = clazz;
    }

    public void a(String key, Method method) {
        this.b.put(key, method);
    }

    public Method a(String key) {
        return (Method) this.b.get(key);
    }

    public void a(String key, Field field) {
        this.c.put(key, field);
    }

    public Field b(String key) {
        return (Field) this.c.get(key);
    }
}
