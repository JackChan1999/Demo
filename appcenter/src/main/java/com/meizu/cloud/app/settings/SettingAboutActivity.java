package com.meizu.cloud.app.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.core.i;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.b.a;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.base.app.BaseActivity;
import com.meizu.cloud.statistics.b;

public class SettingAboutActivity extends BaseActivity {
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        b(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        onBackPressed();
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(g.about_layout);
        ViewGroup layout = (LinearLayout) findViewById(f.about_main);
        ImageView icon = (ImageView) layout.findViewById(f.icon);
        a(g());
        a(icon);
        d.a((Context) this, layout);
        ((TextView) findViewById(f.version)).setText("V " + i.e(this, getPackageName()));
        if (x.b((Context) this)) {
            ((TextView) findViewById(f.web_url)).setText("http://app.meizu.com/games/public/index");
        }
    }

    protected void a(ImageView icon) {
    }

    protected void a(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.a(a.i.about);
            actionBar.b(true);
            actionBar.a(true);
        }
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
