package com.meizu.common.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.common.widget.BasePartitionAdapter.Partition;
import java.util.List;

public abstract class MultiArrayPartitionAdapter<T> extends BasePartitionAdapter {

    public static class ArrayPartition extends Partition {
        List mObjects;

        public ArrayPartition(boolean z, boolean z2, List list) {
            super(z, z2, list == null ? 0 : list.size());
            this.mObjects = list;
        }
    }

    protected abstract void bindView(View view, Context context, int i, int i2, T t, int i3, int i4);

    protected abstract View newView(Context context, int i, int i2, T t, int i3, int i4, ViewGroup viewGroup);

    public MultiArrayPartitionAdapter(Context context) {
        super(context);
    }

    public MultiArrayPartitionAdapter(Context context, List<T>... listArr) {
        int length = (listArr == null || listArr.length <= 0) ? 10 : listArr.length;
        super(context, length);
        addPartitions(listArr);
    }

    public void addPartitions(List<T>... listArr) {
        if (listArr != null && listArr.length > 0) {
            setNotificationsEnabled(false);
            for (List addPartition : listArr) {
                addPartition(false, true, addPartition);
            }
            setNotificationsEnabled(true);
        }
    }

    public int addPartition(boolean z, boolean z2, List<T> list) {
        return addPartition(new ArrayPartition(z, z2, list));
    }

    public int addPartition(ArrayPartition arrayPartition) {
        return super.addPartition(arrayPartition);
    }

    public ArrayPartition getPartition(int i) {
        return (ArrayPartition) super.getPartition(i);
    }

    public List<T> getPartitionData(int i) {
        return getPartition(i).mObjects;
    }

    public int getDataPosition(int i) {
        int i2 = 0;
        ensureCacheValid();
        int i3 = 0;
        while (i2 < this.mPartitionCount) {
            int i4 = this.mPartitions[i2].mSize + i3;
            if (i < i3 || i >= i4) {
                i2++;
                i3 = i4;
            } else {
                i3 = i - i3;
                if (this.mPartitions[i2].mHasHeader) {
                    i3--;
                }
                i4 = this.mPartitions[i2].mCount - this.mPartitions[i2].mFooterViewsCount;
                if (i3 < this.mPartitions[i2].mHeaderViewsCount || i3 >= i4) {
                    return -1;
                }
                return getDataPosition(i2, i3);
            }
        }
        return -1;
    }

    protected int getDataPosition(int i, int i2) {
        return i2 - this.mPartitions[i].mHeaderViewsCount;
    }

    public void changePartition(int i, List<T> list) {
        ArrayPartition partition = getPartition(i);
        partition.mObjects = list;
        partition.mItemCount = list == null ? 0 : list.size();
        invalidate();
        notifyDataSetChanged();
    }

    protected T getItem(int i, int i2) {
        List list = getPartition(i).mObjects;
        if (list == null) {
            return null;
        }
        int dataPosition = getDataPosition(i, i2);
        if (dataPosition >= 0) {
            return list.get(dataPosition);
        }
        return null;
    }

    protected long getItemId(int i, int i2) {
        if (getPartition(i).mObjects == null) {
            return 0;
        }
        int dataPosition = getDataPosition(i, i2);
        if (dataPosition >= 0) {
            return (long) dataPosition;
        }
        return 0;
    }

    protected View getView(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup) {
        List list = getPartition(i2).mObjects;
        if (list == null) {
            throw new IllegalStateException("the partition " + i2 + " list is null");
        } else if (list.size() <= 0) {
            Log.w("IndexOutOfBounds", "MultiArrayPartitionAdapter getView exception, List partition item size :" + list.size());
            throw new IndexOutOfBoundsException("APP数据集为空:请先检查数据集中书否有数据,然后再访问!");
        } else {
            int dataPosition = getDataPosition(i2, i3);
            if (dataPosition >= list.size()) {
                Log.w("IndexOutOfBounds", "MultiArrayPartitionAdapter getView exception, List partition item size :" + list.size() + ", listIndex :" + dataPosition);
                throw new IndexOutOfBoundsException("APP越界操作:当前数据集大小为:" + list.size() + ",有效访问范围为:0~" + (list.size() - 1) + ",当前访问序号为:" + dataPosition + ",非法,请处理！");
            }
            View newView;
            Object obj = list.get(dataPosition);
            if (view == null) {
                newView = newView(this.mContext, i, i2, obj, i3, i4, viewGroup);
            } else {
                newView = view;
            }
            bindView(newView, this.mContext, i, i2, obj, i3, i4);
            return newView;
        }
    }
}
