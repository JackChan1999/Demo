package com.meizu.common.renderer.effect.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LongSparseArray;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLRenderManager;
import java.lang.ref.WeakReference;

public class EGLBitmapCache {
    private static LongSparseArray<WeakReference<EGLBitmap>> sBitmapCache = new LongSparseArray();
    private static LongSparseArray<WeakReference<EGLBitmap>> sResBitmapCache = new LongSparseArray();

    public static EGLBitmap get(Bitmap bitmap) {
        EGLBitmap eGLBitmap;
        synchronized (EGLBitmapCache.class) {
            long bitmapNativeId = Utils.getBitmapNativeId(bitmap);
            WeakReference weakReference = (WeakReference) sBitmapCache.get(bitmapNativeId);
            eGLBitmap = weakReference == null ? null : (EGLBitmap) weakReference.get();
            if (eGLBitmap == null || !eGLBitmap.isValid()) {
                eGLBitmap = new EGLBitmap(bitmap);
                sBitmapCache.put(bitmapNativeId, new WeakReference(eGLBitmap));
            } else if (eGLBitmap.getGenerationId() != bitmap.getGenerationId()) {
                eGLBitmap.setBitmap(bitmap);
                Log.w(GLRenderManager.TAG, "EGLBitmapCache: texture.getGenerationId() != bitmap.getGenerationId()");
            }
        }
        return eGLBitmap;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.meizu.common.renderer.effect.EGLBitmap get(int r8) {
        /*
        r1 = 0;
        if (r8 > 0) goto L_0x0005;
    L_0x0003:
        r0 = r1;
    L_0x0004:
        return r0;
    L_0x0005:
        r2 = com.meizu.common.renderer.effect.cache.EGLBitmapCache.class;
        monitor-enter(r2);
        r4 = (long) r8;
        r0 = sResBitmapCache;	 Catch:{ all -> 0x003f }
        r0 = r0.get(r4);	 Catch:{ all -> 0x003f }
        r0 = (java.lang.ref.WeakReference) r0;	 Catch:{ all -> 0x003f }
        if (r0 != 0) goto L_0x0042;
    L_0x0013:
        r0 = r1;
    L_0x0014:
        if (r0 != 0) goto L_0x005f;
    L_0x0016:
        r0 = com.meizu.common.renderer.effect.GLRenderManager.getInstance();	 Catch:{ all -> 0x003f }
        r0 = r0.getAppContext();	 Catch:{ all -> 0x003f }
        r0 = r0.getResources();	 Catch:{ all -> 0x003f }
        r0 = r0.getDrawable(r8);	 Catch:{ all -> 0x003f }
        r0 = (android.graphics.drawable.BitmapDrawable) r0;	 Catch:{ all -> 0x003f }
        r3 = r0.getBitmap();	 Catch:{ all -> 0x003f }
        r0 = sBitmapCache;	 Catch:{ all -> 0x003f }
        r6 = com.meizu.common.renderer.Utils.getBitmapNativeId(r3);	 Catch:{ all -> 0x003f }
        r0 = r0.get(r6);	 Catch:{ all -> 0x003f }
        r0 = (java.lang.ref.WeakReference) r0;	 Catch:{ all -> 0x003f }
        if (r0 != 0) goto L_0x0049;
    L_0x003a:
        r0 = r1;
    L_0x003b:
        if (r0 == 0) goto L_0x0050;
    L_0x003d:
        monitor-exit(r2);	 Catch:{ all -> 0x003f }
        goto L_0x0004;
    L_0x003f:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x003f }
        throw r0;
    L_0x0042:
        r0 = r0.get();	 Catch:{ all -> 0x003f }
        r0 = (com.meizu.common.renderer.effect.EGLBitmap) r0;	 Catch:{ all -> 0x003f }
        goto L_0x0014;
    L_0x0049:
        r0 = r0.get();	 Catch:{ all -> 0x003f }
        r0 = (com.meizu.common.renderer.effect.EGLBitmap) r0;	 Catch:{ all -> 0x003f }
        goto L_0x003b;
    L_0x0050:
        r0 = new com.meizu.common.renderer.effect.EGLBitmap;	 Catch:{ all -> 0x003f }
        r0.<init>(r3);	 Catch:{ all -> 0x003f }
        r1 = sResBitmapCache;	 Catch:{ all -> 0x003f }
        r3 = new java.lang.ref.WeakReference;	 Catch:{ all -> 0x003f }
        r3.<init>(r0);	 Catch:{ all -> 0x003f }
        r1.put(r4, r3);	 Catch:{ all -> 0x003f }
    L_0x005f:
        monitor-exit(r2);	 Catch:{ all -> 0x003f }
        goto L_0x0004;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.common.renderer.effect.cache.EGLBitmapCache.get(int):com.meizu.common.renderer.effect.EGLBitmap");
    }

    public static void freeResource() {
        synchronized (EGLBitmapCache.class) {
            sBitmapCache.clear();
            sResBitmapCache.clear();
        }
    }
}
