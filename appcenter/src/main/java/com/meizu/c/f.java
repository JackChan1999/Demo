package com.meizu.c;

public class f extends Exception {
    private static final long serialVersionUID = -2623309261327598087L;
    private int a = -1;
    private int b = -1;

    public f(String msg, int statusCode) {
        super(msg);
        this.a = statusCode;
    }

    public f(Exception cause, int statusCode, int exceptionTypeCode) {
        super(cause);
        this.a = statusCode;
        this.b = exceptionTypeCode;
    }
}
