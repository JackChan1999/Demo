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
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.pushsdk.constants.MeizuConstants;
import com.meizu.statsapp.b;
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
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Stack;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;

public class Utils {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'cancel', 'c', 'd', 'e', 'f'};
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
        StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
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
        try {
            Object temp = b.a("android.telephony.MzTelephonyManager", "getDeviceId", null);
            if (temp != null && (temp instanceof String)) {
                String imei = (String) temp;
                if (!isEmpty(imei)) {
                    sIMEI = imei;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sIMEI;
    }

    public static String getSN() {
        if (!isEmpty(sSN)) {
            return sSN;
        }
        String sn = getProperty("ro.serialno");
        if (isEmpty(sn)) {
            return sn;
        }
        sSN = sn;
        return sn;
    }

    public static String getFlymeUid(Context context) {
        try {
            Account[] account = ((AccountManager) context.getSystemService("account")).getAccountsByType("com.meizu.account");
            if (!(account == null || account.length <= 0 || account[0] == null)) {
                return account[0].name;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getOperater(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
        if (tm == null || 5 != tm.getSimState()) {
            return "";
        }
        return tm.getSimOperator();
    }

    public static boolean isWiFiWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(1);
        if (wifiInfo == null || State.CONNECTED != wifiInfo.getState()) {
            return false;
        }
        return true;
    }

    public static boolean isNetworkWorking(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(0);
        if ((wifiInfo == null || State.CONNECTED != wifiInfo.getState()) && (mobileInfo == null || State.CONNECTED != mobileInfo.getState())) {
            return false;
        }
        return true;
    }

    public static String getNetworkType(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info == null || !info.isAvailable()) {
                    return "off";
                }
                if (info.getType() == 1) {
                    return "wifi";
                }
                TelephonyManager tm = (TelephonyManager) context.getSystemService("phone");
                if (tm.getNetworkType() == 1 || tm.getNetworkType() == 2) {
                    return "2g";
                }
                if (tm.getNetworkType() == 13) {
                    return "4g";
                }
                return "3g";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "unknown";
    }

    public static boolean isRoot(Context context) {
        Object deviceStateManager = context.getSystemService("device_states");
        if (deviceStateManager == null) {
            deviceStateManager = context.getSystemService("deivce_states");
            if (deviceStateManager == null) {
                return false;
            }
        }
        try {
            Method method = deviceStateManager.getClass().getMethod("doCheckState", new Class[]{Integer.TYPE});
            if (method == null) {
                return false;
            }
            method.setAccessible(true);
            Integer res = (Integer) method.invoke(deviceStateManager, new Object[]{Integer.valueOf(1)});
            if (res == null || 1 != res.intValue()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean ensureDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (dir.exists()) {
            return true;
        }
        for (int i = 0; i < 3 && !dir.mkdirs(); i++) {
        }
        return dir.exists();
    }

    public static byte[] getFileMd5(String path) {
        if (isEmpty(path)) {
            return null;
        }
        return getFileMd5(new File(path));
    }

    public static byte[] getFileMd5(File file) {
        IOException e;
        FileNotFoundException e2;
        Throwable th;
        NoSuchAlgorithmException e3;
        byte[] bArr = null;
        if (file != null && file.exists()) {
            FileInputStream fin = null;
            try {
                FileInputStream fin2 = new FileInputStream(file);
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] buffer = new byte[524288];
                    while (true) {
                        int len = fin2.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        md.update(buffer, 0, len);
                    }
                    bArr = md.digest();
                    if (fin2 != null) {
                        try {
                            fin2.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e5) {
                    e2 = e5;
                    fin = fin2;
                    try {
                        e2.printStackTrace();
                        if (fin != null) {
                            try {
                                fin.close();
                            } catch (IOException e42) {
                                e42.printStackTrace();
                            }
                        }
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fin != null) {
                            try {
                                fin.close();
                            } catch (IOException e422) {
                                e422.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (NoSuchAlgorithmException e6) {
                    e3 = e6;
                    fin = fin2;
                    e3.printStackTrace();
                    if (fin != null) {
                        try {
                            fin.close();
                        } catch (IOException e4222) {
                            e4222.printStackTrace();
                        }
                    }
                    return bArr;
                } catch (IOException e7) {
                    e4222 = e7;
                    fin = fin2;
                    e4222.printStackTrace();
                    if (fin != null) {
                        try {
                            fin.close();
                        } catch (IOException e42222) {
                            e42222.printStackTrace();
                        }
                    }
                    return bArr;
                } catch (Throwable th3) {
                    th = th3;
                    fin = fin2;
                    if (fin != null) {
                        fin.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e8) {
                e2 = e8;
                e2.printStackTrace();
                if (fin != null) {
                    fin.close();
                }
                return bArr;
            } catch (NoSuchAlgorithmException e9) {
                e3 = e9;
                e3.printStackTrace();
                if (fin != null) {
                    fin.close();
                }
                return bArr;
            } catch (IOException e10) {
                e42222 = e10;
                e42222.printStackTrace();
                if (fin != null) {
                    fin.close();
                }
                return bArr;
            }
        }
        return bArr;
    }

    public static byte[] getMD5(byte[] source) {
        byte[] bArr = null;
        if (source != null && source.length >= 1) {
            try {
                bArr = MessageDigest.getInstance("MD5").digest(source);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return bArr;
    }

    public static byte[] gzip(byte[] source) {
        IOException e;
        Throwable th;
        byte[] bArr = null;
        if (source != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = null;
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(byteArrayOutputStream);
                try {
                    gZIPOutputStream2.write(source, 0, source.length);
                    gZIPOutputStream2.flush();
                    if (gZIPOutputStream2 != null) {
                        try {
                            gZIPOutputStream2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    bArr = byteArrayOutputStream.toByteArray();
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                } catch (IOException e3) {
                    e22 = e3;
                    gZIPOutputStream = gZIPOutputStream2;
                    try {
                        e22.printStackTrace();
                        if (gZIPOutputStream != null) {
                            try {
                                gZIPOutputStream.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        if (gZIPOutputStream != null) {
                            try {
                                gZIPOutputStream.close();
                            } catch (IOException e2222) {
                                e2222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    gZIPOutputStream = gZIPOutputStream2;
                    if (gZIPOutputStream != null) {
                        gZIPOutputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e2222 = e4;
                e2222.printStackTrace();
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                return bArr;
            }
        }
        return bArr;
    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        char[] buf = new char[(bytes.length * 2)];
        int c = 0;
        for (byte b : bytes) {
            int i = c + 1;
            buf[c] = DIGITS[(b >> 4) & 15];
            c = i + 1;
            buf[i] = DIGITS[b & 15];
        }
        return new String(buf);
    }

    public static String getCpuUsage() {
        return String.valueOf((int) getProcessCpuRate());
    }

    public static String getMemInfo(String processName) {
        return executeCmd("dumpsys meminfo " + processName);
    }

    public static String getCpuInfo() {
        return executeCmd("top -d 1 -n 1");
    }

    public static String dumpKernalLog() {
        StringBuilder dump = new StringBuilder(1024);
        dump.append("\n\n----------Kernal----------\n");
        dump.append(executeCmd("dmesg"));
        return dump.toString();
    }

    public static String getMemUsage() {
        String cmdResult = executeCmd("cat /proc/meminfo");
        String ret = String.valueOf(-1);
        if (cmdResult == null) {
            return ret;
        }
        String line1 = getLine(cmdResult, 1);
        String line2 = getLine(cmdResult, 2);
        if (line1 == null || line2 == null) {
            return ret;
        }
        String[] splitStrs1 = line1.split(" +");
        String[] splitStrs2 = line2.split(" +");
        if (splitStrs1 == null || splitStrs1.length < 3 || splitStrs2 == null || splitStrs2.length < 3) {
            return ret;
        }
        try {
            long memTotal = Long.parseLong(splitStrs1[1]);
            long memFree = Long.parseLong(splitStrs2[1]);
            if (memTotal == 0) {
                return ret;
            }
            ret = String.valueOf((int) ((100.0d * (((double) memTotal) - (((double) memFree) * 1.0d))) / ((double) memTotal)));
            return ret;
        } catch (Exception e) {
        }
    }

    public static String getSamsungKernalBootReason() {
        String cmdResult = executeCmd("cat /proc/reset_reason");
        int indexBegin = cmdResult.indexOf("Last boot from: ");
        int indexEnd = cmdResult.indexOf(",");
        if (-1 == indexBegin || -1 == indexEnd || indexBegin >= indexEnd) {
            return null;
        }
        return cmdResult.substring(indexBegin, indexEnd);
    }

    private static String getLine(String source, int line) {
        if (source == null || line < 1) {
            return null;
        }
        int start = 0;
        int newLineIndex = -1;
        int linePos = 0;
        while (start < source.length() && linePos < line) {
            newLineIndex = source.indexOf(10, start);
            if (-1 == newLineIndex) {
                break;
            } else if (linePos != line - 1) {
                start = newLineIndex + 1;
                linePos++;
            } else if (newLineIndex > start) {
                return source.substring(start, newLineIndex - 1);
            } else {
                return null;
            }
        }
        if (linePos == line - 1 && -1 == newLineIndex && start < source.length()) {
            return source.substring(start);
        }
        return null;
    }

    public static String executeCmd(String cmd) {
        Exception e;
        Throwable th;
        if (cmd == null || cmd.length() <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        InputStreamReader input = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            if (process != null) {
                InputStreamReader input2 = new InputStreamReader(process.getInputStream());
                try {
                    char[] buf = new char[2048];
                    while (true) {
                        int num = input2.read(buf);
                        if (num <= 0) {
                            break;
                        }
                        sb.append(buf, 0, num);
                    }
                    input = input2;
                } catch (Exception e2) {
                    e = e2;
                    input = input2;
                    try {
                        e.printStackTrace();
                        if (input != null) {
                            try {
                                input.close();
                            } catch (IOException e3) {
                            }
                        }
                        if (process != null) {
                            process.destroy();
                        }
                        return sb.toString();
                    } catch (Throwable th2) {
                        th = th2;
                        if (input != null) {
                            try {
                                input.close();
                            } catch (IOException e4) {
                            }
                        }
                        if (process != null) {
                            process.destroy();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    input = input2;
                    if (input != null) {
                        input.close();
                    }
                    if (process != null) {
                        process.destroy();
                    }
                    throw th;
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e5) {
                }
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Exception e6) {
            e = e6;
            e.printStackTrace();
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
            return sb.toString();
        }
        return sb.toString();
    }

    public static String getMACAddress(Context context) {
        if (!isEmpty(sMACAddress)) {
            return sMACAddress;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.meizu.stats", 0);
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String address = wifiInfo == null ? null : wifiInfo.getMacAddress();
            if (!isEmpty(address)) {
                sMACAddress = address;
                sharedPreferences.edit().putString("mac_address", sMACAddress).apply();
            }
        }
        if (TextUtils.isEmpty(sMACAddress)) {
            return sharedPreferences.getString("mac_address", sMACAddress);
        }
        return sMACAddress;
    }

    public static String getLocation(Context context) {
        String str = null;
        if (context != null) {
            try {
                LocationManager locationManager = (LocationManager) context.getSystemService("location");
                if (!(locationManager == null || locationManager.getLastKnownLocation("network") == null)) {
                    str = String.format("%.6f, %.6f", new Object[]{Double.valueOf(locationManager.getLastKnownLocation("network").getLatitude()), Double.valueOf(locationManager.getLastKnownLocation("network").getLongitude())});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getCellId(Context context) {
        if (context == null) {
            return null;
        }
        TelephonyManager phone = (TelephonyManager) context.getSystemService("phone");
        if (phone == null || phone.getPhoneType() != 1) {
            return null;
        }
        GsmCellLocation gsm_cell = (GsmCellLocation) phone.getCellLocation();
        if (gsm_cell != null) {
            return "cid: " + gsm_cell.getCid() + ", lac: " + gsm_cell.getLac();
        }
        return null;
    }

    public static boolean zipFile(File source, File dest) {
        if (source == null || dest == null || !source.exists()) {
            return false;
        }
        if (dest.exists()) {
            dest.delete();
        }
        if (source.isDirectory()) {
            return zipDir(source, dest);
        }
        return gZipFile(source, dest);
    }

    private static boolean gZipFile(File source, File dest) {
        IOException e;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        GZIPOutputStream gZIPOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            dest.createNewFile();
            FileOutputStream fileOutputStream2 = new FileOutputStream(dest);
            try {
                GZIPOutputStream gZIPOutputStream2 = new GZIPOutputStream(new BufferedOutputStream(fileOutputStream2));
                try {
                    FileInputStream fileInputStream2 = new FileInputStream(source);
                    try {
                        byte[] buffer = new byte[FileUtils.ONE_MB];
                        while (true) {
                            int count = fileInputStream2.read(buffer);
                            if (count <= 0) {
                                break;
                            }
                            gZIPOutputStream2.write(buffer, 0, count);
                        }
                        gZIPOutputStream2.flush();
                        if (gZIPOutputStream2 != null) {
                            try {
                                gZIPOutputStream2.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        fileInputStream = fileInputStream2;
                        gZIPOutputStream = gZIPOutputStream2;
                        fileOutputStream = fileOutputStream2;
                        return true;
                    } catch (IOException e3) {
                        e222 = e3;
                        fileInputStream = fileInputStream2;
                        gZIPOutputStream = gZIPOutputStream2;
                        fileOutputStream = fileOutputStream2;
                        try {
                            e222.printStackTrace();
                            if (gZIPOutputStream != null) {
                                try {
                                    gZIPOutputStream.close();
                                } catch (IOException e2222) {
                                    e2222.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e22222) {
                                    e22222.printStackTrace();
                                }
                            }
                            if (fileInputStream == null) {
                                return false;
                            }
                            try {
                                fileInputStream.close();
                                return false;
                            } catch (IOException e222222) {
                                e222222.printStackTrace();
                                return false;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (gZIPOutputStream != null) {
                                try {
                                    gZIPOutputStream.close();
                                } catch (IOException e2222222) {
                                    e2222222.printStackTrace();
                                }
                            }
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e22222222) {
                                    e22222222.printStackTrace();
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e222222222) {
                                    e222222222.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        fileInputStream = fileInputStream2;
                        gZIPOutputStream = gZIPOutputStream2;
                        fileOutputStream = fileOutputStream2;
                        if (gZIPOutputStream != null) {
                            gZIPOutputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    e222222222 = e4;
                    gZIPOutputStream = gZIPOutputStream2;
                    fileOutputStream = fileOutputStream2;
                    e222222222.printStackTrace();
                    if (gZIPOutputStream != null) {
                        gZIPOutputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (fileInputStream == null) {
                        return false;
                    }
                    fileInputStream.close();
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    gZIPOutputStream = gZIPOutputStream2;
                    fileOutputStream = fileOutputStream2;
                    if (gZIPOutputStream != null) {
                        gZIPOutputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }
                    throw th;
                }
            } catch (IOException e5) {
                e222222222 = e5;
                fileOutputStream = fileOutputStream2;
                e222222222.printStackTrace();
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream == null) {
                    return false;
                }
                fileInputStream.close();
                return false;
            } catch (Throwable th5) {
                th = th5;
                fileOutputStream = fileOutputStream2;
                if (gZIPOutputStream != null) {
                    gZIPOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
        } catch (IOException e6) {
            e222222222 = e6;
            e222222222.printStackTrace();
            if (gZIPOutputStream != null) {
                gZIPOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (fileInputStream == null) {
                return false;
            }
            fileInputStream.close();
            return false;
        }
    }

    private static boolean zipDir(File source, File dest) {
        IOException e;
        Throwable th;
        Stack<File> dirStack = new Stack();
        String topDir = source.getParent();
        if (topDir == null) {
            topDir = "/";
        }
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            dest.createNewFile();
            FileOutputStream fileOutputStream2 = new FileOutputStream(dest);
            try {
                ZipOutputStream zipOutputStream2 = new ZipOutputStream(new BufferedOutputStream(fileOutputStream2));
                try {
                    if (zipDir(dirStack, topDir, source, zipOutputStream2)) {
                        do {
                            if (dirStack.size() <= 0) {
                                if (zipOutputStream2 != null) {
                                    try {
                                        zipOutputStream2.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (IOException e22) {
                                        e22.printStackTrace();
                                    }
                                }
                                zipOutputStream = zipOutputStream2;
                                fileOutputStream = fileOutputStream2;
                                return true;
                            }
                        } while (zipDir(dirStack, topDir, (File) dirStack.pop(), zipOutputStream2));
                        if (zipOutputStream2 != null) {
                            try {
                                zipOutputStream2.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e2222) {
                                e2222.printStackTrace();
                            }
                        }
                        zipOutputStream = zipOutputStream2;
                        fileOutputStream = fileOutputStream2;
                        return false;
                    }
                    if (zipOutputStream2 != null) {
                        try {
                            zipOutputStream2.close();
                        } catch (IOException e22222) {
                            e22222.printStackTrace();
                        }
                    }
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException e222222) {
                            e222222.printStackTrace();
                        }
                    }
                    zipOutputStream = zipOutputStream2;
                    fileOutputStream = fileOutputStream2;
                    return false;
                } catch (IOException e3) {
                    e222222 = e3;
                    zipOutputStream = zipOutputStream2;
                    fileOutputStream = fileOutputStream2;
                } catch (Throwable th2) {
                    th = th2;
                    zipOutputStream = zipOutputStream2;
                    fileOutputStream = fileOutputStream2;
                }
            } catch (IOException e4) {
                e222222 = e4;
                fileOutputStream = fileOutputStream2;
                try {
                    e222222.printStackTrace();
                    if (zipOutputStream != null) {
                        try {
                            zipOutputStream.close();
                        } catch (IOException e2222222) {
                            e2222222.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e22222222) {
                            e22222222.printStackTrace();
                        }
                    }
                    return false;
                } catch (Throwable th3) {
                    th = th3;
                    if (zipOutputStream != null) {
                        try {
                            zipOutputStream.close();
                        } catch (IOException e222222222) {
                            e222222222.printStackTrace();
                        }
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e2222222222) {
                            e2222222222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = fileOutputStream2;
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } catch (IOException e5) {
            e2222222222 = e5;
            e2222222222.printStackTrace();
            if (zipOutputStream != null) {
                zipOutputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return false;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean zipDir(Stack<File> r17, String r18, File r19, ZipOutputStream r20) {
        /*
        r13 = r19.getAbsolutePath();
        if (r13 == 0) goto L_0x000d;
    L_0x0006:
        r14 = r13.length();
        r15 = 1;
        if (r14 >= r15) goto L_0x000f;
    L_0x000d:
        r14 = 0;
    L_0x000e:
        return r14;
    L_0x000f:
        r8 = 0;
        r14 = r13.length();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = r18.length();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r16 = java.io.File.separator;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r16 = r16.length();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = r15 + r16;
        if (r14 > r15) goto L_0x0025;
    L_0x0022:
        r14 = 0;
        r8 = 0;
        goto L_0x000e;
    L_0x0025:
        r14 = r18.length();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = java.io.File.separator;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = r15.length();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r14 + r15;
        r13 = r13.substring(r14);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = java.io.File.separator;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r13.endsWith(r14);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        if (r14 != 0) goto L_0x004f;
    L_0x003c:
        r14 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14.<init>();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r14.append(r13);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = java.io.File.separator;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r14.append(r15);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r13 = r14.toString();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
    L_0x004f:
        r4 = new java.util.zip.ZipEntry;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r4.<init>(r13);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r0 = r20;
        r0.putNextEntry(r4);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r20.flush();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r20.closeEntry();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r10 = r19.listFiles();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        if (r10 == 0) goto L_0x0069;
    L_0x0065:
        r14 = r10.length;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = 1;
        if (r14 >= r15) goto L_0x006c;
    L_0x0069:
        r14 = 1;
        r8 = 0;
        goto L_0x000e;
    L_0x006c:
        r14 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r2 = new byte[r14];	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r3 = 0;
        r1 = r10;
        r12 = r1.length;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r11 = 0;
        r9 = r8;
    L_0x0075:
        if (r11 >= r12) goto L_0x00e1;
    L_0x0077:
        r6 = r1[r11];	 Catch:{ IOException -> 0x00ee, Exception -> 0x00eb, all -> 0x00e8 }
        if (r6 == 0) goto L_0x00f1;
    L_0x007b:
        r14 = r6.exists();	 Catch:{ IOException -> 0x00ee, Exception -> 0x00eb, all -> 0x00e8 }
        if (r14 != 0) goto L_0x0086;
    L_0x0081:
        r8 = r9;
    L_0x0082:
        r11 = r11 + 1;
        r9 = r8;
        goto L_0x0075;
    L_0x0086:
        r14 = r6.isDirectory();	 Catch:{ IOException -> 0x00ee, Exception -> 0x00eb, all -> 0x00e8 }
        if (r14 == 0) goto L_0x0093;
    L_0x008c:
        r0 = r17;
        r0.push(r6);	 Catch:{ IOException -> 0x00ee, Exception -> 0x00eb, all -> 0x00e8 }
        r8 = r9;
        goto L_0x0082;
    L_0x0093:
        r8 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x00ee, Exception -> 0x00eb, all -> 0x00e8 }
        r8.<init>(r6);	 Catch:{ IOException -> 0x00ee, Exception -> 0x00eb, all -> 0x00e8 }
        r7 = new java.util.zip.ZipEntry;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14.<init>();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r14.append(r13);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r15 = r6.getName();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r14.append(r15);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r14 = r14.toString();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r7.<init>(r14);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r0 = r20;
        r0.putNextEntry(r7);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
    L_0x00b7:
        r3 = r8.read(r2);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        if (r3 <= 0) goto L_0x00cc;
    L_0x00bd:
        r14 = 0;
        r0 = r20;
        r0.write(r2, r14, r3);	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        goto L_0x00b7;
    L_0x00c4:
        r5 = move-exception;
    L_0x00c5:
        r5.printStackTrace();	 Catch:{ all -> 0x00e5 }
        r8 = 0;
    L_0x00c9:
        r14 = 0;
        goto L_0x000e;
    L_0x00cc:
        r20.flush();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r20.closeEntry();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        r8.close();	 Catch:{ IOException -> 0x00d6, Exception -> 0x00db }
        goto L_0x0082;
    L_0x00d6:
        r5 = move-exception;
        r5.printStackTrace();	 Catch:{ IOException -> 0x00c4, Exception -> 0x00db }
        goto L_0x0082;
    L_0x00db:
        r5 = move-exception;
    L_0x00dc:
        r5.printStackTrace();	 Catch:{ all -> 0x00e5 }
        r8 = 0;
        goto L_0x00c9;
    L_0x00e1:
        r14 = 1;
        r8 = 0;
        goto L_0x000e;
    L_0x00e5:
        r14 = move-exception;
    L_0x00e6:
        r8 = 0;
        throw r14;
    L_0x00e8:
        r14 = move-exception;
        r8 = r9;
        goto L_0x00e6;
    L_0x00eb:
        r5 = move-exception;
        r8 = r9;
        goto L_0x00dc;
    L_0x00ee:
        r5 = move-exception;
        r8 = r9;
        goto L_0x00c5;
    L_0x00f1:
        r8 = r9;
        goto L_0x0082;
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
        boolean res = file.delete();
        if (res) {
            return res;
        }
        System.out.println("delete file " + file + " unsuccessfully.");
        return res;
    }

    private static boolean delDir(File dir) {
        Stack<File> dirStack = new Stack();
        if (!delDir(dirStack, dir)) {
            return false;
        }
        while (dirStack.size() > 0) {
            if (!delDir(dirStack, (File) dirStack.pop())) {
                return false;
            }
        }
        return true;
    }

    private static boolean delDir(Stack<File> dirStack, File dir) {
        File[] subList = dir.listFiles();
        if (subList != null && subList.length >= 1) {
            dirStack.push(dir);
            for (File subFile : subList) {
                if (subFile.isDirectory()) {
                    dirStack.push(subFile);
                } else if (!subFile.delete()) {
                    System.out.println("delete file " + subFile.getAbsolutePath() + " unsuccessfully.");
                    return false;
                }
            }
            return true;
        } else if (dir.delete()) {
            return true;
        } else {
            System.out.println("delete dir " + dir + " unsuccessfully.");
            return false;
        }
    }

    public static String getProductModel() {
        if (isEmpty(sPRODUCT_MODEL)) {
            sPRODUCT_MODEL = getProperty("ro.meizu.product.model");
        }
        return sPRODUCT_MODEL;
    }

    private static String getProperty(String property) {
        try {
            Object temp = b.a(MeizuConstants.CLS_NAME_SYSTEM_PROPERTIES, "get", new Object[]{property, ""});
            return temp == null ? null : temp.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() < 1) {
            return true;
        }
        return false;
    }

    public static String dumpLogcat(int lines) {
        IOException e;
        Throwable th;
        StringBuilder dump = new StringBuilder(1024);
        dump.append("\n\n----------Logcat----------\n");
        InputStreamReader inputStreamReader = null;
        try {
            Process logcat = new ProcessBuilder(new String[]{"/system/bin/logcat", "-v", RequestManager.TIME, "-cancel", "events", "-cancel", "system", "-cancel", "main", "-t", String.valueOf(lines), "-d"}).redirectErrorStream(true).start();
            try {
                logcat.getOutputStream().close();
            } catch (IOException e2) {
            }
            try {
                logcat.getErrorStream().close();
            } catch (IOException e3) {
            }
            InputStreamReader input = new InputStreamReader(logcat.getInputStream());
            try {
                char[] buf = new char[8192];
                while (true) {
                    int num = input.read(buf);
                    if (num <= 0) {
                        break;
                    }
                    dump.append(buf, 0, num);
                }
                if (input != null) {
                    try {
                        input.close();
                        inputStreamReader = input;
                    } catch (IOException e4) {
                        inputStreamReader = input;
                    }
                }
            } catch (IOException e5) {
                e = e5;
                inputStreamReader = input;
                try {
                    Log.e(TAG, "Error running logcat", e);
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e6) {
                        }
                    }
                    return dump.toString();
                } catch (Throwable th2) {
                    th = th2;
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e7) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStreamReader = input;
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                throw th;
            }
        } catch (IOException e8) {
            e = e8;
            Log.e(TAG, "Error running logcat", e);
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            return dump.toString();
        }
        return dump.toString();
    }

    public static void writeFileAppend(String file, String conent) {
        Exception e;
        Throwable th;
        BufferedWriter out = null;
        try {
            BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            try {
                out2.write(conent);
                try {
                    out2.close();
                    out = out2;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    out = out2;
                }
            } catch (Exception e3) {
                e = e3;
                out = out2;
                try {
                    e.printStackTrace();
                    try {
                        out.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        out.close();
                    } catch (IOException e222) {
                        e222.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                out.close();
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            out.close();
        }
    }

    public static File readFileFromDropbox(String uuid) throws IOException {
        File res = null;
        File dropboxDir = new File("/data/system/dropbox/");
        if (!dropboxDir.exists()) {
            return null;
        }
        for (File file : dropboxDir.listFiles()) {
            if (file.getName().contains(uuid)) {
                res = file;
                break;
            }
        }
        return res;
    }

    public static String getSamsungRestartReason() {
        String lastBootFrom = "Last boot from:";
        String result = executeCmd("cat /proc/reset_reason");
        if (TextUtils.isEmpty(result)) {
            return result;
        }
        int beginIndex = result.indexOf(lastBootFrom);
        int endIndex = result.indexOf(",");
        if (-1 == beginIndex || -1 == endIndex) {
            return result;
        }
        return result.substring(lastBootFrom.length() + beginIndex, endIndex).trim();
    }

    public static String[] getMtkRestartReason() {
        String wdtStatus = "WDT status:";
        String fiqStep = "fiq step:";
        String exceptionType = "exception type:";
        String temp = executeCmd("cat /proc/aed/reboot-reason");
        if (TextUtils.isEmpty(temp)) {
            return null;
        }
        int beginIndex = temp.indexOf(wdtStatus);
        int endIndex = temp.indexOf(fiqStep);
        int exceptionTypeIndex = temp.indexOf(exceptionType);
        int enterIndex = temp.indexOf("\n");
        if (-1 != beginIndex && -1 != endIndex && -1 == exceptionTypeIndex) {
            return new String[]{temp.substring(wdtStatus.length() + beginIndex, endIndex).trim(), temp.substring(fiqStep.length() + endIndex, enterIndex).trim()};
        } else if (-1 == beginIndex || -1 == endIndex || -1 == exceptionTypeIndex || exceptionTypeIndex >= enterIndex) {
            return null;
        } else {
            return new String[]{temp.substring(wdtStatus.length() + beginIndex, endIndex).trim(), temp.substring(fiqStep.length() + endIndex, exceptionTypeIndex).trim()};
        }
    }

    public static void copyDir(File srcFile, File dstFile) {
        IOException ex;
        Throwable th;
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        File[] arr$ = srcFile.listFiles();
        int len$ = arr$.length;
        int i$ = 0;
        FileInputStream input = null;
        FileOutputStream output = null;
        while (i$ < len$) {
            try {
                File file = arr$[i$];
                if (file.isFile()) {
                    file.toString();
                    fileInputStream = new FileInputStream(file);
                    try {
                        fileOutputStream = new FileOutputStream(dstFile + File.separator + file.getName());
                        try {
                            byte[] b = new byte[4096];
                            while (true) {
                                int len = fileInputStream.read(b);
                                if (len == -1) {
                                    break;
                                }
                                fileOutputStream.write(b, 0, len);
                            }
                            fileOutputStream.flush();
                        } catch (IOException e) {
                            ex = e;
                        }
                    } catch (IOException e2) {
                        ex = e2;
                        fileOutputStream = output;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = output;
                    }
                } else {
                    if (file.isDirectory()) {
                        File dst = new File(dstFile.getAbsoluteFile() + File.separator + file.getName());
                        ensureDir(dst);
                        copyDir(file, dst);
                    }
                    fileInputStream = input;
                    fileOutputStream = output;
                }
                i$++;
                input = fileInputStream;
                output = fileOutputStream;
            } catch (IOException e3) {
                ex = e3;
                fileInputStream = input;
                fileOutputStream = output;
            } catch (Throwable th3) {
                th = th3;
                fileInputStream = input;
                fileOutputStream = output;
            }
        }
        if (input != null) {
            try {
                input.close();
            } catch (IOException ex2) {
                ex2.printStackTrace();
            }
        }
        if (output != null) {
            try {
                output.close();
                fileInputStream = input;
                fileOutputStream = output;
                return;
            } catch (IOException ex22) {
                ex22.printStackTrace();
                fileInputStream = input;
                fileOutputStream = output;
                return;
            }
        }
        fileOutputStream = output;
        return;
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException ex222) {
                ex222.printStackTrace();
                return;
            }
        }
        try {
            ex222.printStackTrace();
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex2222) {
                    ex2222.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Throwable th4) {
            th = th4;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex22222) {
                    ex22222.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex222222) {
                    ex222222.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static float getProcessCpuRate() {
        float totalCpuTime1 = (float) getTotalCpuTime();
        float processCpuTime1 = (float) getAppCpuTime();
        try {
            Thread.sleep(360);
        } catch (Exception e) {
        }
        return (100.0f * (((float) getAppCpuTime()) - processCpuTime1)) / (((float) getTotalCpuTime()) - totalCpuTime1);
    }

    public static long getTotalCpuTime() {
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return (((((Long.parseLong(cpuInfos[2]) + Long.parseLong(cpuInfos[3])) + Long.parseLong(cpuInfos[4])) + Long.parseLong(cpuInfos[6])) + Long.parseLong(cpuInfos[5])) + Long.parseLong(cpuInfos[7])) + Long.parseLong(cpuInfos[8]);
    }

    public static long getAppCpuTime() {
        String[] cpuInfos = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ((Long.parseLong(cpuInfos[13]) + Long.parseLong(cpuInfos[14])) + Long.parseLong(cpuInfos[15])) + Long.parseLong(cpuInfos[16]);
    }

    public static String getSre(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.meizu.stats", 0);
        String result = sharedPreferences.getString("screen_resolution", null);
        if (result != null) {
            return result;
        }
        Display display = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        result = width + "." + size.y;
        sharedPreferences.edit().putString("screen_resolution", result).apply();
        return result;
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

    public static boolean checkUmid(String umid, Context context) {
        if (TextUtils.isEmpty(umid)) {
            return false;
        }
        if (umid.length() < 35) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.meizu.stats", 0);
        StringBuilder result = new StringBuilder();
        String imei = getIMEI(context);
        String mac = sharedPreferences.getString(b.h, sMACAddress);
        StringBuilder trueMac = new StringBuilder();
        if (mac != null) {
            String[] temp = mac.split(":");
            if (temp != null) {
                for (String m : temp) {
                    trueMac.append(m.toUpperCase());
                }
            }
        }
        String md5 = bytesToHexString(getMD5((imei + trueMac.toString()).getBytes())).toUpperCase();
        int offsetValue = to16Int(umid.substring(0, 1));
        result.append(umid.substring(offsetValue + 1));
        result.append(umid.substring(1, offsetValue + 1));
        String tempLeft = result.substring(0, 16);
        String md5Left = md5.substring(0, 16);
        String tempRight = result.substring(17, 33);
        String md5Right = md5.substring(16);
        if (TextUtils.equals(tempLeft, md5Left) && TextUtils.equals(tempRight, md5Right)) {
            return true;
        }
        return false;
    }

    public static int to16Int(String value) {
        int result = 0;
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            if ("A".equals(value.toUpperCase())) {
                return 10;
            }
            if ("B".equals(value.toUpperCase())) {
                return 11;
            }
            if ("C".equals(value.toUpperCase())) {
                return 12;
            }
            if ("D".equals(value.toUpperCase())) {
                return 13;
            }
            if ("E".equals(value.toUpperCase())) {
                return 14;
            }
            if ("F".equals(value.toUpperCase())) {
                return 15;
            }
            return result;
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

    public static String getPackageVersion(String packageName, Context context) {
        if (TextUtils.isEmpty(packageName) || context == null) {
            return "";
        }
        PackageManager pm = context.getPackageManager();
        if (pm == null) {
            return "";
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
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
