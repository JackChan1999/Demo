package com.meizu.flyme.launchermenu;

import android.content.Context;
import defpackage.aji;
import java.util.ArrayList;

public class LauncherMenu {
    public static void add(Context context, ArrayList arrayList) {
        try {
            aji.a(context).a(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete(Context context, String... strArr) {
        try {
            aji.a(context).a(strArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAll(Context context) {
        try {
            aji.a(context).a();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Context context, ArrayList arrayList) {
        try {
            aji.a(context).b(arrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList query(Context context) {
        try {
            return aji.a(context).b();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
