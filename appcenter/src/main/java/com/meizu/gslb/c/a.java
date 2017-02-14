package com.meizu.gslb.c;

import java.util.Map;

public class a {

    public static class a {
        public final String a;
        public Map<String, String> b;

        a(String str, Map<String, String> map) {
            this.a = str;
            this.b = map;
        }
    }

    public static a a(String r9, java.util.List<android.util.Pair<String, String>> r10, String[] r11) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextEntry(HashMap.java:925)
	at java.util.HashMap$KeyIterator.next(HashMap.java:956)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
        /*
        r2 = 0;
        r3 = 0;
        r0 = "MEIZU";
        r1 = 8000; // 0x1f40 float:1.121E-41 double:3.9525E-320;
        r4 = com.meizu.gslb.c.c.a(r0, r1, r3);
        r5 = new org.apache.http.client.methods.HttpPost;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5.<init>(r9);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r6 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r6.<init>();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r7 = r10.iterator();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x0018:
        r0 = r7.hasNext();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r0 == 0) goto L_0x0040;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x001e:
        r0 = r7.next();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = (android.util.Pair) r0;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r8 = new org.apache.http.message.BasicNameValuePair;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1 = r0.first;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1 = (java.lang.String) r1;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = r0.second;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = (java.lang.String) r0;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r8.<init>(r1, r0);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r6.add(r8);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        goto L_0x0018;
    L_0x0035:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r4 == 0) goto L_0x003e;
    L_0x003b:
        r4.a();
    L_0x003e:
        r0 = r2;
    L_0x003f:
        return r0;
    L_0x0040:
        r0 = new org.apache.http.client.entity.UrlEncodedFormEntity;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1 = "UTF-8";	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0.<init>(r6, r1);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5.setEntity(r0);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5 = r4.execute(r5);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = r5.getStatusLine();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = r0.getStatusCode();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r0 != r1) goto L_0x00be;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x005a:
        if (r11 == 0) goto L_0x00d7;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x005c:
        r0 = r11.length;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r0 <= 0) goto L_0x00d7;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x005f:
        r0 = new java.util.HashMap;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1 = r11.length;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r6 = r11.length;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1 = r3;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x0067:
        if (r1 >= r6) goto L_0x007b;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x0069:
        r3 = r11[r1];	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r7 = r5.getFirstHeader(r3);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r7 == 0) goto L_0x0078;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x0071:
        r7 = r7.getValue();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0.put(r3, r7);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x0078:
        r1 = r1 + 1;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        goto L_0x0067;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x007b:
        r1 = r0;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x007c:
        r0 = r5.getEntity();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = r0.getContent();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r6 = "UTF-8";	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5.<init>(r0, r6);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r3.<init>(r5);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5.<init>();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x0095:
        r6 = r3.readLine();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r6 == 0) goto L_0x00ac;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x009b:
        r6 = r5.append(r6);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r7 = "\n";	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r6.append(r7);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        goto L_0x0095;
    L_0x00a5:
        r0 = move-exception;
        if (r4 == 0) goto L_0x00ab;
    L_0x00a8:
        r4.a();
    L_0x00ab:
        throw r0;
    L_0x00ac:
        r0.close();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = new com.meizu.gslb.c.a$a;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r3 = r5.toString();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0.<init>(r3, r1);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        if (r4 == 0) goto L_0x003f;
    L_0x00ba:
        r4.a();
        goto L_0x003f;
    L_0x00be:
        r1 = new java.lang.Exception;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r3.<init>();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r5 = "server response code : ";	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r3 = r3.append(r5);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        r1.<init>(r0);	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
        throw r1;	 Catch:{ Exception -> 0x0035, all -> 0x00a5 }
    L_0x00d7:
        r1 = r2;
        goto L_0x007c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.gslb.c.a.a(java.lang.String, java.util.List, java.lang.String[]):com.meizu.gslb.c.a$a");
    }
}
