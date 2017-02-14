package com.qq.demo;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2016
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * <p/>
 * Project_Name：Demo
 * Package_Name：com.qq.demo
 * Version：1.0
 * time：2016/8/16 17:28
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mHttpClient;
    private android.os.Handler mDelivery;
    private Gson mGson;

    private OkHttpClientManager(){
        mHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url,cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
        mGson = new Gson();
        mDelivery = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance(){
        if (mInstance == null){
            synchronized (OkHttpClientManager.class){
                if (mInstance == null){
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    private Response getAsyn(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mHttpClient.newCall(request);
        return call.execute();
    }

    private String getAsString(String url) throws IOException {
        Response response = getAsyn(url);
        return response.body().string();
    }

    private void getAsyn(String url,Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                call.cancel();
                mHttpClient.dispatcher().cancelAll();
            }
        });
    }


}
