package com.meizu.statsapp.a.a;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.util.EncodingUtils;

public class d extends c {
    private byte[] i;
    private String j;

    public d(String name, String value, String charset) {
        String str = "text/plain";
        if (charset == null) {
            charset = "US-ASCII";
        }
        super(name, str, charset, "8bit");
        if (value == null) {
            value = "";
        }
        if (value.indexOf(0) != -1) {
            throw new IllegalArgumentException("NULs may not be present in string parts");
        }
        this.j = value;
    }

    public d(String name, String value) {
        this(name, value, null);
    }

    private byte[] g() {
        if (this.i == null) {
            this.i = EncodingUtils.getBytes(this.j, d());
        }
        return this.i;
    }

    protected void b(OutputStream out) throws IOException {
        out.write(g());
    }

    protected long a() {
        return (long) g().length;
    }
}
