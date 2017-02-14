package com.meizu.cloud.app.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.a.n;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.AppCommentItem;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.DataReultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.widget.BaseStarRateWidget;
import com.meizu.cloud.b.a;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.b.e;
import java.util.ArrayList;
import java.util.List;

public class c extends e<ResultModel<DataReultModel<AppCommentItem>>> implements OnClickListener, OnScrollListener {
    private Context a;
    private AppStructDetailsItem b;
    private List<AppCommentItem> c;
    private List<AppCommentItem> d;
    private n e;
    private boolean f;
    private boolean g;
    private View h;
    private TextView i;
    private BaseStarRateWidget j;
    private LinearLayout k;
    private int l = 1;
    private boolean m = false;
    private FastJsonRequest<DataReultModel<AppCommentItem>> n;

    static /* synthetic */ int a(c x0, int x1) {
        int i = x0.l + x1;
        x0.l = i;
        return i;
    }

    protected /* synthetic */ boolean onResponse(Object obj) {
        return a((ResultModel) obj);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.a = getActivity();
        this.b = (AppStructDetailsItem) getArguments().getSerializable("details_info");
        a(getArguments().getParcelableArrayList("comment_list_data"));
        this.f = getArguments().getBoolean("more", false);
        this.m = getArguments().getBoolean("build_in", false);
        this.mPageName = "user_comment";
    }

    private void a(List<AppCommentItem> commentsListData) {
        if (commentsListData != null && commentsListData.size() > 0) {
            for (AppCommentItem item : commentsListData) {
                if (item.type == 2) {
                    if (this.c == null) {
                        this.c = new ArrayList();
                    }
                    this.c.add(item);
                } else {
                    if (this.d == null) {
                        this.d = new ArrayList();
                    }
                    this.d.add(item);
                }
            }
        }
    }

