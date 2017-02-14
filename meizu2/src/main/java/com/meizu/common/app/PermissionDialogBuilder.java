package com.meizu.common.app;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.common.R;
import com.meizu.common.util.PermissionUtils;
import java.lang.reflect.Method;

public class PermissionDialogBuilder extends Builder {
    private Class atsClazz;
    private Class clazz;
    private Method isPerformanceTestMethod;
    private Method isProductInternationalMethod;
    private Method isShopDemoVersionMethod;
    private CheckBox mCheckBox;
    private Context mContext;
    private String mFormatString;
    private OnPermissionClickListener mOnPermissionClickListener;
    private TextView mTextView;

    public interface OnPermissionClickListener {
        void onPerMisssionClick(DialogInterface dialogInterface, boolean z, boolean z2);
    }

    public PermissionDialogBuilder(Context context) {
        this(context, 0);
    }

    public PermissionDialogBuilder(Context context, int i) {
        super(context, i);
        this.clazz = null;
        this.isProductInternationalMethod = null;
        this.atsClazz = null;
        this.isPerformanceTestMethod = null;
        this.isShopDemoVersionMethod = null;
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.mc_permission_dialog_view, null);
        setView(inflate);
        setCancelable(false);
        this.mCheckBox = (CheckBox) inflate.findViewById(R.id.mc_pm_check);
        this.mTextView = (TextView) inflate.findViewById(R.id.mc_pm_textView);
        this.mFormatString = context.getResources().getString(R.string.mc_permission_message_content);
        setPositiveButton(R.string.mc_allow, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (PermissionDialogBuilder.this.mOnPermissionClickListener != null) {
                    PermissionDialogBuilder.this.mOnPermissionClickListener.onPerMisssionClick(dialogInterface, PermissionDialogBuilder.this.mCheckBox.isChecked(), true);
                }
            }
        });
        setNegativeButton(R.string.mc_reject, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                if (PermissionDialogBuilder.this.mOnPermissionClickListener != null) {
                    PermissionDialogBuilder.this.mOnPermissionClickListener.onPerMisssionClick(dialogInterface, PermissionDialogBuilder.this.mCheckBox.isChecked(), false);
                }
            }
        });
        setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialogInterface) {
                if (PermissionDialogBuilder.this.mOnPermissionClickListener != null) {
                    PermissionDialogBuilder.this.mOnPermissionClickListener.onPerMisssionClick(dialogInterface, PermissionDialogBuilder.this.mCheckBox.isChecked(), false);
                }
            }
        });
    }

    public void setMessage(String str, String[] strArr) {
        String str2 = "";
        String[] loadPemissionLables = new PermissionUtils(this.mContext).loadPemissionLables(strArr);
        if (loadPemissionLables != null && loadPemissionLables.length > 0) {
            str2 = String.format(this.mFormatString, new Object[]{str, join(loadPemissionLables, ", "), Integer.valueOf(loadPemissionLables.length)});
        }
        setMessage(str2);
    }

    public void setMessage(String str) {
        this.mTextView.setText(str);
    }

    public void setOnPermissonListener(OnPermissionClickListener onPermissionClickListener) {
        this.mOnPermissionClickListener = onPermissionClickListener;
    }

    private String join(String[] strArr, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strArr.length; i++) {
            if (i == strArr.length - 1) {
                stringBuffer.append(strArr[i]);
            } else {
                stringBuffer.append(strArr[i]).append(str);
            }
        }
        return new String(stringBuffer);
    }

    public AlertDialog create() {
        final AlertDialog create = super.create();
        create.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialogInterface) {
                if (PermissionDialogBuilder.this.isProductInternational() || PermissionDialogBuilder.this.isPerformanceTest() || PermissionDialogBuilder.this.isShopDemo()) {
                    create.dismiss();
                    if (PermissionDialogBuilder.this.mOnPermissionClickListener != null) {
                        PermissionDialogBuilder.this.mOnPermissionClickListener.onPerMisssionClick(dialogInterface, PermissionDialogBuilder.this.mCheckBox.isChecked(), true);
                    }
                }
            }
        });
        return create;
    }

    public AlertDialog show() {
        if (!isProductInternational() && !isPerformanceTest() && !isShopDemo()) {
            return super.show();
        }
        if (this.mOnPermissionClickListener == null) {
            return null;
        }
        this.mOnPermissionClickListener.onPerMisssionClick(null, this.mCheckBox.isChecked(), true);
        return null;
    }

    public boolean isProductInternational() {
        try {
            if (this.clazz == null) {
                this.clazz = Class.forName("android.os.BuildExt");
            }
            this.isProductInternationalMethod = this.clazz.getDeclaredMethod("isProductInternational", new Class[0]);
            return ((Boolean) this.isProductInternationalMethod.invoke(this.clazz, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isShopDemo() {
        try {
            if (this.clazz == null) {
                this.clazz = Class.forName("android.os.BuildExt");
            }
            this.isShopDemoVersionMethod = this.clazz.getDeclaredMethod("isShopDemoVersion", new Class[0]);
            return ((Boolean) this.isShopDemoVersionMethod.invoke(this.clazz, new Object[0])).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPerformanceTest() {
        try {
            if (this.atsClazz == null) {
                this.atsClazz = Class.forName(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES);
            }
            this.isPerformanceTestMethod = this.atsClazz.getDeclaredMethod("getBoolean", new Class[]{String.class, Boolean.TYPE});
            return ((Boolean) this.isPerformanceTestMethod.invoke(this.atsClazz, new Object[]{"debug.perf.applunch", Boolean.valueOf(false)})).booleanValue();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
