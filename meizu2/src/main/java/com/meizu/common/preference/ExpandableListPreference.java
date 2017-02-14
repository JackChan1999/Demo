package com.meizu.common.preference;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Handler;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.meizu.common.R;
import com.meizu.common.widget.PullRefreshLayout;
import java.lang.reflect.Field;

@SuppressLint({"NewApi"})
public class ExpandableListPreference extends Preference {
    private int ANIMATION_DURATION;
    private PreferenceAdapter mAdapter;
    private AnimHelper mAnimHelper;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private Handler mHandler;
    private ImageView mImageView;
    public boolean mIsExtand;
    private boolean mIsWaitingToChange;
    private LinearLayout mLinearLayout;
    private ListView mListView;
    private TextView mSummary;
    private String mValue;
    private boolean mValueSet;
    private Runnable performClick;

    @SuppressLint({"NewApi"})
    public class AnimHelper {
        public static final int COLLAPSE = 1;
        public static final int EXPAND = 0;
        private float mEndAlpha;
        private int mEndBottomMargin;
        private int mEndHeight;
        private boolean mIsAnimating = false;
        private LayoutParams mLayoutParams;
        private int mMarginTop = 0;
        private long mMillisTime;
        private View mRonateView;
        private float mStartAlpha;
        private int mStartBottomMargin;
        private View mSummaryView;
        private View mTarget;
        private int mType;

        class AnimInterpolator implements Interpolator {
            private AnimInterpolator() {
            }

            public float getInterpolation(float f) {
                return (float) (1.0d - Math.pow((double) (1.0f - f), 3.0d));
            }
        }

        public void init(View view, View view2, View view3, int i, long j) {
            float f;
            float f2 = 1.0f;
            this.mTarget = view;
            this.mSummaryView = view3;
            this.mRonateView = view2;
            this.mType = i;
            this.mMillisTime = j;
            this.mLayoutParams = (LayoutParams) this.mTarget.getLayoutParams();
            this.mEndHeight = this.mLayoutParams.height;
            if (this.mType == 0) {
                this.mLayoutParams.bottomMargin = -this.mEndHeight;
            } else {
                this.mLayoutParams.bottomMargin = 0;
            }
            this.mTarget.setVisibility(0);
            View view4 = this.mTarget;
            if (this.mType == 0) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            view4.setAlpha(f);
            this.mSummaryView.setVisibility(0);
            View view5 = this.mSummaryView;
            if (this.mType != 0) {
                f2 = 0.0f;
            }
            view5.setAlpha(f2);
        }

        public void animateHeightPrt() {
            if (this.mType == 0) {
                this.mStartBottomMargin = (-this.mEndHeight) + this.mMarginTop;
                this.mEndBottomMargin = 0;
                this.mStartAlpha = 0.0f;
                this.mEndAlpha = 1.0f;
            } else if (this.mType == 1) {
                this.mStartBottomMargin = 0;
                this.mEndBottomMargin = (-this.mEndHeight) + this.mMarginTop;
                this.mStartAlpha = 1.0f;
                this.mEndAlpha = 0.0f;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mEndAlpha, this.mStartAlpha});
            ofFloat.setDuration((long) ((int) (((double) this.mMillisTime) * 0.4d)));
            if (this.mType == 1) {
                ofFloat.setStartDelay((long) ((int) (((double) this.mMillisTime) * 0.6d)));
            }
            ofFloat.setInterpolator(getInterpolator());
            ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AnimHelper.this.mSummaryView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    if (AnimHelper.this.mType == 1) {
                        AnimHelper.this.mSummaryView.setVisibility(0);
                    } else {
                        AnimHelper.this.mSummaryView.setVisibility(4);
                    }
                }

