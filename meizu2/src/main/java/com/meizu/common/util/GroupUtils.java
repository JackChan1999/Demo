package com.meizu.common.util;

import android.database.Cursor;
import android.text.format.Time;
import android.util.Log;
import java.util.List;

public class GroupUtils {
    private static final int DATE_TYPE_LENGTH = 6;
    private static final int DEFAULT_DATE = -1;
    public static final int FORMER_YEAR = 5;
    public static final int FUTURE = 0;
    private static final int SPECIFIED_DATE_GROUP_LENGTH = 4;
    public static final int SPECIFIED_FUTURE = 0;
    public static final int SPECIFIED_OTHER = 3;
    public static final int SPECIFIED_THIS_MONTH = 2;
    public static final int SPECIFIED_THIS_WEEK = 1;
    public static final int THIS_MONTH = 3;
    public static final int THIS_WEEK = 2;
    public static final int THIS_YEAR = 4;
    public static final int TODAY = 1;
    public static final int TYPE_FWMO = 0;

    public static int[] getGroupCountsByCursor(Cursor cursor, int i) {
        return getGroupCountsByCursor(cursor, i, 0, cursor.getCount() - 1);
    }

    public static int[] getGroupCountsByList(List<Long> list) {
        return getGroupCountsByList(list, 0, list.size() - 1);
    }

    public static int[] getGroupCountsByCursor(Cursor cursor, int i, int i2, int i3) {
        if (i2 > i3) {
            Log.e("Error", "getGroupConntByCursor startPos > endPos error");
            return null;
        }
        int[] iArr = new int[6];
        Time time = new Time();
        Time time2 = new Time();
        long currentTimeMillis = System.currentTimeMillis();
        time2.set(currentTimeMillis);
        if (cursor.moveToPosition(i2)) {
            do {
                signDateCount(checkDateType(cursor.getLong(i), time, time2, currentTimeMillis), iArr);
                if (cursor.getPosition() == i3) {
                    break;
                }
            } while (cursor.moveToNext());
        }
        return iArr;
    }

    public static int[] getGroupCountsByList(List<Long> list, int i, int i2) {
        if (i > i2) {
            Log.e("Error", "getGroupConntByCursor startPos > endPos error");
            return null;
        }
        int[] iArr = new int[6];
        Time time = new Time();
        Time time2 = new Time();
        long currentTimeMillis = System.currentTimeMillis();
        time2.set(currentTimeMillis);
        while (i <= i2) {
            signDateCount(checkDateType(((Long) list.get(i)).longValue(), time, time2, currentTimeMillis), iArr);
            i++;
        }
        return iArr;
    }

    private static int checkDateType(long j, Time time, Time time2, long j2) {
        int i = -1;
        time.set(j);
        if (time.year < time2.year) {
            return 5;
        }
        if (j > j2) {
            i = 0;
        }
        if (time.year == time2.year) {
            i = 4;
            if (time.month == time2.month) {
                i = 3;
            }
        }
        int i2 = time2.yearDay - time2.weekDay;
        int i3 = i2 + 7;
        if (time.yearDay < i2 || time.yearDay >= i3) {
            return i;
        }
        if (time.monthDay == time2.monthDay) {
            return 1;
        }
        return 2;
    }

    private static void signDateCount(int i, int[] iArr) {
        switch (i) {
            case 0:
                iArr[0] = iArr[0] + 1;
                return;
            case 1:
                iArr[1] = iArr[1] + 1;
                return;
            case 2:
                iArr[2] = iArr[2] + 1;
                return;
            case 3:
                iArr[3] = iArr[3] + 1;
                return;
            case 4:
                iArr[4] = iArr[4] + 1;
                return;
            case 5:
                iArr[5] = iArr[5] + 1;
                return;
            default:
                return;
        }
    }

    public static int[] getSpecifiedGroupCounts(int i, Cursor cursor, int i2, int i3, int i4) {
        r0 = new int[4];
        int[] groupCountsByCursor = getGroupCountsByCursor(cursor, i2, i3, i4);
        r0[0] = groupCountsByCursor[0];
        r0[1] = groupCountsByCursor[1] + groupCountsByCursor[2];
        r0[2] = groupCountsByCursor[3];
        r0[3] = groupCountsByCursor[5] + groupCountsByCursor[4];
        return r0;
    }

    public static int[] getSpecifiedGroupCounts(int i, Cursor cursor, int i2) {
        return getSpecifiedGroupCounts(i, cursor, i2, 0, cursor.getCount() - 1);
    }

    public static int[] getSpecifiedGroupCounts(int i, List<Long> list, int i2, int i3) {
        r0 = new int[4];
        int[] groupCountsByList = getGroupCountsByList(list, i2, i3);
        r0[0] = groupCountsByList[0];
        r0[1] = groupCountsByList[1] + groupCountsByList[2];
        r0[2] = groupCountsByList[3];
        r0[3] = groupCountsByList[5] + groupCountsByList[4];
        return r0;
    }

    public static int[] getSpecifiedGroupCounts(int i, List<Long> list) {
        return getSpecifiedGroupCounts(i, list, 0, list.size() - 1);
    }
}
