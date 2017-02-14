package com.meizu.common.widget;

import android.content.Context;
import com.meizu.common.widget.PinnedHeaderListView.PinnedHeaderAdapter;

public abstract class PinnedHeaderListAdapter extends MultiCursorPartitionAdapter implements PinnedHeaderAdapter {
    public static final int PARTITION_HEADER_TYPE = 0;
    private boolean[] mHeaderVisibility;
    private boolean mPinnedPartitionHeadersEnabled;

    public PinnedHeaderListAdapter(Context context) {
        super(context);
    }

    public PinnedHeaderListAdapter(Context context, int i) {
        super(context, i);
    }

    public boolean getPinnedPartitionHeadersEnabled() {
        return this.mPinnedPartitionHeadersEnabled;
    }

    public void setPinnedPartitionHeadersEnabled(boolean z) {
        this.mPinnedPartitionHeadersEnabled = z;
    }

    public int getPinnedHeaderCount() {
        if (this.mPinnedPartitionHeadersEnabled) {
            return getPartitionCount();
        }
        return 0;
    }

    protected boolean isPinnedPartitionHeaderVisible(int i) {
        return this.mPinnedPartitionHeadersEnabled && hasHeader(i) && !isPartitionEmpty(i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getPinnedHeaderView(int r4, android.view.View r5, android.view.ViewGroup r6) {
        /*
        r3 = this;
        r1 = 0;
        r2 = 0;
        r0 = r3.hasHeader(r4);
        if (r0 == 0) goto L_0x0037;
    L_0x0008:
        if (r5 == 0) goto L_0x0039;
    L_0x000a:
        r0 = r5.getTag();
        r0 = (java.lang.Integer) r0;
        if (r0 == 0) goto L_0x0039;
    L_0x0012:
        r0 = r0.intValue();
        if (r0 != 0) goto L_0x0039;
    L_0x0018:
        r0 = r3.getPositionForPartition(r4);
        if (r5 != 0) goto L_0x0031;
    L_0x001e:
        r1 = r3.mContext;
        r5 = r3.newHeaderView(r1, r0, r4, r6);
        r1 = java.lang.Integer.valueOf(r2);
        r5.setTag(r1);
        r5.setFocusable(r2);
        r5.setEnabled(r2);
    L_0x0031:
        r1 = r3.mContext;
        r3.bindHeaderView(r5, r1, r0, r4);
    L_0x0036:
        return r5;
    L_0x0037:
        r5 = r1;
        goto L_0x0036;
    L_0x0039:
        r5 = r1;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.common.widget.PinnedHeaderListAdapter.getPinnedHeaderView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public void configurePinnedHeaders(PinnedHeaderListView pinnedHeaderListView) {
        if (this.mPinnedPartitionHeadersEnabled) {
            int i;
            int i2;
            int partitionCount = getPartitionCount();
            if (this.mHeaderVisibility == null || this.mHeaderVisibility.length != partitionCount) {
                this.mHeaderVisibility = new boolean[partitionCount];
            }
            for (i = 0; i < partitionCount; i++) {
                boolean isPinnedPartitionHeaderVisible = isPinnedPartitionHeaderVisible(i);
                this.mHeaderVisibility[i] = isPinnedPartitionHeaderVisible;
                if (!isPinnedPartitionHeaderVisible) {
                    pinnedHeaderListView.setHeaderInvisible(i, true);
                }
            }
            int headerViewsCount = pinnedHeaderListView.getHeaderViewsCount();
            i = 0;
            int i3 = -1;
            for (i2 = 0; i2 < partitionCount; i2++) {
                if (this.mHeaderVisibility[i2]) {
                    if (i2 > getPartitionForPosition(pinnedHeaderListView.getPositionAt(i) - headerViewsCount)) {
                        break;
                    }
                    pinnedHeaderListView.setHeaderPinnedAtTop(i2, i, false);
                    i += pinnedHeaderListView.getPinnedHeaderHeight(i2);
                    i3 = i2;
                }
            }
            int height = pinnedHeaderListView.getHeight();
            i = partitionCount;
            int i4 = partitionCount;
            partitionCount = 0;
            while (true) {
                i2 = i - 1;
                if (i2 <= i3) {
                    break;
                } else if (this.mHeaderVisibility[i2]) {
                    i = pinnedHeaderListView.getPositionAt(height - partitionCount) - headerViewsCount;
                    if (i >= 0) {
                        int partitionForPosition = getPartitionForPosition(i - 1);
                        if (partitionForPosition == -1 || i2 <= partitionForPosition) {
                            break;
                        }
                        boolean z;
                        partitionCount += pinnedHeaderListView.getPinnedHeaderHeight(i2);
                        if (i < getPositionForPartition(i2)) {
                            z = true;
                        } else {
                            z = false;
                        }
                        pinnedHeaderListView.setHeaderPinnedAtBottom(i2, height - partitionCount, z);
                        i = i2;
                        i4 = i2;
                    } else {
                        break;
                    }
                } else {
                    i = i2;
                }
            }
            for (i = i3 + 1; i < i4; i++) {
                if (this.mHeaderVisibility[i]) {
                    pinnedHeaderListView.setHeaderInvisible(i, isPartitionEmpty(i));
                }
            }
        }
    }

    public int getScrollPositionForHeader(int i) {
        return getPositionForPartition(i);
    }
}
