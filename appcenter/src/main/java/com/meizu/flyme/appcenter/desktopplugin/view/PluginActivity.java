package com.meizu.flyme.appcenter.desktopplugin.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.meizu.cloud.app.request.model.PluginItem;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.statistics.b;
import com.meizu.flyme.appcenter.desktopplugin.presenter.a;
import com.meizu.flyme.appcenter.desktopplugin.presenter.c;
import com.meizu.flyme.appcenter.desktopplugin.view.customview.IconBadgeView;
import com.meizu.flyme.appcenter.desktopplugin.view.customview.MaskIamgeview;
import com.meizu.flyme.appcenter.desktopplugin.view.customview.PluginGridView;
import com.meizu.mstore.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PluginActivity extends FragmentActivity implements b {
    public static boolean k;
    public int j;
    private c l;
    private a m;
    private com.meizu.flyme.appcenter.desktopplugin.a.a n;
    private LinearLayout o;
    private RelativeLayout p;
    private PluginGridView q;
    private RelativeLayout r;
    private RelativeLayout s;
    private ImageView t;
    private TextView u;
    private int v = -1;
    private int w = -1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a.a.a.c.a().a((Object) this);
        if (this.l == null) {
            this.l = new c();
            this.l.a((b) this);
        }
        if (this.m == null) {
            this.m = new a();
            this.m.a((b) this);
        }
        if (s.c(a()).booleanValue()) {
            sendBroadcast(new Intent("com.meizu.mstore.plugin_wallpapercolor_recalculate"));
            this.j = s.d(a());
        } else {
            this.j = s.d(a());
        }
        k = d.l(a().getApplicationContext());
        setContentView(R.layout.plugin_desktop_layout);
        getWindow().setBackgroundDrawable(this.l.c());
        this.o = (LinearLayout) findViewById(R.id.desktop_background);
        this.o.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PluginActivity a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.finish();
            }
        });
        this.s = (RelativeLayout) findViewById(R.id.plugin_toast_layout);
        this.q = (PluginGridView) findViewById(R.id.plugin_gridview);
        this.r = (RelativeLayout) findViewById(R.id.search_layout);
        this.t = (ImageView) findViewById(R.id.plugin_notice_icon);
        this.t.setColorFilter(this.j, Mode.SRC_ATOP);
        this.t.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PluginActivity a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.i();
            }
        });
        this.u = (TextView) findViewById(R.id.plugin_topName);
        this.p = (RelativeLayout) findViewById(R.id.plugin_noapp);
        this.p.setVisibility(4);
        a(this.q);
        this.r.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ PluginActivity a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.l.b();
            }
        });
        l();
        new Thread(new Runnable(this) {
            final /* synthetic */ PluginActivity a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.m.a(Boolean.valueOf(true));
            }
        }).start();
        b.a().a("jxcj_baoguang", "plugin", null);
    }

    protected void onStart() {
        super.onStart();
        b.a().a(getClass().getSimpleName());
    }

    private void l() {
        this.u.setTextColor(this.j);
        ((TextView) this.r.findViewById(R.id.plugin_searchText)).setTextColor(this.j);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void a(PluginGridView gridView) {
        this.n = new com.meizu.flyme.appcenter.desktopplugin.a.a(a().getApplicationContext(), this.j);
        gridView.setVisibility(0);
        gridView.setSelector(new ColorDrawable(0));
        gridView.setAdapter(this.n);
        gridView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ PluginActivity a;

            {
                this.a = r1;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PluginItem downloadPlugin = (PluginItem) this.a.q.getAdapter().getItem(position);
                this.a.m.a(downloadPlugin, view, this.a.m.d());
                if (downloadPlugin != null) {
                    b.a().a("jxcj_click", "plugin", com.meizu.cloud.statistics.c.b(downloadPlugin.getPackage_name()));
                }
            }
        });
        gridView.setOnLoadMoreListener(new PluginGridView.a(this) {
            final /* synthetic */ PluginActivity a;

            {
                this.a = r1;
            }

            public void a() {
                if (this.a.m.d().size() != 0 && !((PluginItem) this.a.m.d().get(0)).isFromCache.booleanValue() && m.b(this.a.a().getApplicationContext())) {
                    if (!this.a.m.i.booleanValue()) {
                        this.a.m.a(Boolean.valueOf(false));
                    }
                    if (m.b(this.a.a()) && this.a.w != this.a.m.d().size()) {
                        int pageNum = this.a.m.d().size() / 9;
                        if (pageNum != this.a.v && pageNum > 0) {
                            b.a().a("jxcj_nextpage", "plugin", com.meizu.cloud.statistics.c.c(pageNum + ""));
                        }
                        this.a.v = pageNum;
                        this.a.w = this.a.m.d().size();
                    }
                }
            }
        });
        Drawable bg = gridView.getBackground();
        bg.setColorFilter(new PorterDuffColorFilter(this.j, Mode.SRC_ATOP));
        gridView.setBackground(bg);
    }

    protected void onStop() {
        super.onStop();
        b.a().a(getClass().getSimpleName(), null);
        if (this.m.l.booleanValue() || this.m.m.booleanValue()) {
            this.m.b();
        }
    }

    protected void onDestroy() {
        for (int i = 0; i < this.n.a.size(); i++) {
            IconBadgeView iconBadgeView = (IconBadgeView) this.n.a.get(i);
            iconBadgeView.cancel();
            iconBadgeView.a();
        }
        Picasso.with(a().getApplicationContext()).cancelTag(PluginActivity.class);
        a.a.a.c.a().c(this);
        this.l.a();
        this.m.a();
        super.onDestroy();
    }

    public void onEvent(com.meizu.flyme.appcenter.desktopplugin.presenter.a.b iconReadyEvent) {
        com.meizu.flyme.appcenter.desktopplugin.b.c.a(a().getApplicationContext(), this.m.j);
    }

    public void onEventMainThread(com.meizu.flyme.appcenter.desktopplugin.presenter.a.a dataReadyEvent) {
        a(dataReadyEvent.a());
    }

    public void a(List<PluginItem> pluginItemList) {
        if (pluginItemList.size() == 0) {
            j();
            return;
        }
        this.q.setVisibility(0);
        this.p.setVisibility(4);
        if (!((PluginItem) pluginItemList.get(0)).isFromCache.booleanValue()) {
            this.m.l = Boolean.valueOf(true);
        }
        com.meizu.flyme.appcenter.desktopplugin.a.a adapter = (com.meizu.flyme.appcenter.desktopplugin.a.a) this.q.getAdapter();
        List<PluginItem> temp = new ArrayList();
        temp.addAll(pluginItemList);
        pluginItemList.clear();
        pluginItemList.addAll(temp);
        adapter.a((List) pluginItemList);
        adapter.notifyDataSetChanged();
    }

    protected void onResume() {
        super.onResume();
    }

    public void a(PluginItem pluginItem) {
        View view = this.q.findViewWithTag(pluginItem.getPackage_name());
        if (view != null) {
            MaskIamgeview maskIamgeview = (MaskIamgeview) view.findViewById(R.id.plugin_item_icon);
            IconBadgeView iconBadgeView = (IconBadgeView) view.findViewById(R.id.plugin_item_badgeview);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.plugin_progress);
            if (pluginItem.getState() == 3) {
                progressBar.setVisibility(0);
            } else {
                progressBar.setVisibility(4);
            }
            maskIamgeview.setState(pluginItem.getState());
            maskIamgeview.setmProgress(pluginItem.getProgerss());
            iconBadgeView.setState(pluginItem.getState());
        }
    }

    public void a(final View v, final PluginItem pluginItem) {
        ObjectAnimator animator = a(v, true);
        animator.setInterpolator(new PathInterpolator(0.2f, 0.0f, 0.3f, 1.0f));
        animator.setDuration(300);
        animator.addListener(new AnimatorListener(this) {
            final /* synthetic */ PluginActivity c;

            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                v.setTag(pluginItem.getPackage_name());
                final MaskIamgeview maskIamgeview = (MaskIamgeview) v.findViewById(R.id.plugin_item_icon);
                final TextView textView = (TextView) v.findViewById(R.id.plugin_item_title);
                Picasso.with(this.c.a().getApplicationContext()).load(pluginItem.getIcon()).tag(PluginActivity.class).error((int) R.drawable.plugin_appdefault_ic_noflyme).placeholder((int) R.drawable.plugin_appdefault_ic_noflyme).into(maskIamgeview, new Callback(this) {
                    final /* synthetic */ AnonymousClass7 c;

                    public void onSuccess() {
                        textView.setText(pluginItem.getName());
                        this.c.c.a(v, pluginItem, Boolean.valueOf(false));
                    }

                    public void onError() {
                        maskIamgeview.setImageResource(R.drawable.plugin_appdefault_ic_noflyme);
                        textView.setText(pluginItem.getName());
                        this.c.c.a(v, pluginItem, Boolean.valueOf(false));
                    }
                });
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    public void a(final View v, PluginItem pluginItem, final Boolean isDelete) {
        ObjectAnimator animator = a(v, false);
        animator.setInterpolator(new PathInterpolator(0.2f, 0.0f, 0.3f, 1.0f));
        animator.setDuration(300);
        animator.addListener(new AnimatorListener(this) {
            final /* synthetic */ PluginActivity c;

            public void onAnimationStart(Animator animation) {
                v.setVisibility(0);
                ((IconBadgeView) v.findViewById(R.id.plugin_item_badgeview)).setState(0);
            }

            public void onAnimationEnd(Animator animation) {
                v.setVisibility(0);
                if (!this.c.m.k.booleanValue() && !isDelete.booleanValue()) {
                    this.c.h();
                    this.c.m.k = Boolean.valueOf(true);
                }
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }

    public void g() {
        TextView toastText = (TextView) this.s.findViewById(R.id.plugin_toast_text);
        ImageView toastIcon = (ImageView) this.s.findViewById(R.id.plugin_toast_icon);
        ImageView noticeIcon = (ImageView) findViewById(R.id.plugin_notice_icon);
        toastIcon.setVisibility(0);
        noticeIcon.setColorFilter(this.j, Mode.SRC_ATOP);
        toastText.setText(getResources().getString(R.string.plugin_nonetwork));
        toastText.setTextColor(this.j);
        toastText.setBackground(null);
        toastIcon.setImageResource(R.drawable.plugin_ic_alarm);
        toastIcon.setColorFilter(this.j, Mode.SRC_ATOP);
        toastIcon.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(toastText, "alpha", new float[]{0.0f, 1.0f, 1.0f, 0.0f});
        animatorSet.play(animator1).with(ObjectAnimator.ofFloat(toastIcon, "alpha", new float[]{0.0f, 1.0f, 1.0f, 0.0f}));
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    public void h() {
        TextView toastText = (TextView) this.s.findViewById(R.id.plugin_toast_text);
        ImageView toastIcon = (ImageView) this.s.findViewById(R.id.plugin_toast_icon);
        ImageView noticeIcon = (ImageView) findViewById(R.id.plugin_notice_icon);
        toastIcon.setVisibility(0);
        noticeIcon.setColorFilter(this.j, Mode.SRC_ATOP);
        toastText.setText(getResources().getString(R.string.plugin_icon_todesktop));
        toastText.setTextColor(this.j);
        toastText.setBackground(null);
        toastIcon.setImageResource(R.drawable.plugin_ic_done);
        toastIcon.setColorFilter(this.j, Mode.SRC_ATOP);
        toastIcon.setAlpha(0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(toastText, "alpha", new float[]{0.0f, 1.0f, 1.0f, 0.0f});
        animatorSet.play(animator1).with(ObjectAnimator.ofFloat(toastIcon, "alpha", new float[]{0.0f, 1.0f, 1.0f, 0.0f}));
        animatorSet.setDuration(5000);
        animatorSet.start();
    }

    public void i() {
        final TextView toastText = (TextView) this.s.findViewById(R.id.plugin_toast_text);
        ImageView toastIcon = (ImageView) this.s.findViewById(R.id.plugin_toast_icon);
        ImageView noticeIcon = (ImageView) findViewById(R.id.plugin_notice_icon);
        toastIcon.setVisibility(4);
        noticeIcon.setColorFilter(this.j, Mode.SRC_ATOP);
        toastText.setTextColor(this.j);
        toastText.setText(getResources().getString(R.string.plugin_notice_close));
        toastText.setBackground(getDrawable(R.drawable.plugin_notice_bg));
        toastText.setAlpha(0.5f);
        toastIcon.setColorFilter(this.j, Mode.SRC_ATOP);
        toastIcon.setAlpha(0.0f);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ PluginActivity b;

            public void onAnimationUpdate(ValueAnimator animation) {
                toastText.setAlpha(this.b.a(((Float) animation.getAnimatedValue()).floatValue()));
            }
        });
        animator.setDuration(4000);
        animator.start();
    }

    public float a(float time) {
        if (0.0f <= time && ((double) time) <= 0.0625d) {
            return 12.8f * time;
        }
        if (0.0625d >= ((double) time) || ((double) time) > 0.9375d) {
            return 12.8f * (1.0f - time);
        }
        return 0.8f;
    }

    public View b(PluginItem pluginItem) {
        return this.q.findViewWithTag(pluginItem.getPackage_name());
    }

    public void j() {
        this.m.m = Boolean.valueOf(true);
        this.q.setVisibility(4);
        this.p.setVisibility(0);
        TextView noApptext2 = (TextView) this.p.findViewById(R.id.plugin_noapp_text2);
        ((TextView) this.p.findViewById(R.id.plugin_noapp_text1)).setTextColor(this.j);
        noApptext2.setTextColor(this.j);
    }

    public int k() {
        return this.j;
    }

    private ObjectAnimator a(View view, boolean isShow) {
        PropertyValuesHolder pvhX;
        PropertyValuesHolder pvhY;
        PropertyValuesHolder pvhZ;
        if (isShow) {
            pvhX = PropertyValuesHolder.ofFloat("alpha", new float[]{1.0f, 0.0f});
            pvhY = PropertyValuesHolder.ofFloat("scaleX", new float[]{1.0f, 0.8f});
            pvhZ = PropertyValuesHolder.ofFloat("scaleY", new float[]{1.0f, 0.8f});
        } else {
            pvhX = PropertyValuesHolder.ofFloat("alpha", new float[]{0.0f, 1.0f});
            pvhY = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.8f, 1.0f});
            pvhZ = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.8f, 1.0f});
        }
        return ObjectAnimator.ofPropertyValuesHolder(view, new PropertyValuesHolder[]{pvhX, pvhY, pvhZ}).setDuration(1000);
    }

    public Context a() {
        return this;
    }
}
