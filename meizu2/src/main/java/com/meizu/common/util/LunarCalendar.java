package com.meizu.common.util;

import java.security.InvalidParameterException;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class LunarCalendar {
    public static final String DATE_SEPARATOR = "-";
    private static final int[] DAYS_BEFORE_MONTH = new int[]{0, 31, 59, 90, 120, 151, 181, 212,
            243, 273, 304, 334, 365};
    public static final String LUNAR_DATE_REGEXP = "(19|20)[0-9]{2}-((0)?[1-9]|1[012])-((0)" +
            "?[1-9]|(1|2)[0-9]|30)$";
    private static final Pattern DATE_PATTERN = Pattern.compile(LUNAR_DATE_REGEXP);
    private static final int[] LUNAR_INFO = new int[]{701770, 8697535, 306771, 677704, 5580477,
            861776, 890180, 4631225, 354893, 634178, 2404022, 306762, 6966718, 675154, 861510,
            6116026, 742478, 879171, 2714935, 613195, 7642049, 300884, 674632, 5973436, 435536,
            447557, 4905656, 177741, 612162, 2398135, 300874, 6703934, 870993, 959814, 5690554,
            372046, 177732, 3749688, 601675, 8165055, 824659, 870984, 7185723, 742735, 354885,
            4894137, 154957, 601410, 2921910, 693578, 8080061, 445009, 742726, 5593787, 318030,
            678723, 3484600, 338764, 9082175, 955730, 436808, 7001404, 701775, 308805, 4871993,
            677709, 337474, 4100917, 890185, 7711422, 354897, 617798, 5549755, 306511, 675139,
            5056183, 861515, 9261759, 742482, 748103, 6909244, 613200, 301893, 4869049, 674637,
            11216322, 435540, 447561, 7002685, 702033, 612166, 5543867, 300879, 412484, 3581239,
            959818, 8827583, 371795, 702023, 5846716, 601680, 824901, 5065400, 870988, 894273,
            2468534, 354889, 8039869, 154962, 601415, 6067642, 693582, 739907, 4937015, 709962,
            9788095, 309843, 678728, 6630332, 338768, 693061, 4672185, 436812, 709953, 2415286,
            308810, 6969149, 675409, 861766, 6198074, 873293, 371267, 3585335, 617803, 11841215,
            306515, 675144, 7153084, 861519, 873028, 6138424, 744012, 355649, 2403766, 301898,
            8014782, 674641, 697670, 5984954, 447054, 711234, 3496759, 603979, 8689601, 300883,
            412488, 6726972, 959823, 436804, 4896312, 699980, 601666, 3970869, 824905, 8211133,
            870993, 894277, 5614266, 354894, 683331, 4533943, 339275, 9082303, 693587, 739911,
            7034171, 709967, 350789, 4873528, 678732, 338754, 3838902, 430921, 7809469, 436817,
            709958, 5561018, 308814, 677699, 4532024, 861770, 9343806, 873042, 895559, 6731067,
            355663, 306757, 4869817, 675148, 857409, 2986677};
    public static final int MAX_YEAR = 2099;
    public static final int MIN_YEAR = 1899;
    private static final TimeZone TZ_UTC = new SimpleTimeZone(0, "UTC");

    /**
     * 阴历转阳历
     * @param year
     * @param month
     * @param day
     * @param z
     * @return
     */
    public static final int[] lunarToSolar(int year, int month, int day, boolean z) {
        if (year < MIN_YEAR || year > MAX_YEAR || month < 1 || month > 12 || day < 1 || day > 30) {
            throw new IllegalArgumentException("Illegal lunar date, must be like that:\n\tyear : " +
                    "1900~2099\n\tmonth : 1~12\n\tday : 1~30\n\terror date:" + year + " " + month + " "
                    + day);
        }
        int i4;
        int i5 = (LUNAR_INFO[year - 1899] & 31) - 1;
        if (((LUNAR_INFO[year - 1899] & 96) >> 5) == 2) {
            i5 += 31;
        }
        for (i4 = 1; i4 < month; i4++) {
            if ((LUNAR_INFO[year - 1899] & (524288 >> (i4 - 1))) == 0) {
                i5 += 29;
            } else {
                i5 += 30;
            }
        }
        i5 += day;
        i4 = (LUNAR_INFO[year - 1899] & 15728640) >> 20;
        if (i4 != 0 && (month > i4 || (month == i4 && z))) {
            i5 = (LUNAR_INFO[year + -1899] & (524288 >> (month + -1))) == 0 ? i5 + 29 : i5 + 30;
        }
        if (i5 > 366 || (year % 4 != 0 && i5 > 365)) {
            year++;
            if (year % 4 == 1) {
                i5 -= 366;
            } else {
                i5 -= 365;
            }
        }
        int[] iArr = new int[3];
        int i6 = 1;
        while (i6 < 13) {
            i4 = DAYS_BEFORE_MONTH[i6];
            if (year % 4 == 0 && i6 > 2) {
                i4++;
            }
            if (year % 4 == 0 && i6 == 2 && i4 + 1 == i5) {
                iArr[1] = i6;
                iArr[2] = i5 - 31;
                break;
            } else if (i4 >= i5) {
                iArr[1] = i6;
                i4 = DAYS_BEFORE_MONTH[i6 - 1];
                if (year % 4 == 0 && i6 > 2) {
                    i4++;
                }
                if (i5 > i4) {
                    iArr[2] = i5 - i4;
                } else if (i5 != i4) {
                    iArr[2] = i5;
                } else if (year % 4 == 0 && i6 == 2) {
                    iArr[2] = (DAYS_BEFORE_MONTH[i6] - DAYS_BEFORE_MONTH[i6 - 1]) + 1;
                } else {
                    iArr[2] = DAYS_BEFORE_MONTH[i6] - DAYS_BEFORE_MONTH[i6 - 1];
                }
            } else {
                i6++;
            }
        }
        iArr[0] = year;
        return iArr;
    }

    public static final int[] solarToLunar(int year, int month, int day) {
        int i4 = MIN_YEAR;
        int i5 = 1;
        int[] iArr = new int[4];
        GregorianCalendar gregorianCalendar = new GregorianCalendar(MIN_YEAR, 1, 10);
        gregorianCalendar.setTimeZone(TZ_UTC);
        GregorianCalendar gregorianCalendar2 = new GregorianCalendar(year, month - 1, day);
        gregorianCalendar2.setTimeZone(TZ_UTC);
        int time = (int) ((gregorianCalendar2.getTime().getTime() - gregorianCalendar.getTime()
                .getTime()) / 86400000);
        int i6 = 0;
        while (i4 <= MAX_YEAR && time > 0) {
            i6 = daysInLunarYear(i4);
            time -= i6;
            i4++;
        }
        if (time < 0) {
            time += i6;
            i4--;
        }
        iArr[0] = i4;
        int leapMonth = leapMonth(i4);
        int i7 = 0;
        i6 = time;
        time = 1;
        while (time <= 13 && i6 > 0) {
            i7 = daysInLunarMonth(i4, time);
            i6 -= i7;
            time++;
        }
        if (i6 < 0) {
            i6 += i7;
            i4 = time - 1;
        } else {
            i4 = time;
        }
        if (leapMonth != 0 && i4 > leapMonth) {
            i4--;
            if (i4 == leapMonth) {
                time = 1;
                iArr[1] = i4;
                iArr[2] = i6 + 1;
                if (time == 0) {
                    i5 = 0;
                }
                iArr[3] = i5;
                return iArr;
            }
        }
        time = 0;
        iArr[1] = i4;
        iArr[2] = i6 + 1;
        if (time == 0) {
            i5 = 0;
        }
        iArr[3] = i5;
        return iArr;
    }

    public static final int daysInMonth(int i, int i2) {
        return daysInMonth(i, i2, false);
    }

    public static final int daysInMonth(int i, int i2, boolean z) {
        int i3;
        int leapMonth = leapMonth(i);
        if (leapMonth == 0 || i2 <= leapMonth) {
            i3 = 0;
        } else {
            i3 = 1;
        }
        if (!z) {
            return daysInLunarMonth(i, i2 + i3);
        }
        if (leapMonth == 0 || leapMonth != i2) {
            return 0;
        }
        return daysInLunarMonth(i, i2 + 1);
    }

    private static int daysInLunarYear(int i) {
        int i2 = 348;
        if (leapMonth(i) != 0) {
            i2 = 377;
        }
        int i3 = 1048448 & LUNAR_INFO[i - 1899];
        for (int i4 = 524288; i4 > 7; i4 >>= 1) {
            if ((i3 & i4) != 0) {
                i2++;
            }
        }
        return i2;
    }

    private static int daysInLunarMonth(int i, int i2) {
        if ((LUNAR_INFO[i - 1899] & (1048576 >> i2)) == 0) {
            return 29;
        }
        return 30;
    }

    public static int leapMonth(int i) {
        return (LUNAR_INFO[i - 1899] & 15728640) >> 20;
    }

    public static final boolean validateDateFormat(String str) {
        return DATE_PATTERN.matcher(str).matches();
    }

    public static final int[] parseLunarDate(String str) {
        int[] iArr = new int[3];
        if (validateDateFormat(str)) {
            String[] split = str.split(DATE_SEPARATOR);
            iArr[0] = Integer.valueOf(split[0]).intValue();
            iArr[1] = Integer.valueOf(split[1]).intValue();
            iArr[2] = Integer.valueOf(split[2]).intValue();
            return iArr;
        }
        throw new InvalidParameterException("Invalid date : " + str + " to parse. Must be from " +
                "1900-1-1 to 2099-12-31 in format YYYY-MM-DD");
    }
}
