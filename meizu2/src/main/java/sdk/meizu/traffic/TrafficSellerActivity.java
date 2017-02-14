package sdk.meizu.traffic;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.meizu.statsapp.UsageStatsProxy;
import sdk.meizu.traffic.interfaces.ActionBarHandler;

public class TrafficSellerActivity extends AppCompatActivity implements ActionBarHandler {
    private static final String TAG = TrafficSellerActivity.class.getSimpleName();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        View inflate = LayoutInflater.from(this).inflate(R.layout.fragment_container, null);
        inflate.setFitsSystemWindows(true);
        setContentView(inflate);
        initActionBar();
        replaceFragment(new TrafficMainFragment(), false);
    }

    private void replaceFragment(Fragment fragment, boolean z) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fragment_container, fragment);
        if (z) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commit();
    }

    protected void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowCustomEnabled(true);
            TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.action_bar_custom_view, null);
            textView.setText(R.string.title_help);
            ColorStateList customTextColor = getCustomTextColor();
            if (customTextColor != null) {
                textView.setTextColor(customTextColor);
            }
            textView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    TrafficSellerActivity.this.replaceFragment(new TrafficTipFragment(), true);
                    UsageStatsProxy.a(TrafficSellerActivity.this, true).a("showHelpTip", TrafficMainFragment.class.getSimpleName(), "");
                    Log.v("UsageEvent", "showHelpTip:eventName");
                }
            });
            supportActionBar.setCustomView(textView, new LayoutParams(-2, -1, 5));
        }
    }

    private ColorStateList getCustomTextColor() {
        try {
            return ((TextView) ((ViewGroup) getWindow().getDecorView().findViewById(R.id.action_bar)).getChildAt(0)).getTextColors();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        if (getFragmentManager() == null || !getFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
            UsageStatsProxy.a((Context) this, true).a("onBackPressed", TrafficMainFragment.class.getSimpleName(), "");
            Log.v("UsageEvent", "eventName:onBackPressed");
        }
    }

    public void enableCustomView(boolean z) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowCustomEnabled(z);
        }
    }

    public void enableHomeBack(boolean z) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowHomeEnabled(!z);
            supportActionBar.setDisplayHomeAsUpEnabled(z);
            supportActionBar.setHomeButtonEnabled(z);
        }
    }
}
