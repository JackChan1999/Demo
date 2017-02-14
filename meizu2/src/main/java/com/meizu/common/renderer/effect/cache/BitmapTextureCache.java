package com.meizu.common.renderer.effect.cache;

import android.graphics.Bitmap;
import android.opengl.ETC1Util;
import android.util.Log;
import android.util.LongSparseArray;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.EGLBitmap;
import com.meizu.common.renderer.effect.GLRenderManager;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.effect.texture.BitmapTexture;
import com.meizu.common.renderer.effect.texture.EGLBitmapTexture;
import com.meizu.common.renderer.effect.texture.ETC1BitmapTexture;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class BitmapTextureCache {
    private static LongSparseArray<WeakReference<BitmapTexture>> sBitmapTextureCache = new LongSparseArray();
    private static LongSparseArray<WeakReference<EGLBitmapTexture>> sEGLBitmapTextureCache = new LongSparseArray();
    private static LongSparseArray<WeakReference<ETC1BitmapTexture>> sETC1TextureCache = new LongSparseArray();
    private static LongSparseArray<WeakReference<BitmapTexture>> sResTextureCache = new LongSparseArray();

    static class SafeRelase<T extends BasicTexture> {
        private SafeRelase() {
        }

        public void relase(LongSparseArray<WeakReference<T>> longSparseArray) {
            int size = BitmapTextureCache.sETC1TextureCache.size();
            for (int i = 0; i < size; i++) {
                WeakReference weakReference = (WeakReference) longSparseArray.get(longSparseArray.keyAt(i));
                if (!(weakReference == null || weakReference.get() == null)) {
                    ((BasicTexture) weakReference.get()).freeGLResource();
                }
            }
            longSparseArray.clear();
        }
    }

    public static BitmapTexture get(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        BitmapTexture bitmapTexture;
        synchronized (BitmapTextureCache.class) {
            long bitmapNativeId = Utils.getBitmapNativeId(bitmap);
            WeakReference weakReference = (WeakReference) sBitmapTextureCache.get(bitmapNativeId);
            bitmapTexture = weakReference == null ? null : (BitmapTexture) weakReference.get();
            if (bitmapTexture == null) {
                bitmapTexture = new BitmapTexture(bitmap);
                sBitmapTextureCache.put(bitmapNativeId, new WeakReference(bitmapTexture));
            } else if (bitmapTexture.getGenerationId() != bitmap.getGenerationId()) {
                Log.e(GLRenderManager.TAG, "BitmapTexture: texture.getGenerationId() != bitmap.getGenerationId()");
                bitmapTexture.setBitmap(bitmap);
            }
        }
        return bitmapTexture;
    }

    public static EGLBitmapTexture get(EGLBitmap eGLBitmap) {
        if (eGLBitmap == null || !eGLBitmap.isValid()) {
            return null;
        }
        EGLBitmapTexture eGLBitmapTexture;
        synchronized (BitmapTextureCache.class) {
            long nativeEGLBitmap = eGLBitmap.getNativeEGLBitmap();
            WeakReference weakReference = (WeakReference) sEGLBitmapTextureCache.get(nativeEGLBitmap);
            eGLBitmapTexture = weakReference == null ? null : (EGLBitmapTexture) weakReference.get();
            if (eGLBitmapTexture == null) {
                eGLBitmapTexture = new EGLBitmapTexture(eGLBitmap);
                sEGLBitmapTextureCache.put(nativeEGLBitmap, new WeakReference(eGLBitmapTexture));
            } else if (eGLBitmapTexture.getGenerationId() != eGLBitmap.getGenerationId()) {
                Log.e(GLRenderManager.TAG, "EGLBitmapTexture: texture.getGenerationId() != bitmap.getGenerationId()");
                eGLBitmapTexture.setEGLBitmap(eGLBitmap);
            }
        }
        return eGLBitmapTexture;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.meizu.common.renderer.effect.texture.BitmapTexture get(int r8) {
        /*
        r1 = 0;
        if (r8 > 0) goto L_0x0005;
    L_0x0003:
        r0 = r1;
    L_0x0004:
        return r0;
    L_0x0005:
        r2 = com.meizu.common.renderer.effect.cache.BitmapTextureCache.class;
        monitor-enter(r2);
        r4 = (long) r8;
        r0 = sResTextureCache;	 Catch:{ all -> 0x003f }
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
        r0 = sBitmapTextureCache;	 Catch:{ all -> 0x003f }
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
        r0 = (com.meizu.common.renderer.effect.texture.BitmapTexture) r0;	 Catch:{ all -> 0x003f }
        goto L_0x0014;
    L_0x0049:
        r0 = r0.get();	 Catch:{ all -> 0x003f }
        r0 = (com.meizu.common.renderer.effect.texture.BitmapTexture) r0;	 Catch:{ all -> 0x003f }
        goto L_0x003b;
    L_0x0050:
        r0 = new com.meizu.common.renderer.effect.texture.BitmapTexture;	 Catch:{ all -> 0x003f }
        r0.<init>(r3);	 Catch:{ all -> 0x003f }
        r1 = sResTextureCache;	 Catch:{ all -> 0x003f }
        r3 = new java.lang.ref.WeakReference;	 Catch:{ all -> 0x003f }
        r3.<init>(r0);	 Catch:{ all -> 0x003f }
        r1.put(r4, r3);	 Catch:{ all -> 0x003f }
    L_0x005f:
        monitor-exit(r2);	 Catch:{ all -> 0x003f }
        goto L_0x0004;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.common.renderer.effect.cache.BitmapTextureCache.get(int):com.meizu.common.renderer.effect.texture.BitmapTexture");
    }

    public static ETC1BitmapTexture getETC1(int i) {
        ETC1BitmapTexture eTC1BitmapTexture;
        ETC1BitmapTexture eTC1BitmapTexture2;
        ETC1BitmapTexture eTC1BitmapTexture3;
        Object e;
        if (i <= 0) {
            return null;
        }
        synchronized (BitmapTextureCache.class) {
            long j = (long) i;
            WeakReference weakReference = (WeakReference) sETC1TextureCache.get(j);
            if (weakReference == null) {
                eTC1BitmapTexture = null;
            } else {
                eTC1BitmapTexture = (ETC1BitmapTexture) weakReference.get();
            }
            if (eTC1BitmapTexture == null) {
                r6 = GLRenderManager.getInstance().getAppContext().getResources().openRawResource(i);
                try {
                    eTC1BitmapTexture2 = new ETC1BitmapTexture(ETC1Util.createTexture(r6));
                    try {
                        sETC1TextureCache.put(j, new WeakReference(eTC1BitmapTexture2));
                        try {
                            r6.close();
                            eTC1BitmapTexture3 = eTC1BitmapTexture2;
                        } catch (IOException e2) {
                            eTC1BitmapTexture3 = eTC1BitmapTexture2;
                        }
                    } catch (IOException e3) {
                        e = e3;
                        try {
                            Log.e(GLRenderManager.TAG, "Could not load texture: " + e);
                            eTC1BitmapTexture3 = eTC1BitmapTexture2;
                            return eTC1BitmapTexture3;
                        } finally {
                            try {
                                InputStream openRawResource;
                                openRawResource.close();
                            } catch (IOException e4) {
                                eTC1BitmapTexture2 = e4;
                            }
                        }
                    }
                } catch (IOException e5) {
                    e = e5;
                    eTC1BitmapTexture2 = eTC1BitmapTexture;
                    Log.e(GLRenderManager.TAG, "Could not load texture: " + e);
                    eTC1BitmapTexture3 = eTC1BitmapTexture2;
                    return eTC1BitmapTexture3;
                }
            }
            eTC1BitmapTexture3 = eTC1BitmapTexture;
        }
        return eTC1BitmapTexture3;
    }

    public static void freeGLResource() {
        synchronized (BitmapTextureCache.class) {
            new SafeRelase().relase(sBitmapTextureCache);
            new SafeRelase().relase(sResTextureCache);
            new SafeRelase().relase(sEGLBitmapTextureCache);
            new SafeRelase().relase(sETC1TextureCache);
        }
    }
}
