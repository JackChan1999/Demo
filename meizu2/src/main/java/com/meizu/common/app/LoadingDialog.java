package com.meizu.common.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.common.R;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import com.meizu.common.widget.LoadingView;

public class LoadingDialog extends Dialog {
    private float dimAmount;
    private boolean isCancelable;
    private Drawable mBackgrond;
    private Context mContext;
    private LoadingView mLoadingView;
    private CharSequence mMessage;
    private TextView mMessageView;
    private LinearLayout mParentPanel;
    private int mTextColor;
    private Window mWindow;

    public LoadingDialog(Context context) {
        this(context, R.style.LoadingDialogTheme);
    }

    public LoadingDialog(Context context, int i) {
        super(context, i);
        this.mTextColor = -1;
        this.dimAmount = FastBlurParameters.DEFAULT_LEVEL;
        this.mContext = getContext();
        this.mBackgrond = this.mContext.getResources().getDrawable(R.drawable.mz_toast_frame);
    }

    public static LoadingDialog show(Context context, CharSequence charSequence, CharSequence charSequence2) {
        return show(context, charSequence, charSequence2, false);
    }

    public static LoadingDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z) {
        return show(context, charSequence, charSequence2, z, null);
    }

    public static LoadingDialog show(Context context, CharSequence charSequence, CharSequence charSequence2, boolean z, OnCancelListener onCancelListener) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.setTitle(charSequence);
        loadingDialog.setMessage(charSequence2);
        loadingDialog.setCancelable(z);
        loadingDialog.setOnCancelListener(onCancelListener);
        loadingDialog.show();
        return loadingDialog;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWindow = getWindow();
        this.mWindow.requestFeature(1);
        setContentView(R.layout.mc_loading_dialog);
        this.mLoadingView = (LoadingView) findViewById(R.id.loadingView);
        this.mMessageView = (TextView) findViewById(R.id.message);
        this.mParentPanel = (LinearLayout) findViewById(R.id.parentPanel);
        initView();
    }

    private void initView() {
        if (this.mMessageView != null && !TextUtils.isEmpty(this.mMessage)) {
            this.mMessageView.setVisibility(0);
            this.mMessageView.setText(this.mMessage);
            if (this.mTextColor != -1) {
                this.mMessageView.setTextColor(this.mTextColor);
            }
        } else if (this.mParentPanel != null) {
            this.mParentPanel.setPadding(0, 0, 0, 0);
        }
        this.mWindow.setDimAmount(FastBlurParameters.DEFAULT_LEVEL);
        if (this.mBackgrond != null) {
            this.mWindow.setBackgroundDrawable(this.mBackgrond);
        }
    }

    protected void onStart() {
        super.onStart();
        this.mLoadingView.startAnimator();
    }

    public void setMessage(int i) {
        setMessage(this.mContext.getResources().getString(i));
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        if (this.mMessageView != null && !TextUtils.isEmpty(this.mMessage)) {
            this.mMessageView.setVisibility(0);
            this.mMessageView.setText(this.mMessage);
        }
    }

    public void setMessageTextColor(int i) {
        this.mTextColor = i;
        if (this.mMessageView != null) {
            this.mMessageView.setTextColor(this.mTextColor);
        }
    }

    public void setMessageTextColorResource(int i) {
        setMessageTextColor(this.mContext.getResources().getColor(i));
    }

    public void setCancelable(boolean z) {
        super.setCancelable(z);
        this.isCancelable = z;
    }

    public boolean isCancelable() {
        return this.isCancelable;
    }

    public void setDimAmount(float f) {
        this.dimAmount = f;
        if (this.mWindow != null) {
            this.mWindow.setDimAmount(f);
        }
    }

    public void setBackgroundDrawableResource(int i) {
        setBackgroundDrawable(this.mContext.getResources().getDrawable(i));
    }

    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackgrond = drawable;
        if (this.mWindow != null) {
            this.mWindow.setBackgroundDrawable(drawable);
        }
    }

    public void setBarForeground(int i) {
        this.mLoadingView.setBarColor(i);
    }

    public void setBarBackground(int i) {
        this.mLoadingView.setBarBackgroundColor(i);
    }
}
