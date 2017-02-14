package com.meizu.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.common.a.c;
import com.meizu.common.a.d;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.h;
import com.meizu.common.a.j;
import com.meizu.common.widget.ScrollTextView.e;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class DatePicker extends FrameLayout {
    private float A;
    private float B;
    private int C;
    private int D;
    private int E;
    private int F;
    private LinearLayout G;
    private int H;
    private int I;
    private int J;
    private Paint K;
    private boolean L;
    private boolean M;
    String a;
    String[] b;
    String[] c;
    String d;
    boolean e;
    private TextView f;
    private TextView g;
    private TextView h;
    private ScrollTextView i;
    private ScrollTextView j;
    private ScrollTextView k;
    private boolean l;
    private b m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private String[] s;
    private Object t;
    private volatile Locale u;
    private String[] v;
    private int w;
    private int x;
    private int y;
    private int z;

    public interface b {
        void a(DatePicker datePicker, int i, int i2, int i3);
    }

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
        private final int c;

        private SavedState(Parcelable superState, int year, int month, int day) {
            super(superState);
            this.a = year;
            this.b = month;
            this.c = day;
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
            this.b = in.readInt();
            this.c = in.readInt();
        }

        public int a() {
            return this.a;
        }

        public int b() {
            return this.b;
        }

        public int c() {
            return this.c;
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.a);
            dest.writeInt(this.b);
            dest.writeInt(this.c);
        }
    }

    private class a implements com.meizu.common.widget.ScrollTextView.b {
        int a = 0;
        final /* synthetic */ DatePicker b;

        a(DatePicker datePicker, int i) {
            this.b = datePicker;
            this.a = i;
        }

        public String c(int position) {
            switch (this.a) {
                case 1:
                    return String.valueOf(this.b.q + position);
                case 2:
                    if (this.b.l) {
                        return this.b.a(position);
                    }
                    if (position < this.b.s.length) {
                        return this.b.s[position];
                    }
                    break;
                case 3:
                    if (this.b.l) {
                        return this.b.b(position);
                    }
                    return String.valueOf(position + 1);
            }
            return null;
        }

        public void a(View view, int fromOld, int toNew) {
            int maxdays = this.b.getMonthDays();
            int maxmonths = this.b.getYearMonths();
            switch (this.a) {
                case 1:
                    this.b.p = this.b.q + toNew;
                    if (!(maxmonths == this.b.getYearMonths() || this.b.j == null)) {
                        maxmonths = this.b.getYearMonths();
                        this.b.j.a(maxmonths);
                        if (maxmonths - 1 < this.b.o) {
                            this.b.n = maxdays;
                            this.b.o = maxmonths - 1;
                            this.b.j.setCurrentItem(this.b.o, true);
                        }
                    }
                    if (!(maxdays == this.b.getMonthDays() || this.b.i == null)) {
                        maxdays = this.b.getMonthDays();
                        this.b.i.a(maxdays);
                        if (maxdays < this.b.n) {
                            this.b.n = maxdays;
                            this.b.i.setCurrentItem(this.b.n - 1, true);
                            break;
                        }
                    }
                    break;
                case 2:
                    this.b.o = toNew;
                    if (!(maxdays == this.b.getMonthDays() || this.b.i == null)) {
                        maxdays = this.b.getMonthDays();
                        this.b.i.a(maxdays);
                        if (maxdays < this.b.n) {
                            this.b.n = maxdays;
                            this.b.i.setCurrentItem(this.b.n - 1, true);
                            break;
                        }
                    }
                    break;
                case 3:
                    this.b.n = toNew + 1;
                    break;
                default:
                    return;
            }
            if (this.b.m != null) {
                this.b.m.a(this.b, this.b.p, this.b.o, this.b.n);
            }
            this.b.c();
        }
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.l = false;
        this.t = new Object();
        this.w = 5;
        this.x = g.mc_date_picker;
        this.M = false;
        this.e = false;
        TypedArray a = context.obtainStyledAttributes(attrs, j.DatePicker);
        this.q = a.getInt(j.DatePicker_mcStartYear, 1970);
        this.r = a.getInt(j.DatePicker_mcEndYear, 2037);
        this.x = a.getResourceId(j.DatePicker_mcInternalLayout, this.x);
        this.w = a.getInt(j.DatePicker_mcVisibleRow, this.w);
        this.B = a.getDimension(j.DatePicker_mcSelectItemHeight, this.B);
        this.A = a.getDimension(j.DatePicker_mcNormalItemHeight, this.A);
        a.recycle();
        inflate(getContext(), this.x, this);
        this.g = (TextView) findViewById(f.mc_scroll1_text);
        if (this.g != null) {
            this.g.setText(h.mc_date_time_month);
        }
        this.f = (TextView) findViewById(f.mc_scroll2_text);
        if (this.f != null) {
            this.f.setText(h.mc_date_time_day);
        }
        this.h = (TextView) findViewById(f.mc_scroll3_text);
        if (this.h != null) {
            this.h.setText(h.mc_date_time_year);
        }
        Calendar cal = Calendar.getInstance();
        this.p = cal.get(1);
        this.o = cal.get(2);
        this.n = cal.get(5);
        this.m = null;
        int max = cal.getActualMaximum(5);
        this.G = (LinearLayout) findViewById(f.mc_column_parent);
        this.i = (ScrollTextView) findViewById(f.mc_scroll2);
        if (!(this.B == 0.0f || this.A == 0.0f)) {
            this.i.setItemHeight((float) ((int) this.B), (float) ((int) this.A));
        }
        this.i.setData(new a(this, 3), -1.0f, this.n - 1, max, this.w, 0, max - 1, true);
        this.j = (ScrollTextView) findViewById(f.mc_scroll1);
        if (!(this.B == 0.0f || this.A == 0.0f)) {
            this.j.setItemHeight((float) ((int) this.B), (float) ((int) this.A));
        }
        this.s = getShortMonths();
        this.j.setData(new a(this, 2), -1.0f, this.o, 12, this.w, 0, 11, true);
        this.k = (ScrollTextView) findViewById(f.mc_scroll3);
        this.q -= this.w;
        this.r += this.w;
        if (!(this.B == 0.0f || this.A == 0.0f)) {
            this.k.setItemHeight((float) ((int) this.B), (float) ((int) this.A));
        }
        a();
        a(this.s);
        this.g.setVisibility(a(this.s[0]) ? 0 : 8);
        b();
        int textUnitPaddingTop = this.h.getPaddingTop();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics.setToDefaults();
        float defaultScaledDensity = displayMetrics.scaledDensity;
        float nowScaledDensity = getResources().getDisplayMetrics().scaledDensity;
        float paddingTopOffset = ((this.h.getTextSize() / nowScaledDensity) * (nowScaledDensity - defaultScaledDensity)) / 1.3f;
        this.h.setPadding(this.h.getPaddingLeft(), (int) (((float) textUnitPaddingTop) - paddingTopOffset), this.h.getPaddingRight(), this.h.getPaddingBottom());
        this.g.setPadding(this.g.getPaddingLeft(), (int) (((float) textUnitPaddingTop) - paddingTopOffset), this.g.getPaddingRight(), this.g.getPaddingBottom());
        this.f.setPadding(this.f.getPaddingLeft(), (int) (((float) textUnitPaddingTop) - paddingTopOffset), this.f.getPaddingRight(), this.f.getPaddingBottom());
        if (!isEnabled()) {
            setEnabled(false);
        }
        this.D = context.getResources().getDimensionPixelOffset(d.mc_date_picker_normal_lunar_size);
        this.C = context.getResources().getDimensionPixelOffset(d.mc_date_picker_selected_lunar_size);
        this.F = context.getResources().getDimensionPixelOffset(d.mc_picker_normal_number_size);
        this.E = context.getResources().getDimensionPixelOffset(d.mc_picker_selected_number_size);
        this.H = 0;
        this.I = 0;
        this.J = context.getResources().getDimensionPixelSize(d.mc_custom_time_picker_line_width_padding);
        this.K = new Paint();
        TypedArray b = context.obtainStyledAttributes(j.MZTheme);
        int lineColor = b.getInt(j.MZTheme_mzThemeColor, context.getResources().getColor(c.mc_custom_date_picker_selected_gregorian_color));
        b.recycle();
        this.K.setColor(lineColor);
        this.K.setAntiAlias(true);
        this.K.setStrokeWidth((float) context.getResources().getDimensionPixelSize(d.mc_custom_time_picker_line_stroke_width));
        this.L = false;
        setWillNotDraw(false);
        this.b = getResources().getStringArray(com.meizu.common.a.a.mc_custom_time_picker_lunar_month);
        this.c = getResources().getStringArray(com.meizu.common.a.a.mc_custom_time_picker_lunar_day);
        this.d = getResources().getString(h.mc_time_picker_leap);
        if (Build.DEVICE.equals("mx4pro")) {
            this.k.a(new e(this) {
                final /* synthetic */ DatePicker a;

                {
                    this.a = r1;
                }

                public void a(ScrollTextView view) {
                }

                public void b(ScrollTextView view) {
                    view.setIsDrawFading(true);
                }
            });
            this.j.a(new e(this) {
                final /* synthetic */ DatePicker a;

                {
                    this.a = r1;
                }

                public void a(ScrollTextView view) {
                }

                public void b(ScrollTextView view) {
                    view.setIsDrawFading(true);
                }
            });
            this.i.a(new e(this) {
                final /* synthetic */ DatePicker a;

                {
                    this.a = r1;
                }

                public void a(ScrollTextView view) {
                }

                public void b(ScrollTextView view) {
                    this.a.setIsDrawFading(true);
                }
            });
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        if (accessibilityManager != null) {
            this.M = accessibilityManager.isEnabled();
        }
        if (this.M) {
            setFocusable(true);
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.i.setEnabled(enabled);
        this.j.setEnabled(enabled);
        this.k.setEnabled(enabled);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(DatePicker.class.getName());
    }

    private void a(String[] months) {
        DateFormat format;
        if (months[0].startsWith(PushConstants.CLICK_TYPE_ACTIVITY)) {
            format = android.text.format.DateFormat.getDateFormat(getContext());
        } else {
            format = android.text.format.DateFormat.getMediumDateFormat(getContext());
        }
        if (format instanceof SimpleDateFormat) {
            this.a = ((SimpleDateFormat) format).toPattern();
        } else {
            this.a = new String(android.text.format.DateFormat.getDateFormatOrder(getContext()));
        }
        FrameLayout monthLayout = (FrameLayout) findViewById(f.mc_column1Layout);
        FrameLayout dayLayout = (FrameLayout) findViewById(f.mc_column2Layout);
        View yearLayout = (FrameLayout) findViewById(f.mc_column3Layout);
        ImageView divider1 = (ImageView) findViewById(f.mc_divider_bar1);
        ImageView divider2 = (ImageView) findViewById(f.mc_divider_bar2);
        LinearLayout parent = (LinearLayout) findViewById(f.mc_column_parent);
        parent.removeAllViews();
        boolean quoted = false;
        boolean didDay = false;
        boolean didMonth = false;
        boolean didYear = false;
        boolean didDiv1 = false;
        boolean didDiv2 = false;
        for (int i = 0; i < this.a.length(); i++) {
            char c = this.a.charAt(i);
            if (c == '\'') {
                quoted = !quoted;
            }
            if (!quoted) {
                boolean need_divider = false;
                if (c == 'd' && !didDay) {
                    parent.addView(dayLayout);
                    didDay = true;
                    need_divider = true;
                } else if ((c == 'M' || c == 'L') && !didMonth) {
                    parent.addView(monthLayout);
                    didMonth = true;
                    need_divider = true;
                } else if (c == 'y' && !didYear) {
                    parent.addView(yearLayout);
                    didYear = true;
                    need_divider = true;
                }
                if (true == need_divider) {
                    if (!didDiv1) {
                        parent.addView(divider1);
                        didDiv1 = true;
                    } else if (!didDiv2) {
                        parent.addView(divider2);
                        didDiv2 = true;
                    }
                }
            }
        }
        if (!didMonth) {
            parent.addView(monthLayout);
        }
        if (!didDay) {
            parent.addView(dayLayout);
        }
        if (!didYear) {
            parent.addView(yearLayout);
        }
        this.k.post(new Runnable(this) {
            final /* synthetic */ DatePicker a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.k == null || VERSION.SDK_INT < 17 || this.a.k.getLayoutDirection() != 1) {
                    this.a.e = false;
                } else {
                    this.a.e = true;
                }
                if (this.a.e) {
                    FrameLayout dayParent = (FrameLayout) this.a.findViewById(f.mc_column2Layout);
                    LayoutParams dayParentLayoutParams = (LayoutParams) dayParent.getLayoutParams();
                    dayParentLayoutParams.rightMargin = 0;
                    dayParentLayoutParams.leftMargin = 0;
                    dayParent.setLayoutParams(dayParentLayoutParams);
                    LayoutParams yearLayoutParams = (LayoutParams) this.a.k.getLayoutParams();
                    yearLayoutParams.gravity = 5;
                    yearLayoutParams.rightMargin = 0;
                    yearLayoutParams.leftMargin = 0;
                    this.a.k.setLayoutParams(yearLayoutParams);
                    yearLayoutParams = (LayoutParams) this.a.h.getLayoutParams();
                    yearLayoutParams.gravity = 3;
                    yearLayoutParams.rightMargin = 0;
                    yearLayoutParams.leftMargin = 0;
                    this.a.h.setLayoutParams(yearLayoutParams);
                    LayoutParams monthLayoutParams = (LayoutParams) this.a.j.getLayoutParams();
                    monthLayoutParams.gravity = 5;
                    monthLayoutParams.rightMargin = 0;
                    monthLayoutParams.leftMargin = 0;
                    this.a.j.setLayoutParams(monthLayoutParams);
                    monthLayoutParams = (LayoutParams) this.a.g.getLayoutParams();
                    monthLayoutParams.gravity = 3;
                    monthLayoutParams.rightMargin = 0;
                    monthLayoutParams.leftMargin = 0;
                    this.a.g.setLayoutParams(yearLayoutParams);
                    LayoutParams dayLayoutParams = (LayoutParams) this.a.i.getLayoutParams();
                    dayLayoutParams.gravity = 5;
                    dayLayoutParams.rightMargin = 0;
                    dayLayoutParams.leftMargin = 0;
                    this.a.i.setLayoutParams(dayLayoutParams);
                    dayLayoutParams = (LayoutParams) this.a.f.getLayoutParams();
                    dayLayoutParams.gravity = 3;
                    dayLayoutParams.rightMargin = 0;
                    dayLayoutParams.leftMargin = 0;
                    this.a.f.setLayoutParams(dayLayoutParams);
                }
            }
        });
    }

    public void a(int year, int monthOfYear, int dayOfMonth, boolean doAnimate) {
        a(year, monthOfYear, dayOfMonth, doAnimate, true);
    }

    private void a(int year, int monthOfYear, int dayOfMonth, boolean doAnimate, boolean isReorderPickers) {
        this.p = year;
        this.o = monthOfYear;
        this.n = dayOfMonth;
        this.s = null;
        this.s = getShortMonths();
        this.k.setCurrentItem(this.p - this.q, doAnimate);
        if (this.y != getYearMonths()) {
            this.y = getYearMonths();
            this.j.a(this.y);
        }
        this.j.setCurrentItem(this.o, doAnimate);
        if (this.z != getMonthDays()) {
            this.z = getMonthDays();
            this.i.a(getMonthDays());
        }
        this.i.setCurrentItem(this.n - 1, doAnimate);
        if (isReorderPickers) {
            a(this.s);
        }
    }

    private String[] getShortMonths() {
        Locale currentLocale = Locale.getDefault();
        if (currentLocale.equals(this.u) && this.v != null) {
            return this.v;
        }
        synchronized (this.t) {
            if (!currentLocale.equals(this.u)) {
                int i;
                this.v = new String[12];
                for (i = 0; i < 12; i++) {
                    this.v[i] = DateUtils.getMonthString(i + 0, 20);
                }
                if (this.v[0].startsWith(PushConstants.CLICK_TYPE_ACTIVITY)) {
                    for (i = 0; i < this.v.length; i++) {
                        this.v[i] = String.valueOf(i + 1);
                    }
                }
                this.u = currentLocale;
            }
        }
        return this.v;
    }

    private String a(int position) {
        int leapMonth = com.meizu.common.util.b.a(this.p);
        if (leapMonth == 0) {
            if (position >= 12) {
                return null;
            }
        } else if (position >= 13) {
            return null;
        }
        if (leapMonth == 0 || position <= leapMonth - 1) {
            return this.b[position];
        }
        if (position != leapMonth) {
            return this.b[position - 1];
        }
        if (this.e) {
            return "  " + this.d + this.b[position - 1];
        }
        return this.d + this.b[position - 1] + "  ";
    }

    private String b(int position) {
        if (position > this.c.length - 1) {
            return null;
        }
        return this.c[position];
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.L) {
            int pickerWidth = this.G.getWidth() - (this.J * 2);
            int paddingWdith = (getWidth() - pickerWidth) / 2;
            canvas.drawLine((float) paddingWdith, (float) this.H, (float) (paddingWdith + pickerWidth), (float) this.H, this.K);
            canvas.drawLine((float) paddingWdith, (float) this.I, (float) (paddingWdith + pickerWidth), (float) this.I, this.K);
        }
    }

    public void setIsDrawLine(boolean isDrawLine) {
        this.L = isDrawLine;
    }

    public void setLineHeight(int lineOneHeight, int lineTwoHeight) {
        this.H = lineOneHeight;
        this.I = lineTwoHeight;
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.p, this.o, this.n);
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.p = ss.a();
        this.o = ss.b();
        this.n = ss.c();
    }

    public void a(int year, int monthOfYear, int dayOfMonth, b onDateChangedListener, boolean doAnimate) {
        if (!(this.p == year && this.o == monthOfYear && this.n == dayOfMonth)) {
            a(year, monthOfYear, dayOfMonth, doAnimate);
        }
        this.m = onDateChangedListener;
        c();
    }

    public int getYear() {
        return this.p;
    }

    public int getMonth() {
        return this.o;
    }

    public int getDayOfMonth() {
        return this.n;
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            DateFormat format;
            String order;
            String[] months = getShortMonths();
            if (months[0].startsWith(PushConstants.CLICK_TYPE_ACTIVITY)) {
                format = android.text.format.DateFormat.getDateFormat(getContext());
            } else {
                format = android.text.format.DateFormat.getMediumDateFormat(getContext());
            }
            if (format instanceof SimpleDateFormat) {
                order = ((SimpleDateFormat) format).toPattern();
            } else {
                order = new String(android.text.format.DateFormat.getDateFormatOrder(getContext()));
            }
            if (!this.a.equals(order)) {
                this.s = months;
                a(this.s);
            }
        }
    }

    private void a() {
        this.k.setData(new a(this, 1), -1.0f, this.p - this.q, (this.r - this.q) + 1, this.w, this.w, (this.r - this.q) - this.w, false);
    }

    public void setMaxDate(long maxDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(maxDate);
        int newYear = cal.get(1);
        if (newYear != this.r && newYear > this.q) {
            this.r = newYear;
            this.r += this.w;
            a();
        }
    }

    public void setMinDate(long minDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(minDate);
        int newYear = cal.get(1);
        if (newYear != this.q && newYear < this.r) {
            this.q = newYear;
            this.q -= this.w;
            a();
        }
    }

    public void setLunar(boolean isLunar, boolean isAnimation) {
        this.l = isLunar;
        int[] date = new int[]{this.p, this.o + 1, this.n, 0};
        int solarYear = date[0];
        int leapMonth = com.meizu.common.util.b.a(date[0]);
        int leapMonthLastYear = com.meizu.common.util.b.a(date[0] - 1);
        if (this.l) {
            date = com.meizu.common.util.b.a(date[0], date[1], date[2]);
            if (!(solarYear == date[0] || leapMonthLastYear == 0 || (date[3] != 1 && date[1] <= leapMonthLastYear)) || (solarYear == date[0] && leapMonth != 0 && (date[3] == 1 || date[1] > leapMonth))) {
                date[1] = date[1] + 1;
            }
            this.j.setTextSize((float) this.C, (float) this.D);
            this.i.setTextSize((float) this.C, (float) this.D);
            this.f.setAlpha(0.0f);
        } else {
            boolean lunar = false;
            int month = 0;
            if (leapMonth == 0 || leapMonth >= date[1]) {
                month = date[1];
            } else if (leapMonth + 1 == date[1]) {
                month = date[1] - 1;
                lunar = true;
            } else if (leapMonth + 1 < date[1]) {
                month = date[1] - 1;
            }
            this.j.setTextSize((float) this.E, (float) this.F);
            this.i.setTextSize((float) this.E, (float) this.F);
            this.f.setAlpha(1.0f);
            date = com.meizu.common.util.b.a(date[0], month, date[2], lunar);
        }
        a(date[0], date[1] + -1 < 0 ? 12 : date[1] - 1, date[2], isAnimation, false);
    }

    public void setLunar(boolean isLunar) {
        setLunar(isLunar, true);
    }

    private int getMonthDays() {
        if (this.l) {
            int m = this.o;
            int leapMonth = com.meizu.common.util.b.a(this.p);
            boolean isLeapMonth = false;
            if (leapMonth != 0) {
                isLeapMonth = leapMonth == m;
            }
            if (leapMonth == 0 || (leapMonth != 0 && this.o < leapMonth)) {
                m++;
            }
            return com.meizu.common.util.b.a(this.p, m, isLeapMonth);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        cal.set(1, this.p);
        cal.set(2, this.o);
        return cal.getActualMaximum(5);
    }

    private int getYearMonths() {
        if (!this.l || com.meizu.common.util.b.a(this.p) == 0) {
            return 12;
        }
        return 13;
    }

    private void b() {
        this.g = (TextView) findViewById(f.mc_scroll1_text);
        if (this.g != null) {
            this.g.setText(h.mc_date_time_month);
        }
        this.f = (TextView) findViewById(f.mc_scroll2_text);
        if (this.f != null) {
            this.f.setText(h.mc_date_time_day);
        }
        this.h = (TextView) findViewById(f.mc_scroll3_text);
        if (this.h != null) {
            this.h.setText(h.mc_date_time_year);
        }
    }

    public static boolean a(String str) {
        return Pattern.compile("[0-9]*").matcher(str).matches();
    }

    public void setTextColor(int selectedColor, int normalColor, int unitColor) {
        this.i.setTextColor(selectedColor, normalColor);
        this.j.setTextColor(selectedColor, normalColor);
        this.k.setTextColor(selectedColor, normalColor);
        this.f.setTextColor(unitColor);
        this.g.setTextColor(unitColor);
        this.h.setTextColor(unitColor);
    }

    public void setItemHeight(int selectHeight, int normalHeight) {
        this.i.setItemHeight((float) selectHeight, (float) normalHeight);
        this.j.setItemHeight((float) selectHeight, (float) normalHeight);
        this.k.setItemHeight((float) selectHeight, (float) normalHeight);
    }

    public TextView getDayUnit() {
        return this.f;
    }

    public void setIsDrawFading(boolean isDrawFading) {
        this.k.setIsDrawFading(isDrawFading);
        this.j.setIsDrawFading(isDrawFading);
        this.i.setIsDrawFading(isDrawFading);
    }

    private String c(int type) {
        int position;
        switch (type) {
            case 1:
                return String.valueOf(this.p);
            case 2:
                position = this.o;
                if (this.l) {
                    return a(position);
                }
                if (this.s == null) {
                    this.s = getShortMonths();
                }
                if (position < this.s.length) {
                    return this.s[position];
                }
                break;
            case 3:
                position = this.n - 1;
                if (this.l) {
                    return b(position);
                }
                return String.valueOf(position + 1);
        }
        return "";
    }

    private void c() {
        if (this.M) {
            setContentDescription((c(1) + this.h.getText() + c(2) + this.g.getText() + c(3) + this.f.getText()).replace(" ", "").replace("廿十", "二十").replace("廿", "二十"));
            sendAccessibilityEvent(4);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        if (!this.M || event.getEventType() != 32) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }
        event.getText().add((c(1) + this.h.getText() + c(2) + this.g.getText() + c(3) + this.f.getText()).replace(" ", "").replace("廿十", "二十").replace("廿", "二十"));
        return false;
    }
}
