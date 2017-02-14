package com.meizu.common.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.meizu.common.R;

public class GuidePopupWindow extends PopupWindow {
    public static final int CORAL = 3;
    public static final int DODGERBLUE = 0;
    public static final int FIREBRICK = 2;
    public static final int GUIDE_LAYOUT_MODE_AUTO = 6;
    public static final int GUIDE_LAYOUT_MODE_CENTER = 3;
    public static final int GUIDE_LAYOUT_MODE_CENTER_HORIZONTAL = 1;
    public static final int GUIDE_LAYOUT_MODE_CENTER_VERTICAL = 2;
    public static final int GUIDE_LAYOUT_MODE_DOWN = 5;
    public static final int GUIDE_LAYOUT_MODE_UP = 4;
    public static final int LIMEGREEN = 1;
    public static final int TOMATO = 4;
    private View mAnchorView;
    private int mArrowOffx;
    private Context mContext;
    private HandleView mHandleView;
    private int mLayoutMode = 6;
    private int mOffX;
    private int mOffY;
    private View mParentView;
    private int mPopX;
    private int mPopY;

    class HandleView extends FrameLayout {
        private int layoutResourceId = R.layout.mc_guide_popup_window;
        private int mArrowLeft = -1;
        private int mArrowPadding;
        private View mBGLeft;
        private View mBGMiddle;
        private Rect mBGPadding = new Rect();
        private View mBGRight;
        private LinearLayout mBGVertical;
        private ImageView mCloseIcon;
        private LinearLayout mContentView;
        private Drawable mLeftDrawable;
        private int mLeftMinWidth;
        private int mMarging;
        private TextView mMessageTextView;
        private int mMidMinWidth;
        private Drawable mMiddleDrawable;
        private Drawable mMiddleDrawableUp;
        private int mMinHeight;
        private int mMinWidth;
        private Resources mResources;
        private Drawable mRightDrawable;
        private int mRightMinWidth;
        private int mTextSize;
        private boolean mWithArrow = true;

        public HandleView(Context context, Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            super(context);
            View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(this.layoutResourceId, this, true);
            this.mResources = context.getResources();
            this.mMessageTextView = (TextView) inflate.findViewById(R.id.guide_message);
            this.mCloseIcon = (ImageView) inflate.findViewById(R.id.guide_close);
            this.mContentView = (LinearLayout) inflate.findViewById(R.id.guide_content);
            this.mBGLeft = inflate.findViewById(R.id.guide_bg_left);
            this.mBGMiddle = inflate.findViewById(R.id.guide_bg_middle);
            this.mBGRight = inflate.findViewById(R.id.guide_bg_right);
            this.mBGVertical = (LinearLayout) inflate.findViewById(R.id.guide_bg_vertical);
            this.mTextSize = this.mResources.getDimensionPixelSize(R.dimen.mc_guide_popup_text_size);
            this.mMinHeight = this.mResources.getDimensionPixelSize(R.dimen.mc_guide_popup_min_height);
            this.mLeftDrawable = drawable;
            this.mMiddleDrawable = drawable2;
            this.mMiddleDrawableUp = drawable3;
            this.mRightDrawable = drawable4;
            this.mBGLeft.setBackground(this.mLeftDrawable);
            this.mBGMiddle.setBackground(this.mMiddleDrawable);
            this.mBGRight.setBackground(this.mRightDrawable);
            this.mArrowPadding = this.mResources.getDimensionPixelSize(R.dimen.mc_guide_popup_arrow_padding);
            this.mMarging = this.mResources.getDimensionPixelSize(R.dimen.mc_guide_popup_marging);
            Rect rect = new Rect();
            this.mLeftDrawable.getPadding(rect);
            this.mBGPadding.left = Math.max(rect.left, this.mBGPadding.left);
            this.mBGPadding.top = Math.max(rect.top, this.mBGPadding.top);
            this.mBGPadding.bottom = Math.max(rect.bottom, this.mBGPadding.bottom);
            this.mMiddleDrawable.getPadding(rect);
            this.mBGPadding.top = Math.max(rect.top, this.mBGPadding.top);
            this.mBGPadding.bottom = Math.max(rect.bottom, this.mBGPadding.bottom);
            this.mRightDrawable.getPadding(rect);
            this.mBGPadding.right = Math.max(rect.right, this.mBGPadding.right);
            this.mBGPadding.top = Math.max(rect.top, this.mBGPadding.top);
            this.mBGPadding.bottom = Math.max(rect.bottom, this.mBGPadding.bottom);
            this.mLeftMinWidth = this.mLeftDrawable.getIntrinsicWidth();
            this.mMidMinWidth = this.mMiddleDrawable.getIntrinsicWidth();
            this.mRightMinWidth = this.mRightDrawable.getIntrinsicWidth();
            this.mMinWidth = (this.mLeftMinWidth + this.mMidMinWidth) + this.mRightMinWidth;
            this.mContentView.setMinimumHeight((this.mMinHeight + this.mBGPadding.top) + this.mBGPadding.bottom);
            this.mContentView.setMinimumWidth(this.mMinWidth);
            this.mContentView.setPadding(this.mBGPadding.left, this.mBGPadding.top, this.mBGPadding.right, this.mBGPadding.bottom);
            this.mCloseIcon.setOnClickListener(new OnClickListener(GuidePopupWindow.this) {
                public void onClick(View view) {
                    GuidePopupWindow.this.dismiss();
                }
            });
        }

