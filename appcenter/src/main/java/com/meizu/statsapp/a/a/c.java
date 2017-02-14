package com.meizu.statsapp.a.a;

public abstract class c extends b {
    private String i;
    private String j;
    private String k;
    private String l;

    public c(String name, String contentType, String charSet, String transferEncoding) {
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        this.i = name;
        this.j = contentType;
        this.k = charSet;
        this.l = transferEncoding;
    }

    public String b() {
        return this.i;
    }

    public String c() {
        return this.j;
    }

    public String d() {
        return this.k;
    }

    public String e() {
        return this.l;
    }
}
