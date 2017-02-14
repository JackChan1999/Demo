package com.meizu.cloud.app.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.widget.TextView;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.i;
import java.util.Locale;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class g {
    public static String a(double fileSize, String... sizeUnit) {
        int nSize;
        if (fileSize < 1024.0d) {
            nSize = 0;
            if (fileSize > 0.0d) {
                nSize = (int) fileSize;
            }
            return String.format("%d" + sizeUnit[0], new Object[]{Integer.valueOf(nSize)});
        } else if (fileSize >= 1024.0d && fileSize < 10240.0d) {
            return String.format("%d" + sizeUnit[1], new Object[]{Integer.valueOf((int) (fileSize / 1024.0d))});
        } else if (fileSize >= 10240.0d && fileSize < 102400.0d) {
            return String.format("%d" + sizeUnit[1], new Object[]{Integer.valueOf((int) (fileSize / 1024.0d))});
        } else if (fileSize >= 102400.0d && fileSize < 1048576.0d) {
            nSize = (int) (fileSize / 1024.0d);
            return String.format("%d" + sizeUnit[1], new Object[]{Integer.valueOf(nSize)});
        } else if (fileSize >= 1048576.0d && fileSize < 1.048576E8d) {
            return String.format("%.1f" + sizeUnit[2], new Object[]{Double.valueOf(fileSize / 1048576.0d)});
        } else if (fileSize >= 1.048576E8d && fileSize < 1.073741824E9d) {
            return String.format("%.0f" + sizeUnit[2], new Object[]{Double.valueOf(fileSize / 1048576.0d)});
        } else if (fileSize >= 1.073741824E9d && fileSize < 1.073741824E10d) {
            return String.format("%.1f" + sizeUnit[3], new Object[]{Double.valueOf(fileSize / 1.073741824E9d)});
        } else if (fileSize < 1.073741824E10d || fileSize >= 1.073741824E11d) {
            nSize = (int) (fileSize / 1.073741824E9d);
            return String.format("%d" + sizeUnit[3], new Object[]{Integer.valueOf(nSize)});
        } else {
            return String.format("%.0f" + sizeUnit[3], new Object[]{Double.valueOf(fileSize / 1.073741824E9d)});
        }
    }

    public static String a(long counts) {
        String integerCounts = "";
        if (counts < 1000) {
            return "" + counts;
        }
        if (counts < 10000) {
            return (counts / 1000) + ",000+";
        }
        if (counts < 100000) {
            return (counts / 10000) + "0,000+";
        }
        if (counts < 1000000) {
            return (counts / 100000) + "00,000+";
        }
        if (counts < 10000000) {
            return (counts / 1000000) + ",000,000+";
        }
        if (counts < 100000000) {
            return (counts / 10000000) + "0,000,000+";
        }
        if (counts < 1000000000) {
            return (counts / 100000000) + "00,000,000+";
        }
        return "1,000,000,000+";
    }

    public static String a(String str) {
        return str.replace(String.valueOf(' '), " ").trim();
    }

    public static String b(String str) {
        return str.replaceAll("\\s", "");
    }

    public static String a(Context context, long counts) {
        return b(context, counts);
    }

    public static String b(Context context, long counts) {
        String integerCounts = "";
        Locale locale = context.getResources().getConfiguration().locale;
        String language = Locale.getDefault().toString();
        Locale HONGKONG_CHINESE = new Locale("zh", "HK");
        if (language.equals(Locale.SIMPLIFIED_CHINESE.toString()) || language.equals(Locale.TRADITIONAL_CHINESE.toString()) || language.equals(HONGKONG_CHINESE.toString())) {
            if (counts < 0) {
                return context.getString(i.less_than_one_thousand);
            } else if (counts < 1000) {
                return context.getString(i.less_than_one_thousand);
            } else if (1000 <= counts && counts < 10000) {
                range = counts / 1000;
                if (counts % (1000 * range) > 0) {
                    range++;
                }
                if (range < 10) {
                    return String.format("%d%s", new Object[]{Long.valueOf(range), context.getString(i.thousand)});
                }
                return String.format("%d%s", new Object[]{Integer.valueOf(1), context.getString(i.tenThousand)});
            } else if (10000 > counts || counts >= 100000000) {
                range = counts / 100000000;
                if (counts % (100000000 * range) > 0) {
                    range++;
                }
                return String.format("%d%s", new Object[]{Long.valueOf(range), context.getString(i.hundredMillion)});
            } else {
                range = counts / 10000;
                if (counts % (10000 * range) > 0) {
                    range++;
                }
                if (range < 10000) {
                    return String.format("%d%s", new Object[]{Long.valueOf(range), context.getString(i.tenThousand)});
                }
                return String.format("%d%s", new Object[]{Integer.valueOf(1), context.getString(i.hundredMillion)});
            }
        } else if (counts >= 1000) {
            return a(counts);
        } else {
            return context.getString(i.less_than_one_thousand);
        }
    }

    public static boolean c(String str) {
        if (Pattern.compile("[0-9]*").matcher(str).matches()) {
            return true;
        }
        return false;
    }

    public static String c(Context context, long valid_millisec) {
        long valid_second = valid_millisec / 1000;
        if (valid_second <= 0) {
            return 0 + context.getString(i.simplified_second);
        }
        String text = "";
        long day = valid_second / 86400;
        long hour = (valid_second - ((3600 * day) * 24)) / 3600;
        long min = ((valid_second - ((3600 * day) * 24)) - (3600 * hour)) / 60;
        long second = ((valid_second - ((3600 * day) * 24)) - (3600 * hour)) - (60 * min);
        if (day > 0) {
            text = text + day + context.getString(i.simplified_day);
            if (hour <= 0) {
                return text;
            }
            return text + hour + context.getString(i.simplified_hour);
        } else if (hour > 0) {
            text = text + hour + context.getString(i.simplified_hour);
            if (min <= 0) {
                return text;
            }
            return text + min + context.getString(i.simplified_min);
        } else if (min > 0) {
            text = text + min + context.getString(i.simplified_min);
            if (second <= 0) {
                return text;
            }
            return text + second + context.getString(i.simplified_second);
        } else {
            return text + second + context.getString(i.simplified_second);
        }
    }

    public static String a(double srcDecimal) {
        if (srcDecimal == ((double) ((long) srcDecimal))) {
            return String.format("%d", new Object[]{Long.valueOf((long) srcDecimal)});
        }
        return String.format("%s", new Object[]{Double.valueOf(srcDecimal)});
    }

    public static void a(Context context, AppUpdateStructItem appStructItem, TextView textView) {
        String size = a((double) appStructItem.size, context.getResources().getStringArray(b.sizeUnit));
        SpannableString spanSize = null;
        if (appStructItem.isDownload) {
            spanSize = a(String.format("%s %s", new Object[]{size, context.getText(i.update_downloaded)}), 0, size.length());
        } else if (appStructItem.patchSize > 0) {
            String patchSize = a((double) appStructItem.patchSize, context.getResources().getStringArray(b.sizeUnit));
            spanSize = a(String.format("%s %s", new Object[]{size, patchSize}), 0, size.length());
        }
        if (spanSize == null) {
            textView.setText(size);
        } else {
            textView.setText(spanSize);
        }
    }

    public static void b(Context context, AppUpdateStructItem appStructItem, TextView textView) {
        textView.setText(String.format("%s%s", new Object[]{a(context, appStructItem.download_count), context.getResources().getString(i.user_downloaded)}));
    }

    public static SpannableString a(CharSequence rawString, int start, int end) {
        SpannableString spanString = new SpannableString(rawString);
        if (!TextUtils.isEmpty(rawString)) {
            spanString.setSpan(new StrikethroughSpan(), start, end, 33);
        }
        return spanString;
    }

    public static String a(AppStructItem appStructItem, int giftCount) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("package", appStructItem.package_name);
            jsonObject.put("version", appStructItem.version_code);
            jsonObject.put("id", appStructItem.id);
            jsonObject.put("name", appStructItem.name);
            jsonObject.put("gift_count", giftCount);
            jsonObject.put("publisher", appStructItem.publisher);
            jsonObject.put("star", appStructItem.star);
            jsonObject.put("icon", appStructItem.icon);
            jsonObject.put("evaluate_count", appStructItem.evaluate_count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static long d(String detailUrl) {
        if (TextUtils.isEmpty(detailUrl)) {
            return 0;
        }
        long appId = 0;
        try {
            return Long.valueOf(detailUrl.substring(detailUrl.lastIndexOf("/") + 1)).longValue();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("format_url", "detail url : " + detailUrl);
            return appId;
        }
    }
}
