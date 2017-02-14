package sdk.meizu.traffic.auth;

import android.content.Intent;

public class MzAuthException extends Exception {
    private Intent mLoginIntent;

    public MzAuthException(String str) {
        super(str);
    }

    public MzAuthException(String str, Intent intent) {
        super(str);
        this.mLoginIntent = intent;
    }

    public boolean needLogin() {
        return this.mLoginIntent != null;
    }

    public Intent getLoginIntent() {
        return this.mLoginIntent;
    }
}
