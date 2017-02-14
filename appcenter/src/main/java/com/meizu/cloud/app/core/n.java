package com.meizu.cloud.app.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alibaba.fastjson.JSON;
import com.meizu.cloud.a.c;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class n {
    private static int a = 0;
    private static int b = 1;
    private static int c = 2;
    private static int d = 3;
    private Context e;
    private List<Pair<String, Integer>> f;
    private List<SubmitAppInfo> g;
    private boolean h = false;
    private boolean i;
    private int j;
    private String k;

    public n(Context context) {
        this.e = context;
    }

    public boolean a() {
        if (h() == null) {
            return false;
        }
        e();
        return this.i;
    }

    public String b() {
        e();
        return this.k;
    }

    public int c() {
        e();
        return this.j;
    }

    private void e() {
        if (!this.h) {
            this.h = true;
            this.g = f();
            this.f = i.a(this.e, 2);
            String oldAccount = g();
            String curAccount = h();
            boolean notAdd = false;
            if (!(TextUtils.isEmpty(curAccount) || curAccount.equals(oldAccount))) {
                Log.w("SubmitHistory", "account change, set to not add");
                this.g = null;
                notAdd = true;
            }
            Object submitList = new ArrayList();
            if (this.g == null) {
                if (notAdd) {
                    this.j = d;
                } else {
                    this.j = a;
                }
                for (Pair info : this.f) {
                    submitList.add(new SubmitAppInfo(info, 1));
                }
            } else {
                int i;
                this.j = b;
                Set<Integer> historySet = new HashSet();
                int historySize = this.g.size();
                for (Pair localInfo : this.f) {
                    boolean found = false;
                    for (i = 0; i < historySize; i++) {
                        if (((SubmitAppInfo) this.g.get(i)).isSame(localInfo)) {
                            found = true;
                            historySet.add(Integer.valueOf(i));
                            break;
                        }
                    }
                    if (!found) {
                        submitList.add(new SubmitAppInfo(localInfo, 1));
                    }
                }
                for (i = 0; i < historySize; i++) {
                    if (!historySet.contains(Integer.valueOf(i))) {
                        submitList.add(new SubmitAppInfo((SubmitAppInfo) this.g.get(i), 0));
                    }
                }
            }
            if (submitList.size() == 0) {
                this.i = false;
            } else {
                this.i = true;
                this.k = JSON.toJSONString(submitList, true);
            }
            if (this.i) {
                Log.d("SubmitHistory", "submit type : " + this.j);
                Log.d("SubmitHistory", "submit str  : " + this.k);
                return;
            }
            Log.w("SubmitHistory", "no app new to submit.");
        }
    }

    private List<SubmitAppInfo> f() {
        String historyStr = i();
        if (historyStr == null) {
            return null;
        }
        List<SubmitAppInfo> submitAppInfos = JSON.parseArray(historyStr, SubmitAppInfo.class);
        List<SubmitAppInfo> stayRemove = new ArrayList();
        for (SubmitAppInfo submitAppInfo : submitAppInfos) {
            if (i.a(submitAppInfo.package_name, this.e)) {
                stayRemove.add(submitAppInfo);
            }
        }
        submitAppInfos.removeAll(stayRemove);
        return submitAppInfos;
    }

    public void d() {
        final List<SubmitAppInfo> list = new ArrayList();
        for (Pair info : this.f) {
            list.add(new SubmitAppInfo(info, 1));
        }
        new Thread(new Runnable(this) {
            final /* synthetic */ n b;

            public void run() {
                this.b.a(JSON.toJSONString(list, true));
                this.b.b(this.b.h());
            }
        }).start();
    }

    private String g() {
        return j();
    }

    private String h() {
        return c.a(this.e);
    }

    private String i() {
        return k().getString("history_str", null);
    }

    private void a(String str) {
        Editor editor = k().edit();
        if (str == null) {
            editor.remove("history_str");
        } else {
            editor.putString("history_str", str);
        }
        editor.apply();
    }

    private String j() {
        return k().getString("flyme_name", null);
    }

    private void b(String account) {
        Editor editor = k().edit();
        if (account == null) {
            editor.remove("flyme_name");
        } else {
            editor.putString("flyme_name", account);
        }
        editor.apply();
    }

    private SharedPreferences k() {
        return this.e.getSharedPreferences("submit_app_hostory", 0);
    }
}
