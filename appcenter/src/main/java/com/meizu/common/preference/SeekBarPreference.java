package com.meizu.common.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import com.meizu.common.a.f;
import com.meizu.common.a.g;

public class SeekBarPreference extends Preference {
    protected SeekBar a;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(g.mz_preference_seekbar);
    }

    public void onBindView(View view) {
        super.onBindView(view);
        this.a = (SeekBar) view.findViewById(f.seekbar);
    }
}
