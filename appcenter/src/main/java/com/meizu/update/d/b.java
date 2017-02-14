package com.meizu.update.d;

import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.update.d.c.c;
import com.meizu.update.d.c.d;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class b implements d {
    private String a;
    private String b;
    private List<Pair<String, String>> c;
    private List<Pair<String, String>> d;
    private boolean e;
    private a f = null;
    private d g;
    private long h = 200;

    public interface a {
        void a(int i, long j);
    }

    public void a(List<Pair<String, String>> headers) {
        if (this.d != null) {
            for (Pair<String, String> newHead : headers) {
                for (Pair<String, String> oldHead : this.d) {
                    if (((String) newHead.first).equals(oldHead.first)) {
                        this.d.remove(oldHead);
                        break;
                    }
                }
            }
        }
        this.d = new ArrayList();
        this.d.addAll(headers);
    }

    public void a(String newUrl) {
        this.a = newUrl;
    }

    public void a(d checker) {
        this.g = checker;
    }

    public b(String url, String targetPath, List<Pair<String, String>> requestParams, List<Pair<String, String>> requestHeaders) {
        this.a = url;
        this.b = targetPath;
        this.c = requestParams;
        this.d = requestHeaders;
        this.e = false;
    }

    public void a(a downloadProgressLinstenr) {
        this.f = downloadProgressLinstenr;
    }

    public void a() {
        this.e = true;
    }

    public boolean a(boolean redirecting) throws a, e, h, c, f {
        IOException e;
        Throwable th;
        a e2;
        Exception e3;
        c();
        g client = null;
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        if (redirecting) {
            try {
                client = g.b("MEIZU", 20000);
            } catch (IOException e4) {
                e = e4;
                try {
                    e.printStackTrace();
                    if (client != null) {
                        try {
                            client.a();
                        } catch (Exception ignore) {
                            ignore.printStackTrace();
                            return false;
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (a e5) {
                e2 = e5;
                throw e2;
            } catch (e e6) {
                e = e6;
                throw e;
            } catch (h e7) {
                e = e7;
                throw e;
            } catch (c e8) {
                e = e8;
                throw e;
            } catch (Exception e9) {
                e3 = e9;
                e3.printStackTrace();
                throw new f(e3.getMessage());
            }
        }
        client = g.a("MEIZU", 20000);
        HttpUriRequest httpGet;
        if (this.c == null || this.c.size() <= 0) {
            httpGet = new HttpGet(this.a);
        } else {
            httpGet = new HttpPost(this.a);
            List<NameValuePair> par = new ArrayList();
            for (Pair<String, String> param : this.c) {
                par.add(new BasicNameValuePair((String) param.first, (String) param.second));
            }
            try {
                ((HttpPost) httpGet).setEntity(new UrlEncodedFormEntity(par, "UTF-8"));
            } catch (UnsupportedEncodingException e10) {
                e10.printStackTrace();
            }
        }
        if (this.d != null && this.d.size() > 0) {
            for (Pair<String, String> header : this.d) {
                post.setHeader((String) header.first, (String) header.second);
            }
        }
        long startPos = b(this.b);
        if (startPos > 0) {
            d("Start download pos : " + startPos);
            post.setHeader("Range", "bytes=" + startPos + "-");
        }
        e("Start connect...");
        HttpResponse response = client.execute(post);
        int responseCode = response.getStatusLine().getStatusCode();
        d("responseCode:" + responseCode);
        Header contentType = response.getEntity().getContentType();
        long contentLength = response.getEntity().getContentLength();
        e("content length:" + contentLength);
        e("contentType:" + contentType);
        if (responseCode == 200 || responseCode == 206) {
            c checkResult;
            inputStream = response.getEntity().getContent();
            long inStreamLength = response.getEntity().getContentLength();
            if (this.g != null) {
                checkResult = this.g.a(startPos, inStreamLength);
                if (!checkResult.b()) {
                    if (startPos > 0) {
                        c(this.b);
                        throw new f("Break point download size not match.");
                    }
                    throw new c(responseCode, checkResult.c());
                }
            }
            byte[] buffer = new byte[4096];
            long readCount = 0;
            FileOutputStream fileOutputStream = new FileOutputStream(new File(this.b), startPos > 0);
            try {
                long startTime = SystemClock.elapsedRealtime();
                long lastNotifyTime = 0;
                while (true) {
                    b();
                    int perReadSize = inputStream.read(buffer);
                    if (perReadSize > 0) {
                        fileOutputStream.write(buffer, 0, perReadSize);
                        fileOutputStream.flush();
                        readCount += (long) perReadSize;
                        long intervalSecond = (SystemClock.elapsedRealtime() - startTime) / 1000;
                        if (intervalSecond < 1) {
                            intervalSecond = 1;
                        }
                        long speed = readCount / intervalSecond;
                        int percent = (int) (((readCount + startPos) * 100) / (inStreamLength + startPos));
                        long timeTickNow = SystemClock.elapsedRealtime();
                        if (timeTickNow - lastNotifyTime > this.h || percent == 100) {
                            lastNotifyTime = timeTickNow;
                            if (this.f != null) {
                                this.f.a(percent, speed);
                            }
                        }
                    }
                    if (perReadSize == -1 || (inStreamLength > 0 && readCount >= inStreamLength)) {
                    }
                }
                if (inStreamLength <= 0 || readCount >= inStreamLength) {
                    if (this.g != null) {
                        checkResult = this.g.a(this.b);
                        if (!checkResult.b()) {
                            c(this.b);
                            throw new c(responseCode, checkResult.c());
                        }
                    }
                    if (client != null) {
                        try {
                            client.a();
                        } catch (Exception ignore2) {
                            ignore2.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    outputStream = fileOutputStream;
                    return true;
                }
                d("Download length not math: download length = " + readCount + " , in stream length = " + inStreamLength);
                throw new f("Download length not math: download length = " + readCount + " , in stream length = " + inStreamLength);
            } catch (IOException e11) {
                e = e11;
                outputStream = fileOutputStream;
                e.printStackTrace();
                if (client != null) {
                    client.a();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                return false;
            } catch (a e12) {
                e2 = e12;
                outputStream = fileOutputStream;
                throw e2;
            } catch (e e13) {
                e e14;
                e14 = e13;
                outputStream = fileOutputStream;
                throw e14;
            } catch (h e15) {
                h e16;
                e16 = e15;
                outputStream = fileOutputStream;
                throw e16;
            } catch (c e17) {
                c e18;
                e18 = e17;
                outputStream = fileOutputStream;
                throw e18;
            } catch (Exception e19) {
                e3 = e19;
                outputStream = fileOutputStream;
                e3.printStackTrace();
                throw new f(e3.getMessage());
            } catch (Throwable th3) {
                th = th3;
                outputStream = fileOutputStream;
                if (client != null) {
                    try {
                        client.a();
                    } catch (Exception ignore22) {
                        ignore22.printStackTrace();
                        throw th;
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th;
            }
        }
        String content;
        if (responseCode == 301 || responseCode == 302) {
            Header locationHeader = response.getFirstHeader("Location");
            if (locationHeader != null) {
                String relocateUrl = locationHeader.getValue();
                if (TextUtils.isEmpty(relocateUrl)) {
                    d("relocate url is empty!");
                } else {
                    throw new h(responseCode, relocateUrl);
                }
            }
            d("relocate no location header!");
        } else if (responseCode == 416) {
            d("request over range, error!");
            c(this.b);
        }
        if (contentLength > 1048576) {
            content = "Content to large to parse!";
        } else {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        throw new e(responseCode, content);
    }

    private void b() throws a {
        if (this.e) {
            throw new a();
        }
    }

    private void c() {
        File file = new File(this.b);
        if (!file.exists()) {
            File rootPath = file.getParentFile();
            if (!rootPath.exists()) {
                rootPath.mkdirs();
            }
        }
    }

    private long b(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    private void c(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void d(String msg) {
        com.meizu.update.h.b.d(msg);
    }

    private void e(String msg) {
        com.meizu.update.h.b.c(msg);
    }
}
