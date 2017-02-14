package com.meizu.common.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroupOverlay;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.meizu.common.R;

@TargetApi(18)
@SuppressLint({"ViewConstructor"})
public class FastScrollLetter extends View {
    public static final int SECTION_COMPARE_TYPE1 = 1;
    public static final int SECTION_COMPARE_TYPE2 = 2;
    private static final int STATE_DRAGGING = 1;
    private static final int STATE_NONE = 0;
    private static final String TAG = FastScrollLetter.class.getSimpleName();
    private static final String[] mDefaultLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private AbsListView mAbsListView;
    private String mCurrentLetter;
    private int mCurrentShowPos;
    private int mDefaultSectionType;
    private int mEventDownY;
    private int mEventMoveY;
    private int mHeaderCount;
    private int mHeaderHeight;
    private SparseArray<Integer> mHideLetteSparseArray;
    private int mHideNum;
    private String mHideStr;
    private boolean mIsAlwayShowLetter;
    private boolean mIsEnable;
    private int mLetterActiveTextColor;
    private int mLetterMarginBottom;
    private int mLetterMarginRight;
    private int mLetterMarginTop;
    private int mLetterTextColor;
    private int mLetterTextSize;
    private int mLetterWidth;
    private String[] mLetters;
    private ViewGroupOverlay mOverlay;
    private int mOverlayOneTextSize;
    private TextView mOverlayText;
    private int mOverlayTextHeight;
    private String[] mOverlayTextLetters;
    private int mOverlayTextMarginRight;
    private int mOverlayTextTop;
    private int mOverlayTextWidth;
    private int mOverlayThreeTextSize;
    private int mOverlayTwoTextSize;
    private int mPaddingLeft;
    Paint mPaint;
    private Bitmap mPointBitmap;
    private SectionCompare mSectionCompare;
    private SectionIndexer mSectionIndexer;
    private String[] mShowLetters;
    private int mSingleLetterHeight;
    private int mState;
    private String mTopLetter;

    public interface SectionCompare {
        int getSection(int i);
    }

    public FastScrollLetter(Context context, AbsListView absListView) {
        this(context, absListView, (TextView) LayoutInflater.from(context).inflate(R.layout.mc_letter_overlay, null));
    }

    public FastScrollLetter(Context context, AbsListView absListView, int i) {
        this(context, absListView, (TextView) LayoutInflater.from(context).inflate(R.layout.mc_letter_overlay, null));
        this.mDefaultSectionType = i;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(FastScrollLetter.class.getName());
    }

    public FastScrollLetter(Context context, AbsListView absListView, TextView textView, int i) {
        this(context, absListView, textView);
        this.mDefaultSectionType = i;
    }

