package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meizu.common.R;
import com.meizu.common.drawble.BlurDrawable;

public class StretchSearchView extends RelativeLayout {
    private static final int SIZE_DEFAULT = 0;
    public static final int STATE_ERROR = -1;
    public static final int STATE_READY = 0;
    public static final int STATE_SHORTENED = 4;
    public static final int STATE_SHORTENING = 3;
    public static final int STATE_STRETCHED = 2;
    public static final int STATE_STRETCHING = 1;
    public static final String TAG = "StretchSearchView";
    public static final int TYPE_CUSTOM = 0;
    public static final int TYPE_MIDDLE_TO_LEFT = 3;
    public static final int TYPE_MIDDLE_TO_LEFT_TEXTVIEW = 4;
    public static final int TYPE_RIGHT_TO_LEFT = 1;
    public static final int TYPE_RIGHT_TO_LEFT_TEXTVIEW = 2;
    private boolean mAlignRightWhenAnim;
    private int mAnimationState;
    private TextView mButton;
    private int mButtonColor;
    private Context mContext;
    private float mCustomAnimValueFrom;
    private float mCustomAnimValueTo;
    private boolean mHasBtn;
    private boolean mHasVoiceIcon;
    private ImageView mImgSearch;
    private ImageView mImgSearchClear;
    private float mInputClearAlphaFrom;
    private float mInputClearAlphaTo;
    private SearchEditText mInputText;
    private float mInputTextAlphaFrom;
    private float mInputTextAlphaTo;
    private int mLayoutMarginLeftAdjust;
    private int mLayoutMarginRightAdjust;
    private int mLayoutPaddingLeft;
    private int mLayoutPaddingRight;
    private RelativeLayout mMainLayout;
    private boolean mPlayInputTextAlhpaAnim;
    private boolean mPlayMoveXAnim;
    private boolean mPlaySearchImgScaleAnim;
    private boolean mPlaySearchclearAlphaAnim;
    private boolean mPlayStretchOnPreDraw;
    private boolean mPlayStretchWidthAnim;
    private float mScaleFrom;
    private float mScaleTo;
    private RelativeLayout mSearchLayout;
    private float mSearchLayoutInitAlpha;
    private String mSearchTextHint;
    private View mSearchView;
    private ShortenAnimationListener mShortenAnimationListener;
    private int mShortenDuration;
    private StretchAnimationListener mStretchAnimationListener;
    private int mStretchDuration;
    private int mStretchTpye;
    private int mStretchWidthFrom;
    private int mStretchWidthTo;
    private int mStretchXfrom;
    private int mStretchXto;
    private String mTextViewContent;
    private boolean mUseSysInterpolater;
    private ImageView mVoiceIcon;

    public interface ShortenAnimationListener {
        void onShortenAnimationEnd(View view);

        void onShortenAnimationStart(View view);

        void onShortenAnimationUpdate(View view, float f);
    }

    public interface StretchAnimationListener {
        void onStetchAnimationEnd(View view);

        void onStetchAnimationStart(View view);

        void onStetchAnimationUpdate(View view, float f);
    }

    public StretchSearchView(Context context) {
        this(context, null);
    }

