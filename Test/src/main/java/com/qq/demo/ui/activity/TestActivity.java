package com.qq.demo.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qq.demo.R;
import com.qq.demo.bean.AppInfoBean;
import com.qq.demo.http.AppProtocol;
import com.qq.demo.http.Constants;
import com.qq.demo.http.HttpUtils;
import com.qq.demo.manager.ThreadPoolFactory;
import com.qq.demo.recyclerview.EndlessRecyclerOnScrollListener;
import com.qq.demo.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.qq.demo.recyclerview.RecyclerViewUtils;
import com.qq.demo.ui.adapter.BaseAdapter;
import com.qq.demo.ui.adapter.CommonAdapter;
import com.qq.demo.ui.holder.BaseHolder;
import com.qq.demo.ui.holder.ViewHolder;
import com.qq.demo.ui.widget.LoadingFooter;
import com.qq.demo.utils.BitmapHelper;
import com.qq.demo.utils.RecyclerViewStateUtils;
import com.qq.demo.utils.StringUtils;
import com.qq.demo.utils.UIUtils;

import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/6/23 22:42
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class TestActivity extends AppCompatActivity {

    private RecyclerView mRv;
    private View mItemView;
    private List<AppInfoBean> mData;
    private AppProtocol mAppProtocol;
    private HeaderAndFooterRecyclerViewAdapter mRvAdapter;
    private MyAdapter mAdapter;

    /**每一页展示多少条数据*/
    private static final int REQUEST_COUNT = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //initData();
        //setAdapter();
        requestData();
    }

    private void initView() {
        setContentView(R.layout.layout_test);
        mRv = (RecyclerView) findViewById(R.id.rv);
        //mItemView = LayoutInflater.from(this).inflate(R.layout.item_app_info,null,false);
    }

    private void setupRecyclerview(){
        mAdapter = new MyAdapter(this, R.layout.item_app_info,mData);
        mRvAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        mRv.setAdapter(mRvAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewUtils.setHeaderView(mRv, getTv());
        mRv.addOnScrollListener(mOnScrollListener);
    }

    private TextView getTv(){
        TextView textView = new TextView(this);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);
        textView.setText("header");
        return textView;
    }

    private void initData() {
        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                mAppProtocol = new AppProtocol();
                try {
                    mData = mAppProtocol.loadData(0);
                    System.out.println("size---------"+mData.size());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                UIUtils.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                    }
                });
            }
        });

    }

    private void setAdapter(){
        AppAdapter adapter = new AppAdapter(this,R.layout.item_app_info,mData);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(adapter);
    }

    private class AppAdapter extends BaseAdapter<AppInfoBean>{

        public AppAdapter(Context context, int layoutId, List<AppInfoBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(BaseHolder holder, AppInfoBean appInfoBean) {
            holder.setText(R.id.item_appinfo_tv_des,appInfoBean.des);
            holder.setText(R.id.item_appinfo_tv_size, StringUtils.formatFileSize(appInfoBean.size));
            holder.setText(R.id.item_appinfo_tv_title,appInfoBean.name);
            holder.setRating(R.id.item_appinfo_rb_stars,appInfoBean.stars);
            String url = Constants.URLS.IMAGEBASEURL + appInfoBean.iconUrl;
            BitmapHelper.display((ImageView) holder.getView(R.id.item_appinfo_iv_icon), url);
        }

        @Override
        public List<AppInfoBean> onLoadMore() throws Throwable {
            return mAppProtocol.loadData(mData.size());
        }
    }

    private class MyAdapter extends CommonAdapter<AppInfoBean>{

        public MyAdapter(Context context, int layoutId, List<AppInfoBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder holder, AppInfoBean appInfoBean) {
            holder.setText(R.id.item_appinfo_tv_des,appInfoBean.des);
            holder.setText(R.id.item_appinfo_tv_size, StringUtils.formatFileSize(appInfoBean.size));
            holder.setText(R.id.item_appinfo_tv_title,appInfoBean.name);
            holder.setRating(R.id.item_appinfo_rb_stars,appInfoBean.stars);
            String url = Constants.URLS.IMAGEBASEURL + appInfoBean.iconUrl;
            BitmapHelper.display((ImageView) holder.getView(R.id.item_appinfo_iv_icon), url);
        }
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState((RecyclerView) view);
            if(state == LoadingFooter.State.Loading) {
                Log.d("@Allen", "the state is Loading, just wait..");
                return;
            }

            if (hasLoadMore()) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(TestActivity.this, (RecyclerView) view, REQUEST_COUNT,
                        LoadingFooter.State.Loading, null);
               loadMore();
            } else {
                //the end
                RecyclerViewStateUtils.setFooterViewState(TestActivity.this, (RecyclerView) view, REQUEST_COUNT,
                        LoadingFooter.State.TheEnd, null);
            }
        }
    };

    private void loadMore() {
        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                List<AppInfoBean> tempLoadMoreDatas = null;
                LoadingFooter.State state = LoadingFooter.State.Loading;
                try {
                    tempLoadMoreDatas = mAppProtocol.loadData(mData.size());
                    if (tempLoadMoreDatas == null) {
                        state = LoadingFooter.State.TheEnd;
                    } else {
                        if (tempLoadMoreDatas.size() < Constants.PAGESIZE) {
                            state = LoadingFooter.State.NetWorkError;
                        } else {
                            state = LoadingFooter.State.Normal;
                        }
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    state = LoadingFooter.State.NetWorkError;
                }

                final List<AppInfoBean> finalTempLoadMoreDatas = tempLoadMoreDatas;
                final LoadingFooter.State tempstate = state;
                UIUtils.postTaskSafely(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerViewStateUtils.setFooterViewState(TestActivity.this, mRv, 20, tempstate, mFooterClick);
                        if (finalTempLoadMoreDatas != null) {
                            mData.addAll(finalTempLoadMoreDatas);
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        });
    }

    private void notifyDataSetChanged() {
        mRvAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(TestActivity.this, mRv, REQUEST_COUNT,
                    LoadingFooter.State.Loading, null);
            loadMore();
        }
    };

    private void requestData() {

        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                if(HttpUtils.isNetworkAvailable(TestActivity.this)) {
                    mAppProtocol = new AppProtocol();
                    try {
                        mData = mAppProtocol.loadData(0);
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                    UIUtils.postTaskSafely(new Runnable() {
                        @Override
                        public void run() {
                            setupRecyclerview();
                            //RecyclerViewStateUtils.setFooterViewState(mRv, LoadingFooter.State.Normal);
                        }
                    });

                } else {

                    UIUtils.postTaskSafely(new Runnable() {
                        @Override
                        public void run() {
                           /* RecyclerViewStateUtils.setFooterViewState(TestActivity.this, mRv,
                                    REQUEST_COUNT, LoadingFooter.State.NetWorkError, mFooterClick);*/
                        }
                    });

                }
            }
        });
    }

    protected boolean hasLoadMore() {
        return true;
    }

}
