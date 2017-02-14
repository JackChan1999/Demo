package com.meizu.mstore.license;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.alibaba.fastjson.asm.Opcodes;
import com.meizu.cloud.app.utils.c;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.c.c.a;
import com.meizu.e.e;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Calendar;

public class b {
    private static final String b = b.class.getSimpleName();
    private static final boolean c = c.b;
    private static Signature d;
    Context a = null;
    private String e = null;
    private String f;
    private String g;

    static {
        try {
            d = Signature.getInstance("SHA1WithRSA");
        } catch (NoSuchAlgorithmException e) {
            if (c) {
                Log.e(c.a, e.getMessage());
            }
        }
    }

    public b(Context context, String licenseFileName, String testLicenseFilePath) {
        this.a = context;
        this.f = licenseFileName;
        this.g = testLicenseFilePath;
    }

    private int a(Calendar cal) {
        return ((cal.get(1) * 10000) + ((cal.get(2) + 1) * 100)) + cal.get(5);
    }

    public LicenseResult a(String packName, String version, int packType, String path) {
        Exception e;
        FileInputStream fileInputStream;
        LicenseResult result = new LicenseResult();
        File hFile = new File(this.a.getDir(this.f, 0).getPath() + File.separator + packName + ".license");
        if (!hFile.exists()) {
            try {
                File hFile2 = new File(this.g + packName + ".license");
                try {
                    if (hFile2.exists() && hFile2.isFile()) {
                        hFile = hFile2;
                    } else {
                        result.a(-2);
                        if (c) {
                            Log.e(b, "License file not found");
                        }
                        hFile = hFile2;
                        return result;
                    }
                } catch (Exception e2) {
                    e = e2;
                    hFile = hFile2;
                    if (c) {
                        Log.e(b, "read test license file exception!");
                        e.printStackTrace();
                    }
                    result.a(-2);
                    return result;
                }
            } catch (Exception e3) {
                e = e3;
                if (c) {
                    Log.e(b, "read test license file exception!");
                    e.printStackTrace();
                }
                result.a(-2);
                return result;
            }
        }
        try {
            int nRead;
            fileInputStream = new FileInputStream(hFile);
            ByteArrayOutputStream fileArray = new ByteArrayOutputStream(1024);
            byte[] tempBuf = new byte[1024];
            while (true) {
                nRead = fileInputStream.read(tempBuf);
                if (nRead == -1) {
                    break;
                }
                fileArray.write(tempBuf, 0, nRead);
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            byte[] buffer = fileArray.toByteArray();
            nRead = buffer.length;
            if (nRead >= 268) {
                ByteArrayOutputStream byteArrayOutputStream;
                byte[] subProductBytes;
                if (nRead > 268) {
                    byteArrayOutputStream = new ByteArrayOutputStream(128);
                    byteArrayOutputStream.write(buffer, 268, nRead - 268);
                    subProductBytes = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                } else {
                    subProductBytes = new byte[0];
                }
                if (c) {
                    Log.e(b, a.a(buffer, 0, nRead));
                }
                CertificateFactory certity = CertificateFactory.getInstance("X.509");
                InputStream in = this.a.getAssets().open("license.cer");
                Certificate cer = certity.generateCertificate(in);
                in.close();
                PublicKey publicKey = cer.getPublicKey();
                if (buffer[0] == (byte) 77 && ((buffer[1] == (byte) 65 || buffer[1] == (byte) 66) && (buffer[2] == (byte) 1 || buffer[2] == (byte) 2))) {
                    byte[] md5;
                    boolean bTest = false;
                    if (buffer[2] == (byte) 2) {
                        bTest = true;
                    }
                    byte bPurchaseType = buffer[3];
                    switch (packType) {
                        case 0:
                            md5 = a(packName, bTest);
                            break;
                        default:
                            md5 = a(packName, bTest);
                            break;
                    }
                    if (md5 != null && md5.length > 0) {
                        if (c) {
                            Log.e("md5:", a.a(md5));
                        }
                        byteArrayOutputStream = new ByteArrayOutputStream(287);
                        byteArrayOutputStream.write(bPurchaseType);
                        byteArrayOutputStream.write(buffer, Opcodes.IINC, 128);
                        byteArrayOutputStream.write(packName.getBytes());
                        String sn = d.b(this.a);
                        String imei_sn = d.a(d.a(this.a), sn);
                        if (buffer[1] == (byte) 65) {
                            byteArrayOutputStream.write(sn.getBytes());
                        } else {
                            byteArrayOutputStream.write(imei_sn.getBytes());
                        }
                        byteArrayOutputStream.write(md5);
                        byteArrayOutputStream.write(buffer, 260, 8);
                        byteArrayOutputStream.write(subProductBytes);
                        byte[] storeByte = byteArrayOutputStream.toByteArray();
                        if (c) {
                            Log.e(b, a.a(storeByte));
                        }
                        d.initVerify(publicKey);
                        d.update(storeByte);
                        if (d.verify(buffer, 4, 128)) {
                            result.a(1);
                            result.b(buffer, Opcodes.IINC, 128);
                            ByteArrayOutputStream apk_plain = new ByteArrayOutputStream(287);
                            apk_plain.write(1);
                            apk_plain.write(bPurchaseType);
                            apk_plain.write(packName.getBytes());
                            if (buffer[1] == (byte) 65) {
                                apk_plain.write(sn.getBytes());
                            } else {
                                apk_plain.write(imei_sn.getBytes());
                            }
                            apk_plain.write(md5);
                            apk_plain.write(buffer, 260, 8);
                            apk_plain.write(subProductBytes);
                            byte[] apkByte = apk_plain.toByteArray();
                            if (c) {
                                Log.e(b, a.a(apkByte));
                            }
                            result.a(apkByte, 0, apkByte.length);
                            if (subProductBytes.length > 0 && subProductBytes.length % 11 == 0) {
                                for (int i = 0; i < subProductBytes.length; i += 11) {
                                    SubProduct subProduct = new SubProduct();
                                    subProduct.a(new String(subProductBytes, i, 3).trim());
                                    subProduct.a(a(subProductBytes, i + 3));
                                    subProduct.b(a(subProductBytes, i + 7));
                                    result.b().add(subProduct);
                                }
                            } else if (subProductBytes.length != 0 && c) {
                                Log.e(b, "subProduct length error:" + String.valueOf(subProductBytes.length));
                            }
                            if (bPurchaseType == (byte) 1) {
                                if (a(a(buffer, 260), a(buffer, 264))) {
                                    result.b(bPurchaseType);
                                } else {
                                    result.b(2);
                                }
                            } else {
                                result.b(bPurchaseType);
                            }
                            result.c(a(buffer, 260));
                        } else if (c) {
                            Log.e(b, "License mstore signature not equals");
                        }
                    } else if (c) {
                        Log.e(b, "Get APK md5 error");
                    }
                } else if (c) {
                    Log.e(b, "License file begin not right");
                }
            } else if (c) {
                Log.e(b, "License file'length not right");
            }
        } catch (Exception e42) {
            if (c) {
                e42.printStackTrace();
                Log.e(b, "exception:" + e42.getMessage());
            }
        } catch (Throwable th) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e422) {
                    e422.printStackTrace();
                }
            }
        }
        return result;
    }

    private static int a(byte[] bytes, int offset) {
        if (bytes.length < offset + 4) {
            return 0;
        }
        return (((bytes[offset + 3] & 255) | ((bytes[offset + 2] << 8) & 65280)) | ((bytes[offset + 1] << 24) >>> 8)) | (bytes[offset] << 24);
    }

    private byte[] a(String packName, boolean bTest) {
        if (bTest) {
            return a.b(packName.getBytes());
        }
        String filePath = null;
        try {
            ApplicationInfo ai = this.a.getPackageManager().getApplicationInfo(packName, 0);
            if (ai != null) {
                filePath = ai.sourceDir;
            }
        } catch (Exception e) {
            if (c.b) {
                e.printStackTrace();
            }
        }
        if (filePath != null) {
            return e.b(filePath);
        }
        if (c) {
            Log.e(b, "can not find apk dir");
        }
        return null;
    }

    private boolean a(int startDate, int endDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return a(cal) <= endDate;
    }
}
