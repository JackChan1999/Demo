package com.meizu.cloud.app.update.exclude;

import android.os.Bundle;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.base.app.BaseActivity;
import com.meizu.cloud.statistics.b;

public class AppUpdateExcludeActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(g.base_main_activity);
        f().a().b(f.main_container, new c()).b();
    }

    protected void onStart() {
        super.onStart();
        b.a().a(getClass().getSimpleName());
    }

    protected void onStop() {
        super.onStop();
        b.a().a(getClass().getSimpleName(), null);
    }
}
