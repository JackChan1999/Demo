package com.qq.demo.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class BitmapHelper {

    public static  void  display(ImageView iv , String url){
        Glide.with(UIUtils.getContext()).load(url).into(iv);
    }

}
