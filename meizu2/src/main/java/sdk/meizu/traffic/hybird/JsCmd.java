package sdk.meizu.traffic.hybird;

import android.text.TextUtils;
import android.webkit.WebView;
import com.meizu.common.widget.MzContactsContract.MzGroups;

public class JsCmd {
    private static final String JS_COMMAND_PREFIX = "javascript:";
    private String className;
    private String[] methodArgs;
    private String methodName;

    private JsCmd(String str) {
        this.className = str;
    }

    public static JsCmd from(String str) {
        return new JsCmd(str);
    }

    public JsCmd setMethod(String str) {
        this.methodName = str;
        return this;
    }

    public JsCmd setMethodArgs(String... strArr) {
        this.methodArgs = strArr;
        return this;
    }

    public static void execute(WebView webView, String str) {
        webView.loadUrl(JS_COMMAND_PREFIX + str);
    }

    public void execute(WebView webView) {
        webView.loadUrl(toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(JS_COMMAND_PREFIX);
        if (!TextUtils.isEmpty(this.className)) {
            stringBuilder.append(this.className + ".");
        }
        if (TextUtils.isEmpty(this.methodName)) {
            throw new IllegalArgumentException("js method required!");
        }
        stringBuilder.append(this.methodName + "(");
        if (this.methodArgs != null) {
            int length = this.methodArgs.length;
            for (int i = 0; i < length; i++) {
                stringBuilder.append(this.methodArgs[i]);
                if (i < length - 1) {
                    stringBuilder.append(MzGroups.GROUP_SPLIT_MARK_EXTRA);
                }
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
