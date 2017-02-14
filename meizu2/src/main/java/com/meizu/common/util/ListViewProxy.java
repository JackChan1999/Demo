package com.meizu.common.util;

import android.content.ClipData;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.widget.AbsListView;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.meizu.common.widget.AnimCheckBox;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ListViewProxy implements InvocationHandler {
    public static final int ACTION_DRAG_FLAG_NFC_SHARE = 1;
    public static final int ACTION_DRAG_FLAG_NONE = 0;
    public static final int ACTION_TYPE_NORMAL = 0;
    public static final int ACTION_TYPE_WARNING = 1;
    private static Method mNotifyDragDropAnimType = null;
    private static Method mNotifyStatusBarNfcShareStateChanged = null;
    private static Method mStartDragMz = null;
    private static final String tag = "AbsListViewProxy";
    protected AbsListView mAbsList;
    private Method mApplyMeizuPartitionStyle;
    private Method mCheckedAll;
    private Field mChoiceActionMode;
    private Method mGetDragPosition;
    private boolean mIsFlyOS = CommonUtils.isFlymeOS();
    private Method mSetDelayTopOverScrollEnabled;
    private Method mSetDelayTopOverScrollOffset;
    private Method mSetDividerFilterListenerMethod;
    private Method mSetDividerPaddingListenerMethod;
    private Method mSetDragListenerMethod;
    private Method mSetDragSelectionListenerMethod;
    private Method mSetEnableDragSelectionMethod;
    private Method mUnCheckedAll;

    public ListViewProxy(AbsListView absListView) {
        this.mAbsList = absListView;
    }

    public Object invoke(Object obj, Method method, Object[] objArr) {
        try {
            String name = method.getName();
            long[] jArr;
            if ("onActionItemDragStart".equals(name)) {
                int onActionItemDragStart;
                if (objArr.length > 0) {
                    onActionItemDragStart = onActionItemDragStart(((Integer) objArr[0]).intValue(), ((Long) objArr[1]).longValue());
                } else {
                    jArr = new long[2];
                    getDragPostionAndId(jArr);
                    onActionItemDragStart = onActionItemDragStart((int) jArr[0], jArr[1]);
                }
                return Integer.valueOf(onActionItemDragStart);
            } else if ("onActionItemDrop".equals(name)) {
                onActionItemDrop((MenuItem) objArr[0], ((Integer) objArr[1]).intValue(), ((Long) objArr[2]).longValue());
                return null;
            } else if ("onActionItemDragEnd".equals(name)) {
                if (objArr.length > 0) {
                    onActionItemDragEnd(((Integer) objArr[0]).intValue(), ((Long) objArr[1]).longValue());
                    return null;
                }
                jArr = new long[2];
                getDragPostionAndId(jArr);
                onActionItemDragEnd((int) jArr[0], jArr[1]);
                return null;
            } else if ("getActionItemType".equals(name)) {
                return Integer.valueOf(getActionItemType((MenuItem) objArr[0]));
            } else {
                if ("onDragSelection".equals(name)) {
                    return Boolean.valueOf(onDragSelection((View) objArr[0], ((Integer) objArr[1]).intValue(), ((Long) objArr[2]).longValue()));
                }
                if ("topDividerEnabled".equals(name)) {
                    return Boolean.valueOf(topDividerEnabled());
                }
                if ("dividerEnabled".equals(name)) {
                    return Boolean.valueOf(dividerEnabled(((Integer) objArr[0]).intValue()));
                }
                if ("bottomDeviderEnabled".equals(name)) {
                    return Boolean.valueOf(bottomDeviderEnabled());
                }
                if ("getDividerPadding".equals(name)) {
                    return getDividerPadding(((Integer) objArr[0]).intValue());
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setDelayTopOverScrollEnabled(boolean z) {
        if (!this.mIsFlyOS) {
            return false;
        }
        try {
            Class cls = AbsListView.class;
            if (this.mSetDelayTopOverScrollEnabled == null) {
                this.mSetDelayTopOverScrollEnabled = cls.getDeclaredMethod("setDelayTopOverScrollEnabled", new Class[]{Boolean.TYPE});
            }
            this.mSetDelayTopOverScrollEnabled.setAccessible(true);
            this.mSetDelayTopOverScrollEnabled.invoke(this.mAbsList, new Object[]{Boolean.valueOf(z)});
            return true;
        } catch (Exception e) {
            Log.e(tag, "setDelayTopOverScrollEnabled fail to be invoked");
            this.mSetDelayTopOverScrollEnabled = null;
            return false;
        }
    }

    public boolean setDelayTopOverScrollOffset(int i) {
        if (!this.mIsFlyOS) {
            return false;
        }
        try {
            Class cls = AbsListView.class;
            if (this.mSetDelayTopOverScrollOffset == null) {
                this.mSetDelayTopOverScrollOffset = cls.getDeclaredMethod("setDelayTopOverScrollOffset", new Class[]{Integer.TYPE});
            }
            this.mSetDelayTopOverScrollOffset.setAccessible(true);
            this.mSetDelayTopOverScrollOffset.invoke(this.mAbsList, new Object[]{Integer.valueOf(i)});
            return true;
        } catch (Exception e) {
            Log.e(tag, "setDelayTopOverScrollOffset fail to be invoked");
            this.mSetDelayTopOverScrollOffset = null;
            return false;
        }
    }

    public boolean setCenterListContent(boolean z) {
        return false;
    }

    public boolean applyMeizuPartitionStyle() {
        if (!this.mIsFlyOS || !(this.mAbsList instanceof ListView)) {
            return false;
        }
        try {
            Class cls = ListView.class;
            if (this.mApplyMeizuPartitionStyle == null) {
                this.mApplyMeizuPartitionStyle = cls.getDeclaredMethod("applyMeizuPartitionStyle", new Class[0]);
            }
            this.mApplyMeizuPartitionStyle.setAccessible(true);
            this.mApplyMeizuPartitionStyle.invoke(this.mAbsList, new Object[0]);
            return true;
        } catch (Exception e) {
            Log.e(tag, "setCenterListContent fail to be invoked");
            this.mApplyMeizuPartitionStyle = null;
            return false;
        }
    }

    public boolean setEnabledMultiChoice() {
        if (this.mIsFlyOS) {
            try {
                Class cls = Class.forName("android.widget.AbsListView$OnItemDragListener");
                if (this.mSetDragListenerMethod == null) {
                    this.mSetDragListenerMethod = AbsListView.class.getMethod("setItemDragListener", new Class[]{cls});
                }
                if (createListenerInstance(cls) != null) {
                    try {
                        this.mSetDragListenerMethod.invoke(this.mAbsList, new Object[]{r3});
                        this.mAbsList.setChoiceMode(4);
                        return true;
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                        return false;
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                        return false;
                    } catch (InvocationTargetException e3) {
                        e3.printStackTrace();
                    }
                }
                return false;
            } catch (Exception e4) {
                e4.printStackTrace();
                this.mSetDragListenerMethod = null;
                return false;
            }
        }
        this.mAbsList.setChoiceMode(3);
        return true;
    }

    public boolean setEnableDragSelection(boolean z) {
        boolean z2 = true;
        if (!this.mIsFlyOS || !(this.mAbsList instanceof ListView)) {
            return false;
        }
        try {
            if (this.mSetEnableDragSelectionMethod == null) {
                this.mSetEnableDragSelectionMethod = ListView.class.getMethod("setEnableDragSelection", new Class[]{Boolean.TYPE});
            }
            try {
                this.mSetEnableDragSelectionMethod.invoke(this.mAbsList, new Object[]{Boolean.valueOf(z)});
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                z2 = false;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                z2 = false;
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
                z2 = false;
            }
            return z2;
        } catch (Exception e4) {
            e4.printStackTrace();
            this.mSetEnableDragSelectionMethod = null;
            return false;
        }
    }

    public boolean setEnableDragSelectionListener() {
        boolean z = true;
        if (!this.mIsFlyOS || !(this.mAbsList instanceof ListView)) {
            return false;
        }
        try {
            Class cls = Class.forName("android.widget.ListView$OnDragSelectListener");
            if (this.mSetDragSelectionListenerMethod == null) {
                this.mSetDragSelectionListenerMethod = ListView.class.getMethod("setEnableDragSelection", new Class[]{cls});
            }
            try {
                if (createListenerInstance(cls) != null) {
                    this.mSetDragSelectionListenerMethod.invoke(this.mAbsList, new Object[]{r2});
                } else {
                    z = false;
                }
                return z;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return false;
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            this.mSetDragSelectionListenerMethod = null;
            return false;
        }
    }

    public boolean setDividerFilterListener() {
        boolean z = true;
        if (!this.mIsFlyOS || !(this.mAbsList instanceof ListView)) {
            return false;
        }
        try {
            Class cls = Class.forName("android.widget.ListView$DividerFilter");
            if (this.mSetDividerFilterListenerMethod == null) {
                this.mSetDividerFilterListenerMethod = ListView.class.getMethod("setDividerFilterListener", new Class[]{cls});
            }
            try {
                if (createListenerInstance(cls) != null) {
                    this.mSetDividerFilterListenerMethod.invoke(this.mAbsList, new Object[]{r2});
                } else {
                    z = false;
                }
                return z;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return false;
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            this.mSetDividerFilterListenerMethod = null;
            return false;
        }
    }

    public boolean setDividerPaddingsListener() {
        boolean z = true;
        if (!this.mIsFlyOS || !(this.mAbsList instanceof ListView)) {
            return false;
        }
        try {
            Class cls = Class.forName("android.widget.ListView$DividerPadding");
            if (this.mSetDividerPaddingListenerMethod == null) {
                this.mSetDividerPaddingListenerMethod = ListView.class.getMethod("setDividerPadding", new Class[]{cls});
            }
            try {
                if (createListenerInstance(cls) != null) {
                    this.mSetDividerPaddingListenerMethod.invoke(this.mAbsList, new Object[]{r2});
                } else {
                    z = false;
                }
                return z;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
                return false;
            } catch (InvocationTargetException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
            this.mSetDividerPaddingListenerMethod = null;
            return false;
        }
    }

    public boolean checkedAll() {
        int i = 0;
        if (!this.mIsFlyOS) {
            while (i < this.mAbsList.getCount()) {
                this.mAbsList.setItemChecked(i, true);
                i++;
            }
            return true;
        } else if (this.mAbsList instanceof ListView) {
            try {
                Class cls = ListView.class;
                if (this.mCheckedAll == null) {
                    this.mCheckedAll = cls.getDeclaredMethod("checkedAll", new Class[0]);
                }
                this.mCheckedAll.setAccessible(true);
                this.mCheckedAll.invoke(this.mAbsList, new Object[0]);
                return true;
            } catch (Exception e) {
                this.mCheckedAll = null;
                Log.e(tag, "checkedAll fail to be invoked");
                return false;
            }
        } else {
            Log.e("tag", "the Target is not a ListView");
            return false;
        }
    }

    public boolean unCheckedAll() {
        if (!(this.mAbsList instanceof ListView)) {
            Log.e("tag", "unchecked error");
            return false;
        } else if (this.mIsFlyOS) {
            try {
                Class cls = ListView.class;
                if (this.mUnCheckedAll == null) {
                    this.mUnCheckedAll = cls.getDeclaredMethod("unCheckedAll", new Class[0]);
                }
                this.mUnCheckedAll.setAccessible(true);
                this.mUnCheckedAll.invoke(this.mAbsList, new Object[0]);
                return true;
            } catch (Exception e) {
                Log.e(tag, "unCheckedAll fail to be invoked");
                this.mUnCheckedAll = null;
                return false;
            }
        } else {
            this.mAbsList.clearChoices();
            this.mAbsList.setItemChecked(0, false);
            this.mAbsList.requestLayout();
            return true;
        }
    }

    public boolean finishMultiChoice() {
        if (!this.mIsFlyOS) {
            return false;
        }
        try {
            ActionMode actionMode;
            Class cls = AbsListView.class;
            if (this.mChoiceActionMode == null) {
                this.mChoiceActionMode = cls.getDeclaredField("mChoiceActionMode");
            }
            this.mChoiceActionMode.setAccessible(true);
            if (this.mChoiceActionMode.get(this.mAbsList) instanceof ActionMode) {
                actionMode = (ActionMode) this.mChoiceActionMode.get(this.mAbsList);
            } else {
                actionMode = null;
            }
            if (actionMode == null) {
                return false;
            }
            actionMode.finish();
            return true;
        } catch (Exception e) {
            Log.e(tag, "finishMultiChoice fail to be invoked");
            this.mChoiceActionMode = null;
            return false;
        }
    }

    public static boolean startDragMz(View view, ClipData clipData, DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        if (!CommonUtils.isFlymeOS()) {
            return false;
        }
        try {
            Class cls = View.class;
            if (mStartDragMz == null) {
                mStartDragMz = cls.getDeclaredMethod("startDragMz", new Class[]{ClipData.class, DragShadowBuilder.class, Object.class, Integer.TYPE});
            }
            mStartDragMz.setAccessible(true);
            mStartDragMz.invoke(view, new Object[0]);
            return true;
        } catch (Exception e) {
            Log.e(tag, "startDragMz fail to be invoked");
            mStartDragMz = null;
            return false;
        }
    }

    public static boolean notifyDragDropAnimType(View view, int i) {
        if (!CommonUtils.isFlymeOS()) {
            return false;
        }
        try {
            Class cls = View.class;
            if (mNotifyDragDropAnimType == null) {
                mNotifyDragDropAnimType = cls.getDeclaredMethod("notifyDragDropAnimType", new Class[]{Integer.TYPE});
            }
            mNotifyDragDropAnimType.setAccessible(true);
            mNotifyDragDropAnimType.invoke(view, new Object[0]);
            return true;
        } catch (Exception e) {
            Log.e(tag, "notifyDragDropAnimType fail to be invoked");
            mNotifyDragDropAnimType = null;
            return false;
        }
    }

    public static boolean notifyStatusBarNfcShareStateChanged(View view, boolean z) {
        if (!CommonUtils.isFlymeOS()) {
            return false;
        }
        try {
            Class cls = View.class;
            if (mNotifyStatusBarNfcShareStateChanged == null) {
                mNotifyStatusBarNfcShareStateChanged = cls.getDeclaredMethod("notifyStatusBarNfcShareStateChanged", new Class[]{Boolean.TYPE});
            }
            mNotifyStatusBarNfcShareStateChanged.setAccessible(true);
            mNotifyStatusBarNfcShareStateChanged.invoke(view, new Object[0]);
            return true;
        } catch (Exception e) {
            Log.e(tag, "notifyStatusBarNfcShareStateChanged fail to be invoked");
            mNotifyStatusBarNfcShareStateChanged = null;
            return false;
        }
    }

    public static boolean setItemForChecked(AbsListView absListView, View view) {
        boolean z = VERSION.SDK_INT >= 21;
        if (view == null || (CommonUtils.isFlymeOS() && z)) {
            return false;
        }
        if (absListView.getChoiceMode() == 3) {
            View findViewById = view.findViewById(16908289);
            if (findViewById != null && (findViewById instanceof Checkable)) {
                if (findViewById instanceof AnimCheckBox) {
                    ((AnimCheckBox) findViewById).setIsAnimation(true);
                }
                if (absListView.getCheckedItemCount() > 0) {
                    ((Checkable) findViewById).setChecked(true);
                } else {
                    ((Checkable) findViewById).setChecked(false);
                }
                return true;
            }
        }
        return false;
    }

    private Object createListenerInstance(Class<?> cls) {
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, this);
    }

    private void getDragPostionAndId(long[] jArr) {
        if (this.mAbsList != null) {
            try {
                if (this.mGetDragPosition == null) {
                    this.mGetDragPosition = AbsListView.class.getMethod("getDragPosition", new Class[0]);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                this.mGetDragPosition = null;
            }
            ListAdapter listAdapter = (ListAdapter) this.mAbsList.getAdapter();
            if (this.mGetDragPosition != null && listAdapter != null) {
                Object invoke;
                try {
                    invoke = this.mGetDragPosition.invoke(this.mAbsList, new Object[0]);
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                    invoke = null;
                } catch (IllegalAccessException e3) {
                    e3.printStackTrace();
                    invoke = null;
                } catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                    invoke = null;
                }
                if (invoke instanceof Integer) {
                    int intValue = ((Integer) invoke).intValue();
                    long itemId = ((ListAdapter) this.mAbsList.getAdapter()).getItemId(intValue);
                    jArr[0] = (long) intValue;
                    jArr[1] = itemId;
                }
            }
        }
    }

    protected int onActionItemDragStart(int i, long j) {
        return 0;
    }

    protected void onActionItemDrop(MenuItem menuItem, int i, long j) {
    }

    protected void onActionItemDragEnd(int i, long j) {
    }

    protected int getActionItemType(MenuItem menuItem) {
        return 0;
    }

    protected boolean onDragSelection(View view, int i, long j) {
        return false;
    }

    protected boolean topDividerEnabled() {
        return true;
    }

    protected boolean dividerEnabled(int i) {
        return true;
    }

    protected boolean bottomDeviderEnabled() {
        return true;
    }

    public int[] getDividerPadding(int i) {
        return null;
    }
}
