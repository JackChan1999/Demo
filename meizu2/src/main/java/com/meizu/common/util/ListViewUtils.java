package com.meizu.common.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ListViewUtils {
    private static final int DEFAULT_DURATION = 300;
    private static final int DELAY_ANIMATION = 33;
    private Interpolator mAlInterpolator;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap();
    private Interpolator mTrInterpolator;
    private Interpolator mUpInterpolator;

    public interface OnListViewFadeListener {
        void onEndListViewFadedOut();

        void onEndResetListView();

        void onStartListViewFadeOut();
    }

    public ListViewUtils() {
        if (VERSION.SDK_INT >= 21) {
            this.mTrInterpolator = new PathInterpolator(FastBlurParameters.DEFAULT_LEVEL, 0.0f, FastBlurParameters.DEFAULT_LEVEL, 1.0f);
            this.mAlInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
            this.mUpInterpolator = new PathInterpolator(0.33f, 0.0f, FastBlurParameters.DEFAULT_SCALE, 1.0f);
            return;
        }
        this.mTrInterpolator = new LinearInterpolator();
        this.mAlInterpolator = new LinearInterpolator();
        this.mUpInterpolator = new LinearInterpolator();
    }

    public void fadeOutItemView(ListView listView, int i, int i2, OnListViewFadeListener onListViewFadeListener, BaseAdapter baseAdapter) {
        int i3 = 0;
        final int firstVisiblePosition = listView.getFirstVisiblePosition();
        Collection arrayList = new ArrayList();
        for (int i4 = i; i4 <= i2; i4++) {
            View childAt = listView.getChildAt(i4 - firstVisiblePosition);
            if (childAt != null) {
                Keyframe ofFloat = Keyframe.ofFloat(0.0f, 1.0f);
                Keyframe.ofFloat(1.0f, 0.0f).setInterpolator(this.mAlInterpolator);
                Keyframe ofFloat2 = Keyframe.ofFloat(0.0f, 0.0f);
                Keyframe.ofFloat(1.0f, (float) (-childAt.getWidth())).setInterpolator(this.mTrInterpolator);
                PropertyValuesHolder ofKeyframe = PropertyValuesHolder.ofKeyframe("alpha", new Keyframe[]{ofFloat, r5});
                PropertyValuesHolder ofKeyframe2 = PropertyValuesHolder.ofKeyframe("translationX", new Keyframe[]{ofFloat2, r8});
                ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(childAt, new PropertyValuesHolder[]{ofKeyframe, ofKeyframe2});
                ofPropertyValuesHolder.setDuration(300);
                ofPropertyValuesHolder.setStartDelay((long) (i3 * 33));
                arrayList.add(ofPropertyValuesHolder);
                i3++;
            }
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        final OnListViewFadeListener onListViewFadeListener2 = onListViewFadeListener;
        final int i5 = i;
        final int i6 = i2;
        final ListView listView2 = listView;
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationStart(Animator animator) {
                if (onListViewFadeListener2 != null) {
                    onListViewFadeListener2.onStartListViewFadeOut();
                }
            }

            public void onAnimationEnd(Animator animator) {
                if (i5 == i6) {
                    ListViewUtils.this.animateRemoval(listView2, listView2.getChildAt(i5 - firstVisiblePosition), onListViewFadeListener2);
                } else if (onListViewFadeListener2 != null) {
                    onListViewFadeListener2.onEndListViewFadedOut();
                }
                for (int i = i5; i <= i6; i++) {
                    View childAt = listView2.getChildAt(i - firstVisiblePosition);
                    if (childAt != null) {
                        childAt.setTranslationX(0.0f);
                        childAt.setAlpha(1.0f);
                    }
                }
            }
        });
        animatorSet.start();
    }

    private void animateRemoval(ListView listView, View view, OnListViewFadeListener onListViewFadeListener) {
        int i;
        int i2 = 0;
        final ArrayList arrayList = new ArrayList();
        for (i = 0; i < listView.getCount(); i++) {
            arrayList.add(Integer.valueOf(i));
        }
        i = listView.getFirstVisiblePosition();
        while (i2 < listView.getChildCount()) {
            View childAt = listView.getChildAt(i2);
            if (childAt != view) {
                this.mItemIdTopMap.put(Long.valueOf((long) (i + i2)), Integer.valueOf(childAt.getTop()));
            }
            i2++;
        }
        if (onListViewFadeListener != null) {
            onListViewFadeListener.onEndListViewFadedOut();
            arrayList.remove(listView.getPositionForView(view));
        }
        final ViewTreeObserver viewTreeObserver = listView.getViewTreeObserver();
        final ListView listView2 = listView;
        final OnListViewFadeListener onListViewFadeListener2 = onListViewFadeListener;
        viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() {
                viewTreeObserver.removeOnPreDrawListener(this);
                int firstVisiblePosition = listView2.getFirstVisiblePosition();
                Collection arrayList = new ArrayList();
                for (int i = 0; i < listView2.getChildCount(); i++) {
                    View childAt = listView2.getChildAt(i);
                    Integer num = (Integer) ListViewUtils.this.mItemIdTopMap.get(Long.valueOf((long) ((Integer) arrayList.get(firstVisiblePosition + i)).intValue()));
                    int top = childAt.getTop();
                    int height;
                    Keyframe ofFloat;
                    Keyframe[] keyframeArr;
                    if (num == null) {
                        height = childAt.getHeight() + listView2.getDividerHeight();
                        if (i <= 0) {
                            height = -height;
                        }
                        height = Integer.valueOf(height + top).intValue() - top;
                        if (childAt != null) {
                            ofFloat = Keyframe.ofFloat(0.0f, (float) height);
                            Keyframe.ofFloat(1.0f, 0.0f).setInterpolator(ListViewUtils.this.mUpInterpolator);
                            keyframeArr = new Keyframe[]{ofFloat, r6};
                            arrayList.add(ObjectAnimator.ofPropertyValuesHolder(childAt, new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe("translationY", keyframeArr)}));
                        }
                    } else if (num.intValue() != top) {
                        height = num.intValue() - top;
                        if (childAt != null) {
                            ofFloat = Keyframe.ofFloat(0.0f, (float) height);
                            Keyframe.ofFloat(1.0f, 0.0f).setInterpolator(ListViewUtils.this.mUpInterpolator);
                            keyframeArr = new Keyframe[]{ofFloat, r6};
                            arrayList.add(ObjectAnimator.ofPropertyValuesHolder(childAt, new PropertyValuesHolder[]{PropertyValuesHolder.ofKeyframe("translationY", keyframeArr)}));
                        }
                    }
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (onListViewFadeListener2 != null) {
                            onListViewFadeListener2.onEndResetListView();
                        }
                    }
                });
                animatorSet.start();
                ListViewUtils.this.mItemIdTopMap.clear();
                return true;
            }
        });
    }
}
