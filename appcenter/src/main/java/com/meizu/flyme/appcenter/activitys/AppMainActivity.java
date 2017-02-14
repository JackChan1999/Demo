package com.meizu.flyme.appcenter.activitys;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.o;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.alibaba.fastjson.asm.Opcodes;
import com.meizu.cloud.app.c.g;
import com.meizu.cloud.app.core.h;
import com.meizu.cloud.app.core.m.d;
import com.meizu.cloud.app.fragment.p;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.settings.a;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.base.app.BaseMainActivity;
import com.meizu.cloud.compaign.task.BaseTask;
import com.meizu.cloud.compaign.task.app.BaseAppTask;
import com.meizu.cloud.compaign.task.app.BaseAppTaskInfo;
import com.meizu.flyme.appcenter.AppCenterApplication;
import com.meizu.flyme.appcenter.desktopplugin.b.c;
import com.meizu.flyme.appcenter.fragment.AppEventWebviewFragment;
import com.meizu.flyme.appcenter.fragment.AppSearchFragment;
import com.meizu.flyme.appcenter.fragment.e;
import com.meizu.flyme.appcenter.fragment.j;
import com.meizu.flyme.appcenter.fragment.q;
import com.meizu.flyme.appcenter.fragment.s;
import com.meizu.flyme.appcenter.recommend.b;
import com.meizu.mstore.R;

public class AppMainActivity extends BaseMainActivity {
    private static String t = "com.meizu.flyme.launcher";
    private static int u = Opcodes.I2C;
    private UriMatcher v = new UriMatcher(-1);

    public AppMainActivity() {
        this.v.addURI("app.meizu.com", "/phone/apps/package/*", 3);
        this.v.addURI("app.meizu.com", "/phone/apps/#", 1);
        this.v.addURI("app.meizu.com", "/phone/apps/*", 2);
    }

