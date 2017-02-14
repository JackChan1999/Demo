package com.qq.demo.ui.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.qq.demo.R;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/6/23 22:50
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class AppItemHolder extends BaseHolder {

    @Bind(R.id.item_appinfo_iv_icon)
    ImageView mIvIcon;

    @Bind(R.id.item_appinfo_rb_stars)
    RatingBar mRbStars;

    @Bind(R.id.item_appinfo_tv_des)
    TextView mTvDes;

    @Bind(R.id.item_appinfo_tv_size)
    TextView mTvSize;

    @Bind(R.id.item_appinfo_tv_title)
    TextView mTvTitle;

    public AppItemHolder(Context context, View itemView, ViewGroup parent, int position) {
        super(context, itemView, parent, position);
        ButterKnife.bind(this,itemView);
    }

  /*  @Override
    public void refreshHolderView(AppInfoBean data) {
        mTvDes.setText(data.des);
        mTvSize.setText(formatFileSize(data.size,false));
        mTvTitle.setText(data.name);

        String url = Constants.URLS.IMAGEBASEURL + data.iconUrl;
        BitmapHelper.display(mIvIcon, url);
        mRbStars.setRating(data.stars);
    }*/

    /** 格式化文件大小，保留末尾的0，达到长度一致 */
    public static String formatFileSize(long len, boolean keepZero) {
        String size;
        DecimalFormat formatKeepTwoZero = new DecimalFormat("#.00");
        DecimalFormat formatKeepOneZero = new DecimalFormat("#.0");
        if (len < 1024) {
            size = String.valueOf(len + "B");
        } else if (len < 10 * 1024) {
            // [0, 10KB)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / (float) 100) + "KB";
        } else if (len < 100 * 1024) {
            // [10KB, 100KB)，保留一位小数
            size = String.valueOf(len * 10 / 1024 / (float) 10) + "KB";
        } else if (len < 1024 * 1024) {
            // [100KB, 1MB)，个位四舍五入
            size = String.valueOf(len / 1024) + "KB";
        } else if (len < 10 * 1024 * 1024) {
            // [1MB, 10MB)，保留两位小数
            if (keepZero) {
                size = String.valueOf(formatKeepTwoZero.format(len * 100 / 1024 / 1024 / (float) 100)) + "MB";
            } else {
                size = String.valueOf(len * 100 / 1024 / 1024 / (float) 100) + "MB";
            }
        } else if (len < 100 * 1024 * 1024) {
            // [10MB, 100MB)，保留一位小数
            if (keepZero) {
                size = String.valueOf(formatKeepOneZero.format(len * 10 / 1024 / 1024 / (float) 10)) + "MB";
            } else {
                size = String.valueOf(len * 10 / 1024 / 1024 / (float) 10) + "MB";
            }
        } else if (len < 1024 * 1024 * 1024) {
            // [100MB, 1GB)，个位四舍五入
            size = String.valueOf(len / 1024 / 1024) + "MB";
        } else {
            // [1GB, ...)，保留两位小数
            size = String.valueOf(len * 100 / 1024 / 1024 / 1024 / (float) 100) + "GB";
        }
        return size;
    }
}
