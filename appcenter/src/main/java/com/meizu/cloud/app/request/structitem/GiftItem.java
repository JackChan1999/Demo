package com.meizu.cloud.app.request.structitem;

import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.b.a.i;

public class GiftItem implements Blockable {
    public int app_id;
    public String code;
    public String content;
    public String direction;
    public int height_v5 = -1;
    public String icon;
    public int id;
    public String image_v5;
    public String logo;
    public String name;
    public String package_name;
    public double price;
    public int remnant_code;
    public Tags tags;
    public int take_satus;
    public int total_code;
    public String url;
    public long valid_second;
    public int version_code;
    public int width_v5 = -1;

    public boolean isReceive() {
        return !TextUtils.isEmpty(this.code) || this.take_satus == 1;
    }

    public static String giftRemainUinitFormat(Context context, String preText, long valid_second) {
        if (valid_second < 0) {
            return context.getString(i.gift_overdue);
        }
        String text = preText;
        long day = valid_second / 86400;
        long hour = (valid_second - ((3600 * day) * 24)) / 3600;
        long min = ((valid_second - ((3600 * day) * 24)) - (3600 * hour)) / 60;
        long second = ((valid_second - ((3600 * day) * 24)) - (3600 * hour)) - (60 * min);
        if (day > 0) {
            return text + day + context.getString(i.simplified_day);
        }
        if (hour > 0) {
            return text + hour + context.getString(i.simplified_hour);
        }
        if (min > 0) {
            return text + min + context.getString(i.simplified_min);
        }
        return text + second + context.getString(i.simplified_second);
    }

    public static String giftRemainTimeFormat(Context context, long valid_second) {
        if (valid_second < 0) {
            return context.getString(i.gift_overdue);
        }
        String text = context.getString(i.gift_remain_time);
        long day = valid_second / 86400;
        if (day > 0) {
            text = text + day + context.getString(i.simplified_day);
        }
        long hour = (valid_second - ((3600 * day) * 24)) / 3600;
        if (hour > 0) {
            text = text + hour + context.getString(i.simplified_hour);
        }
        long min = ((valid_second - ((3600 * day) * 24)) - (3600 * hour)) / 60;
        if (min > 0) {
            text = text + min + context.getString(i.simplified_min);
        }
        return text + (((valid_second - ((3600 * day) * 24)) - (3600 * hour)) - (60 * min)) + context.getString(i.simplified_second);
    }

    public Class getBlockClass() {
        return getClass();
    }
}
