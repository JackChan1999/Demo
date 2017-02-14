package com.meizu.common.preference;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.meizu.common.a.d;

public class RingtonePreference extends android.preference.RingtonePreference {
    public RingtonePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSaveRingtone(Uri ringtoneUri) {
        RingtoneManager.setActualDefaultRingtoneUri(getContext(), getRingtoneType(), ringtoneUri);
    }

    protected Uri onRestoreRingtone() {
        return RingtoneManager.getActualDefaultRingtoneUri(getContext(), getRingtoneType());
    }

    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        if (view != null) {
            ViewGroup widgetFrame = (ViewGroup) view.findViewById(16908312);
            if (widgetFrame != null) {
                LayoutParams viewGroupParams = widgetFrame.getLayoutParams();
                if (viewGroupParams instanceof MarginLayoutParams) {
                    MarginLayoutParams params = (MarginLayoutParams) viewGroupParams;
                    params.rightMargin = 0;
                    params.width = getContext().getResources().getDimensionPixelSize(d.mz_preference_widget_icon_width);
                    widgetFrame.setLayoutParams(params);
                }
            }
        }
        return view;
    }
}
