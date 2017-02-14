package com.google.demo;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2016
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * <p/>
 * Project_Name：Demo
 * Package_Name：com.google.demo
 * Version：1.0
 * time：2016/9/19 17:22
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Utils {
    @BindingAdapter({"imageUrl"})
    public void loadImage(ImageView iv, String url){
        if (url == null){
            iv.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(iv.getContext()).load(url).into(iv);
        }
    }
}
