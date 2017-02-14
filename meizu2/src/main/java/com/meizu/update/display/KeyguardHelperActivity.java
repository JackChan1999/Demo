package com.meizu.update.display;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import defpackage.arx;

public class KeyguardHelperActivity extends Activity {
    protected void onCreate(Bundle bundle) {
        arx.c("KeyguardHelperActivity create");
        super.onCreate(bundle);
        Window window = getWindow();
        window.addFlags(4194304);
        window.addFlags(524288);
        window.addFlags(2097152);
        finish();
    }
}
