package com.meizu.common.util;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class AlertDialogExtUtil {
    private static final String TAG = "AlertDialogExtUtil";
    public static final int TITLE_CENTER_HIGHLIGHT = 1;
    private static Class alertDialogClazz = null;
    private static Method setMaxHeightMethod = null;
    private static Method setWidthMethod = null;

    final class AnonymousClass1 implements OnClickListener {
        final /* synthetic */ AlertDialog val$dialog;
        final /* synthetic */ DialogInterface.OnClickListener val$listener;
        final /* synthetic */ int val$whichButton;

        AnonymousClass1(AlertDialog alertDialog, int i, DialogInterface.OnClickListener onClickListener) {
            this.val$dialog = alertDialog;
            this.val$whichButton = i;
            this.val$listener = onClickListener;
        }

        public void onClick(View view) {
            ButtonHandler buttonHandler = new ButtonHandler(this.val$dialog);
            switch (this.val$whichButton) {
                case -3:
                case -2:
                case -1:
                    Message obtainMessage;
                    if (this.val$listener != null) {
                        obtainMessage = buttonHandler.obtainMessage(this.val$whichButton, this.val$listener);
                    } else {
                        obtainMessage = null;
                    }
                    if (obtainMessage != null) {
                        obtainMessage.sendToTarget();
                        return;
                    }
                    return;
                default:
                    throw new IllegalArgumentException("Button does not exist");
            }
        }
    }

    static final class ButtonHandler extends Handler {
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference(dialogInterface);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case -3:
                case -2:
                case -1:
                    ((DialogInterface.OnClickListener) message.obj).onClick((DialogInterface) this.mDialog.get(), message.what);
                    return;
                default:
                    return;
            }
        }
    }

    public static boolean setWidth(AlertDialog alertDialog, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                if (alertDialogClazz == null) {
                    alertDialogClazz = AlertDialog.class;
                    setWidthMethod = alertDialogClazz.getDeclaredMethod("setWidth", new Class[]{Integer.TYPE});
                    setWidthMethod.setAccessible(true);
                    setWidthMethod.invoke(alertDialog, new Object[]{Integer.valueOf(i)});
                    return true;
                } else if (setWidthMethod != null) {
                    setWidthMethod.invoke(alertDialog, new Object[]{Integer.valueOf(i)});
                }
            } catch (Exception e) {
                Log.e(TAG, "setWidth fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setButtonTextColor(AlertDialog alertDialog, int i, int i2) {
        if (alertDialog == null) {
            return false;
        }
        Button button = alertDialog.getButton(i);
        if (button != null && button.getVisibility() == 0) {
            button.setTextColor(alertDialog.getContext().getResources().getColorStateList(i2));
        }
        return true;
    }

    public static boolean setAutoShowSoftInput(AlertDialog alertDialog, boolean z) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = alertDialogClazz.getDeclaredMethod("setAutoShowSoftInput", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(alertDialog, new Object[]{Boolean.valueOf(z)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setAutoShowSoftInput fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setTitleStyle(AlertDialog alertDialog, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = alertDialogClazz.getDeclaredMethod("setTitleStyle", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(alertDialog, new Object[]{Integer.valueOf(i)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setTitleStyle fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setMaxHeight(AlertDialog alertDialog, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                if (alertDialogClazz == null) {
                    alertDialogClazz = AlertDialog.class;
                    setMaxHeightMethod = alertDialogClazz.getDeclaredMethod("setMaxHeight", new Class[]{Integer.TYPE});
                    setMaxHeightMethod.setAccessible(true);
                    setMaxHeightMethod.invoke(alertDialog, new Object[]{Integer.valueOf(i)});
                    return true;
                } else if (setMaxHeightMethod != null) {
                    setMaxHeightMethod.invoke(alertDialog, new Object[]{Integer.valueOf(i)});
                }
            } catch (Exception e) {
                Log.e(TAG, "setMaxHeight fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setMessage(AlertDialog alertDialog, CharSequence charSequence, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = alertDialogClazz.getDeclaredMethod("setMessage", new Class[]{CharSequence.class, Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(alertDialog, new Object[]{charSequence, Integer.valueOf(i)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setMessage fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static boolean setCustEditViewHasMargin(AlertDialog alertDialog, boolean z) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = alertDialogClazz.getDeclaredMethod("setCustEditViewHasMargin", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(alertDialog, new Object[]{Boolean.valueOf(z)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setCustEditViewHasMargin fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }

    public static void setButtonUnDismissOnClick(AlertDialog alertDialog, int i, DialogInterface.OnClickListener onClickListener) {
        if (alertDialog != null) {
            Button button = alertDialog.getButton(i);
            if (button != null && button.getVisibility() == 0) {
                button.setOnClickListener(new AnonymousClass1(alertDialog, i, onClickListener));
            }
        }
    }

    public static boolean setListViewLayoutWidth(AlertDialog alertDialog, int i) {
        if (CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = alertDialogClazz.getDeclaredMethod("setListViewLayoutWidth", new Class[]{Integer.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(alertDialog, new Object[]{Integer.valueOf(i)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setListViewLayoutWidth fail to be invoked.Caused by:" + e.getMessage());
            }
        }
        return false;
    }
}
