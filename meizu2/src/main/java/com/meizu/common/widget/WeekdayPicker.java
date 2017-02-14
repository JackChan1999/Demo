package com.meizu.common.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.common.R;
import com.ted.android.data.BubbleEntity;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class WeekdayPicker extends LinearLayout {
    static final int DIFF_FRIDAY = 3;
    static final int DIFF_MONDAY = 0;
    static final int DIFF_SATURDAY = 2;
    static final int DIFF_SUNDAY = 1;
    static final int DIFF_THURSDAY = 4;
    static final int DIFF_TUESDAY = 6;
    static final int DIFF_WEDNESDAY = 5;
    static final int FIXED_DIFF_NORMAL_DAY = 0;
    static final String FIXED_WEEK_START_NORMAL_DAY = "-1";
    public static final int FRIDAY = 16;
    static final int HEIGHT_DELAY = 32;
    static final String IMG_SELECTED_TAG = "selected";
    static final String IMG_UNSELECTED_TAG = "unselected";
    public static final int MONDAY = 1;
    public static final int NO_DAY = 0;
    static final int PADDING_SIZE = 12;
    public static final int SATURDAY = 32;
    static final int SQUARE_WIDTH = 64;
    public static final int SUNDAY = 64;
    public static final int THURSDAY = 8;
    static final int TOTAL_ITEM_COUNT = 7;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 4;
    static final String WEEK_START_FRIDAY = "6";
    static final String WEEK_START_MONDAY = "2";
    static final String WEEK_START_NORMAL = "-1";
    static final String WEEK_START_NULL = "-2";
    static final String WEEK_START_SATURDAY = "7";
    static final String WEEK_START_SUNDAY = "1";
    static final String WEEK_START_THURSDAY = "5";
    static final String WEEK_START_TUESDAY = "3";
    static final String WEEK_START_WEDNESDAY = "4";
    private ImageView[] mBackgrouds;
    private Context mContext;
    private int mDiffWeekStart = 0;
    private int mHeightDelay = -1;
    private int mLastChangedIndex = -1;
    private int mLastLastChangedIndex = -1;
    private boolean mOutSide = false;
    private DaysOfWeek mRepeatData;
    private OnSelectChangedListener mSelectChangedListener = null;
    private int mSquareWidth = -1;
    private TextView[] mTexts;
    private String mWeekStart = BubbleEntity.VERIFICATION_ID;

    static final class DaysOfWeek {
        private static int[] DAY_MAP = new int[]{2, 3, 4, 5, 6, 7, 1};
        private int mDays;

        public DaysOfWeek(int i) {
            this.mDays = i;
        }

        public void setDays(int i) {
            this.mDays = i;
        }

        public String toString(Context context, boolean z) {
            StringBuilder stringBuilder = new StringBuilder();
            if (this.mDays == 0) {
                if (z) {
                    return context.getText(R.string.mc_never).toString();
                }
                return "";
            } else if (this.mDays == 127) {
                return context.getText(R.string.mc_every_day).toString();
            } else {
                if (this.mDays == 31) {
                    return context.getText(R.string.mc_working_day).toString();
                }
                if (this.mDays == 96) {
                    return context.getText(R.string.mc_weekend).toString();
                }
                int i;
                int i2 = 0;
                for (i = this.mDays; i > 0; i >>= 1) {
                    if ((i & 1) == 1) {
                        i2++;
                    }
                }
                String[] shortWeekdays = new DateFormatSymbols().getShortWeekdays();
                i = 0;
                int i3 = i2;
                while (i < 7) {
                    if ((this.mDays & (1 << i)) != 0) {
                        String str = shortWeekdays[DAY_MAP[i]];
                        if (TextUtils.equals(Locale.getDefault().getLanguage(), "zh") && stringBuilder.length() > 0) {
                            str = str.substring(1);
                        }
                        stringBuilder.append(str);
                        i2 = i3 - 1;
                        if (i2 > 0) {
                            stringBuilder.append("  ");
                        }
                    } else {
                        i2 = i3;
                    }
                    i++;
                    i3 = i2;
                }
                return stringBuilder.toString();
            }
        }

        private boolean isSet(int i) {
            return (this.mDays & (1 << i)) > 0;
        }

        public void set(int i, boolean z) {
            if (z) {
                this.mDays |= 1 << i;
            } else {
                this.mDays &= (1 << i) ^ -1;
            }
        }

        public void set(DaysOfWeek daysOfWeek) {
            this.mDays = daysOfWeek.mDays;
        }

        public int getCoded() {
            return this.mDays;
        }

        public boolean[] getBooleanArray() {
            boolean[] zArr = new boolean[7];
            for (int i = 0; i < 7; i++) {
                zArr[i] = isSet(i);
            }
            return zArr;
        }
    }

    public interface OnSelectChangedListener {
        void OnSelectChanged(int i);
    }

    public WeekdayPicker(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public WeekdayPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        initView();
    }

    public WeekdayPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        initView();
    }

    private void initView() {
        this.mSquareWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_chooser_item_width);
        this.mHeightDelay = this.mSquareWidth / 2;
        this.mBackgrouds = new ImageView[7];
        this.mTexts = new TextView[7];
        String[] strArr = new String[]{getResources().getString(R.string.mc_monday), getResources().getString(R.string.mc_tuesday), getResources().getString(R.string.mc_wednesday), getResources().getString(R.string.mc_thursday), getResources().getString(R.string.mc_friday), getResources().getString(R.string.mc_saturday), getResources().getString(R.string.mc_sunday)};
        if ("-2".equals(this.mWeekStart)) {
            this.mWeekStart = String.valueOf(Calendar.getInstance().getFirstDayOfWeek());
        } else if (BubbleEntity.VERIFICATION_ID.equals(this.mWeekStart)) {
            this.mWeekStart = String.valueOf(Calendar.getInstance().getFirstDayOfWeek());
        }
        if ("1".equals(this.mWeekStart)) {
            this.mDiffWeekStart = 1;
        } else if ("2".equals(this.mWeekStart)) {
            this.mDiffWeekStart = 0;
        } else if (WEEK_START_TUESDAY.equals(this.mWeekStart)) {
            this.mDiffWeekStart = 6;
        } else if (WEEK_START_WEDNESDAY.equals(this.mWeekStart)) {
            this.mDiffWeekStart = 5;
        } else if (WEEK_START_THURSDAY.equals(this.mWeekStart)) {
            this.mDiffWeekStart = 4;
        } else if (WEEK_START_FRIDAY.equals(this.mWeekStart)) {
            this.mDiffWeekStart = 3;
        } else if (WEEK_START_SATURDAY.equals(this.mWeekStart)) {
            this.mDiffWeekStart = 2;
        }
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.weight = 1.0f;
        layoutParams.gravity = 17;
        if (this.mRepeatData == null) {
            this.mRepeatData = new DaysOfWeek(0);
        }
        boolean[] booleanArray = this.mRepeatData.getBooleanArray();
        int i = 0;
        int i2 = 0;
        while (i < 7) {
            int i3;
            View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.mc_weekday_picker_item, null);
            inflate.setLayoutParams(layoutParams);
            TextView textView = (TextView) inflate.findViewById(R.id.mc_chooser_text);
            textView.setText(strArr[i]);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.mc_background_img);
            if (booleanArray == null) {
                imageView.setTag(IMG_UNSELECTED_TAG);
                imageView.setBackgroundResource(R.drawable.mc_bg_week_switch_off);
                textView.setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_unselected));
            } else if (booleanArray[i]) {
                imageView.setTag(IMG_SELECTED_TAG);
                if (isEnabled()) {
                    imageView.setBackgroundResource(R.drawable.mc_bg_week_switch_on);
                } else {
                    imageView.setBackgroundResource(R.drawable.mc_bg_week_switch_on_disable);
                }
                textView.setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_selected));
            } else {
                imageView.setTag(IMG_UNSELECTED_TAG);
                if (isEnabled()) {
                    imageView.setBackgroundResource(R.drawable.mc_bg_week_switch_off);
                    textView.setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_unselected));
                } else {
                    imageView.setBackgroundResource(R.drawable.mc_bg_week_switch_off_disable);
                    textView.setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_unselected_disable));
                }
            }
            this.mBackgrouds[i] = imageView;
            this.mTexts[i] = textView;
            if (this.mDiffWeekStart + i >= 7) {
                addView(inflate, i2);
                i3 = i2 + 1;
            } else {
                addView(inflate);
                i3 = i2;
            }
            i++;
            i2 = i3;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (isEnabled()) {
            int action = motionEvent.getAction();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            ViewParent parent = getParent();
            switch (action) {
                case 0:
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(true);
                    }
                    this.mOutSide = false;
                    action = currentTouchIndex(x);
                    if (action < 0 || action >= 7) {
                        return true;
                    }
                    onBackgoundSelected(action, false);
                    return true;
                case 1:
                case 3:
                    this.mOutSide = false;
                    if (parent != null) {
                        parent.requestDisallowInterceptTouchEvent(false);
                    }
                    this.mLastChangedIndex = -1;
                    this.mLastLastChangedIndex = -1;
                    if (this.mSelectChangedListener == null) {
                        return true;
                    }
                    this.mSelectChangedListener.OnSelectChanged(this.mRepeatData.getCoded());
                    return true;
                case 2:
                    if (this.mOutSide) {
                        if (parent == null) {
                            return true;
                        }
                        parent.requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else if (x < ((float) (-this.mHeightDelay)) || x > ((float) (getWidth() + this.mHeightDelay)) || y < ((float) (-this.mHeightDelay)) || y > ((float) (getHeight() + this.mHeightDelay))) {
                        this.mLastChangedIndex = -1;
                        this.mLastLastChangedIndex = -1;
                        if (this.mSelectChangedListener != null) {
                            this.mSelectChangedListener.OnSelectChanged(this.mRepeatData.getCoded());
                        }
                        this.mOutSide = true;
                        if (parent == null) {
                            return true;
                        }
                        parent.requestDisallowInterceptTouchEvent(false);
                        return true;
                    } else {
                        int currentTouchIndex = currentTouchIndex(x);
                        if (currentTouchIndex < 0 || currentTouchIndex >= 7 || currentTouchIndex == this.mLastChangedIndex) {
                            return true;
                        }
                        onBackgoundSelected(currentTouchIndex, true);
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

    private int currentTouchIndex(float f) {
        int width = getWidth();
        float f2 = (float) (width / 7);
        float f3 = (f2 - ((float) this.mSquareWidth)) / CircleProgressBar.BAR_WIDTH_DEF_DIP;
        if (VERSION.SDK_INT > 16 && getLayoutDirection() == 1) {
            f = ((float) width) - f;
        }
        width = (int) (f / f2);
        if (width >= 7) {
            return -1;
        }
        width -= this.mDiffWeekStart;
        if (width < 0) {
            width += 7;
        }
        if (f % f2 < f3 || (f % f2) - f3 > ((float) this.mSquareWidth)) {
            return -1;
        }
        return width;
    }

    private void onBackgoundSelected(int i, boolean z) {
        if (this.mBackgrouds != null && i >= 0 && i < this.mBackgrouds.length && this.mBackgrouds[i] != null && this.mRepeatData != null) {
            if (z && this.mLastLastChangedIndex == i && this.mLastLastChangedIndex >= 0 && this.mLastLastChangedIndex < this.mBackgrouds.length && this.mBackgrouds[this.mLastChangedIndex] != null) {
                if (IMG_SELECTED_TAG.equals(this.mBackgrouds[this.mLastChangedIndex].getTag())) {
                    this.mBackgrouds[this.mLastChangedIndex].setTag(IMG_UNSELECTED_TAG);
                    this.mBackgrouds[this.mLastChangedIndex].setBackgroundResource(R.drawable.mc_bg_week_switch_off);
                    this.mTexts[this.mLastChangedIndex].setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_unselected));
                    this.mRepeatData.set(this.mLastChangedIndex, false);
                } else {
                    this.mBackgrouds[this.mLastChangedIndex].setTag(IMG_SELECTED_TAG);
                    this.mBackgrouds[this.mLastChangedIndex].setBackgroundResource(R.drawable.mc_bg_week_switch_on);
                    this.mTexts[this.mLastChangedIndex].setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_selected));
                    this.mRepeatData.set(this.mLastChangedIndex, true);
                }
            }
            this.mLastLastChangedIndex = this.mLastChangedIndex;
            this.mLastChangedIndex = i;
            if (IMG_SELECTED_TAG.equals(this.mBackgrouds[i].getTag())) {
                this.mBackgrouds[i].setTag(IMG_UNSELECTED_TAG);
                this.mBackgrouds[i].setBackgroundResource(R.drawable.mc_bg_week_switch_off);
                this.mTexts[i].setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_unselected));
                this.mRepeatData.set(i, false);
                return;
            }
            this.mBackgrouds[i].setTag(IMG_SELECTED_TAG);
            this.mBackgrouds[i].setBackgroundResource(R.drawable.mc_bg_week_switch_on);
            this.mTexts[i].setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_selected));
            this.mRepeatData.set(i, true);
        }
    }

    public void resetView() {
        removeAllViews();
        initView();
    }

    public void setOnSelectChangedListener(OnSelectChangedListener onSelectChangedListener) {
        this.mSelectChangedListener = onSelectChangedListener;
    }

    public void setFirstDayOfWeek(int i) {
        if (i < 1 || i > 7) {
            i = 1;
        }
        this.mWeekStart = String.valueOf(i);
        resetView();
    }

    public void setSelectedDays(int i) {
        this.mRepeatData.setDays(i);
        updateView();
    }

    public int getSelectedDays() {
        return this.mRepeatData.getCoded();
    }

    public boolean[] getSelectedArray() {
        return this.mRepeatData.getBooleanArray();
    }

    private void updateView() {
        if (this.mRepeatData != null && this.mBackgrouds != null) {
            boolean[] booleanArray = this.mRepeatData.getBooleanArray();
            for (int i = 0; i < 7; i++) {
                if (booleanArray[i]) {
                    this.mBackgrouds[i].setTag(IMG_SELECTED_TAG);
                    this.mBackgrouds[i].setBackgroundResource(R.drawable.mc_bg_week_switch_on);
                    this.mTexts[i].setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_selected));
                } else {
                    this.mBackgrouds[i].setTag(IMG_UNSELECTED_TAG);
                    this.mBackgrouds[i].setBackgroundResource(R.drawable.mc_bg_week_switch_off);
                    this.mTexts[i].setTextColor(this.mContext.getResources().getColor(R.color.mc_chooser_text_color_unselected));
                }
            }
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        removeAllViews();
        initView();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(WeekdayPicker.class.getName());
    }
}
