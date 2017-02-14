package com.qq.demo.ui.viewutils;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/3 18:56
 * 描 述 ：注解注入或控制反转
 * 修订历史 ：
 * ============================================================
 **/
public class ViewUtils {
    public static void inject(Activity activity) throws IllegalAccessException {
        bindView(activity);
    }

    private static void bindView(Activity activity) throws IllegalAccessException {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields){
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null){
                int resId = viewInject.value();
                View view = activity.findViewById(resId);
                field.setAccessible(true);
                field.set(activity,view);
            }
        }
    }

    public static void onClick(final Activity activity){
        Method[] methods = activity.getClass().getDeclaredMethods();
        for (final Method method : methods){
            Onclick onclick = method.getAnnotation(Onclick.class);
            if (onclick != null){
                int resId = onclick.value();
                final View view = activity.findViewById(resId);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        method.setAccessible(true);
                        try {
                            method.invoke(activity,view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
