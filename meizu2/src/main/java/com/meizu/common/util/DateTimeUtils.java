package com.meizu.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import cn.com.xy.sms.sdk.constant.Constant;
import com.meizu.common.R;
import com.meizu.common.widget.MzContactsContract.MzGroups;

public class DateTimeUtils {
    public static final int FORMAT_TYPE_APP_VERSIONS = 7;
    public static final int FORMAT_TYPE_CALENDAR_APPWIDGET = 8;
    public static final int FORMAT_TYPE_CALL_LOGS = 5;
    public static final int FORMAT_TYPE_CALL_LOGS_NEW = 11;
    public static final int FORMAT_TYPE_CONTACTS_BIRTHDAY_MD = 10;
    public static final int FORMAT_TYPE_CONTACTS_BIRTHDAY_YMD = 9;
    public static final int FORMAT_TYPE_EMAIL = 2;
    public static final int FORMAT_TYPE_MMS = 1;
    public static final int FORMAT_TYPE_NORMAL = 0;
    public static final int FORMAT_TYPE_PERSONAL_FOOTPRINT = 6;
    public static final int FORMAT_TYPE_RECORDER = 3;
    public static final int FORMAT_TYPE_RECORDER_PHONE = 4;
    private static String FormatResultLast = null;
    private static int FormatTypeLast = -1;
    private static final int MILLISECONDS_OF_HOUR = 3600000;
    private static long NowMillisLast = 0;
    private static Time NowTimeLast;
    private static Time ThenTimeLast;

    public static String formatTimeStampString(Context context, long j, boolean z) {
        return formatTimeStampString(context, j, 0, z);
    }

    public static String formatTimeStampString(Context context, long j, int i, boolean z) {
        return formatTimeStampString(context, j, i);
    }

