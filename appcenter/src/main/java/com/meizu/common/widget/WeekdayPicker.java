package com.meizu.common.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.common.a.c;
import com.meizu.common.a.d;
import com.meizu.common.a.e;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.h;
import java.util.Calendar;

public class WeekdayPicker extends LinearLayout {
    private Context a;
    private ImageView[] b;
    private TextView[] c;
    private b d = null;
    private a e;
    private int f = -1;
    private int g = -1;
    private boolean h = false;
    private String i = "-1";
    private int j = 0;
    private int k = -1;
    private int l = -1;

    private static final class a {
        private static int[] a = new int[]{2, 3, 4, 5, 6, 7, 1};
        private int b;

        public a(int days) {
            this.b = days;
        }

        public void a(int days) {
            this.b = days;
        }

        private boolean b(int day) {
            return (this.b & (1 << day)) > 0;
        }

        public void a(int day, boolean set) {
            if (set) {
                this.b |= 1 << day;
            } else {
                this.b &= (1 << day) ^ -1;
            }
        }

        public int a() {
            return this.b;
        }

        public boolean[] b() {
            boolean[] ret = new boolean[7];
            for (int i = 0; i < 7; i++) {
                ret[i] = b(i);
            }
            return ret;
        }
    }

    public interface b {
        void a(int i);
    }

    public WeekdayPicker(Context context) {
        super(context);
        this.a = context;
        b();
    }

