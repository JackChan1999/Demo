package com.meizu.update.display;

import android.app.AlertDialog;
import com.meizu.update.d;

public class f implements d {
    private final AlertDialog a;
    private final boolean b;
    private final boolean c;

    public f(AlertDialog dialog, boolean forceUpdate, boolean systemAlert) {
        this.a = dialog;
        this.b = forceUpdate;
        this.c = systemAlert;
    }
}
