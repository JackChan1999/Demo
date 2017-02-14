package com.google.demo3.bitmap;

import java.security.MessageDigest;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/9 07:59
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class MD5Utils {

    public static String encode(String str) throws Exception{
        byte[] hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        StringBuffer sb = new StringBuffer(hash.length*2);
        for (byte b : hash){
            if ((b & 0xff) < 0x10 ){
                sb.append("0");
            }
            sb.append(Integer.toHexString(b & 0xff));
        }
        return sb.toString();
    }
}
