package com.meizu.cloud.download.service;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.meizu.cloud.download.b.d;
import com.meizu.cloud.download.b.e;
import com.meizu.cloud.download.b.f;
import com.meizu.cloud.download.b.g;
import com.meizu.cloud.download.c.g.b;
import com.meizu.cloud.download.c.g.c;
import com.meizu.cloud.download.c.h;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class a implements b<b> {
    private Context a;
    private DownloadTaskInfo b;
    private com.meizu.cloud.download.service.e.a c;
    private b d;
    private int e = 3;
    private e f;
    private int g = 10;

    public /* synthetic */ Object b(c cVar) {
        return a(cVar);
    }

    public a(Context context, DownloadTaskInfo taskInfo, com.meizu.cloud.download.service.e.a stateListener, e relocateHelper) {
        this.a = context;
        this.b = taskInfo;
        this.c = stateListener;
        this.d = new b();
        this.f = relocateHelper;
    }

    private void a(int state) {
        this.c.a(this.b, state);
    }

    private String a(c jc, Bundle outArgs) {
        return this.c.a(jc, this.b.b, new Bundle());
    }

    public b a(c jc) {
        String backupUrl;
        com.meizu.cloud.download.b.e.a transform;
        a(2);
        Bundle outArgs = new Bundle();
        String requestUrl = a(jc, outArgs);
        if (a(jc, null)) {
            this.d.b = 0;
            return this.d;
        } else if (requestUrl == null) {
            String message = outArgs.getString("fail_message", null);
            if (message != null) {
                this.c.a(message);
            }
            this.d.b = 7;
            return this.d;
        } else {
            String destFile = this.b.c;
            String ext = outArgs.getString("file_extension", null);
            if (!(ext == null || this.b.c.endsWith(ext))) {
                destFile = destFile + ext;
            }
            File file = new File(destFile);
            if (file.exists()) {
                this.d.a = file.getAbsolutePath();
                this.d.b = 0;
                return this.d;
            }
            String tmpFilePath = destFile + ".dat";
            if (!tmpFilePath.equals(this.b.k)) {
                this.b.k = tmpFilePath;
                this.c.a(this.b.mId, "temp_file", tmpFilePath);
            }
            File tmpFile = new File(tmpFilePath);
            this.b.e = 0;
            if (tmpFile.exists()) {
                this.b.e = tmpFile.length();
            }
            File parent = tmpFile.getParentFile();
            if ((parent.exists() || parent.mkdirs()) && parent.canWrite()) {
                long extraFileSize = 0;
                if (destFile.endsWith("apk")) {
                    extraFileSize = 0 + this.b.d;
                }
                long availableSize;
                if (tmpFilePath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath())) {
                    availableSize = h.a() + this.b.e;
                    if (availableSize <= this.b.d || availableSize < 209715200 + extraFileSize || availableSize < 2 * extraFileSize) {
                        this.d.b = 1;
                        return this.d;
                    }
                }
                availableSize = h.b(tmpFilePath.substring(0, tmpFilePath.lastIndexOf("/"))) + this.b.e;
                if (availableSize <= this.b.d || availableSize < 209715200 + extraFileSize || availableSize < 2 * extraFileSize) {
                    this.d.b = 1;
                    return this.d;
                }
                this.e = 3;
                String originalUrl = requestUrl;
                boolean usedProxy = false;
                boolean submittedError = false;
                List<Pair<String, String>> extraHeaders = null;
                boolean finished = false;
                while (!a(jc, tmpFile)) {
                    int i;
                    int[] iArr = new int[3];
                    iArr = new int[]{24, 12, 6};
                    boolean downloadSuccess = false;
                    boolean useBackupUrl = false;
                    String oldRequestUrl = requestUrl;
                    try {
                        downloadSuccess = a(jc, requestUrl, tmpFile, extraHeaders, true);
                        if (!a(jc, tmpFile)) {
                            if (!downloadSuccess) {
                                submittedError = true;
                                this.f.b(originalUrl, 100000, requestUrl, "Uncaugth http exception.");
                            } else if (usedProxy || submittedError) {
                                this.f.a(originalUrl, requestUrl, "Download success");
                            }
                        }
                    } catch (d e) {
                        a("Handle FileIllegalException!");
                        submittedError = true;
                        this.f.a(originalUrl, e.a(), requestUrl, e.getMessage());
                        backupUrl = this.f.a();
                        if (!TextUtils.isEmpty(backupUrl)) {
                            useBackupUrl = true;
                            this.g = 10;
                            requestUrl = backupUrl;
                        } else if (a(jc, tmpFile)) {
                            if (1 != null) {
                                this.f.b(originalUrl, requestUrl, "User Canceled");
                            }
                            this.d.b = 0;
                            return this.d;
                        } else {
                            transform = this.f.a(this.a, originalUrl);
                            if (transform != null) {
                                this.g = 10;
                                usedProxy = true;
                                requestUrl = transform.a;
                                if (transform.b != null) {
                                    extraHeaders = transform.b;
                                }
                                a("Trans to proxy server request:" + requestUrl);
                                this.f.a(false);
                            } else {
                                this.f.b(originalUrl, 100001, requestUrl, "Cant trans to proxy server.");
                                a("trans proxy failed, end error.");
                                this.d.b = 7;
                                return this.d;
                            }
                        }
                    } catch (g e2) {
                        String relocateUrl = e2.getMessage();
                        a("Relocate to: " + relocateUrl);
                        requestUrl = relocateUrl;
                        if (usedProxy) {
                            transform = this.f.b(this.a, relocateUrl);
                            if (transform != null) {
                                a("Relocate and re proxy success");
                                requestUrl = transform.a;
                                if (transform.b != null) {
                                    extraHeaders = transform.b;
                                }
                            }
                        }
                        if (a(jc, tmpFile)) {
                            if (submittedError) {
                                this.f.b(originalUrl, requestUrl, "User Canceled");
                            }
                            this.d.b = 0;
                            return this.d;
                        }
                        i = this.g - 1;
                        this.g = i;
                        if (i <= 0) {
                        }
                    } catch (com.meizu.cloud.download.b.b e3) {
                        int responseCode = e3.a();
                        a("LoadException: " + responseCode);
                        submittedError = true;
                        this.f.b(originalUrl, responseCode, requestUrl, "Http response code error");
                        if (usedProxy && responseCode == 401) {
                            a("Proxy auth exception:" + responseCode);
                            this.f.b();
                            if (a(jc, tmpFile)) {
                                if (1 != null) {
                                    this.f.b(originalUrl, requestUrl, "User Canceled");
                                }
                                this.d.b = 0;
                                return this.d;
                            }
                            transform = this.f.a(this.a, originalUrl);
                            if (transform != null) {
                                requestUrl = transform.a;
                                extraHeaders = transform.b;
                                a("Re proxy success");
                            }
                        }
                    } catch (f e4) {
                        submittedError = true;
                        this.f.b(originalUrl, 100000, requestUrl, e4.getMessage());
                    }
                    if (downloadSuccess) {
                        finished = true;
                    } else {
                        i = this.e - 1;
                        this.e = i;
                        if (i > 0) {
                            if (a(jc, tmpFile)) {
                                if (submittedError) {
                                    this.f.b(originalUrl, requestUrl, "User Canceled");
                                }
                                this.d.b = 0;
                                return this.d;
                            }
                            if (!useBackupUrl) {
                                backupUrl = this.f.a();
                                if (!TextUtils.isEmpty(backupUrl)) {
                                    this.g = 10;
                                    requestUrl = backupUrl;
                                }
                            }
                            boolean networkChanging = this.c.a();
                            int waitCnt = iArr[this.e];
                            while (this.c.a()) {
                                waitCnt--;
                                if (waitCnt < 0) {
                                    break;
                                }
                                if (a(jc, tmpFile, 1000, this.d)) {
                                    if (submittedError) {
                                        this.f.b(originalUrl, requestUrl, "User Canceled");
                                    }
                                    return this.d;
                                } else if (a(jc, tmpFile)) {
                                    if (submittedError) {
                                        this.f.b(originalUrl, requestUrl, "User Canceled");
                                    }
                                    this.d.b = 0;
                                    return this.d;
                                }
                            }
                            if (!networkChanging) {
                                if (a(jc, tmpFile, 6000, this.d)) {
                                    return this.d;
                                }
                            }
                            if (a(jc, tmpFile)) {
                                if (submittedError) {
                                    this.f.b(originalUrl, requestUrl, "User Canceled");
                                }
                                this.d.b = 0;
                                return this.d;
                            }
                            if (!(TextUtils.isEmpty(oldRequestUrl) || oldRequestUrl.equals(requestUrl))) {
                                this.e = 3;
                            }
                            if (this.e <= 0) {
                            }
                        }
                    }
                    if (a(jc, tmpFile)) {
                        if (submittedError) {
                            this.f.b(originalUrl, requestUrl, "User Canceled");
                        }
                        this.d.b = 0;
                        return this.d;
                    } else if (finished) {
                        tmpFile.renameTo(file);
                        this.d.a = file.getAbsolutePath();
                        this.d.b = 0;
                        return this.d;
                    } else {
                        this.d.b = 7;
                        return this.d;
                    }
                }
                if (submittedError) {
                    this.f.b(originalUrl, requestUrl, "User Canceled");
                }
                this.d.b = 0;
                return this.d;
            }
            this.d.b = 2;
            return this.d;
        }
    }

    private boolean a(c jc, File tmpFile, long sleepTime, b result) {
        while (sleepTime > 0) {
            try {
                if (a(jc, tmpFile)) {
                    result.b = 0;
                    return true;
                }
                Thread.sleep(250);
                sleepTime -= 250;
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    private boolean a(c jc, String url, File tmpFile, List<Pair<String, String>> headers, boolean redirecting) throws com.meizu.cloud.download.b.b, f {
        f e;
        Exception e2;
        final Thread currentThread = Thread.currentThread();
        jc.a(new com.meizu.cloud.download.c.g.a(this) {
            final /* synthetic */ a b;

            public void a() {
                currentThread.interrupt();
            }
        });
        h client = null;
        Closeable input = null;
        Closeable output = null;
        if (redirecting) {
            try {
                client = h.a("MEIZU", 30000);
            } catch (com.meizu.cloud.download.b.b e3) {
                e = e3;
                try {
                    com.meizu.cloud.download.b.b e4;
                    throw e4;
                } catch (Throwable th) {
                    Throwable th2 = th;
                }
            } catch (f e5) {
                e = e5;
                throw e;
            } catch (Exception e6) {
                e2 = e6;
                if (jc.b()) {
                    h.a("Task is cancelled: %s", this.b.j);
                } else {
                    e2.printStackTrace();
                }
                throw new f(e2.getMessage());
            }
        }
        client = h.b("MEIZU", 30000);
        HttpUriRequest httpGet = new HttpGet(url);
        if (headers != null && headers.size() > 0) {
            for (Pair<String, String> header : headers) {
                httpGet.setHeader((String) header.first, (String) header.second);
            }
        }
        this.b.e = tmpFile.length();
        boolean append = false;
        if (this.b.e > 0) {
            append = true;
            httpGet.setHeader("Range", "bytes=" + this.b.e + "-");
        }
        if (a(jc, tmpFile)) {
            a("connect finally");
            if (client != null) {
                client.a();
            }
            h.a(null);
            h.a(null);
            jc.a(null);
            Thread.interrupted();
            return false;
        }
        a("Start connect...");
        HttpResponse response = client.execute(httpGet);
        int responseCode = response.getStatusLine().getStatusCode();
        a("responseCode:" + responseCode);
        if (a(jc, tmpFile)) {
            a("connect finally");
            if (client != null) {
                client.a();
            }
            h.a(null);
            h.a(null);
            jc.a(null);
            Thread.interrupted();
            return false;
        }
        Header contentType = response.getEntity().getContentType();
        a("content length:" + response.getEntity().getContentLength());
        a("contentType:" + contentType);
        if (a(jc, tmpFile)) {
            a("connect finally");
            if (client != null) {
                client.a();
            }
            h.a(null);
            h.a(null);
            jc.a(null);
            Thread.interrupted();
            return false;
        } else if (responseCode != 200 && responseCode != 206) {
            if (responseCode == 416) {
                this.b.e = 0;
                a(tmpFile);
            } else if (responseCode == 301 || responseCode == 302) {
                Header locationHeader = response.getFirstHeader("Location");
                if (locationHeader != null) {
                    String relocateUrl = locationHeader.getValue();
                    if (TextUtils.isEmpty(relocateUrl)) {
                        a("relocate url is empty!");
                    } else {
                        throw new g(responseCode, relocateUrl);
                    }
                }
                a("relocate no location header!");
            }
            throw new com.meizu.cloud.download.b.b(responseCode, "Http response code Error");
        } else if (contentType == null || contentType.getValue() == null || !contentType.getValue().contains("text/html")) {
            input = response.getEntity().getContent();
            Closeable fileOutputStream = new FileOutputStream(tmpFile, append);
            try {
                long fileSize = response.getEntity().getContentLength();
                com.meizu.cloud.download.b.a checkResult = this.f.a(this.b.e, fileSize);
                if (checkResult.b()) {
                    if (fileSize >= 0) {
                        fileSize += this.b.e;
                        if (this.b.d != fileSize) {
                            this.b.d = fileSize;
                            this.c.a(this.b.mId, "file_size", fileSize);
                        }
                    }
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;
                    int totalBytesRead = 0;
                    long lastTime = System.currentTimeMillis();
                    a("start download");
                    while (bytesRead != -1) {
                        this.e = 3;
                        if (a(jc, tmpFile)) {
                            a("connect finally");
                            if (client != null) {
                                client.a();
                            }
                            h.a(input);
                            h.a(fileOutputStream);
                            jc.a(null);
                            Thread.interrupted();
                            output = fileOutputStream;
                            return false;
                        }
                        bytesRead = input.read(buffer);
                        if (bytesRead > 0) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                            totalBytesRead += bytesRead;
                            DownloadTaskInfo downloadTaskInfo = this.b;
                            downloadTaskInfo.e += (long) bytesRead;
                            long currentTime = System.currentTimeMillis();
                            long diff = currentTime - lastTime;
                            if (diff < 0 || diff > 500 || this.b.e == this.b.d) {
                                this.b.f = diff > 0 ? (int) ((((long) totalBytesRead) * 1000) / diff) : 0;
                                this.b.g = -1;
                                if (totalBytesRead > 0 && this.b.d > 0 && this.b.d >= this.b.e) {
                                    this.b.g = (int) (((this.b.d - this.b.e) * diff) / ((long) totalBytesRead));
                                }
                                this.b.h = 2;
                                a();
                                totalBytesRead = 0;
                                lastTime = currentTime;
                            }
                        }
                    }
                    if (a(jc, tmpFile)) {
                        a("connect finally");
                        if (client != null) {
                            client.a();
                        }
                        h.a(input);
                        h.a(fileOutputStream);
                        jc.a(null);
                        Thread.interrupted();
                        output = fileOutputStream;
                        return false;
                    }
                    a(7);
                    checkResult = this.f.a(tmpFile.getAbsolutePath());
                    if (checkResult.b()) {
                        a("connect finally");
                        if (client != null) {
                            client.a();
                        }
                        h.a(input);
                        h.a(fileOutputStream);
                        jc.a(null);
                        Thread.interrupted();
                        output = fileOutputStream;
                        return true;
                    }
                    a(tmpFile);
                    throw new d(responseCode, checkResult.c());
                } else if (this.b.e > 0) {
                    a(tmpFile);
                    throw new f("Break point download size not match.");
                } else {
                    throw new d(responseCode, checkResult.c());
                }
            } catch (com.meizu.cloud.download.b.b e7) {
                e4 = e7;
                output = fileOutputStream;
            } catch (f e8) {
                e = e8;
                output = fileOutputStream;
            } catch (Exception e9) {
                e2 = e9;
                output = fileOutputStream;
            } catch (Throwable th3) {
                th2 = th3;
                output = fileOutputStream;
            }
        } else {
            throw new f("Unknown contentType:" + contentType);
        }
        a("connect finally");
        if (client != null) {
            client.a();
        }
        h.a(input);
        h.a(output);
        jc.a(null);
        Thread.interrupted();
        throw th2;
    }

    private boolean a(c jc, File tmpFile) {
        if (!jc.b()) {
            return false;
        }
        if (this.b.m) {
            this.c.a(this.b, this.b.n);
        } else {
            a(3);
        }
        return true;
    }

    private void a() {
        this.c.a(this.b);
        this.c.a(this.b.mId, this.b.e);
    }

    private void a(String msg) {
        Log.e("MzUpdateComponent", msg);
    }

    private void a(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
