package com.meizu.common.util;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import com.meizu.common.R;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

public class ActionBarProxy implements InvocationHandler {
    private static final String TAG = ActionBarProxy.class.getName();
    private boolean mListenerHadAdd = false;
    private OnBackButtonEnableChangeListener mOnBackButtonEnableChangeListener;
    private Class mOnBackButtonEnableChangeListenerClazz;
    private ArrayList<OnBackButtonEnableChangeListener> mOnBackButtonEnableChangeListeners;
    private OnTopBackButtonEnableChangeListener mOnTopBackButtonEnableChangeListener;
    private ActionBar mProxidedActionBar;
    private OnBackButtonEnableChangeListener mTopBackBtnListener;
    private int mTopBackButtonColor;
    private boolean mTopBackButtonEnabled;

    public interface OnBackButtonEnableChangeListener {
        void onBackButtonEnableChange(boolean z);
    }

    public interface OnTopBackButtonEnableChangeListener {
        void onTopBackButtonEnableChange(boolean z);
    }

    public ActionBarProxy(ActionBar actionBar) {
        this.mProxidedActionBar = actionBar;
        if (actionBar == null) {
            throw new IllegalArgumentException("the ActionBar proxied can not be null");
        }
        try {
            this.mOnBackButtonEnableChangeListenerClazz = Class.forName("android.app.ActionBar$OnBackButtonEnableChangeListener");
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "init ActionBarProxy error:" + e.getMessage());
        }
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        try {
            if (method.getName().equals("onBackButtonEnableChange") && this.mOnBackButtonEnableChangeListeners != null && this.mOnBackButtonEnableChangeListeners.size() > 0) {
                boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
                ArrayList arrayList = (ArrayList) this.mOnBackButtonEnableChangeListeners.clone();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    ((OnBackButtonEnableChangeListener) arrayList.get(i)).onBackButtonEnableChange(booleanValue);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "ActionBarProxy invoke error:" + e.getMessage());
        }
        return null;
    }

    public boolean setOnBackButtonEnableChangeListener(OnBackButtonEnableChangeListener onBackButtonEnableChangeListener) {
        if (this.mProxidedActionBar == null) {
            return false;
        }
        boolean z;
        if (!(this.mOnBackButtonEnableChangeListener == null || this.mOnBackButtonEnableChangeListeners == null)) {
            this.mOnBackButtonEnableChangeListeners.remove(this.mOnBackButtonEnableChangeListener);
        }
        this.mOnBackButtonEnableChangeListener = onBackButtonEnableChangeListener;
        if (onBackButtonEnableChangeListener != null) {
            if (this.mOnBackButtonEnableChangeListeners == null) {
                this.mOnBackButtonEnableChangeListeners = new ArrayList();
            }
            if (!this.mOnBackButtonEnableChangeListeners.contains(this.mOnBackButtonEnableChangeListener)) {
                this.mOnBackButtonEnableChangeListeners.add(this.mOnBackButtonEnableChangeListener);
                z = false;
            }
            z = false;
        } else {
            if (this.mOnBackButtonEnableChangeListeners.size() == 0) {
                z = true;
            }
            z = false;
        }
        try {
            Object obj;
            Method declaredMethod = this.mProxidedActionBar.getClass().getDeclaredMethod("setOnBackButtonEnableChangeListener", new Class[]{this.mOnBackButtonEnableChangeListenerClazz});
            declaredMethod.setAccessible(true);
            if (z) {
                obj = null;
            } else {
                obj = Proxy.newProxyInstance(this.mOnBackButtonEnableChangeListenerClazz.getClassLoader(), new Class[]{this.mOnBackButtonEnableChangeListenerClazz}, this);
                if (obj == null) {
                    return false;
                }
            }
            declaredMethod.invoke(this.mProxidedActionBar, new Object[]{obj});
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addOnBackButtonEnableChangeListener(OnBackButtonEnableChangeListener onBackButtonEnableChangeListener) {
        if (this.mProxidedActionBar == null || onBackButtonEnableChangeListener == null) {
            return false;
        }
        if (this.mOnBackButtonEnableChangeListener != null) {
            if (this.mOnBackButtonEnableChangeListeners == null) {
                this.mOnBackButtonEnableChangeListeners = new ArrayList();
            }
            if (!this.mOnBackButtonEnableChangeListeners.contains(onBackButtonEnableChangeListener)) {
                this.mOnBackButtonEnableChangeListeners.add(onBackButtonEnableChangeListener);
            }
            return true;
        } else if (setOnBackButtonEnableChangeListener(onBackButtonEnableChangeListener)) {
            return true;
        } else {
            return false;
        }
    }

    public void removeOnBackButtonEnableChangeListener(OnBackButtonEnableChangeListener onBackButtonEnableChangeListener) {
        if (this.mOnBackButtonEnableChangeListeners != null) {
            this.mOnBackButtonEnableChangeListeners.remove(onBackButtonEnableChangeListener);
        }
    }

    public boolean isBackButtonEnabled() {
        if (this.mProxidedActionBar == null) {
            return true;
        }
        try {
            Method declaredMethod = this.mProxidedActionBar.getClass().getDeclaredMethod("isBackButtonEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            return ((Boolean) declaredMethod.invoke(this.mProxidedActionBar, new Object[0])).booleanValue();
        } catch (Exception e) {
            return true;
        }
    }

    @SuppressLint({"NewApi"})
    public void setTopBackButtonEnabled(boolean z, boolean z2) {
        boolean isBackButtonEnabled = isBackButtonEnabled();
        Configuration configuration = this.mProxidedActionBar.getThemedContext().getResources().getConfiguration();
        if (z2 || configuration.orientation == 2) {
            isBackButtonEnabled = z;
        } else if (isBackButtonEnabled && configuration.orientation == 1) {
            isBackButtonEnabled = false;
        } else {
            isBackButtonEnabled = true;
        }
        this.mProxidedActionBar.setDisplayHomeAsUpEnabled(isBackButtonEnabled);
        this.mProxidedActionBar.setHomeButtonEnabled(isBackButtonEnabled);
        this.mTopBackButtonEnabled = isBackButtonEnabled;
        if (isBackButtonEnabled) {
            if (VERSION.SDK_INT >= 18) {
                setHomeAsUpIndicator(R.drawable.mz_ic_ab_back_top);
            }
        } else if (VERSION.SDK_INT >= 18) {
            this.mProxidedActionBar.setHomeAsUpIndicator(R.drawable.mz_ic_ab_back_transparent);
        }
        if (this.mOnTopBackButtonEnableChangeListener != null) {
            this.mOnTopBackButtonEnableChangeListener.onTopBackButtonEnableChange(isBackButtonEnabled);
        }
        if (this.mTopBackBtnListener == null) {
            this.mTopBackBtnListener = new OnBackButtonEnableChangeListener() {
                public void onBackButtonEnableChange(boolean z) {
                    boolean z2;
                    boolean z3 = true;
                    if (ActionBarProxy.this.mProxidedActionBar.getThemedContext().getResources().getConfiguration().orientation == 2) {
                        z = true;
                    }
                    ActionBarProxy.this.mProxidedActionBar.setDisplayHomeAsUpEnabled(!z);
                    ActionBar access$000 = ActionBarProxy.this.mProxidedActionBar;
                    if (z) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    access$000.setHomeButtonEnabled(z2);
                    if (z) {
                        if (VERSION.SDK_INT >= 18) {
                            ActionBarProxy.this.mProxidedActionBar.setHomeAsUpIndicator(R.drawable.mz_ic_ab_back_transparent);
                        }
                    } else if (VERSION.SDK_INT >= 18) {
                        ActionBarProxy.this.setHomeAsUpIndicator(R.drawable.mz_ic_ab_back_top);
                    }
                    if (ActionBarProxy.this.mOnTopBackButtonEnableChangeListener != null && ActionBarProxy.this.mTopBackButtonEnabled == z) {
                        OnTopBackButtonEnableChangeListener access$200 = ActionBarProxy.this.mOnTopBackButtonEnableChangeListener;
                        if (z) {
                            z2 = false;
                        } else {
                            z2 = true;
                        }
                        access$200.onTopBackButtonEnableChange(z2);
                    }
                    ActionBarProxy actionBarProxy = ActionBarProxy.this;
                    if (z) {
                        z3 = false;
                    }
                    actionBarProxy.mTopBackButtonEnabled = z3;
                }
            };
        }
        if (!this.mListenerHadAdd) {
            if (addOnBackButtonEnableChangeListener(this.mTopBackBtnListener)) {
                this.mListenerHadAdd = true;
            } else {
                this.mProxidedActionBar.setDisplayHomeAsUpEnabled(false);
                this.mProxidedActionBar.setHomeButtonEnabled(false);
                if (VERSION.SDK_INT >= 18) {
                    this.mProxidedActionBar.setHomeAsUpIndicator(R.drawable.mz_ic_ab_back_transparent);
                }
            }
        }
        if (!z && z2 && this.mListenerHadAdd) {
            removeOnBackButtonEnableChangeListener(this.mTopBackBtnListener);
            this.mListenerHadAdd = false;
        }
    }

    public void setOnTopBackButtonEnableChangeListener(OnTopBackButtonEnableChangeListener onTopBackButtonEnableChangeListener) {
        this.mOnTopBackButtonEnableChangeListener = onTopBackButtonEnableChangeListener;
    }

    public void setTopBackButtonColor(int i) {
        this.mTopBackButtonColor = i;
        if (this.mTopBackButtonEnabled) {
            setHomeAsUpIndicator(R.drawable.mz_ic_ab_back_top);
        }
    }

    public void setTopBackButtonColorRes(int i) {
        if (this.mProxidedActionBar != null) {
            setTopBackButtonColor(this.mProxidedActionBar.getThemedContext().getResources().getColor(i));
        }
    }

    @SuppressLint({"NewApi"})
    private void setHomeAsUpIndicator(int i) {
        if (this.mTopBackButtonColor == 0) {
            this.mProxidedActionBar.setHomeAsUpIndicator(i);
            return;
        }
        Drawable drawable = this.mProxidedActionBar.getThemedContext().getResources().getDrawable(i);
        drawable.setColorFilter(new LightingColorFilter(0, this.mTopBackButtonColor));
        this.mProxidedActionBar.setHomeAsUpIndicator(drawable);
    }

    public boolean setEnabledBackWhenOverlay(boolean z) {
        if (this.mProxidedActionBar != null && CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = this.mProxidedActionBar.getClass().getDeclaredMethod("setEnabledBackWhenOverlay", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this.mProxidedActionBar, new Object[]{Boolean.valueOf(z)});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setEnabledBackWhenOverlay fail to be invoked. Caused by :" + e.getMessage());
            }
        }
        return false;
    }

    public boolean setBackButtonDrawable(Drawable drawable) {
        if (this.mProxidedActionBar != null && CommonUtils.isFlymeOS()) {
            try {
                Method declaredMethod = this.mProxidedActionBar.getClass().getDeclaredMethod("setBackButtonDrawable", new Class[]{Drawable.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(this.mProxidedActionBar, new Object[]{drawable});
                return true;
            } catch (Exception e) {
                Log.e(TAG, "setBackButtonDrawable fail to be invoked. Caused by :" + e.getMessage());
            }
        }
        return false;
    }

    public boolean setActionBarViewCollapsable(Activity activity, boolean z) {
        int i = 0;
        if (activity != null) {
            View findViewById = activity.findViewById(activity.getResources().getIdentifier("action_bar", "id", "android"));
            if (findViewById != null) {
                if (z) {
                    i = 8;
                }
                findViewById.setVisibility(i);
                return true;
            }
            Log.e(TAG, "setActionBarViewCollapsable fail to be invoked. Caused by actionbarView not found");
            return false;
        }
        Log.e(TAG, "setActionBarViewCollapsable fail to be invoked. Caused by activity which is null");
        return false;
    }
}
