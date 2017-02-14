package com.meizu.volley.d;

import com.meizu.gslb.d.g;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class e implements g {
    private HttpURLConnection a;

    public e(HttpURLConnection connection) {
        this.a = connection;
    }

    public int a() throws IOException {
        return this.a.getResponseCode();
    }

    public HttpResponse b() throws IOException {
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        if (this.a.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        BasicHttpResponse response = new BasicHttpResponse(new BasicStatusLine(protocolVersion, this.a.getResponseCode(), this.a.getResponseMessage()));
        response.setEntity(a(this.a));
        for (Entry<String, List<String>> header : this.a.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                response.addHeader(new BasicHeader((String) header.getKey(), (String) ((List) header.getValue()).get(0)));
            }
        }
        return response;
    }

    private static HttpEntity a(HttpURLConnection connection) {
        InputStream inputStream;
        BasicHttpEntity entity = new BasicHttpEntity();
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            inputStream = connection.getErrorStream();
        }
        entity.setContent(inputStream);
        entity.setContentLength((long) connection.getContentLength());
        entity.setContentEncoding(connection.getContentEncoding());
        entity.setContentType(connection.getContentType());
        return entity;
    }
}
