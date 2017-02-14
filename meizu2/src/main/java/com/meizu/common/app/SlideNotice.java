package com.meizu.common.app;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.meizu.common.R;
import com.meizu.common.app.SlideNoticeManagerService.NoticeCallBack;
import com.meizu.common.util.ResourceUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

public class SlideNotice {
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final int NOTICE_CLICK = 0;
    public static final int NOTICE_TYPE_FAILURE = 0;
    public static final int NOTICE_TYPE_SUCCESS = 1;
    public static final int SHOW_ANIMATION_DURATION = 300;
    private static final String TAG = "SlideNotice";
    private static SlideNoticeManagerService mService;
    private static Field sMeizuFlag;
    private Context mContext;
    private int mDuration;
    private SlideViewController mSlideViewController;
    private Toast mToast;

    static final class NoticeHandler extends Handler {
        private WeakReference<SlideNotice> mNotice;

        public NoticeHandler(SlideNotice slideNotice) {
            this.mNotice = new WeakReference(slideNotice);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    ((OnClickNoticeListener) message.obj).onClick((SlideNotice) this.mNotice.get());
                    return;
                default:
                    return;
            }
        }
    }

    public interface OnClickNoticeListener {
        void onClick(SlideNotice slideNotice);
    }

    static final class SlideContainerView extends FrameLayout {
        public SlideContainerView(Context context) {
            super(context);
        }
    }

    class SlideViewController implements NoticeCallBack {
        private static final int SLIDE_ANIMATION_TYPE_HIDE = 1;
        private static final int SLIDE_ANIMATION_TYPE_SHOW = 0;
        private boolean isBelowDefaultActionBar;
        private boolean isSupportedImmersedStatusBar;
        private int mActionBarHeight;
        private WeakReference<View> mAnchor;
        private AnimationExecutor mAnimationExecutor;
        private int mAnimationType;
        private int mBeginColor;
        private LinearLayout mContent;
        private final OnClickListener mContentClickListener = new OnClickListener() {
            public void onClick(View view) {
                Message obtain;
                if (SlideViewController.this.mNoticeClickMsg != null) {
                    obtain = Message.obtain(SlideViewController.this.mNoticeClickMsg);
                } else {
                    obtain = null;
                }
                if (obtain != null) {
                    obtain.sendToTarget();
                }
            }
        };
        private FrameLayout mCustom;
        private View mCustomView;
        private int[] mDrawingLocation = new int[2];
        private int mEndColor;
        final Handler mHandler = new Handler();
        final Runnable mHide = new Runnable() {
            public void run() {
                SlideViewController.this.handleHide();
            }
        };
        private boolean mIsOverlaidByStatusBar = false;
        private boolean mIsOverlaidByStatusBarSet;
        private int mLeft;
        private Message mNoticeClickMsg;
        private Handler mNoticeHandler;
        private int mNoticeHeight;
        private LinearLayout mNoticePanel;
        private TextView mNoticeView;
        private int mNoticeWidth;
        private OnClickNoticeListener mOnClickNoticeListener;
        private OnScrollChangedListener mOnScrollChangedListener = new OnScrollChangedListener() {
            public void onScrollChanged() {
                View view = SlideViewController.this.mAnchor != null ? (View) SlideViewController.this.mAnchor.get() : null;
                if (view != null && view.getParent() != null && SlideViewController.this.mSlideContent != null) {
                    LayoutParams layoutParams = new LayoutParams();
                    SlideViewController.this.findViewPosition(view, layoutParams);
                    SlideViewController.this.update(layoutParams.x, layoutParams.y);
                }
            }
        };
        private LayoutParams mParams = new LayoutParams();
        final Runnable mShow = new Runnable() {
            public void run() {
                SlideViewController.this.handleShow();
            }
        };
        private int mSlidHeight;
        private View mSlideContent;
        private View mSlideView;
        private int mSlideY = -1;
        private int mStatusBarHeight;
        private int mTop;
        private WindowManager mWManager;
        private boolean showing;
        private CharSequence text;

        class AnimationExecutor {
            private ValueAnimator anim;
            private SlidingAnimatorListener animatorListener;

            public AnimationExecutor() {
                this.animatorListener = new SlidingAnimatorListener();
            }

            public void cancel() {
                if (this.anim != null && this.anim.isRunning()) {
                    this.anim.cancel();
                }
            }

            public void start(int i, int i2) {
                if (this.anim != null && this.anim.isRunning()) {
                    this.anim.cancel();
                }
                this.anim = ObjectAnimator.ofInt(SlideNotice.this.mSlideViewController, "height", new int[]{i, i2});
                this.anim.setDuration(300);
                this.anim.addListener(this.animatorListener);
                this.anim.setInterpolator(new DecelerateInterpolator());
                this.anim.start();
            }

            public boolean isRunning() {
                return this.anim == null ? false : this.anim.isRunning();
            }
        }

        class SlidingAnimatorListener implements AnimatorListener {
            private SlidingAnimatorListener() {
            }

            public void onAnimationStart(Animator animator) {
                SlideNotice.this.mSlideViewController.showing = true;
            }

            public void onAnimationEnd(Animator animator) {
                if (SlideViewController.this.mAnimationType != 0 && SlideViewController.this.mSlideY <= 0) {
                    SlideViewController.this.mSlideY = -1;
                    SlideNotice.this.mSlideViewController.destroy();
                }
            }

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
            }
        }

        public SlideViewController(SlideNotice slideNotice) {
            this.mNoticeHandler = new NoticeHandler(slideNotice);
            init();
        }

        public void setGravity(int i) {
            this.mNoticePanel.setGravity(i);
        }

        public void setAnchor(View view) {
            if (view != null) {
                unregisterForScrollChanged();
                this.mAnchor = new WeakReference(view);
                ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
                if (viewTreeObserver != null) {
                    viewTreeObserver.addOnScrollChangedListener(this.mOnScrollChangedListener);
                }
            }
        }

        private void unregisterForScrollChanged() {
            View view;
            WeakReference weakReference = this.mAnchor;
            if (weakReference != null) {
                view = (View) weakReference.get();
            } else {
                view = null;
            }
            if (view != null) {
                view.getViewTreeObserver().removeOnScrollChangedListener(this.mOnScrollChangedListener);
            }
            this.mAnchor = null;
        }

        public void setOnClickNoticeListener(OnClickNoticeListener onClickNoticeListener) {
            this.mOnClickNoticeListener = onClickNoticeListener;
        }

        public boolean isShowing() {
            return this.showing;
        }

        public void setBelowDefaultActionBar(boolean z) {
            this.isBelowDefaultActionBar = z;
            if (this.isBelowDefaultActionBar) {
                this.mTop = this.mActionBarHeight + this.mStatusBarHeight;
            } else {
                this.mTop = 0;
            }
        }

        public void setText(CharSequence charSequence) {
            this.text = charSequence;
            if (this.mNoticeView != null) {
                this.mNoticeView.setText(charSequence);
            }
        }

        public CharSequence getText() {
            return this.text;
        }

        public void setBeginColor(int i) {
            this.mBeginColor = i;
        }

        public void setEndColor(int i) {
            this.mEndColor = i;
        }

        public void setIsOverlaidByStatusBar(boolean z) {
            if (this.isSupportedImmersedStatusBar) {
                this.mIsOverlaidByStatusBar = z;
                this.mIsOverlaidByStatusBarSet = true;
            }
        }

        public void setNoticeHeight(int i) {
            this.mNoticeHeight = i;
        }

        public void setNoticeWidth(int i) {
            this.mNoticeWidth = i;
        }

        private void init() {
            this.mSlideContent = LayoutInflater.from(SlideNotice.this.mContext).inflate(R.layout.mc_slide_notice_content, null);
            this.mNoticeView = (TextView) this.mSlideContent.findViewById(R.id.noticeView);
            this.mNoticePanel = (LinearLayout) this.mSlideContent.findViewById(R.id.noticePanel);
            this.mCustom = (FrameLayout) this.mSlideContent.findViewById(R.id.custom);
            this.mSlideContent.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
            this.mStatusBarHeight = ResourceUtils.getStatusBarHeight(SlideNotice.this.mContext);
            this.mActionBarHeight = getActionBarHeight(SlideNotice.this.mContext);
            this.mAnimationExecutor = new AnimationExecutor();
            Context applicationContext = SlideNotice.this.mContext.getApplicationContext();
            if (applicationContext != null) {
                this.mWManager = (WindowManager) applicationContext.getSystemService("window");
            } else {
                this.mWManager = (WindowManager) SlideNotice.this.mContext.getSystemService("window");
            }
            this.mParams.height = -2;
            this.mParams.width = -1;
            this.mParams.gravity = 51;
            this.mParams.format = -3;
            this.mParams.setTitle("SlideNotice:" + Integer.toHexString(hashCode()));
            this.mParams.flags = 40;
            this.isSupportedImmersedStatusBar = setTransStatusBar(this.mParams, true);
            if (!this.isSupportedImmersedStatusBar) {
                this.mStatusBarHeight = 0;
            }
            if (!(SlideNotice.this.mContext instanceof Activity)) {
                this.mActionBarHeight = SlideNotice.this.mContext.getResources().getDimensionPixelSize(R.dimen.mz_action_bar_default_height);
            }
        }

        private void destroy() {
            if (!(this.mSlideView == null || this.mSlideView.getParent() == null)) {
                this.mWManager.removeView(this.mSlideView);
            }
            unregisterForScrollChanged();
            this.mOnScrollChangedListener = null;
            this.showing = false;
        }

        public boolean handleShow() {
            if (this.showing) {
                return false;
            }
            prepareNotice(this.mParams);
            setupPosition();
            setupView();
            this.mParams.x = this.mLeft;
            this.mParams.y = this.mTop;
            this.mSlideContent.setVisibility(0);
            invokeNotice(this.mParams);
            this.mSlideContent.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    SlideViewController.this.mSlideContent.getViewTreeObserver().removeOnPreDrawListener(this);
                    SlideViewController.this.mSlidHeight = SlideViewController.this.mSlideView.getMeasuredHeight();
                    SlideViewController.this.mAnimationExecutor.start(0, SlideViewController.this.mSlidHeight);
                    SlideViewController.this.mAnimationType = 0;
                    return false;
                }
            });
            this.showing = true;
            trySendAccessibilityEvent();
            return true;
        }

        private void prepareNotice(LayoutParams layoutParams) {
            if (this.mSlideView == null) {
                View slideContainerView = new SlideContainerView(SlideNotice.this.mContext);
                slideContainerView.addView(this.mSlideContent);
                this.mSlideView = slideContainerView;
            }
            if (SlideNotice.this.mContext instanceof Activity) {
                IBinder windowToken = ((Activity) SlideNotice.this.mContext).getWindow().getDecorView().getWindowToken();
                if (windowToken != null) {
                    layoutParams.token = windowToken;
                    layoutParams.type = 1000;
                } else {
                    layoutParams.type = 2005;
                }
            } else {
                layoutParams.type = 2005;
            }
            if (this.mNoticeWidth > 0) {
                layoutParams.width = this.mNoticeWidth;
            }
        }

        private void invokeNotice(LayoutParams layoutParams) {
            if (SlideNotice.this.mContext != null) {
                layoutParams.packageName = SlideNotice.this.mContext.getPackageName();
            }
            this.mWManager.addView(this.mSlideView, layoutParams);
        }

        private void setupView() {
            if (setupCustom()) {
                this.mNoticePanel.setVisibility(8);
            } else {
                setupContent();
            }
            if (this.mOnClickNoticeListener != null) {
                this.mSlideContent.setOnClickListener(this.mContentClickListener);
                this.mNoticeClickMsg = this.mNoticeHandler.obtainMessage(0, this.mOnClickNoticeListener);
            }
        }

        private void setupContent() {
            this.mNoticeView.setVisibility(8);
            if (this.mTop < this.mStatusBarHeight && !this.mIsOverlaidByStatusBarSet) {
                this.mIsOverlaidByStatusBar = true;
            }
            if (this.mIsOverlaidByStatusBar) {
                this.mNoticeView = (TextView) this.mSlideContent.findViewById(R.id.noticeView_no_title_bar);
                ((LinearLayout.LayoutParams) this.mNoticeView.getLayoutParams()).topMargin = SlideNotice.this.mContext.getResources().getDimensionPixelSize(R.dimen.mc_slide_notice_textview_margin_top);
            } else {
                ((LinearLayout.LayoutParams) this.mNoticeView.getLayoutParams()).topMargin = 0;
                this.mNoticeView = (TextView) this.mSlideContent.findViewById(R.id.noticeView);
            }
            if (this.mNoticeHeight > 0) {
                this.mNoticePanel.getLayoutParams().height = this.mNoticeHeight;
            }
            this.mNoticeView.setText(this.text);
            this.mNoticeView.setVisibility(0);
            this.mNoticePanel.setBackgroundColor(this.mBeginColor);
            this.mNoticePanel.setVisibility(0);
        }

        private boolean setupCustom() {
            if (this.mCustomView != null) {
                if (this.mCustomView.getParent() == this.mCustom) {
                    this.mCustom.removeView(this.mCustomView);
                }
                this.mCustom.addView(this.mCustomView);
                ViewGroup.LayoutParams layoutParams = this.mCustomView.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = -2;
                this.mCustom.setVisibility(0);
                return true;
            }
            this.mCustom.setVisibility(8);
            return false;
        }

        private void setupPosition() {
            if (this.mAnchor != null) {
                View view = this.mAnchor != null ? (View) this.mAnchor.get() : null;
                if (view != null && view.getParent() != null) {
                    LayoutParams layoutParams = new LayoutParams();
                    findViewPosition(view, layoutParams);
                    this.mTop = layoutParams.y;
                }
            }
        }

        private void trySendAccessibilityEvent() {
            AccessibilityManager accessibilityManager = (AccessibilityManager) SlideNotice.this.mContext.getSystemService("accessibility");
            if (accessibilityManager.isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(64);
                obtain.setClassName(getClass().getName());
                obtain.setPackageName(this.mSlideView.getContext().getPackageName());
                this.mSlideView.dispatchPopulateAccessibilityEvent(obtain);
                accessibilityManager.sendAccessibilityEvent(obtain);
            }
        }

        public void handleHide() {
            if (!this.showing) {
                return;
            }
            if (this.mSlideY < 0) {
                if (this.mAnimationExecutor.isRunning()) {
                    this.mAnimationExecutor.cancel();
                }
                destroy();
                return;
            }
            this.mAnimationExecutor.start(this.mSlideY, 0);
            this.mAnimationType = 1;
        }

        public void setLeft(int i) {
            this.mLeft = i;
        }

        public void setTop(int i) {
            this.mTop = i;
        }

        public int getTop() {
            return this.mTop;
        }

        public int getHeight() {
            return this.mSlideY;
        }

        public void setHeight(int i) {
            this.mNoticePanel.setBackgroundColor(ResourceUtils.getGradualColor(this.mBeginColor, this.mEndColor, ((float) i) / (((float) this.mSlidHeight) + 0.1f), 1));
            this.mNoticeView.setTextColor(ResourceUtils.getGradualColor(ViewCompat.MEASURED_SIZE_MASK, -1, ((float) i) / (((float) this.mSlidHeight) + 0.1f), 1));
            this.mSlideY = i;
            this.mSlideContent.setTranslationY((float) (this.mSlideY - this.mSlidHeight));
        }

        public void resetTop() {
            if (this.isBelowDefaultActionBar) {
                this.mTop = this.mActionBarHeight + this.mStatusBarHeight;
            } else {
                this.mTop = 0;
            }
        }

        private int getActionBarHeight(Context context) {
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(16843499, typedValue, true)) {
                return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
            }
            return 144;
        }

        private void findViewPosition(View view, LayoutParams layoutParams) {
            int height = view.getHeight();
            view.getLocationInWindow(this.mDrawingLocation);
            layoutParams.x = this.mDrawingLocation[0];
            layoutParams.y = height + this.mDrawingLocation[1];
        }

        private void update(int i, int i2) {
            if (this.showing && this.mSlideView != null && this.mSlideView.getParent() != null) {
                this.mTop = i2;
                this.mParams.y = this.mTop;
                this.mWManager.updateViewLayout(this.mSlideView, this.mParams);
            }
        }

        public void setCustomView(View view) {
            this.mCustomView = view;
        }

        public void hide() {
            try {
                this.mHandler.post(this.mHide);
            } catch (Exception e) {
                handleHide();
            }
        }

        public void show() {
            try {
                this.mHandler.post(this.mShow);
            } catch (Exception e) {
                handleShow();
            }
        }

        private boolean setTransStatusBar(LayoutParams layoutParams, boolean z) {
            try {
                if (VERSION.SDK_INT < 19) {
                    int i;
                    if (SlideNotice.sMeizuFlag == null) {
                        i = 64;
                        SlideNotice.sMeizuFlag = layoutParams.getClass().getDeclaredField("meizuFlags");
                        SlideNotice.sMeizuFlag.setAccessible(true);
                    } else {
                        i = 0;
                    }
                    int i2 = SlideNotice.sMeizuFlag.getInt(layoutParams);
                    if (z) {
                        i |= i2;
                    } else {
                        i = (i ^ -1) & i2;
                    }
                    SlideNotice.sMeizuFlag.setInt(layoutParams, i);
                    return true;
                } else if (z) {
                    layoutParams.flags |= 67108864;
                    return true;
                } else {
                    layoutParams.flags &= -67108865;
                    return true;
                }
            } catch (Exception e) {
                Log.e(SlideNotice.TAG, "Can't set the status bar to be transparent, Caused by:" + e.getMessage());
                return false;
            }
        }
    }

    private static SlideNoticeManagerService getService() {
        if (mService != null) {
            return mService;
        }
        mService = new SlideNoticeManagerService();
        return mService;
    }

    public SlideNotice(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context can not be null!");
        }
        this.mContext = context;
        this.mSlideViewController = new SlideViewController(this);
    }

    public SlideNotice(Context context, CharSequence charSequence, int i) {
        this(context);
        this.mToast = Toast.makeText(context, charSequence, i);
    }

    public void setText(CharSequence charSequence) {
        this.mSlideViewController.setText(charSequence);
        if (this.mToast != null) {
            this.mToast.setText(charSequence);
        }
    }

    public void setBeginColor(int i) {
        this.mSlideViewController.setBeginColor(i);
    }

    public void setEndColor(int i) {
        this.mSlideViewController.setEndColor(i);
    }

    public void setNoticeType(int i) {
        if (!this.mSlideViewController.isShowing()) {
            switch (i) {
                case 0:
                    setBeginColor(this.mContext.getResources().getColor(R.color.mc_slide_notice_failure_begin_color));
                    setEndColor(this.mContext.getResources().getColor(R.color.mc_slide_notice_failure_end_color));
                    return;
                default:
                    setBeginColor(this.mContext.getResources().getColor(R.color.mc_slide_notice_success_begin_color));
                    setEndColor(this.mContext.getResources().getColor(R.color.mc_slide_notice_success_end_color));
                    return;
            }
        }
    }

    public void setTop(int i) {
        this.mSlideViewController.setTop(i);
    }

    public void setLeft(int i) {
        this.mSlideViewController.setLeft(i);
    }

    public void resetSlideFrom() {
        this.mSlideViewController.resetTop();
    }

    public void setActionBarToTop(boolean z) {
        this.mSlideViewController.setBelowDefaultActionBar(z);
    }

    public void setBelowDefaultActionBar(boolean z) {
        this.mSlideViewController.setBelowDefaultActionBar(z);
    }

    public void setHeight(int i) {
        this.mSlideViewController.setNoticeHeight(i);
    }

    public void setWidth(int i) {
        this.mSlideViewController.setNoticeWidth(i);
    }

    public boolean isShowing() {
        return this.mSlideViewController.isShowing();
    }

    public void setNoTitleBarStyle(boolean z) {
        this.mSlideViewController.setIsOverlaidByStatusBar(z);
    }

    public void setIsOverlaidByStatusBar(boolean z) {
        this.mSlideViewController.setIsOverlaidByStatusBar(z);
    }

    public void setOnClickNoticeListener(OnClickNoticeListener onClickNoticeListener) {
        this.mSlideViewController.setOnClickNoticeListener(onClickNoticeListener);
    }

    public void setDuration(int i) {
        this.mDuration = i;
        if (this.mToast != null) {
            this.mToast.setDuration(i);
        }
    }

    public int getDuration() {
        return this.mDuration;
    }

    public void setAnchorView(View view) {
        this.mSlideViewController.setAnchor(view);
    }

    public void show() {
        if (this.mToast != null) {
            this.mToast.show();
        }
    }

    public void show(boolean z) {
    }

    public void slideToShow() {
        getService().enqueueNotice(this.mSlideViewController.getText(), this.mSlideViewController, this.mDuration);
    }

    public void slideToShow(boolean z) {
        if (z) {
            this.mSlideViewController.show();
        } else {
            show();
        }
    }

    public void finish() {
        cancel();
    }

    public void cancel() {
        if (this.mToast != null) {
            this.mToast.cancel();
        }
    }

    public void slideToCancel() {
        this.mSlideViewController.hide();
        getService().cancelNotice(this.mSlideViewController);
    }

    public void cancelWithoutAnim() {
        this.mSlideViewController.destroy();
    }

    public void showAndFinish(long j) {
    }

    public static SlideNotice makeNotice(Context context, CharSequence charSequence) {
        return makeNotice(context, charSequence, 1, 0);
    }

    public static SlideNotice makeNotice(Context context, CharSequence charSequence, int i) {
        return makeNotice(context, charSequence, 0, 0);
    }

    public static SlideNotice makeNotice(Context context, CharSequence charSequence, int i, int i2) {
        return new SlideNotice(context, charSequence, i2);
    }

    public void setGravity(int i) {
        this.mSlideViewController.setGravity(i);
    }

    public static SlideNotice makeSlideNotice(Context context, CharSequence charSequence) {
        return makeSlideNotice(context, charSequence, 1, 0);
    }

    public static SlideNotice makeSlideNotice(Context context, CharSequence charSequence, int i) {
        SlideNotice slideNotice = new SlideNotice(context);
        slideNotice.setText(charSequence);
        slideNotice.setNoticeType(i);
        return slideNotice;
    }

    public static SlideNotice makeSlideNotice(Context context, CharSequence charSequence, int i, int i2) {
        SlideNotice slideNotice = new SlideNotice(context);
        slideNotice.setText(charSequence);
        slideNotice.setNoticeType(i);
        slideNotice.mDuration = i2;
        return slideNotice;
    }

    public void setCustomView(View view) {
        this.mSlideViewController.setCustomView(view);
    }
}
