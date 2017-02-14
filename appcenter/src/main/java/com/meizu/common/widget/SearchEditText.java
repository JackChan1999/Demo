package com.meizu.common.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.lang.ref.WeakReference;

public class SearchEditText extends EditText {
    private a a;

    private static class a extends Handler {
        WeakReference<SearchEditText> a = null;

        public a(WeakReference<SearchEditText> weakReference) {
            this.a = weakReference;
        }

        public void handleMessage(Message msg) {
            SearchEditText searchEditText = (SearchEditText) this.a.get();
            switch (msg.what) {
                case 1:
                    searchEditText.requestFocus();
                    InputMethodManager showInputManager = (InputMethodManager) searchEditText.getContext().getSystemService("input_method");
                    showInputManager.restartInput(searchEditText);
                    showInputManager.showSoftInput(searchEditText, 1);
                    return;
                case 2:
                    searchEditText.clearFocus();
                    ((InputMethodManager) searchEditText.getContext().getSystemService("input_method")).hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
                    return;
                default:
                    return;
            }
        }
    }

    public SearchEditText(Context context) {
        super(context);
        a();
    }

    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    public void a() {
        this.a = new a(new WeakReference(this));
    }

    public void a(boolean show) {
        int i = 1;
        this.a.removeMessages(1);
        this.a.removeMessages(2);
        a aVar = this.a;
        if (!show) {
            i = 2;
        }
        aVar.sendEmptyMessageDelayed(i, 100);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(SearchEditText.class.getName());
    }
}