    public FastScrollLetter(Context context, AbsListView absListView, TextView textView) {
        super(context, null, R.attr.MeizuCommon_FastScrollLetter);
        this.mIsAlwayShowLetter = false;
        this.mIsEnable = true;
        this.mPaint = new Paint();
        this.mCurrentShowPos = -1;
        this.mSingleLetterHeight = 0;
        this.mTopLetter = "";
        this.mOverlayOneTextSize = 0;
        this.mOverlayTwoTextSize = 0;
        this.mOverlayThreeTextSize = 0;
        this.mOverlayTextWidth = 0;
        this.mOverlayTextHeight = 0;
        this.mOverlayTextMarginRight = 220;
        this.mOverlayTextTop = 0;
        this.mLetterTextSize = 20;
        this.mLetterMarginTop = 0;
        this.mLetterMarginBottom = 0;
        this.mLetterMarginRight = 0;
        this.mLetterWidth = 24;
        this.mPaddingLeft = 0;
        this.mPointBitmap = null;
        this.mHideStr = "";
        this.mHideNum = 0;
        this.mEventDownY = 0;
        this.mEventMoveY = 0;
        this.mHeaderCount = 0;
        this.mHeaderHeight = 53;
        this.mDefaultSectionType = 2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.FastScrollLetter, R.attr.MeizuCommon_FastScrollLetter, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.FastScrollLetter_mcFastScrollLetter);
        if (drawable != null) {
            textView.setBackground(drawable);
        }
        if (absListView != null) {
            this.mAbsListView = absListView;
            this.mOverlay = absListView.getOverlay();
            this.mOverlay.add(this);
            this.mOverlayOneTextSize = getPxSize(context, R.dimen.mc_fastscroll_letter_overlay_text_size);
            this.mOverlayTwoTextSize = getPxSize(context, R.dimen.mc_fastscroll_letter_overlay_two_text_size);
            this.mOverlayThreeTextSize = getPxSize(context, R.dimen.mc_fastscroll_letter_overlay_three_text_size);
            int pxSize = getPxSize(context, R.dimen.mc_fastscroll_letter_overlay_layout_width);
            this.mOverlayTextHeight = pxSize;
            this.mOverlayTextWidth = pxSize;
            this.mOverlayTextMarginRight = getPxSize(context, R.dimen.mc_fastscroll_letter_overlay_layout_margin_right);
            this.mOverlayTextTop = (-this.mOverlayTextHeight) / 2;
            this.mLetterTextSize = getPxSize(context, R.dimen.mc_fastscroll_letter_text_size);
            this.mLetterTextColor = context.getResources().getColor(R.color.mc_fastscroll_letter_text_color);
            this.mLetterMarginTop = getPxSize(context, R.dimen.mc_fastscroll_letter_layout_margin_top);
            this.mLetterMarginRight = getPxSize(context, R.dimen.mc_fastscroll_letter_layout_margin_right);
            this.mLetterMarginBottom = getPxSize(context, R.dimen.mc_fastscroll_letter_layout_margin_bottom);
            this.mLetterWidth = getPxSize(context, R.dimen.mc_fastscroll_letter_layout_wdith);
            this.mOverlayText = textView;
            this.mLetterTextColor = obtainStyledAttributes.getColor(R.styleable.FastScrollLetter_mcLetterTextColor, this.mLetterTextColor);
            this.mLetterActiveTextColor = obtainStyledAttributes.getColor(R.styleable.FastScrollLetter_mcLetterActiveTextColor, this.mLetterActiveTextColor);
            this.mLetterTextSize = (int) obtainStyledAttributes.getDimension(R.styleable.FastScrollLetter_mcLetterTextSize, (float) this.mLetterTextSize);
            this.mLetterWidth = (int) obtainStyledAttributes.getDimension(R.styleable.FastScrollLetter_mcLetterWidth, (float) this.mLetterWidth);
            this.mLetterMarginTop = (int) obtainStyledAttributes.getDimension(R.styleable.FastScrollLetter_mcLetterMarginTop, (float) this.mLetterMarginTop);
            this.mLetterMarginBottom = (int) obtainStyledAttributes.getDimension(R.styleable.FastScrollLetter_mcLetterMarginBottom, (float) this.mLetterMarginBottom);
            this.mLetterMarginRight = (int) obtainStyledAttributes.getDimension(R.styleable.FastScrollLetter_mcLetterMarginRight, (float) this.mLetterMarginRight);
            this.mOverlay.add(this.mOverlayText);
            this.mOverlayText.setVisibility(4);
            this.mOverlayText.setLayoutDirection(this.mAbsListView.getLayoutDirection());
            this.mLetterActiveTextColor = this.mLetterTextColor;
            this.mPaint.setColor(this.mLetterTextColor);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setTextSize((float) this.mLetterTextSize);
            this.mPointBitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.mc_ic_letter_search_point)).getBitmap();
            setBackgroundResource(R.drawable.mc_ic_letter_search_bg);
            this.mShowLetters = mDefaultLetters;
            this.mOverlayTextLetters = mDefaultLetters;
            this.mLetters = mDefaultLetters;
            setVisibility(4);
        }
        obtainStyledAttributes.recycle();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setHideLetterNum(String str, int i) {
        int i2 = 0;
        this.mHideStr = str;
        this.mHideNum = i;
        String[] strArr = new String[((((this.mShowLetters.length - 1) / (this.mHideNum + 1)) * 2) + 2)];
        int i3 = 0;
        while (i2 < this.mShowLetters.length) {
            int i4 = i3 + 1;
            strArr[i3] = this.mShowLetters[i2];
            i3 = i4 + 1;
            strArr[i4] = this.mHideStr;
            i2 += this.mHideNum + 1;
        }
        strArr[strArr.length - 1] = this.mShowLetters[this.mShowLetters.length - 1];
        this.mShowLetters = strArr;
    }

    public void setHideLetterStr(String str, Bitmap bitmap) {
        if (bitmap != null) {
            this.mPointBitmap = bitmap;
        }
        this.mHideStr = str;
    }

    public void setOverlayTextLetters(String[] strArr) {
        this.mOverlayTextLetters = strArr;
        if (this.mOverlayTextLetters == null || this.mOverlayTextLetters.length == 0) {
            setOverLayText(this.mTopLetter);
        }
    }

    public void setHideLetter(SparseArray<Integer> sparseArray, String[] strArr) {
        this.mHideLetteSparseArray = sparseArray;
        this.mShowLetters = strArr;
    }

    public void setOverlayParam(int i, int i2) {
        if (i != -1) {
            this.mOverlayTextHeight = i;
            this.mOverlayTextWidth = i;
        }
        if (i2 != -1) {
            this.mOverlayTextMarginRight = i2;
        }
    }

    public void setFastScrollAlwaysVisible(boolean z) {
        this.mIsAlwayShowLetter = z;
        if (this.mIsAlwayShowLetter) {
            setVisibility(0);
        }
    }

    public void setLetterActiveColor(int i, int i2) {
        this.mLetterActiveTextColor = i2;
        this.mLetterTextColor = i;
        this.mPaint.setColor(this.mLetterTextColor);
        invalidate();
    }

    public void setFastScrollEnabled(boolean z) {
        this.mIsEnable = z;
        setVisibility(z ? 0 : 8);
    }

    public void setLetters(String[] strArr) {
        this.mShowLetters = strArr;
        this.mLetters = strArr;
        setOverlayTextLetters(strArr);
    }

    public void setTopLetter(String str) {
        this.mTopLetter = str;
        if (this.mOverlayTextLetters == null || this.mOverlayTextLetters.length == 0) {
            setOverLayText(this.mTopLetter);
        }
    }

    public void setHeaderHeight(int i) {
        this.mHeaderHeight = i;
    }

    public void setLetterParam(int i, int i2, int i3, int i4, int i5, int i6) {
        if (i != -1) {
            this.mLetterTextSize = i;
            this.mPaint.setTextSize((float) this.mLetterTextSize);
        }
        if (i2 != -1) {
            this.mLetterTextColor = i2;
            this.mLetterActiveTextColor = this.mLetterTextColor;
            this.mPaint.setColor(this.mLetterTextColor);
            invalidate();
        }
        if (i3 != -1) {
            this.mLetterMarginTop = i3;
        }
        if (i4 != -1) {
            this.mLetterMarginBottom = i4;
        }
        if (i5 != -1) {
            this.mLetterMarginRight = i5;
        }
        if (i6 != -1) {
            this.mLetterWidth = i6;
        }
    }

    public void setLayoutPaddingLeft(int i) {
        this.mPaddingLeft = i;
    }

    @TargetApi(16)
    public void setLetterBackground(Drawable drawable) {
        setBackground(drawable);
    }

    @TargetApi(16)
    public void setOverlayBackground(Drawable drawable) {
        this.mOverlayText.setBackground(drawable);
    }

    @TargetApi(17)
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        int width = this.mAbsListView.getWidth() - (this.mLetterMarginRight + this.mLetterWidth);
        int i5 = this.mLetterMarginTop;
        int width2 = this.mAbsListView.getWidth() - this.mLetterMarginRight;
        int height = this.mAbsListView.getHeight() - this.mLetterMarginBottom;
        if (this.mAbsListView.getLayoutDirection() == 0) {
            layout(width, i5, width2, height);
        } else {
            layout(this.mLetterMarginRight, i5, this.mLetterMarginRight + this.mLetterWidth, height);
        }
        this.mOverlayText.measure(this.mOverlayTextWidth, this.mOverlayTextWidth);
        setOverlayTextLayout(0.0f);
    }

    public void setSectionCompare(SectionCompare sectionCompare) {
        this.mSectionCompare = sectionCompare;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = 0.0f;
        if (!this.mIsEnable) {
            return false;
        }
        if (this.mAbsListView.getScrollY() != 0) {
            return false;
        }
        int action = motionEvent.getAction();
        float y = motionEvent.getY();
        if (y >= 0.0f) {
            f = y;
        }
        if (this.mShowLetters == null || this.mShowLetters.length == 0 || this.mAbsListView == null || this.mOverlayText == null || this.mOverlayTextLetters == null || this.mOverlayTextLetters.length == 0) {
            return false;
        }
        int choosePos = getChoosePos(f - ((float) this.mLetterMarginTop));
        switch (action) {
            case 0:
                if (choosePos < 0) {
                    return false;
                }
                this.mEventMoveY = (int) motionEvent.getY();
                if (isContain(motionEvent.getX(), motionEvent.getY())) {
                    this.mPaint.setColor(this.mLetterActiveTextColor);
                    invalidate();
                    this.mAbsListView.requestDisallowInterceptTouchEvent(true);
                    cancelFling();
                    setOverlayTextLayout(motionEvent.getY());
                    setLetterState(true, this);
                    OnTouchingLetterChange(motionEvent, choosePos);
                    this.mState = 1;
                    return true;
                } else if (!this.mTopLetter.equals("") && isContainWithTop(motionEvent.getX(), motionEvent.getY())) {
                    this.mCurrentShowPos = -1;
                    this.mPaint.setColor(this.mLetterActiveTextColor);
                    invalidate();
                    this.mState = 1;
                    setOverlayTextLayout((float) getTop());
                    setLetterState(true, this);
                    onTouchingLetterTop();
                    return true;
                }
            case 1:
            case 3:
            case 4:
                this.mAbsListView.requestDisallowInterceptTouchEvent(false);
                if (this.mState == 1) {
                    this.mCurrentShowPos = -1;
                    this.mPaint.setColor(this.mLetterTextColor);
                    invalidate();
                    setOverlayTextLayout((float) this.mEventMoveY);
                    setLetterState(false, this);
                    this.mState = 0;
                    return true;
                }
                break;
            case 2:
                break;
        }
        if (this.mState == 1) {
            if (choosePos >= 0 && choosePos < this.mOverlayTextLetters.length) {
                this.mEventMoveY = (int) motionEvent.getY();
                if (this.mCurrentShowPos == -1) {
                    setOverlayTextLayout(motionEvent.getY());
                }
                OnTouchingLetterChange(motionEvent, choosePos);
            } else if (!this.mTopLetter.equals("") && isContainWithTop(motionEvent.getX(), motionEvent.getY())) {
                this.mCurrentShowPos = -1;
                onTouchingLetterTop();
            }
        }
        if (this.mState != 1) {
            return false;
        }
        return true;
    }

    private int getPxSize(Context context, int i) {
        return context.getResources().getDimensionPixelSize(i);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.mPaddingLeft;
        if (this.mAbsListView.getLayoutDirection() == 1) {
            i = this.mPaddingLeft * -1;
        }
        String[] strArr = this.mShowLetters;
        if (strArr != null && strArr.length != 0) {
            int height = getHeight();
            int width = getWidth();
            this.mSingleLetterHeight = height / strArr.length;
            height = 0;
            while (height < strArr.length) {
                float measureText = (((float) (width / 2)) - (this.mPaint.measureText(strArr[height].toString()) / CircleProgressBar.BAR_WIDTH_DEF_DIP)) + ((float) i);
                float f = (float) ((this.mSingleLetterHeight * height) + this.mSingleLetterHeight);
                if (this.mPointBitmap == null || !strArr[height].toString().equals(this.mHideStr)) {
                    canvas.drawText(strArr[height].toString(), measureText, f, this.mPaint);
                } else {
                    canvas.drawBitmap(this.mPointBitmap, (float) (((width / 2) - (this.mPointBitmap.getWidth() / 2)) + i), (float) ((this.mSingleLetterHeight * height) + (this.mSingleLetterHeight / 2)), this.mPaint);
                }
                height++;
            }
        }
    }

    private boolean isContain(float f, float f2) {
        if (((float) getLeft()) >= f || f >= ((float) getRight()) || ((float) getTop()) >= f2 || ((float) getBottom()) <= f2) {
            return false;
        }
        return true;
    }

    private boolean isContainWithTop(float f, float f2) {
        if (((float) getLeft()) >= f || f >= ((float) getRight()) || ((float) getTop()) <= f2 || ((float) getBottom()) <= f2) {
            return false;
        }
        return true;
    }

    private int getChoosePos(float f) {
        int i = 0;
        this.mSingleLetterHeight = getHeight() / this.mShowLetters.length;
        int ceil = (int) (Math.ceil((double) (f / ((float) this.mSingleLetterHeight))) - 1.0d);
        if (ceil < 0 || ceil >= this.mShowLetters.length) {
            return -1;
        }
        int i2;
        if (this.mHideLetteSparseArray == null) {
            for (i2 = 0; i2 < ceil; i2++) {
                if (this.mShowLetters[i2].equals(this.mHideStr)) {
                    i += this.mHideNum;
                } else {
                    i++;
                }
            }
            float f2 = f - ((float) (this.mSingleLetterHeight * ceil));
            if (this.mShowLetters[ceil].equals(this.mHideStr)) {
                return i + ((int) (f2 / ((float) (this.mSingleLetterHeight / this.mHideNum))));
            }
            return i;
        }
        int i3 = 0;
        i2 = 0;
        while (i3 < ceil) {
            if (this.mShowLetters[i3].equals(this.mHideStr)) {
                i = ((Integer) this.mHideLetteSparseArray.get(i3)).intValue() + i2;
            } else {
                i = i2 + 1;
            }
            i3++;
            i2 = i;
        }
        float f3 = f - ((float) (this.mSingleLetterHeight * ceil));
        if (!this.mShowLetters[ceil].equals(this.mHideStr)) {
            return i2;
        }
        return ((int) Math.floor((double) (((float) ((Integer) this.mHideLetteSparseArray.get(ceil)).intValue()) * (f3 / ((float) this.mSingleLetterHeight))))) + i2;
    }

    private void onTouchingLetterTop() {
        setOverLayText(this.mTopLetter);
        if (this.mAbsListView instanceof ListView) {
            ((ListView) this.mAbsListView).setSelectionFromTop(0, -this.mHeaderHeight);
        } else {
            this.mAbsListView.setSelection(this.mHeaderCount);
        }
    }

    private int getSection(int i) {
        int i2 = 0;
        if (this.mSectionCompare != null) {
            return this.mSectionCompare.getSection(i);
        }
        Object obj = this.mOverlayTextLetters[i];
        Object[] sections = this.mSectionIndexer.getSections();
        if (sections == null) {
            return -1;
        }
        int i3;
        for (int i4 = 0; i4 < sections.length; i4++) {
            if (sections[i4].toString().equals(obj)) {
                i3 = i4;
                break;
            }
        }
        i3 = -1;
        if (this.mDefaultSectionType == 2 && i3 >= 0) {
            return i3;
        }
        if (this.mDefaultSectionType != 1) {
            return -1;
        }
        int positionForSection = this.mSectionIndexer.getPositionForSection(i3);
        if (this.mAbsListView instanceof ListView) {
            i2 = ((ListView) this.mAbsListView).getFooterViewsCount();
        }
        if (positionForSection >= this.mAbsListView.getCount() - i2 || this.mSectionIndexer.getSectionForPosition(positionForSection) != i3) {
            return -1;
        }
        return i3;
    }

    private void setSelection(int i, float f) {
        ListAdapter listAdapter = (ListAdapter) this.mAbsListView.getAdapter();
        if (listAdapter instanceof HeaderViewListAdapter) {
            this.mHeaderCount = ((HeaderViewListAdapter) listAdapter).getHeadersCount();
            listAdapter = ((HeaderViewListAdapter) listAdapter).getWrappedAdapter();
        }
        if (listAdapter instanceof SectionIndexer) {
            int nextPos;
            this.mSectionIndexer = (SectionIndexer) listAdapter;
            int i2 = this.mCurrentShowPos;
            int currentPos = getCurrentPos(i);
            if (this.mCurrentShowPos == -1) {
                nextPos = getNextPos(i);
            } else {
                nextPos = currentPos;
            }
            if (this.mCurrentShowPos < 0 || this.mCurrentShowPos >= this.mOverlayTextLetters.length) {
                if (this.mTopLetter != null && !this.mTopLetter.equals("")) {
                    setOverLayText(this.mTopLetter);
                    return;
                }
                return;
            } else if (i2 != this.mCurrentShowPos) {
                setOverLayText(this.mCurrentShowPos);
                if (this.mAbsListView instanceof ListView) {
                    ((ListView) this.mAbsListView).setSelectionFromTop(nextPos + this.mHeaderCount, -this.mHeaderHeight);
                    return;
                } else {
                    this.mAbsListView.setSelection(nextPos + this.mHeaderCount);
                    return;
                }
            } else {
                return;
            }
        }
        Log.w(TAG, "mSectionIndexer is null, adapter is not implements");
    }

    private void setOverLayText(int i) {
        setOverLayText(this.mOverlayTextLetters[i]);
    }

    private void setOverLayText(String str) {
        int i = this.mOverlayThreeTextSize;
        if (str != this.mCurrentLetter) {
            this.mCurrentLetter = str;
            switch (this.mCurrentLetter.length()) {
                case 1:
                    i = this.mOverlayOneTextSize;
                    break;
                case 2:
                case 3:
                case 4:
                    i = this.mOverlayTwoTextSize;
                    break;
            }
            this.mOverlayText.setTextSize(0, (float) i);
            this.mOverlayText.setText(this.mCurrentLetter);
        }
    }

    private int getCurrentPos(int i) {
        this.mCurrentShowPos = -1;
        int i2 = -1;
        while (i2 == -1) {
            int i3 = i - 1;
            if (i >= this.mOverlayTextLetters.length || i < 0) {
                break;
            }
            int section = getSection(i);
            if (section == -1) {
                i = i3;
            } else {
                i2 = this.mSectionIndexer.getPositionForSection(section);
                if (i2 != -1) {
                    this.mCurrentShowPos = Math.max(i, 0);
                }
                i = i3;
            }
        }
        return i2;
    }

    private int getNextPos(int i) {
        int i2 = -1;
        int i3 = i;
        while (i2 == -1) {
            i3++;
            if (i3 >= this.mOverlayTextLetters.length || i3 < 0) {
                break;
            }
            int section = getSection(i3);
            if (section != -1) {
                i2 = this.mSectionIndexer.getPositionForSection(section);
            }
        }
        if (this.mCurrentShowPos < 0 && i3 < this.mOverlayTextLetters.length) {
            this.mCurrentShowPos = i3;
        }
        if (i2 == -1) {
            return this.mAbsListView.getCount();
        }
        return i2;
    }

    private void setLetterState(boolean z, View view) {
        alphaAnim(z, this.mOverlayText);
        if (!this.mIsAlwayShowLetter) {
            alphaAnim(z, view);
        }
    }

    private void alphaAnim(final boolean z, final View view) {
        float f = 1.0f;
        if (view.getAnimation() == null) {
            if (!z || view.getVisibility() != 0) {
                if (!z && view.getVisibility() == 4) {
                    return;
                }
            }
            return;
        }
        float f2 = z ? 0.0f : 1.0f;
        if (!z) {
            f = 0.0f;
        }
        view.clearAnimation();
        Animation alphaAnimation = new AlphaAnimation(f2, f);
        alphaAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(z ? 0 : 4);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        alphaAnimation.setDuration(180);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        view.setAnimation(alphaAnimation);
        alphaAnimation.startNow();
    }

    @TargetApi(17)
    private void setOverlayTextLayout(float f) {
        this.mEventDownY = (int) f;
        int width = this.mAbsListView.getWidth() - (this.mOverlayTextMarginRight + this.mOverlayTextWidth);
        int i = (int) (((float) this.mOverlayTextTop) + f);
        int width2 = this.mAbsListView.getWidth() - this.mOverlayTextMarginRight;
        int i2 = this.mOverlayTextHeight + i;
        this.mOverlayText.setTranslationY(0.0f);
        if (this.mAbsListView.getLayoutDirection() == 0) {
            this.mOverlayText.layout(width, i, width2, i2);
        } else {
            this.mOverlayText.layout(this.mOverlayTextMarginRight, i, this.mOverlayTextMarginRight + this.mOverlayTextWidth, i2);
        }
    }

    private void cancelFling() {
        MotionEvent obtain = MotionEvent.obtain(0, 0, 3, 0.0f, 0.0f, 0);
        this.mAbsListView.onTouchEvent(obtain);
        obtain.recycle();
    }

    private void OnTouchingLetterChange(MotionEvent motionEvent, int i) {
        this.mOverlayText.setTranslationY((float) (((int) ((((float) this.mOverlayTextTop) + motionEvent.getY()) - ((float) this.mEventDownY))) + (this.mOverlayTextHeight / 2)));
        setSelection(i, motionEvent.getY());
    }

    public int getOverlayTextWidth() {
        return this.mOverlayTextWidth;
    }

    public int getOverlayTextMarginRight() {
        return this.mOverlayTextMarginRight;
    }

    public int getLetterTextColor() {
        return this.mLetterTextColor;
    }

    public int getLetterTextSize() {
        return this.mLetterTextSize;
    }

    public int getLetterMarginTop() {
        return this.mLetterMarginTop;
    }

    public int getLetterMarginBottom() {
        return this.mLetterMarginBottom;
    }

    public int getLetterMarginRight() {
        return this.mLetterMarginRight;
    }

    public int getLetterWidth() {
        return this.mLetterWidth;
    }

    public String[] getOverlayTextLetters() {
        return this.mOverlayTextLetters;
    }

    public String[] getLetters() {
        return this.mLetters;
    }

    public int getHeaderHeight() {
        return this.mHeaderHeight;
    }

    public int getPaddingLeft() {
        return this.mPaddingLeft;
    }

    public int getHideNum() {
        return this.mHideNum;
    }

    public String getHideStr() {
        return this.mHideStr;
    }
}
