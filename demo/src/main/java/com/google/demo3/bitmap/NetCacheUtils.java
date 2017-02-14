package com.google.demo3.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/9 07:38
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class NetCacheUtils {

    private MemoryCAcheUtils mMemoryCAcheUtils ;
    private LocalCacheUtils mLocalCacheUtils ;

    public NetCacheUtils(MemoryCAcheUtils memoryCAcheUtils, LocalCacheUtils localCacheUtils){
        mMemoryCAcheUtils = memoryCAcheUtils ;
        mLocalCacheUtils = localCacheUtils ;
    }

    public void getBitmapFromNet(ImageView iv, String url){
        iv.setTag(url);
        new BitmapTask().execute(iv,url);
    }

    class BitmapTask extends AsyncTask<Object, Void, Bitmap>{

        private ImageView iv;
        private String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            iv = (ImageView) params[0];
            url = (String) params[1];
            return downloadBitmap(url);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                String bindUrl = (String) iv.getTag();
                if (url.equals(bindUrl)){
                    iv.setImageBitmap(bitmap);
                    mMemoryCAcheUtils.addBitmapToMemory(bitmap,url);
                    mLocalCacheUtils.addBitmapToLocal(bitmap,url);
                }
            }
        }
    }

    private Bitmap downloadBitmap(String url){
        Bitmap bitmap = null ;
        HttpURLConnection connection = null ;

        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5*1000);
            connection.setConnectTimeout(5*1000);

            if (connection.getResponseCode() == 200){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2 ;
                options.inPreferredConfig = Bitmap.Config.RGB_565 ;

                bitmap = BitmapFactory.decodeStream(connection.getInputStream(),null,options);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return bitmap ;
    }
}
