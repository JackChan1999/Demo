package com.meizu.flyme.appcenter.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.statistics.b;
import com.meizu.mstore.R;

public class k extends p {

    private static class a {
        private static int a = -1;

        public static boolean a(Context context) {
            if (a < 0) {
                int i;
                if (PreferenceManager.getDefaultSharedPreferences(context).getBoolean("filter_installed_5.0", false)) {
                    i = 1;
                } else {
                    i = 0;
                }
                a = i;
            }
            if (a == 1) {
                return true;
            }
            return false;
        }

        public static void b(Context context) {
            if (a <= 0) {
                PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean("filter_installed_5.0", true).apply();
                a = 1;
            }
        }

        public static void c(Context context) {
            if (a != 1) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                int times = sp.getInt("filter_installed_showtimes_5.0", 0) + 1;
                sp.edit().putInt("filter_installed_showtimes_5.0", times).apply();
                if (times == 2) {
                    b(context);
                }
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.a = com.meizu.cloud.app.settings.a.a(getActivity()).f();
        this.b.c().b = true;
    }

    public void initView(View rootView) {
        super.initView(rootView);
        if (com.meizu.cloud.app.settings.a.a(getActivity()).f() && !a.a(getActivity())) {
            final RelativeLayout filterLayout = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.filter_install_layout, null);
            ((TextView) filterLayout.findViewById(R.id.installed_filter_tips)).setBackgroundColor(getResources().getColor(R.color.installed_filter_baground));
            d.b(getActivity(), filterLayout);
            final int filterHeight = getResources().getDimensionPixelOffset(R.dimen.installed_tip_height) + getResources().getDimensionPixelOffset(R.dimen.installed_tip_margintop);
            getRecyclerView().setPadding(getRecyclerView().getPaddingLeft(), getRecyclerView().getPaddingTop() + filterHeight, getRecyclerView().getPaddingRight(), getRecyclerView().getPaddingBottom());
            final FrameLayout layout = (FrameLayout) rootView.findViewById(R.id.recycler_outside_layout);
            layout.addView(filterLayout);
            a.c(getActivity());
            filterLayout.findViewById(R.id.delete_tip).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ k d;

                public void onClick(View v) {
                    a.b(this.d.getActivity());
                    this.d.getRecyclerView().setPadding(this.d.getRecyclerView().getPaddingLeft(), this.d.getRecyclerView().getPaddingTop() - filterHeight, this.d.getRecyclerView().getPaddingRight(), this.d.getRecyclerView().getPaddingBottom());
                    layout.removeView(filterLayout);
                    b.a().a("topall_cancelfilter", this.d.mPageName, null);
                }
            });
        }
    }

    protected void onRealPageStart() {
    }

    protected void onRealPageStop() {
    }
}
