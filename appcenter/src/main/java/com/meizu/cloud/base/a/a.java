package com.meizu.cloud.base.a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.List;

public abstract class a<T> extends BaseAdapter {
    protected List<T> d;
    protected Context e;

    protected abstract View a(Context context, int i, List<T> list);

    protected abstract void a(View view, Context context, int i, T t);

    public void a(List<T> dataList) {
        if (this.d != dataList) {
            b(dataList);
            this.d = dataList;
        }
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.d == null ? 0 : this.d.size();
    }

    public Object getItem(int position) {
        if (this.d == null || position < 0 || position >= getCount()) {
            return null;
        }
        return this.d.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        T data = getItem(position);
        if (data == null) {
            throw new IllegalStateException("couldn't move datalist to position " + position);
        }
        View v = convertView;
        if (v == null) {
            v = a(this.e, position, this.d);
        }
        a(v, this.e, position, data);
        return v;
    }

    protected void b(List<T> list) {
    }
}
