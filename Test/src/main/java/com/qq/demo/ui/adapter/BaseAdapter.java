package com.qq.demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qq.demo.R;
import com.qq.demo.http.Constants;
import com.qq.demo.manager.ThreadPoolFactory;
import com.qq.demo.ui.holder.BaseHolder;
import com.qq.demo.ui.holder.LoadMoreHolder;
import com.qq.demo.utils.UIUtils;

import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/4/15 12:23
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected Context mContext;
    protected List<T> mDatas;
    protected int mLayoutId;
    protected LayoutInflater mInflater;
    private LoadMoreTask mLoadMoreTask;
    private LoadMoreHolder mLoadMoreHolder;

    public static final int VIEWTYPE_LOADMORE = 0;
    public static final int VIEWTYPE_NORMAL = 1;

    public BaseAdapter(Context context, int layoutId, List<T> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        mLayoutId = layoutId;
        mContext = context;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("viewType----" + viewType);
        BaseHolder holder = null;
        if (viewType == VIEWTYPE_LOADMORE) {
            holder = BaseHolder.get(parent.getContext(), null, parent, R.layout.item_loadmore, -1);
        } else {
            holder = BaseHolder.get(parent.getContext(), null, parent, mLayoutId, -1);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        System.out.println("position----" + position);
        holder.updatePosition(position);

        if (getItemViewType(position) == VIEWTYPE_LOADMORE) {
            if (hasLoadMore()) {
                //perFormLoadMore();
                setLoadMoreHolder(holder,0);
                mLoadMoreTask = new LoadMoreTask(holder);
                ThreadPoolFactory.getNormalPool().execute(mLoadMoreTask);
            } else {
                //mLoadMoreHolder.refreshHolderView(LoadMoreHolder.STATE_NONE);
                setLoadMoreHolder(holder,2);
            }
        } else {
            convert(holder, mDatas.get(position));
        }
    }

   /* private void perFormLoadMore() {
        if (mLoadMoreTask == null) {
            int state = LoadMoreHolder.STATE_LOADING;
            mLoadMoreHolder.refreshHolderView(state);
            mLoadMoreTask = new LoadMoreTask();
            ThreadPoolFactory.getNormalPool().execute(mLoadMoreTask);
        }
    }*/

    public void setLoadMoreHolder(BaseHolder holder,Integer state) {
        holder.setVisible(R.id.item_loadmore_container_loading, false);
        holder.setVisible(R.id.item_loadmore_container_retry, false);

        switch (state) {
            case 0:
                holder.setVisible(R.id.item_loadmore_container_loading, true);
                break;
            case 1:
                holder.setVisible(R.id.item_loadmore_container_retry, true);
                break;
            case 2:

                break;
        }
    }

    public abstract void convert(BaseHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private LoadMoreHolder getLoadMoreHolder(ViewGroup parent) {
        View itemView = mInflater.inflate(R.layout.item_loadmore, parent, false);
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder(mContext, itemView, parent, -1);
        }
        return mLoadMoreHolder;
    }

    /**
     * @return
     * @des 决定有没有加载更多, 默认是返回true, 但是子类可以覆写此方法, 如果子类返回的是flase, 就不会去加载更多
     * @call getView中滑动底的时候会调用
     */
    protected boolean hasLoadMore() {
        return true;
    }

    private class LoadMoreTask implements Runnable {
        BaseHolder holder;

        public LoadMoreTask(BaseHolder holder){
            this.holder = holder;
        }


        @Override
        public void run() {

            //请求网络加载更多数据
            List<T> loadMoreDatas = null;
            int state = LoadMoreHolder.STATE_LOADING;
            try {
                loadMoreDatas = onLoadMore();
                //处理返回的结果
                if (loadMoreDatas == null) {//没有过多数据
                    state = 2;
                } else {
                    if (loadMoreDatas.size() < Constants.PAGESIZE) {
                        state = 2;
                    } else {
                        state = 0;
                    }
                }


            } catch (Throwable throwable) {
                throwable.printStackTrace();
                state = 1;
            }

            final int tempsate = state; //定义一个中转的临时变量
            final List<T> tempLoadMoreDatas = loadMoreDatas;

            UIUtils.postTaskSafely(new Runnable() {
                @Override
                public void run() {
                    //刷新loadmore视图
                    //mLoadMoreHolder.refreshHolderView(tempsate);
                    setLoadMoreHolder(holder,tempsate);
                    //刷新listview视图
                    if (tempLoadMoreDatas != null) {
                        mDatas.addAll(tempLoadMoreDatas);
                        notifyDataSetChanged();
                    }
                }
            });
            mLoadMoreTask = null;
        }
    }

    /**
     * @des 可有可无的方法, 定义成一个public方法, 如果子类有加载更多.再去覆写就行了
     * @des 真正开始加载更多数据的地方
     * @call 滑到底的时候
     */
    public List<T> onLoadMore() throws Throwable {
        return null;
    }
}