    protected void n() {
        super.n();
        b.a((Context) this).a(false);
        AppCenterApplication.c();
        getWindow().getDecorView().post(new Runnable(this) {
            final /* synthetic */ AppMainActivity a;

            {
                this.a = r1;
            }

            public void run() {
                a.a(this.a.getApplicationContext()).g(c.a(this.a.getApplicationContext()));
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d.a(this, "last_launch_time", System.currentTimeMillis());
    }

    protected void p() {
        o fragmentTransaction = f().a();
        fragmentTransaction.b(R.id.main_container, new j());
        fragmentTransaction.c();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != R.id.search_menu) {
            return super.onOptionsItemSelected(item);
        }
        com.meizu.cloud.base.b.d.startSearchFragment(this, new AppSearchFragment());
        if (this.k != null && this.k.size() > 0) {
            for (b listener : this.k) {
                listener.b();
            }
        }
        return true;
    }

    protected boolean c(Intent intent) {
        String action = intent.getAction();
        Bundle bundle;
        if ("android.intent.action.VIEW".equals(action)) {
            String data = intent.getDataString();
            if (data != null) {
                String packageName;
                if (data.startsWith("market://search?q=")) {
                    if (data.startsWith("market://search?q=pub:")) {
                        String searchKey = Uri.parse(data).getQueryParameter("q");
                        bundle = new Bundle();
                        bundle.putString(AppSearchFragment.EXTRA_SEARCH, searchKey);
                        a(a(AppSearchFragment.class, bundle), p.SEARCH_TAG, o());
                        return true;
                    } else if (data.startsWith("market://search?q=pname:")) {
                        param = Uri.parse(data).getQueryParameter("q");
                        packageName = param.substring(param.indexOf(":") + 1);
                        bundle = new Bundle();
                        bundle.putString("package_name", packageName);
                        a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                        return true;
                    } else {
                        param = Uri.parse(data).getQueryParameter("q");
                        boolean isShowKeybord = intent.getExtras() != null && intent.getExtras().getBoolean(p.SHOW_KEYBOARD, false);
                        boolean isClearTask = intent.getExtras() != null && intent.getExtras().getBoolean(p.CLEAR_TASK, false);
                        bundle = new Bundle();
                        bundle.putString(AppSearchFragment.EXTRA_SEARCH, param);
                        bundle.putBoolean(p.SHOW_KEYBOARD, isShowKeybord);
                        bundle.putBoolean(p.CLEAR_TASK, isClearTask);
                        a(a(AppSearchFragment.class, bundle), p.SEARCH_TAG, o());
                        return true;
                    }
                } else if (data.indexOf("mstore:http://app.meizu.com/phone/apps/") == 0) {
                    Uri uri = Uri.parse(data.substring("mstore:".length()));
                    bundle = new Bundle();
                    switch (this.v.match(uri)) {
                        case 1:
                            bundle.putLong("app_id", ContentUris.parseId(uri));
                            a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                            return true;
                        case 2:
                            bundle.putString("identify", uri.getLastPathSegment());
                            a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                            return true;
                        case 3:
                            bundle.putString("package_name", uri.getLastPathSegment());
                            a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                            return true;
                        default:
                            Log.e("BaseUpdateActivity", "no match url.");
                            return false;
                    }
                } else if (data.contains("AppDetailURLFlash_044")) {
                    bundle = new Bundle();
                    bundle.putString("AppDetailURLFlash_044", null);
                    a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                    return true;
                } else if (data.startsWith("market://details?id=")) {
                    packageName = Uri.parse(data).getQueryParameter("id");
                    bundle = new Bundle();
                    bundle.putString("package_name", packageName);
                    a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                    return true;
                } else if (data.startsWith("http://app.meizu.com/apps/public/detail?package_name=")) {
                    packageName = Uri.parse(data).getQueryParameter("package_name");
                    bundle = new Bundle();
                    bundle.putString("package_name", packageName);
                    a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                    return true;
                } else if (data.startsWith("http://app.meizu.com/games/public/detail?package_name=")) {
                    packageName = Uri.parse(data).getQueryParameter("package_name");
                    bundle = new Bundle();
                    bundle.putString("package_name", packageName);
                    a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                    return true;
                } else if (data.startsWith("flyme_3dtouch://com.meizu.mstore/search")) {
                    Log.i("data_str", "uri: " + data);
                    bundle = new Bundle();
                    bundle.putBoolean(p.SHOW_KEYBOARD, true);
                    a(a(AppSearchFragment.class, bundle), p.SEARCH_TAG, o());
                    return true;
                }
            }
        } else if ("com.meizu.flyme.appcenter.app.download.manage".equals(action)) {
            String actionType = intent.getStringExtra("Action");
            if (!TextUtils.isEmpty(actionType)) {
                h.a((Context) this, actionType);
            }
            BaseMainActivity.a((FragmentActivity) this, a(e.class, null), o());
            return true;
        } else if ("com.meizu.flyme.appcenter.app.update".equals(action)) {
            a.a.a.c.a().e(new g(3));
            return false;
        } else if ("com.meizu.flyme.appcenter.app.special".equals(action)) {
            a(a(q.class, getIntent().getExtras()), null, o());
            return true;
        } else if ("com.meizu.flyme.appcenter.event".equals(action)) {
            a(a(AppEventWebviewFragment.class, getIntent().getExtras()), null, o());
            return true;
        } else if ("com.meizu.flyme.appcenter.html".equals(action)) {
            a(a(com.meizu.cloud.app.fragment.j.class, getIntent().getExtras()), null, o());
            return true;
        } else if ("com.meizu.flyme.appcenter.action.perform".equals(action)) {
            return d(intent);
        } else {
            if ("sdk.meizu.compaign.EXECUTOR".equals(action)) {
                return w();
            }
            if ("sdk.meizu.compaign.DEFAULT".equals(action)) {
                v();
                return false;
            } else if ("mz_ad_com.meizu.mstore".equals(action)) {
                String pkgName = intent.getStringExtra("package_name");
                if (TextUtils.isEmpty(pkgName)) {
                    return false;
                }
                bundle = new Bundle();
                bundle.putString("package_name", pkgName);
                a(intent, bundle);
                a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
                return true;
            } else if ("com.meizu.flyme.appcenter.app.updaterecord".equals(action)) {
                a(a(s.class, null), null, o());
            } else if (intent.getExtras() != null) {
                return e(intent);
            }
        }
        return false;
    }

    private boolean w() {
        Intent intent = getIntent();
        BaseTask task = com.meizu.cloud.compaign.a.a((Context) this).a(new meizu.sdk.compaign.b(intent), true);
        if (task instanceof BaseAppTask) {
            BaseAppTaskInfo downloadTaskInfo = (BaseAppTaskInfo) ((BaseAppTask) task).getTaskInfo();
            if (downloadTaskInfo != null) {
                intent.putExtra("url", RequestConstants.APP_DETAIL_PATH_URL + downloadTaskInfo.appId);
                intent.putExtra("task_type", task.getTaskType());
                com.meizu.flyme.appcenter.fragment.d tarFragment = new com.meizu.flyme.appcenter.fragment.d();
                tarFragment.setArguments(intent.getExtras());
                a((Fragment) tarFragment, null, o());
                return true;
            }
        }
        Toast.makeText(this, getString(R.string.task_unrecognizable), 1).show();
        return false;
    }

    private boolean d(Intent intent) {
        String data = intent.getDataString();
        if (data == null || !data.startsWith("http://app.meizu.com/apps/public/detail?package_name=")) {
            return false;
        }
        Uri uri = Uri.parse(data);
        String packageName = uri.getQueryParameter("package_name");
        String appName = uri.getQueryParameter("app_name");
        boolean bSearch = uri.getBooleanQueryParameter("goto_search_page", false);
        Bundle bundle = new Bundle();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bundle.putString("result_app_action", extras.getString("result_app_action"));
        }
        if (!TextUtils.isEmpty(packageName)) {
            bundle.putString("package_name", packageName);
        }
        if (!TextUtils.isEmpty(appName)) {
            bundle.putString("app_name", appName);
        }
        bundle.putBoolean("goto_search_page", bSearch);
        a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
        return true;
    }

    private boolean e(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey("detail_url")) {
            String url = extras.getString("detail_url");
            if (TextUtils.isEmpty(url)) {
                url = RequestConstants.APP_DETAIL_PATH_URL + extras.getInt("app_id");
            }
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            a(a(com.meizu.flyme.appcenter.fragment.d.class, bundle), null, o());
            return true;
        } else if (extras == null || (!extras.containsKey(AppSearchFragment.EXTRA_MIME) && !extras.containsKey(AppSearchFragment.EXTRA_SEARCH))) {
            return false;
        } else {
            a(a(AppSearchFragment.class, extras), p.SEARCH_TAG, o());
            return true;
        }
    }

    protected void u() {
        this.m = true;
        startActivityForResult(new Intent(this, AppWizardActivity.class), this.n);
    }
}
