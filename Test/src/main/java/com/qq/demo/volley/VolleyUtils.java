package com.qq.demo.volley;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/6/30 09:05
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public enum VolleyUtils {

    instance;

    private static RequestQueue	mQueue;
    private static ImageLoader mImageLoader;
    private static ImageLoader.ImageCache mImageCache;

    public VolleyUtils init(Context context){
        mQueue = Volley.newRequestQueue(context);
        mImageCache = new ImageCacheImpl();
        mImageLoader = new ImageLoader(mQueue,mImageCache);
        return this;
    }

    public static void requestString(String url,CallBack callBack) {
        StringRequest request = new StringRequest(url, new ResponseListener(callBack),
                new ResponseErrorListener(callBack));
        mQueue.add(request);
    }

    public static void requestJsonObj(String url, final CallBack callBack) {
        //post请求参数可以JSONObject发送
        JsonObjectRequest request = new JsonObjectRequest(url, null, new ResponseListener(callBack),
                new ResponseErrorListener(callBack));
        mQueue.add(request);
    }

    public static void requestJsonArray(String url, final CallBack callBack) {
        JsonArrayRequest request = new JsonArrayRequest(url, new ResponseListener(callBack),
                new ResponseErrorListener(callBack));

        mQueue.add(request);
    }

    public static void requestImage(String url, final CallBack callBack) {
        ImageRequest request = new ImageRequest(url, new ResponseListener(callBack), 0, 0,
                Bitmap.Config.ARGB_8888, new ResponseErrorListener(callBack));
        mQueue.add(request);
    }

    public RequestQueue getQueue() {
        return mQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public ImageLoader.ImageCache getImageCache() {
        return mImageCache;
    }

    private static class ResponseListener implements Response.Listener {

        private CallBack mCallBack;

        public ResponseListener(CallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void onResponse(Object obj) {
            if (mCallBack != null) {
                mCallBack.onResponse(obj);
            }
        }
    }

    private static class ResponseErrorListener implements Response.ErrorListener {

        private CallBack mCallBack;

        public ResponseErrorListener(CallBack callBack) {
            mCallBack = callBack;
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            if (mCallBack != null) {
                mCallBack.onResponse(volleyError);
            }
        }
    }
}
