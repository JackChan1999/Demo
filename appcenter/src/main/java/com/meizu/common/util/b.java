package com.meizu.common.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

public class b {
    private static final int[] a = new int[]{0, 31, 59, 90, 120, Opcodes.DCMPL, Opcodes.PUTFIELD, 212, 243, 273, 304, 334, 365};
    private static final int[] b = new int[]{701770, 8697535, 306771, 677704, 5580477, 861776, 890180, 4631225, 354893, 634178, 2404022, 306762, 6966718, 675154, 861510, 6116026, 742478, 879171, 2714935, 613195, 7642049, 300884, 674632, 5973436, 435536, 447557, 4905656, 177741, 612162, 2398135, 300874, 6703934, 870993, 959814, 5690554, 372046, 177732, 3749688, 601675, 8165055, 824659, 870984, 7185723, 742735, 354885, 4894137, 154957, 601410, 2921910, 693578, 8080061, 445009, 742726, 5593787, 318030, 678723, 3484600, 338764, 9082175, 955730, 436808, 7001404, 701775, 308805, 4871993, 677709, 337474, 4100917, 890185, 7711422, 354897, 617798, 5549755, 306511, 675139, 5056183, 861515, 9261759, 742482, 748103, 6909244, 613200, 301893, 4869049, 674637, 11216322, 435540, 447561, 7002685, 702033, 612166, 5543867, 300879, 412484, 3581239, 959818, 8827583, 371795, 702023, 5846716, 601680, 824901, 5065400, 870988, 894273, 2468534, 354889, 8039869, 154962, 601415, 6067642, 693582, 739907, 4937015, 709962, 9788095, 309843, 678728, 6630332, 338768, 693061, 4672185, 436812, 709953, 2415286, 308810, 6969149, 675409, 861766, 6198074, 873293, 371267, 3585335, 617803, 11841215, 306515, 675144, 7153084, 861519, 873028, 6138424, 744012, 355649, 2403766, 301898, 8014782, 674641, 697670, 5984954, 447054, 711234, 3496759, 603979, 8689601, 300883, 412488, 6726972, 959823, 436804, 4896312, 699980, 601666, 3970869, 824905, 8211133, 870993, 894277, 5614266, 354894, 683331, 4533943, 339275, 9082303, 693587, 739911, 7034171, 709967, 350789, 4873528, 678732, 338754, 3838902, 430921, 7809469, 436817, 709958, 5561018, 308814, 677699, 4532024, 861770, 9343806, 873042, 895559, 6731067, 355663, 306757, 4869817, 675148, 857409, 2986677};
    private static final TimeZone c = new SimpleTimeZone(0, "UTC");
    private static final Pattern d = Pattern.compile("(19|20)[0-9]{2}-((0)?[1-9]|1[012])-((0)?[1-9]|(1|2)[0-9]|30)$");

    public static final int[] a(int year, int month, int monthDay, boolean isLeapMonth) {
        if (year < 1899 || year > 2099 || month < 1 || month > 12 || monthDay < 1 || monthDay > 30) {
            throw new IllegalArgumentException("Illegal lunar date, must be like that:\n\tyear : 1900~2099\n\tmonth : 1~12\n\tday : 1~30\n\terror date:" + year + " " + month + " " + monthDay);
        }
        int i;
        int dayOffset = (b[year - 1899] & 31) - 1;
        if (((b[year - 1899] & 96) >> 5) == 2) {
            dayOffset += 31;
        }
        for (i = 1; i < month; i++) {
            if ((b[year - 1899] & (524288 >> (i - 1))) == 0) {
                dayOffset += 29;
            } else {
                dayOffset += 30;
            }
        }
        dayOffset += monthDay;
        int leapMonth = (b[year - 1899] & 15728640) >> 20;
        if (leapMonth != 0 && (month > leapMonth || (month == leapMonth && isLeapMonth))) {
            dayOffset = (b[year + -1899] & (524288 >> (month + -1))) == 0 ? dayOffset + 29 : dayOffset + 30;
        }
        if (dayOffset > 366 || (year % 4 != 0 && dayOffset > 365)) {
            year++;
            if (year % 4 == 1) {
                dayOffset -= 366;
            } else {
                dayOffset -= 365;
            }
        }
        int[] solarInfo = new int[3];
        i = 1;
        while (i < 13) {
            int iPos = a[i];
            if (year % 4 == 0 && i > 2) {
                iPos++;
            }
            if (year % 4 == 0 && i == 2 && iPos + 1 == dayOffset) {
                solarInfo[1] = i;
                solarInfo[2] = dayOffset - 31;
                break;
            } else if (iPos >= dayOffset) {
                solarInfo[1] = i;
                iPos = a[i - 1];
                if (year % 4 == 0 && i > 2) {
                    iPos++;
                }
                if (dayOffset > iPos) {
                    solarInfo[2] = dayOffset - iPos;
                } else if (dayOffset != iPos) {
                    solarInfo[2] = dayOffset;
                } else if (year % 4 == 0 && i == 2) {
                    solarInfo[2] = (a[i] - a[i - 1]) + 1;
                } else {
                    solarInfo[2] = a[i] - a[i - 1];
                }
            } else {
                i++;
            }
        }
        solarInfo[0] = year;
        return solarInfo;
    }

    public static final int[] a(int year, int month, int monthDay) {
        int[] lunarDate = new int[4];
        GregorianCalendar baseCalendar = new GregorianCalendar(1899, 1, 10);
        baseCalendar.setTimeZone(c);
        GregorianCalendar objCalendar = new GregorianCalendar(year, month - 1, monthDay);
        objCalendar.setTimeZone(c);
        int offset = (int) ((objCalendar.getTime().getTime() - baseCalendar.getTime().getTime()) / 86400000);
        int daysOfYear = 0;
        int iYear = 1899;
        while (iYear <= 2099 && offset > 0) {
            daysOfYear = b(iYear);
            offset -= daysOfYear;
            iYear++;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        lunarDate[0] = iYear;
        int leapMonth = a(iYear);
        boolean isLeap = false;
        int daysOfMonth = 0;
        int iMonth = 1;
        while (iMonth <= 13 && offset > 0) {
            daysOfMonth = a(iYear, iMonth);
            offset -= daysOfMonth;
            iMonth++;
        }
        if (offset < 0) {
            offset += daysOfMonth;
            iMonth--;
        }
        if (leapMonth != 0 && iMonth > leapMonth) {
            iMonth--;
            if (iMonth == leapMonth) {
                isLeap = true;
            }
        }
        lunarDate[1] = iMonth;
        lunarDate[2] = offset + 1;
        lunarDate[3] = isLeap ? 1 : 0;
        return lunarDate;
    }

    public static final int a(int year, int month, boolean leap) {
        int leapMonth = a(year);
        int offset = 0;
        if (leapMonth != 0 && month > leapMonth) {
            offset = 1;
        }
        if (!leap) {
            return a(year, month + offset);
        }
        if (leapMonth == 0 || leapMonth != month) {
            return 0;
        }
        return a(year, month + 1);
    }

    private static int b(int year) {
        int sum = 348;
        if (a(year) != 0) {
            sum = 377;
        }
        int monthInfo = b[year - 1899] & 1048448;
        for (int i = 524288; i > 7; i >>= 1) {
            if ((monthInfo & i) != 0) {
                sum++;
            }
        }
        return sum;
    }

    private static int a(int year, int month) {
        if ((b[year - 1899] & (FileUtils.ONE_MB >> month)) == 0) {
            return 29;
        }
        return 30;
    }

    public static int a(int year) {
        return (b[year - 1899] & 15728640) >> 20;
    }
}
