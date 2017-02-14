package com.meizu.update.h;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import com.meizu.update.c.d.d;

public class h {
    public static ProgressDialog a(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(0);
        progressDialog.setMessage(context.getResources().getString(d.mzuc_wait_tip));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static AlertDialog a(Context context, String strTitle, String strMessage, OnClickListener onClickListenerOK, OnCancelListener onCancelListener, int iconId) {
        Builder builder = new Builder(context);
        if (strTitle != null && strTitle.length() > 0) {
            builder.setTitle(strTitle);
        }
        if (strMessage != null) {
            builder.setMessage(strMessage);
        }
        if (iconId > 0) {
            builder.setIcon(iconId);
        }
        builder.setPositiveButton(context.getResources().getString(17039370), onClickListenerOK);
        builder.setCancelable(false);
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        return builder.show();
    }

    public static AlertDialog a(Context context, String strMessage, OnClickListener onClickListenerOK, OnCancelListener onCancelListener) {
        return a(context, null, strMessage, onClickListenerOK, onCancelListener, -1);
    }
}
