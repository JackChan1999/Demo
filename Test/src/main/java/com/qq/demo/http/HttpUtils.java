package com.qq.demo.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/6/25 18:13
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class HttpUtils {
    public static boolean isNetworkAvailable(Context paramContext)
    {
        if (paramContext != null)
        {
            NetworkInfo[] arrayOfNetworkInfo = ((ConnectivityManager)paramContext.
                    getSystemService(Context.CONNECTIVITY_SERVICE)).getAllNetworkInfo();
            if (arrayOfNetworkInfo != null)
                for (int i = 0; i < arrayOfNetworkInfo.length; i++)
                    if (arrayOfNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
        }
        return false;
    }

    public static boolean isWifiConnection(Context paramContext)
    {
        NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.
                getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(1);
        if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
        {
            Log.d("net", "wifi is connected!");
            return true;
        }
        return false;
    }
}
