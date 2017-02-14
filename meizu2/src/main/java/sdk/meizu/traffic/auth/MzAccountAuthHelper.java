package sdk.meizu.traffic.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MzAccountAuthHelper {
    public static final String ACCOUNT_TYPE = "com.meizu.account";
    public static final String BASIC_SCOPE = "basic";
    private static final String TAG = "MzAccountAuthHelper";
    private AuthListener mAuthListener;
    private boolean mCanceled;
    private Context mContext;
    private AccountManagerFuture<Bundle> mRequestFuture;

    public MzAccountAuthHelper(Context context) {
        this.mContext = context;
    }

    public void cancel() {
        this.mCanceled = true;
        if (this.mRequestFuture != null) {
            this.mRequestFuture.cancel(true);
        }
    }

    public void getToken(boolean z, AuthListener authListener) {
        this.mAuthListener = authListener;
        AccountManager accountManager = AccountManager.get(this.mContext);
        Account baseAccount = MzAccountManager.getBaseAccount(this.mContext);
        if (baseAccount == null) {
            baseAccount = new Account(EnvironmentCompat.MEDIA_UNKNOWN, "com.meizu.account");
        }
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putBoolean("invalidateToken", true);
        }
        this.mCanceled = false;
        this.mRequestFuture = accountManager.getAuthToken(baseAccount, BASIC_SCOPE, bundle, null, new AccountManagerCallback<Bundle>() {
            public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
                Log.d(MzAccountAuthHelper.TAG, "receive account callback");
                if (MzAccountAuthHelper.this.mCanceled) {
                    Log.d(MzAccountAuthHelper.TAG, "op canceled.");
                    return;
                }
                try {
                    Bundle bundle = (Bundle) accountManagerFuture.getResult();
                    if (bundle == null) {
                        MzAccountAuthHelper.this.onError(1);
                    } else if (bundle.containsKey("authtoken")) {
                        MzAccountAuthHelper.this.onSuccess(bundle.getString("authtoken"));
                    } else if (bundle.containsKey("intent")) {
                        MzAccountAuthHelper.this.onLoginRequest((Intent) bundle.getParcelable("intent"));
                    } else if (bundle.containsKey("errorCode")) {
                        MzAccountAuthHelper.this.onError(bundle.getInt("errorCode"));
                    } else {
                        MzAccountAuthHelper.this.onError(1);
                    }
                } catch (OperationCanceledException e) {
                } catch (AuthenticatorException e2) {
                    MzAccountAuthHelper.this.onError(1);
                } catch (IOException e3) {
                    MzAccountAuthHelper.this.onError(1);
                }
            }
        }, null);
    }

    private void onLoginRequest(Intent intent) {
        if (!this.mCanceled && this.mAuthListener != null) {
            this.mAuthListener.onLoginRequst(intent);
        }
    }

    private void onError(int i) {
        if (!this.mCanceled && this.mAuthListener != null) {
            this.mAuthListener.onError(i);
        }
    }

    private void onSuccess(String str) {
        if (!this.mCanceled && this.mAuthListener != null) {
            this.mAuthListener.onSuccess(str);
            CUserManager.getInstance(this.mContext).setCacheToken(str);
        }
    }

    public String getTokenSynchronize(boolean z) {
        Account baseAccount = MzAccountManager.getBaseAccount(this.mContext);
        if (baseAccount == null) {
            baseAccount = new Account(EnvironmentCompat.MEDIA_UNKNOWN, "com.meizu.account");
        }
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putBoolean("invalidateToken", true);
        }
        try {
            Bundle bundle2 = (Bundle) AccountManager.get(this.mContext).getAuthToken(baseAccount, BASIC_SCOPE, bundle, null, null, null).getResult(30, TimeUnit.SECONDS);
            if (bundle2.containsKey("authtoken")) {
                return bundle2.getString("authtoken");
            }
            if (bundle2.containsKey("intent")) {
                throw new MzAuthException("Relogin Needed!", (Intent) bundle2.getParcelable("intent"));
            } else if (bundle2.containsKey("errorMessage")) {
                throw new MzAuthException(bundle2.getString("errorMessage"));
            } else {
                throw new MzAuthException("Auth Error");
            }
        } catch (OperationCanceledException e) {
            e.printStackTrace();
            return null;
        } catch (AuthenticatorException e2) {
            e2.printStackTrace();
            return null;
        } catch (IOException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
