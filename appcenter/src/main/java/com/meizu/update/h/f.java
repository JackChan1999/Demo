package com.meizu.update.h;

import android.util.Pair;
import com.meizu.update.d.g;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

public class f {
    public static String a(String url, List<Pair<String, String>> params) {
        try {
            return b(url, params);
        } catch (a e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String b(String url, List<Pair<String, String>> params) throws a {
        g client = g.b("MEIZU", 30000);
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> par = new ArrayList();
            for (Pair<String, String> param : params) {
                par.add(new BasicNameValuePair((String) param.first, (String) param.second));
            }
            post.setEntity(new UrlEncodedFormEntity(par, "UTF-8"));
            HttpResponse response = client.execute(post);
            int responseCode = response.getStatusLine().getStatusCode();
            if (responseCode == 200) {
                InputStream stream = response.getEntity().getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                StringBuffer buf = new StringBuffer();
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    buf.append(line).append("\n");
                }
                stream.close();
                String stringBuffer = buf.toString();
                if (client != null) {
                    client.a();
                }
                return stringBuffer;
            }
            throw new a(responseCode, "Server response code : " + responseCode);
        } catch (a e) {
            throw e;
        } catch (Exception e2) {
            e2.printStackTrace();
            throw new a(e2.getMessage());
        } catch (Throwable th) {
            if (client != null) {
                client.a();
            }
        }
    }
}
