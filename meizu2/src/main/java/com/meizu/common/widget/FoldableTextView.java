package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.meizu.common.R;
import com.meizu.common.util.ResourceUtils;

public class FoldableTextView extends TextView implements OnClickListener {
    private static final boolean DEBUG = false;
    private static final String ELLIPSIS_TWO_DOTS = "‥";
    private static final String TAG = "FoldableTextView";
    private Layout layout;
    private boolean mAlignViewEdge;
    private Float mAnimatorFraction;
    private boolean mClickToFold;
    private int mDuration;
    private CharSequence mEllipseText;
    private int mFoldLineMax;
    private CharSequence mFoldedText;
    private boolean mIsAnimating;
    private boolean mIsfolded;
    private int mLinkColor;
    private boolean mLinkHit;
    private FoldingListener mListener;
    private int mMaxHeight;
    private int mMinHeight;
    private CharSequence mMoreText;
    private boolean mNonClicks;
    private boolean mNonSpanClickable;
    private CharSequence mainText;

    public interface FoldingListener {
        boolean onFolding(FoldableTextView foldableTextView, boolean z);
    }

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;

        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null) {
                sInstance = new LocalLinkMovementMethod();
            }
            return sInstance;
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action != 1 && action != 0) {
                return Touch.onTouchEvent(textView, spannable, motionEvent);
            }
            int x = (((int) motionEvent.getX()) - textView.getTotalPaddingLeft()) + textView.getScrollX();
            int y = (((int) motionEvent.getY()) - textView.getTotalPaddingTop()) + textView.getScrollY();
            Layout layout = textView.getLayout();
            x = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(x, x, ClickableSpan.class);
            if (clickableSpanArr.length != 0) {
                if (action == 1) {
                    clickableSpanArr[0].onClick(textView);
                } else if (action == 0) {
                    Selection.setSelection(spannable, spannable.getSpanStart(clickableSpanArr[0]), spannable.getSpanEnd(clickableSpanArr[0]));
                }
                if (textView instanceof FoldableTextView) {
                    ((FoldableTextView) textView).mLinkHit = true;
                }
                return true;
            }
            Selection.removeSelection(spannable);
            Touch.onTouchEvent(textView, spannable, motionEvent);
            return false;
        }
    }

    class MoreClickSpan extends ClickableSpan {
        private final CharSequence mText;

        public MoreClickSpan(CharSequence charSequence) {
            this.mText = charSequence;
        }

        public void updateDrawState(TextPaint textPaint) {
            if (FoldableTextView.this.mLinkColor == 0) {
                textPaint.setColor(textPaint.linkColor);
            } else {
                textPaint.setColor(FoldableTextView.this.mLinkColor);
            }
        }

        public void onClick(View view) {
            if (!FoldableTextView.this.mIsAnimating) {
                if (FoldableTextView.this.mListener == null || FoldableTextView.this.mListener.onFolding(FoldableTextView.this, false)) {
                    FoldableTextView.this.mIsfolded = false;
                    FoldableTextView.this.setText(FoldableTextView.this.mainText, BufferType.NORMAL);
                    FoldableTextView.this.startAnimator();
                }
            }
        }
    }

    class ValueHolder {
        private int mHeight;

        private ValueHolder() {
        }

        public void setHeight(int i) {
            this.mHeight = i;
        }

        public int getHeight() {
            return this.mHeight;
        }
    }

    public FoldableTextView(Context context) {
        this(context, null);
    }

    public FoldableTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.Widget_MeizuCommon_FoldableTextView);
    }

    public FoldableTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsfolded = true;
        this.mAlignViewEdge = false;
        this.mClickToFold = true;
        this.mLinkColor = 0;
        this.mMaxHeight = 0;
        this.mMinHeight = 0;
        this.layout = null;
        this.mDuration = 250;
        this.mIsAnimating = false;
        this.mAnimatorFraction = Float.valueOf(1.0f);
        this.mNonClicks = true;
        this.mNonSpanClickable = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FoldableTextView, i, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = obtainStyledAttributes.getIndex(i2);
            if (index == R.styleable.FoldableTextView_mzTextEllipse) {
                this.mEllipseText = obtainStyledAttributes.getText(index);
            } else if (index == R.styleable.FoldableTextView_mzTextUnfold) {
                this.mMoreText = obtainStyledAttributes.getText(index);
            } else if (index == R.styleable.FoldableTextView_mzMaxFoldLine) {
                this.mFoldLineMax = obtainStyledAttributes.getInt(index, 0);
            } else if (index == R.styleable.FoldableTextView_mzUnfoldAlignViewEdge) {
                this.mAlignViewEdge = obtainStyledAttributes.getBoolean(index, false);
            } else if (index == R.styleable.FoldableTextView_mzClickToFold) {
                this.mClickToFold = obtainStyledAttributes.getBoolean(index, false);
            } else if (index == R.styleable.FoldableTextView_mzNonSpanClickable) {
                this.mNonSpanClickable = obtainStyledAttributes.getBoolean(index, true);
            } else if (index == R.styleable.FoldableTextView_mzLinkColor) {
                this.mLinkColor = obtainStyledAttributes.getColor(index, 0);
                if (this.mLinkColor == 0) {
                    this.mLinkColor = ResourceUtils.getMzThemeColor(context) == null ? 0 : ResourceUtils.getMzThemeColor(context).intValue();
                }
            } else if (index == R.styleable.FoldableTextView_mzIsFold) {
                this.mIsfolded = obtainStyledAttributes.getBoolean(index, true);
            }
        }
        obtainStyledAttributes.recycle();
        if (TextUtils.isEmpty(this.mMoreText)) {
            this.mMoreText = context.getString(R.string.more_item_label);
        }
        if (TextUtils.isEmpty(this.mEllipseText)) {
            this.mEllipseText = ELLIPSIS_TWO_DOTS;
        }
        setMovementMethod(LocalLinkMovementMethod.getInstance());
        setOnClickListener(true);
    }

    public void setFoldText(String str, String str2, boolean z) {
        this.mAlignViewEdge = z;
        if (str != null) {
            this.mEllipseText = str;
        }
        if (str2 != null) {
            this.mMoreText = str2;
        }
    }

    public CharSequence getStrEllipse() {
        return this.mEllipseText;
    }

    public CharSequence getMoreText() {
        return this.mMoreText;
    }

    public boolean isAlignViewEdge() {
        return this.mAlignViewEdge;
    }

    public void setFolding(int i, FoldingListener foldingListener) {
        this.mFoldLineMax = i;
        this.mListener = foldingListener;
        setText(this.mainText, BufferType.NORMAL);
    }

    public int getFoldLineMax() {
        return this.mFoldLineMax;
    }

    public void setClickToFold(boolean z) {
        if (z) {
            this.mClickToFold = true;
        } else {
            this.mClickToFold = false;
        }
    }

    public boolean isClickToFold() {
        return this.mClickToFold;
    }

    public void setLinkColor(int i) {
        this.mLinkColor = i;
        invalidate();
    }

    public int getLinkColor() {
        return this.mLinkColor;
    }

    public boolean getFoldStatus() {
        return this.mIsfolded;
    }

    public void setFoldStatus(boolean z) {
        if (this.mIsfolded != z) {
            this.mIsfolded = z;
            requestLayout();
            invalidate();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(FoldableTextView.class.getName());
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(charSequence, bufferType);
        if (!(this.mainText == null || this.mFoldedText == null || charSequence == null || charSequence.toString().equals(this.mainText.toString()) || charSequence.toString().equals(this.mFoldedText.toString()))) {
            this.mainText = charSequence;
            initHeight(charSequence);
            if (this.mIsfolded) {
                setHeight(this.mMinHeight);
            } else {
                setHeight(this.mMaxHeight);
            }
        }
        requestLayout();
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mainText == null) {
            this.mainText = getText();
        }
        initHeight(this.mainText);
        if (!this.mIsAnimating) {
            this.mFoldedText = foldText(this.mainText);
            if (!this.mIsfolded || this.mFoldLineMax <= 0) {
                setText(this.mainText, BufferType.SPANNABLE);
                setMeasuredDimension(i, this.mMaxHeight);
                return;
            }
            setText(this.mFoldedText, BufferType.SPANNABLE);
            setMeasuredDimension(i, this.mMinHeight);
        } else if (this.mIsfolded) {
            setMeasuredDimension(i, (int) (((float) this.mMaxHeight) - (((float) (this.mMaxHeight - this.mMinHeight)) * this.mAnimatorFraction.floatValue())));
        } else {
            setMeasuredDimension(i, (int) (((float) this.mMinHeight) + (((float) (this.mMaxHeight - this.mMinHeight)) * this.mAnimatorFraction.floatValue())));
        }
    }

    private CharSequence foldText(CharSequence charSequence) {
        this.layout = getLayout();
        if (this.layout == null) {
            return charSequence;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        DynamicLayout dynamicLayout = new DynamicLayout(spannableStringBuilder, this.layout.getPaint(), this.layout.getWidth(), this.layout.getAlignment(), 1.0f, 0.0f, false);
        if (dynamicLayout.getLineCount() <= this.mFoldLineMax || this.mFoldLineMax == 0) {
            return charSequence;
        }
        int i;
        int i2 = this.mFoldLineMax;
        while (i2 > 1) {
            i = i2 - 1;
            if (dynamicLayout.getLineStart(i) < dynamicLayout.getLineVisibleEnd(i)) {
                break;
            }
            i2 = i;
        }
        i = dynamicLayout.getLineVisibleEnd(i2 - 1);
        if (TextUtils.isEmpty(this.mEllipseText)) {
            spannableStringBuilder.delete(i, spannableStringBuilder.length());
        } else {
            spannableStringBuilder.replace(i, spannableStringBuilder.length(), this.mEllipseText);
        }
        spannableStringBuilder.append(' ');
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(this.mMoreText);
        spannableStringBuilder.setSpan(new MoreClickSpan(charSequence), length, spannableStringBuilder.length(), 33);
        if (i > 0 && dynamicLayout.getLineCount() > i2) {
            do {
                i--;
                spannableStringBuilder.delete(i, i + 1);
                if (i <= 0) {
                    break;
                }
            } while (dynamicLayout.getLineCount() > i2);
        } else if (this.mAlignViewEdge) {
            i = length;
            while (dynamicLayout.getLineCount() == i2) {
                spannableStringBuilder.replace(i, i, " ");
                if (dynamicLayout.getLineCount() > i2) {
                    spannableStringBuilder.delete(i, i + 1);
                    break;
                }
                i++;
            }
        }
        return spannableStringBuilder;
    }

    private void setOnClickListener(boolean z) {
        if (z) {
            setOnClickListener(this);
        } else {
            setOnClickListener(null);
        }
    }

    public void onClick(View view) {
        changeFoldState();
    }

    public void changeFoldState() {
        if (this.mIsAnimating || !this.mClickToFold) {
            return;
        }
        if (this.mIsfolded) {
            if (this.mListener == null || this.mListener.onFolding(this, false)) {
                this.mIsfolded = false;
                setText(this.mainText, BufferType.NORMAL);
                if (this.mMinHeight != 0) {
                    startAnimator();
                }
            }
        } else if (this.mListener == null || this.mListener.onFolding(this, true)) {
            this.mIsfolded = true;
            if (this.mMaxHeight != 0 && getLayout() != null && getLayout().getLineCount() > this.mFoldLineMax) {
                startAnimator();
            }
        }
    }

    private void startAnimator() {
        if (this.mFoldLineMax != 0) {
            this.mIsAnimating = true;
            ValueHolder valueHolder = new ValueHolder();
            String str = "height";
            int[] iArr = new int[2];
            iArr[0] = this.mIsfolded ? this.mMaxHeight : this.mMinHeight;
            iArr[1] = this.mIsfolded ? this.mMinHeight : this.mMaxHeight;
            ObjectAnimator ofInt = ObjectAnimator.ofInt(valueHolder, str, iArr);
            ofInt.setDuration((long) this.mDuration);
            ofInt.setInterpolator(new AccelerateDecelerateInterpolator());
            ofInt.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    boolean z = false;
                    if (FoldableTextView.this.mIsfolded) {
                        FoldableTextView.this.setText(FoldableTextView.this.mFoldedText, BufferType.SPANNABLE);
                        FoldableTextView.this.setHeight(FoldableTextView.this.mMinHeight);
                    } else {
                        FoldableTextView.this.setHeight(FoldableTextView.this.mMaxHeight);
                    }
                    FoldableTextView.this.mIsAnimating = false;
                    FoldableTextView foldableTextView = FoldableTextView.this;
                    if (!FoldableTextView.this.mNonClicks) {
                        z = true;
                    }
                    foldableTextView.mNonClicks = z;
                }

                public void onAnimationCancel(Animator animator) {
                    FoldableTextView.this.mIsAnimating = false;
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FoldableTextView.this.mAnimatorFraction = Float.valueOf(valueAnimator.getAnimatedFraction());
                    FoldableTextView.this.requestLayout();
                }
            });
            ofInt.start();
        }
    }

    private void initHeight(CharSequence charSequence) {
        this.layout = getLayout();
        if (this.layout != null) {
            this.mMaxHeight = (int) (((double) (((this.layout.getLineBottom(this.layout.getLineCount() - 1) - this.layout.getLineTop(0)) + getPaddingBottom()) + getPaddingTop())) + 0.5d);
            if (this.layout.getLineCount() <= this.mFoldLineMax) {
                this.mMinHeight = this.mMaxHeight;
            } else {
                this.mMinHeight = (int) ((((double) (((this.layout.getLineBottom(this.mFoldLineMax - 1) - this.layout.getLineTop(0)) + getPaddingBottom()) + getPaddingTop())) + 0.5d) + ((double) getLineSpacingExtra()));
            }
        }
    }

    public void setFoldDuration(int i) {
        this.mDuration = i;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mLinkHit = false;
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (!this.mNonClicks || this.mNonSpanClickable) {
            return onTouchEvent;
        }
        return this.mLinkHit;
    }

    public void setNonSpanClickable(boolean z) {
        this.mNonSpanClickable = z;
    }

    public boolean isNonSpanClickable() {
        return this.mNonSpanClickable;
    }

    public boolean hasFocusable() {
        return false;
    }
}
