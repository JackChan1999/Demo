package com.meizu.cloud.base.app;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.meizu.cloud.thread.component.ExecBaseActivity;

public class BaseActivity extends ExecBaseActivity {
    public boolean j = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b(true);
        if (g() != null) {
            g().b(true);
        }
    }

    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    public void b(boolean on) {
        Window win = getWindow();
        LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= 67108864;
        } else {
            winParams.flags &= -67108865;
        }
        win.setAttributes(winParams);
    }
}
