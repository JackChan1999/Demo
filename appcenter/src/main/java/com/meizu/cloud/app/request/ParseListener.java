package com.meizu.cloud.app.request;

public interface ParseListener<T> {
    T onParseResponse(String str);
}
