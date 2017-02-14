package com.meizu.statsapp.a.a;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public abstract class b {
    public static final byte[] a = EncodingUtils.getAsciiBytes("----------------314159265358979323846");
    protected static final byte[] b = EncodingUtils.getAsciiBytes("\r\n");
    protected static final byte[] c = EncodingUtils.getAsciiBytes("\"");
    protected static final byte[] d = EncodingUtils.getAsciiBytes("--");
    protected static final byte[] e = EncodingUtils.getAsciiBytes("Content-Disposition: form-data; name=");
    protected static final byte[] f = EncodingUtils.getAsciiBytes("Content-Type: ");
    protected static final byte[] g = EncodingUtils.getAsciiBytes("; charset=");
    protected static final byte[] h = EncodingUtils.getAsciiBytes("Content-Transfer-Encoding: ");
    private static final byte[] i = a;
    private byte[] j;

    protected abstract long a() throws IOException;

    public abstract String b();

    protected abstract void b(OutputStream outputStream) throws IOException;

    public abstract String c();

    public abstract String d();

    public abstract String e();

    protected byte[] f() {
        if (this.j == null) {
            return i;
        }
        return this.j;
    }

    void a(byte[] boundaryBytes) {
        this.j = boundaryBytes;
    }

    protected void c(OutputStream out) throws IOException {
        out.write(d);
        out.write(f());
        out.write(b);
    }

    protected void a(OutputStream out) throws IOException {
        out.write(e);
        out.write(c);
        out.write(EncodingUtils.getAsciiBytes(b()));
        out.write(c);
    }

    protected void d(OutputStream out) throws IOException {
        String contentType = c();
        if (contentType != null) {
            out.write(b);
            out.write(f);
            out.write(EncodingUtils.getAsciiBytes(contentType));
            String charSet = d();
            if (charSet != null) {
                out.write(g);
                out.write(EncodingUtils.getAsciiBytes(charSet));
            }
        }
    }

    protected void e(OutputStream out) throws IOException {
        String transferEncoding = e();
        if (transferEncoding != null) {
            out.write(b);
            out.write(h);
            out.write(EncodingUtils.getAsciiBytes(transferEncoding));
        }
    }

    protected void f(OutputStream out) throws IOException {
        out.write(b);
        out.write(b);
    }

    protected void g(OutputStream out) throws IOException {
        out.write(b);
    }

    public void h(OutputStream out) throws IOException {
        c(out);
        a(out);
        d(out);
        e(out);
        f(out);
        b(out);
        g(out);
    }

    public String toString() {
        return b();
    }

    public static void a(OutputStream out, b[] parts, byte[] partBoundary) throws IOException {
        if (parts == null) {
            throw new IllegalArgumentException("Parts may not be null");
        } else if (partBoundary == null || partBoundary.length == 0) {
            throw new IllegalArgumentException("partBoundary may not be empty");
        } else {
            for (int i = 0; i < parts.length; i++) {
                parts[i].a(partBoundary);
                parts[i].h(out);
            }
            out.write(d);
            out.write(partBoundary);
            out.write(d);
            out.write(b);
        }
    }
}
