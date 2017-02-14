package com.meizu.common.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.common.widget.BasePartitionAdapter.Partition;

public abstract class MultiCursorPartitionAdapter extends BasePartitionAdapter {

    public static class CursorPartition extends Partition {
        Cursor mCursor;
        int mRowIDColumnIndex;

        public CursorPartition(boolean z, boolean z2, Cursor cursor) {
            super(z, z2, cursor == null ? 0 : cursor.getCount());
            this.mCursor = cursor;
        }
    }

    protected abstract void bindView(View view, Context context, int i, int i2, Cursor cursor, int i3, int i4);

    protected abstract View newView(Context context, int i, int i2, Cursor cursor, int i3, int i4, ViewGroup viewGroup);

    public MultiCursorPartitionAdapter(Context context) {
        super(context);
    }

    public MultiCursorPartitionAdapter(Context context, int i) {
        super(context, i);
    }

    public int addPartition(boolean z, boolean z2, Cursor cursor) {
        return addPartition(new CursorPartition(z, z2, cursor));
    }

    public int addPartition(CursorPartition cursorPartition) {
        return super.addPartition(cursorPartition);
    }

    public void removePartition(int i) {
        CursorPartition partition = getPartition(i);
        Cursor cursor = partition.mCursor;
        if (!(cursor == null || cursor.isClosed())) {
            cursor.close();
            partition.mCursor = null;
        }
        super.removePartition(i);
    }

    public void clearPartitions() {
        for (int i = 0; i < this.mPartitionCount; i++) {
            CursorPartition partition = getPartition(i);
            Cursor cursor = partition.mCursor;
            if (!(cursor == null || cursor.isClosed())) {
                cursor.close();
                partition.mCursor = null;
            }
        }
        super.clearPartitions();
    }

    public void clearCursors() {
        for (int i = 0; i < this.mPartitionCount; i++) {
            CursorPartition partition = getPartition(i);
            partition.mCursor = null;
            partition.mItemCount = 0;
        }
        invalidate();
        notifyDataSetChanged();
    }

    public CursorPartition getPartition(int i) {
        return (CursorPartition) super.getPartition(i);
    }

    public Cursor getCursor(int i) {
        return getPartition(i).mCursor;
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

    public void changeCursor(int i, Cursor cursor) {
        Cursor swapCursor = swapCursor(i, cursor);
        if (swapCursor != null && !swapCursor.isClosed()) {
            swapCursor.close();
        }
    }

    public Cursor swapCursor(int i, Cursor cursor) {
        int i2 = 0;
        CursorPartition partition = getPartition(i);
        Cursor cursor2 = partition.mCursor;
        if (cursor2 == cursor) {
            return null;
        }
        partition.mCursor = cursor;
        if (cursor != null) {
            partition.mRowIDColumnIndex = cursor.getColumnIndex("_id");
            if (!cursor.isClosed()) {
                i2 = cursor.getCount();
            }
            partition.mItemCount = i2;
        } else {
            partition.mItemCount = 0;
        }
        invalidate();
        notifyDataSetChanged();
        return cursor2;
    }

    protected View getView(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup) {
        Cursor cursor = getPartition(i2).mCursor;
        if (cursor == null) {
            throw new IllegalStateException("the partition " + i2 + " cursor is null");
        }
        int dataPosition = getDataPosition(i2, i3);
        if (cursor.moveToPosition(dataPosition)) {
            View newView;
            if (view == null) {
                newView = newView(this.mContext, i, i2, cursor, i3, i4, viewGroup);
            } else {
                newView = view;
            }
            bindView(newView, this.mContext, i, i2, cursor, i3, i4);
            return newView;
        }
        throw new IllegalStateException("Couldn't move cursor to position " + dataPosition);
    }

    protected Object getItem(int i, int i2) {
        Object obj = getPartition(i).mCursor;
        if (obj == null || obj.isClosed()) {
            return null;
        }
        int dataPosition = getDataPosition(i, i2);
        if (dataPosition < 0 || !obj.moveToPosition(dataPosition)) {
            return null;
        }
        return obj;
    }

    protected long getItemId(int i, int i2) {
        CursorPartition partition = getPartition(i);
        if (partition.mRowIDColumnIndex == -1) {
            return 0;
        }
        Cursor cursor = partition.mCursor;
        if (cursor == null || cursor.isClosed()) {
            return 0;
        }
        int dataPosition = getDataPosition(i, i2);
        if (dataPosition < 0 || !cursor.moveToPosition(dataPosition)) {
            return 0;
        }
        return cursor.getLong(partition.mRowIDColumnIndex);
    }
}
