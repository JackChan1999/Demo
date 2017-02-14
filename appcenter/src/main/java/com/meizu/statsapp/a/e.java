package com.meizu.statsapp.a;

import com.meizu.gslb.d.g;
import java.io.IOException;
import java.net.HttpURLConnection;

public class e implements g {
    private HttpURLConnection a;

    public e(HttpURLConnection connection) {
        this.a = connection;
    }

    public int a() throws IOException {
        return this.a.getResponseCode();
    }

    public HttpURLConnection b() {
        return this.a;
    }
}
