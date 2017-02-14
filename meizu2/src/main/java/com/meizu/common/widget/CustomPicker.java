package com.meizu.common.widget;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.meizu.common.R;
import com.meizu.common.widget.ScrollTextView.IDataAdapter;

public class CustomPicker extends FrameLayout {
    private int mColumnCount;
    private TextView[] mColumnTextViews;
    private int[] mCurrentItems;
    private int mFocusedPosition;
    private OnCurrentItemChangedListener mOnItemChangedListener;
    private int mOneScreenCount;
    private ScrollTextView[] mPickers;
    private String[] mText;

    public static class ColumnData {
        String mColumnText;
        int mCount;
        int mCurrentItem;
        boolean mCycleEnabled;
        IDataAdapter mDataAdapter;
        float mLineOffset;
        int mOneScreenCount;
        int mStartValue;
        int mValidEnd;
        int mValidStart;

        public ColumnData(float f, int i, int i2, int i3, int i4, int i5, int i6, boolean z, String str) {
            this.mDataAdapter = null;
            this.mLineOffset = f;
            this.mCurrentItem = i;
            this.mCount = i2;
            this.mOneScreenCount = i3;
            this.mValidStart = i4;
            this.mValidEnd = i5;
            this.mStartValue = i6;
            this.mCycleEnabled = z;
            this.mColumnText = str;
        }

        public ColumnData(IDataAdapter iDataAdapter, float f, int i, int i2, int i3, int i4, int i5, boolean z, String str) {
            this.mDataAdapter = iDataAdapter;
            this.mLineOffset = f;
            this.mCurrentItem = i;
            this.mCount = i2;
            this.mOneScreenCount = i3;
            this.mValidStart = i4;
            this.mValidEnd = i5;
            this.mStartValue = 0;
            this.mCycleEnabled = z;
            this.mColumnText = str;
        }
    }

    class DataAdapter implements IDataAdapter {
        private int mColumnIndex = 0;
        private int mStartValue;

        DataAdapter(int i, int i2) {
            this.mColumnIndex = i;
            this.mStartValue = i2;
        }

        public void onChanged(View view, int i, int i2) {
            CustomPicker.this.mCurrentItems[this.mColumnIndex] = i2;
            if (CustomPicker.this.mOnItemChangedListener != null) {
                CustomPicker.this.mOnItemChangedListener.onCurrentItemChanged(CustomPicker.this, CustomPicker.this.mCurrentItems);
            }
        }

        public String getItemText(int i) {
            return String.valueOf(this.mStartValue + i);
        }
    }

    public interface OnCurrentItemChangedListener {
        void onCurrentItemChanged(CustomPicker customPicker, int... iArr);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        private final int[] mSaveCurrentItems;

        private SavedState(Parcelable parcelable, int... iArr) {
            super(parcelable);
            this.mSaveCurrentItems = (int[]) iArr.clone();
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mSaveCurrentItems = parcel.createIntArray();
        }

        public int[] getCurrentItems() {
            return this.mSaveCurrentItems;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeIntArray(this.mSaveCurrentItems);
        }
    }

    public CustomPicker(Context context) {
        this(context, null);
    }

