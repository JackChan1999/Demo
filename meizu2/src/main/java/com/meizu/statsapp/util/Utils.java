package com.meizu.statsapp.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.amap.api.location.LocationManagerProxy;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.common.widget.MzContactsContract.MzGroups;
import com.ted.sdk.ivr.DialpadAction;
import defpackage.anz;
import defpackage.aov;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Stack;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;
import sdk.meizu.traffic.auth.MzAccountManager;

public class Utils {
    private static final char[] DIGITS = new char[]{DialpadAction.ZERO, DialpadAction.ONE, DialpadAction.TWO, DialpadAction.THREE, DialpadAction.FOUR, DialpadAction.FIVE, DialpadAction.SIX, DialpadAction.SEVEN, DialpadAction.EIGHT, DialpadAction.NINE, 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String TAG = "Utils";
    public static String sBUILD_MASK;
    private static volatile String sIMEI;
    private static volatile String sMACAddress;
    public static String sPRODUCT_MODEL;
    private static volatile String sSN;

    public static long getDataDirAvailableSize() {
        if (VERSION.SDK_INT >= 18) {
            return new StatFs(Environment.getDataDirectory().getAbsolutePath()).getAvailableBytes();
        }
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public static String getCountry(Context context) {
        if (context == null || context.getResources().getConfiguration() == null || context.getResources().getConfiguration().locale == null) {
            return "";
        }
        return context.getResources().getConfiguration().locale.getCountry();
    }

    public static String getIMEI(Context context) {
        if (!isEmpty(sIMEI)) {
            return sIMEI;
        }
        String str;
        try {
            Object a = aov.a("android.telephony.MzTelephonyManager", "getDeviceId", null);
            if (a != null && (a instanceof String)) {
                str = (String) a;
                if (!isEmpty(str)) {
                    sIMEI = str;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager == null) {
                    return "";
                }
                str = telephonyManager.getDeviceId();
                if (!isEmpty(str)) {
                    sIMEI = str;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return sIMEI;
    }

    public static String getSN() {
        if (!isEmpty(sSN)) {
            return sSN;
        }
        String property = getProperty("ro.serialno");
        if (isEmpty(property)) {
            return property;
        }
        sSN = property;
        return property;
    }

    public static String getFlymeUid(Context context) {
        try {
            Account[] accountsByType = ((AccountManager) context.getSystemService(MzAccountManager.PATH_ACCOUNT)).getAccountsByType("com.meizu.account");
            if (!(accountsByType == null || accountsByType.length <= 0 || accountsByType[0] == null)) {
                return accountsByType[0].name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getOperater(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null || 5 != telephonyManager.getSimState()) {
            return "";
        }
        return telephonyManager.getSimOperator();
    }

    public static boolean isWiFiWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        if (networkInfo == null || State.CONNECTED != networkInfo.getState()) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        if ((networkInfo == null || State.CONNECTED != networkInfo.getState()) && (networkInfo2 == null || State.CONNECTED != networkInfo2.getState())) {
            return false;
        }
        return true;
    }

    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager != null) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                    return "off";
                }
                if (activeNetworkInfo.getType() == 1) {
                    return "wifi";
                }
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
                if (telephonyManager.getNetworkType() == 1 || telephonyManager.getNetworkType() == 2) {
                    return "2g";
                }
                if (telephonyManager.getNetworkType() == 13) {
                    return "4g";
                }
                return "3g";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EnvironmentCompat.MEDIA_UNKNOWN;
    }

    public static boolean isRoot(Context context) {
        Object systemService = context.getSystemService("device_states");
        if (systemService == null) {
            systemService = context.getSystemService("deivce_states");
            if (systemService == null) {
                return false;
            }
        }
        try {
            Method method = systemService.getClass().getMethod("doCheckState", new Class[]{Integer.TYPE});
            if (method == null) {
                return false;
            }
            method.setAccessible(true);
            Integer num = (Integer) method.invoke(systemService, new Object[]{Integer.valueOf(1)});
            if (num != null && 1 == num.intValue()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean ensureDir(File file) {
        int i = 0;
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return true;
        }
        while (i < 3 && !file.mkdirs()) {
            i++;
        }
        return file.exists();
    }

    public static byte[] getFileMd5(String str) {
        if (isEmpty(str)) {
            return null;
        }
        return getFileMd5(new File(str));
    }

    public static byte[] getFileMd5(File file) {
        FileInputStream fileInputStream;
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        NoSuchAlgorithmException e3;
        byte[] bArr = null;
        if (file != null && file.exists()) {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    MessageDigest instance = MessageDigest.getInstance("MD5");
                    byte[] bArr2 = new byte[524288];
                    while (true) {
                        int read = fileInputStream.read(bArr2);
                        if (read == -1) {
                            break;
                        }
                        instance.update(bArr2, 0, read);
                    }
                    bArr = instance.digest();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e5) {
                    e2 = e5;
                    try {
                        e2.printStackTrace();
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e42) {
                                e42.printStackTrace();
                            }
                        }
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e422) {
                                e422.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (NoSuchAlgorithmException e6) {
                    e3 = e6;
                    e3.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e4222) {
                            e4222.printStackTrace();
                        }
                    }
                    return bArr;
                } catch (IOException e7) {
                    e4222 = e7;
                    e4222.printStackTrace();
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e42222) {
                            e42222.printStackTrace();
                        }
                    }
                    return bArr;
                }
            } catch (FileNotFoundException e8) {
                e2 = e8;
                fileInputStream = bArr;
                e2.printStackTrace();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return bArr;
            } catch (NoSuchAlgorithmException e9) {
                e3 = e9;
                fileInputStream = bArr;
                e3.printStackTrace();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return bArr;
            } catch (IOException e10) {
                e42222 = e10;
                fileInputStream = bArr;
                e42222.printStackTrace();
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                return bArr;
            } catch (Throwable th3) {
                fileInputStream = bArr;
                th = th3;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        }
        return bArr;
    }

