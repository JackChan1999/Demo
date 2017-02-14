package com.meizu.common.preference;

import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.meizu.common.R;

public class RingtonePreference extends android.preference.RingtonePreference {
    private static final String TAG = "com.meizu.common.preference.RingtonePreference";

    public RingtonePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onSaveRingtone(Uri uri) {
        RingtoneManager.setActualDefaultRingtoneUri(getContext(), getRingtoneType(), uri);
    }

    protected Uri onRestoreRingtone() {
        return RingtoneManager.getActualDefaultRingtoneUri(getContext(), getRingtoneType());
    }

    protected View onCreateView(ViewGroup viewGroup) {
        View onCreateView = super.onCreateView(viewGroup);
        if (onCreateView != null) {
            ViewGroup viewGroup2 = (ViewGroup) onCreateView.findViewById(16908312);
            if (viewGroup2 != null) {
                LayoutParams layoutParams = viewGroup2.getLayoutParams();
                if (layoutParams instanceof MarginLayoutParams) {
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
                    marginLayoutParams.rightMargin = 0;
                    marginLayoutParams.width = getContext().getResources().getDimensionPixelSize(R.dimen.mz_preference_widget_icon_width);
                    viewGroup2.setLayoutParams(marginLayoutParams);
                }
            }
        }
        return onCreateView;
    }
}
