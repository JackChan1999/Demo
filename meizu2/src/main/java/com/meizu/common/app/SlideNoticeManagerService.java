package com.meizu.common.app;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;

public class SlideNoticeManagerService {
    private static final boolean DBG = true;
    private static final int LONG_DELAY = 3500;
    private static final int MESSAGE_TIMEOUT = 1;
    private static final int SHORT_DELAY = 2000;
    private static final String TAG = "SlideNoticeManagerService";
    private Activity mCurActivity;
    private WorkerHandler mHandler = new WorkerHandler();
    private ArrayList<NoticeRecord> mNoticeQueue = new ArrayList();

    public interface NoticeCallBack {
        void hide();

        void show();
    }

    static final class NoticeRecord {
        final Activity activity;
        final NoticeCallBack callback;
        int duration;
        CharSequence message;

        NoticeRecord(CharSequence charSequence, int i, NoticeCallBack noticeCallBack) {
            this.message = charSequence;
            this.duration = i;
            this.callback = noticeCallBack;
            this.activity = null;
        }

        NoticeRecord(CharSequence charSequence, int i, NoticeCallBack noticeCallBack, Activity activity) {
            this.message = charSequence;
            this.duration = i;
            this.callback = noticeCallBack;
            this.activity = activity;
        }

        void update(int i) {
            this.duration = i;
        }

        public final String toString() {
            return "NoticeRecord{" + Integer.toHexString(System.identityHashCode(this)) + " callback=" + this.callback + " duration=" + this.duration;
        }
    }

    final class WorkerHandler extends Handler {
        private WorkerHandler() {
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    SlideNoticeManagerService.this.handleTimeout((NoticeRecord) message.obj);
                    return;
                default:
                    return;
            }
        }
    }

    SlideNoticeManagerService() {
    }

    public void enqueueNotice(CharSequence charSequence, NoticeCallBack noticeCallBack, int i) {
        Log.i(TAG, "enqueueNotice callback=" + noticeCallBack + " message=" + charSequence + " duration=" + i);
        if (noticeCallBack != null) {
            synchronized (this.mNoticeQueue) {
                int i2;
                int indexOfNotice = indexOfNotice(noticeCallBack);
                if (indexOfNotice >= 0) {
                    ((NoticeRecord) this.mNoticeQueue.get(indexOfNotice)).update(i);
                    i2 = indexOfNotice;
                } else {
                    Object obj;
                    if (this.mNoticeQueue.size() <= 0 || !TextUtils.equals(charSequence, ((NoticeRecord) this.mNoticeQueue.get(this.mNoticeQueue.size() - 1)).message)) {
                        obj = null;
                    } else {
                        obj = 1;
                    }
                    if (obj == null) {
                        this.mNoticeQueue.add(new NoticeRecord(charSequence, i, noticeCallBack));
                    }
                    i2 = this.mNoticeQueue.size() - 1;
                }
                if (i2 == 0) {
                    showNextNotice();
                }
            }
        }
    }

    private void showNextNotice() {
        NoticeRecord noticeRecord = (NoticeRecord) this.mNoticeQueue.get(0);
        while (noticeRecord != null) {
            try {
                Log.d(TAG, "Show callback=" + noticeRecord.callback);
                noticeRecord.callback.show();
                scheduleTimeout(noticeRecord);
                return;
            } catch (Throwable e) {
                NoticeRecord noticeRecord2;
                Log.e(TAG, "catch an exception when showing next notice, it will be romoved from queue", e);
                int indexOf = this.mNoticeQueue.indexOf(noticeRecord);
                if (indexOf >= 0) {
                    this.mNoticeQueue.remove(indexOf);
                }
                if (this.mNoticeQueue.size() > 0) {
                    noticeRecord2 = (NoticeRecord) this.mNoticeQueue.get(0);
                } else {
                    noticeRecord2 = null;
                }
                noticeRecord = noticeRecord2;
            }
        }
    }

    private void scheduleTimeout(NoticeRecord noticeRecord) {
        this.mHandler.removeCallbacksAndMessages(noticeRecord);
        this.mHandler.sendMessageDelayed(Message.obtain(this.mHandler, 1, noticeRecord), noticeRecord.duration == 1 ? 3500 : 2000);
    }

    private void handleTimeout(NoticeRecord noticeRecord) {
        Log.d(TAG, "Timeout callback=" + noticeRecord.callback);
        synchronized (this.mNoticeQueue) {
            int indexOfNotice = indexOfNotice(noticeRecord.callback);
            if (indexOfNotice >= 0) {
                cancelNotice(indexOfNotice);
            }
        }
    }

    private void cancelNotice(int i) {
        Log.d(TAG, "cancelNotice index=" + i);
        ((NoticeRecord) this.mNoticeQueue.get(i)).callback.hide();
        this.mNoticeQueue.remove(i);
        if (this.mNoticeQueue.size() > 0) {
            showNextNotice();
        }
    }

    public void cancelNotice(NoticeCallBack noticeCallBack) {
        if (noticeCallBack != null) {
            synchronized (this.mNoticeQueue) {
                int indexOfNotice = indexOfNotice(noticeCallBack);
                if (indexOfNotice >= 0) {
                    this.mHandler.removeCallbacksAndMessages(this.mNoticeQueue.get(indexOfNotice));
                    cancelNotice(indexOfNotice);
                } else {
                    Log.w(TAG, "Notice already cancelled. callback=" + noticeCallBack);
                }
            }
        }
    }

    private int indexOfNotice(NoticeCallBack noticeCallBack) {
        ArrayList arrayList = this.mNoticeQueue;
        for (int i = 0; i < arrayList.size(); i++) {
            if (((NoticeRecord) arrayList.get(i)).callback == noticeCallBack) {
                return i;
            }
        }
        return -1;
    }

    public void enqueueNoticeInActivity(CharSequence charSequence, NoticeCallBack noticeCallBack, int i, Activity activity) {
        Log.i(TAG, "enqueueNoticeInActivity callback=" + noticeCallBack + " message=" + charSequence + " duration=" + i + " activity=" + activity);
        if (noticeCallBack != null && activity != null) {
            synchronized (this.mNoticeQueue) {
                int i2;
                if (this.mCurActivity != activity) {
                    this.mCurActivity = activity;
                    removeLastActivityRecord();
                }
                int indexOfNotice = indexOfNotice(noticeCallBack);
                if (indexOfNotice >= 0) {
                    ((NoticeRecord) this.mNoticeQueue.get(indexOfNotice)).update(i);
                    i2 = indexOfNotice;
                } else {
                    Object obj;
                    if (this.mNoticeQueue.size() <= 0 || !TextUtils.equals(charSequence, ((NoticeRecord) this.mNoticeQueue.get(this.mNoticeQueue.size() - 1)).message)) {
                        obj = null;
                    } else {
                        obj = 1;
                    }
                    if (obj == null) {
                        this.mNoticeQueue.add(new NoticeRecord(charSequence, i, noticeCallBack, activity));
                    }
                    i2 = this.mNoticeQueue.size() - 1;
                }
                if (i2 == 0) {
                    showNextNotice();
                }
            }
        }
    }

    private void removeLastActivityRecord() {
        ArrayList arrayList = this.mNoticeQueue;
        int i = 0;
        while (i < arrayList.size()) {
            if (((NoticeRecord) arrayList.get(i)).activity != null) {
                arrayList.remove(i);
                i--;
            }
            i++;
        }
    }
}