    public WeekdayPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = context;
        b();
    }

    public WeekdayPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.a = context;
        b();
    }

    private void b() {
        this.k = this.a.getResources().getDimensionPixelSize(d.mc_chooser_item_width);
        this.l = this.k / 2;
        this.b = new ImageView[7];
        this.c = new TextView[7];
        String[] daysOfWeekStr = new String[]{getResources().getString(h.mc_monday), getResources().getString(h.mc_tuesday), getResources().getString(h.mc_wednesday), getResources().getString(h.mc_thursday), getResources().getString(h.mc_friday), getResources().getString(h.mc_saturday), getResources().getString(h.mc_sunday)};
        if ("-2".equals(this.i)) {
            this.i = String.valueOf(Calendar.getInstance().getFirstDayOfWeek());
        } else if ("-1".equals(this.i)) {
            this.i = String.valueOf(Calendar.getInstance().getFirstDayOfWeek());
        }
        if (PushConstants.CLICK_TYPE_ACTIVITY.equals(this.i)) {
            this.j = 1;
        } else if (PushConstants.CLICK_TYPE_WEB.equals(this.i)) {
            this.j = 0;
        } else if ("3".equals(this.i)) {
            this.j = 6;
        } else if ("4".equals(this.i)) {
            this.j = 5;
        } else if ("5".equals(this.i)) {
            this.j = 4;
        } else if ("6".equals(this.i)) {
            this.j = 3;
        } else if ("7".equals(this.i)) {
            this.j = 2;
        }
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.weight = 1.0f;
        lp.gravity = 17;
        if (this.e == null) {
            this.e = new a(0);
        }
        boolean[] setArray = this.e.b();
        int hasAddIndex = 0;
        for (int i = 0; i < 7; i++) {
            View view = LayoutInflater.from(this.a).inflate(g.mc_weekday_picker_item, null);
            view.setLayoutParams(lp);
            TextView text = (TextView) view.findViewById(f.mc_chooser_text);
            text.setText(daysOfWeekStr[i]);
            ImageView backImg = (ImageView) view.findViewById(f.mc_background_img);
            if (setArray == null) {
                backImg.setTag("unselected");
                backImg.setBackgroundResource(e.mc_bg_week_switch_off);
                text.setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_unselected));
            } else if (setArray[i]) {
                backImg.setTag("selected");
                if (isEnabled()) {
                    backImg.setBackgroundResource(e.mc_bg_week_switch_on);
                } else {
                    backImg.setBackgroundResource(e.mc_bg_week_switch_on_disable);
                }
                text.setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_selected));
            } else {
                backImg.setTag("unselected");
                if (isEnabled()) {
                    backImg.setBackgroundResource(e.mc_bg_week_switch_off);
                    text.setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_unselected));
                } else {
                    backImg.setBackgroundResource(e.mc_bg_week_switch_off_disable);
                    text.setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_unselected_disable));
                }
            }
            this.b[i] = backImg;
            this.c[i] = text;
            if (this.j + i >= 7) {
                addView(view, hasAddIndex);
                hasAddIndex++;
            } else {
                addView(view);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean z = false;
        if (isEnabled()) {
            int action = event.getAction();
            float insideX = event.getX();
            float insideY = event.getY();
            ViewParent parent = getParent();
            switch (action) {
                case 0:
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    this.h = false;
                    int currentIndex = a(insideX);
                    if (currentIndex < 0 || currentIndex >= 7) {
                        return true;
                    }
                    a(currentIndex, false);
                    return true;
                case 1:
                case 3:
                    this.h = false;
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                    this.f = -1;
                    this.g = -1;
                    if (this.d == null) {
                        return true;
                    }
                    this.d.a(this.e.a());
                    return true;
                case 2:
                    if (this.h) {
                        if (parent == null) {
                            return true;
                        }
                        parent.requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else if (insideX < ((float) (-this.l)) || insideX > ((float) (getWidth() + this.l)) || insideY < ((float) (-this.l)) || insideY > ((float) (getHeight() + this.l))) {
                        this.f = -1;
                        this.g = -1;
                        if (this.d != null) {
                            this.d.a(this.e.a());
                        }
                        this.h = true;
                        if (parent == null) {
                            return true;
                        }
                        parent.requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else {
                        int currentIndexMove = a(insideX);
                        if (currentIndexMove < 0 || currentIndexMove >= 7 || currentIndexMove == this.f) {
                            return true;
                        }
                        a(currentIndexMove, true);
                        return true;
                    }
                default:
                    return true;
            }
        }
        if (isClickable() || isLongClickable()) {
            z = true;
        }
        return z;
    }

    private int a(float currentX) {
        int layoutWidth = getWidth();
        float eachWidth = (float) (layoutWidth / 7);
        float squarePadding = (eachWidth - ((float) this.k)) / 2.0f;
        if (VERSION.SDK_INT > 16 && getLayoutDirection() == 1) {
            currentX = ((float) layoutWidth) - currentX;
        }
        int inPlace = (int) (currentX / eachWidth);
        if (inPlace >= 7) {
            return -1;
        }
        inPlace -= this.j;
        if (inPlace < 0) {
            inPlace += 7;
        }
        if (currentX % eachWidth < squarePadding || (currentX % eachWidth) - squarePadding > ((float) this.k)) {
            return -1;
        }
        return inPlace;
    }

    private void a(int index, boolean isMove) {
        if (this.b != null && index >= 0 && index < this.b.length && this.b[index] != null && this.e != null) {
            if (isMove && this.g == index && this.g >= 0 && this.g < this.b.length && this.b[this.f] != null) {
                if ("selected".equals(this.b[this.f].getTag())) {
                    this.b[this.f].setTag("unselected");
                    this.b[this.f].setBackgroundResource(e.mc_bg_week_switch_off);
                    this.c[this.f].setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_unselected));
                    this.e.a(this.f, false);
                } else {
                    this.b[this.f].setTag("selected");
                    this.b[this.f].setBackgroundResource(e.mc_bg_week_switch_on);
                    this.c[this.f].setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_selected));
                    this.e.a(this.f, true);
                }
            }
            this.g = this.f;
            this.f = index;
            if ("selected".equals(this.b[index].getTag())) {
                this.b[index].setTag("unselected");
                this.b[index].setBackgroundResource(e.mc_bg_week_switch_off);
                this.c[index].setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_unselected));
                this.e.a(index, false);
                return;
            }
            this.b[index].setTag("selected");
            this.b[index].setBackgroundResource(e.mc_bg_week_switch_on);
            this.c[index].setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_selected));
            this.e.a(index, true);
        }
    }

    public void a() {
        removeAllViews();
        b();
    }

    public void setOnSelectChangedListener(b listener) {
        this.d = listener;
    }

    public void setFirstDayOfWeek(int firstDay) {
        if (firstDay < 1 || firstDay > 7) {
            firstDay = 1;
        }
        this.i = String.valueOf(firstDay);
        a();
    }

    public void setSelectedDays(int days) {
        this.e.a(days);
        c();
    }

    public int getSelectedDays() {
        return this.e.a();
    }

    public boolean[] getSelectedArray() {
        return this.e.b();
    }

    private void c() {
        if (this.e != null && this.b != null) {
            boolean[] setArray = this.e.b();
            for (int i = 0; i < 7; i++) {
                if (setArray[i]) {
                    this.b[i].setTag("selected");
                    this.b[i].setBackgroundResource(e.mc_bg_week_switch_on);
                    this.c[i].setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_selected));
                } else {
                    this.b[i].setTag("unselected");
                    this.b[i].setBackgroundResource(e.mc_bg_week_switch_off);
                    this.c[i].setTextColor(this.a.getResources().getColor(c.mc_chooser_text_color_unselected));
                }
            }
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        removeAllViews();
        b();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(WeekdayPicker.class.getName());
    }
}
