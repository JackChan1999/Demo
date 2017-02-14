package com.google.slidingmenu;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/22 00:29
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class PullToRefreshView extends ListView implements AbsListView.OnScrollListener {


    private View mHeaderView;
    private TextView mTv_state;
    private TextView mTv_time;
    private ProgressBar mPb_rotate;
    private ImageView mIv_arrow;
    private int mHeaderHeight;
    private View mFootView;
    private int mFootHeight;
    private ViewPropertyAnimator mUpAnimation;
    private ViewPropertyAnimator mDownAnimation;

    private final int PULL_REFRESH = 0;
    private final int RELEASE_REFRESH = 1;
    private final int REFRESHING = 2;
    private int currentState = PULL_REFRESH;

    public PullToRefreshView(Context context) {
        this(context, null);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnScrollListener(this);
        initHeaderView();
        initAnimation();
        initFootView();
    }

    private void initFootView() {
        mFootView = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null);
        mFootView.measure(0, 0);
        mFootHeight = mFootView.getMeasuredHeight();
        mFootView.setPadding(0, -mFootHeight, 0, 0);
        addFooterView(mFootView);
    }

    private void initAnimation() {
        mUpAnimation = mIv_arrow.animate();
        mUpAnimation.rotation(-180).setDuration(300);
        mDownAnimation = mIv_arrow.animate();
        mDownAnimation.rotation(-360).setDuration(300);
    }

    private void initHeaderView() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.layout_header, null);
        mTv_state = (TextView) mHeaderView.findViewById(R.id.tv_state);
        mTv_time = (TextView) mHeaderView.findViewById(R.id.tv_time);
        mPb_rotate = (ProgressBar) findViewById(R.id.pb_rotate);
        mIv_arrow = (ImageView) findViewById(R.id.iv_arrow);

        mHeaderView.measure(0, 0);
        mHeaderHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
        addHeaderView(mHeaderView);
    }

    float downY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)) {
            case MotionEvent.ACTION_DOWN:
                downY = getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == REFRESHING) {
                    break;
                }
                int dy = (int) (ev.getY() - downY + 0.5f);
                int paddingTop = dy - mHeaderHeight;
                if (paddingTop > -mHeaderHeight && getFirstVisiblePosition() == 0) {
                    mHeaderView.setPadding(0, paddingTop, 0, 0);
                    if (paddingTop >= 0 && currentState == PULL_REFRESH) {
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {
                        currentState = PULL_REFRESH;
                        refreshHeaderView();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentState == PULL_REFRESH) {
                    mHeaderView.setPadding(0, -mHeaderHeight, 0, 0);
                } else {
                    currentState = REFRESHING;
                    mHeaderView.setPadding(0, 0, 0, 0);
                    if (mListener != null) {
                        mListener.onPullRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void refreshHeaderView() {
        switch (currentState) {
            case PULL_REFRESH:
                mTv_state.setText("下拉刷新");
                mUpAnimation.start();
                break;
            case RELEASE_REFRESH:
                mTv_state.setText("松开刷新");
                mDownAnimation.start();
                break;
            case REFRESHING:
                mIv_arrow.clearAnimation();
                mTv_state.setText("正在刷新...");
                mIv_arrow.setVisibility(View.INVISIBLE);
                mPb_rotate.setVisibility(View.VISIBLE);
                mTv_time.setText("上次刷新："+getCurrentTime());
                break;
        }
    }

    public void completeRefresh() {
        if (isLoadingMore){
            isLoadingMore = false;
            mFootView.setPadding(0,-mFootHeight,0,0);
        }else {
            currentState = REFRESHING;
            mHeaderView.setPadding(0,0,0,0);
            mIv_arrow.setVisibility(View.VISIBLE);
            mPb_rotate.setVisibility(View.INVISIBLE);
        }
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("yy-MM-dd HH:mm:ss").format(new Date());
    }

    private boolean isLoadingMore = false;

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && getLastVisiblePosition() ==
                (getCount() - 1) && !isLoadingMore){
            isLoadingMore = true;
            mFootView.setPadding(0,0,0,0);
            setSelection(getCount() - 1);
            if (mListener != null){
                mListener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount) {

    }

    private OnRefreshListener mListener;

    public interface OnRefreshListener {
        void onPullRefresh();

        void onLoadingMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }
}