    public static String formatTimeStampString(Context context, long j, int i) {
        Object obj;
        Time time;
        Time time2 = new Time();
        time2.set(j);
        Long valueOf = Long.valueOf(System.currentTimeMillis());
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        Object obj2 = i == FormatTypeLast ? 1 : null;
        FormatTypeLast = i;
        if (NowTimeLast == null) {
            obj = null;
        } else if (valueOf.longValue() < NowMillisLast || valueOf.longValue() >= NowMillisLast + 86400000) {
            obj = null;
        } else {
            int i2 = 1;
        }
        if (obj == null) {
            time = new Time();
            time.set(valueOf.longValue());
            NowTimeLast = time;
            NowMillisLast = valueOf.longValue() - ((long) (((((time.hour * 60) * 60) * 1000) + ((time.minute * 60) * 1000)) + (time.second * 1000)));
        } else {
            time = NowTimeLast;
        }
        Object obj3 = null;
        if (ThenTimeLast != null) {
            obj3 = (ThenTimeLast.year == time2.year && ThenTimeLast.yearDay == time2.yearDay) ? 1 : null;
        }
        Object obj4 = null;
        if (ThenTimeLast != null) {
            obj4 = (ThenTimeLast.year == time2.year && ThenTimeLast.month == time2.month) ? 1 : null;
        }
        ThenTimeLast = time2;
        int i3 = time.yearDay - time.weekDay;
        Object obj5 = time2.year <= time.year ? 1 : null;
        Object obj6 = (time.year != time2.year || time2.yearDay > time.yearDay) ? null : 1;
        Object obj7 = (obj6 == null || time2.yearDay != time.yearDay) ? null : 1;
        Object obj8 = (obj6 == null || time2.yearDay != time.yearDay - 1) ? null : 1;
        Object obj9 = (obj6 == null || time2.yearDay < i3 || time2.yearDay >= time.yearDay) ? null : 1;
        Resources resources = context.getResources();
        String str = "";
        str = "";
        switch (i) {
            case 0:
                if (obj7 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_hour_minute_12));
                } else if (obj9 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_week));
                    return FormatResultLast;
                } else if (obj6 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                    return FormatResultLast;
                } else if (obj5 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                } else if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                    return FormatResultLast;
                } else {
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                }
            case 1:
                if (obj7 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_hour_minute_12));
                } else if (obj9 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_week_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_week_hour_minute_12));
                } else if (obj6 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_month_day_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_month_day_hour_minute_12));
                } else if (obj5 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                } else if (is24HourFormat) {
                    return time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute));
                } else {
                    return time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute_12));
                }
            case 2:
                if (obj6 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_week_month_day_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_week_month_day_hour_minute_12));
                } else if (obj5 == null) {
                    return time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute));
                } else {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                }
            case 3:
                if (obj7 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_hour_minute_12));
                } else if (obj6 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_month_day_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_month_day_hour_minute_12));
                } else if (obj5 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                } else if (is24HourFormat) {
                    return time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute));
                } else {
                    return time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute_12));
                }
            case 4:
                if (obj7 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_hour_minute_12));
                } else if (obj6 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                    return FormatResultLast;
                } else if (obj5 != null) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                } else if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                    return FormatResultLast;
                } else {
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                    return FormatResultLast;
                }
            case 5:
                if (obj7 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_hour_minute_12));
                } else if (obj9 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_week_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_week_hour_minute_12));
                } else if (obj6 == null) {
                    if (is24HourFormat) {
                        FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute));
                    } else {
                        FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day_hour_minute_12));
                    }
                    return FormatResultLast;
                } else if (is24HourFormat) {
                    return time2.format(resources.getString(R.string.mc_pattern_month_day_hour_minute));
                } else {
                    return time2.format(resources.getString(R.string.mc_pattern_month_day_hour_minute_12));
                }
            case 6:
                if (obj7 != null) {
                    long longValue = valueOf.longValue() - j;
                    int i4;
                    if (longValue >= Constant.HOUR) {
                        i4 = ((((int) longValue) / 60) / 60) / 1000;
                        if (i4 == 1) {
                            return resources.getString(R.string.mc_pattern_a_hour_before);
                        }
                        return resources.getString(R.string.mc_pattern_hour_before).replace(MzGroups.GROUP_SPLIT_MARK_EXTRA, String.valueOf(i4));
                    }
                    i4 = (((int) longValue) / 60) / 1000;
                    if (i4 <= 1) {
                        return resources.getString(R.string.mc_pattern_a_minute_before);
                    }
                    return resources.getString(R.string.mc_pattern_minute_before).replace(MzGroups.GROUP_SPLIT_MARK_EXTRA, String.valueOf(i4));
                } else if (obj8 != null) {
                    return resources.getString(R.string.mc_pattern_yesterday);
                } else {
                    if (obj6 != null) {
                        if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                            return FormatResultLast;
                        }
                        FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                        return FormatResultLast;
                    } else if (obj5 != null) {
                        if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                            return FormatResultLast;
                        }
                        FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                        return FormatResultLast;
                    } else if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    } else {
                        FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                        return FormatResultLast;
                    }
                }
            case 7:
                if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                    return FormatResultLast;
                }
                if (obj6 != null) {
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                    return FormatResultLast;
                }
                FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                return FormatResultLast;
            case 8:
                if (time.year == time2.year) {
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                    return FormatResultLast;
                } else if (obj != null && r5 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                    return FormatResultLast;
                } else {
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month));
                    return FormatResultLast;
                }
            case 9:
                if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                    return FormatResultLast;
                }
                FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_year_month_day));
                return FormatResultLast;
            case 10:
                if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                    return FormatResultLast;
                }
                FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                return FormatResultLast;
            case 11:
                if (obj7 != null) {
                    if (is24HourFormat) {
                        return time2.format(resources.getString(R.string.mc_pattern_hour_minute));
                    }
                    return time2.format(resources.getString(R.string.mc_pattern_hour_minute_12));
                } else if (obj9 != null) {
                    return time2.format(resources.getString(R.string.mc_pattern_week));
                } else {
                    if (obj6 != null) {
                        return time2.format(resources.getString(R.string.mc_pattern_month_day));
                    }
                    if (obj != null && r4 != null && obj2 != null && !TextUtils.isEmpty(FormatResultLast)) {
                        return FormatResultLast;
                    }
                    FormatResultLast = time2.format(resources.getString(R.string.mc_pattern_month_day));
                    return FormatResultLast;
                }
            default:
                return null;
        }
    }
}
