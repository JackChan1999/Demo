package com.qq.demo.http;

import android.os.SystemClock;

import com.qq.demo.utils.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/3/24 16:03
 * 描 述 ： Protocol基类
 * 修订历史 ：
 * ============================================================
 **/
public abstract class BaseProtocol<T> {

    public T loadData(int index) throws Throwable {

        SystemClock.sleep(300);// 休息1秒，防止加载过快，看不到界面变化效果

        // 1、读入本地缓存
        T localData = getDataFromLocal(index);
        if (localData != null) {
            return localData;
        }
        //2、发送网络请求
        String jsonString = getDataFromNet(index);

        //3、解析json
        T homeBean = parsejson(jsonString);

        return homeBean;
    }

    private T getDataFromLocal(int index) {

       /*
            //读取本地文件
			if(文件存在){
				//读取插入时间
				//判断是否过期
				if(未过期){
					//读取缓存内容
					//Json解析解析内容
					if(不为null){
						//返回并结束
					}
				}
			}
		 */

        File cacheFile = getCacheFile(index);
        if (cacheFile.exists()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheFile));
                String timeTimeMillis = reader.readLine();//读入插入时间
                if (System.currentTimeMillis() - Long.parseLong(timeTimeMillis) < Constants
                        .PROTOCOLTIMEOUT) {
                    String jsonString = reader.readLine();//读入缓存内容
                    return parsejson(jsonString);//解析json
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public String getDataFromNet(int index) throws Throwable {
        String url = Constants.URLS.BASEURL + getInterfaceKey();
        String jsonString = OkHttpUtils.get().url(url).addParams("index", index + "").build().execute().body().string();

        //因为loadData()方法是在子线程中去调用的，所以用同步方法请求网络即可，没有必要异步开辟线程加载数据
       // String jsonString = x.http().getSync(params, String.class);
        //读取网络数据
        /*
            if(网络数据加载成功){
				//加载网络数据
				//保存网络数据到本地
				//Json解析内容
				//返回并结束

			}else{
				//返回null
			}*/
        //保存网络数据到本地
        File cacheFile = getCacheFile(index);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(cacheFile));
            writer.write(System.currentTimeMillis() + "");//第一行写入插入时间
            writer.write("\r\n");//换行
            writer.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        return jsonString;

    }


    public File getCacheFile(int index) {

        String dir = FileUtils.getDir("demo");// sdcard/Android/data/包名/json
        String name = getInterfaceKey() + "." + index;// interfacekey+"."+index
        File cacheFile = new File(dir, name);
        return cacheFile;
    }

    public abstract String getInterfaceKey();

    public abstract T parsejson(String jsonString);
}
