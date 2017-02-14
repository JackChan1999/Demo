package com.meizu.volley.b;

import org.apache.http.NameValuePair;

public class a implements NameValuePair {
    private final String a;
    private final String b;

    public a(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.a = name;
        this.b = value;
    }

    public String getName() {
        return this.a;
    }

    public String getValue() {
        return this.b;
    }
}
