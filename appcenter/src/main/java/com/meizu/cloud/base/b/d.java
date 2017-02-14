package com.meizu.cloud.base.b;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.cloud.app.fragment.p;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.base.app.BaseSecondActivity;
import com.meizu.cloud.thread.component.b;

public abstract class d extends b {
    protected boolean mIsFirstClassPage = false;
    private boolean mIsPageShowwing = false;
    private View mMainView = null;
    protected int[] mPageInfo = new int[3];
    protected String mPageName;

    public interface a {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);
    }

    protected abstract View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle);

    protected abstract void initView(View view);

    protected ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).g();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.mMainView == null || this.mMainView.getParent() != null) {
            this.mMainView = createView(inflater, container, savedInstanceState);
            initView(this.mMainView);
        }
        return this.mMainView;
    }

    public View getView() {
        return super.getView() == null ? this.mMainView : super.getView();
    }

    protected void setupActionBar() {
        getActionBar().c(true);
        getActionBar().e(false);
        getActionBar().a(false);
        getActionBar().f(false);
        getActionBar().e(false);
        getActionBar().e();
        if (this.mIsFirstClassPage) {
            getActionBar().b(false);
            return;
        }
        getActionBar().b(true);
        getActionBar().a(true);
    }

    public void onResume() {
        super.onResume();
        setupActionBar();
    }

    public Fragment getRootFragment() {
        Fragment fragment = this;
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        getActivity().onBackPressed();
        return true;
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(f.search_menu);
        MenuItem shareItem = menu.findItem(f.share_menu);
        if (menuItem != null) {
            menuItem.setVisible(true);
        }
        if (shareItem != null) {
            shareItem.setVisible(false);
        }
    }

    public static void startFragment(FragmentActivity activity, Fragment tarFragment) {
        if (activity != null) {
            BaseSecondActivity.a((Context) activity, tarFragment);
        }
    }

    public static void startSearchFragment(FragmentActivity activity, p tarFragment) {
        startSearchFragment(activity, tarFragment, false, true);
    }

    public static void startSearchFragment(FragmentActivity activity, p tarFragment, boolean popBackStack, boolean showKeyboard) {
        if (popBackStack) {
            activity.finish();
        }
        Bundle bundle = tarFragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putBoolean(p.SHOW_KEYBOARD, showKeyboard);
        tarFragment.setArguments(bundle);
        BaseSecondActivity.a((Context) activity, (Fragment) tarFragment);
        activity.overridePendingTransition(com.meizu.cloud.b.a.a.mz_search_activity_open_enter_alpha, com.meizu.cloud.b.a.a.mz_search_activity_open_exit_alpha);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!this.mIsPageShowwing) {
                onRealPageStart();
                this.mIsPageShowwing = true;
            }
        } else if (this.mIsPageShowwing) {
            onRealPageStop();
            this.mIsPageShowwing = false;
        }
    }

    public void onStop() {
        super.onStop();
        if (this.mIsPageShowwing) {
            onRealPageStop();
            this.mIsPageShowwing = false;
        }
    }

    public void onStart() {
        super.onStart();
        if (!this.mIsPageShowwing && getUserVisibleHint()) {
            onRealPageStart();
            this.mIsPageShowwing = true;
        }
    }

    protected void onRealPageStart() {
    }

    protected void onRealPageStop() {
    }
}
