package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import cn.com.xy.sms.sdk.constant.Constant;
import com.meizu.common.R;
import com.ted.android.smscard.CardBase;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PullRefreshLayout extends RelativeLayout {
    public static final int DEFAULT_DURATION = 400;
    public static final int DRAW_ARC = 1;
    public static final int DRAW_LINE = 0;
    public static final int INVALIDATE_TIME = 15;
    private static final String SHAREDPREFERENCES_NAME = "pull_to_refresh";
    public static final String TAG = "PullRefreshLayout";
    private int mAction;
    private float mAppendResistance;
    private int mArcAnimationDistance;
    private Bouncer mBouncer;
    private View mContentView;
    private Context mContext;
    private int mCurrentOverScrollDistance;
    private SimpleDateFormat mDateFormat;
    private boolean mDrawOnTop;
    private boolean mEnablePull;
    private boolean mHandled;
    private String mHoursAgo;
    private TimeInterpolator mInterpolator;
    private boolean mIsAnimation;
    private boolean mIsMx2;
    private boolean mIsOptionalLastTimeSet;
    private float mLastEventX;
    private float mLastEventY;
    private String mLastRefreshStr;
    private Date mLastRefreshTime;
    private String mLastRefreshTimeKey;
    private int mLastTargetTop;
    private int mLineAnimationDistance;
    private String mMinutesAgo;
    private int mOffset;
    private float mOffsetY;
    private int mOptionalSeconds;
    private String mOptionalText;
    private int mOverScrollDistance;
    private boolean mOverScrolling;
    private int mPullDrawType;
    private int mPullRefreshAnimationColor;
    private PullRefreshAnimationView mPullRefreshAnimationView;
    private PullRefreshLayoutListener mPullRefreshLayoutListener;
    private PullRefreshGetData mPullRefreshListener;
    private boolean mRefreshFinished;
    private float mResistance;
    private ScrollState mScrollState;
    private String mSecondsAgo;
    private boolean mSpringBack;
    private View mTargetView;
    private int mTextColor;
    private int mThemeColor;
    private float mTotalOffset;
    private int mTouchSlop;
    private ViewState mViewTopState;
    private float xOffset;
    private float yOffset;

    class Bouncer implements AnimatorListener, AnimatorUpdateListener {
        private ValueAnimator mAnimator;
        private boolean mCanceled;
        private int mLastOffset;

        private Bouncer() {
        }

        public void recover(int i) {
            cancel();
            this.mCanceled = false;
            int abs = Math.abs((i * PullRefreshLayout.DEFAULT_DURATION) / PullRefreshLayout.this.mOverScrollDistance);
            this.mAnimator = new ValueAnimator();
            this.mAnimator.setIntValues(new int[]{0, i});
            this.mLastOffset = 0;
            this.mAnimator.setDuration((long) abs);
            this.mAnimator.setRepeatCount(0);
            if (PullRefreshLayout.this.mInterpolator == null) {
                if (VERSION.SDK_INT < 21) {
                    PullRefreshLayout.this.mInterpolator = new DecelerateInterpolator();
                } else {
                    PullRefreshLayout.this.mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.33f, 1.0f);
                }
            }
            this.mAnimator.setInterpolator(PullRefreshLayout.this.mInterpolator);
            this.mAnimator.addListener(this);
            this.mAnimator.addUpdateListener(this);
            this.mAnimator.start();
            PullRefreshLayout.this.mSpringBack = true;
        }

        public void cancel() {
            if (this.mAnimator != null && this.mAnimator.isRunning()) {
                this.mAnimator.cancel();
                PullRefreshLayout.this.mSpringBack = false;
            }
            this.mAnimator = null;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            PullRefreshLayout.this.offsetContent(this.mLastOffset - intValue);
            this.mLastOffset = intValue;
            PullRefreshLayout.this.mCurrentOverScrollDistance = PullRefreshLayout.this.mContentView.getTop();
        }

        public void onAnimationStart(Animator animator) {
        }

        public void onAnimationEnd(Animator animator) {
            this.mAnimator = null;
            if (!this.mCanceled) {
                if (PullRefreshLayout.this.mScrollState == ScrollState.STATE_LINE_END) {
                    PullRefreshLayout.this.mPullRefreshAnimationView.setStartTime();
                    PullRefreshLayout.this.mIsAnimation = true;
                    if (PullRefreshLayout.this.mPullRefreshListener != null) {
                        PullRefreshLayout.this.mPullRefreshListener.startGetData();
                    }
                } else if (PullRefreshLayout.this.mScrollState == ScrollState.STATE_ARC_END) {
                    PullRefreshLayout.this.mPullRefreshAnimationView.setStartTime();
                    PullRefreshLayout.this.mIsAnimation = true;
                    if (PullRefreshLayout.this.mPullRefreshListener != null) {
                        PullRefreshLayout.this.mPullRefreshListener.startGetData();
                    }
                }
                PullRefreshLayout.this.mSpringBack = false;
            }
        }

        public void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        public void onAnimationRepeat(Animator animator) {
        }
    }

    public interface PullRefreshGetData {
        void startGetData();
    }

    public interface PullRefreshLayoutListener {
        void updateScrollOffset(int i);
    }

    enum ScrollState {
        STATE_DEFAULT,
        STATE_LINE_MOVE,
        STATE_LINE_END,
        STATE_ARC_MOVE,
        STATE_ARC_END
    }

    enum ViewState {
        VIEW_NEED_OFFSET_DOWN,
        VIEW_NEED_OFFSET_UP,
        VIEW_NO_OFFSET
    }

    public PullRefreshLayout(Context context) {
        this(context, null);
    }

    public PullRefreshLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PullRefreshLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBouncer = new Bouncer();
        this.mPullDrawType = 0;
        this.mLineAnimationDistance = 20;
        this.mOverScrollDistance = 300;
        this.mArcAnimationDistance = 120;
        this.mOffset = 0;
        this.mPullRefreshListener = null;
        this.mPullRefreshLayoutListener = null;
        this.mScrollState = ScrollState.STATE_DEFAULT;
        this.mViewTopState = ViewState.VIEW_NO_OFFSET;
        this.mIsAnimation = false;
        this.mRefreshFinished = false;
        this.mSpringBack = false;
        this.mHandled = false;
        this.mDrawOnTop = true;
        this.mOverScrolling = false;
        this.mTotalOffset = 0.0f;
        this.mIsMx2 = false;
        this.mDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        this.mEnablePull = true;
        this.mResistance = 1.5f;
        this.mAppendResistance = 1.2f;
        this.mTextColor = -1;
        this.mIsOptionalLastTimeSet = false;
        this.mOptionalSeconds = 60;
        this.mOptionalText = null;
        this.mContext = context;
        this.mLineAnimationDistance = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_animheight);
        this.mArcAnimationDistance = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_holdheight);
        this.mOverScrollDistance = context.getResources().getDimensionPixelOffset(R.dimen.mc_pullRefresh_overscrollheight);
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(R.styleable.MZTheme);
        this.mThemeColor = obtainStyledAttributes.getColor(R.styleable.MZTheme_mzThemeColor, CardBase.DEFAULT_HIGHLIGHT);
        obtainStyledAttributes.recycle();
        obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.PullRefreshLayout);
        this.mPullDrawType = obtainStyledAttributes.getInt(R.styleable.PullRefreshLayout_mcPullRefreshAnimType, 0);
        this.mDrawOnTop = obtainStyledAttributes.getBoolean(R.styleable.PullRefreshLayout_mcPullRefreshDrawOnTop, true);
        this.mPullRefreshAnimationColor = obtainStyledAttributes.getColor(R.styleable.PullRefreshLayout_mcPullRefreshAnimationColor, this.mThemeColor);
        this.mTextColor = obtainStyledAttributes.getColor(R.styleable.PullRefreshLayout_mcPullRefreshTextColor, this.mTextColor);
        obtainStyledAttributes.recycle();
        initView();
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void stopRefresh() {
        if (this.mPullRefreshAnimationView != null && this.mContentView != null) {
            if (this.mPullDrawType == 1) {
                this.mPullRefreshAnimationView.stopDrawArcAnimation();
            } else {
                this.mPullRefreshAnimationView.stopDrawLineAnimation();
            }
            if (this.mIsAnimation) {
                this.mIsAnimation = false;
                if (this.mAction == 2) {
                    this.mRefreshFinished = true;
                }
                this.mScrollState = ScrollState.STATE_DEFAULT;
                this.mBouncer.recover(this.mContentView.getTop());
            }
        }
    }

    public void startRefresh() {
        if (!this.mIsAnimation) {
            if (this.mPullDrawType == 0) {
                this.mBouncer.recover(-this.mLineAnimationDistance);
                this.mScrollState = ScrollState.STATE_LINE_END;
                return;
            }
            this.mBouncer.recover(-this.mArcAnimationDistance);
            this.mScrollState = ScrollState.STATE_ARC_END;
        }
    }

    public void setOffset(int i) {
        this.mOffset = i;
        this.mPullRefreshAnimationView.setExtraOffset(i);
    }

    public int getOffset() {
        return this.mOffset;
    }

    public void setPullGetDataListener(PullRefreshGetData pullRefreshGetData) {
        if (pullRefreshGetData != null) {
            this.mPullRefreshListener = pullRefreshGetData;
        }
    }

    public void setPullRefreshLayoutListener(PullRefreshLayoutListener pullRefreshLayoutListener) {
        if (pullRefreshLayoutListener != null) {
            this.mPullRefreshLayoutListener = pullRefreshLayoutListener;
        }
    }

    public boolean getRefreshState() {
        return this.mIsAnimation;
    }

    public void setPromptTextColor(int i) {
        this.mPullRefreshAnimationView.setTextColor(i);
    }

    public int getPromptTextColor() {
        return this.mPullRefreshAnimationView.getTextColor();
    }

    public void setOverScrollDistance(int i) {
        this.mOverScrollDistance = i;
        this.mPullRefreshAnimationView.setOverScrollDistance(i);
    }

    public int getOverScrollDistance() {
        return this.mOverScrollDistance;
    }

    private void initView() {
        this.mPullRefreshAnimationView = new PullRefreshAnimationView(this.mContext, this.mPullRefreshAnimationColor, this.mPullDrawType, this);
        setPromptTextColor(this.mTextColor);
        this.mIsMx2 = isMx2();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mContentView = getChildAt(0);
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (!this.mEnablePull) {
            return super.dispatchTouchEvent(motionEvent);
        }
        if (this.mContentView == null && !eventInView(motionEvent, this.mContentView)) {
            return super.dispatchTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        this.mHandled = processTouchEvent(motionEvent);
        super.dispatchTouchEvent(motionEvent);
        if (action == 3 || action == 1) {
            this.mLastEventY = 0.0f;
            this.mLastTargetTop = 0;
            this.mRefreshFinished = false;
            return true;
        }
        if (action == 0) {
            this.mBouncer.cancel();
        }
        this.mLastEventY = motionEvent.getY();
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mEnablePull) {
            return this.mHandled;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    protected void dispatchDraw(Canvas canvas) {
        if (!this.mEnablePull) {
            super.dispatchDraw(canvas);
        } else if (this.mDrawOnTop) {
            super.dispatchDraw(canvas);
            if (this.mPullRefreshAnimationView != null) {
                this.mPullRefreshAnimationView.setCurrentOverScrollDistance(this.mCurrentOverScrollDistance, canvas);
            }
        } else {
            if (this.mPullRefreshAnimationView != null) {
                this.mPullRefreshAnimationView.setCurrentOverScrollDistance(this.mCurrentOverScrollDistance, canvas);
            }
            super.dispatchDraw(canvas);
        }
    }

    private void offsetContent(int i) {
        int top = this.mContentView.getTop() + i;
        if (top <= 0) {
            i = -this.mContentView.getTop();
        } else if (top > this.mOverScrollDistance) {
            i = this.mOverScrollDistance - this.mContentView.getTop();
        }
        this.mContentView.offsetTopAndBottom(i);
        this.mCurrentOverScrollDistance = this.mContentView.getTop();
        if (this.mPullRefreshLayoutListener != null) {
            this.mPullRefreshLayoutListener.updateScrollOffset(this.mCurrentOverScrollDistance);
        }
        if (getParent() != null && this.mCurrentOverScrollDistance > 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        postInvalidateDelayed(15);
    }

    private boolean processTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        this.mAction = action;
        int contentViewTop = getContentViewTop(this.mContentView);
        if (action == 3 || action == 1) {
            if (contentViewTop > 0) {
                action = this.mContentView.getTop();
                if (this.mPullDrawType == 0) {
                    if (action >= this.mLineAnimationDistance) {
                        this.mBouncer.recover(action - this.mLineAnimationDistance);
                        this.mScrollState = ScrollState.STATE_LINE_END;
                    } else {
                        this.mBouncer.recover(action);
                        this.mScrollState = ScrollState.STATE_DEFAULT;
                    }
                } else if (action >= this.mArcAnimationDistance) {
                    this.mBouncer.recover(action - this.mArcAnimationDistance);
                    this.mScrollState = ScrollState.STATE_ARC_END;
                    if (!TextUtils.isEmpty(this.mLastRefreshTimeKey)) {
                        this.mLastRefreshTime = new Date();
                    }
                } else {
                    this.mBouncer.recover(action);
                    this.mScrollState = ScrollState.STATE_DEFAULT;
                }
            }
            this.mOverScrolling = false;
            this.mTotalOffset = 0.0f;
            return false;
        } else if (action == 0) {
            this.mLastEventY = motionEvent.getY();
            this.mLastEventX = motionEvent.getX();
            this.mOffsetY = motionEvent.getY();
            if (this.mLastRefreshTime == null || TextUtils.isEmpty(this.mLastRefreshTimeKey)) {
                return false;
            }
            if (this.mIsOptionalLastTimeSet) {
                this.mPullRefreshAnimationView.setLastRefreshTime(getLastTimeOptional());
                return false;
            }
            this.mPullRefreshAnimationView.setLastRefreshTime(getLastTime());
            return false;
        } else if (action != 2) {
            return false;
        } else {
            float y = motionEvent.getY() - this.mLastEventY;
            this.xOffset = Math.abs(motionEvent.getX() - this.mLastEventX);
            this.yOffset = Math.abs(motionEvent.getY() - this.mOffsetY);
            this.mPullRefreshAnimationView.resetSpringFlag(false);
            if (Math.abs(y) < 1.0f) {
                this.mTotalOffset = y + this.mTotalOffset;
                if (Math.abs(this.mTotalOffset) <= 1.0f) {
                    return false;
                }
                y = this.mTotalOffset;
                this.mTotalOffset = 0.0f;
            }
            if (this.yOffset < ((float) this.mTouchSlop)) {
                return false;
            }
            if (this.xOffset > ((float) this.mTouchSlop) && this.xOffset > this.yOffset && this.mCurrentOverScrollDistance == 0) {
                return false;
            }
            if ((this.mIsAnimation && this.mPullDrawType != 1) || this.mRefreshFinished) {
                return false;
            }
            if (this.mCurrentOverScrollDistance >= this.mOverScrollDistance && y > 0.0f) {
                return false;
            }
            boolean z;
            int i = (int) y;
            if (this.mPullDrawType == 0) {
                this.mScrollState = ScrollState.STATE_LINE_MOVE;
                this.mPullRefreshAnimationView.stopDrawLineAnimation();
            } else {
                this.mScrollState = ScrollState.STATE_ARC_MOVE;
            }
            if (i <= 0) {
                z = false;
            } else {
                z = true;
            }
            if (contentViewTop >= 0) {
                this.mViewTopState = getViewTopState(getTouchViewParent(this.mContentView, motionEvent), z);
                if (ViewState.VIEW_NO_OFFSET == this.mViewTopState) {
                    return false;
                }
                this.mOverScrolling = true;
                offsetContent(Math.round(((float) i) / (((this.mAppendResistance * ((float) this.mCurrentOverScrollDistance)) / ((float) this.mOverScrollDistance)) + this.mResistance)));
                z = true;
            } else {
                z = false;
            }
            return z;
        }
    }

    private ViewState getViewTopState(View view, boolean z) {
        ViewState viewState = ViewState.VIEW_NO_OFFSET;
        if (view == null || view.getScrollY() > 0) {
            return viewState;
        }
        ViewState viewState2;
        if (this.mContentView.getScrollY() >= 0 || !z) {
            viewState2 = viewState;
        } else {
            viewState2 = ViewState.VIEW_NEED_OFFSET_DOWN;
        }
        if (AbsListView.class.isAssignableFrom(view.getClass())) {
            if (((AbsListView) view).getFirstVisiblePosition() == 0) {
                if (((AbsListView) view).getChildCount() <= 0) {
                    return ViewState.VIEW_NO_OFFSET;
                }
                if (getContentViewTop(((AbsListView) view).getChildAt(0)) >= view.getPaddingTop() && z) {
                    return ViewState.VIEW_NEED_OFFSET_DOWN;
                }
                if (getContentViewTop(this.mContentView) <= 0 || z) {
                    return viewState2;
                }
                return ViewState.VIEW_NEED_OFFSET_UP;
            } else if (getContentViewTop(this.mContentView) > 0) {
                return ViewState.VIEW_NEED_OFFSET_UP;
            } else {
                return viewState2;
            }
        } else if (ScrollView.class.isAssignableFrom(view.getClass())) {
            if (view.getScrollY() <= 0 && z) {
                return ViewState.VIEW_NEED_OFFSET_DOWN;
            }
            if (view.getTop() <= 0 || this.yOffset <= ((float) this.mTouchSlop) || z) {
                return viewState2;
            }
            return ViewState.VIEW_NEED_OFFSET_UP;
        } else if (VERSION.SDK_INT > 14) {
            if (this.mContentView == null) {
                return viewState2;
            }
            if (this.mContentView.canScrollVertically(-1) || (this.mCurrentOverScrollDistance == 0 && !z)) {
                return ViewState.VIEW_NO_OFFSET;
            }
            if (!this.mContentView.canScrollVertically(-1) && z) {
                return ViewState.VIEW_NEED_OFFSET_DOWN;
            }
            if (this.mContentView.canScrollVertically(-1) || z) {
                return viewState2;
            }
            return ViewState.VIEW_NEED_OFFSET_UP;
        } else if (z) {
            if (view.getScrollY() <= 0) {
                return ViewState.VIEW_NEED_OFFSET_DOWN;
            }
            return ViewState.VIEW_NO_OFFSET;
        } else if (view.getTop() <= 0 || this.yOffset <= ((float) this.mTouchSlop)) {
            return viewState2;
        } else {
            return ViewState.VIEW_NEED_OFFSET_UP;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mEnablePull) {
            super.onLayout(z, i, i2, i3, i4);
        } else if (this.mContentView != null) {
            int top = this.mContentView.getTop();
            this.mContentView.layout(0, top, i3, getMeasuredHeight() + top);
        }
    }

    private int getViewTop(View view) {
        if (view == null) {
            return 0;
        }
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        return iArr[1];
    }

    private boolean eventInView(MotionEvent motionEvent, View view) {
        if (motionEvent == null || view == null) {
            return false;
        }
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int width = view.getWidth();
        int height = view.getHeight();
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(i, i2, width + i, height + i2).contains(rawX, rawY);
    }

    private View getTouchViewParent(View view, MotionEvent motionEvent) {
        if (view == null) {
            return null;
        }
        if (!eventInView(motionEvent, view)) {
            return null;
        }
        if (!(view instanceof ViewGroup)) {
            return view;
        }
        if (AbsListView.class.isAssignableFrom(view.getClass()) || ScrollView.class.isAssignableFrom(view.getClass())) {
            return eventInView(motionEvent, view) ? view : view;
        } else {
            if (!(view instanceof ViewGroup)) {
                return view;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount() - 1;
            while (childCount >= 0) {
                View childAt = viewGroup.getChildAt(childCount);
                if (!eventInView(motionEvent, childAt)) {
                    childCount--;
                } else if (childAt instanceof ViewGroup) {
                    return getTouchViewParent(childAt, motionEvent);
                } else {
                    return childAt;
                }
            }
            return view;
        }
    }

    private int getContentViewTop(View view) {
        return view.getTop();
    }

    boolean isMx2() {
        try {
            Field field = Class.forName("android.os.BuildExt").getField("IS_MX2");
            field.setAccessible(true);
            return ((Boolean) field.get(null)).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public String getLastTime() {
        if (this.mLastRefreshTime == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mLastRefreshStr);
        stringBuilder.append(" ");
        long time = new Date().getTime() - this.mLastRefreshTime.getTime();
        if (time > 0) {
            if (time < Constant.MINUTE) {
                stringBuilder.append(time / 1000);
                stringBuilder.append(this.mSecondsAgo);
            } else if (time < Constant.HOUR) {
                stringBuilder.append(time / Constant.MINUTE);
                stringBuilder.append(this.mMinutesAgo);
            } else if (time < 86400000) {
                stringBuilder.append(time / Constant.HOUR);
                stringBuilder.append(this.mHoursAgo);
            } else {
                stringBuilder.append(this.mDateFormat.format(this.mLastRefreshTime));
            }
        }
        return stringBuilder.toString();
    }

    public void setOptionalLastTimeDisplay(int i, String str) {
        this.mIsOptionalLastTimeSet = true;
        if (i < 60) {
            i = 60;
        }
        this.mOptionalSeconds = i;
        if (str == null) {
            this.mOptionalText = getResources().getString(R.string.mc_last_refresh_just_now);
        } else {
            this.mOptionalText = str;
        }
    }

    public boolean isOptionalLastTimeDisplaySet() {
        return this.mIsOptionalLastTimeSet;
    }

    private String getLastTimeOptional() {
        if (this.mLastRefreshTime == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mLastRefreshStr);
        stringBuilder.append(" ");
        long time = new Date().getTime() - this.mLastRefreshTime.getTime();
        if (time >= 0) {
            if (time < ((long) (this.mOptionalSeconds * 1000))) {
                stringBuilder.append(this.mOptionalText);
            } else if (time < Constant.HOUR) {
                stringBuilder.append(time / Constant.MINUTE);
                stringBuilder.append(this.mMinutesAgo);
            } else if (time < 86400000) {
                stringBuilder.append(time / Constant.HOUR);
                stringBuilder.append(this.mHoursAgo);
            } else {
                stringBuilder.append(this.mDateFormat.format(this.mLastRefreshTime));
            }
        }
        return stringBuilder.toString();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!TextUtils.isEmpty(this.mLastRefreshTimeKey)) {
            long j = this.mContext.getSharedPreferences(SHAREDPREFERENCES_NAME, 0).getLong(this.mLastRefreshTimeKey, 0);
            if (j != 0) {
                this.mLastRefreshTime = new Date(j);
            }
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!(TextUtils.isEmpty(this.mLastRefreshTimeKey) || this.mLastRefreshTime == null)) {
            Editor edit = this.mContext.getSharedPreferences(SHAREDPREFERENCES_NAME, 0).edit();
            edit.putLong(this.mLastRefreshTimeKey, this.mLastRefreshTime.getTime());
            edit.commit();
        }
        this.mPullRefreshAnimationView.removeCallbacksAndMessages();
    }

    public void setLastRefreshTimeKey(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.mLastRefreshTimeKey = str;
            if (TextUtils.isEmpty(this.mLastRefreshStr)) {
                this.mLastRefreshStr = this.mContext.getString(R.string.mc_last_refresh);
                this.mHoursAgo = this.mContext.getString(R.string.mc_last_refresh_hour);
                this.mMinutesAgo = this.mContext.getString(R.string.mc_last_refresh_minute);
                this.mSecondsAgo = this.mContext.getString(R.string.mc_last_refresh_second);
            }
        }
    }

    public void setEnablePull(boolean z) {
        this.mEnablePull = z;
    }

    public boolean isEnablePull() {
        return this.mEnablePull;
    }

    public void removeLastRefreshTimeKey() {
        if (!TextUtils.isEmpty(this.mLastRefreshTimeKey)) {
            Editor edit = this.mContext.getSharedPreferences(SHAREDPREFERENCES_NAME, 0).edit();
            edit.remove(this.mLastRefreshTimeKey);
            edit.commit();
            this.mLastRefreshTimeKey = null;
            this.mLastRefreshTime = null;
            this.mPullRefreshAnimationView.setLastRefreshTimeDefault();
        }
    }

    public static void removeLastRefreshTimeKeys(Context context, String[] strArr) {
        int i = 0;
        if (context != null && strArr != null) {
            Editor edit = context.getSharedPreferences(SHAREDPREFERENCES_NAME, 0).edit();
            int length = strArr.length;
            while (i < length) {
                Object obj = strArr[i];
                if (!TextUtils.isEmpty(obj)) {
                    edit.remove(obj);
                }
                i++;
            }
            edit.commit();
        }
    }

    public void setRingColor(int i) {
        if (this.mPullRefreshAnimationView != null) {
            this.mPullRefreshAnimationView.setPaintArcColor(i);
        }
    }

    public int getRingColor() {
        return this.mPullRefreshAnimationView != null ? this.mPullRefreshAnimationView.getPaintArcColor() : 0;
    }

    public void setRingBackgroundColor(int i) {
        if (this.mPullRefreshAnimationView != null) {
            this.mPullRefreshAnimationView.setPaintArcBackColor(i);
        }
    }

    public int getRingBackgroundColor() {
        return this.mPullRefreshAnimationView != null ? this.mPullRefreshAnimationView.getPaintArcBackColor() : 0;
    }

    public void resetRingColor() {
        if (this.mPullRefreshAnimationView != null) {
            this.mPullRefreshAnimationView.resetRingColor();
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (this.mContentView != null && AbsListView.class.isAssignableFrom(this.mContentView.getClass()) && getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(z);
        }
    }

    public void setResistance(float f) {
        this.mResistance = f;
    }

    public void setAppendResistance(float f) {
        this.mAppendResistance = f;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        accessibilityNodeInfo.setClassName(PullRefreshLayout.class.getName());
    }
}