        public void setText(String str) {
            this.mMessageTextView.setText(str);
        }

        public TextView getMessageTextView() {
            return this.mMessageTextView;
        }

        public void setMessageOnClickListener(OnClickListener onClickListener) {
            this.mMessageTextView.setOnClickListener(onClickListener);
        }

        public void setTextSize(int i) {
            this.mMessageTextView.setTextSize(1, (float) i);
        }

        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            this.mContentView.measure(0, 0);
            int measuredHeight = this.mContentView.getMeasuredHeight();
            int measuredWidth = this.mContentView.getMeasuredWidth();
            setMeasuredDimension(measuredWidth, measuredHeight);
            this.mBGVertical.measure(measuredWidth, measuredHeight);
            LayoutParams layoutParams;
            if (!this.mWithArrow) {
                layoutParams = (LayoutParams) this.mBGLeft.getLayoutParams();
                layoutParams.width = measuredWidth - this.mRightDrawable.getMinimumWidth();
                layoutParams.height = measuredHeight;
                this.mBGLeft.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mBGRight.getLayoutParams();
                layoutParams.width = this.mRightDrawable.getMinimumWidth();
                layoutParams.height = measuredHeight;
                this.mBGRight.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mBGMiddle.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.height = measuredHeight;
                this.mBGMiddle.setLayoutParams(layoutParams);
            } else if (this.mArrowLeft > 0) {
                layoutParams = (LayoutParams) this.mBGLeft.getLayoutParams();
                layoutParams.width = this.mArrowLeft;
                layoutParams.height = measuredHeight;
                this.mBGLeft.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mBGRight.getLayoutParams();
                layoutParams.width = (measuredWidth - this.mMiddleDrawable.getMinimumWidth()) - this.mArrowLeft;
                layoutParams.height = measuredHeight;
                this.mBGRight.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mBGMiddle.getLayoutParams();
                layoutParams.width = this.mMiddleDrawable.getMinimumWidth();
                layoutParams.height = measuredHeight;
                this.mBGMiddle.setLayoutParams(layoutParams);
            } else {
                layoutParams = (LayoutParams) this.mBGLeft.getLayoutParams();
                layoutParams.width = (measuredWidth - this.mMiddleDrawable.getMinimumWidth()) / 2;
                layoutParams.height = measuredHeight;
                this.mBGLeft.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mBGRight.getLayoutParams();
                layoutParams.width = (measuredWidth - this.mMiddleDrawable.getMinimumWidth()) / 2;
                layoutParams.height = measuredHeight;
                this.mBGRight.setLayoutParams(layoutParams);
                layoutParams = (LayoutParams) this.mBGMiddle.getLayoutParams();
                layoutParams.width = this.mMiddleDrawable.getMinimumWidth();
                layoutParams.height = measuredHeight;
                this.mBGMiddle.setLayoutParams(layoutParams);
            }
        }

