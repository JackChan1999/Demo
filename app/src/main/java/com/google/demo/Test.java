package com.google.demo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/8 22:11
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class Test extends Activity {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }






    class Adapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
            super.onViewAttachedToWindow(holder);

        }
    }




    public class EmptyWrapper<T> extends
            RecyclerView.Adapter<RecyclerView.ViewHolder>
    {
        public static final int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 1;

        private RecyclerView.Adapter mInnerAdapter;
        private View mEmptyView;
        private int mEmptyLayoutId;


        public EmptyWrapper(RecyclerView.Adapter adapter)
        {
            mInnerAdapter = adapter;
        }

        private boolean isEmpty()
        {
            return (mEmptyView != null || mEmptyLayoutId != 0)
                    && mInnerAdapter.getItemCount() == 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(
                ViewGroup parent, int viewType)
        {
            if (isEmpty())
            {
                ViewHolder holder;
                if (mEmptyView != null)
                {
                    holder = ViewHolder.createViewHolder(
                            parent.getContext(), mEmptyView);
                } else
                {
                    holder = ViewHolder.createViewHolder(
                            parent.getContext(), parent, mEmptyLayoutId);
                }
                return holder;
            }
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView)
        {
            WrapperUtils.onAttachedToRecyclerView(mInnerAdapter,
                    recyclerView, new WrapperUtils.SpanSizeCallback()
            {
                @Override
                public int getSpanSize(
                        GridLayoutManager gridLayoutManager,
                        GridLayoutManager.SpanSizeLookup oldLookup, int position)
                {
                    if (isEmpty())
                    {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (oldLookup != null)
                    {
                        return oldLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });


        }

        @Override
        public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
        {
            mInnerAdapter.onViewAttachedToWindow(holder);
            if (isEmpty())
            {
                WrapperUtils.setFullSpan(holder);
            }
        }


        @Override
        public int getItemViewType(int position)
        {
            if (isEmpty())
            {
                return ITEM_TYPE_EMPTY;
            }
            return mInnerAdapter.getItemViewType(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
        {
            if (isEmpty())
            {
                return;
            }
            mInnerAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount()
        {
            if (isEmpty()) return 1;
            return mInnerAdapter.getItemCount();
        }



        public void setEmptyView(View emptyView)
        {
            mEmptyView = emptyView;
        }

        public void setEmptyView(int layoutId)
        {
            mEmptyLayoutId = layoutId;
        }

    }




}
