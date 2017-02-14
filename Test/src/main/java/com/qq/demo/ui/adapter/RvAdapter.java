package com.qq.demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qq.demo.R;

import java.util.List;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/1 19:52
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> implements View.OnClickListener{

    private List<String> mList;
    private Context mContext;
    private onItemClickListener mItemClickListener;
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;

    public RvAdapter(Context context, List<String> list){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;


        //LinearLayoutManager manager = new LinearLayoutManager();参数三：反转布局
        GridLayoutManager layoutManager = new GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {
                if (position == 0){
                    return 3;
                }
                return 1;
            }
        });
    }

    public void setItemClickListener(onItemClickListener itemClickListener){
        mItemClickListener = itemClickListener;
    }

    public void remove(int position){
        mList.remove(position);
        //notifyDataSetChanged();
        notifyItemRemoved(position);//带动画
    }

    public void add(int position, String data){
        mList.add(position,data);
        notifyItemInserted(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_item,parent,false);
        itemView.setOnClickListener(this);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv.setText(mList.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView = null;
    }

    @Override
    public int getItemCount() {return mList.size();}

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null && mRecyclerView != null){
            int position = mRecyclerView.getChildAdapterPosition(v);
            mItemClickListener.onItemclick(mRecyclerView,v,position,mList.get(position));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    public interface onItemClickListener{
        void onItemclick(RecyclerView parent,View view,int position,String data);
    }
}
