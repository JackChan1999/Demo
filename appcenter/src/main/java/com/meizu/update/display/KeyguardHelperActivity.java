package com.meizu.update.display;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import com.meizu.update.h.b;

public class KeyguardHelperActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        b.c("KeyguardHelperActivity create");
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(4194304);
        window.addFlags(524288);
        window.addFlags(2097152);
        finish();
    }
}
