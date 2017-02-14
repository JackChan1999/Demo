package com.meizu.cloud.download.c;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public abstract class a {
    public static final String[] ID_PROJECTION = new String[]{"_id"};
    @a(a = "_id")
    public long mId = 0;

    public interface b<T extends a> {
        T create();
    }

    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface a {
        String a();

        boolean b() default false;

        boolean c() default false;

        String d() default "";

        boolean e() default false;

        boolean f() default true;
    }

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface c {
        String a();
    }

    public void clear() {
        this.mId = 0;
    }
}