                public void onAnimationCancel(Animator animator) {
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{this.mStartAlpha, this.mEndAlpha});
            ofFloat2.setDuration((long) ((int) (((double) this.mMillisTime) * 0.5d)));
            if (this.mType == 0) {
                ofFloat2.setStartDelay((long) ((int) (((double) this.mMillisTime) * 0.4d)));
            }
            ofFloat2.setInterpolator(getInterpolator());
            ofFloat2.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AnimHelper.this.mTarget.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            final ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mStartBottomMargin, this.mEndBottomMargin});
            ofInt.setInterpolator(getInterpolator());
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float abs = ((float) Math.abs(((Integer) valueAnimator.getAnimatedValue()).intValue() - AnimHelper.this.mStartBottomMargin)) / ((float) Math.abs(AnimHelper.this.mStartBottomMargin - AnimHelper.this.mEndBottomMargin));
                    if (AnimHelper.this.mType == 0) {
                        AnimHelper.this.mRonateView.setRotation(abs * BitmapDescriptorFactory.HUE_CYAN);
                    } else {
                        AnimHelper.this.mRonateView.setRotation((1.0f - abs) * BitmapDescriptorFactory.HUE_CYAN);
                    }
                    AnimHelper.this.mLayoutParams.bottomMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    if (!AnimHelper.this.mTarget.isInLayout()) {
                        AnimHelper.this.mTarget.requestLayout();
                    }
                }
            });
            ofInt.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                    AnimHelper.this.mTarget.setVisibility(0);
                    AnimHelper.this.mIsAnimating = true;
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    animator.removeAllListeners();
                    ofInt.removeAllUpdateListeners();
                    ofInt.removeAllListeners();
                    if (AnimHelper.this.mType == 1) {
                        AnimHelper.this.mTarget.setVisibility(4);
                    }
                    AnimHelper.this.mIsAnimating = false;
                }

                public void onAnimationCancel(Animator animator) {
                }
            });
            ofInt.setDuration(this.mMillisTime);
            animatorSet.playTogether(new Animator[]{ofInt, ofFloat2, ofFloat});
            animatorSet.start();
        }

        public void setMarginTop(int i) {
            this.mMarginTop = i;
        }

        private Interpolator getInterpolator() {
            if (VERSION.SDK_INT >= 21) {
                return new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
            }
            return new AnimInterpolator();
        }

        public boolean iSAnimating() {
            return this.mIsAnimating;
        }
    }

    class PreferenceAdapter extends BaseAdapter {
        private Context mContext;
        private CharSequence[] mData;
        private ListView mList;
        private int mSelectedPosition = -1;

        class Holder {
            public CheckedTextView checkedTextView;

            private Holder() {
            }
        }

        public PreferenceAdapter(Context context, CharSequence[] charSequenceArr) {
            this.mContext = context;
            this.mData = charSequenceArr;
        }

        public int getCount() {
            return this.mData.length;
        }

        public Object getItem(int i) {
            return this.mData[i];
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate;
            Holder holder;
            if (view == null) {
                Holder holder2 = new Holder();
                inflate = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.mc_expandable_preference_list_item, null);
                holder2.checkedTextView = (CheckedTextView) inflate;
                inflate.setLayoutParams(new AbsListView.LayoutParams(-1, this.mContext.getResources().getDimensionPixelOffset(R.dimen.mc_expandable_preference_list_item_height)));
                inflate.setTag(holder2);
                holder = holder2;
            } else {
                holder = (Holder) view.getTag();
                inflate = view;
            }
            holder.checkedTextView.setText(this.mData[i]);
            if (i == this.mSelectedPosition) {
                this.mList.setItemChecked(i, true);
            }
            return inflate;
        }

        public void setSelectedPosition(int i) {
            this.mSelectedPosition = i;
        }

        public void setTargetList(ListView listView) {
            this.mList = listView;
        }
    }

    public ExpandableListPreference(Context context) {
        this(context, null);
    }

    public ExpandableListPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ExpandableListPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsExtand = false;
        this.ANIMATION_DURATION = PullRefreshLayout.DEFAULT_DURATION;
        this.mHandler = new Handler();
        this.mIsWaitingToChange = false;
        this.performClick = new Runnable() {
            public void run() {
                ExpandableListPreference.this.performCollapseAnim();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableListPreference, 0, 0);
        this.mEntries = obtainStyledAttributes.getTextArray(R.styleable.ExpandableListPreference_mcEntries);
        this.mEntryValues = obtainStyledAttributes.getTextArray(R.styleable.ExpandableListPreference_mcEntryValues);
        obtainStyledAttributes.recycle();
        setLayoutResource(R.layout.mc_expandable_preference_layout);
        this.mAnimHelper = new AnimHelper();
        this.mAnimHelper.setMarginTop(-context.getResources().getDimensionPixelSize(R.dimen.mc_expandable_preference_inner_list_margin));
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.mListView = (ListView) view.findViewById(R.id.expand_listview);
        this.mAdapter = new PreferenceAdapter(getContext(), this.mEntries);
        this.mSummary = (TextView) view.findViewById(16908304);
        this.mImageView = (ImageView) view.findViewById(R.id.pull_icon);
        if (this.mEntries != null && this.mEntries.length > 0) {
            int valueIndex;
            if (getValueIndex() != -1) {
                valueIndex = getValueIndex();
            } else {
                valueIndex = 0;
            }
            setSummary(this.mEntries[valueIndex]);
            this.mSummary.setText(this.mEntries[valueIndex]);
            this.mAdapter.setSelectedPosition(valueIndex);
            this.mAdapter.notifyDataSetChanged();
            if (this.mIsExtand) {
                this.mSummary.setVisibility(4);
            } else {
                this.mSummary.setVisibility(0);
            }
            this.mListView.setAdapter(this.mAdapter);
            this.mAdapter.setTargetList(this.mListView);
            this.mListView.setChoiceMode(1);
            this.mListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    if (!ExpandableListPreference.this.iSAnimating() && !ExpandableListPreference.this.mIsWaitingToChange) {
                        ExpandableListPreference.this.mAdapter.setSelectedPosition(i);
                        ExpandableListPreference.this.mAdapter.notifyDataSetChanged();
                        if (ExpandableListPreference.this.mEntryValues != null) {
                            String charSequence = ExpandableListPreference.this.mEntryValues[i].toString();
                            ExpandableListPreference.this.mSummary.setText(ExpandableListPreference.this.mEntries[i]);
                            ExpandableListPreference.this.setListSummary(ExpandableListPreference.this.mEntries[i]);
                            if (ExpandableListPreference.this.callChangeListener(charSequence)) {
                                ExpandableListPreference.this.setValue(charSequence);
                            }
                        }
                        ExpandableListPreference.this.mHandler.postDelayed(ExpandableListPreference.this.performClick, 200);
                    }
                }
            });
        }
        this.mLinearLayout = (LinearLayout) view.findViewById(R.id.container);
        this.mLinearLayout.measure(0, 0);
        LayoutParams layoutParams = (LayoutParams) this.mLinearLayout.getLayoutParams();
        if (this.mEntries != null && this.mEntries.length > 0) {
            layoutParams.height = this.mLinearLayout.getMeasuredHeight() * this.mEntries.length;
        }
        if (this.mIsExtand) {
            this.mLinearLayout.setVisibility(0);
            this.mLinearLayout.setFocusable(false);
            return;
        }
        this.mLinearLayout.setVisibility(8);
    }

    protected void onClick() {
        if (!iSAnimating() && !this.mIsWaitingToChange) {
            if (this.mIsExtand) {
                this.mAnimHelper.init(this.mLinearLayout, this.mImageView, this.mSummary, 1, (long) this.ANIMATION_DURATION);
                this.mAnimHelper.animateHeightPrt();
                this.mIsExtand = false;
                return;
            }
            this.mAnimHelper.init(this.mLinearLayout, this.mImageView, this.mSummary, 0, (long) this.ANIMATION_DURATION);
            this.mAnimHelper.animateHeightPrt();
            this.mIsExtand = true;
        }
    }

    public void performCollapseAnim() {
        if (this.mIsExtand) {
            this.mAnimHelper.init(this.mLinearLayout, this.mImageView, this.mSummary, 1, (long) this.ANIMATION_DURATION);
            this.mAnimHelper.animateHeightPrt();
            this.mIsExtand = false;
        }
    }

    protected void onSetInitialValue(boolean z, Object obj) {
        if (z) {
            obj = getPersistedString(this.mValue);
        } else {
            String str = (String) obj;
        }
        setValue(obj);
    }

    public void setValue(String str) {
        boolean z = !TextUtils.equals(this.mValue, str);
        if (z || !this.mValueSet) {
            this.mValue = str;
            this.mValueSet = true;
            persistString(str);
            if (z) {
                notifyChanged();
            }
        }
    }

    public String getValue() {
        return this.mValue;
    }

    public int findIndexOfValue(String str) {
        if (!(str == null || this.mEntryValues == null)) {
            for (int length = this.mEntryValues.length - 1; length >= 0; length--) {
                if (this.mEntryValues[length].equals(str)) {
                    return length;
                }
            }
        }
        return -1;
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int i) {
        return typedArray.getString(i);
    }

    private int getValueIndex() {
        return findIndexOfValue(this.mValue);
    }

    public boolean iSAnimating() {
        if (this.mAnimHelper != null) {
            return this.mAnimHelper.iSAnimating();
        }
        return false;
    }

    private void setListSummary(CharSequence charSequence) {
        try {
            Field declaredField = Preference.class.getDeclaredField("mSummary");
            declaredField.setAccessible(true);
            declaredField.set(this, charSequence);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
        }
    }

    public CharSequence[] getEntries() {
        return this.mEntries;
    }

    public void setEntries(CharSequence[] charSequenceArr) {
        this.mEntries = charSequenceArr;
    }

    public CharSequence[] getEntryValues() {
        return this.mEntryValues;
    }

    public void setEntryValues(CharSequence[] charSequenceArr) {
        this.mEntryValues = charSequenceArr;
    }

    public CharSequence getEntry() {
        int valueIndex = getValueIndex();
        return (valueIndex < 0 || this.mEntries == null) ? null : this.mEntries[valueIndex];
    }
}
