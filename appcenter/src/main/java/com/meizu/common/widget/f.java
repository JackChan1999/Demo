package com.meizu.common.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class f<T> extends b {

    public static class a extends com.meizu.common.widget.b.a {
        List j;

        public a(boolean showIfEmpty, boolean hasHeader, List objects) {
            super(showIfEmpty, hasHeader, objects == null ? 0 : objects.size());
            this.j = objects;
        }
    }

    protected abstract View a(Context context, int i, int i2, T t, int i3, int i4, ViewGroup viewGroup);

    protected abstract void a(View view, Context context, int i, int i2, T t, int i3, int i4);

    public /* synthetic */ com.meizu.common.widget.b.a a(int x0) {
        return b(x0);
    }

    public f(Context context, List<T>... lists) {
        int length = (lists == null || lists.length <= 0) ? 10 : lists.length;
        super(context, length);
        a((List[]) lists);
    }

    public void a(List<T>... lists) {
        if (lists != null && lists.length > 0) {
            a(false);
            for (List a : lists) {
                a(false, true, a);
            }
            a(true);
        }
    }

    public int a(boolean showIfEmpty, boolean hasHeader, List<T> objects) {
        return a(new a(showIfEmpty, hasHeader, objects));
    }

    public int a(a partition) {
        return super.a((com.meizu.common.widget.b.a) partition);
    }

    public a b(int partitionIndex) {
        return (a) super.a(partitionIndex);
    }

    protected int h(int partitionIndex, int offset) {
        return offset - this.f[partitionIndex].f;
    }

    public void a(int partitionIndex, List<T> objects) {
        a g = b(partitionIndex);
        g.j = objects;
        g.e = objects == null ? 0 : objects.size();
        c_();
        notifyDataSetChanged();
    }

    protected T f(int partitionIndex, int offset) {
        List<T> objects = b(partitionIndex).j;
        if (objects == null) {
            return null;
        }
        int pos = h(partitionIndex, offset);
        if (pos >= 0) {
            return objects.get(pos);
        }
        return null;
    }

    protected long g(int partitionIndex, int offset) {
        if (b(partitionIndex).j == null) {
            return 0;
        }
        int pos = h(partitionIndex, offset);
        if (pos >= 0) {
            return (long) pos;
        }
        return 0;
    }

    protected View a(int position, int partitionIndex, int offset, int itemBgType, View convertView, ViewGroup parent) {
        List<T> objects = b(partitionIndex).j;
        if (objects == null) {
            throw new IllegalStateException("the partition " + partitionIndex + " list is null");
        } else if (objects.size() <= 0) {
            Log.w("IndexOutOfBounds", "MultiArrayPartitionAdapter getView exception, List partition item size :" + objects.size());
            throw new IndexOutOfBoundsException("APP数据集为空:请先检查数据集中书否有数据,然后再访问!");
        } else {
            int listIndex = h(partitionIndex, offset);
            if (listIndex >= objects.size()) {
                Log.w("IndexOutOfBounds", "MultiArrayPartitionAdapter getView exception, List partition item size :" + objects.size() + ", listIndex :" + listIndex);
                throw new IndexOutOfBoundsException("APP越界操作:当前数据集大小为:" + objects.size() + ",有效访问范围为:0~" + (objects.size() - 1) + ",当前访问序号为:" + listIndex + ",非法,请处理！");
            }
            View view;
            Object object = objects.get(listIndex);
            if (convertView == null) {
                view = a(this.b, position, partitionIndex, object, offset, itemBgType, parent);
            } else {
                view = convertView;
            }
            a(view, this.b, position, partitionIndex, object, offset, itemBgType);
            return view;
        }
    }
}
