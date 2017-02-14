package com.meizu.common.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.common.a.d;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.h;
import com.meizu.common.widget.ScrollTextView.e;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CustomTimePicker extends FrameLayout {
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private int G;
    private int H;
    private FrameLayout I;
    private int a;
    private int b;
    private Boolean c;
    private boolean d;
    private a e;
    private b f;
    private ScrollTextView g;
    private ScrollTextView h;
    private ScrollTextView i;
    private final String j;
    private final String k;
    private TextView l;
    private TextView m;
    private TextView n;
    private TextView o;
    private boolean p;
    private boolean q;
    private Object r;
    private volatile Locale s;
    private String[] t;
    private int u;
    private int v;
    private int w;
    private int x;
    private final Calendar y;
    private int z;

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
        private final int d;
        private final int e;
        private final int f;
        private final int g;

        private SavedState(Parcelable superState, int hour, int minute, int year, int month, int day, boolean isLunar, boolean isLeapMonth) {
            int i;
            int i2 = 1;
            super(superState);
            this.a = hour;
            this.b = minute;
            this.c = year;
            this.d = month;
            this.e = day;
            if (isLunar) {
                i = 1;
            } else {
                i = 0;
            }
            this.f = i;
            if (!isLeapMonth) {
                i2 = 0;
            }
            this.g = i2;
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readInt();
            this.b = in.readInt();
            this.c = in.readInt();
            this.d = in.readInt();
            this.e = in.readInt();
            this.f = in.readInt();
            this.g = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.a);
            dest.writeInt(this.b);
            dest.writeInt(this.c);
            dest.writeInt(this.d);
            dest.writeInt(this.e);
            dest.writeInt(this.f);
            dest.writeInt(this.g);
        }
    }

    private class a implements e {
        final /* synthetic */ CustomTimePicker a;
        private ScrollTextView b;
        private int c;
        private int d;

        public a(CustomTimePicker customTimePicker, ScrollTextView picker) {
            this.a = customTimePicker;
            this.b = picker;
        }

        public void a(int selectHeight) {
            this.b.setSelectItemHeight((float) selectHeight);
        }

        public void a(c dataAdapter, int lineOffset, int currentItem, int count, int oneScreenCount, int validStart, int validEnd, boolean cycleEnabled) {
            c(validEnd);
            d(validStart);
            this.b.setData(dataAdapter, (float) lineOffset, currentItem, count, oneScreenCount, 0, count - 1, cycleEnabled);
            this.b.a((e) this);
        }

        public int a() {
            return this.b.getCurrentItem();
        }

        public void a(int index, boolean animated) {
            this.b.setCurrentItem(index, animated);
        }

        public void b(int count) {
            this.b.a(count);
        }

        public void a(int selectedColor, int normalColor) {
            this.b.setTextColor(selectedColor, normalColor);
        }

        public void b(int selectedSize, int normalSize) {
            this.b.setTextSize((float) selectedSize, (float) normalSize);
        }

        public void a(ScrollTextView view) {
        }

        public int b() {
            return this.c;
        }

        public int c() {
            return this.d;
        }

        public void c(int id) {
            this.c = id;
        }

        public void d(int id) {
            this.d = id;
        }

        public void b(ScrollTextView view) {
            this.b.setCurrentItem(Math.max(Math.min(this.b.getCurrentItem(), b()), c()), true);
        }
    }

    private class b implements ScrollTextView.b {
        final /* synthetic */ CustomTimePicker a;
        private ScrollTextView b;
        private String[] c;
        private int[] d = new int[4];
        private int[] e = new int[4];
        private int[] f = new int[4];
        private int[] g = new int[4];
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int m;

        public b(CustomTimePicker customTimePicker, ScrollTextView picker) {
            this.a = customTimePicker;
            this.b = picker;
            this.c = c();
            b();
        }

        public void a(int selectHeight) {
            this.b.setSelectItemHeight((float) selectHeight);
        }

        public int a(int[] data) {
            int id = a();
            if (this.a.p) {
                if (id >= this.j) {
                    id -= this.j - 1;
                    if (data != null) {
                        data[0] = this.g[0];
                    }
                    if (id != this.m + 1 || data == null) {
                        data[1] = 0;
                    } else {
                        data[1] = 1;
                    }
                    if (this.m == 0 || id <= this.m) {
                        return id;
                    }
                    return id - 1;
                }
                id += this.f[1];
                if (data != null) {
                    data[0] = this.f[0];
                }
                if (this.l == 0) {
                    data[1] = 0;
                    return id;
                } else if (this.f[3] == 1 && id == this.l) {
                    data[1] = 1;
                    return id;
                } else if (this.j <= (12 - this.l) + 1 || id <= this.l) {
                    data[1] = 0;
                    return id;
                } else {
                    data[1] = 1;
                    return id - 1;
                }
            } else if (id > 12 - this.d[1]) {
                id -= 12 - this.d[1];
                if (data == null) {
                    return id;
                }
                data[0] = this.e[0];
                data[1] = 0;
                return id;
            } else {
                id += this.d[1];
                if (data == null) {
                    return id;
                }
                data[0] = this.d[0];
                data[1] = 0;
                return id;
            }
        }

        public void a(int year, int month, int day, boolean isleapmonth) {
            if (month >= 0) {
                this.a.q = isleapmonth;
                if (this.a.p) {
                    if (year == this.f[0]) {
                        if (this.l != 0 && this.f[0] <= this.l && this.f[3] != 1 && (month > this.l || (this.l == month && isleapmonth))) {
                            month++;
                        }
                        a(this.a.D, month - this.f[1]);
                    } else if (year == this.g[0]) {
                        if (this.m != 0 && (month > this.m || (this.m == month && isleapmonth))) {
                            month++;
                        }
                        a(this.a.D, (this.j + month) - 1);
                    }
                } else if (month <= 12) {
                    if (year == this.d[0] && month >= this.d[1]) {
                        a(12, Math.min(11, month - this.d[1]));
                    } else if (year == this.e[0] && month <= this.e[1]) {
                        a(12, 11 - (this.e[1] - month));
                    }
                }
                b(day);
            }
        }

        public void b(int day) {
            int maxdays = this.a.b(this.a.x, this.a.w, this.a.p);
            if (this.a.p) {
                if (this.a.x == this.f[0] && this.a.w == this.f[1]) {
                    this.a.e.d(this.f[2] - 1);
                } else {
                    this.a.e.d(0);
                }
                if (this.a.x == this.g[0] && this.a.w == this.g[1]) {
                    this.a.e.c(this.g[2] - 1);
                } else {
                    this.a.e.c(maxdays - 1);
                }
            } else {
                if (this.a.x == this.d[0] && this.a.w == this.d[1]) {
                    this.a.e.d(this.d[2] - 1);
                } else {
                    this.a.e.d(0);
                }
                if (this.a.x == this.e[0] && this.a.w == this.e[1]) {
                    this.a.e.c(this.e[2] - 1);
                } else {
                    this.a.e.c(maxdays - 1);
                }
            }
            this.a.v = day;
            this.a.v = Math.min(this.a.v, maxdays);
            this.a.v = Math.min(this.a.v, this.a.e.b() + 1);
            this.a.v = Math.max(this.a.v, this.a.e.c() + 1);
            this.a.e.a(this.a.v - 1, true);
        }

        public int a() {
            return this.b.getCurrentItem() - (this.a.u / 2);
        }

        public void a(int count, int current) {
            this.b.a(((this.a.u / 2) * 2) + count, (this.a.u / 2) + current);
        }

        public void b(int selectedSize, int normalSize) {
            this.b.setTextSize((float) selectedSize, (float) normalSize);
        }

        public void c(int selectedColor, int normalColor) {
            this.b.setTextColor(selectedColor, normalColor);
        }

        public void a(ScrollTextView.b dataAdapter, float lineOffset, int currentItem, int count, int oneScreenCount, int validStart, int validEnd, boolean cycleEnabled) {
            this.h = validStart;
            this.i = (((oneScreenCount / 2) * 2) + validEnd) + 1;
            this.b.setData(this, lineOffset, currentItem, count + ((oneScreenCount / 2) * 2), oneScreenCount, this.h, this.i, cycleEnabled);
        }

        private void b() {
            int i;
            this.a.y.setTimeInMillis(System.currentTimeMillis());
            this.d[0] = this.a.y.get(1);
            this.d[1] = this.a.y.get(2) + 1;
            this.d[2] = this.a.y.get(5);
            this.d[3] = 0;
            this.e[0] = this.d[1] == 1 ? this.d[0] : this.d[0] + 1;
            int[] iArr = this.e;
            if (this.d[1] - 1 <= 0) {
                i = 12;
            } else {
                i = this.d[1] - 1;
            }
            iArr[1] = i;
            this.e[2] = this.a.b(this.e[0], this.e[1], false);
            this.e[3] = 0;
            this.f = com.meizu.common.util.b.a(this.d[0], this.d[1], this.d[2]);
            this.g = com.meizu.common.util.b.a(this.e[0], this.e[1], this.e[2]);
            if (this.f[0] == this.g[0]) {
                this.j = (this.g[1] - this.f[1]) + 1;
                this.k = 0;
                this.a.D = this.j + this.k;
                return;
            }
            this.j = (12 - this.f[1]) + 1;
            int leapMonth = com.meizu.common.util.b.a(this.f[0]);
            this.l = leapMonth;
            if (leapMonth != 0 && (this.f[1] < leapMonth || (leapMonth == this.f[1] && this.f[3] != 1))) {
                this.j++;
            }
            this.k = this.g[1];
            leapMonth = com.meizu.common.util.b.a(this.g[0]);
            this.m = leapMonth;
            if (leapMonth != 0 && (this.g[1] > leapMonth || (this.g[1] == leapMonth && this.g[3] == 1))) {
                this.k++;
            }
            this.a.D = this.j + this.k;
        }

        public String c(int position) {
            if (this.a.p) {
                position -= this.a.u / 2;
                if (position < 0 || position > this.a.D - 1) {
                    return "";
                }
                int year;
                if (position >= this.j) {
                    position -= this.j;
                    year = this.g[0];
                } else {
                    position += this.f[1] - 1;
                    if (this.l != 0 && (this.f[1] > this.l || this.f[3] == 1 || (this.f[1] < this.l && position >= this.l))) {
                        position++;
                    }
                    year = this.f[0];
                }
                return d(position, year);
            } else if (position < this.a.u / 2 || position > (this.a.u / 2) + 11) {
                return "";
            } else {
                return this.c[(((this.d[1] - 1) + position) - (this.a.u / 2)) % 12];
            }
        }

        public void a(View view, int fromOld, int toNew) {
            int maxdays = this.a.b(this.a.x, this.a.w, this.a.p);
            int id = toNew - (this.a.u / 2);
            if (this.a.p) {
                if (id >= this.j) {
                    this.a.x = this.g[0];
                } else {
                    this.a.x = this.f[0];
                }
            } else if (id > 12 - this.d[1]) {
                this.a.x = this.e[0];
            } else {
                this.a.x = this.d[0];
            }
            if (!this.a.p) {
                id = id > 12 - this.d[1] ? id - (12 - this.d[1]) : id + this.d[1];
            } else if (id >= this.j) {
                id -= this.j - 1;
                if (this.m != 0 && id > this.m) {
                    id--;
                }
            } else {
                id += this.f[1];
                if (this.l != 0 && this.f[1] <= this.l && this.f[3] != 1 && id > this.l) {
                    id--;
                }
            }
            this.a.w = id;
            if (!(maxdays == this.a.b(this.a.x, this.a.w, this.a.p) || this.a.e == null)) {
                this.a.e.b(this.a.b(this.a.x, this.a.w, this.a.p));
            }
            b(this.a.e.a() + 1);
        }

        private String[] c() {
            Locale currentLocale = Locale.getDefault();
            if (currentLocale.equals(this.a.s) && this.a.t != null) {
                return this.a.t;
            }
            synchronized (this.a.r) {
                if (!currentLocale.equals(this.a.s)) {
                    int i;
                    this.a.t = new String[12];
                    for (i = 0; i < 12; i++) {
                        this.a.t[i] = DateUtils.getMonthString(i + 0, 20);
                    }
                    if (this.a.t[0].startsWith(PushConstants.CLICK_TYPE_ACTIVITY)) {
                        for (i = 0; i < this.a.t.length; i++) {
                            this.a.t[i] = String.valueOf(i + 1);
                        }
                    }
                    this.a.s = currentLocale;
                }
            }
            return this.a.t;
        }

        private String d(int position, int year) {
            position %= 13;
            int leapMonth = com.meizu.common.util.b.a(year);
            if (leapMonth == 0) {
                if (position >= 12) {
                    return null;
                }
            } else if (position >= 13) {
                return null;
            }
            String[] mouths = this.a.getResources().getStringArray(com.meizu.common.a.a.mc_custom_time_picker_lunar_month);
            String leap = this.a.getResources().getString(h.mc_time_picker_leap);
            if (leapMonth == 0 || position <= leapMonth - 1) {
                return mouths[position];
            }
            if (position == leapMonth) {
                return leap + mouths[position - 1];
            }
            return mouths[position - 1];
        }
    }

    private class c implements ScrollTextView.b {
        int a = 0;
        final /* synthetic */ CustomTimePicker b;

        c(CustomTimePicker customTimePicker, int i) {
            this.b = customTimePicker;
            this.a = i;
        }

        public void a(View view, int fromOld, int toNew) {
            switch (this.a) {
                case 1:
                    this.b.a = toNew;
                    return;
                case 2:
                    this.b.b = toNew;
                    return;
                case 3:
                    this.b.d = toNew == 0;
                    return;
                case 5:
                    this.b.v = toNew + 1;
                    return;
                default:
                    return;
            }
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
                        return this.b.j;
                    }
                    if (position == 1) {
                        return this.b.k;
                    }
                    break;
                case 5:
                    if (this.b.p) {
                        return this.b.b(position);
                    }
                    return String.valueOf(position + 1);
            }
            return null;
        }
    }

    public CustomTimePicker(Context context) {
        this(context, null);
    }

    public CustomTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = 0;
        this.b = 0;
        this.c = Boolean.valueOf(false);
        this.d = true;
        this.p = false;
        this.q = false;
        this.r = new Object();
        this.u = 5;
        this.y = Calendar.getInstance();
        try {
            this.a = this.y.get(11);
            this.b = this.y.get(12);
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
        this.j = dfsAmPm[0];
        this.k = dfsAmPm[1];
        this.A = context.getResources().getDimensionPixelOffset(d.mc_date_picker_normal_lunar_size);
        this.z = context.getResources().getDimensionPixelOffset(d.mc_date_picker_selected_lunar_size);
        this.C = context.getResources().getDimensionPixelOffset(d.mc_picker_normal_number_size);
        this.B = context.getResources().getDimensionPixelOffset(d.mc_picker_selected_number_size);
        b();
        setBackgroundColor(-1);
    }

    private void b() {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        this.u = 3;
        int resid = g.mc_custom_picker_24hour;
        int selectItemHeight = getResources().getDimensionPixelOffset(d.mc_custom_time_picker_select_h);
        inflate(getContext(), resid, this);
        this.I = (FrameLayout) findViewById(f.picker_holder);
        this.n = (TextView) findViewById(f.mc_scroll_hour_text);
        if (this.n != null) {
            this.n.setText(h.mc_date_time_hour);
        }
        this.o = (TextView) findViewById(f.mc_scroll_min_text);
        if (this.o != null) {
            this.o.setText(h.mc_date_time_min);
        }
        this.g = (ScrollTextView) findViewById(f.mc_scroll_hour);
        this.g.setData(new c(this, 1), -1.0f, this.a, this.c.booleanValue() ? 24 : 12, this.u, 0, this.c.booleanValue() ? 23 : 11, true);
        this.g.setSelectItemHeight((float) selectItemHeight);
        this.h = (ScrollTextView) findViewById(f.mc_scroll_min);
        this.h.setData(new c(this, 2), -1.0f, this.b, 60, this.u, 0, 59, true);
        this.h.setSelectItemHeight((float) selectItemHeight);
        this.i = (ScrollTextView) findViewById(f.mc_scroll_ampm);
        this.i.setData(new c(this, 3), -1.0f, this.d ? 0 : 1, 2, this.u, 0, 1, false);
        this.i.setTextSize(getContext().getResources().getDimension(d.mz_picker_selected_ampm_size), getContext().getResources().getDimension(d.mz_picker_unselected_ampm_size));
        this.i.setSelectItemHeight((float) selectItemHeight);
        FrameLayout ampmholder = (FrameLayout) findViewById(f.mc_column_ampm);
        if (this.c.booleanValue()) {
            ampmholder.setVisibility(8);
        } else {
            ampmholder.setVisibility(0);
        }
        this.l = (TextView) findViewById(f.mc_scroll_month_text);
        if (this.l != null) {
            this.l.setText(h.mc_date_time_month);
        }
        this.m = (TextView) findViewById(f.mc_scroll_day_text);
        if (this.m != null) {
            this.m.setText(h.mc_date_time_day);
        }
        Calendar cal = Calendar.getInstance();
        this.x = cal.get(1);
        this.w = cal.get(2);
        this.v = cal.get(5);
        int max = cal.getActualMaximum(5);
        this.e = new a(this, (ScrollTextView) findViewById(f.mc_scroll_day));
        this.e.a(new c(this, 5), -1, this.v - 1, max, this.u, this.v - 1, max - 1, true);
        this.e.a(selectItemHeight);
        this.f = new b(this, (ScrollTextView) findViewById(f.mc_scroll_month));
        this.f.a(null, -1.0f, a(this.w), 12, this.u, 0, 11, false);
        this.f.a(selectItemHeight);
        d();
    }

    private void c() {
        int selectcolor = this.p ? this.G : this.F;
        a(selectcolor, !this.p, true);
        setTextColor(selectcolor, this.H, selectcolor);
    }

    private void d() {
        this.F = getResources().getColor(com.meizu.common.a.c.mc_custom_date_picker_selected_lunar_color);
        this.G = getResources().getColor(com.meizu.common.a.c.mc_custom_date_picker_selected_gregorian_color);
        this.H = getResources().getColor(com.meizu.common.a.c.mc_custom_date_picker_unselected_color);
    }

    private void a(int color, boolean bLunar, boolean doAnimate) {
        int unselectedTabColor = getContext().getResources().getColor(com.meizu.common.a.c.mc_custom_date_picker_unselected_tab_color);
        if (!bLunar) {
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
            this.g.b(this.a);
        }
        if (updateMinPicker) {
            this.h.b(this.b);
        }
        if (this.i != null) {
            ScrollTextView scrollTextView = this.i;
            if (!this.d) {
                i = 1;
            }
            scrollTextView.b(i);
        }
    }

    private int a(int month) {
        int id = this.u / 2;
        if (month < 0 || month > 11) {
            return id;
        }
        int m = this.y.get(2);
        if (month >= m) {
            id += month - m;
        } else {
            id += (12 - m) + month;
        }
        return id;
    }

    public void a(int[] time) {
        int i = 1;
        if (this.p) {
            lunartime = new int[3];
            lunartime = com.meizu.common.util.b.a(this.x, this.w, this.v, false);
            time[0] = lunartime[0];
            time[1] = lunartime[1];
            time[2] = lunartime[2];
        } else {
            time[0] = this.x;
            time[1] = this.w;
            time[2] = this.v;
        }
        time[3] = getCurrentHour();
        time[4] = getCurrentMinute().intValue();
        if (!this.p) {
            i = 0;
        }
        time[5] = i;
    }

    public long getTimeMillis() {
        int[] time = new int[6];
        a(time);
        Calendar c = Calendar.getInstance();
        c.set(time[0], time[1] - 1, time[2], time[3], time[4], 0);
        return c.getTimeInMillis();
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getCurrentHour(), this.b, this.x, this.w, this.v, this.p, this.q);
    }

    protected void onRestoreInstanceState(Parcelable state) {
        boolean z;
        boolean z2 = true;
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        a(ss.a, ss.b, this.c.booleanValue());
        int c = ss.c;
        int d = ss.d;
        int e = ss.e;
        if (ss.f == 1) {
            z = true;
        } else {
            z = false;
        }
        a(c, d, e, z, ss.g == 1, false);
        int selectColor = ss.f == 1 ? this.F : this.G;
        if (ss.f != 1) {
            z2 = false;
        }
        a(selectColor, z2, false);
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

    public void setCurrentMinute(Integer currentMinute) {
        a(getCurrentHour(), currentMinute.intValue(), this.c.booleanValue());
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            int selectColor;
            boolean is24Hour = this.c.booleanValue();
            try {
                is24Hour = DateFormat.is24HourFormat(getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (is24Hour != this.c.booleanValue()) {
                a(getCurrentHour(), this.b, is24Hour);
            }
            if (this.p) {
                this.f.b(this.z, this.A);
                this.e.b(this.z, this.A);
                this.m.setAlpha(0.0f);
                selectColor = this.F;
                a(selectColor, this.p, false);
            } else {
                this.f.b(this.B, this.C);
                this.e.b(this.B, this.C);
                this.m.setAlpha(1.0f);
                selectColor = this.G;
            }
            this.f.c(selectColor, this.H);
            this.e.a(selectColor, this.H);
            this.g.setTextColor(selectColor, this.H);
            this.h.setTextColor(selectColor, this.H);
            this.i.setTextColor(selectColor, this.H);
            this.l.setTextColor(selectColor);
            this.m.setTextColor(selectColor);
            this.n.setTextColor(selectColor);
            this.o.setTextColor(selectColor);
        }
    }

    public void setTextColor(int selectedColor, int normalColor, int unitColor) {
        this.f.c(selectedColor, normalColor);
        this.e.a(selectedColor, normalColor);
        this.g.setTextColor(selectedColor, normalColor);
        this.h.setTextColor(selectedColor, normalColor);
        if (this.i != null) {
            this.i.setTextColor(selectedColor, normalColor);
        }
        this.n.setTextColor(unitColor);
        this.o.setTextColor(unitColor);
        this.l.setTextColor(unitColor);
        this.m.setTextColor(unitColor);
    }

    private int b(int year, int month, boolean isLunar) {
        if (isLunar) {
            int leapMonth = com.meizu.common.util.b.a(year);
            boolean isLeapMonth = false;
            if (leapMonth != 0) {
                isLeapMonth = leapMonth == month;
            }
            return com.meizu.common.util.b.a(year, month, isLeapMonth);
        }
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);
        cal.set(1, year);
        cal.set(2, month - 1);
        return cal.getActualMaximum(5);
    }

    public void setLunar(boolean isLunar) {
        boolean z = false;
        c();
        date = new int[4];
        int[] monthData = new int[]{this.x, this.f.a(monthData)};
        date[2] = this.e.a() + 1;
        date[3] = 0;
        if (isLunar) {
            date = com.meizu.common.util.b.a(date[0], date[1], date[2]);
        } else {
            date = com.meizu.common.util.b.a(date[0], date[1], date[2], monthData[1] == 1);
        }
        int i = date[0];
        int i2 = date[1];
        int i3 = date[2];
        if (monthData[1] == 1) {
            z = true;
        }
        a(i, i2, i3, isLunar, z);
    }

    public void a(int year, int monthOfYear, int dayOfMonth, boolean islunar, boolean isleapmonth, boolean doAnimate) {
        this.p = islunar;
        this.x = year;
        this.w = monthOfYear;
        this.v = dayOfMonth;
        this.f.a(this.x, this.w, this.v, isleapmonth);
        if (this.E != b(this.x, this.w, islunar)) {
            this.E = b(this.x, this.w, islunar);
            this.e.b(b(this.x, this.w, islunar));
        }
        this.e.a(this.v - 1, doAnimate);
        if (this.p) {
            this.f.b(this.z, this.A);
            this.e.b(this.z, this.A);
            this.m.setAlpha(0.0f);
            return;
        }
        this.f.b(this.B, this.C);
        this.e.b(this.B, this.C);
        this.m.setAlpha(1.0f);
    }

    public void a(int year, int monthOfYear, int dayOfMonth, boolean islunar, boolean isleapmonth) {
        a(year, monthOfYear, dayOfMonth, islunar, isleapmonth, true);
    }

    private String b(int position) {
        String[] days = getResources().getStringArray(com.meizu.common.a.a.mc_custom_time_picker_lunar_day);
        if (position > days.length - 1) {
            return null;
        }
        return days[position];
    }

    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int padding = getResources().getDimensionPixelSize(d.mc_custom_time_picker_padding);
        int selectItemHeight = getResources().getDimensionPixelSize(d.mc_custom_time_picker_select_h);
        int pickerHeight = getResources().getDimensionPixelSize(d.mc_custom_time_picker_picker_height);
        this.f.a(selectItemHeight);
        this.e.a(selectItemHeight);
        this.g.setSelectItemHeight((float) selectItemHeight);
        this.h.setSelectItemHeight((float) selectItemHeight);
        LayoutParams plp = (LayoutParams) this.I.getLayoutParams();
        plp.leftMargin = padding;
        plp.rightMargin = padding;
        plp.height = pickerHeight;
        this.I.setLayoutParams(plp);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(CustomTimePicker.class.getName());
    }
}
