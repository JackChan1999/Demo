package com.meizu.e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class k {
    private static Pattern f = Pattern.compile("&#([0-9]{3,5});");
    private boolean a = false;
    private int b;
    private String c = null;
    private InputStream d;
    private boolean e = false;

    public k(int statusCode, InputStream contentStream, String contentEncoding) throws IOException {
        this.b = statusCode;
        if (contentEncoding == null || !contentEncoding.trim().toLowerCase().equals("gzip")) {
            this.d = contentStream;
        } else {
            this.d = new GZIPInputStream(contentStream);
        }
        c();
    }

    public k(String content, int responseCode) {
        this.c = content;
        this.b = responseCode;
    }

    private void c() throws IOException {
        try {
            InputStream stream = d();
            if (stream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuffer buf = new StringBuffer();
                while (true) {
                    String line = br.readLine();
                    if (line != null) {
                        buf.append(line).append("\n");
                    } else {
                        this.c = buf.toString();
                        a(this.c);
                        stream.close();
                        this.e = true;
                        return;
                    }
                }
            }
        } catch (NullPointerException npe) {
            throw new IOException(npe.getMessage(), npe);
        }
    }

    public int a() {
        return this.b;
    }

    private InputStream d() {
        if (!this.e) {
            return this.d;
        }
        throw new IllegalStateException("Stream has already been consumed.");
    }

    public String b() {
        return this.c;
    }

    public String toString() {
        if (this.c != null) {
            return this.c;
        }
        return "Response{statusCode=" + this.b + ", responseString='" + this.c + '\'' + ", is=" + this.d + '}';
    }

    private void a(String message) {
        if (this.a) {
            System.out.println("[" + new Date() + "]" + message);
        }
    }
}