    public StretchSearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.MeizuCommon_StretchSearchViewStyle);
    }

    public StretchSearchView(Context context, int i) {
        this(context);
        this.mStretchTpye = i;
    }

    public StretchSearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mStretchXfrom = 0;
        mStretchXto = 0;
        mStretchWidthFrom = 0;
        mStretchWidthTo = 0;
        mLayoutMarginLeftAdjust = 0;
        mLayoutPaddingLeft = 0;
        mLayoutPaddingRight = 0;
        mLayoutMarginRightAdjust = 0;
        mPlayStretchOnPreDraw = false;
        mHasBtn = false;
        mHasVoiceIcon = false;
        mPlayStretchWidthAnim = true;
        mPlayMoveXAnim = true;
        mPlaySearchImgScaleAnim = true;
        mPlaySearchclearAlphaAnim = true;
        mPlayInputTextAlhpaAnim = true;
        mStretchTpye = 1;
        mUseSysInterpolater = false;
        mAlignRightWhenAnim = false;
        mStretchDuration = 320;
        mShortenDuration = this.mStretchDuration;
        mInputClearAlphaFrom = 0.0f;
        mInputClearAlphaTo = 1.0f;
        mInputTextAlphaFrom = 0.0f;
        mInputTextAlphaTo = 1.0f;
        mCustomAnimValueFrom = 0.0f;
        mCustomAnimValueTo = 1.0f;
        mScaleFrom = 1.0f;
        mScaleTo = BlurDrawable.DEFAULT_BLUR_LEVEL;
        mSearchLayoutInitAlpha = 0.0f;
        mButtonColor = -1;
        mSearchTextHint = "搜索";
        mStretchAnimationListener = null;
        mShortenAnimationListener = null;
        mAnimationState = -1;
        mContext = context;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R
                .styleable.StretchSearchView, i, 0);
        mStretchTpye = obtainStyledAttributes.getInteger(R.styleable
                .StretchSearchView_mcStretchTpye, this.mStretchTpye);
        mHasVoiceIcon = obtainStyledAttributes.getBoolean(R.styleable
                .StretchSearchView_mcHasVoiceIcon, this.mHasVoiceIcon);
        mPlayStretchOnPreDraw = obtainStyledAttributes.getBoolean(R.styleable
                .StretchSearchView_mcPlayStretchOnPreDraw, this.mPlayStretchOnPreDraw);
        mAlignRightWhenAnim = obtainStyledAttributes.getBoolean(R.styleable
                .StretchSearchView_mcAlignRightWhenAnim, this.mAlignRightWhenAnim);
        mUseSysInterpolater = obtainStyledAttributes.getBoolean(R.styleable
                .StretchSearchView_mcUseSysInterpolater, this.mUseSysInterpolater);
        mStretchDuration = obtainStyledAttributes.getInteger(R.styleable
                .StretchSearchView_mcStretchDuration, this.mStretchDuration);
        mShortenDuration = obtainStyledAttributes.getInteger(R.styleable
                .StretchSearchView_mcShortenDuration, this.mShortenDuration);
        mSearchTextHint = obtainStyledAttributes.getString(R.styleable
                .StretchSearchView_mcSearchTextHint);
        mTextViewContent = obtainStyledAttributes.getString(R.styleable
                .StretchSearchView_mcTextViewContent);
        mSearchLayoutInitAlpha = obtainStyledAttributes.getFloat(R.styleable
                .StretchSearchView_mcSearchLayoutInitAlpha, this.mSearchLayoutInitAlpha);
        mButtonColor = obtainStyledAttributes.getColor(R.styleable
                .StretchSearchView_mcTextViewColor, -1);
        mLayoutMarginLeftAdjust = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcLayoutMarginLeftAdjust, (float) this.mLayoutMarginLeftAdjust);
        mLayoutMarginRightAdjust = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcLayoutMarginRightAdjust, (float) this
                .mLayoutMarginRightAdjust);
        mLayoutPaddingLeft = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcLayoutPaddingLeft, (float) this.mLayoutPaddingLeft);
        mLayoutPaddingRight = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcLayoutPaddingRight, (float) this.mLayoutPaddingRight);
        mStretchWidthFrom = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcStretchWidthFrom, 0.0f);
        mStretchWidthTo = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcStretchWidthTo, 0.0f);
        mStretchXfrom = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcStretchXfrom, 0.0f);
        mStretchXto = (int) obtainStyledAttributes.getDimension(R.styleable
                .StretchSearchView_mcStretchXto, 0.0f);
        obtainStyledAttributes.recycle();
        initAll();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void initView(Context context) {
        String str = "";
        this.mSearchView = null;
        if (this.mStretchTpye == 0) {
            this.mSearchView = View.inflate(context, R.layout.mc_stretch_search_layout_ext, this);
            str = "R.layout.mc_move_search_layout";
        } else if (3 == this.mStretchTpye) {
            this.mSearchView = View.inflate(context, R.layout.mc_move_search_layout, this);
            str = "R.layout.mc_move_search_layout";
        } else if (2 == this.mStretchTpye) {
            this.mSearchView = View.inflate(context, R.layout.mc_stretch_search_layout_ext, this);
            str = "R.layout.mc_stretch_search_layout_ext";
        } else {
            this.mSearchView = View.inflate(context, R.layout.mc_stretch_search_layout, this);
            str = "R.layout.mc_stretch_search_layout";
        }
        if (this.mSearchView == null) {
            throw new IllegalArgumentException("StretchSearchView cannot inflate " + str + "!");
        }
        this.mMainLayout = (RelativeLayout) this.mSearchView.findViewById(R.id
                .mc_stretch_search_layout);
        this.mSearchLayout = (RelativeLayout) this.mSearchView.findViewById(R.id.mc_search_layout);
        this.mVoiceIcon = (ImageView) this.mSearchView.findViewById(R.id.mc_voice_icon);
        this.mImgSearch = (ImageView) this.mSearchView.findViewById(R.id.mc_search_icon);
        this.mImgSearchClear = (ImageView) this.mSearchView.findViewById(R.id
                .mc_search_icon_input_clear);
        this.mInputText = (SearchEditText) this.mSearchView.findViewById(R.id.mc_search_edit);
        this.mInputText.setAlpha(this.mSearchLayoutInitAlpha);
        this.mInputText.setHint(this.mSearchTextHint);
        if (this.mHasBtn) {
            this.mButton = (TextView) this.mSearchView.findViewById(R.id.mc_search_textview);
            this.mButton.setTextColor(this.mButtonColor);
            this.mButton.setText(this.mTextViewContent);
            this.mButton.setAlpha(0.0f);
        }
        LayoutParams layoutParams = new LayoutParams(-2, -1);
        layoutParams.rightMargin = this.mLayoutMarginRightAdjust;
        this.mSearchLayout.setLayoutParams(layoutParams);
        this.mMainLayout.setPadding(this.mLayoutPaddingLeft, this.mMainLayout.getTop(), this
                .mLayoutPaddingRight, this.mMainLayout.getBottom());
        this.mMainLayout.requestLayout();
    }

    protected void initAll() {
        initStretchType();
        initView(this.mContext);
        initViewState();
        initListener();
    }

    private void initMeasure() {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int makeMeasureSpec2 = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        this.mMainLayout.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mSearchLayout.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mImgSearch.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mInputText.measure(makeMeasureSpec, makeMeasureSpec2);
    }

    protected void initStretchType() {
        boolean z;
        boolean z2 = false;
        if (2 == this.mStretchTpye || 4 == this.mStretchTpye || this.mStretchTpye == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mHasBtn = z;
        if (true != this.mAlignRightWhenAnim) {
            z2 = true;
        }
        this.mPlayMoveXAnim = z2;
    }

    protected void initListener() {
        this.mImgSearch.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StretchSearchView.this.startStretch();
            }
        });
        this.mImgSearchClear.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StretchSearchView.this.mInputText.setText("");
            }
        });
        this.mInputText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable editable) {
                String obj = StretchSearchView.this.mInputText.getText().toString();
                if (obj == null || obj.isEmpty()) {
                    StretchSearchView.this.mImgSearchClear.setVisibility(View.GONE);
                    if (StretchSearchView.this.mAnimationState == 2 && StretchSearchView.this
                            .mHasVoiceIcon) {
                        StretchSearchView.this.mVoiceIcon.setVisibility(View.VISIBLE);
                    }
                    StretchSearchView.this.showIme(true);
                    return;
                }
                if (StretchSearchView.this.mHasVoiceIcon) {
                    StretchSearchView.this.mVoiceIcon.setVisibility(View.GONE);
                }
                StretchSearchView.this.mImgSearchClear.setVisibility(View.VISIBLE);
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        if (4 == this.mStretchTpye || 3 == this.mStretchTpye) {
            this.mInputText.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    StretchSearchView.this.startStretch();
                }
            });
        }
        addPreDrawListener();
    }

    private void addGlobalPreDrawListener() {
        final ViewTreeObserver viewTreeObserver = this.mMainLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                viewTreeObserver.removeGlobalOnLayoutListener(this);
                StretchSearchView.this.onInitLayout();
            }
        });
    }

    private void addPreDrawListener() {
        this.mMainLayout.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() {
                StretchSearchView.this.mMainLayout.getViewTreeObserver().removeOnPreDrawListener
                        (this);
                StretchSearchView.this.onInitLayout();
                if (StretchSearchView.this.mPlayStretchOnPreDraw) {
                    StretchSearchView.this.startStretch();
                }
                return true;
            }
        });
    }

    protected void onInitLayout() {
        if (this.mStretchTpye != 0) {
            calcX();
            calcWidth();
        }
        if (3 == this.mStretchTpye || 4 == this.mStretchTpye) {
            recalcFromMiddle();
        }
        Log.i(TAG, "Stretch width from " + this.mStretchWidthFrom + " to " + this.mStretchWidthTo
                + ", move X from " + this.mStretchXfrom + " to " + this.mStretchXto + " !");
    }

    protected void calcWidth() {
        this.mStretchWidthFrom = this.mSearchLayout.getMeasuredWidth();
        this.mStretchWidthTo = getMaxStretchWidth();
    }

    protected void calcX() {
        this.mStretchXfrom = (int) this.mSearchLayout.getX();
        this.mStretchXto = getMinMoveX();
    }

    protected void recalcFromMiddle() {
        int i = this.mStretchXfrom;
        i = this.mImgSearch.getMeasuredWidth() + (((int) this.mInputText.getPaint().measureText
                (this.mInputText.getHint().toString())) / 2);
        this.mStretchWidthFrom = (getMaxStretchWidth() / 2) + i;
        this.mStretchXfrom = (this.mMainLayout.getMeasuredWidth() / 2) - i;
        this.mStretchXto = getMinMoveX();
        this.mSearchLayout.setX((float) this.mStretchXfrom);
        Log.i(TAG, "Reset stretch layout, search icon location X to layout right:  " + this
                .mStretchWidthFrom + ",search icon location X: " + this.mStretchXfrom + " !");
        ImageView imageView = (ImageView) this.mMainLayout.findViewById(R.id
                .mc_stretch_search_layout_1);
        if (imageView != null) {
            imageView.setX((float) (this.mStretchXto - this.mImgSearch.getPaddingLeft()));
        }
    }

    protected int getMaxStretchWidth() {
        int measuredWidth = this.mMainLayout.getMeasuredWidth();
        int paddingLeft = this.mMainLayout.getPaddingLeft();
        int paddingRight = this.mMainLayout.getPaddingRight();
        if (this.mHasBtn) {
            return (measuredWidth - this.mButton.getLayoutParams().width) - paddingLeft;
        }
        return (measuredWidth - paddingLeft) - paddingRight;
    }

    protected int getMinMoveX() {
        return (this.mMainLayout.getPaddingLeft() + ((int) this.mMainLayout.getX())) + this
                .mLayoutMarginLeftAdjust;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void initViewState() {
        if (3 == mStretchTpye || 4 == mStretchTpye) {
            mSearchView.setVisibility(View.VISIBLE);
            mInputText.setVisibility(View.VISIBLE);
            mInputText.setBackground(null);
            mInputTextAlphaFrom = 0.8f;
        } else {
            this.mSearchView.setVisibility(View.VISIBLE);
            this.mInputText.setVisibility(View.GONE);
        }
        this.mAnimationState = 0;
    }

    protected void beforeStretchViewState() {
       mSearchView.requestLayout();
       mSearchView.setVisibility(View.VISIBLE);
       mInputText.setVisibility(View.VISIBLE);
       mInputText.setText("");
        if (mHasBtn) {
            mButton.setVisibility(View.VISIBLE);
            mButton.setAlpha(0.0f);
        }
    }

    protected void afterStretchViewState() {
       mSearchView.requestLayout();
       mInputText.showIme(true);
        if (mHasVoiceIcon) {
            mVoiceIcon.setVisibility(View.VISIBLE);
        }
    }

    protected void beforeShortenViewState() {
       mSearchView.requestLayout();
       mInputText.showIme(false);
        if (mHasVoiceIcon) {
            mVoiceIcon.setVisibility(View.GONE);
        }
    }

    protected void afterShortenViewState() {
       mSearchView.requestLayout();
       mInputText.setVisibility(View.GONE);
       mSearchView.setVisibility(View.VISIBLE);
        if (mHasBtn) {
            mButton.setVisibility(View.INVISIBLE);
        }
    }

    public void startStretchOnPreDraw() {
        stretchAmin();
    }

    public void startStretch() {
        stretchAmin();
    }

    public void startShorten() {
        shortenAmin();
    }

    private void stretchAmin() {
        if (this.mAnimationState == 0 || this.mAnimationState == 4) {
            this.mAnimationState = 1;
            beforeStretchViewState();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration((long) this.mStretchDuration);

            ObjectAnimator anim_x = ObjectAnimator.ofFloat(this.mSearchLayout, "x", mStretchXto);
            anim_x.setDuration((long) this.mStretchDuration);

            ObjectAnimator anim_width = ObjectAnimator.ofFloat(this.mSearchLayout, "width", mStretchWidthTo);
            anim_width.setDuration((long) this.mStretchDuration);
            anim_width.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    StretchSearchView.this.mSearchLayout.setLayoutParams(new LayoutParams((int) (
                            (Float) valueAnimator.getAnimatedValue()).floatValue(),
                            StretchSearchView.this.mSearchLayout.getHeight()));
                }
            });

            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(mSearchLayout,
                    "StretchSearchViewAnimValue", mCustomAnimValueFrom, mCustomAnimValueTo);
            ofFloat3.setDuration((long) this.mStretchDuration);
            ofFloat3.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    if (StretchSearchView.this.mStretchAnimationListener != null) {
                        StretchSearchView.this.mStretchAnimationListener.onStetchAnimationUpdate
                                (StretchSearchView.this.mSearchView, floatValue);
                    }
                }
            });

            animatorSet.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    if (StretchSearchView.this.mStretchAnimationListener != null) {
                        StretchSearchView.this.mStretchAnimationListener.onStetchAnimationStart
                                (StretchSearchView.this.mSearchView);
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    StretchSearchView.this.afterStretchViewState();
                    StretchSearchView.this.mAnimationState = 2;
                    if (StretchSearchView.this.mStretchAnimationListener != null) {
                        StretchSearchView.this.mStretchAnimationListener.onStetchAnimationEnd
                                (StretchSearchView.this.mSearchView);
                    }
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            ObjectAnimator anim_clear = ObjectAnimator.ofFloat(mImgSearchClear, "alpha",
                    mInputClearAlphaFrom, mInputClearAlphaTo);
            anim_clear.setDuration((long) mStretchDuration);

            ObjectAnimator alpha_input_text = ObjectAnimator.ofFloat(this.mInputText, "alpha",
                    mInputTextAlphaFrom, mInputTextAlphaTo);
            alpha_input_text.setDuration((long) mStretchDuration);

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mImgSearch, "scaleX", mScaleFrom, mScaleTo);

            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImgSearch, "scaleY", mScaleFrom, mScaleTo);

            scaleX.setDuration((long) this.mStretchDuration);
            scaleY.setDuration((long) this.mStretchDuration);
            if (this.mUseSysInterpolater) {
                anim_x.setInterpolator(getMovingInterpolater());
                anim_width.setInterpolator(getStretchInterpolater());
                scaleX.setInterpolator(getScaleInterpolater());
                scaleY.setInterpolator(getScaleInterpolater());
            }
            animatorSet.play(ofFloat3);
            if (this.mPlayMoveXAnim) {
                animatorSet.play(ofFloat3).with(anim_x);
            }
            if (this.mPlaySearchclearAlphaAnim) {
                animatorSet.play(ofFloat3).with(anim_clear);
            }
            if (this.mPlayInputTextAlhpaAnim) {
                animatorSet.play(ofFloat3).with(alpha_input_text);
            }
            if (this.mPlayStretchWidthAnim) {
                animatorSet.play(ofFloat3).with(anim_width);
            }
            if (this.mPlaySearchImgScaleAnim) {
                animatorSet.play(ofFloat3).with(scaleX).with(scaleY);
            }
            if (this.mHasBtn) {
                anim_x = ObjectAnimator.ofFloat(this.mButton, "alpha", 0.0f, 1.0f);
                anim_x.setDuration((long) ((this.mStretchDuration * 2) / 3));
                AnimatorSet animatorSet2 = new AnimatorSet();
                animatorSet2.setDuration((long) ((this.mStretchDuration * 2) / 3));
                animatorSet2.play(anim_x).after((long) (this.mStretchDuration / 3));
                animatorSet2.start();
            }
            animatorSet.start();
        }
    }

    private void shortenAmin() {
        if (this.mAnimationState == 2) {
            this.mAnimationState = 3;
            beforeShortenViewState();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration((long) this.mShortenDuration);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mSearchLayout, "x", new float[]{
                    (float) this.mStretchXto, (float) this.mStretchXfrom});
            ofFloat.setDuration((long) this.mShortenDuration);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mSearchLayout, "width", new float[]{
                    (float) this.mStretchWidthTo, (float) this.mStretchWidthFrom});
            ofFloat2.setDuration((long) this.mShortenDuration);
            ofFloat2.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    StretchSearchView.this.mSearchLayout.setLayoutParams(new LayoutParams((int) (
                            (Float) valueAnimator.getAnimatedValue()).floatValue(),
                            StretchSearchView.this.mSearchLayout.getHeight()));
                }
            });
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mSearchLayout,
                    "StretchSearchViewAnimValue", new float[]{this.mCustomAnimValueTo, this
                            .mCustomAnimValueFrom});
            ofFloat3.setDuration((long) this.mShortenDuration);
            ofFloat3.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    if (StretchSearchView.this.mShortenAnimationListener != null) {
                        StretchSearchView.this.mShortenAnimationListener.onShortenAnimationUpdate
                                (StretchSearchView.this.mSearchView, floatValue);
                    }
                }
            });
            animatorSet.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    if (StretchSearchView.this.mShortenAnimationListener != null) {
                        StretchSearchView.this.mShortenAnimationListener.onShortenAnimationStart
                                (StretchSearchView.this.mSearchView);
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    StretchSearchView.this.afterShortenViewState();
                    StretchSearchView.this.mAnimationState = 4;
                    if (StretchSearchView.this.mShortenAnimationListener != null) {
                        StretchSearchView.this.mShortenAnimationListener.onShortenAnimationEnd
                                (StretchSearchView.this.mSearchView);
                    }
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(this.mInputText, "alpha", mInputTextAlphaTo, mInputTextAlphaFrom);
            ofFloat4.setDuration((long) ((this.mShortenDuration * 4) / 5));

            ObjectAnimator alpha = ObjectAnimator.ofFloat(this.mImgSearchClear, "alpha", mInputClearAlphaTo, mInputClearAlphaFrom);
            alpha.setDuration((long) ((this.mShortenDuration * 4) / 5));

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this.mImgSearch, "scaleX", mScaleTo, mScaleFrom);
            ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(this.mImgSearch, "scaleY", mScaleTo, mScaleFrom);

            if (this.mUseSysInterpolater) {
                ofFloat.setInterpolator(getMovingInterpolater());
                ofFloat2.setInterpolator(getStretchInterpolater());
                scaleX.setInterpolator(getScaleInterpolater());
                ofFloat7.setInterpolator(getScaleInterpolater());
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.setDuration((long) ((this.mShortenDuration * 4) / 5));
            if (this.mPlaySearchclearAlphaAnim) {
                animatorSet2.play(alpha);
            }
            if (this.mPlayInputTextAlhpaAnim) {
                animatorSet2.play(ofFloat4);
            }
            animatorSet2.start();
            animatorSet.play(ofFloat3);
            if (this.mPlayMoveXAnim) {
                animatorSet.play(ofFloat3).with(ofFloat);
            }
            if (this.mPlayStretchWidthAnim) {
                animatorSet.play(ofFloat3).with(ofFloat2);
            }
            if (this.mPlaySearchImgScaleAnim) {
                animatorSet.play(ofFloat3).with(scaleX).with(ofFloat7);
            }
            if (this.mHasBtn) {
                ofFloat = ObjectAnimator.ofFloat(this.mButton, "alpha", new float[]{1.0f, 0.0f});
                ofFloat.setDuration((long) ((this.mShortenDuration * 2) / 3));
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.setDuration((long) ((this.mShortenDuration * 2) / 3));
                animatorSet3.play(ofFloat);
                animatorSet3.start();
            }
            animatorSet.start();
        }
    }

    public void showIme(boolean z) {
        this.mInputText.showIme(z);
    }

    public void setAutoPlayStretchOnPreDraw(boolean z) {
        this.mPlayStretchOnPreDraw = z;
    }

    public boolean isAutoPlayStretchOnPreDraw() {
        return this.mPlayStretchOnPreDraw;
    }

    public void setHaveVoiceIcon(boolean z) {
        this.mHasVoiceIcon = z;
    }

    public boolean isHaveVoiceIcon() {
        return this.mHasVoiceIcon;
    }

    public boolean isPlayStretchWidthAnim() {
        return this.mPlayStretchWidthAnim;
    }

    public void setPlayStretchWidthAnim(boolean z) {
        this.mPlayStretchWidthAnim = z;
    }

    public boolean isPlayMoveXAnim() {
        return this.mPlayMoveXAnim;
    }

    public void setPlayMoveXAnim(boolean z) {
        this.mPlayMoveXAnim = z;
    }

    public boolean isPlaySearchImgScaleAnim() {
        return this.mPlaySearchImgScaleAnim;
    }

    public void setPlaySearchImgScaleAnim(boolean z) {
        this.mPlaySearchImgScaleAnim = z;
    }

    public boolean isPlaySearchclearAlphaAnim() {
        return this.mPlaySearchclearAlphaAnim;
    }

    public void setPlaySearchclearAlphaAnim(boolean z) {
        this.mPlaySearchclearAlphaAnim = z;
    }

    public boolean isPlayInputTextAlhpaAnim() {
        return this.mPlayInputTextAlhpaAnim;
    }

    public void setPlayInputTextAlhpaAnim(boolean z) {
        this.mPlayInputTextAlhpaAnim = z;
    }

    public void setCustomAnimValueFrom(float f) {
        this.mCustomAnimValueFrom = f;
    }

    public float getCustomAnimValueFrom() {
        return this.mCustomAnimValueFrom;
    }

    public void setCustomAnimValueTo(float f) {
        this.mCustomAnimValueTo = f;
    }

    public float getCustomAnimValueTo() {
        return this.mInputClearAlphaTo;
    }

    public void setInputTextAlphaFrom(float f) {
        this.mInputTextAlphaFrom = f;
    }

    public float getInputTextAlphaFrom() {
        return this.mInputTextAlphaFrom;
    }

    public void setInputTextAlphaTo(float f) {
        this.mInputTextAlphaTo = f;
    }

    public float getInputTextAlphaTo() {
        return this.mInputClearAlphaTo;
    }

    public void setInputClearAlphaFrom(float f) {
        this.mInputTextAlphaFrom = f;
    }

    public float getInputClearAlphaFrom() {
        return this.mInputTextAlphaFrom;
    }

    public void setInputClearAlphaTo(float f) {
        this.mInputTextAlphaTo = f;
    }

    public int getInputClearAlphaTo() {
        return this.mStretchWidthTo;
    }

    public void setVoiceIconListener(OnClickListener onClickListener) {
        if (this.mHasVoiceIcon) {
            this.mVoiceIcon.setOnClickListener(onClickListener);
        }
    }

    public void setBtnListener(OnClickListener onClickListener) {
        if (this.mButton != null) {
            this.mButton.setOnClickListener(onClickListener);
        }
    }

    public void setEditTextListener(OnClickListener onClickListener) {
        this.mInputText.setOnClickListener(onClickListener);
    }

    public void addEditTextChangedListener(TextWatcher textWatcher) {
        this.mInputText.addTextChangedListener(textWatcher);
    }

    public int getStretchWidthFrom() {
        return this.mStretchWidthFrom;
    }

    public void setStretchWidthFrom(int i) {
        this.mStretchWidthFrom = i;
    }

    public int getStretchWidthTo() {
        return this.mStretchWidthTo;
    }

    public void setStretchWidthTo(int i) {
        this.mStretchWidthTo = i;
    }

    public int getStretchXfrom() {
        return this.mStretchXfrom;
    }

    public void setStretchXfrom(int i) {
        this.mStretchXfrom = i;
    }

    public int getStretchXto() {
        return this.mStretchXto;
    }

    public void setStretchXto(int i) {
        this.mStretchXto = i;
    }

    public float getScaleFrom() {
        return this.mScaleFrom;
    }

    public void setScaleFrom(float f) {
        this.mScaleFrom = f;
    }

    public float getScaleTo() {
        return this.mScaleTo;
    }

    public void setScaleTo(float f) {
        this.mScaleTo = f;
    }

    public int getLayoutMarginLeftAdjust() {
        return this.mLayoutMarginLeftAdjust;
    }

    public void setLayoutMarginLeftAdjust(int i) {
        this.mLayoutMarginLeftAdjust = i;
    }

    public int getLayoutMarginRightAdjust() {
        return this.mLayoutMarginRightAdjust;
    }

    public void setLayoutMarginRightAdjust(int i) {
        this.mLayoutMarginRightAdjust = i;
    }

    public String getSearchText() {
        return this.mInputText.getText().toString();
    }

    public void setSearchText(String str) {
        this.mInputText.setText(str);
    }

    public String getBtnText() {
        if (this.mHasBtn) {
            return this.mButton.getText().toString();
        }
        return null;
    }

    public void setBtnText(String str) {
        if (this.mHasBtn) {
            this.mButton.setText(str);
        }
    }

    public boolean getUseInterpolater() {
        return this.mUseSysInterpolater;
    }

    public void setUseInterpolater(boolean z) {
        this.mUseSysInterpolater = z;
    }

    public boolean getIsAlignRight() {
        return this.mAlignRightWhenAnim;
    }

    public void setIsAlignRigh(boolean z) {
        this.mAlignRightWhenAnim = z;
    }

    private Interpolator getMovingInterpolater() {
        Interpolator decelerateInterpolator = new DecelerateInterpolator();
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.3333f, 0.0f, 0.1f, 1.0f);
        }
        return decelerateInterpolator;
    }

    private Interpolator getScaleInterpolater() {
        Interpolator decelerateInterpolator = new DecelerateInterpolator();
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.3333f, 0.0f, 0.0f, 1.0f);
        }
        return decelerateInterpolator;
    }

    private Interpolator getStretchInterpolater() {
        Interpolator decelerateInterpolator = new DecelerateInterpolator();
        if (VERSION.SDK_INT >= 21) {
            return new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        }
        return decelerateInterpolator;
    }

    public void setStretchAnimDuration(int i) {
        this.mStretchDuration = i;
    }

    public int getStretchAnimDuration() {
        return this.mStretchDuration;
    }

    public void setShortenAnimDuration(int i) {
        this.mShortenDuration = i;
    }

    public int getShortenAnimDuration() {
        return this.mShortenDuration;
    }

    public int getAnimationState() {
        return this.mAnimationState;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = context.getResources().getDisplayMetrics();
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        return i;
    }

    public void setOnStretchAnimationListener(StretchAnimationListener stretchAnimationListener) {
        this.mStretchAnimationListener = stretchAnimationListener;
    }

    public void setOnShortenAnimationListener(ShortenAnimationListener shortenAnimationListener) {
        this.mShortenAnimationListener = shortenAnimationListener;
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(StretchSearchView.class.getName());
    }
}
