package com.meizu.cloud.app.fragment;

import android.graphics.Bitmap;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.meizu.cloud.app.a.i;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.base.b.h.a;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.statistics.c;
import com.squareup.picasso.Callback;
import flyme.support.v7.widget.LinearLayoutManager;
import java.util.HashMap;
import java.util.Map;

public abstract class q extends s implements Callback {
    final int a = 500;
    final int b = 500;
    protected t c;
    private boolean e = false;
    private int k;
    private Drawable l;
    private Drawable m;
    private Drawable n;
    private MenuItem o;
    private Drawable p;
    private Drawable q;
    private String r;
    private UxipPageSourceInfo s;

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((a) obj);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageName = "Topic_" + getArguments().getString("title_name", "");
        this.c = new t(getActivity(), new u());
        this.mPageInfo[1] = 3;
        this.mPageInfo[2] = c.f(getArguments().getString("url", ""));
        this.c.a(this.mPageName);
        this.c.a(this.mPageInfo);
        this.mExtraPaddingTop = -d.f(getActivity());
        if (getArguments().containsKey("uxip_page_source_info")) {
            this.s = (UxipPageSourceInfo) getArguments().getParcelable("uxip_page_source_info");
        } else if (getArguments().containsKey("source_page")) {
            this.r = getArguments().getString("source_page", "");
        }
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        LayoutParams layoutParams = (LayoutParams) getRecyclerView().getLayoutParams();
        layoutParams.topMargin = d.f(getActivity());
        getRecyclerView().setLayoutParams(layoutParams);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.o = menu.findItem(f.search_menu);
        this.n = menu.findItem(f.search_menu).getIcon();
    }

    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar actionBar = getActionBar();
        Bundle bundle = getArguments();
        if (bundle != null) {
            actionBar.a(bundle.getString("title_name", ""));
        }
        c();
        d();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.e = true;
    }

    public void onPause() {
        super.onPause();
        com.meizu.cloud.app.utils.u.a(getActivity(), this.k);
        Drawable icon = com.meizu.cloud.app.utils.u.b(getActivity());
        if (icon != null && this.n != null) {
            icon.setColorFilter(this.k, Mode.MULTIPLY);
            this.n.setColorFilter(this.k, Mode.MULTIPLY);
            if (this.o != null) {
                this.o.setIcon(this.n);
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.e = false;
        e();
    }

    protected void c() {
        if (this.l == null) {
            try {
                FragmentActivity activity = getActivity();
                this.l = com.meizu.cloud.app.utils.u.c(activity);
                this.k = com.meizu.cloud.app.utils.u.a(activity);
                this.m = activity.getWindow().getDecorView().getBackground();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void d() {
        if (this.d != null && this.d.colors != null && this.e) {
            if (this.d.colors.statusicon_color == -1) {
                com.meizu.cloud.app.utils.u.a(getActivity(), false);
            } else if (this.d.colors.statusicon_color == -16777216) {
                com.meizu.cloud.app.utils.u.a(getActivity(), true);
            } else {
                com.meizu.cloud.app.utils.u.a(getActivity(), true);
            }
            Window window = getActivity().getWindow();
            if (window != null) {
                window.setBackgroundDrawable(com.meizu.cloud.app.utils.u.a(getActivity(), this.d.colors.bg_color));
            }
            int titleColor = this.d.colors.title_color;
            com.meizu.cloud.app.utils.u.a(getActivity(), titleColor);
            Drawable icon = com.meizu.cloud.app.utils.u.b(getActivity());
            if (!(icon == null || this.n == null)) {
                icon.setColorFilter(titleColor, Mode.MULTIPLY);
                this.n.setColorFilter(titleColor, Mode.MULTIPLY);
            }
            ActionBar actionBar = getActionBar();
            if (actionBar == null) {
                return;
            }
            if (this.p == null) {
                Drawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(getResources().getColor(com.meizu.cloud.b.a.c.theme_color)), com.meizu.cloud.app.utils.u.a(this.d.colors.actionbar_color, 92)});
                actionBar.a(transitionDrawable);
                transitionDrawable.startTransition(500);
                return;
            }
            actionBar.a(this.p);
        }
    }

    protected void e() {
        com.meizu.cloud.app.utils.u.a(getActivity(), false);
        if (this.m != null) {
            getActivity().getWindow().setBackgroundDrawable(this.m);
        }
        com.meizu.cloud.app.utils.u.a(getActivity(), this.k);
        Drawable icon = com.meizu.cloud.app.utils.u.b(getActivity());
        if (!(icon == null || this.n == null)) {
            icon.setColorFilter(this.k, Mode.MULTIPLY);
            this.n.setColorFilter(this.k, Mode.MULTIPLY);
            if (this.o != null) {
                this.o.setIcon(this.n);
            }
        }
        ActionBar actionBar = getActionBar();
        if (!(actionBar == null || this.l == null)) {
            actionBar.a(this.l);
        }
        getActivity().invalidateOptionsMenu();
    }

    public void onSuccess() {
        if (getActivity() != null && !isDetached() && this.q == null) {
            asyncExec(new Runnable(this) {
                final /* synthetic */ q a;

                {
                    this.a = r1;
                }

                public void run() {
                    Process.setThreadPriority(10);
                    if (this.a.d != null && !TextUtils.isEmpty(this.a.d.banner)) {
                        Drawable[] drawables = this.a.a(h.a(this.a.getActivity(), this.a.d.banner));
                        if (drawables != null) {
                            this.a.p = drawables[0];
                            this.a.q = drawables[1];
                        }
                        this.a.runOnUi(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                if (this.a.a.getActivity() != null) {
                                    this.a.a.a(this.a.a.p);
                                    this.a.a.b(this.a.a.q);
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    private Drawable[] a(Bitmap bitmap) {
        if (bitmap == null || getActivity() == null) {
            return null;
        }
        int coverColor = 0;
        int shadowColor = 0;
        if (!(this.d == null || this.d.colors == null)) {
            coverColor = this.d.colors.actionbar_color;
            shadowColor = this.d.colors.bg_color;
        }
        int actionBarHeight = d.f(getActivity());
        int headerHeight = getResources().getDimensionPixelSize(com.meizu.cloud.b.a.d.special_header_image_layout_height);
        Bitmap blurBitmap = com.meizu.cloud.app.utils.u.a(bitmap, coverColor);
        int actionBarBmWidth = blurBitmap.getWidth();
        int actionBarBmHeight = (blurBitmap.getHeight() * actionBarHeight) / (actionBarHeight + headerHeight);
        BitmapDrawable actionBarDrawable = new BitmapDrawable(Bitmap.createBitmap(blurBitmap, 0, 0, actionBarBmWidth, actionBarBmHeight));
        Bitmap bmHeader = Bitmap.createBitmap(blurBitmap, 0, actionBarBmHeight, actionBarBmWidth, blurBitmap.getHeight() - actionBarBmHeight);
        com.meizu.cloud.app.utils.u.a(bmHeader, new Rect(0, 0, bmHeader.getWidth(), bmHeader.getHeight()), new int[]{16777215 & shadowColor, 184549375 & shadowColor, 872415231 & shadowColor, 1728053247 & shadowColor, -1711276033 & shadowColor, -855638017 & shadowColor, shadowColor, shadowColor}, Orientation.TOP_BOTTOM);
        BitmapDrawable headerDrawable = new BitmapDrawable(bmHeader);
        bitmap.recycle();
        blurBitmap.recycle();
        return new Drawable[]{actionBarDrawable, headerDrawable};
    }

    public void onError() {
    }

    private void a(Drawable drawable) {
        if (drawable != null && this.mRunning && this.e) {
            int startColor = getResources().getColor(com.meizu.cloud.b.a.c.theme_color);
            if (!(this.d == null || this.d.colors == null)) {
                startColor = this.d.colors.actionbar_color;
            }
            Drawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(startColor), drawable});
            getActionBar().a(transitionDrawable);
            transitionDrawable.startTransition(500);
        }
    }

    private void b(Drawable drawable) {
        if (drawable != null && getRecyclerView() != null && getRecyclerView().getLayoutManager() != null) {
            i adapter = (i) getRecyclerViewAdapter();
            adapter.a(drawable);
            if (((LinearLayoutManager) getRecyclerView().getLayoutManager()).findFirstVisibleItemPosition() == 0) {
                TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{new ColorDrawable(0), drawable});
                View headerView = getRecyclerView().findViewWithTag("header_layout");
                if (headerView != null) {
                    headerView.setBackground(transitionDrawable);
                }
                transitionDrawable.startTransition(500);
                return;
            }
            adapter.g();
        }
    }

    public com.meizu.cloud.app.a.a f() {
        i adapter = new i(getActivity(), this.c);
        adapter.a((Callback) this);
        return adapter;
    }

    public void onDestroy() {
        super.onDestroy();
        if ((getRecyclerViewAdapter() instanceof i) && getRecyclerView() != null) {
            ((i) getRecyclerViewAdapter()).a((ImageView) getRecyclerView().findViewWithTag("header_image"));
        }
    }

    protected boolean a(a resultModel) {
        if (resultModel instanceof s.a) {
            ((i) getRecyclerViewAdapter()).a(((s.a) resultModel).a);
        }
        return super.a(resultModel);
    }

    protected void onLoadFinished() {
        super.onLoadFinished();
        d();
    }

    public void onStart() {
        super.onStart();
        b.a().a(this.mPageName);
    }

    public void onStop() {
        super.onStop();
        b.a().a(this.mPageName, g());
    }

    public Map<String, String> g() {
        Map<String, String> wdmParamsMap = new HashMap();
        wdmParamsMap.put("count", String.valueOf(1));
        wdmParamsMap.put("sum", String.valueOf(getRecyclerViewAdapter().d()));
        wdmParamsMap.put("topicid", String.valueOf(this.mPageInfo[2]));
        wdmParamsMap.put("topicname", getArguments().getString("title_name", ""));
        if (this.s != null) {
            wdmParamsMap.put("source_page", this.s.f);
            wdmParamsMap.put("source_block_id", String.valueOf(this.s.b));
            wdmParamsMap.put("source_block_name", this.s.c);
            wdmParamsMap.put("source_block_type", this.s.a);
            if (this.s.g > 0) {
                wdmParamsMap.put("source_block_profile_id", String.valueOf(this.s.g));
            }
            wdmParamsMap.put("source_pos", String.valueOf(this.s.d));
            if (this.s.e > 0) {
                wdmParamsMap.put("source_hor_pos", String.valueOf(this.s.e));
            }
        } else if (!TextUtils.isEmpty(this.r)) {
            wdmParamsMap.put("source_page", this.r);
        }
        long pushId = getArguments().getLong("push_message_id", 0);
        if (pushId > 0) {
            wdmParamsMap.put("push_id", String.valueOf(pushId));
        }
        return wdmParamsMap;
    }
}
