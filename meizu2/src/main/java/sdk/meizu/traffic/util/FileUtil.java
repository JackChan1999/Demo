package sdk.meizu.traffic.util;

import android.content.Context;
import android.text.TextUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import org.apache.http.util.EncodingUtils;

public class FileUtil {
    private static final int ASSETS_SUFFIX_BEGIN = 101;
    private static final int ASSETS_SUFFIX_END = 102;

    public static final boolean deleteFile(String str) {
        makeParentPath(str);
        File file = new File(str);
        if (file.isDirectory()) {
            return false;
        }
        return file.delete();
    }

    public static final void deleteDirectory(String str) {
        File file = new File(str);
        if (file.exists() && !file.delete()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].isDirectory()) {
                    listFiles[i].delete();
                } else if (!listFiles[i].delete()) {
                    deleteDirectory(listFiles[i].getAbsolutePath());
                }
            }
            file.delete();
        }
    }

    public static final void makeParentPath(String str) {
        File parentFile = new File(str).getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            parentFile.mkdirs();
        }
    }

    public static void writeFileSdcardFile(Context context, String str, String str2) {
        makeParentPath(str);
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes());
            openFileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFileSdcardFile(String str) {
        String string;
        Exception e;
        makeParentPath(str);
        String str2 = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            byte[] bArr = new byte[fileInputStream.available()];
            fileInputStream.read(bArr);
            string = EncodingUtils.getString(bArr, "UTF-8");
            try {
                fileInputStream.close();
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return string;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            string = str2;
            e = exception;
            e.printStackTrace();
            return string;
        }
        return string;
    }

    public static boolean unzipFile(String str, String str2) {
        File file = !TextUtils.isEmpty(str) ? new File(str) : null;
        if (file != null) {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            try {
                ZipFile zipFile = new ZipFile(file);
                while (true) {
                    ZipEntry nextEntry = zipInputStream.getNextEntry();
                    if (nextEntry == null) {
                        break;
                    }
                    File file2 = new File(str2 + nextEntry.getName());
                    if (nextEntry.isDirectory()) {
                        file2.mkdirs();
                    } else {
                        if (!file2.exists()) {
                            file2.createNewFile();
                        }
                        InputStream inputStream = zipFile.getInputStream(nextEntry);
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.close();
                        inputStream.close();
                    }
                }
                file.delete();
            } finally {
                zipInputStream.closeEntry();
                zipInputStream.close();
            }
        }
        return true;
    }

    public static boolean unzipAssertFile(InputStream inputStream, String str) {
        Throwable th;
        ZipInputStream zipInputStream;
        FileOutputStream fileOutputStream = null;
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file.isDirectory()) {
            throw new IOException("Invalid Unzip destination " + file);
        } else if (inputStream == null) {
            throw new IOException("InputStream is null");
        } else {
            try {
                ZipInputStream zipInputStream2 = new ZipInputStream(inputStream);
                while (true) {
                    ZipEntry nextEntry = zipInputStream2.getNextEntry();
                    if (nextEntry == null) {
                        break;
                    }
                    String str2 = file.getAbsolutePath() + File.separator + nextEntry.getName();
                    String name = nextEntry.getName();
                    if (name.charAt(name.length() - 1) == File.separatorChar) {
                        File file2 = new File(str2);
                        if (!(file2.exists() || file2.mkdirs())) {
                            throw new IOException("Unable to create folder " + file2);
                        }
                    }
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(str2);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = zipInputStream2.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream2.write(bArr, 0, read);
                            }
                            fileOutputStream = fileOutputStream2;
                        } catch (Throwable th2) {
                            th = th2;
                            fileOutputStream = fileOutputStream2;
                            zipInputStream = zipInputStream2;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        zipInputStream = zipInputStream2;
                    }
                }
                if (zipInputStream2 != null) {
                    zipInputStream2.closeEntry();
                    zipInputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return true;
            } catch (Throwable th4) {
                th = th4;
                zipInputStream = null;
                if (zipInputStream != null) {
                    zipInputStream.closeEntry();
                    zipInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        }
    }

    public static boolean copyAssertFileTo(Context context, String str, String str2) {
        OutputStream fileOutputStream;
        Throwable th;
        InputStream inputStream = null;
        try {
            fileOutputStream = new FileOutputStream(str2);
            try {
                inputStream = context.getAssets().open(str);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                return true;
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    public static boolean exist(String str) {
        return new File(str).exists();
    }
}
