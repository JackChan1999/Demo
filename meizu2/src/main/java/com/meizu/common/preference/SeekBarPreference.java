package com.meizu.common.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import com.meizu.common.R;

public class SeekBarPreference extends Preference {
    protected SeekBar mSeekBar;

    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.mz_preference_seekbar);
    }

    public void onBindView(View view) {
        super.onBindView(view);
        this.mSeekBar = (SeekBar) view.findViewById(R.id.seekbar);
    }

    public SeekBar getSeekBar() {
        return this.mSeekBar;
    }
}
