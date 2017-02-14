package sdk.meizu.traffic.auth;

import android.content.Intent;

public interface AuthListener {
    void onError(int i);

    void onLoginRequst(Intent intent);

    void onSuccess(String str);
}