    public CustomPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPickers = new ScrollTextView[3];
        this.mOneScreenCount = 0;
        this.mFocusedPosition = -1;
    }

    public CustomPicker(Context context, int i, ColumnData... columnDataArr) {
        this(context, null, 0);
        initPicker(i, columnDataArr);
    }

    public void initPicker(int i, ColumnData... columnDataArr) {
        if (getChildCount() > 0) {
            removeAllViews();
        }
        switch (i) {
            case 1:
                inflate(getContext(), R.layout.mc_picker_column_1, this);
                break;
            case 2:
                inflate(getContext(), R.layout.mc_picker_column_2, this);
                break;
            case 3:
                inflate(getContext(), R.layout.mc_picker_column_3, this);
                break;
            default:
                throw new IllegalArgumentException("columnCount only be 1 or 2 or 3.");
        }
        this.mColumnCount = i;
        this.mCurrentItems = new int[this.mColumnCount];
        this.mColumnTextViews = new TextView[3];
        this.mText = new String[3];
        this.mPickers[0] = (ScrollTextView) findViewById(R.id.mc_scroll1);
        this.mColumnTextViews[0] = (TextView) findViewById(R.id.mc_scroll1_text);
        this.mPickers[1] = (ScrollTextView) findViewById(R.id.mc_scroll2);
        this.mColumnTextViews[1] = (TextView) findViewById(R.id.mc_scroll2_text);
        this.mPickers[2] = (ScrollTextView) findViewById(R.id.mc_scroll3);
        this.mColumnTextViews[2] = (TextView) findViewById(R.id.mc_scroll3_text);
        int i2 = 0;
        int i3 = 0;
        while (i3 < this.mColumnCount) {
            ScrollTextView scrollTextView = this.mPickers[i3];
            TextView textView = this.mColumnTextViews[i3];
            if (!(scrollTextView == null || textView == null)) {
                if (columnDataArr == null || columnDataArr.length <= 0 || columnDataArr[0] == null) {
                    switch (i3) {
                        case 0:
                            textView.setText(R.string.mc_hour);
                            scrollTextView.setData(new DataAdapter(0, 0), this.mCurrentItems[0], 13, 5);
                            break;
                        case 1:
                            textView.setText(R.string.mc_minute);
                            scrollTextView.setData(new DataAdapter(1, 0), this.mCurrentItems[1], 60, 5);
                            break;
                        case 2:
                            textView.setText(R.string.mc_second);
                            scrollTextView.setData(new DataAdapter(2, 0), this.mCurrentItems[2], 60, 5);
                            break;
                        default:
                            break;
                    }
                }
                ColumnData columnData;
                int i4;
                ColumnData columnData2 = columnDataArr[i2];
                if (columnData2 == null) {
                    int i5 = i2 - 1;
                    columnData = columnDataArr[i5];
                    i4 = i5;
                } else {
                    columnData = columnData2;
                    i4 = i2 + 1;
                }
                if (this.mOneScreenCount < columnData.mOneScreenCount) {
                    this.mOneScreenCount = columnData.mOneScreenCount;
                }
                IDataAdapter dataAdapter = columnData.mDataAdapter != null ? columnData.mDataAdapter : new DataAdapter(i3, columnData.mStartValue);
                textView.setText(columnData.mColumnText);
                this.mText[i3] = columnData.mColumnText;
                scrollTextView.setData(dataAdapter, columnData.mLineOffset, columnData.mCurrentItem, columnData.mCount, columnData.mOneScreenCount, columnData.mValidStart, columnData.mValidEnd, columnData.mCycleEnabled);
                i2 = i4;
            }
            i3++;
        }
        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    public void updateCurrentItems(int... iArr) {
        if (iArr != null) {
            int i = 0;
            while (i < iArr.length && i < this.mColumnCount) {
                this.mCurrentItems[i] = iArr[i];
                this.mPickers[i].refreshCurrent(iArr[i]);
                i++;
            }
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        for (int i = 0; i < this.mColumnCount; i++) {
            this.mPickers[i].setEnabled(z);
        }
    }

    public void setOnCurrentItemChangedListener(OnCurrentItemChangedListener onCurrentItemChangedListener) {
        this.mOnItemChangedListener = onCurrentItemChangedListener;
    }

    public int[] getCurrentItems() {
        return this.mCurrentItems;
    }

    public int getCurrentItem(int i) {
        if (i < 0 || i >= this.mColumnCount) {
            return 0;
        }
        return this.mCurrentItems[i];
    }

    public void setCurrentItem(int i, int i2) {
        if (i >= 0 && i < this.mColumnCount) {
            this.mCurrentItems[i] = i2;
            updateCurrentItems(this.mCurrentItems);
        }
    }

    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mCurrentItems);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        updateCurrentItems(savedState.getCurrentItems());
    }

    private void setTextPadding(TextView textView, float f) {
        if (isDefaultFocusedPosition()) {
            textView.setGravity(17);
            return;
        }
        textView.setGravity(48);
        textView.setPadding(textView.getPaddingLeft(), (int) (((f - textView.getTextSize()) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + (((float) this.mFocusedPosition) * f)), textView.getPaddingRight(), textView.getPaddingBottom());
    }

    private boolean isDefaultFocusedPosition() {
        if (this.mFocusedPosition == -1) {
            return true;
        }
        return false;
    }

    public void setTextColor(int i, int i2, int i3) {
        for (int i4 = 0; i4 < this.mColumnCount; i4++) {
            this.mPickers[i4].setTextColor(i, i2);
            this.mColumnTextViews[i4].setTextColor(i3);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(CustomPicker.class.getName());
    }
}
