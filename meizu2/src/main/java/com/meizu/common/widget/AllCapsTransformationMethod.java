package com.meizu.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import java.util.Locale;

class AllCapsTransformationMethod implements TransformationMethod2 {
    private static final String TAG = "AllCapsTransformationMethod";
    private boolean mEnabled;
    private Locale mLocale;

    public AllCapsTransformationMethod(Context context) {
        this.mLocale = context.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence charSequence, View view) {
        if (this.mEnabled) {
            return charSequence != null ? charSequence.toString().toUpperCase(this.mLocale) : null;
        } else {
            return charSequence;
        }
    }

    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
    }

    public void setLengthChangesAllowed(boolean z) {
        this.mEnabled = z;
    }
}
