package com.meizu.cloud.base.app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.o;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.f;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.utils.param.a;
import com.meizu.cloud.app.utils.q;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.statistics.b;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseMainActivity extends BaseUpdateActivity {
    protected boolean m = false;
    protected int n = 1000;
    protected boolean o = false;
    protected AlertDialog p;

    protected abstract boolean c(Intent intent);

    protected abstract void p();

    protected abstract void u();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(g.base_main_activity);
        setTitle("");
        getWindow().getDecorView().setBackgroundColor(getResources().getColor(c.activity_background_color));
        g().b(false);
        this.o = true;
        if (r() && t()) {
            getWindow().getDecorView().post(new Runnable(this) {
                final /* synthetic */ BaseMainActivity a;

                {
                    this.a = r1;
                }

                public void run() {
                    new Handler().post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            BaseApplication.a(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    if (!this.a.a.a.isDestroyed() && this.a.a.a.q()) {
                                        this.a.a.a.n();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    protected void onResume() {
        super.onResume();
        if (!s()) {
            getWindow().getDecorView().post(new Runnable(this) {
                final /* synthetic */ BaseMainActivity a;

                {
                    this.a = r1;
                }

                public void run() {
                    new Handler().post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass2 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            BaseApplication.a(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    if (!this.a.a.a.isDestroyed()) {
                                        this.a.a.a.c(this.a.a.a.getIntent());
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    protected void n() {
        super.n();
        a.a(this).a();
        if (o()) {
            if (c(getIntent())) {
                p();
            } else {
                p();
            }
            return;
        }
        p();
    }

    protected boolean o() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (intent.getExtras() != null) {
            return extras.getBoolean("perform_internal", true);
        }
        return true;
    }

    protected void onDestroy() {
        h.a((Context) this);
        if (q.a(this)) {
            w();
        }
        super.onDestroy();
        System.gc();
    }

    protected <T extends Fragment> Fragment a(Class<T> clazz, Bundle bundle) {
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            if (bundle == null) {
                return fragment;
            }
            fragment.setArguments(bundle);
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return new Fragment();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return new Fragment();
        }
    }

    protected void a(Fragment fragment, String tag, boolean popBackStack) {
        a(fragment, tag, popBackStack, false);
    }

    protected void a(Fragment fragment, String tag, boolean popBackStack, boolean goToWithFragment) {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("push_message_id")) {
            long id = extras.getLong("push_message_id", -1);
            if (id != -1) {
                Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putLong("push_message_id", id);
                }
                f.a(getApplicationContext(), id);
            }
        }
        if (goToWithFragment) {
            o fragmentTransaction = f().a();
            if (popBackStack) {
                f().a(null, 1);
                fragmentTransaction.b(com.meizu.cloud.b.a.f.main_container, fragment, tag);
                fragmentTransaction.c();
            } else {
                fragmentTransaction.b(com.meizu.cloud.b.a.f.main_container, fragment, tag);
                fragmentTransaction.a(null);
                fragmentTransaction.c();
            }
        } else {
            BaseSecondActivity.a((Context) this, fragment);
            if (popBackStack) {
                finish();
            }
        }
        setIntent(new Intent());
    }

    public static void a(FragmentActivity activity, Fragment tarFragment, boolean popBackStack) {
        BaseSecondActivity.a((Context) activity, tarFragment);
        if (popBackStack) {
            activity.finish();
        }
        activity.setIntent(new Intent());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.n && resultCode == -1) {
            this.m = false;
            n();
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (this.m && d(intent)) {
            u();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
    }

    protected boolean q() {
        if (!d(getIntent()) || !getSharedPreferences("setting", 0).getBoolean("show_user_guide_5.0", true) || !m.b(this)) {
            return true;
        }
        u();
        return false;
    }

    protected boolean r() {
        if (!d(getIntent())) {
            return true;
        }
        Locale locale = getResources().getConfiguration().locale;
        Locale locale2 = Locale.getDefault();
        if (!d.g() || locale2.equals(Locale.SIMPLIFIED_CHINESE) || locale2.equals(Locale.TRADITIONAL_CHINESE)) {
            return true;
        }
        Builder builder = new Builder(this);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(i.international_tips));
        builder.setNegativeButton(i.international_exit, new OnClickListener(this) {
            final /* synthetic */ BaseMainActivity a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                this.a.finish();
            }
        });
        builder.setPositiveButton(i.international_continue, new OnClickListener(this) {
            final /* synthetic */ BaseMainActivity a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if (this.a.q()) {
                    this.a.n();
                }
            }
        });
        builder.show();
        return false;
    }

    protected boolean s() {
        if (this.p == null) {
            return false;
        }
        return this.p.isShowing();
    }

    protected boolean t() {
        return true;
    }

    private boolean d(Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return false;
        }
        if (intent.getAction().equals("android.intent.action.MAIN") || intent.getAction().equals("com.meizu.setup.DOWNLOAD_NOTIFY")) {
            return true;
        }
        return false;
    }

    private void w() {
        int i;
        int i2 = 1;
        Map<String, String> wdmDataMap = new HashMap();
        wdmDataMap.put("updatenotify", String.valueOf(com.meizu.cloud.app.settings.a.a((Context) this).a() ? 1 : 0));
        String str = "trafficfree";
        if (com.meizu.cloud.app.settings.a.a((Context) this).c()) {
            i = 1;
        } else {
            i = 0;
        }
        wdmDataMap.put(str, String.valueOf(i));
        str = "slientupdate";
        if (com.meizu.cloud.app.settings.a.a((Context) this).d()) {
            i = 1;
        } else {
            i = 0;
        }
        wdmDataMap.put(str, String.valueOf(i));
        str = "deleteapk";
        if (com.meizu.cloud.app.settings.a.a((Context) this).e()) {
            i = 1;
        } else {
            i = 0;
        }
        wdmDataMap.put(str, String.valueOf(i));
        String str2 = "myapp_fliterIntsalled";
        if (!com.meizu.cloud.app.settings.a.a((Context) this).f()) {
            i2 = 0;
        }
        wdmDataMap.put(str2, String.valueOf(i2));
        b.a().a("settingswitch", null, wdmDataMap);
    }

    protected void onStart() {
        super.onStart();
        if (q.a(this)) {
            b.a().a(getClass().getSimpleName());
        }
    }

    protected void onStop() {
        super.onStop();
        if (q.a(this)) {
            b.a().a(getClass().getSimpleName(), null);
        }
    }

    protected void a(Intent intent, Bundle bundle) {
        if ("MzDlAd".equals(intent.getStringExtra("source"))) {
            bundle.putBundle("track_bundle", intent.getExtras());
        }
    }
}
