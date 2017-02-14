package com.google.demo3.bitmapdemo;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.demo3.R;

/**
 * 自定义图片加载工具
 * 
 * @author Kevin
 * 
 */
public class MyBitmapUtils {

	NetCacheUtils mNetCacheUtils;
	LocalCacheUtils mLocalCacheUtils;
	MemoryCacheUtils mMemoryCacheUtils;

	public MyBitmapUtils() {
		mMemoryCacheUtils = new MemoryCacheUtils();
		mLocalCacheUtils = new LocalCacheUtils();
		mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils, mMemoryCacheUtils);
	}

	public void display(ImageView ivPic, String url) {
		ivPic.setImageResource(R.mipmap.ic_launcher);// 设置默认加载图片

		Bitmap bitmap = null;
		// 从内存读
		bitmap = mMemoryCacheUtils.getBitmapFromMemory(url);
		if (bitmap != null) {
			ivPic.setImageBitmap(bitmap);
			System.out.println("从内存读取图片啦...");
			return;
		}

		// 从本地读
		bitmap = mLocalCacheUtils.getBitmapFromLocal(url);
		if (bitmap != null) {
			ivPic.setImageBitmap(bitmap);
			System.out.println("从本地读取图片啦...");
			mMemoryCacheUtils.setBitmapToMemory(url, bitmap);// 将图片保存在内存
			return;
		}

		// 从网络读
		mNetCacheUtils.getBitmapFromNet(ivPic, url);
	}

}
