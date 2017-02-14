package com.meizu.common.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.common.widget.BasePartitionAdapter.Partition;
import java.util.Arrays;

public abstract class SingleCursorPartitionAdapter extends BasePartitionAdapter {
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 1;
    protected ChangeObserver mChangeObserver;
    protected Cursor mCursor;
    protected DataSetObserver mDataSetObserver;
    protected int mRowIDColumnIndex;

    class ChangeObserver extends ContentObserver {
        public ChangeObserver() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean z) {
            SingleCursorPartitionAdapter.this.onContentChanged();
        }
    }

    class MyDataSetObserver extends DataSetObserver {
        private MyDataSetObserver() {
        }

        public void onChanged() {
            SingleCursorPartitionAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            SingleCursorPartitionAdapter.this.notifyDataSetInvalidated();
        }
    }

    protected abstract void bindView(View view, Context context, int i, int i2, Cursor cursor, int i3, int i4);

    protected abstract View newView(Context context, int i, int i2, Cursor cursor, int i3, int i4, ViewGroup viewGroup);

    public SingleCursorPartitionAdapter(Context context, int i) {
        super(context);
        init(null, i);
    }

    public SingleCursorPartitionAdapter(Context context, Cursor cursor, int[] iArr, int i) {
        int length = (iArr == null || iArr.length <= 0) ? 10 : iArr.length;
        super(context, length);
        init(cursor, i);
        addPartitions(iArr);
    }

    private void init(Cursor cursor, int i) {
        Object obj = cursor != null ? 1 : null;
        this.mCursor = cursor;
        this.mRowIDColumnIndex = obj != null ? cursor.getColumnIndexOrThrow("_id") : -1;
        if ((i & 1) == 1) {
            this.mChangeObserver = new ChangeObserver();
            this.mDataSetObserver = new MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if (obj != null) {
            if (this.mChangeObserver != null) {
                cursor.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                cursor.registerDataSetObserver(this.mDataSetObserver);
            }
        }
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

    public void changeCursor(Cursor cursor, Partition... partitionArr) {
        Cursor swapCursor = swapCursor(cursor, partitionArr);
        if (swapCursor != null) {
            swapCursor.close();
        }
    }

    public Cursor swapCursor(Cursor cursor, Partition... partitionArr) {
        int i = 0;
        Cursor cursor2 = this.mCursor;
        if (cursor == this.mCursor) {
            cursor2 = null;
        } else {
            if (cursor2 != null) {
                if (this.mChangeObserver != null) {
                    cursor2.unregisterContentObserver(this.mChangeObserver);
                }
                if (this.mDataSetObserver != null) {
                    cursor2.unregisterDataSetObserver(this.mDataSetObserver);
                }
            }
            this.mCursor = cursor;
            if (cursor != null) {
                if (this.mChangeObserver != null) {
                    cursor.registerContentObserver(this.mChangeObserver);
                }
                if (this.mDataSetObserver != null) {
                    cursor.registerDataSetObserver(this.mDataSetObserver);
                }
                this.mRowIDColumnIndex = cursor.getColumnIndexOrThrow("_id");
            } else {
                this.mRowIDColumnIndex = -1;
            }
        }
        setNotificationsEnabled(false);
        clearPartitions();
        if (partitionArr != null && partitionArr.length > 0) {
            while (i < partitionArr.length) {
                addPartition(partitionArr[i]);
                i++;
            }
        }
        setNotificationsEnabled(true);
        return cursor2;
    }

    public void changeCursor(Cursor cursor, int... iArr) {
        Cursor swapCursor = swapCursor(cursor, iArr);
        if (swapCursor != null) {
            swapCursor.close();
        }
    }

    public Cursor swapCursor(Cursor cursor, int... iArr) {
        int i;
        Cursor cursor2 = this.mCursor;
        if (cursor == this.mCursor) {
            cursor2 = null;
        } else {
            if (cursor2 != null) {
                if (this.mChangeObserver != null) {
                    cursor2.unregisterContentObserver(this.mChangeObserver);
                }
                if (this.mDataSetObserver != null) {
                    cursor2.unregisterDataSetObserver(this.mDataSetObserver);
                }
            }
            this.mCursor = cursor;
            if (cursor != null) {
                if (this.mChangeObserver != null) {
                    cursor.registerContentObserver(this.mChangeObserver);
                }
                if (this.mDataSetObserver != null) {
                    cursor.registerDataSetObserver(this.mDataSetObserver);
                }
                this.mRowIDColumnIndex = cursor.getColumnIndexOrThrow("_id");
            } else {
                this.mRowIDColumnIndex = -1;
            }
        }
        setNotificationsEnabled(false);
        int length = iArr == null ? 0 : iArr.length;
        if (length > this.mPartitionCount) {
            i = this.mPartitionCount;
        } else {
            i = length;
        }
        for (int i2 = 0; i2 < i; i2++) {
            this.mPartitions[i2].mItemCount = iArr[i2];
        }
        if (this.mPartitionCount > i) {
            Arrays.fill(this.mPartitions, i, this.mPartitionCount, null);
            this.mPartitionCount = i;
        } else if (length > i) {
            length -= i;
            for (int i3 = 0; i3 < length; i3++) {
                addPartition(false, true, iArr[i + i3]);
            }
        }
        invalidate();
        notifyDataSetChanged();
        setNotificationsEnabled(true);
        return cursor2;
    }

    public Cursor getCursor() {
        return this.mCursor;
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

    protected Object getItem(int i, int i2) {
        if (this.mCursor == null || this.mCursor.isClosed() || this.mCursor.getCount() == 0) {
            return null;
        }
        if (this.mCursor.moveToPosition(getDataPosition(i, i2))) {
            return this.mCursor;
        }
        return null;
    }

    protected long getItemId(int i, int i2) {
        if (this.mRowIDColumnIndex == -1 || this.mCursor == null || this.mCursor.isClosed() || this.mCursor.getCount() == 0) {
            return 0;
        }
        if (this.mCursor.moveToPosition(getDataPosition(i, i2))) {
            return this.mCursor.getLong(this.mRowIDColumnIndex);
        }
        return 0;
    }

    public boolean hasStableIds() {
        return true;
    }

    protected View getView(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup) {
        if (this.mCursor == null) {
            throw new IllegalStateException("the cursor is null");
        }
        int dataPosition = getDataPosition(i2, i3);
        if (this.mCursor.moveToPosition(dataPosition)) {
            View newView;
            if (view == null) {
                newView = newView(this.mContext, i, i2, this.mCursor, i3, i4, viewGroup);
            } else {
                newView = view;
            }
            bindView(newView, this.mContext, i, i2, this.mCursor, i3, i4);
            return newView;
        }
        throw new IllegalStateException("Couldn't move cursor to position " + dataPosition);
    }

    protected void onContentChanged() {
    }
}
