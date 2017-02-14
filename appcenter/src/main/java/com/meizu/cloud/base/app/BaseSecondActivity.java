package com.meizu.cloud.base.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.o;
import android.view.MenuItem;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.fragment.p;
import com.meizu.cloud.b.a.a;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.base.app.BaseCommonActivity.b;

public class BaseSecondActivity extends BaseCommonActivity {
    private static String m = "fragment_name_key";
    private static int n = 0;
    private boolean o = false;

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != f.search_menu) {
            return super.onOptionsItemSelected(item);
        }
        a((Context) this, x.a((Context) this) ? "com.meizu.flyme.appcenter.fragment.AppSearchFragment" : "com.meizu.flyme.gamecenter.fragment.GameSearchFragment");
        if (this.k != null && this.k.size() > 0) {
            for (b listener : this.k) {
                listener.b();
            }
        }
        return true;
    }

    private static void a(Context context, String fragmentName) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(p.SHOW_KEYBOARD, true);
        bundle.putString(m, fragmentName);
        a(context, fragmentName, bundle);
        if (context instanceof FragmentActivity) {
            ((Activity) context).overridePendingTransition(a.mz_search_activity_open_enter_alpha, a.mz_search_activity_open_exit_alpha);
        }
    }

    public static void a(Context context, Fragment fragment) {
        a(context, fragment.getClass(), fragment.getArguments());
    }

    public static void a(Context context, Class<? extends Fragment> clazz, Bundle args) {
        a(context, clazz.getName(), args);
    }

    public static void a(Context context, String fragmentName, Bundle args) {
        Intent intent = new Intent(context, BaseSecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(m, fragmentName);
        if (args != null) {
            bundle.putAll((Bundle) args.clone());
        }
        intent.putExtras(bundle);
        if (bundle.getBoolean(p.CLEAR_TASK, false)) {
            intent.setFlags(67108864);
        }
        context.startActivity(intent);
        if (n > 10 && (context instanceof FragmentActivity)) {
            ((FragmentActivity) context).finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(g.base_main_activity);
        setTitle("");
        g().b(true);
        if (savedInstanceState == null) {
            Fragment fragment = null;
            try {
                Bundle bundle = getIntent().getExtras();
                String className = bundle.getString(m);
                bundle.remove(m);
                fragment = (Fragment) Class.forName(className).newInstance();
                fragment.setArguments(bundle);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fragment != null) {
                o fragmentTransaction = f().a();
                fragmentTransaction.a(f.main_container, fragment);
                fragmentTransaction.c();
                if (fragment instanceof p) {
                    this.o = true;
                }
            } else {
                finish();
            }
            n++;
        }
    }

    public void finish() {
        super.finish();
        if (this.o) {
            overridePendingTransition(a.mz_search_activity_close_enter_alpha, a.mz_search_activity_close_exit_alpha);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        n--;
    }
}
