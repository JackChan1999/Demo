package com.meizu.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.Time;
import com.meizu.common.a.h;

public class a {
    private static Time a;
    private static Time b;
    private static long c = 0;
    private static int d = -1;
    private static String e = null;

    public static String a(Context context, long when, int type) {
        boolean sameNowDay;
        Time now;
        Time then = new Time();
        then.set(when);
        Long nowmillis = Long.valueOf(System.currentTimeMillis());
        boolean is24 = DateFormat.is24HourFormat(context);
        boolean sameType = type == d;
        d = type;
        if (a == null) {
            sameNowDay = false;
        } else if (nowmillis.longValue() < c || nowmillis.longValue() >= c + 86400000) {
            sameNowDay = false;
        } else {
            sameNowDay = true;
        }
        if (sameNowDay) {
            now = a;
        } else {
            now = new Time();
            now.set(nowmillis.longValue());
            a = now;
            c = nowmillis.longValue() - ((long) (((((now.hour * 60) * 60) * 1000) + ((now.minute * 60) * 1000)) + (now.second * 1000)));
        }
        boolean sameWhenDay = false;
        if (b != null) {
            sameWhenDay = b.year == then.year && b.yearDay == then.yearDay;
        }
        boolean sameWhenMonth = false;
        if (b != null) {
            sameWhenMonth = b.year == then.year && b.month == then.month;
        }
        b = then;
        int weekStart = now.yearDay - now.weekDay;
        boolean isBeforeYear = then.year <= now.year;
        boolean isThisYear = now.year == then.year && then.yearDay <= now.yearDay;
        boolean isToday = isThisYear && then.yearDay == now.yearDay;
        boolean isYesterday = isThisYear && then.yearDay == now.yearDay - 1;
        boolean isThisWeek = isThisYear && then.yearDay >= weekStart && then.yearDay < now.yearDay;
        Resources resources = context.getResources();
        String currentTime = "";
        String currentDay = "";
        switch (type) {
            case 0:
                if (isToday) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_hour_minute_12));
                } else if (isThisWeek) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_week));
                    return e;
                } else if (isThisYear) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_month_day));
                    return e;
                } else if (isBeforeYear) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                } else if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                } else {
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                }
            case 1:
                if (isToday) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_hour_minute_12));
                } else if (isThisWeek) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_week_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_week_hour_minute_12));
                } else if (isThisYear) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_month_day_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_month_day_hour_minute_12));
                } else if (isBeforeYear) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                } else if (is24) {
                    return then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute));
                } else {
                    return then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute_12));
                }
            case 2:
                if (isThisYear) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_week_month_day_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_week_month_day_hour_minute_12));
                } else if (!isBeforeYear) {
                    return then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute));
                } else if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                } else {
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                }
            case 3:
                if (isToday) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_hour_minute_12));
                } else if (isThisYear) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_month_day_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_month_day_hour_minute_12));
                } else if (isBeforeYear) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                } else if (is24) {
                    return then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute));
                } else {
                    return then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute_12));
                }
            case 4:
                if (isToday) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_hour_minute_12));
                } else if (isThisYear) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_month_day));
                    return e;
                } else if (isBeforeYear) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                } else if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                } else {
                    e = then.format(resources.getString(h.mc_pattern_year_month_day));
                    return e;
                }
            case 5:
                if (isToday) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_hour_minute_12));
                } else if (isThisWeek) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_week_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_week_hour_minute_12));
                } else if (!isThisYear) {
                    if (is24) {
                        e = then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute));
                    } else {
                        e = then.format(resources.getString(h.mc_pattern_year_month_day_hour_minute_12));
                    }
                    return e;
                } else if (is24) {
                    return then.format(resources.getString(h.mc_pattern_month_day_hour_minute));
                } else {
                    return then.format(resources.getString(h.mc_pattern_month_day_hour_minute_12));
                }
            case 6:
                if (isToday) {
                    long offsetMilliSecounds = nowmillis.longValue() - when;
                    int returnValue;
                    if (offsetMilliSecounds >= 3600000) {
                        returnValue = ((((int) offsetMilliSecounds) / 60) / 60) / 1000;
                        if (returnValue == 1) {
                            return resources.getString(h.mc_pattern_a_hour_before);
                        }
                        return resources.getString(h.mc_pattern_hour_before).replace(",", String.valueOf(returnValue));
                    }
                    returnValue = (((int) offsetMilliSecounds) / 60) / 1000;
                    if (returnValue <= 1) {
                        return resources.getString(h.mc_pattern_a_minute_before);
                    }
                    return resources.getString(h.mc_pattern_minute_before).replace(",", String.valueOf(returnValue));
                } else if (isYesterday) {
                    return resources.getString(h.mc_pattern_yesterday);
                } else {
                    if (isThisYear) {
                        if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                            return e;
                        }
                        e = then.format(resources.getString(h.mc_pattern_month_day));
                        return e;
                    } else if (isBeforeYear) {
                        if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                            return e;
                        }
                        e = then.format(resources.getString(h.mc_pattern_year_month_day));
                        return e;
                    } else if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    } else {
                        e = then.format(resources.getString(h.mc_pattern_year_month_day));
                        return e;
                    }
                }
            case 7:
                if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                }
                if (isThisYear) {
                    e = then.format(resources.getString(h.mc_pattern_month_day));
                    return e;
                }
                e = then.format(resources.getString(h.mc_pattern_year_month_day));
                return e;
            case 8:
                if (now.year == then.year) {
                    if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                        return e;
                    }
                    e = then.format(resources.getString(h.mc_pattern_month_day));
                    return e;
                } else if (sameNowDay && sameWhenMonth && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                } else {
                    e = then.format(resources.getString(h.mc_pattern_year_month));
                    return e;
                }
            case 9:
                if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                }
                e = then.format(resources.getString(h.mc_pattern_year_month_day));
                return e;
            case 10:
                if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                }
                e = then.format(resources.getString(h.mc_pattern_month_day));
                return e;
            case 11:
                if (isToday) {
                    if (is24) {
                        return then.format(resources.getString(h.mc_pattern_hour_minute));
                    }
                    return then.format(resources.getString(h.mc_pattern_hour_minute_12));
                } else if (isThisWeek) {
                    return then.format(resources.getString(h.mc_pattern_week));
                } else if (isThisYear) {
                    return then.format(resources.getString(h.mc_pattern_month_day));
                } else if (sameNowDay && sameWhenDay && sameType && !TextUtils.isEmpty(e)) {
                    return e;
                } else {
                    e = then.format(resources.getString(h.mc_pattern_month_day));
                    return e;
                }
            default:
                return null;
        }
    }
}
