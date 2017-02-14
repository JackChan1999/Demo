package com.meizu.common.widget;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import com.meizu.common.R;
import com.meizu.common.widget.BasePartitionAdapter.Partition;

public abstract class PinnedHeaderIndexerListAdapter extends PinnedHeaderListAdapter implements SectionIndexer {
    protected Context mContext;
    private View mHeader;
    private SparseIntArray mHeaderMap;
    private int mIndexedPartition = 0;
    private SectionIndexer mIndexer;
    private int mLastSection = -1;
    private int mLastSectionOverScrollDistance = 0;
    private Placement mPlacementCache = new Placement();
    private boolean mSectionHeaderDisplayEnabled;
    private boolean mShowSectionHeaders;

    public static final class Placement {
        public boolean firstInSection;
        public boolean lastInSection;
        private int position = -1;
        public String sectionHeader;

        public void invalidate() {
            this.position = -1;
        }
    }

    public PinnedHeaderIndexerListAdapter(Context context) {
        super(context);
        this.mContext = context;
        this.mHeaderMap = new SparseIntArray(getSections().length);
    }

    public void showSectionHeaders(boolean z) {
        this.mShowSectionHeaders = z;
        invalidate();
    }

    protected View createPinnedSectionHeaderView(Context context, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.mc_pinned_header_view, viewGroup, false);
        if (inflate != null) {
            ((ImageView) inflate.findViewById(16908288)).setVisibility(8);
        }
        return inflate;
    }

    protected void setPinnedSectionHeaderView(View view, int i) {
        if (view != null) {
            ((TextView) view.findViewById(R.id.mc_header_text1)).setText((String) getSections()[i]);
        }
    }

    protected boolean isPinnedSectionHeaderVisible(int i) {
        return true;
    }

    public boolean isSectionHeaderDisplayEnabled() {
        return this.mSectionHeaderDisplayEnabled;
    }

    public void setSectionHeaderDisplayEnabled(boolean z) {
        this.mSectionHeaderDisplayEnabled = z;
    }

    public int getIndexedPartition() {
        return this.mIndexedPartition;
    }

    public void setIndexedPartition(int i) {
        this.mIndexedPartition = i;
        if (this.mShowSectionHeaders) {
            invalidate();
        }
    }

    public SectionIndexer getIndexer() {
        return this.mIndexer;
    }

    public void setIndexer(SectionIndexer sectionIndexer) {
        this.mIndexer = sectionIndexer;
        this.mPlacementCache.invalidate();
        if (this.mShowSectionHeaders) {
            invalidate();
        }
    }

    public Object[] getSections() {
        if (this.mIndexer != null) {
            return this.mIndexer.getSections();
        }
        return new String[]{" "};
    }

    public int getPositionForSection(int i) {
        int i2 = 0;
        if (this.mIndexer == null) {
            return -1;
        }
        if (i < 0) {
            return 0;
        }
        int positionForSection = this.mIndexer.getPositionForSection(i) + this.mPartitions[this.mIndexedPartition].mHeaderViewsCount;
        if (!this.mShowSectionHeaders) {
            return positionForSection;
        }
        while (i2 < i) {
            if (this.mHeaderMap.indexOfKey(i2) >= 0) {
                positionForSection++;
            }
            i2++;
        }
        return positionForSection;
    }

    public int getSectionForPosition(int i) {
        if (this.mIndexer == null) {
            return -1;
        }
        if (isFooterView(this.mIndexedPartition, i) || i > this.mPartitions[this.mIndexedPartition].mCount - 1) {
            return getSections().length - 1;
        }
        int i2 = i - this.mPartitions[this.mIndexedPartition].mHeaderViewsCount;
        if (i2 < 0) {
            return -1;
        }
        if (this.mShowSectionHeaders) {
            int i3 = 0;
            while (i3 < this.mHeaderMap.size() && this.mHeaderMap.valueAt(i3) < i) {
                i2--;
                i3++;
            }
        }
        return this.mIndexer.getSectionForPosition(i2);
    }

    public int getPinnedHeaderCount() {
        if (isSectionHeaderDisplayEnabled()) {
            return super.getPinnedHeaderCount() + 1;
        }
        return super.getPinnedHeaderCount();
    }

    public View getPinnedHeaderView(int i, View view, ViewGroup viewGroup) {
        if (!isSectionHeaderDisplayEnabled() || i != getPinnedHeaderCount() - 1) {
            return super.getPinnedHeaderView(i, view, viewGroup);
        }
        if (this.mHeader == null) {
            this.mHeader = createPinnedSectionHeaderView(this.mContext, viewGroup);
        }
        return this.mHeader;
    }

    public void configurePinnedHeaders(PinnedHeaderListView pinnedHeaderListView) {
        super.configurePinnedHeaders(pinnedHeaderListView);
        if (isSectionHeaderDisplayEnabled()) {
            int currentOverScrollDistance = pinnedHeaderListView.getCurrentOverScrollDistance();
            boolean z = currentOverScrollDistance <= 0 && pinnedHeaderListView.getFirstVisiblePosition() == 0;
            if (z && this.mLastSectionOverScrollDistance >= 0) {
                configureItemHeader(pinnedHeaderListView, getSectionPosition(0) + pinnedHeaderListView.getHeaderViewsCount(), 0, true);
            }
            this.mLastSectionOverScrollDistance = currentOverScrollDistance;
            int pinnedHeaderCount = getPinnedHeaderCount() - 1;
            if (this.mIndexer == null || getCount() == 0 || z) {
                pinnedHeaderListView.setHeaderInvisible(pinnedHeaderCount, false);
                this.mLastSection = -1;
                return;
            }
            int partitionForPosition;
            int positionAt = pinnedHeaderListView.getPositionAt(pinnedHeaderListView.getTotalTopPinnedHeaderHeight() + pinnedHeaderListView.getHeaderPaddingTop());
            int headerViewsCount = positionAt - pinnedHeaderListView.getHeaderViewsCount();
            if (pinnedHeaderListView.getChildAt(0) != null && pinnedHeaderListView.getChildAt(0).getTop() <= pinnedHeaderListView.getHeaderPaddingTop()) {
                partitionForPosition = getPartitionForPosition(headerViewsCount);
                if (partitionForPosition == this.mIndexedPartition) {
                    currentOverScrollDistance = getOffsetInPartition(headerViewsCount);
                    if (currentOverScrollDistance >= this.mPartitions[partitionForPosition].mHeaderViewsCount) {
                        partitionForPosition = getSectionForPosition(currentOverScrollDistance);
                        if (this.mLastSection > partitionForPosition) {
                            for (currentOverScrollDistance = this.mLastSection; currentOverScrollDistance > partitionForPosition; currentOverScrollDistance--) {
                                configureItemHeader(pinnedHeaderListView, getSectionPosition(currentOverScrollDistance) + pinnedHeaderListView.getHeaderViewsCount(), currentOverScrollDistance, true);
                            }
                        } else if (this.mLastSection >= partitionForPosition) {
                            for (currentOverScrollDistance = this.mLastSection + 1; currentOverScrollDistance <= partitionForPosition; currentOverScrollDistance++) {
                                configureItemHeader(pinnedHeaderListView, getSectionPosition(currentOverScrollDistance) + pinnedHeaderListView.getHeaderViewsCount(), currentOverScrollDistance, false);
                            }
                        } else if (this.mLastSection == partitionForPosition && partitionForPosition != -1 && getSectionPosition(partitionForPosition) == headerViewsCount) {
                            configureItemHeader(pinnedHeaderListView, positionAt, partitionForPosition, true);
                        }
                        this.mLastSection = partitionForPosition;
                        if (partitionForPosition == -1 && isPinnedSectionHeaderVisible(partitionForPosition)) {
                            setPinnedSectionHeaderView(this.mHeader, partitionForPosition);
                            if (headerViewsCount == getSectionPosition(partitionForPosition + 1) - 1) {
                                z = true;
                            } else {
                                z = false;
                            }
                            pinnedHeaderListView.setFadingHeader(pinnedHeaderCount, positionAt, z);
                            return;
                        }
                        pinnedHeaderListView.setHeaderInvisible(pinnedHeaderCount, false);
                    }
                }
            }
            partitionForPosition = -1;
            if (this.mLastSection > partitionForPosition) {
                for (currentOverScrollDistance = this.mLastSection; currentOverScrollDistance > partitionForPosition; currentOverScrollDistance--) {
                    configureItemHeader(pinnedHeaderListView, getSectionPosition(currentOverScrollDistance) + pinnedHeaderListView.getHeaderViewsCount(), currentOverScrollDistance, true);
                }
            } else if (this.mLastSection >= partitionForPosition) {
                configureItemHeader(pinnedHeaderListView, positionAt, partitionForPosition, true);
            } else {
                for (currentOverScrollDistance = this.mLastSection + 1; currentOverScrollDistance <= partitionForPosition; currentOverScrollDistance++) {
                    configureItemHeader(pinnedHeaderListView, getSectionPosition(currentOverScrollDistance) + pinnedHeaderListView.getHeaderViewsCount(), currentOverScrollDistance, false);
                }
            }
            this.mLastSection = partitionForPosition;
            if (partitionForPosition == -1) {
            }
            pinnedHeaderListView.setHeaderInvisible(pinnedHeaderCount, false);
        }
    }

    private int getSectionPosition(int i) {
        int positionForPartition = getPositionForPartition(this.mIndexedPartition);
        if (hasHeader(this.mIndexedPartition)) {
            positionForPartition++;
        }
        return positionForPartition + getPositionForSection(i);
    }

    protected void configureItemHeader(ListView listView, int i, int i2, boolean z) {
    }

    public Placement getItemPlacementInSection(int i) {
        if (this.mPlacementCache.position == i) {
            return this.mPlacementCache;
        }
        this.mPlacementCache.position = i;
        if (isSectionHeaderDisplayEnabled()) {
            boolean z;
            int sectionForPosition = getSectionForPosition(i);
            if (sectionForPosition == -1 || getPositionForSection(sectionForPosition) != i) {
                this.mPlacementCache.firstInSection = false;
                this.mPlacementCache.sectionHeader = null;
            } else {
                this.mPlacementCache.firstInSection = true;
                this.mPlacementCache.sectionHeader = (String) getSections()[sectionForPosition];
            }
            Placement placement = this.mPlacementCache;
            if (getPositionForSection(sectionForPosition + 1) - 1 == i) {
                z = true;
            } else {
                z = false;
            }
            placement.lastInSection = z;
        } else {
            this.mPlacementCache.firstInSection = false;
            this.mPlacementCache.lastInSection = false;
            this.mPlacementCache.sectionHeader = null;
        }
        return this.mPlacementCache;
    }

    protected int getItemBackgroundType(int i, int i2) {
        if (this.mIndexedPartition != i || i2 < 0 || this.mIndexer == null) {
            return super.getItemBackgroundType(i, i2);
        }
        int i3;
        if (isHeaderView(this.mIndexedPartition, i2)) {
            i3 = this.mPartitions[this.mIndexedPartition].mHeaderViewsCount;
            if (i3 == 1) {
                return 4;
            }
            if (i2 == 0) {
                return 1;
            }
            if (i2 == i3 - 1) {
                return 3;
            }
            return 2;
        } else if (isFooterView(this.mIndexedPartition, i2)) {
            i3 = this.mPartitions[this.mIndexedPartition].mFooterViewsCount;
            r5 = this.mPartitions[this.mIndexedPartition].mCount - i3;
            if (i3 == 1) {
                return 4;
            }
            if (i2 == r5) {
                return 1;
            }
            if (i2 - r5 == i3 - 1) {
                return 3;
            }
            return 2;
        } else {
            i3 = getSectionForPosition(i2);
            r5 = getPositionForSection(i3);
            if (i3 == getSections().length - 1) {
                i3 = getCountForPartition(i);
            } else {
                i3 = getPositionForSection(i3 + 1);
            }
            if (this.mShowSectionHeaders) {
                if (i2 == r5) {
                    return 0;
                }
                r5++;
            }
            if (i2 == r5 && i3 - r5 == 1) {
                return 4;
            }
            if (i2 == r5) {
                return 1;
            }
            if (i2 == i3 - 1) {
                return 3;
            }
            return 2;
        }
    }

    protected void ensureCacheValid() {
        if (!this.mCacheValid) {
            super.ensureCacheValid();
            ensureSectionHeaders();
        }
    }

    private void ensureSectionHeaders() {
        this.mHeaderMap.clear();
        if (this.mShowSectionHeaders && this.mIndexer != null && this.mPartitions[this.mIndexedPartition].mItemCount > 0) {
            int i = this.mPartitions[this.mIndexedPartition].mHeaderViewsCount;
            int i2 = -1;
            int i3 = 0;
            while (i3 < this.mPartitions[this.mIndexedPartition].mItemCount) {
                int sectionForPosition = this.mIndexer.getSectionForPosition(i3);
                if (i2 == sectionForPosition) {
                    break;
                }
                this.mHeaderMap.put(sectionForPosition, (i3 + i) + this.mHeaderMap.size());
                i2 = this.mIndexer.getPositionForSection(sectionForPosition + 1);
                if (i3 == i2) {
                    break;
                }
                i3 = i2;
                i2 = sectionForPosition;
            }
            i3 = this.mHeaderMap.size();
            Partition partition = this.mPartitions[this.mIndexedPartition];
            partition.mCount += i3;
            partition = this.mPartitions[this.mIndexedPartition];
            partition.mSize += i3;
            this.mCount = i3 + this.mCount;
        }
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    protected int getItemViewType(int i, int i2) {
        if (this.mIndexedPartition == i && this.mShowSectionHeaders && this.mHeaderMap.size() > 0) {
            if (this.mHeaderMap.indexOfValue(getOffsetInPartition(i2)) >= 0) {
                return getViewTypeCount() - 1;
            }
        }
        return super.getItemViewType(i, i2);
    }

    public boolean areAllItemsEnabled() {
        ensureCacheValid();
        if (!this.mShowSectionHeaders || this.mHeaderMap.size() <= 0) {
            return super.areAllItemsEnabled();
        }
        return false;
    }

    protected boolean isEnabled(int i, int i2) {
        if (this.mIndexedPartition == i && this.mShowSectionHeaders && this.mHeaderMap.indexOfValue(i2) >= 0) {
            return false;
        }
        return super.isEnabled(i, i2);
    }

    protected boolean canSelect(int i, int i2) {
        if (this.mIndexedPartition == i && this.mShowSectionHeaders && this.mHeaderMap.indexOfValue(i2) >= 0) {
            return false;
        }
        return super.canSelect(i, i2);
    }

    protected int getDataPosition(int i, int i2) {
        if (this.mIndexedPartition != i || !this.mShowSectionHeaders) {
            return super.getDataPosition(i, i2);
        }
        if (this.mHeaderMap.indexOfValue(i2) >= 0) {
            return -1;
        }
        int i3 = i2 - this.mPartitions[this.mIndexedPartition].mHeaderViewsCount;
        int i4 = 0;
        while (i4 < this.mHeaderMap.size() && this.mHeaderMap.valueAt(i4) <= i2) {
            i3--;
            i4++;
        }
        return i3;
    }

    protected View getView(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup) {
        if (this.mIndexedPartition == i2 && this.mShowSectionHeaders) {
            int indexOfValue = this.mHeaderMap.indexOfValue(i3);
            if (indexOfValue >= 0) {
                return getSectionHeaderView(i3, this.mHeaderMap.keyAt(indexOfValue), view, viewGroup);
            }
        }
        return super.getView(i, i2, i3, i4, view, viewGroup);
    }

    protected View getSectionHeaderView(int i, int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = newSectionHeaderView(this.mContext, i, i2, viewGroup);
        }
        bindSectionHeaderView(view, this.mContext, i, i2);
        return view;
    }

    protected View newSectionHeaderView(Context context, int i, int i2, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.mc_pinned_group_header, viewGroup, false);
    }

    protected void bindSectionHeaderView(View view, Context context, int i, int i2) {
        view.setVisibility(0);
        ((TextView) view.findViewById(R.id.mc_header_text1)).setText((String) getSections()[i2]);
        if (i == 0) {
            view.setMinimumHeight(context.getResources().getDimensionPixelSize(R.dimen.mz_pinned_top_header_minHeight));
        } else {
            view.setMinimumHeight(context.getResources().getDimensionPixelSize(R.dimen.mz_pinned_interval_header_minHeight));
        }
    }
}
