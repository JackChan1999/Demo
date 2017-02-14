package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.common.a.c;
import com.meizu.common.a.d;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.h;
import com.meizu.common.a.j;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class TimePicker extends FrameLayout {
    private int a;
    private int b;
    private Boolean c;
    private boolean d;
    private ScrollTextView e;
    private ScrollTextView f;
    private ScrollTextView g;
    private final String h;
    private final String i;
    private TextView j;
    private TextView k;
    private a l;
    private LinearLayout m;
    private int n;
    private int o;
    private int p;
    private Paint q;
    private boolean r;
    private boolean s;

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public /* synthetic */ Object createFromParcel(Parcel x0) {
                return a(x0);
            }

            public /* synthetic */ Object[] newArray(int x0) {
                return a(x0);
            }

            public SavedState a(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] a(int size) {
                return new SavedState[size];
            }
        };
        private final int a;
        private final int b;

        private SavedState(Parcelable superState, int hour, int minute) {
            super(superState);
            this.a = hour;
            this.b = minute;
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
            this.b = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.a);
            dest.writeInt(this.b);
        }
    }

    public interface a {
        void a(TimePicker timePicker, int i, int i2);
    }

    private class b implements com.meizu.common.widget.ScrollTextView.b {
        int a = 0;
        final /* synthetic */ TimePicker b;

        b(TimePicker timePicker, int i) {
            this.b = timePicker;
            this.a = i;
        }

        public void a(View view, int fromOld, int toNew) {
            switch (this.a) {
                case 1:
                    this.b.a = toNew;
                    break;
                case 2:
                    this.b.b = toNew;
                    break;
                case 3:
                    this.b.d = toNew == 0;
                    break;
                default:
                    return;
            }
            if (this.b.l != null) {
                this.b.l.a(this.b, this.b.getCurrentHour(), this.b.getCurrentMinute().intValue());
            }
            this.b.e();
        }

        public String c(int position) {
            switch (this.a) {
                case 1:
                    if (this.b.a()) {
                        return String.valueOf(position);
                    }
                    if (position == 0) {
                        position = 12;
                    }
                    return String.valueOf(position);
                case 2:
                    return String.valueOf(position);
                case 3:
                    if (position == 0) {
                        return this.b.h;
                    }
                    if (position == 1) {
                        return this.b.i;
                    }
                    break;
            }
            return null;
        }
    }

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = 0;
        this.b = 0;
        this.c = Boolean.valueOf(false);
        this.d = true;
        this.s = false;
        Calendar cal = Calendar.getInstance();
        try {
            this.a = cal.get(11);
            this.b = cal.get(12);
            this.c = Boolean.valueOf(DateFormat.is24HourFormat(context));
        } catch (Exception e) {
            this.a = 12;
            this.b = 30;
            this.c = Boolean.valueOf(true);
        }
        if (!this.c.booleanValue() && this.a >= 12) {
            this.a -= 12;
            this.d = false;
        }
        String[] dfsAmPm = new DateFormatSymbols().getAmPmStrings();
        this.h = dfsAmPm[0];
        this.i = dfsAmPm[1];
        b();
        this.n = 0;
        this.o = 0;
        this.p = context.getResources().getDimensionPixelSize(d.mc_custom_time_picker_line_width_padding);
        this.q = new Paint();
        TypedArray b = context.obtainStyledAttributes(j.MZTheme);
        int lineColor = b.getInt(j.MZTheme_mzThemeColor, context.getResources().getColor(c.mc_custom_date_picker_selected_gregorian_color));
        b.recycle();
        this.q.setColor(lineColor);
        this.q.setAntiAlias(true);
        this.q.setStrokeWidth((float) context.getResources().getDimensionPixelSize(d.mc_custom_time_picker_line_stroke_width));
        this.r = false;
        setWillNotDraw(false);
        this.m = (LinearLayout) findViewById(f.mc_column_parent);
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            this.s = accessibilityManager.isEnabled();
        }
        if (this.s) {
            setFocusable(true);
        }
        e();
    }

    private void b() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        if (this.c.booleanValue()) {
            c();
        } else {
            d();
        }
        int textUnitPaddingTop = this.j.getPaddingTop();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        float defaultScaledDensity = displayMetrics.scaledDensity;
        float nowScaledDensity = getResources().getDisplayMetrics().scaledDensity;
        float paddingTopOffset = ((this.j.getTextSize() / nowScaledDensity) * (nowScaledDensity - defaultScaledDensity)) / 1.3f;
        this.j.setPadding(this.j.getPaddingLeft(), (int) (((float) textUnitPaddingTop) - paddingTopOffset), this.j.getPaddingRight(), this.j.getPaddingBottom());
        this.k.setPadding(this.k.getPaddingLeft(), (int) (((float) textUnitPaddingTop) - paddingTopOffset), this.k.getPaddingRight(), this.k.getPaddingBottom());
        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    private void c() {
        if (this.c.booleanValue()) {
            inflate(getContext(), g.mc_time_picker_column_24, this);
            this.j = (TextView) findViewById(f.mc_scroll1_text);
            if (this.j != null) {
                this.j.setText(h.mc_date_time_hour);
            }
            this.k = (TextView) findViewById(f.mc_scroll2_text);
            if (this.k != null) {
                this.k.setText(h.mc_date_time_min);
            }
            this.e = (ScrollTextView) findViewById(f.mc_scroll1);
            this.e.setData(new b(this, 1), -1.0f, this.a, 24, 3, 0, 23, true);
            this.f = (ScrollTextView) findViewById(f.mc_scroll2);
            this.f.setData(new b(this, 2), -1.0f, this.b, 60, 3, 0, 59, true);
            this.g = null;
            this.e.post(new Runnable(this) {
                final /* synthetic */ TimePicker a;

                {
                    this.a = r1;
                }

                public void run() {
                    boolean isLayoutRtl;
                    if (this.a.e == null || VERSION.SDK_INT < 17 || this.a.e.getLayoutDirection() != 1) {
                        isLayoutRtl = false;
                    } else {
                        isLayoutRtl = true;
                    }
                    if (isLayoutRtl) {
                        ((FrameLayout) this.a.findViewById(f.mc_column1Layout)).setPadding(this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_hour_padding_left_rtl_24), 0, this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_hour_padding_right_rtl_24), 0);
                        LayoutParams hourLayoutParams = (LayoutParams) this.a.e.getLayoutParams();
                        hourLayoutParams.gravity = 5;
                        hourLayoutParams.rightMargin = 0;
                        hourLayoutParams.leftMargin = 0;
                        this.a.e.setLayoutParams(hourLayoutParams);
                        hourLayoutParams = (LayoutParams) this.a.j.getLayoutParams();
                        hourLayoutParams.gravity = 3;
                        hourLayoutParams.rightMargin = 0;
                        hourLayoutParams.leftMargin = 0;
                        this.a.j.setLayoutParams(hourLayoutParams);
                        ((FrameLayout) this.a.findViewById(f.mc_column2Layout)).setPadding(this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_minute_padding_left_rtl_24), 0, 0, 0);
                        LayoutParams minuteLayoutParams = (LayoutParams) this.a.f.getLayoutParams();
                        minuteLayoutParams.gravity = 5;
                        minuteLayoutParams.width = this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_minute_picker_width_rtl_24);
                        minuteLayoutParams.leftMargin = 0;
                        this.a.f.setLayoutParams(minuteLayoutParams);
                        minuteLayoutParams = (LayoutParams) this.a.k.getLayoutParams();
                        minuteLayoutParams.gravity = 3;
                        minuteLayoutParams.rightMargin = 0;
                        minuteLayoutParams.leftMargin = 0;
                        this.a.k.setLayoutParams(minuteLayoutParams);
                    }
                }
            });
        }
    }

    private void d() {
        if (!this.c.booleanValue()) {
            inflate(getContext(), g.mc_time_picker_column_12, this);
            this.j = (TextView) findViewById(f.mc_scroll1_text);
            if (this.j != null) {
                this.j.setText(h.mc_date_time_hour);
            }
            this.k = (TextView) findViewById(f.mc_scroll2_text);
            if (this.k != null) {
                this.k.setText(h.mc_date_time_min);
            }
            this.e = (ScrollTextView) findViewById(f.mc_scroll1);
            this.e.setData(new b(this, 1), -1.0f, this.a, 12, 3, 0, 11, true);
            this.f = (ScrollTextView) findViewById(f.mc_scroll2);
            this.f.setData(new b(this, 2), -1.0f, this.b, 60, 3, 0, 59, true);
            this.g = (ScrollTextView) findViewById(f.mc_scroll3);
            this.g.setData(new b(this, 3), -1.0f, this.d ? 0 : 1, 2, 3, 0, 1, false);
            this.g.setTextSize(getContext().getResources().getDimension(d.mz_picker_selected_ampm_size), getContext().getResources().getDimension(d.mz_picker_unselected_ampm_size));
            this.e.post(new Runnable(this) {
                final /* synthetic */ TimePicker a;

                {
                    this.a = r1;
                }

                public void run() {
                    boolean isLayoutRtl;
                    if (this.a.e == null || VERSION.SDK_INT < 17 || this.a.e.getLayoutDirection() != 1) {
                        isLayoutRtl = false;
                    } else {
                        isLayoutRtl = true;
                    }
                    if (isLayoutRtl) {
                        LayoutParams amPmLayoutParams = (LayoutParams) this.a.g.getLayoutParams();
                        amPmLayoutParams.rightMargin = 0;
                        amPmLayoutParams.leftMargin = 0;
                        this.a.g.setLayoutParams(amPmLayoutParams);
                        ((FrameLayout) this.a.findViewById(f.mc_column1Layout)).setPadding(this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_hour_padding_left_rtl_12), 0, 0, 0);
                        LayoutParams hourLayoutParams = (LayoutParams) this.a.e.getLayoutParams();
                        hourLayoutParams.gravity = 5;
                        hourLayoutParams.rightMargin = 0;
                        hourLayoutParams.leftMargin = this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_hour_picker_margin_left_rtl_12);
                        this.a.e.setLayoutParams(hourLayoutParams);
                        hourLayoutParams = (LayoutParams) this.a.j.getLayoutParams();
                        hourLayoutParams.gravity = 3;
                        hourLayoutParams.rightMargin = 0;
                        hourLayoutParams.leftMargin = 0;
                        this.a.j.setLayoutParams(hourLayoutParams);
                        LayoutParams minuteLayoutParams = (LayoutParams) this.a.f.getLayoutParams();
                        minuteLayoutParams.gravity = 5;
                        minuteLayoutParams.rightMargin = 0;
                        minuteLayoutParams.leftMargin = this.a.getContext().getResources().getDimensionPixelSize(d.mc_time_picker_minute_picker_margin_left_rtl_12);
                        this.a.f.setLayoutParams(minuteLayoutParams);
                        minuteLayoutParams = (LayoutParams) this.a.k.getLayoutParams();
                        minuteLayoutParams.gravity = 3;
                        minuteLayoutParams.rightMargin = 0;
                        minuteLayoutParams.leftMargin = 0;
                        this.a.k.setLayoutParams(minuteLayoutParams);
                    }
                }
            });
        }
    }

    public void a(int hour, int min, boolean is24hour) {
        int i = 0;
        boolean updateHourPicker = false;
        boolean updateMinPicker = false;
        if (!is24hour) {
            this.d = true;
            if (this.a != hour) {
                updateHourPicker = true;
                this.a = hour;
            }
            if (this.a >= 12) {
                this.a -= 12;
                this.d = false;
            }
        } else if (this.a != hour) {
            updateHourPicker = true;
            this.a = hour;
        }
        if (this.b != min) {
            updateMinPicker = true;
            this.b = min;
        }
        if (is24hour != this.c.booleanValue()) {
            this.c = Boolean.valueOf(is24hour);
            b();
        }
        if (updateHourPicker) {
            this.e.b(this.a);
        }
        if (updateMinPicker) {
            this.f.b(this.b);
        }
        if (this.g != null) {
            ScrollTextView scrollTextView = this.g;
            if (!this.d) {
                i = 1;
            }
            scrollTextView.b(i);
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.f.setEnabled(enabled);
        this.e.setEnabled(enabled);
        if (this.g != null) {
            this.g.setEnabled(enabled);
        }
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getCurrentHour(), this.b);
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        a(ss.a, ss.b, this.c.booleanValue());
    }

    public void setOnTimeChangedListener(a onTimeChangedListener) {
        this.l = onTimeChangedListener;
    }

    public int getCurrentHour() {
        if (this.c.booleanValue()) {
            return this.a;
        }
        if (this.d) {
            return this.a;
        }
        return this.a + 12;
    }

    public boolean a() {
        return this.c.booleanValue();
    }

    public Integer getCurrentMinute() {
        return Integer.valueOf(this.b);
    }

    public void setCurrentHour(Integer currentHour) {
        if (currentHour != null && currentHour.intValue() != getCurrentHour()) {
            a(currentHour.intValue(), this.b, this.c.booleanValue());
        }
    }

    public void setIs24HourView(Boolean is24HourView) {
        a(getCurrentHour(), this.b, is24HourView.booleanValue());
    }

    public void setCurrentMinute(Integer currentMinute) {
        a(getCurrentHour(), currentMinute.intValue(), this.c.booleanValue());
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            boolean is24Hour = this.c.booleanValue();
            try {
                is24Hour = DateFormat.is24HourFormat(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (is24Hour != this.c.booleanValue()) {
                setIs24HourView(Boolean.valueOf(is24Hour));
            }
        }
    }

    public void setTextColor(int selectedColor, int normalColor, int unitColor) {
        this.e.setTextColor(selectedColor, normalColor);
        this.f.setTextColor(selectedColor, normalColor);
        if (this.g != null) {
            this.g.setTextColor(selectedColor, normalColor);
        }
        this.j.setTextColor(unitColor);
        this.k.setTextColor(unitColor);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.r) {
            int pickerWidth = this.m.getWidth() - (this.p * 2);
            int paddingWdith = (getWidth() - pickerWidth) / 2;
            canvas.drawLine((float) paddingWdith, (float) this.n, (float) (paddingWdith + pickerWidth), (float) this.n, this.q);
            canvas.drawLine((float) paddingWdith, (float) this.o, (float) (paddingWdith + pickerWidth), (float) this.o, this.q);
        }
    }

    public void setIsDrawLine(boolean isDrawLine) {
        this.r = isDrawLine;
    }

    public void setLineHeight(int lineOneHeight, int lineTwoHeight) {
        this.n = lineOneHeight;
        this.o = lineTwoHeight;
    }

    private void e() {
        if (this.s) {
            String dateText = "";
            if (!this.c.booleanValue()) {
                dateText = dateText + a(3);
            }
            setContentDescription(dateText + a(1) + this.j.getText() + a(2) + this.k.getText());
            sendAccessibilityEvent(4);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (!this.s || event.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        String dateText = "";
        if (!this.c.booleanValue()) {
            dateText = dateText + a(3);
        }
        event.getText().add(dateText + a(1) + this.j.getText() + a(2) + this.k.getText());
        return false;
    }

    private String a(int type) {
        int position;
        switch (type) {
            case 1:
                position = this.a;
                if (a()) {
                    return String.valueOf(position);
                }
                if (position == 0) {
                    position = 12;
                }
                return String.valueOf(position);
            case 2:
                return String.valueOf(this.b);
            case 3:
                if (this.d) {
                    position = 0;
                } else {
                    position = 1;
                }
                if (position == 0) {
                    return this.h;
                }
                if (position == 1) {
                    return this.i;
                }
                break;
        }
        return "";
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(TimePicker.class.getName());
    }
}
