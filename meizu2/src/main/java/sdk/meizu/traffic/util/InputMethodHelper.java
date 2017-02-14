package sdk.meizu.traffic.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

public class InputMethodHelper {

    final class AnonymousClass1 implements Runnable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ WebView val$webView;

        AnonymousClass1(Context context, WebView webView) {
            this.val$context = context;
            this.val$webView = webView;
        }

        public void run() {
            ((InputMethodManager) this.val$context.getSystemService("input_method")).showSoftInput(this.val$webView, 1);
        }
    }

    public static void showInputMethod(Context context, WebView webView) {
        webView.post(new AnonymousClass1(context, webView));
    }

    public static boolean closeInputMethod(Context context, WebView webView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager.isActive() && webView.hasFocus()) {
            return inputMethodManager.hideSoftInputFromWindow(webView.getApplicationWindowToken(), 0);
        }
        return false;
    }
}