    protected void setupActionBar() {
        super.setupActionBar();
        getActionBar().a(i.comments_fragment_actionbar_title);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        g().setPadding(g().getPaddingLeft(), g().getPaddingTop(), g().getPaddingRight(), (int) getResources().getDimension(d.app_info_bottom_height));
        try {
            com.meizu.cloud.c.b.a.c.a().a(g().getClass(), "setDelayTopOverScrollEnabled", Boolean.TYPE).invoke(g(), new Object[]{Boolean.valueOf(false)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        a();
        this.e = new n(this.a, (long) this.b.id, this.b.version_code, this.c, this.d);
        if (this.c == null || this.c.size() <= 0) {
            this.e.a(1, false);
        }
        g().setAdapter(this.e);
        g().setOnScrollListener(this);
        g().setVisibility(8);
        showProgress();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void a() {
        this.h = ((FragmentActivity) this.a).getLayoutInflater().inflate(g.comment_stars_layout, null, false);
        this.j = (BaseStarRateWidget) this.h.findViewById(f.starRate);
        this.j.setClickable(false);
        this.j.setValue((double) (((float) this.b.star) / 10.0f));
        this.k = (LinearLayout) this.h.findViewById(f.add_comment_btn);
        this.k.setOnClickListener(this);
        this.i = (TextView) this.h.findViewById(f.averageValue);
        try {
            this.i.setTypeface(Typeface.createFromFile("/system/fonts/DINPro-Normal.otf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] mStarList = this.b.star_percent;
        ProgressBar bar1 = (ProgressBar) this.h.findViewById(f.fiveProgressBar);
        bar1.setProgressDrawable(a(com.meizu.cloud.b.a.c.comment_detail_progress_fifth));
        bar1.setProgress(mStarList[4]);
        bar1 = (ProgressBar) this.h.findViewById(f.fourProgressBar);
        bar1.setProgressDrawable(a(com.meizu.cloud.b.a.c.comment_detail_progress_fourth));
        bar1.setProgress(mStarList[3]);
        bar1 = (ProgressBar) this.h.findViewById(f.threeProgressBar);
        bar1.setProgressDrawable(a(com.meizu.cloud.b.a.c.comment_detail_progress_third));
        bar1.setProgress(mStarList[2]);
        bar1 = (ProgressBar) this.h.findViewById(f.twoProgressBar);
        bar1.setProgressDrawable(a(com.meizu.cloud.b.a.c.comment_detail_progress_second));
        bar1.setProgress(mStarList[1]);
        bar1 = (ProgressBar) this.h.findViewById(f.oneProgressBar);
        bar1.setProgressDrawable(a(com.meizu.cloud.b.a.c.comment_detail_progress_first));
        bar1.setProgress(mStarList[0]);
        ((TextView) this.h.findViewById(f.star5count)).setText(String.valueOf(mStarList[4]) + "%");
        ((TextView) this.h.findViewById(f.star4count)).setText(String.valueOf(mStarList[3]) + "%");
        ((TextView) this.h.findViewById(f.star3count)).setText(String.valueOf(mStarList[2]) + "%");
        ((TextView) this.h.findViewById(f.star2count)).setText(String.valueOf(mStarList[1]) + "%");
        ((TextView) this.h.findViewById(f.star1count)).setText(String.valueOf(mStarList[0]) + "%");
        this.i.setText(String.format("%.1f", new Object[]{Double.valueOf(this.b.avg_score)}));
        ListView listView = g();
        listView.addHeaderView(this.h);
        listView.setHeaderDividersEnabled(false);
    }

    protected LayerDrawable a(int colorId) {
        LayerDrawable d = (LayerDrawable) getResources().getDrawable(a.e.progress_horizontal_color);
        d.setDrawableByLayerId(16908301, new ClipDrawable(new ColorDrawable(getResources().getColor(colorId)), 3, 1));
        return d;
    }

    private String b() {
        return RequestConstants.getRuntimeDomainUrl(this.a, RequestConstants.EVALUATE);
    }

    private void c() {
        int i = 1;
        if (!this.g) {
            this.g = true;
            TypeReference typeRef = new TypeReference<ResultModel<DataReultModel<AppCommentItem>>>(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }
            };
            List paramList = new ArrayList();
            paramList.add(new com.meizu.volley.b.a("start", String.valueOf(this.l * 10)));
            paramList.add(new com.meizu.volley.b.a("max", String.valueOf(10)));
            paramList.add(new com.meizu.volley.b.a(x.b(this.a) ? "game_id" : "app_id", String.valueOf(this.b.id)));
            String str = "build_in";
            if (!this.m) {
                i = 0;
            }
            paramList.add(new com.meizu.volley.b.a(str, String.valueOf(i)));
            this.n = new FastJsonRequest(typeRef, b(), 0, paramList, new b<ResultModel<DataReultModel<AppCommentItem>>>(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<DataReultModel<AppCommentItem>> response) {
                    if (this.a.mRunning && this.a.getActivity() != null) {
                        if (response == null || response.getCode() != 200 || response.getValue() == null || ((DataReultModel) response.getValue()).data == null) {
                            this.a.a("", true);
                            return;
                        }
                        this.a.d.addAll(((DataReultModel) response.getValue()).data);
                        this.a.f = ((DataReultModel) response.getValue()).more;
                        c.a(this.a, 1);
                        this.a.a(null, true);
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                    this.a.a("", true);
                }
            });
            this.n.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(this.n);
        }
    }

    private void a(String errStr, boolean isLoadMore) {
        if (this.mRunning && this.a != null) {
            if (errStr == null) {
                if (isLoadMore) {
                    this.e.a(1, this.d);
                } else {
                    this.e.a(0, this.c);
                    this.e.a(1, this.d);
                }
            }
            this.g = false;
            hideProgress();
            g().setVisibility(0);
        }
    }

    public void onClick(View v) {
        if (v != this.k) {
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.n != null) {
            this.n.cancel();
        }
    }

    public void onResume() {
        super.onResume();
    }

    protected void onRequestData() {
        int i;
        TypeReference typeRef = new TypeReference<ResultModel<DataReultModel<AppCommentItem>>>(this) {
            final /* synthetic */ c a;

            {
                this.a = r1;
            }
        };
        List paramList = new ArrayList();
        paramList.add(new com.meizu.volley.b.a("start", String.valueOf(0)));
        paramList.add(new com.meizu.volley.b.a("max", String.valueOf(10)));
        paramList.add(new com.meizu.volley.b.a(x.b(this.a) ? "game_id" : "app_id", String.valueOf(this.b.id)));
        String str = "build_in";
        if (this.m) {
            i = 1;
        } else {
            i = 0;
        }
        paramList.add(new com.meizu.volley.b.a(str, String.valueOf(i)));
        this.mLoadRequest = new FastJsonRequest(typeRef, b(), 0, paramList, new com.meizu.cloud.base.b.f.b(this), new com.meizu.cloud.base.b.f.a(this));
        this.mLoadRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
        com.meizu.volley.b.a(this.a).a().a(this.mLoadRequest);
    }

    protected boolean a(ResultModel<DataReultModel<AppCommentItem>> response) {
        if (!this.mRunning || getActivity() == null) {
            return false;
        }
        if (response == null || response.getCode() != 200 || response.getValue() == null || ((DataReultModel) response.getValue()).data == null) {
            a("", false);
            return false;
        }
        if (this.c == null) {
            this.c = new ArrayList();
        } else {
            this.c.clear();
        }
        if (this.d == null) {
            this.d = new ArrayList();
        } else {
            this.d.clear();
        }
        a(((DataReultModel) response.getValue()).data);
        this.f = ((DataReultModel) response.getValue()).more;
        a(null, false);
        return true;
    }

    protected void onErrorResponse(s error) {
        a("", false);
    }

    public void onPause() {
        super.onPause();
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (g().getLastVisiblePosition() == g().getCount() - 1) {
            c();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    protected void onRealPageStart() {
        super.onRealPageStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        super.onRealPageStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }
}
