package com.meizu.gslb.a;

import android.util.Pair;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class a {
    private static String a(List<Pair<String, String>> list) {
        Collections.sort(list, new Comparator<Pair<String, String>>() {
            public int a(Pair<String, String> pair, Pair<String, String> pair2) {
                return pair.first == null ? 0 : ((String) pair.first).compareTo((String) pair2.first);
            }

            public /* synthetic */ int compare(Object obj, Object obj2) {
                return a((Pair) obj, (Pair) obj2);
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair pair : list) {
            stringBuilder.append((String) pair.first).append((String) pair.second);
        }
        return stringBuilder.toString();
    }

    public static boolean a(List<Pair<String, String>> list, List<Pair<String, String>> list2) {
        boolean z = list == null || list.size() == 0;
        boolean z2 = list2 == null || list2.size() == 0;
        return z == z2 ? !z ? list.size() == list2.size() ? a(list).equals(a(list2)) : false : true : false;
    }
}
