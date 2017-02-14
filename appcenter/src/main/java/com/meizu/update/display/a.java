package com.meizu.update.display;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import com.meizu.update.UpdateInfo;
import com.meizu.update.d;
import com.meizu.update.h.b;

public abstract class a {
    protected Context a;
    protected UpdateInfo b;
    protected boolean c;
    protected Dialog d;
    private String e;
    private String f;
    private BroadcastReceiver g = new BroadcastReceiver(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void onReceive(Context context, Intent intent) {
            b.c("Receive dialog show broadcast.");
            if (this.a.d != null && this.a.d.isShowing()) {
                try {
                    this.a.d.dismiss();
                } catch (Exception ignore) {
                    b.d("dismiss dialog exception:" + ignore.getMessage());
                    this.a.d.hide();
                    this.a.g();
                }
            }
        }
    };

    protected static class a {
        String a;
        String b;
        String c;
        String d;
        String e;
        String f;
        a g;

        public interface a {

            public enum a {
            }

            void a(a aVar);
        }

        public a(String r1, String r2, String r3, String r4, String r5, String r6, com.meizu.update.display.a.a.a r7) {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:120)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r0 = this;
            r0.<init>();
            r0.a = r1;
            r0.cancel = r2;
            r0.c = r3;
            r0.d = r4;
            r0.e = r5;
            r0.f = r6;
            r0.g = r7;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.update.display.a.a.<init>(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.meizu.update.display.a$a$a):void");
        }
    }

    public abstract a a();

    protected a(Context context, UpdateInfo info) {
        if (context == null || info == null) {
            throw new IllegalArgumentException("params cant be null!");
        }
        this.a = context;
        this.b = info;
    }

    public void a(boolean enable) {
        this.c = enable;
    }

    public d b() {
        try {
            return e();
        } catch (Exception e) {
            b.d("display dialog exception!");
            e.printStackTrace();
            return null;
        }
    }

    private d e() {
        int theme;
        final a displayInfo = a();
        if (com.meizu.update.h.d.b()) {
            theme = 5;
        } else {
            theme = 3;
        }
        Builder builder = new Builder(this.a, theme);
        boolean hasSubTitle = false;
        if (!TextUtils.isEmpty(displayInfo.a)) {
            if (TextUtils.isEmpty(displayInfo.b)) {
                builder.setTitle(displayInfo.a);
            } else {
                hasSubTitle = true;
                SpannableString spannable = new SpannableString(displayInfo.a + "\n" + displayInfo.b);
                spannable.setSpan(new AbsoluteSizeSpan(this.a.getResources().getDimensionPixelSize(com.meizu.update.c.d.b.mzuc_dialog_sub_title_text_size)), displayInfo.a.length(), spannable.length(), 33);
                spannable.setSpan(new ForegroundColorSpan(this.a.getResources().getColor(com.meizu.update.c.d.a.mzuc_dialog_sub_title_text_color)), displayInfo.a.length(), spannable.length(), 33);
                builder.setTitle(spannable);
            }
        }
        builder.setMessage(displayInfo.c);
        builder.setPositiveButton(displayInfo.d, new OnClickListener(this) {
            final /* synthetic */ a b;

            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                displayInfo.g.a(a.POSITIVE);
            }
        });
        if (TextUtils.isEmpty(displayInfo.e)) {
            builder.setCancelable(false);
        } else {
            builder.setNegativeButton(displayInfo.e, new OnClickListener(this) {
                final /* synthetic */ a b;

                public void onClick(DialogInterface dialog, int which) {
                    displayInfo.g.a(a.NEGATIVE);
                }
            });
        }
        if (!TextUtils.isEmpty(displayInfo.f)) {
            builder.setNeutralButton(displayInfo.f, new OnClickListener(this) {
                final /* synthetic */ a b;

                public void onClick(DialogInterface dialog, int which) {
                    displayInfo.g.a(a.NEUTRAL);
                }
            });
        }
        builder.setOnCancelListener(new OnCancelListener(this) {
            final /* synthetic */ a b;

            public void onCancel(DialogInterface dialog) {
                displayInfo.g.a(a.CANCELED);
            }
        });
        AlertDialog dialog = builder.create();
        this.d = dialog;
        if (this.c) {
            dialog.getWindow().setType(2003);
            i();
        }
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(new OnDismissListener(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void onDismiss(DialogInterface dialog) {
                this.a.g();
            }
        });
        h();
        f();
        dialog.show();
        Button btnPositive = dialog.getButton(-1);
        Button btnNegative = dialog.getButton(-2);
        Button btnNeutral = dialog.getButton(-3);
        if (!(btnPositive == null || btnNegative == null || btnNeutral == null || TextUtils.isEmpty(displayInfo.f) || TextUtils.isEmpty(displayInfo.e))) {
            int btnTextSize = this.a.getResources().getDimensionPixelSize(com.meizu.update.c.d.b.mzuc_dialog_btn_text_size_small);
            btnPositive.setTextSize(0, (float) btnTextSize);
            btnNegative.setTextSize(0, (float) btnTextSize);
            btnNeutral.setTextSize(0, (float) btnTextSize);
        }
        if (hasSubTitle) {
            TextView titleView = a(dialog);
            if (titleView != null) {
                titleView.setLineSpacing((float) this.a.getResources().getDimensionPixelSize(com.meizu.update.c.d.b.mzuc_dialog_title_line_spacing), 1.0f);
            }
            TextView msgView = (TextView) dialog.findViewById(16908299);
            if (msgView != null) {
                msgView.setTextSize(0, (float) this.a.getResources().getDimensionPixelSize(com.meizu.update.c.d.b.mzuc_dialog_msg_text_size));
                msgView.setTextColor(this.a.getResources().getColor(com.meizu.update.c.d.a.mzuc_dialog_msg_text_color));
                msgView.setLineSpacing((float) this.a.getResources().getDimensionPixelSize(com.meizu.update.c.d.b.mzuc_dialog_msg_line_spacing), 1.0f);
                msgView.setPadding(msgView.getPaddingLeft() + 8, msgView.getPaddingTop(), msgView.getPaddingRight() + 8, msgView.getPaddingBottom());
            }
        }
        return new f(dialog, this.b.mNeedUpdate, this.c);
    }

    private TextView a(AlertDialog dialog) {
        try {
            Object id = com.meizu.f.a.a("com.android.internal.R$id", "alertTitle");
            if (id != null && (id instanceof Integer)) {
                return (TextView) dialog.findViewById(((Integer) id).intValue());
            }
        } catch (Exception e) {
        }
        return null;
    }

    private void f() {
        b.b("register broadcast:" + this.d);
        this.a.getApplicationContext().registerReceiver(this.g, new IntentFilter("com.meizu.update.component.dialog_show"));
    }

    private void g() {
        try {
            b.b("unregister broadcast:" + this.d);
            this.a.getApplicationContext().unregisterReceiver(this.g);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private void h() {
        Intent intent = new Intent("com.meizu.update.component.dialog_show");
        intent.setPackage(this.a.getPackageName());
        this.a.sendBroadcast(intent);
    }

    private void i() {
        try {
            b.b("check keyguard state");
            boolean tryUnlock = false;
            if (com.meizu.update.h.d.d()) {
                KeyguardManager keyguardManager = (KeyguardManager) this.a.getSystemService("keyguard");
                if (!keyguardManager.isKeyguardLocked() || keyguardManager.isKeyguardSecure()) {
                    b.d("need not unlock keyguard");
                } else {
                    b.d("need unlock keyguard");
                    tryUnlock = true;
                }
            }
            if (tryUnlock) {
                Intent intent = new Intent(this.a, KeyguardHelperActivity.class);
                intent.addFlags(268435456);
                this.a.startActivity(intent);
            }
        } catch (Exception ignore) {
            b.d("unlock keyguard exception");
            ignore.printStackTrace();
        }
    }

    public void a(String title) {
        this.e = title;
    }

    public void b(String desc) {
        this.f = desc;
    }

    protected String c() {
        return this.e;
    }

    protected String d() {
        return this.f;
    }
}
