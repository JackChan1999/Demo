package com.meizu.common.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.common.R;
import com.meizu.widget.ListSelectFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public abstract class BasePartitionAdapter extends AbsBasePartitionAdapter implements ListSelectFilter {
    private static final int CAPACITY_INCREMENT = 10;
    protected static final int INITIAL_CAPACITY = 10;
    public static final int ITEM_VIEW_TYPE_PARTITION_HEADER = 0;
    public static final int PARTITION_FIRST_ITEM_BG_TYPE = 1;
    public static final int PARTITION_HEADER_ITEM_BG_TYPE = 0;
    public static final int PARTITION_LAST_ITEM_BG_TYPE = 3;
    public static final int PARTITION_MIDDLE_ITEM_BG_TYPE = 2;
    public static final int PARTITION_SINGLE_ITEM_BG_TYPE = 4;
    protected boolean mCacheValid;
    protected final Context mContext;
    protected int mCount;
    protected int mItemCounts;
    private boolean mNotificationNeeded;
    private boolean mNotificationsEnabled;
    protected int mPartitionCount;
    protected Partition[] mPartitions;

    public static class Partition {
        int mCount;
        ArrayList<PartitionFixedViewInfo> mFooterViewInfos = new ArrayList();
        int mFooterViewsCount;
        boolean mHasHeader;
        ArrayList<PartitionFixedViewInfo> mHeaderViewInfos = new ArrayList();
        int mHeaderViewsCount;
        int mItemCount;
        boolean mShowIfEmpty;
        int mSize;

        public Partition(boolean z, boolean z2, int i) {
            this.mShowIfEmpty = z;
            this.mHasHeader = z2;
            this.mItemCount = i;
        }
    }

    public class PartitionFixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;
    }

    protected abstract Object getItem(int i, int i2);

    protected abstract long getItemId(int i, int i2);

    protected abstract View getView(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup);

    public BasePartitionAdapter(Context context) {
        this(context, 10);
    }

    public BasePartitionAdapter(Context context, int i) {
        this.mNotificationsEnabled = true;
        this.mContext = context;
        this.mPartitions = new Partition[i];
    }

    public Context getContext() {
        return this.mContext;
    }

    protected int addPartition(Partition partition) {
        if (this.mPartitionCount >= this.mPartitions.length) {
            Object obj = new Partition[(this.mPartitionCount + 10)];
            System.arraycopy(this.mPartitions, 0, obj, 0, this.mPartitionCount);
            this.mPartitions = obj;
        }
        Partition[] partitionArr = this.mPartitions;
        int i = this.mPartitionCount;
        this.mPartitionCount = i + 1;
        partitionArr[i] = partition;
        invalidate();
        notifyDataSetChanged();
        return this.mPartitionCount - 1;
    }

    public void removePartition(int i) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        System.arraycopy(this.mPartitions, i + 1, this.mPartitions, i, (this.mPartitionCount - i) - 1);
        Partition[] partitionArr = this.mPartitions;
        int i2 = this.mPartitionCount - 1;
        this.mPartitionCount = i2;
        partitionArr[i2] = null;
        invalidate();
        notifyDataSetChanged();
    }

    public void clearPartitions() {
        Arrays.fill(this.mPartitions, null);
        this.mPartitionCount = 0;
        invalidate();
        notifyDataSetChanged();
    }

    public Partition getPartition(int i) {
        if (i < this.mPartitionCount) {
            return this.mPartitions[i];
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    public boolean isShowIfEmpty(int i) {
        return this.mPartitions[i].mShowIfEmpty;
    }

    public void setShowIfEmpty(int i, boolean z) {
        this.mPartitions[i].mShowIfEmpty = z;
        invalidate();
    }

    public boolean hasHeader(int i) {
        return this.mPartitions[i].mHasHeader;
    }

    public void setHasHeader(int i, boolean z) {
        this.mPartitions[i].mHasHeader = z;
        invalidate();
    }

    public boolean isTopHeader() {
        int partitionForPosition = getPartitionForPosition(0);
        if (partitionForPosition < 0) {
            return false;
        }
        return this.mPartitions[partitionForPosition].mHasHeader;
    }

    protected void invalidate() {
        this.mCacheValid = false;
    }

    protected void ensureCacheValid() {
        int i = 0;
        if (!this.mCacheValid) {
            this.mCount = 0;
            this.mItemCounts = 0;
            while (i < this.mPartitionCount) {
                this.mPartitions[i].mHeaderViewsCount = this.mPartitions[i].mHeaderViewInfos.size();
                this.mPartitions[i].mFooterViewsCount = this.mPartitions[i].mFooterViewInfos.size();
                this.mPartitions[i].mCount = (this.mPartitions[i].mHeaderViewsCount + this.mPartitions[i].mItemCount) + this.mPartitions[i].mFooterViewsCount;
                int i2 = this.mPartitions[i].mCount;
                if (this.mPartitions[i].mHasHeader && (i2 != 0 || this.mPartitions[i].mShowIfEmpty)) {
                    i2++;
                }
                this.mPartitions[i].mSize = i2;
                this.mCount = i2 + this.mCount;
                this.mItemCounts += this.mPartitions[i].mItemCount;
                i++;
            }
            this.mCacheValid = true;
        }
    }

    public int getCount() {
        ensureCacheValid();
        return this.mCount;
    }

    public int getItemCount() {
        ensureCacheValid();
        return this.mItemCounts;
    }

    public int getPartitionCount() {
        return this.mPartitionCount;
    }

    public int getCountForPartition(int i) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ensureCacheValid();
        return this.mPartitions[i].mCount;
    }

    public boolean isPartitionEmpty(int i) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ensureCacheValid();
        return this.mPartitions[i].mCount == 0;
    }

    public int getPartitionForPosition(int i) {
        int i2 = 0;
        ensureCacheValid();
        int i3 = 0;
        while (i2 < this.mPartitionCount) {
            int i4 = this.mPartitions[i2].mSize + i3;
            if (i >= i3 && i < i4) {
                return i2;
            }
            i2++;
            i3 = i4;
        }
        return -1;
    }

    public int getOffsetInPartition(int i) {
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
                    return i3 - 1;
                }
                return i3;
            }
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    public int getPositionForPartition(int i) {
        int i2 = 0;
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ensureCacheValid();
        int i3 = 0;
        while (i2 < i) {
            i3 += this.mPartitions[i2].mSize;
            i2++;
        }
        return i3;
    }

    public void addHeaderView(int i, View view, Object obj, boolean z) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        PartitionFixedViewInfo partitionFixedViewInfo = new PartitionFixedViewInfo();
        partitionFixedViewInfo.view = view;
        partitionFixedViewInfo.data = obj;
        partitionFixedViewInfo.isSelectable = z;
        this.mPartitions[i].mHeaderViewInfos.add(partitionFixedViewInfo);
        invalidate();
        notifyDataSetChanged();
    }

    public void addHeaderView(int i, View view) {
        addHeaderView(i, view, null, true);
    }

    public int getHeaderViewsCount(int i) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ensureCacheValid();
        return this.mPartitions[i].mHeaderViewsCount;
    }

    public boolean removeHeaderView(int i, View view) {
        if (i < this.mPartitionCount) {
            return removeFixedViewInfo(view, this.mPartitions[i].mHeaderViewInfos);
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    private boolean removeFixedViewInfo(View view, ArrayList<PartitionFixedViewInfo> arrayList) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (((PartitionFixedViewInfo) arrayList.get(i)).view == view) {
                arrayList.remove(i);
                invalidate();
                notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }

    public void addFooterView(int i, View view, Object obj, boolean z) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        PartitionFixedViewInfo partitionFixedViewInfo = new PartitionFixedViewInfo();
        partitionFixedViewInfo.view = view;
        partitionFixedViewInfo.data = obj;
        partitionFixedViewInfo.isSelectable = z;
        this.mPartitions[i].mFooterViewInfos.add(partitionFixedViewInfo);
        invalidate();
        notifyDataSetChanged();
    }

    public void addFooterView(int i, View view) {
        addFooterView(i, view, null, true);
    }

    public int getFooterViewsCount(int i) {
        if (i >= this.mPartitionCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        ensureCacheValid();
        return this.mPartitions[i].mFooterViewsCount;
    }

    public boolean removeFooterView(int i, View view) {
        if (i < this.mPartitionCount) {
            return removeFixedViewInfo(view, this.mPartitions[i].mFooterViewInfos);
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    public boolean isHeaderView(int i) {
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
                return isHeaderView(i2, i3);
            }
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    protected boolean isHeaderView(int i, int i2) {
        if (i2 < 0 || i2 >= this.mPartitions[i].mHeaderViewsCount) {
            return false;
        }
        return true;
    }

    public boolean isFooterView(int i) {
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
                return isFooterView(i2, i3);
            }
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    protected boolean isFooterView(int i, int i2) {
        if (i2 >= this.mPartitions[i].mCount - this.mPartitions[i].mFooterViewsCount) {
            return true;
        }
        return false;
    }

    public int getViewTypeCount() {
        return getItemViewTypeCount() + 1;
    }

    public int getItemViewTypeCount() {
        return 1;
    }

    public int getItemViewType(int i) {
        ensureCacheValid();
        int i2 = 0;
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
                if (i3 == -1) {
                    return 0;
                }
                if (isHeaderView(i2, i3) || isFooterView(i2, i3)) {
                    return -2;
                }
                return getItemViewType(i2, i);
            }
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    protected int getItemViewType(int i, int i2) {
        return 1;
    }

    protected int getItemBackgroundType(int i, int i2) {
        if (i2 == -1) {
            return 0;
        }
        if (i2 == 0 && this.mPartitions[i].mCount == 1) {
            return 4;
        }
        if (i2 == 0) {
            return 1;
        }
        if (i2 == this.mPartitions[i].mCount - 1) {
            return 3;
        }
        return 2;
    }

    private boolean areAllPartitionFixedViewsSelectable(ArrayList<PartitionFixedViewInfo> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            if (!((PartitionFixedViewInfo) it.next()).isSelectable) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllItemsEnabled() {
        int i = 0;
        while (i < this.mPartitionCount) {
            if (this.mPartitions[i].mHasHeader || !areAllPartitionFixedViewsSelectable(this.mPartitions[i].mHeaderViewInfos) || !areAllPartitionFixedViewsSelectable(this.mPartitions[i].mFooterViewInfos)) {
                return false;
            }
            i++;
        }
        return true;
    }

    public boolean isEnabled(int i) {
        ensureCacheValid();
        int i2 = 0;
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
                if (i3 == -1) {
                    return false;
                }
                if (isHeaderView(i2, i3)) {
                    return ((PartitionFixedViewInfo) this.mPartitions[i2].mHeaderViewInfos.get(i3)).isSelectable;
                }
                if (!isFooterView(i2, i3)) {
                    return isEnabled(i2, i3);
                }
                return ((PartitionFixedViewInfo) this.mPartitions[i2].mFooterViewInfos.get(i3 - (this.mPartitions[i2].mCount - this.mPartitions[i2].mFooterViewsCount))).isSelectable;
            }
        }
        return false;
    }

    protected boolean isEnabled(int i, int i2) {
        return true;
    }

    public Object getItem(int i) {
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
                if (i3 == -1) {
                    return null;
                }
                if (isHeaderView(i2, i3)) {
                    return ((PartitionFixedViewInfo) this.mPartitions[i2].mHeaderViewInfos.get(i3)).data;
                }
                if (!isFooterView(i2, i3)) {
                    return getItem(i2, i3);
                }
                return ((PartitionFixedViewInfo) this.mPartitions[i2].mFooterViewInfos.get(i3 - (this.mPartitions[i2].mCount - this.mPartitions[i2].mFooterViewsCount))).data;
            }
        }
        return null;
    }

    public long getItemId(int i) {
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
                if (i3 == -1) {
                    return 0;
                }
                if (isHeaderView(i2, i3) || isFooterView(i2, i3)) {
                    return -1;
                }
                return getItemId(i2, i3);
            }
        }
        return 0;
    }

    public boolean isSelectable(int i) {
        ensureCacheValid();
        int i2 = 0;
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
                    return false;
                }
                return canSelect(i2, i3);
            }
        }
        return false;
    }

    protected boolean canSelect(int i, int i2) {
        return true;
    }

    public void setNotificationsEnabled(boolean z) {
        this.mNotificationsEnabled = z;
        if (z && this.mNotificationNeeded) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        if (this.mNotificationsEnabled) {
            this.mNotificationNeeded = false;
            super.notifyDataSetChanged();
            return;
        }
        this.mNotificationNeeded = true;
    }

    protected int getBackgroundResource(int i) {
        switch (i) {
            case 1:
                return R.drawable.mz_card_top_shade_light;
            case 2:
                return R.drawable.mz_card_middle_shade_light;
            case 3:
                return R.drawable.mz_card_bottom_shade_light;
            case 4:
                return R.drawable.mz_card_full_shade_light;
            default:
                return 0;
        }
    }

    protected void setViewBackground(View view, int i) {
        view.setBackgroundResource(getBackgroundResource(i));
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        int i2 = 0;
        ensureCacheValid();
        int i3 = 0;
        while (i2 < this.mPartitionCount) {
            int i4 = this.mPartitions[i2].mSize + i3;
            if (i < i3 || i >= i4) {
                i2++;
                i3 = i4;
            } else {
                View headerView;
                int i5 = i - i3;
                if (this.mPartitions[i2].mHasHeader) {
                    i5--;
                }
                int itemBackgroundType = getItemBackgroundType(i2, i5);
                if (i5 == -1) {
                    headerView = getHeaderView(i, i2, view, viewGroup);
                } else if (isHeaderView(i2, i5)) {
                    headerView = ((PartitionFixedViewInfo) this.mPartitions[i2].mHeaderViewInfos.get(i5)).view;
                } else if (isFooterView(i2, i5)) {
                    headerView = ((PartitionFixedViewInfo) this.mPartitions[i2].mFooterViewInfos.get(i5 - (this.mPartitions[i2].mCount - this.mPartitions[i2].mFooterViewsCount))).view;
                } else {
                    headerView = getView(i, i2, i5, itemBackgroundType, view, viewGroup);
                }
                if (headerView != null) {
                    return headerView;
                }
                throw new NullPointerException("View should not be null, partition: " + i2 + " position: " + i);
            }
        }
        throw new ArrayIndexOutOfBoundsException(i);
    }

    protected View getHeaderView(int i, int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = newHeaderView(this.mContext, i, i2, viewGroup);
        }
        bindHeaderView(view, this.mContext, i, i2);
        return view;
    }

    protected View newHeaderView(Context context, int i, int i2, ViewGroup viewGroup) {
        return null;
    }

    protected void bindHeaderView(View view, Context context, int i, int i2) {
    }
}
