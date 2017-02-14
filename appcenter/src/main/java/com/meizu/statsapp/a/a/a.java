package com.meizu.statsapp.a.a;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public class a extends c {
    private static final byte[] i = EncodingUtils.getAsciiBytes("; filename=");
    private byte[] j;
    private String k;

    public a(String name, String filename, byte[] data, String contentType, String charset) {
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        if (charset == null) {
            charset = "ISO-8859-1";
        }
        super(name, contentType, charset, "binary");
        if (data == null) {
            throw new IllegalArgumentException("Source may not be null");
        }
        this.k = filename;
        this.j = data;
    }

    protected void a(OutputStream out) throws IOException {
        super.a(out);
        if (this.k != null) {
            out.write(i);
            out.write(c);
            out.write(EncodingUtils.getAsciiBytes(this.k));
            out.write(c);
        }
    }

    protected void b(OutputStream out) throws IOException {
        if (a() != 0) {
            byte[] tmp = new byte[4096];
            ByteArrayInputStream instream = new ByteArrayInputStream(this.j);
            while (true) {
                try {
                    int len = instream.read(tmp);
                    if (len < 0) {
                        break;
                    }
                    out.write(tmp, 0, len);
                } finally {
                    instream.close();
                }
            }
        }
    }

    protected long a() {
        return (long) this.j.length;
    }
}