        private void getParentBound(View view, int[] iArr) {
            if (view == null) {
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                int i = displayMetrics.widthPixels;
                int i2 = displayMetrics.heightPixels;
                iArr[0] = 0;
                iArr[1] = 0;
                iArr[2] = i;
                iArr[3] = i2;
                return;
            }
            r0 = new int[2];
            view.getLocationOnScreen(r0);
            iArr[0] = r0[0];
            iArr[1] = r0[1];
            iArr[2] = iArr[0] + view.getWidth();
            iArr[3] = iArr[1] + view.getHeight();
        }

        private void setMessageWidth(int i) {
            LayoutParams layoutParams = (LayoutParams) this.mMessageTextView.getLayoutParams();
            layoutParams.width = i;
            this.mMessageTextView.setLayoutParams(layoutParams);
        }

        private int getPopMarging() {
            return this.mMarging;
        }

        public int getArrowPadding() {
            return this.mArrowPadding;
        }

        public int getPaddingLeft() {
            return this.mBGPadding.left;
        }

        public int getCloseIconWidth() {
            return this.mCloseIcon.getMeasuredWidth();
        }

        public void setArrowUp() {
            this.mBGMiddle.setBackground(this.mMiddleDrawableUp);
        }

        public void setArrowDown() {
            this.mBGMiddle.setBackground(this.mMiddleDrawable);
        }

        public int getArrowWidth() {
            return this.mMiddleDrawable.getMinimumWidth();
        }

        public void setArrowPosition(int i) {
            this.mArrowLeft = i;
        }

        public void disableArrow(boolean z) {
            this.mWithArrow = !z;
        }

        public int getBackgroundMinWidth() {
            return this.mMinWidth;
        }

        public int getBackgroundLeftMinWidth() {
            return this.mLeftMinWidth;
        }

        public int getBackgroundMidMinWidth() {
            return this.mMidMinWidth;
        }

