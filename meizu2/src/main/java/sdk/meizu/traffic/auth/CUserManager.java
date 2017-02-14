package sdk.meizu.traffic.auth;

import android.content.Context;
import android.util.Log;

public class CUserManager {
    private static CUserManager sInstance;
    private MzAccountAuthHelper mAuthHelper;
    private String mAuthToken;

    private CUserManager(Context context) {
        this.mAuthHelper = new MzAccountAuthHelper(context.getApplicationContext());
    }

    public static final CUserManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CUserManager(context);
        }
        return sInstance;
    }

    public void logout() {
        this.mAuthToken = null;
    }

    public boolean hasLogined() {
        return hasToken();
    }

    private boolean hasToken() {
        return this.mAuthToken != null;
    }

    public void setCacheToken(String str) {
        this.mAuthToken = str;
    }

    public String getToken(boolean z) {
        if (z) {
            this.mAuthToken = this.mAuthHelper.getTokenSynchronize(true);
            Log.v("CUserManager", "invalidateToken: " + z);
        } else if (this.mAuthToken == null) {
            this.mAuthToken = this.mAuthHelper.getTokenSynchronize(false);
        }
        return this.mAuthToken;
    }

    public void getTokenAsync(boolean z, AuthListener authListener) {
        this.mAuthHelper.getToken(z, authListener);
    }
}