    public static byte[] getMD5(byte[] bArr) {
        byte[] bArr2 = null;
        if (bArr != null && bArr.length >= 1) {
            try {
                bArr2 = MessageDigest.getInstance("MD5").digest(bArr);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return bArr2;
    }

    public static byte[] gzip(byte[] bArr) {
        IOException e;
        Throwable th;
        byte[] bArr2 = null;
        if (bArr != null) {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream;
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream.write(bArr, 0, bArr.length);
                    gZIPOutputStream.flush();
                    if (gZIPOutputStream != null) {
                        try {
                            gZIPOutputStream.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    bArr2 = byteArrayOutputStream.toByteArray();
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                    }
                } catch (IOException e4) {
                    e3 = e4;
                    try {
                        e3.printStackTrace();
                        if (gZIPOutputStream != null) {
                            try {
                                gZIPOutputStream.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                            }
                        }
                        return bArr2;
                    } catch (Throwable th2) {
                        th = th2;
                        if (gZIPOutputStream != null) {
                            try {
                                gZIPOutputStream.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            } catch (IOException e5) {
                e322 = e5;
                gZIPOutputStream = null;
                e322.printStackTrace();
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                return bArr2;
            } catch (Throwable th3) {
                gZIPOutputStream = null;
                th = th3;
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                throw th;
            }
        }
        return bArr2;
    }

    public static String bytesToHexString(byte[] bArr) {
        int i = 0;
        if (bArr == null) {
            return "";
        }
        char[] cArr = new char[(bArr.length * 2)];
        int length = bArr.length;
        int i2 = 0;
        while (i < length) {
            byte b = bArr[i];
            int i3 = i2 + 1;
            cArr[i2] = DIGITS[(b >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = DIGITS[b & 15];
            i++;
        }
        return new String(cArr);
    }

    public static String getCpuUsage() {
        return String.valueOf((int) getProcessCpuRate());
    }

    public static String getMemInfo(String str) {
        return executeCmd("dumpsys meminfo " + str);
    }

    public static String getCpuInfo() {
        return executeCmd("top -d 1 -n 1");
    }

    public static String dumpKernalLog() {
        StringBuilder stringBuilder = new StringBuilder(1024);
        stringBuilder.append("\n\n----------Kernal----------\n");
        stringBuilder.append(executeCmd("dmesg"));
        return stringBuilder.toString();
    }

    public static String getMemUsage() {
        String executeCmd = executeCmd("cat /proc/meminfo");
        String valueOf = String.valueOf(-1);
        if (executeCmd != null) {
            String line = getLine(executeCmd, 1);
            executeCmd = getLine(executeCmd, 2);
            if (!(line == null || executeCmd == null)) {
                String[] split = line.split(" +");
                String[] split2 = executeCmd.split(" +");
                if (split != null && split.length >= 3 && split2 != null && split2.length >= 3) {
                    try {
                        long parseLong = Long.parseLong(split[1]);
                        long parseLong2 = Long.parseLong(split2[1]);
                        if (parseLong != 0) {
                            valueOf = String.valueOf((int) (((((double) parseLong) - (((double) parseLong2) * 1.0d)) * 100.0d) / ((double) parseLong)));
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return valueOf;
    }

    public static String getSamsungKernalBootReason() {
        String executeCmd = executeCmd("cat /proc/reset_reason");
        int indexOf = executeCmd.indexOf("Last boot from: ");
        int indexOf2 = executeCmd.indexOf(MzGroups.GROUP_SPLIT_MARK_EXTRA);
        if (-1 == indexOf || -1 == indexOf2 || indexOf >= indexOf2) {
            return null;
        }
        return executeCmd.substring(indexOf, indexOf2);
    }

    private static String getLine(String str, int i) {
        int i2 = 0;
        if (str == null || i < 1) {
            return null;
        }
        int i3 = -1;
        int i4 = 0;
        while (i4 < str.length() && i2 < i) {
            i3 = str.indexOf(10, i4);
            if (-1 == i3) {
                break;
            } else if (i2 == i - 1) {
                return i3 > i4 ? str.substring(i4, i3 - 1) : null;
            } else {
                i4 = i3 + 1;
                i2++;
            }
        }
        if (i2 == i - 1 && -1 == r1 && i4 < str.length()) {
            return str.substring(i4);
        }
        return null;
    }

    public static String executeCmd(String str) {
        Process process;
        InputStreamReader inputStreamReader;
        Exception exception;
        Throwable th;
        Process process2;
        Throwable th2;
        if (str == null || str.length() <= 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        Process exec;
        InputStreamReader inputStreamReader2;
        try {
            exec = Runtime.getRuntime().exec(str);
            if (exec != null) {
                try {
                    inputStreamReader2 = new InputStreamReader(exec.getInputStream());
                } catch (Exception e) {
                    Exception exception2 = e;
                    process = exec;
                    inputStreamReader = null;
                    exception = exception2;
                    try {
                        exception.printStackTrace();
                        if (inputStreamReader != null) {
                            try {
                                inputStreamReader.close();
                            } catch (IOException e2) {
                            }
                        }
                        if (process != null) {
                            process.destroy();
                        }
                        return stringBuilder.toString();
                    } catch (Throwable th3) {
                        th = th3;
                        process2 = process;
                        inputStreamReader2 = inputStreamReader;
                        exec = process2;
                        if (inputStreamReader2 != null) {
                            try {
                                inputStreamReader2.close();
                            } catch (IOException e3) {
                            }
                        }
                        if (exec != null) {
                            exec.destroy();
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th2 = th4;
                    inputStreamReader2 = null;
                    th = th2;
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                    }
                    if (exec != null) {
                        exec.destroy();
                    }
                    throw th;
                }
                try {
                    char[] cArr = new char[2048];
                    while (true) {
                        int read = inputStreamReader2.read(cArr);
                        if (read <= 0) {
                            break;
                        }
                        stringBuilder.append(cArr, 0, read);
                    }
                } catch (Exception e4) {
                    exception = e4;
                    process2 = exec;
                    inputStreamReader = inputStreamReader2;
                    process = process2;
                    exception.printStackTrace();
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (process != null) {
                        process.destroy();
                    }
                    return stringBuilder.toString();
                } catch (Throwable th5) {
                    th = th5;
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                    }
                    if (exec != null) {
                        exec.destroy();
                    }
                    throw th;
                }
            }
            inputStreamReader2 = null;
            if (inputStreamReader2 != null) {
                try {
                    inputStreamReader2.close();
                } catch (IOException e5) {
                }
            }
            if (exec != null) {
                exec.destroy();
            }
        } catch (Exception e6) {
            inputStreamReader = null;
            exception = e6;
            Object obj = null;
            exception.printStackTrace();
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (process != null) {
                process.destroy();
            }
            return stringBuilder.toString();
        } catch (Throwable th42) {
            exec = null;
            th2 = th42;
            inputStreamReader2 = null;
            th = th2;
            if (inputStreamReader2 != null) {
                inputStreamReader2.close();
            }
            if (exec != null) {
                exec.destroy();
            }
            throw th;
        }
        return stringBuilder.toString();
    }

    public static String getMACAddress(Context context) {
        if (!isEmpty(sMACAddress)) {
            return sMACAddress;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.meizu.stats", 0);
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager != null) {
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            String macAddress = connectionInfo == null ? null : connectionInfo.getMacAddress();
            if (!isEmpty(macAddress)) {
                sMACAddress = macAddress;
                sharedPreferences.edit().putString("mac_address", sMACAddress).apply();
            }
        }
        if (TextUtils.isEmpty(sMACAddress)) {
            return sharedPreferences.getString("mac_address", sMACAddress);
        }
        return sMACAddress;
    }

    public static String getLocation(Context context) {
        if (context == null) {
            return null;
        }
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (locationManager == null) {
                return null;
            }
            if (locationManager.getLastKnownLocation(LocationManagerProxy.NETWORK_PROVIDER) == null) {
                return null;
            }
            return String.format("%.6f, %.6f", new Object[]{Double.valueOf(locationManager.getLastKnownLocation(LocationManagerProxy.NETWORK_PROVIDER).getLatitude()), Double.valueOf(locationManager.getLastKnownLocation(LocationManagerProxy.NETWORK_PROVIDER).getLongitude())});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCellId(Context context) {
        if (context == null) {
            return null;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager == null) {
            return null;
        }
        if (telephonyManager.getPhoneType() == 1) {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            if (gsmCellLocation != null) {
                return "cid: " + gsmCellLocation.getCid() + ", lac: " + gsmCellLocation.getLac();
            }
        }
        return null;
    }

    public static boolean zipFile(File file, File file2) {
        if (file == null || file2 == null || !file.exists()) {
            return false;
        }
        if (file2.exists()) {
            file2.delete();
        }
        if (file.isDirectory()) {
            return zipDir(file, file2);
        }
        return gZipFile(file, file2);
    }

    private static boolean gZipFile(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        IOException e;
        GZIPOutputStream gZIPOutputStream;
        FileOutputStream fileOutputStream2;
        Throwable th;
        FileInputStream fileInputStream2 = null;
        GZIPOutputStream gZIPOutputStream2;
        try {
            file2.createNewFile();
            fileOutputStream = new FileOutputStream(file2);
            try {
                gZIPOutputStream2 = new GZIPOutputStream(new BufferedOutputStream(fileOutputStream));
                try {
                    fileInputStream = new FileInputStream(file);
                    try {
                        byte[] bArr = new byte[1048576];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            gZIPOutputStream2.write(bArr, 0, read);
                        }
                        gZIPOutputStream2.flush();
                        if (gZIPOutputStream2 != null) {
                            try {
                                gZIPOutputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        return true;
                    } catch (IOException e3) {
                        e = e3;
                        gZIPOutputStream = gZIPOutputStream2;
                        fileOutputStream2 = fileOutputStream;
                        try {
                            e.printStackTrace();
                            if (gZIPOutputStream != null) {
                                try {
                                    gZIPOutputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException e42) {
                                    e42.printStackTrace();
                                }
                            }
                            if (fileInputStream == null) {
                                return false;
                            }
                            try {
                                fileInputStream.close();
                                return false;
                            } catch (IOException e422) {
                                e422.printStackTrace();
                                return false;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream2;
                            gZIPOutputStream2 = gZIPOutputStream;
                            fileInputStream2 = fileInputStream;
                            if (gZIPOutputStream2 != null) {
                                try {
                                    gZIPOutputStream2.close();
                                } catch (IOException e4222) {
                                    e4222.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e42222) {
                                    e42222.printStackTrace();
                                }
                            }
                            if (fileInputStream2 != null) {
                                try {
                                    fileInputStream2.close();
                                } catch (IOException e422222) {
                                    e422222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream2 = fileInputStream;
                        if (gZIPOutputStream2 != null) {
                            gZIPOutputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e422222 = e5;
                    fileInputStream = null;
                    gZIPOutputStream = gZIPOutputStream2;
                    fileOutputStream2 = fileOutputStream;
                    e422222.printStackTrace();
                    if (gZIPOutputStream != null) {
                        gZIPOutputStream.close();
                    }
                    if (fileOutputStream2 != null) {
                        fileOutputStream2.close();
                    }
                    if (fileInputStream == null) {
                        return false;
                    }
                    fileInputStream.close();
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    if (gZIPOutputStream2 != null) {
                        gZIPOutputStream2.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    throw th;
                }
            } catch (IOException e6) {
                e422222 = e6;
                fileInputStream = null;
                fileOutputStream2 = fileOutputStream;
                e422222.printStackTrace();
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                if (fileInputStream == null) {
                    return false;
                }
                fileInputStream.close();
                return false;
            } catch (Throwable th5) {
                th = th5;
                gZIPOutputStream2 = null;
                if (gZIPOutputStream2 != null) {
                    gZIPOutputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                throw th;
            }
        } catch (IOException e7) {
            e422222 = e7;
            fileInputStream = null;
            fileOutputStream2 = null;
            e422222.printStackTrace();
            if (gZIPOutputStream != null) {
                gZIPOutputStream.close();
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            if (fileInputStream == null) {
                return false;
            }
            fileInputStream.close();
            return false;
        } catch (Throwable th6) {
            th = th6;
            gZIPOutputStream2 = null;
            fileOutputStream = null;
            if (gZIPOutputStream2 != null) {
                gZIPOutputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            throw th;
        }
    }

    private static boolean zipDir(File file, File file2) {
        String str;
        FileOutputStream fileOutputStream;
        IOException e;
        ZipOutputStream zipOutputStream;
        FileOutputStream fileOutputStream2;
        Throwable th;
        ZipOutputStream zipOutputStream2 = null;
        Stack stack = new Stack();
        String parent = file.getParent();
        if (parent == null) {
            str = MzGroups.GROUP_SPLIT_MARK_SLASH;
        } else {
            str = parent;
        }
        try {
            file2.createNewFile();
            fileOutputStream = new FileOutputStream(file2);
            try {
                ZipOutputStream zipOutputStream3 = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
                try {
                    if (zipDir(stack, str, file, zipOutputStream3)) {
                        do {
                            if (stack.size() <= 0) {
                                if (zipOutputStream3 != null) {
                                    try {
                                        zipOutputStream3.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                return true;
                            }
                        } while (zipDir(stack, str, (File) stack.pop(), zipOutputStream3));
                        if (zipOutputStream3 != null) {
                            try {
                                zipOutputStream3.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2222) {
                                e2222.printStackTrace();
                            }
                        }
                        return false;
                    }
                    if (zipOutputStream3 != null) {
                        try {
                            zipOutputStream3.close();
                        } catch (IOException e22222) {
                            e22222.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e222222) {
                            e222222.printStackTrace();
                        }
                    }
                    return false;
                } catch (IOException e3) {
                    e222222 = e3;
                    zipOutputStream = zipOutputStream3;
                    fileOutputStream2 = fileOutputStream;
                } catch (Throwable th2) {
                    th = th2;
                    zipOutputStream2 = zipOutputStream3;
                }
            } catch (IOException e4) {
                e222222 = e4;
                zipOutputStream = null;
                fileOutputStream2 = fileOutputStream;
                try {
                    e222222.printStackTrace();
                    if (zipOutputStream != null) {
                        try {
                            zipOutputStream.close();
                        } catch (IOException e2222222) {
                            e2222222.printStackTrace();
                        }
                    }
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e22222222) {
                            e22222222.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    zipOutputStream2 = zipOutputStream;
                    if (zipOutputStream2 != null) {
                        try {
                            zipOutputStream2.close();
                        } catch (IOException e5) {
                            e5.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e52) {
                            e52.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                if (zipOutputStream2 != null) {
                    zipOutputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (IOException e6) {
            e22222222 = e6;
            zipOutputStream = null;
            e22222222.printStackTrace();
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            return false;
        } catch (Throwable th5) {
            th = th5;
            fileOutputStream = null;
            if (zipOutputStream2 != null) {
                zipOutputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zipDir(Stack<File> r11, String r12, File r13, ZipOutputStream r14) {
        /*
        r1 = 1;
        r0 = 0;
        r2 = r13.getAbsolutePath();
        if (r2 == 0) goto L_0x000e;
    L_0x0008:
        r3 = r2.length();
        if (r3 >= r1) goto L_0x000f;
    L_0x000e:
        return r0;
    L_0x000f:
        r3 = r2.length();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r4 = r12.length();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r5 = java.io.File.separator;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r5 = r5.length();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r4 = r4 + r5;
        if (r3 <= r4) goto L_0x000e;
    L_0x0020:
        r3 = r12.length();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r4 = java.io.File.separator;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r4 = r4.length();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r3 = r3 + r4;
        r2 = r2.substring(r3);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r3 = java.io.File.separator;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r3 = r2.endsWith(r3);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r3 != 0) goto L_0x00cf;
    L_0x0037:
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r3.<init>();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r2 = r3.append(r2);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r3 = java.io.File.separator;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r3 = r2;
    L_0x004b:
        r2 = new java.util.zip.ZipEntry;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r2.<init>(r3);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r14.putNextEntry(r2);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r14.flush();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r14.closeEntry();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r4 = r13.listFiles();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r4 == 0) goto L_0x0062;
    L_0x005f:
        r2 = r4.length;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r2 >= r1) goto L_0x0064;
    L_0x0062:
        r0 = r1;
        goto L_0x000e;
    L_0x0064:
        r2 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r5 = new byte[r2];	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r6 = r4.length;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r2 = r0;
    L_0x006a:
        if (r2 >= r6) goto L_0x00cc;
    L_0x006c:
        r7 = r4[r2];	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r7 == 0) goto L_0x0076;
    L_0x0070:
        r8 = r7.exists();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r8 != 0) goto L_0x0079;
    L_0x0076:
        r2 = r2 + 1;
        goto L_0x006a;
    L_0x0079:
        r8 = r7.isDirectory();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r8 == 0) goto L_0x008a;
    L_0x007f:
        r11.push(r7);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        goto L_0x0076;
    L_0x0083:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0088 }
        goto L_0x000e;
    L_0x0088:
        r0 = move-exception;
        throw r0;
    L_0x008a:
        r8 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r8.<init>(r7);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r9 = new java.util.zip.ZipEntry;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r10.<init>();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r10 = r10.append(r3);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r7 = r7.getName();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r7 = r10.append(r7);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r7 = r7.toString();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r9.<init>(r7);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r14.putNextEntry(r9);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
    L_0x00ac:
        r7 = r8.read(r5);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        if (r7 <= 0) goto L_0x00bd;
    L_0x00b2:
        r9 = 0;
        r14.write(r5, r9, r7);	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        goto L_0x00ac;
    L_0x00b7:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0088 }
        goto L_0x000e;
    L_0x00bd:
        r14.flush();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r14.closeEntry();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        r8.close();	 Catch:{ IOException -> 0x00c7, Exception -> 0x00b7 }
        goto L_0x0076;
    L_0x00c7:
        r7 = move-exception;
        r7.printStackTrace();	 Catch:{ IOException -> 0x0083, Exception -> 0x00b7 }
        goto L_0x0076;
    L_0x00cc:
        r0 = r1;
        goto L_0x000e;
    L_0x00cf:
        r3 = r2;
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.statsapp.util.Utils.zipDir(java.util.Stack, java.lang.String, java.io.File, java.util.zip.ZipOutputStream):boolean");
    }

    public static boolean delete(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return true;
        }
        if (file.isDirectory()) {
            return delDir(file);
        }
        boolean delete = file.delete();
        if (delete) {
            return delete;
        }
        System.out.println("delete file " + file + " unsuccessfully.");
        return delete;
    }

    private static boolean delDir(File file) {
        Stack stack = new Stack();
        if (!delDir(stack, file)) {
            return false;
        }
        while (stack.size() > 0) {
            if (!delDir(stack, (File) stack.pop())) {
                return false;
            }
        }
        return true;
    }

    private static boolean delDir(Stack<File> stack, File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null && listFiles.length >= 1) {
            stack.push(file);
            for (File file2 : listFiles) {
                if (file2.isDirectory()) {
                    stack.push(file2);
                } else if (!file2.delete()) {
                    System.out.println("delete file " + file2.getAbsolutePath() + " unsuccessfully.");
                    return false;
                }
            }
            return true;
        } else if (file.delete()) {
            return true;
        } else {
            System.out.println("delete dir " + file + " unsuccessfully.");
            return false;
        }
    }

    public static String getProductModel() {
        if (isEmpty(sPRODUCT_MODEL)) {
            sPRODUCT_MODEL = getProperty("ro.meizu.product.model");
        }
        return sPRODUCT_MODEL;
    }

    private static String getProperty(String str) {
        String str2 = null;
        try {
            Object a = aov.a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Object[]{str, ""});
            if (a != null) {
                str2 = a.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() < 1) {
            return true;
        }
        return false;
    }

    public static String dumpLogcat(int i) {
        Throwable th;
        InputStreamReader inputStreamReader;
        StringBuilder stringBuilder = new StringBuilder(1024);
        stringBuilder.append("\n\n----------Logcat----------\n");
        InputStreamReader inputStreamReader2 = null;
        try {
            Process start = new ProcessBuilder(new String[]{"/system/bin/logcat", "-v", "time", "-b", "events", "-b", "system", "-b", "main", "-t", String.valueOf(i), "-d"}).redirectErrorStream(true).start();
            try {
                start.getOutputStream().close();
            } catch (IOException e) {
            } catch (Throwable th2) {
                th = th2;
                if (inputStreamReader2 != null) {
                    try {
                        inputStreamReader2.close();
                    } catch (IOException e2) {
                    }
                }
                throw th;
            }
            try {
                start.getErrorStream().close();
            } catch (IOException e3) {
            } catch (Throwable th22) {
                th = th22;
                if (inputStreamReader2 != null) {
                    try {
                        inputStreamReader2.close();
                    } catch (IOException e22) {
                    }
                }
                throw th;
            }
            inputStreamReader = new InputStreamReader(start.getInputStream());
            try {
                char[] cArr = new char[8192];
                while (true) {
                    int read = inputStreamReader.read(cArr);
                    if (read <= 0) {
                        break;
                    }
                    stringBuilder.append(cArr, 0, read);
                }
                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (IOException e5) {
                th = e5;
                try {
                    Log.e(TAG, "Error running logcat", th);
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e6) {
                        }
                    }
                    return stringBuilder.toString();
                } catch (Throwable th3) {
                    th = th3;
                    inputStreamReader2 = inputStreamReader;
                    if (inputStreamReader2 != null) {
                        inputStreamReader2.close();
                    }
                    throw th;
                }
            }
        } catch (IOException e7) {
            th = e7;
            inputStreamReader = null;
            Log.e(TAG, "Error running logcat", th);
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            return stringBuilder.toString();
        } catch (Throwable th222) {
            th = th222;
            if (inputStreamReader2 != null) {
                try {
                    inputStreamReader2.close();
                } catch (IOException e222) {
                }
            }
            throw th;
        }
        return stringBuilder.toString();
    }

    public static void writeFileAppend(String str, String str2) {
        BufferedWriter bufferedWriter;
        Exception e;
        Throwable th;
        BufferedWriter bufferedWriter2 = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(str, true)));
            try {
                bufferedWriter.write(str2);
                try {
                    bufferedWriter.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e = e3;
                try {
                    e.printStackTrace();
                    try {
                        bufferedWriter.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter2 = bufferedWriter;
                    try {
                        bufferedWriter2.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    throw th;
                }
            }
        } catch (Exception e5) {
            e = e5;
            bufferedWriter = null;
            e.printStackTrace();
            bufferedWriter.close();
        } catch (Throwable th3) {
            th = th3;
            bufferedWriter2.close();
            throw th;
        }
    }

    public static File readFileFromDropbox(String str) {
        File file = new File("/data/system/dropbox/");
        if (!file.exists()) {
            return null;
        }
        for (File file2 : file2.listFiles()) {
            if (file2.getName().contains(str)) {
                break;
            }
        }
        file2 = null;
        return file2;
    }

    public static String getSamsungRestartReason() {
        String str = "Last boot from:";
        String executeCmd = executeCmd("cat /proc/reset_reason");
        if (TextUtils.isEmpty(executeCmd)) {
            return executeCmd;
        }
        int indexOf = executeCmd.indexOf(str);
        int indexOf2 = executeCmd.indexOf(MzGroups.GROUP_SPLIT_MARK_EXTRA);
        if (-1 == indexOf || -1 == indexOf2) {
            return executeCmd;
        }
        return executeCmd.substring(str.length() + indexOf, indexOf2).trim();
    }

    public static String[] getMtkRestartReason() {
        String str = "WDT status:";
        String str2 = "fiq step:";
        String str3 = "exception type:";
        String executeCmd = executeCmd("cat /proc/aed/reboot-reason");
        if (TextUtils.isEmpty(executeCmd)) {
            return null;
        }
        int indexOf = executeCmd.indexOf(str);
        int indexOf2 = executeCmd.indexOf(str2);
        int indexOf3 = executeCmd.indexOf(str3);
        int indexOf4 = executeCmd.indexOf("\n");
        if (-1 != indexOf && -1 != indexOf2 && -1 == indexOf3) {
            return new String[]{executeCmd.substring(str.length() + indexOf, indexOf2).trim(), executeCmd.substring(str2.length() + indexOf2, indexOf4).trim()};
        } else if (-1 == indexOf || -1 == indexOf2 || -1 == indexOf3 || indexOf3 >= indexOf4) {
            return null;
        } else {
            return new String[]{executeCmd.substring(str.length() + indexOf, indexOf2).trim(), executeCmd.substring(str2.length() + indexOf2, indexOf3).trim()};
        }
    }

    public static void copyDir(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream2;
        IOException iOException;
        IOException iOException2;
        Throwable th;
        Throwable th2;
        FileInputStream fileInputStream2 = null;
        try {
            File[] listFiles = file.listFiles();
            int length = listFiles.length;
            int i = 0;
            fileOutputStream = null;
            while (i < length) {
                File file3 = listFiles[i];
                if (file3.isFile()) {
                    file3.toString();
                    fileInputStream = new FileInputStream(file3);
                    try {
                        fileOutputStream2 = new FileOutputStream(file2 + File.separator + file3.getName());
                        try {
                            byte[] bArr = new byte[4096];
                            while (true) {
                                int read = fileInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream2.write(bArr, 0, read);
                            }
                            fileOutputStream2.flush();
                        } catch (IOException e) {
                            iOException = e;
                            fileOutputStream = fileOutputStream2;
                            fileInputStream2 = fileInputStream;
                            iOException2 = iOException;
                        } catch (Throwable th3) {
                            th = th3;
                            fileOutputStream = fileOutputStream2;
                            fileInputStream2 = fileInputStream;
                            th2 = th;
                        }
                    } catch (IOException e2) {
                        iOException = e2;
                        fileInputStream2 = fileInputStream;
                        iOException2 = iOException;
                    } catch (Throwable th4) {
                        th = th4;
                        fileInputStream2 = fileInputStream;
                        th2 = th;
                    }
                } else {
                    try {
                        if (file3.isDirectory()) {
                            File file4 = new File(file2.getAbsoluteFile() + File.separator + file3.getName());
                            ensureDir(file4);
                            copyDir(file3, file4);
                        }
                        fileInputStream = fileInputStream2;
                        fileOutputStream2 = fileOutputStream;
                    } catch (IOException e3) {
                        iOException2 = e3;
                    }
                }
                i++;
                fileOutputStream = fileOutputStream2;
                fileInputStream2 = fileInputStream;
            }
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException iOException22) {
                    iOException22.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException iOException222) {
                    iOException222.printStackTrace();
                }
            }
        } catch (IOException e4) {
            iOException222 = e4;
            fileOutputStream = null;
            try {
                iOException222.printStackTrace();
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException iOException2222) {
                        iOException2222.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException iOException22222) {
                        iOException22222.printStackTrace();
                    }
                }
            } catch (Throwable th5) {
                th2 = th5;
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e222) {
                        e222.printStackTrace();
                    }
                }
                throw th2;
            }
        } catch (Throwable th6) {
            th2 = th6;
            fileOutputStream = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th2;
        }
    }

    public static float getProcessCpuRate() {
        float totalCpuTime = (float) getTotalCpuTime();
        float appCpuTime = (float) getAppCpuTime();
        try {
            Thread.sleep(360);
        } catch (Exception e) {
        }
        return ((((float) getAppCpuTime()) - appCpuTime) * 100.0f) / (((float) getTotalCpuTime()) - totalCpuTime);
    }

    public static long getTotalCpuTime() {
        String[] strArr = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            strArr = readLine.split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.parseLong(strArr[8]) + (((((Long.parseLong(strArr[2]) + Long.parseLong(strArr[3])) + Long.parseLong(strArr[4])) + Long.parseLong(strArr[6])) + Long.parseLong(strArr[5])) + Long.parseLong(strArr[7]));
    }

    public static long getAppCpuTime() {
        String[] strArr = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            strArr = readLine.split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.parseLong(strArr[16]) + ((Long.parseLong(strArr[13]) + Long.parseLong(strArr[14])) + Long.parseLong(strArr[15]));
    }

    public static String getSre(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.meizu.stats", 0);
        String string = sharedPreferences.getString("screen_resolution", null);
        if (string != null) {
            return string;
        }
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        int i = point.x;
        string = i + "." + point.y;
        sharedPreferences.edit().putString("screen_resolution", string).apply();
        return string;
    }

    public static String getLocationLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (language == null) {
            language = "";
        }
        if (country == null) {
            country = "";
        }
        return language + "_" + country;
    }

    public static boolean checkUmid(String str, Context context) {
        if (TextUtils.isEmpty(str) || str.length() < 35) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.meizu.stats", 0);
        StringBuilder stringBuilder = new StringBuilder();
        String imei = getIMEI(context);
        String string = sharedPreferences.getString(anz.h, sMACAddress);
        StringBuilder stringBuilder2 = new StringBuilder();
        if (string != null) {
            String[] split = string.split(":");
            if (split != null) {
                for (String toUpperCase : split) {
                    stringBuilder2.append(toUpperCase.toUpperCase());
                }
            }
        }
        string = bytesToHexString(getMD5((imei + stringBuilder2.toString()).getBytes())).toUpperCase();
        int to16Int = to16Int(str.substring(0, 1));
        stringBuilder.append(str.substring(to16Int + 1));
        stringBuilder.append(str.substring(1, to16Int + 1));
        CharSequence substring = stringBuilder.substring(0, 16);
        CharSequence substring2 = string.substring(0, 16);
        CharSequence substring3 = stringBuilder.substring(17, 33);
        CharSequence substring4 = string.substring(16);
        if (TextUtils.equals(substring, substring2) && TextUtils.equals(substring3, substring4)) {
            return true;
        }
        return false;
    }

    public static int to16Int(String str) {
        int i = 0;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            if ("A".equals(str.toUpperCase())) {
                return 10;
            }
            if ("B".equals(str.toUpperCase())) {
                return 11;
            }
            if ("C".equals(str.toUpperCase())) {
                return 12;
            }
            if ("D".equals(str.toUpperCase())) {
                return 13;
            }
            if ("E".equals(str.toUpperCase())) {
                return 14;
            }
            if ("F".equals(str.toUpperCase())) {
                return 15;
            }
            return i;
        }
    }

    public static int getDeviceType() {
        return 1;
    }

    public static String getOSType() {
        return "Android";
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getPackageVersion(String str, Context context) {
        if (TextUtils.isEmpty(str) || context == null) {
            return "";
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return "";
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(str, 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return "";
    }

    public static String getBuildMask() {
        if (TextUtils.isEmpty(sBUILD_MASK)) {
            sBUILD_MASK = getProperty("ro.build.mask.id");
        }
        return sBUILD_MASK;
    }
}