        public int getBackgroundRightMinWidth() {
            return this.mRightMinWidth;
        }
    }

    public GuidePopupWindow(Context context) {
        super(context);
        this.mContext = context;
        setTouchable(true);
        setOutsideTouchable(true);
        setClippingEnabled(false);
        setWindowLayoutMode(-2, -2);
        setInputMethodMode(2);
        setBackgroundDrawable(new ColorDrawable(0));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.GuidePopupWindow, R.attr.MeizuCommon_GuidePopupWindow, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.GuidePopupWindow_mcGPWBackGroundLeft);
        Drawable drawable2 = obtainStyledAttributes.getDrawable(R.styleable.GuidePopupWindow_mcGPWBackGroundMidArrowDown);
        Drawable drawable3 = obtainStyledAttributes.getDrawable(R.styleable.GuidePopupWindow_mcGPWBackGroundMidArrowUp);
        Drawable drawable4 = obtainStyledAttributes.getDrawable(R.styleable.GuidePopupWindow_mcGPWBackGroundRight);
        obtainStyledAttributes.recycle();
        if (drawable == null || drawable2 == null || drawable3 == null || drawable4 == null) {
            drawable = context.getResources().getDrawable(R.drawable.mc_guide_left);
            drawable2 = context.getResources().getDrawable(R.drawable.mc_guide_middle);
            drawable3 = context.getResources().getDrawable(R.drawable.mc_guide_middle_up);
            drawable4 = context.getResources().getDrawable(R.drawable.mc_guide_right);
        }
        this.mHandleView = new HandleView(this.mContext, drawable, drawable2, drawable3, drawable4);
        setContentView(this.mHandleView);
    }

    public void show(View view) {
        if (view != null) {
            this.mAnchorView = view;
            int[] iArr = new int[4];
            this.mHandleView.getParentBound(null, iArr);
            computeGuidePosition(iArr, view);
            showAsDropDown(view, this.mPopX, this.mPopY);
        }
    }

    public void show(View view, View view2) {
        if (view2 != null) {
            this.mAnchorView = view2;
            this.mParentView = view;
            show(view, view2, this.mOffX, this.mOffY);
        }
    }

    public void show(View view, View view2, int i, int i2) {
        if (view2 != null) {
            this.mOffX = i;
            this.mOffY = i2;
            this.mAnchorView = view2;
            this.mParentView = view;
            int[] iArr = new int[4];
            this.mHandleView.getParentBound(view, iArr);
            computeGuidePosition(iArr, view2);
            showAsDropDown(view2, this.mPopX, this.mPopY);
        }
    }

    public void show(Rect rect, View view) {
        if (view != null) {
            this.mAnchorView = view;
            show(rect, view, 0, 0);
        }
    }

    public void show(View view, int i, int i2) {
        if (view != null) {
            this.mOffX = i;
            this.mOffY = i2;
            this.mAnchorView = view;
            DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
            int i3 = displayMetrics.widthPixels;
            int i4 = displayMetrics.heightPixels;
            computeGuidePosition(new int[]{0, 0, i3, i4}, view);
            showAsDropDown(view, this.mPopX, this.mPopY);
        }
    }

    public void show(Rect rect, View view, int i, int i2) {
        if (view != null) {
            this.mOffX = i;
            this.mOffY = i2;
            this.mAnchorView = view;
            computeGuidePosition(new int[]{rect.left, rect.top, rect.right, rect.bottom}, view);
            showAsDropDown(view, this.mPopX, this.mPopY);
        }
    }

    private void computeGuidePosition(int[] iArr, View view) {
        if (view != null) {
            int paddingLeft;
            this.mHandleView.measure(0, 0);
            int measuredWidth = this.mHandleView.mContentView.getMeasuredWidth();
            this.mPopX = (view.getMeasuredWidth() - this.mHandleView.mContentView.getMeasuredWidth()) / 2;
            if (this.mHandleView.getPaddingLeft() < this.mHandleView.getPopMarging()) {
                paddingLeft = this.mHandleView.getPaddingLeft() - this.mHandleView.getPopMarging();
            } else {
                paddingLeft = this.mHandleView.getPopMarging() - this.mHandleView.getPaddingLeft();
            }
            int[] iArr2 = new int[2];
            getAnchorPositon(view, iArr2);
            if (measuredWidth > iArr[2] - iArr[0]) {
                this.mPopX = (paddingLeft - iArr2[0]) + iArr[0];
                this.mHandleView.setMessageWidth(((iArr[2] - iArr[0]) - (this.mHandleView.getPopMarging() * 2)) - this.mHandleView.getCloseIconWidth());
                this.mHandleView.measure(0, 0);
            } else {
                if (iArr2[0] + ((view.getMeasuredWidth() - measuredWidth) / 2) < iArr[0] + paddingLeft) {
                    this.mPopX = iArr[0] - iArr2[0];
                }
                if ((iArr2[0] + (view.getMeasuredWidth() / 2)) + (measuredWidth / 2) > iArr[2] - paddingLeft) {
                    this.mPopX = ((iArr[2] - paddingLeft) - iArr2[0]) - measuredWidth;
                }
            }
            switch (this.mLayoutMode) {
                case 4:
                    this.mPopY = ((-this.mHandleView.getMeasuredHeight()) - view.getMeasuredHeight()) + this.mHandleView.getArrowPadding();
                    this.mHandleView.setArrowDown();
                    break;
                case 5:
                    this.mPopY = -this.mHandleView.getArrowPadding();
                    this.mHandleView.setArrowUp();
                    break;
                default:
                    this.mPopY = ((-this.mHandleView.getMeasuredHeight()) - view.getMeasuredHeight()) + this.mHandleView.getArrowPadding();
                    if ((iArr2[1] - this.mHandleView.getMeasuredHeight()) + this.mHandleView.getArrowPadding() >= iArr[1]) {
                        this.mPopY = ((-this.mHandleView.getMeasuredHeight()) - view.getMeasuredHeight()) + this.mHandleView.getArrowPadding();
                        this.mHandleView.setArrowDown();
                        break;
                    }
                    this.mPopY = -this.mHandleView.getArrowPadding();
                    this.mHandleView.setArrowUp();
                    break;
            }
            paddingLeft = (((-this.mPopX) + (view.getMeasuredWidth() / 2)) - (this.mHandleView.getArrowWidth() / 2)) + this.mArrowOffx;
            if (paddingLeft > this.mHandleView.getMeasuredWidth() - (this.mHandleView.getBackgroundMidMinWidth() + this.mHandleView.getBackgroundRightMinWidth())) {
                paddingLeft = this.mHandleView.getMeasuredWidth() - (this.mHandleView.getBackgroundMidMinWidth() + this.mHandleView.getBackgroundRightMinWidth());
            } else if (paddingLeft < this.mHandleView.getBackgroundLeftMinWidth()) {
                paddingLeft = this.mHandleView.getBackgroundLeftMinWidth();
            }
            this.mPopY += this.mOffY;
            this.mPopX += this.mOffX;
            if (this.mLayoutMode == 1) {
                this.mPopX = ((iArr[0] + ((iArr[2] - iArr[0]) / 2)) - (this.mHandleView.getMeasuredWidth() / 2)) - iArr2[0];
            } else if (this.mLayoutMode == 2) {
                this.mPopY = (((iArr[1] + ((iArr[3] - iArr[1]) / 2)) - (this.mHandleView.getMeasuredHeight() / 2)) - iArr2[1]) - view.getMeasuredHeight();
            } else if (this.mLayoutMode == 3) {
                this.mPopX = ((iArr[0] + ((iArr[2] - iArr[0]) / 2)) - (this.mHandleView.getMeasuredWidth() / 2)) - iArr2[0];
                this.mPopY = (((iArr[1] + ((iArr[3] - iArr[1]) / 2)) - (this.mHandleView.getMeasuredHeight() / 2)) - iArr2[1]) - view.getMeasuredHeight();
            }
            this.mHandleView.setArrowPosition(paddingLeft);
        }
    }

    public void setHorizontalOffset(int i) {
        this.mOffX = i;
    }

    public void setVerticalOffset(int i) {
        this.mOffY = i;
    }

    public void updatePosition() {
        if (this.mAnchorView != null) {
            int[] iArr = new int[4];
            this.mHandleView.getParentBound(this.mParentView, iArr);
            computeGuidePosition(iArr, this.mAnchorView);
            iArr = new int[2];
            getAnchorPositon(this.mAnchorView, iArr);
            update(this.mPopX + iArr[0], (iArr[1] + this.mPopY) + this.mAnchorView.getMeasuredHeight(), -1, -1);
        }
    }

    private void getAnchorPositon(View view, int[] iArr) {
        if (view != null) {
            view.getLocationOnScreen(iArr);
        }
    }

    public void setArrowPosition(int i) {
        this.mArrowOffx = i;
    }

    public void setMessage(String str) {
        this.mHandleView.setText(str);
    }

    public void setLayoutMode(int i) {
        this.mLayoutMode = i;
    }

    public void disableArrow(boolean z) {
        this.mHandleView.disableArrow(z);
    }

    public void setMessageOnClickListener(OnClickListener onClickListener) {
        this.mHandleView.setMessageOnClickListener(onClickListener);
    }

    @TargetApi(19)
    public void update(int i, int i2, int i3, int i4, boolean z) {
        if (VERSION.SDK_INT < 19 || this.mAnchorView.isAttachedToWindow()) {
            super.update(i, i2, i3, i4, z);
        }
    }

    public void setColorStyle(int i) {
    }

    public TextView getMessageTextView() {
        return this.mHandleView.getMessageTextView();
    }

    public void setTextSize(int i) {
        this.mHandleView.setTextSize(i);
    }
}
