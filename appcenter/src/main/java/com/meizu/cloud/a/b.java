package com.meizu.cloud.a;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.meizu.c.c;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.x;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class b {
    private FragmentActivity a;
    private Fragment b;
    private AccountManager c;
    private int d;
    private boolean e;
    private a f;
    private AccountManagerFuture<Bundle> g;
    private boolean h = false;

    public b(Fragment fragment, int requestCode, a authListener) {
        this.b = fragment;
        this.a = fragment.getActivity();
        this.c = AccountManager.get(this.a.getApplicationContext());
        this.d = requestCode;
        this.f = authListener;
    }

    public void a() {
        this.e = true;
        if (this.g != null) {
            this.g.cancel(true);
        }
    }

    public void a(boolean invalidateToken) {
        Account targetAccount = c.d(this.a.getApplicationContext());
        if (targetAccount == null) {
            targetAccount = new Account("unknown", "com.meizu.account");
        }
        Bundle bundle = new Bundle();
        if (invalidateToken) {
            bundle.putBoolean("invalidateToken", true);
        }
        this.e = false;
        this.g = this.c.getAuthToken(targetAccount, a(this.a.getApplicationContext()), bundle, null, new AccountManagerCallback<Bundle>(this) {
            final /* synthetic */ b a;

            {
                this.a = r1;
            }

            public void run(AccountManagerFuture<Bundle> future) {
                Log.d("MzAccountAuthHelper", "receive account callback");
                if (this.a.e) {
                    Log.d("MzAccountAuthHelper", "op canceled.");
                    return;
                }
                try {
                    Bundle bundle = (Bundle) future.getResult();
                    if (bundle == null) {
                        this.a.a(1);
                    } else if (bundle.containsKey("authtoken")) {
                        this.a.a(bundle.getString("authtoken"));
                    } else if (bundle.containsKey("intent")) {
                        Intent intent = (Intent) bundle.getParcelable("intent");
                        if (this.a.b == null) {
                            this.a.a.startActivityForResult(intent, this.a.d);
                        } else if (this.a.b.isAdded()) {
                            this.a.b.startActivityForResult(intent, this.a.d);
                        }
                    } else if (bundle.containsKey("errorCode")) {
                        this.a.a(bundle.getInt("errorCode"));
                    } else {
                        this.a.a(1);
                    }
                } catch (OperationCanceledException e) {
                } catch (AuthenticatorException e2) {
                    this.a.a(1);
                } catch (IOException e3) {
                    this.a.a(1);
                }
            }
        }, null);
    }

    public static String a(Context context) {
        String scope = "basic";
        String domain = i.a(context).metaData.getString("domain");
        if (x.a(context)) {
            return "basic app_trust";
        }
        if (x.b(context)) {
            return "basic game_trust";
        }
        return scope;
    }

    public boolean a(int requestCode, int resultCode, Intent data) {
        if (requestCode != this.d) {
            return false;
        }
        if (this.e) {
            Log.d("MzAccountAuthHelper", "op canceled.");
            return true;
        } else if (resultCode == -1) {
            this.h = true;
            a(false);
            return true;
        } else if (resultCode == 0) {
            a(4);
            return true;
        } else {
            a(1);
            return true;
        }
    }

    private void a(int code) {
        if (!this.e && this.f != null) {
            this.f.a(code);
        }
    }

    private void a(String token) {
        if (!this.e && this.f != null) {
            this.f.a(token, this.h);
            this.h = false;
        }
    }

    public static String a(Context context, boolean invalidateToken) {
        String str = null;
        Account targetAccount = c.d(context);
        if (targetAccount != null) {
            Bundle bundle = new Bundle();
            if (invalidateToken) {
                bundle.putBoolean("invalidateToken", true);
            }
            try {
                Bundle result = (Bundle) AccountManager.get(context).getAuthToken(targetAccount, a(context), bundle, null, null, null).getResult(30, TimeUnit.SECONDS);
                if (result.containsKey("authtoken")) {
                    str = result.getString("authtoken");
                }
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e2) {
                e2.printStackTrace();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        return str;
    }

    public static String b(Context context, boolean invalidateToken) throws c, com.meizu.c.b {
        Account targetAccount = c.d(context);
        if (targetAccount == null) {
            targetAccount = new Account("unknown", "com.meizu.account");
        }
        Bundle bundle = new Bundle();
        if (invalidateToken) {
            bundle.putBoolean("invalidateToken", true);
        }
        try {
            Bundle result = (Bundle) AccountManager.get(context).getAuthToken(targetAccount, a(context), bundle, null, null, null).getResult(30, TimeUnit.SECONDS);
            if (result.containsKey("authtoken")) {
                return result.getString("authtoken");
            }
            if (result.containsKey("intent")) {
                throw new c((Intent) result.getParcelable("intent"));
            } else if (bundle.containsKey("errorCode")) {
                throw new com.meizu.c.b(bundle.getInt("errorCode"));
            } else {
                throw new com.meizu.c.b(1);
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
