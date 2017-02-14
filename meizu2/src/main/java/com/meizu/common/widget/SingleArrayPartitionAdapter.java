package com.meizu.common.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.common.widget.BasePartitionAdapter.Partition;
import java.util.Arrays;
import java.util.List;

public abstract class SingleArrayPartitionAdapter<T> extends BasePartitionAdapter {
    protected List<T> mObjects;

    protected abstract void bindView(View view, Context context, int i, int i2, T t, int i3, int i4);

    protected abstract View newView(Context context, int i, int i2, T t, int i3, int i4, ViewGroup viewGroup);

    public SingleArrayPartitionAdapter(Context context) {
        super(context);
    }

    public SingleArrayPartitionAdapter(Context context, List<T> list, int... iArr) {
        int length = (iArr == null || iArr.length <= 0) ? 10 : iArr.length;
        super(context, length);
        this.mObjects = list;
        addPartitions(iArr);
    }

    private void addPartitions(int[] iArr) {
        if (iArr != null && iArr.length > 0) {
            setNotificationsEnabled(false);
            for (int addPartition : iArr) {
                addPartition(false, true, addPartition);
            }
            setNotificationsEnabled(true);
        }
    }

    public int addPartition(boolean z, boolean z2, int i) {
        return addPartition(new Partition(z, z2, i));
    }

    public int addPartition(Partition partition) {
        return super.addPartition(partition);
    }

    public void changePartitions(List<T> list, Partition... partitionArr) {
        int i = 0;
        this.mObjects = list;
        setNotificationsEnabled(false);
        clearPartitions();
        if (partitionArr != null && partitionArr.length > 0) {
            while (i < partitionArr.length) {
                addPartition(partitionArr[i]);
                i++;
            }
        }
        setNotificationsEnabled(true);
    }

    public void changePartitions(List<T> list, int... iArr) {
        int i;
        int i2;
        this.mObjects = list;
        setNotificationsEnabled(false);
        int length = iArr == null ? 0 : iArr.length;
        if (length > this.mPartitionCount) {
            i = this.mPartitionCount;
        } else {
            i = length;
        }
        for (i2 = 0; i2 < i; i2++) {
            this.mPartitions[i2].mItemCount = iArr[i2];
        }
        if (this.mPartitionCount > i) {
            Arrays.fill(this.mPartitions, i, this.mPartitionCount, null);
            this.mPartitionCount = i;
        } else if (length > i) {
            i2 = length - i;
            for (length = 0; length < i2; length++) {
                addPartition(false, true, iArr[i + length]);
            }
        }
        invalidate();
        notifyDataSetChanged();
        setNotificationsEnabled(true);
    }

    public List<T> getPartitionData() {
        return this.mObjects;
    }

    public int getDataPosition(int i) {
        int i2 = 0;
        ensureCacheValid();
        int i3 = 0;
        int i4 = 0;
        while (i2 < this.mPartitionCount) {
            int i5 = this.mPartitions[i2].mSize + i3;
            if (i < i3 || i >= i5) {
                i2++;
                i4 = this.mPartitions[i2].mItemCount + i4;
                i3 = i5;
            } else {
                i3 = i - i3;
                if (this.mPartitions[i2].mHasHeader) {
                    i3--;
                }
                i5 = this.mPartitions[i2].mCount - this.mPartitions[i2].mFooterViewsCount;
                if (i3 < this.mPartitions[i2].mHeaderViewsCount || i3 >= i5) {
                    return -1;
                }
                return (i3 - this.mPartitions[i2].mHeaderViewsCount) + i4;
            }
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    protected int getDataPosition(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            i4 += this.mPartitions[i3].mItemCount;
            i3++;
        }
        return (i2 - this.mPartitions[i].mHeaderViewsCount) + i4;
    }

    protected T getItem(int i, int i2) {
        if (this.mObjects == null) {
            return null;
        }
        return this.mObjects.get(getDataPosition(i, i2));
    }

    protected long getItemId(int i, int i2) {
        if (this.mObjects == null) {
            return 0;
        }
        return (long) getDataPosition(i, i2);
    }

    protected View getView(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup) {
        if (this.mObjects == null) {
            throw new IllegalStateException("the list is null");
        }
        View newView;
        Object obj = this.mObjects.get(getDataPosition(i2, i3));
        if (view == null) {
            newView = newView(this.mContext, i, i2, obj, i3, i4, viewGroup);
        } else {
            newView = view;
        }
        bindView(newView, this.mContext, i, i2, obj, i3, i4);
        return newView;
    }
}
