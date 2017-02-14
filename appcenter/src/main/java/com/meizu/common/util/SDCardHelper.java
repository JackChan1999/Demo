package com.meizu.common.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Observable;
import android.os.storage.StorageManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class SDCardHelper extends BroadcastReceiver {
    private static SDCardHelper a = null;
    private boolean b;
    private final b c;
    private List<a> d;
    private Method e;
    private Method f;
    private Method g;
    private Method h;

    public class a {
        final /* synthetic */ SDCardHelper a;
        private String b;
        private String c;
        private String d;
        private boolean e;

        public a(SDCardHelper sDCardHelper) {
            this.a = sDCardHelper;
        }

        private void a(String mDescription) {
            this.b = mDescription;
        }

        private void b(String mPath) {
            this.c = mPath;
        }

        private void c(String mountedState) {
            this.d = mountedState;
        }

        private void a(boolean mIsExternal) {
            this.e = mIsExternal;
        }
    }

    private static class b extends Observable<c> {
        public void a(Intent intent, boolean mounted) {
            synchronized (this.mObservers) {
                for (int i = this.mObservers.size() - 1; i >= 0; i--) {
                    ((c) this.mObservers.get(i)).a(intent, mounted);
                }
            }
        }
    }

    public interface c {
        void a(Intent intent, boolean z);
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        a(context);
        if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
            this.b = true;
            a(intent, true);
        } else if ("android.intent.action.MEDIA_EJECT".equals(action)) {
            this.b = false;
            a(intent, false);
        } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
            this.b = false;
            a(intent, false);
        }
    }

    public void a(Intent intent, boolean mounted) {
        this.c.a(intent, mounted);
    }

    public List<a> a(Context context) {
        StorageManager sm = (StorageManager) context.getSystemService("storage");
        this.d.clear();
        try {
            Object[] storageVolumes = (Object[]) sm.getClass().getMethod("getVolumeList", new Class[0]).invoke(sm, new Object[0]);
            if (storageVolumes != null) {
                for (Object volume : storageVolumes) {
                    a mountPoint = new a(this);
                    if (this.e == null || this.f == null || this.g == null || this.h == null) {
                        this.e = volume.getClass().getDeclaredMethod("getDescription", new Class[]{Context.class});
                        this.f = volume.getClass().getDeclaredMethod("getPath", new Class[0]);
                        this.g = volume.getClass().getDeclaredMethod("isRemovable", new Class[0]);
                        this.h = sm.getClass().getMethod("getVolumeState", new Class[]{String.class});
                    }
                    String location = (String) this.f.invoke(volume, new Object[0]);
                    mountPoint.a((String) this.e.invoke(volume, new Object[]{context}));
                    mountPoint.b(location);
                    mountPoint.c((String) this.h.invoke(sm, new Object[]{location}));
                    mountPoint.a(((Boolean) this.g.invoke(volume, new Object[0])).booleanValue());
                    this.d.add(mountPoint);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        }
        return this.d;
    }
}
