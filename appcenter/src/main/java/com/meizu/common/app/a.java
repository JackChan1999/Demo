package com.meizu.common.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

public class a {
    private ArrayList<b> a = new ArrayList();
    private c b = new c();

    public interface a {
        void f();

        void g();
    }

    private static final class b {
        CharSequence a;
        int b;
        final a c;
        final Activity d = null;

        b(CharSequence message, int duration, a callback) {
            this.a = message;
            this.b = duration;
            this.c = callback;
        }

        void a(int duration) {
            this.b = duration;
        }

        public final String toString() {
            return "NoticeRecord{" + Integer.toHexString(System.identityHashCode(this)) + " callback=" + this.c + " duration=" + this.b;
        }
    }

    private final class c extends Handler {
        final /* synthetic */ a a;

        private c(a aVar) {
            this.a = aVar;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    this.a.b((b) msg.obj);
                    return;
                default:
                    return;
            }
        }
    }

    a() {
    }

    public void a(CharSequence message, a callback, int duration) {
        Log.i("SlideNoticeManagerService", "enqueueNotice callback=" + callback + " message=" + message + " duration=" + duration);
        if (callback != null) {
            synchronized (this.a) {
                int index = b(callback);
                if (index >= 0) {
                    ((b) this.a.get(index)).a(duration);
                } else {
                    boolean isSame = false;
                    if (this.a.size() > 0 && TextUtils.equals(message, ((b) this.a.get(this.a.size() - 1)).a)) {
                        isSame = true;
                    }
                    if (!isSame) {
                        this.a.add(new b(message, duration, callback));
                    }
                    index = this.a.size() - 1;
                }
                if (index == 0) {
                    a();
                }
            }
        }
    }

    private void a() {
        b record = (b) this.a.get(0);
        while (record != null) {
            try {
                Log.d("SlideNoticeManagerService", "Show callback=" + record.c);
                record.c.g();
                a(record);
                return;
            } catch (Exception e) {
                Log.e("SlideNoticeManagerService", "catch an exception when showing next notice, it will be romoved from queue", e);
                int index = this.a.indexOf(record);
                if (index >= 0) {
                    this.a.remove(index);
                }
                if (this.a.size() > 0) {
                    record = (b) this.a.get(0);
                } else {
                    record = null;
                }
            }
        }
    }

    private void a(b record) {
        this.b.removeCallbacksAndMessages(record);
        this.b.sendMessageDelayed(Message.obtain(this.b, 1, record), record.b == 1 ? 3500 : 2000);
    }

    private void b(b record) {
        Log.d("SlideNoticeManagerService", "Timeout callback=" + record.c);
        synchronized (this.a) {
            int index = b(record.c);
            if (index >= 0) {
                a(index);
            }
        }
    }

    private void a(int index) {
        Log.d("SlideNoticeManagerService", "cancelNotice index=" + index);
        ((b) this.a.get(index)).c.f();
        this.a.remove(index);
        if (this.a.size() > 0) {
            a();
        }
    }

    public void a(a callback) {
        if (callback != null) {
            synchronized (this.a) {
                int index = b(callback);
                if (index >= 0) {
                    this.b.removeCallbacksAndMessages(this.a.get(index));
                    a(index);
                } else {
                    Log.w("SlideNoticeManagerService", "Notice already cancelled. callback=" + callback);
                }
            }
        }
    }

    private int b(a callback) {
        ArrayList<b> list = this.a;
        for (int i = 0; i < list.size(); i++) {
            if (((b) list.get(i)).c == callback) {
                return i;
            }
        }
        return -1;
    }
}
