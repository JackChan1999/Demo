package com.meizu.volley.d;

import android.util.Pair;
import com.android.volley.l;
import com.android.volley.toolbox.e;
import com.meizu.gslb.d.b;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;

public class a implements e {
    public HttpResponse a(l<?> request, Map<String, String> additionalHeaders) throws IOException, com.android.volley.a {
        c client = new c();
        List<Pair<String, String>> headers = new ArrayList();
        if (additionalHeaders != null && additionalHeaders.size() > 0) {
            for (String key : additionalHeaders.keySet()) {
                headers.add(new Pair(key, additionalHeaders.get(key)));
            }
        }
        Map<String, String> baseHeaders = request.getHeaders();
        if (baseHeaders != null && baseHeaders.size() > 0) {
            for (String key2 : baseHeaders.keySet()) {
                headers.add(new Pair(key2, baseHeaders.get(key2)));
            }
        }
        try {
            return ((e) client.b(new d(request, headers))).b();
        } catch (b e) {
            if (e.getCause() == null || !(e.getCause() instanceof com.android.volley.a)) {
                throw new IOException(e.getCause());
            }
            throw ((com.android.volley.a) e.getCause());
        }
    }
}
