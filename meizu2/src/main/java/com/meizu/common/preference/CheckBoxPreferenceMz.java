package com.meizu.common.preference;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import com.meizu.common.R;
import java.lang.reflect.Method;

public class CheckBoxPreferenceMz extends CheckBoxPreference {
    private static Method sPerformClick;
    private View mTextLayout;
    private OnClickListener mTextLayoutClickListener;
    private boolean mTextLayoutEnabled;
    private OnLongClickListener mTextLayoutLongClickListener;
    private View mWidgetFrame;
    private boolean mWidgetFrameEnabled;

    public CheckBoxPreferenceMz(Context context) {
        this(context, null);
    }

    public CheckBoxPreferenceMz(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextLayoutEnabled = true;
        this.mWidgetFrameEnabled = true;
        setLayoutResource(R.layout.mz_preference_checkbox);
    }

    public CheckBoxPreferenceMz(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextLayoutEnabled = true;
        this.mWidgetFrameEnabled = true;
        setLayoutResource(R.layout.mz_preference_checkbox);
    }

    protected View onCreateView(ViewGroup viewGroup) {
        View onCreateView = super.onCreateView(viewGroup);
        this.mTextLayout = onCreateView.findViewById(R.id.mz_preference_text_layout);
        if (this.mTextLayoutClickListener != null) {
            this.mTextLayout.setOnClickListener(this.mTextLayoutClickListener);
        }
        if (this.mTextLayoutLongClickListener != null) {
            this.mTextLayout.setOnLongClickListener(this.mTextLayoutLongClickListener);
        }
        this.mWidgetFrame = onCreateView.findViewById(16908312);
        this.mWidgetFrame.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CheckBoxPreferenceMz.this.performClick();
            }
        });
        return onCreateView;
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        if (isEnabled() && !this.mTextLayoutEnabled) {
            setEnabledStateOnViews(this.mTextLayout, this.mTextLayoutEnabled);
        }
        if (isEnabled() && !this.mWidgetFrameEnabled) {
            setEnabledStateOnViews(this.mWidgetFrame, this.mWidgetFrameEnabled);
        }
    }

    public void setTextLayoutEnabled(boolean z) {
        if (this.mTextLayoutEnabled != z) {
            this.mTextLayoutEnabled = z;
            if (this.mTextLayout != null) {
                setEnabledStateOnViews(this.mTextLayout, z);
            }
        }
    }

    public boolean isTextLayoutEnabled() {
        return this.mTextLayoutEnabled;
    }

    public void setWidgetFrameEnabled(boolean z) {
        if (this.mWidgetFrameEnabled != z) {
            this.mWidgetFrameEnabled = z;
            if (this.mWidgetFrame != null) {
                setEnabledStateOnViews(this.mWidgetFrame, z);
            }
        }
    }

    public boolean isWidgetFrameEnabled() {
        return this.mWidgetFrameEnabled;
    }

    private void setEnabledStateOnViews(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                setEnabledStateOnViews(viewGroup.getChildAt(childCount), z);
            }
        }
    }

    public void setTextLayoutClickListener(OnClickListener onClickListener) {
        this.mTextLayoutClickListener = onClickListener;
        if (this.mTextLayout != null) {
            this.mTextLayout.setOnClickListener(onClickListener);
        }
    }

    private void performClick() {
        try {
            if (sPerformClick == null) {
                sPerformClick = CheckBoxPreference.class.getMethod("performClick", new Class[]{PreferenceScreen.class});
                sPerformClick.setAccessible(true);
            }
            sPerformClick.invoke(this, new Object[]{null});
        } catch (Exception e) {
        }
    }

    public void setTextLayoutLongClickListener(OnLongClickListener onLongClickListener) {
        this.mTextLayoutLongClickListener = onLongClickListener;
        if (this.mTextLayout != null) {
            this.mTextLayout.setOnLongClickListener(onLongClickListener);
        }
    }
}
