package com.meizu.cloud.app.fragment;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.PathInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.s;
import com.meizu.cloud.app.a.n;
import com.meizu.cloud.app.block.requestitem.RollMessageStructItem;
import com.meizu.cloud.app.core.k;
import com.meizu.cloud.app.core.o;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.g;
import com.meizu.cloud.app.downlad.f.i;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.m;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.AppCommentItem;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.AppStructDetailsItem.AppTags;
import com.meizu.cloud.app.request.model.AppStructDetailsItem.Sources;
import com.meizu.cloud.app.request.model.DataReultModel;
import com.meizu.cloud.app.request.model.GameSurroundTrainItem;
import com.meizu.cloud.app.request.model.PreviewImage;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.share.ResolverActivity;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.param.BlockGotoPageInfo;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.app.widget.AnimDownloadProgressButton;
import com.meizu.cloud.app.widget.BaseStarRateWidget;
import com.meizu.cloud.app.widget.HeightAnimationLayout;
import com.meizu.cloud.app.widget.HorizontialListView;
import com.meizu.cloud.app.widget.LinearLayoutForListView;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.base.b.f;
import com.meizu.cloud.detail.anim.GalleryFlow;
import com.meizu.cloud.detail.anim.PullToZoomGroup;
import com.meizu.cloud.detail.anim.PullToZoomListView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.statistics.TrackAdInfo;
import com.meizu.cloud.statistics.UxipPageSourceInfo;
import com.meizu.cloud.statistics.c;
import com.meizu.common.widget.FoldableTextView;
import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class d extends f implements OnClickListener, com.meizu.cloud.app.downlad.f.b, com.meizu.cloud.app.downlad.f.d, e, g, i, com.meizu.cloud.base.app.BaseCommonActivity.a, com.meizu.cloud.base.app.BaseCommonActivity.b {
    public static final long[] m = new long[]{2142137411500896221L, -6806118518315004405L, -4880637255783253524L, 3518313652420932814L, -1056543911091379739L};
    private TextView A;
    private TextView B;
    private LinearLayout C;
    private ImageView D;
    private TextView E;
    private TextView F;
    private BaseStarRateWidget G;
    private TextView H;
    private String I;
    private boolean J;
    private RelativeLayout K;
    private TextView L;
    private FoldableTextView M;
    private TextView N;
    private HeightAnimationLayout O;
    private TextView P;
    private TextView Q;
    private TextView R;
    private TextView S;
    private LinearLayout T;
    private List<TextView> U;
    private LinearLayout V;
    private LinearLayout W;
    private View X;
    private RatingBar Y;
    private TextView Z;
    protected ViewFlipper a;
    private boolean aA = false;
    private boolean aB = false;
    private boolean aC = false;
    private boolean aD = false;
    private boolean aE = false;
    private boolean aF = false;
    private boolean aG = false;
    private String aH;
    private List<AppCommentItem> aI;
    private float aJ;
    private boolean aK;
    private v aL;
    private AppCommentItem aM;
    private boolean aN = false;
    private List<AppStructItem> aO;
    private boolean aP = false;
    private List<AppStructItem> aQ;
    private boolean aR = false;
    private FastJsonRequest<AppStructDetailsItem> aS;
    private FastJsonRequest<DataReultModel<AppCommentItem>> aT;
    private FastJsonRequest<DataReultModel<AppStructItem>> aU;
    private FastJsonRequest<DataReultModel<AppStructItem>> aV;
    private com.meizu.volley.a.b<Object> aW;
    private FastJsonRequest<Object> aX;
    private com.meizu.volley.a.b<JSONObject> aY;
    private int aZ;
    private LinearLayoutForListView aa;
    private RelativeLayout ab;
    private n ac;
    private TextView ad;
    private LinearLayout ae;
    private LinearLayout af;
    private HorizontialListView ag;
    private LinearLayout ah;
    private LinearLayout ai;
    private HorizontialListView aj;
    private LinearLayout ak;
    private LinearLayout al;
    private TextView am;
    private TextView an;
    private TextView ao;
    private TextView ap;
    private TextView aq;
    private TextView ar;
    private TextView as;
    private TextView at;
    private TextView au;
    private TextView av;
    private TextView aw;
    private LinearLayout ax;
    private boolean ay = false;
    private boolean az = false;
    protected ImageView b;
    private int ba;
    private String bb;
    private String bc;
    private String bd;
    private long be;
    private int bf;
    private String bg;
    private UxipPageSourceInfo bh;
    private String bi;
    private int bj;
    private int bk;
    private Bitmap bl;
    private Drawable bm;
    private int bn;
    private Drawable bo;
    private com.meizu.cloud.a.b bp;
    private TrackAdInfo bq;
    private boolean br = false;
    private ProgressDialog bs;
    private boolean bt;
    private Boolean bu = Boolean.valueOf(true);
    private com.meizu.cloud.app.share.b bv;
    private boolean bw = false;
    protected TextView c;
    protected TextView d;
    protected TextView e;
    protected TextView f;
    protected TextView g;
    protected TextView h;
    protected Context i;
    protected long j;
    protected AppStructDetailsItem k;
    protected t l;
    protected String n = "";
    protected String o = "";
    protected int p = -1;
    protected String q;
    private PullToZoomGroup r;
    private FrameLayout s;
    private GalleryFlow t;
    private PullToZoomListView u;
    private com.meizu.cloud.detail.anim.a v;
    private RelativeLayout w;
    private RelativeLayout x;
    private AnimDownloadProgressButton y;
    private FrameLayout z;

    protected class a extends BaseAdapter {
        final /* synthetic */ d a;
        private Context b;
        private List<AppStructItem> c;

        public a(d dVar, Context context, List<AppStructItem> listData) {
            this.a = dVar;
            this.b = context;
            this.c = listData;
        }

        public int getCount() {
            if (this.c == null || this.c.size() <= 0) {
                return 0;
            }
            return this.c.size();
        }

        public Object getItem(int position) {
            if (this.c == null || this.c.size() <= 0) {
                return null;
            }
            return this.c.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            b viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.b).inflate(com.meizu.cloud.b.a.g.app_details_related_horzilistview_item, null);
                viewHolder = new b(this.a);
                viewHolder.a = (ImageView) convertView.findViewById(com.meizu.cloud.b.a.f.related_horzilistview_item_icon);
                viewHolder.b = (TextView) convertView.findViewById(com.meizu.cloud.b.a.f.related_horzilistview_item_name);
                convertView.setTag(viewHolder);
            }
            viewHolder = (b) convertView.getTag();
            h.a(this.b, ((AppStructItem) this.c.get(position)).icon, viewHolder.a);
            viewHolder.b.setText(((AppStructItem) this.c.get(position)).name);
            return convertView;
        }
    }

    class b {
        public ImageView a;
        public TextView b;
        final /* synthetic */ d c;

        b(d dVar) {
            this.c = dVar;
        }
    }

    protected abstract void a(FragmentActivity fragmentActivity, BlockGotoPageInfo blockGotoPageInfo);

    protected abstract void a(View view, AppStructItem appStructItem, int i);

    protected abstract void f();

    public abstract IWXAPI g();

    public void onCreate(Bundle savedInstanceState) {
        int i;
        super.onCreate(savedInstanceState);
        this.i = getActivity();
        Bundle bundle = getArguments();
        this.mPageName = "detail";
        this.bm = u.c(getActivity());
        if (this.bm == null) {
            i = 255;
        } else {
            i = this.bm.getAlpha();
        }
        this.bn = i;
        this.bo = getActivity().getWindow().getDecorView().getBackground();
        com.meizu.cloud.app.core.u pageInfo = new com.meizu.cloud.app.core.u();
        pageInfo.c(true);
        this.l = new t((FragmentActivity) this.i, pageInfo);
        this.mPageInfo[1] = 4;
        this.mPageInfo[2] = bundle.getInt("source_page_id", 0);
        this.l.a(this.mPageName);
        this.l.a(this.mPageInfo);
        if (bundle.containsKey("app_id")) {
            this.j = bundle.getLong("app_id");
            this.aH = RequestConstants.getRuntimeDomainUrl(this.i, "/public/detail/") + this.j;
        } else if (bundle.containsKey("url")) {
            this.aH = bundle.getString("url", "");
            if (!TextUtils.isEmpty(this.aH)) {
                this.j = com.meizu.cloud.app.utils.g.d(this.aH);
                if (!this.aH.startsWith(a())) {
                    this.aH = a() + this.aH;
                }
            }
        }
        if (bundle.containsKey("unitId") && bundle.containsKey("requestId")) {
            this.aZ = bundle.getInt("positionId", 0);
            this.ba = bundle.getInt("unitId", 0);
            this.bb = bundle.getString("requestId", "");
            this.bc = bundle.getString("version", "");
            if (bundle.containsKey("kw")) {
                this.bd = bundle.getString("kw", "");
                this.be = bundle.getLong("kwId", 0);
                this.bf = bundle.getInt("trackerType", 0);
            }
        }
        if (bundle.containsKey("task_type")) {
            this.n = bundle.getString("task_type");
        }
        if (bundle.containsKey("track_bundle")) {
            Bundle trackBundle = bundle.getBundle("track_bundle");
            if (trackBundle != null) {
                this.bq = new TrackAdInfo();
                this.bq.e = trackBundle.getLong("platform_id", 0);
                this.bq.a = trackBundle.getLong("position_id", 0);
                this.bq.c = trackBundle.getLong("plan_id", 0);
                this.bq.b = trackBundle.getLong("unit_id", 0);
                this.bq.d = trackBundle.getString("request_id", "");
                this.bq.f = trackBundle.getLong("platform_id", 0);
                this.bq.h = trackBundle.getString(RequestManager.MZOS, "");
                this.bq.i = trackBundle.getLong("content_id", 0);
                this.bq.j = trackBundle.getLong("content_type", 0);
                this.bq.k = trackBundle.getLong("display_type", 0);
                this.bq.g = trackBundle.getString("version", "");
            }
        }
        this.I = bundle.getString("app_name", "");
        this.J = bundle.getBoolean("goto_search_page", false);
        if (bundle.containsKey("uxip_page_source_info")) {
            this.bh = (UxipPageSourceInfo) bundle.getParcelable("uxip_page_source_info");
        } else if (bundle.containsKey("source_page")) {
            this.bg = bundle.getString("source_page", "");
        }
        this.bi = bundle.getString("search_id", "");
        com.meizu.cloud.app.downlad.d.a(this.i).a((m) this);
        ((BaseCommonActivity) this.i).a((com.meizu.cloud.base.app.BaseCommonActivity.b) this);
    }

    protected String a() {
        if (x.a(this.i)) {
            return RequestConstants.APP_CENTER_HOST;
        }
        if (x.b(this.i)) {
            return RequestConstants.GAME_CENTER_HOST;
        }
        return "";
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(com.meizu.cloud.b.a.g.base_app_details_layout, container, false);
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        this.r = (PullToZoomGroup) rootView.findViewById(com.meizu.cloud.b.a.f.app_details_pulltozoom_groupview);
        this.u = this.r.getListView();
        this.s = this.r.getGalleryLayout();
        this.t = this.r.getGalleryFlow();
        this.r.setPullGroupListener(new com.meizu.cloud.detail.anim.b(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(float progress) {
                if (this.a.getActivity() != null) {
                    if (((double) progress) >= 0.5d) {
                        this.a.s.setBackgroundDrawable(new BitmapDrawable(this.a.getResources(), this.a.bl));
                        this.a.s.getBackground().setAlpha((int) (progress * 255.0f));
                    } else {
                        this.a.s.setBackgroundDrawable(this.a.getResources().getDrawable(17170444));
                        this.a.s.getBackground().setAlpha((int) ((1.0f - progress) * 255.0f));
                    }
                    this.a.x.getBackground().setAlpha((int) (progress * 255.0f));
                    if (((double) progress) <= 0.8d && progress >= 0.0f) {
                        this.a.x.setTranslationY(((0.8f - progress) / 0.8f) * ((float) this.a.x.getHeight()));
                    } else if (((double) progress) >= 0.8d && this.a.x.getTranslationY() != 0.0f) {
                        this.a.x.setTranslationY(0.0f);
                    } else if (progress <= 0.0f && this.a.x.getTranslationY() != ((float) this.a.x.getHeight())) {
                        this.a.x.setTranslationY((float) this.a.x.getHeight());
                    }
                    if (((double) progress) == 1.0d) {
                        this.a.getActionBar().e(50);
                        com.meizu.cloud.app.utils.d.a(this.a.getActivity(), false);
                    } else if (this.a.getActionBar().g()) {
                        this.a.getActionBar().f(50);
                        com.meizu.cloud.app.utils.d.a(this.a.getActivity(), true);
                    }
                    Log.i("bottomViewAlphaListener", "" + progress);
                }
            }
        });
        this.t.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getTag().toString().equals("pullgroup_tag")) {
                    this.a.r.a();
                }
            }
        });
        this.u.setOnScrollListener(new OnScrollListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.a.getActivity() != null) {
                    if (this.a.u.getChildCount() > 0 && this.a.u.getChildAt(0).getTop() >= 0) {
                        this.a.o = "";
                        this.a.getActionBar().a((CharSequence) "");
                    } else if (this.a.u.getChildCount() > 0 && this.a.u.getChildAt(0).getTop() < 0 && this.a.k != null) {
                        this.a.o = this.a.k.name;
                        this.a.getActionBar().a(this.a.k.name);
                    }
                    if (this.a.u.getChildAt(0) != null) {
                        Log.i("mPullToZoomListView", "" + this.a.u.getChildAt(0).getTop());
                    }
                }
            }
        });
        this.w = (RelativeLayout) rootView.findViewById(com.meizu.cloud.b.a.f.app_details_topview);
        com.meizu.cloud.app.utils.d.b(this.i, this.w);
        this.a = (ViewFlipper) this.w.findViewById(com.meizu.cloud.b.a.f.app_details_topnotif);
        this.x = (RelativeLayout) this.w.findViewById(com.meizu.cloud.b.a.f.install_view_layout);
        this.y = (AnimDownloadProgressButton) this.w.findViewById(com.meizu.cloud.b.a.f.round_corner_button);
        this.y.setOnClickListener(this);
        this.z = (FrameLayout) this.w.findViewById(com.meizu.cloud.b.a.f.try_install_layout);
        this.A = (TextView) this.w.findViewById(com.meizu.cloud.b.a.f.try_install_btn);
        this.A.setOnClickListener(this);
        this.B = (TextView) this.w.findViewById(com.meizu.cloud.b.a.f.price_install_btn);
        this.B.setOnClickListener(this);
        View headerView = LayoutInflater.from(this.i).inflate(com.meizu.cloud.b.a.g.base_app_details_listview_header, null);
        a(headerView);
        b(headerView);
        c(headerView);
        d(headerView);
        this.u.addHeaderView(headerView);
        this.ax = (LinearLayout) rootView.findViewById(com.meizu.cloud.b.a.f.app_details_loadingmore_view);
        this.ax.setVisibility(8);
    }

    protected void onDataConnected() {
        i();
    }

    private void i() {
        if (getActivity() != null && com.meizu.cloud.app.utils.m.b(getActivity())) {
            hideEmptyView();
            if (this.j > 0) {
                k();
            } else if (getArguments() != null) {
                a(getArguments());
            }
        }
    }

    private void a(View rootView) {
        this.C = (LinearLayout) rootView.findViewById(com.meizu.cloud.b.a.f.app_details_maininfo);
        this.C.setVisibility(8);
        this.D = (ImageView) rootView.findViewById(com.meizu.cloud.b.a.f.app_image);
        this.E = (TextView) rootView.findViewById(com.meizu.cloud.b.a.f.app_name);
        this.G = (BaseStarRateWidget) this.C.findViewById(com.meizu.cloud.b.a.f.app_star_view);
        this.H = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.comment_count);
        this.F = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_sizeinstall_count);
        this.b = (ImageView) this.C.findViewById(com.meizu.cloud.b.a.f.app_details_floating_view);
        this.b.setOnClickListener(this);
        this.K = (RelativeLayout) this.C.findViewById(com.meizu.cloud.b.a.f.main_info_app_recomdes_view);
        this.L = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_recom_descriptions_text);
        this.M = (FoldableTextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_info_abridge_desc);
        this.M.setFolding(3, new com.meizu.common.widget.FoldableTextView.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public boolean a(FoldableTextView ftv, boolean folding) {
                if (this.a.N != null) {
                    if (folding) {
                        this.a.N.setVisibility(0);
                    } else {
                        this.a.N.setVisibility(8);
                    }
                }
                return true;
            }
        });
        this.N = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.maininfo_more_btn);
        this.N.setOnClickListener(this);
        this.O = (HeightAnimationLayout) this.C.findViewById(com.meizu.cloud.b.a.f.app_info_detail_desc);
        this.O.setOnClickListener(this);
        this.P = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_info_install_desc_title);
        this.Q = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_info_install_desc);
        this.R = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_info_update_desc_title);
        this.S = (TextView) this.C.findViewById(com.meizu.cloud.b.a.f.app_info_update_desc);
        this.T = (LinearLayout) this.C.findViewById(com.meizu.cloud.b.a.f.surrounding_entrance_view);
        TextView surEntanceFir = (TextView) this.T.findViewById(com.meizu.cloud.b.a.f.surround_entrance_first);
        TextView surEntanceSec = (TextView) this.T.findViewById(com.meizu.cloud.b.a.f.surround_entrance_second);
        TextView surEntanceThi = (TextView) this.T.findViewById(com.meizu.cloud.b.a.f.surround_entrance_third);
        TextView surEntanceFou = (TextView) this.T.findViewById(com.meizu.cloud.b.a.f.surround_entrance_fourth);
        this.U = new ArrayList();
        this.U.add(surEntanceFir);
        this.U.add(surEntanceSec);
        this.U.add(surEntanceThi);
        this.U.add(surEntanceFou);
    }

    private void b(View rootView) {
        this.V = (LinearLayout) rootView.findViewById(com.meizu.cloud.b.a.f.app_details_commentsview);
        this.V.setVisibility(8);
        this.ae = (LinearLayout) rootView.findViewById(com.meizu.cloud.b.a.f.comment_block_lineralayout);
        this.ae.setVisibility(8);
        this.W = (LinearLayout) this.V.findViewById(com.meizu.cloud.b.a.f.comments_add_entrance);
        this.X = this.V.findViewById(com.meizu.cloud.b.a.f.comments_add_entrance_dividerview);
        this.Z = (TextView) this.V.findViewById(com.meizu.cloud.b.a.f.omments_ratingbar_tv);
        this.Y = (RatingBar) this.V.findViewById(com.meizu.cloud.b.a.f.comments_ratingbar);
        this.Y.setOnRatingBarChangeListener(new OnRatingBarChangeListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.i("onRatingChanged", "rating >" + rating + ";fromUser >" + fromUser);
                if (!fromUser) {
                    return;
                }
                if (this.a.t() != 2 || rating <= 0.0f) {
                    this.a.aJ = rating;
                    if (rating > 0.0f) {
                        if (this.a.bs == null) {
                            this.a.bs = v.a(this.a.i);
                        }
                        this.a.bs.show();
                        this.a.d(false);
                        return;
                    }
                    return;
                }
                com.meizu.cloud.app.utils.a.a(this.a.i, this.a.getString(com.meizu.cloud.b.a.i.add_comment_installing_toast));
                this.a.Y.setRating(0.0f);
            }
        });
        this.aa = (LinearLayoutForListView) this.V.findViewById(com.meizu.cloud.b.a.f.comments_listview);
        this.ab = (RelativeLayout) this.V.findViewById(com.meizu.cloud.b.a.f.comments_view_more);
        this.ad = (TextView) this.V.findViewById(com.meizu.cloud.b.a.f.comments_viewmore_tx);
        this.ad.setOnClickListener(this);
    }

    private void c(View rootView) {
        this.af = (LinearLayout) rootView.findViewById(com.meizu.cloud.b.a.f.recommend_view);
        this.af.setVisibility(8);
        this.c = (TextView) rootView.findViewById(com.meizu.cloud.b.a.f.more);
        this.c.setOnClickListener(this);
        this.ag = (HorizontialListView) rootView.findViewById(com.meizu.cloud.b.a.f.recommend_horizontial_listview);
        this.ag.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.a.getActivity() != null) {
                    AppStructItem appStructItem = (AppStructItem) this.a.ag.getAdapter().getItem(position);
                    appStructItem.install_page = "recom_list";
                    appStructItem.click_pos = position + 1;
                    appStructItem.page_info = new int[]{0, 5, 0};
                    this.a.a(view, appStructItem, position);
                    com.meizu.cloud.statistics.b.a().a("detail_recomitem", this.a.mPageName, c.a(appStructItem));
                    if (this.a.p > 0 && !TextUtils.isEmpty(this.a.q)) {
                        com.meizu.cloud.statistics.b.a().a("recom_click", "", this.a.a(String.valueOf(appStructItem.id)));
                    }
                }
            }
        });
    }

    private void d(View rootView) {
        this.ah = (LinearLayout) rootView.findViewById(com.meizu.cloud.b.a.f.app_details_relatedview);
        this.ah.setVisibility(8);
        this.d = (TextView) rootView.findViewById(com.meizu.cloud.b.a.f.dev_more);
        this.d.setOnClickListener(this);
        this.ai = (LinearLayout) this.ah.findViewById(com.meizu.cloud.b.a.f.developer_view);
        this.aj = (HorizontialListView) this.ah.findViewById(com.meizu.cloud.b.a.f.related_horizontial_listview);
        this.aj.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.a.getActivity() != null) {
                    AppStructItem appStructItem = (AppStructItem) this.a.aj.getAdapter().getItem(position);
                    appStructItem.install_page = "other_developers_list";
                    appStructItem.click_pos = position + 1;
                    appStructItem.page_info = new int[]{0, 6, 0};
                    this.a.a(view, appStructItem, position);
                    com.meizu.cloud.statistics.b.a().a("detail_developeritem", this.a.mPageName, c.a(appStructItem));
                }
            }
        });
        this.ak = (LinearLayout) this.ah.findViewById(com.meizu.cloud.b.a.f.app_details_lables_title);
        this.al = (LinearLayout) this.ah.findViewById(com.meizu.cloud.b.a.f.app_detials_labels_view);
        this.e = (TextView) this.al.findViewById(com.meizu.cloud.b.a.f.app_info_label1);
        this.f = (TextView) this.al.findViewById(com.meizu.cloud.b.a.f.app_info_label2);
        this.g = (TextView) this.al.findViewById(com.meizu.cloud.b.a.f.app_info_label3);
        this.am = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_update_time);
        this.an = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_update_time_txt);
        this.ao = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_current_version);
        this.ap = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_current_version_txt);
        this.h = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_view_historyver);
        this.h.setOnClickListener(this);
        this.aq = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_developer_name);
        this.ar = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_developer_name_txt);
        this.as = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_permission_info);
        this.at = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_permission_info_txt);
        this.au = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_view_all_permissions);
        this.au.setOnClickListener(this);
        this.av = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_source);
        this.aw = (TextView) this.ah.findViewById(com.meizu.cloud.b.a.f.app_source_txt);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (com.meizu.cloud.app.utils.m.b(this.i)) {
            if (this.j <= 0) {
                a(bundle);
            }
            k();
        } else if (!this.ay && !this.az) {
            showEmptyView(getString(com.meizu.cloud.b.a.i.network_error), null, new OnClickListener(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.i();
                }
            });
        }
    }

    private void a(Bundle bundle) {
        if (bundle.containsKey("identify")) {
            e(bundle.getString("identify"));
        } else if (bundle.containsKey("package_name")) {
            e(bundle.getString("package_name"));
        } else if (bundle.containsKey("flash_fetch_param")) {
            j();
        }
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem shareItem = menu.findItem(com.meizu.cloud.b.a.f.share_menu);
        if (shareItem != null) {
            shareItem.setVisible(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != com.meizu.cloud.b.a.f.share_menu) {
            return super.onOptionsItemSelected(item);
        }
        if (this.bv == null) {
            this.bv = new com.meizu.cloud.app.share.b(getActivity(), this, this.k, Boolean.valueOf(false));
        }
        f();
        c();
        return true;
    }

    public void b() {
        com.meizu.cloud.statistics.b.a().a("detail_search", "detail", d(null));
        if (this.bm != null) {
            this.bm.setAlpha(this.bn);
        }
    }

    private Map<String, String> d(String shareToPkg) {
        if (this.k == null) {
            return null;
        }
        Map<String, String> dataMap = new HashMap();
        dataMap.put("appid", String.valueOf(this.k.id));
        dataMap.put("apkname", this.k.package_name);
        dataMap.put("name", this.k.name);
        if (TextUtils.isEmpty(shareToPkg)) {
            return dataMap;
        }
        dataMap.put("share_to_pkgName", shareToPkg);
        return dataMap;
    }

    protected void c() {
        if (this.k != null) {
            Intent intent = new Intent();
            String weChatShareUrl = this.k.h5_detail_url;
            if (weChatShareUrl == null) {
                weChatShareUrl = "";
            }
            intent.putExtra("wechat_shareurl", weChatShareUrl);
            intent.setClass(getActivity(), ResolverActivity.class);
            if (!this.bt) {
                startActivityForResult(intent, 10);
                this.bt = true;
                com.meizu.cloud.statistics.b.a().a("detail_share", this.mPageName, d(null));
            }
        }
    }

    private void e(String param) {
        if (!this.az) {
            showProgress();
            this.az = true;
            TypeReference tr = new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }
            };
            List paramPairs = new ArrayList();
            paramPairs.add(new com.meizu.volley.b.a("package_name", param));
            this.aX = new FastJsonRequest(tr, RequestConstants.getRuntimeDomainUrl(this.i, RequestConstants.GET_EXTERNAL_APP_URL), 0, paramPairs, new com.android.volley.n.b<ResultModel<Object>>(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<Object> response) {
                    if (!this.a.mRunning) {
                        return;
                    }
                    if (response == null || response.getCode() != 200 || response.getValue() == null) {
                        if (response == null || response.getCode() != RequestConstants.CODE_APP_NOT_FOUND) {
                            this.a.a(false, false);
                        } else if (!this.a.J || TextUtils.isEmpty(this.a.I)) {
                            this.a.a(false, false, this.a.getString(com.meizu.cloud.b.a.i.app_not_found));
                        } else {
                            this.a.b(this.a.I);
                        }
                    } else if (((JSONObject) response.getValue()).containsKey("redirect_url")) {
                        String detailUrl = ((JSONObject) response.getValue()).getString("redirect_url");
                        if (TextUtils.isEmpty(detailUrl)) {
                            this.a.a(false, false);
                            return;
                        }
                        this.a.j = com.meizu.cloud.app.utils.g.d(detailUrl);
                        this.a.aH = this.a.a() + detailUrl;
                        this.a.az = false;
                        this.a.k();
                    } else {
                        this.a.a(false, false);
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                    this.a.a(false, true);
                }
            });
            this.aX.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.i));
            com.meizu.volley.b.a(this.i).a().a(this.aX);
        }
    }

    private void j() {
        TypeReference tr = new TypeReference<ResultModel<Object>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("mime", "application/x-shockwave-flash"));
        l jsonRequest = new FastJsonRequest(tr, RequestConstants.GET_DETAIL_URL, 0, paramPairs, new com.android.volley.n.b<ResultModel<Object>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(ResultModel<Object> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null) {
                    this.a.a(false, false);
                } else if (((JSONObject) response.getValue()).containsKey("upgrade_url")) {
                    String detailUrl = ((JSONObject) response.getValue()).getString("upgrade_url");
                    if (TextUtils.isEmpty(detailUrl)) {
                        this.a.a(false, false);
                        return;
                    }
                    this.a.j = Long.valueOf(detailUrl.substring(detailUrl.lastIndexOf("/") + 1)).longValue();
                    this.a.aH = this.a.a() + detailUrl;
                    this.a.k();
                } else {
                    this.a.a(false, false);
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(s error) {
                this.a.a(false, true);
            }
        });
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        com.meizu.volley.b.a(getActivity()).a().a(jsonRequest);
    }

    public boolean d() {
        if (this.r == null || this.r.getGalleryLayout().getHeight() != this.r.getSTATUS3()) {
            return false;
        }
        this.r.a();
        return true;
    }

    public void onResume() {
        super.onResume();
        v();
    }

    protected void setupActionBar() {
        super.setupActionBar();
        a(this.bl);
        getActionBar().a(this.o);
    }

    public void onPause() {
        super.onPause();
    }

    private void a(Bitmap bitmap) {
        if (getActivity() != null && this.bm != null) {
            if (bitmap != null) {
                this.bm.setAlpha(0);
                getActionBar().a(this.bm);
                return;
            }
            this.bm.setAlpha(this.bn);
        }
    }

    private void k() {
        if (!this.ay && this.j > 0 && !this.az) {
            o();
        }
    }

    private void l() {
        if (!TextUtils.isEmpty(com.meizu.cloud.a.c.c(getActivity()))) {
            d(true);
        }
        if (!(this.aA || this.j <= 0 || this.aB)) {
            b(false);
        }
        if (!(this.aC || this.j <= 0 || this.aD)) {
            p();
        }
        if (!this.aE && this.j > 0 && !this.aF) {
            q();
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.v != null) {
            this.v.notifyDataSetChanged();
        }
        this.bw = false;
        if (getActivity() != null && (getActivity() instanceof BaseCommonActivity)) {
            ((BaseCommonActivity) getActivity()).a((com.meizu.cloud.base.app.BaseCommonActivity.a) this);
        }
    }

    public void onDestroyView() {
        m();
        for (int i = 0; i < this.t.getChildCount(); i++) {
            ImageView imageView = (ImageView) ((ViewGroup) this.t.getChildAt(i)).findViewById(16908294);
            if (imageView != null) {
                imageView.setImageDrawable(null);
                Picasso.with(this.i.getApplicationContext()).cancelRequest(imageView);
            }
        }
        super.onDestroyView();
        if (this.bm != null) {
            this.bm.setAlpha(this.bn);
        }
        com.meizu.cloud.app.utils.d.a(getActivity(), false);
        this.bw = true;
        getActivity().getWindow().setBackgroundDrawable(this.bo);
        if (getActivity() != null && (getActivity() instanceof BaseCommonActivity)) {
            ((BaseCommonActivity) getActivity()).a(null);
        }
    }

    private void m() {
        if (this.v != null) {
            this.v.a();
            this.v.notifyDataSetChanged();
        }
    }

    public void onDestroy() {
        if (this.i != null) {
            com.meizu.cloud.app.downlad.d.a(this.i).b((m) this);
            ((BaseCommonActivity) this.i).b(this);
        }
        if (this.bs != null) {
            this.bs.dismiss();
        }
        if (this.r != null) {
            this.r.setPullGroupListener(null);
        }
        if (this.bp != null) {
            this.bp.a();
        }
        this.bm = null;
        this.bl = null;
        n();
        super.onDestroy();
        if (this.y != null) {
            this.y.a();
        }
    }

    private void n() {
        if (this.aY != null) {
            this.aY.cancel();
        }
        if (this.aS != null) {
            this.aS.cancel();
        }
        if (this.aT != null) {
            this.aT.cancel();
        }
        if (this.aU != null) {
            this.aU.cancel();
        }
        if (this.aV != null) {
            this.aV.cancel();
        }
        if (this.aW != null) {
            this.aW.cancel();
        }
        if (this.aX != null) {
            this.aX.cancel();
        }
    }

    private void o() {
        showProgress();
        this.az = true;
        this.aS = new FastJsonRequest(new TypeReference<ResultModel<AppStructDetailsItem>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }
        }, this.aH, new com.android.volley.n.b<ResultModel<AppStructDetailsItem>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(ResultModel<AppStructDetailsItem> response) {
                if (!this.a.mRunning) {
                    return;
                }
                if (response != null && 200 == response.getCode() && response.getValue() != null) {
                    this.a.k = (AppStructDetailsItem) response.getValue();
                    if (this.a.k != null) {
                        if (this.a.k.package_name == null) {
                            Log.w("AsyncExecuteFragment", "package is null: " + this.a.k.name);
                            this.a.k.package_name = "";
                        }
                        this.a.k.url = this.a.aH.substring(this.a.a().length(), this.a.aH.length());
                        this.a.k.install_page = this.a.mPageName;
                        if (this.a.ba > 0 && !TextUtils.isEmpty(this.a.bb)) {
                            this.a.k.position_id = this.a.aZ;
                            this.a.k.unit_id = this.a.ba;
                            this.a.k.request_id = this.a.bb;
                            this.a.k.version = this.a.bc;
                            this.a.k.kw = this.a.bd;
                            this.a.k.kw_id = this.a.be;
                            this.a.k.tracker_type = this.a.bf;
                        }
                        if ("pay".equals(this.a.n)) {
                            this.a.a(true);
                        } else if (this.a.k.price > 0.0d) {
                            this.a.a(false);
                        }
                        if (this.a.bq != null) {
                            this.a.k.mTrackAdInfo = this.a.bq;
                        }
                        if (this.a.bh != null) {
                            this.a.k.source_page = this.a.bh.f;
                        } else if (!TextUtils.isEmpty(this.a.bg)) {
                            this.a.k.source_page = this.a.bg;
                        }
                        if (!TextUtils.isEmpty(this.a.bi)) {
                            this.a.k.search_id = this.a.bi;
                        }
                        this.a.a(true, false);
                        return;
                    }
                    this.a.a(false, false);
                } else if (response == null || response.getCode() != RequestConstants.CODE_APP_NOT_FOUND) {
                    this.a.a(false, false);
                } else {
                    this.a.a(false, false, this.a.getString(com.meizu.cloud.b.a.i.app_not_found));
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(s error) {
                if (this.a.mRunning) {
                    this.a.a(false, true);
                }
            }
        });
        this.aS.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.i));
        com.meizu.volley.b.a(this.i).a().a(this.aS);
    }

    private void a(final boolean isFromActivity) {
        TypeReference<ResultModel<JSONObject>> typeReference = new TypeReference<ResultModel<JSONObject>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }
        };
        List<com.meizu.volley.b.a> paramPairs = new ArrayList();
        this.aY = new com.meizu.volley.a.b(getActivity(), typeReference, 0, RequestConstants.getRuntimeDomainUrl(getActivity(), "/public/detail/" + this.k.id + RequestConstants.DETAILS_CHECK_FOOTER), paramPairs, new com.android.volley.n.b<ResultModel<JSONObject>>(this) {
            final /* synthetic */ d b;

            public void a(ResultModel<JSONObject> response) {
                if (response == null) {
                    Log.w("AsyncExecuteFragment", "/public/detail/ " + this.b.k.id + " return null");
                } else if (response.getCode() == 200 || response.getValue() != null) {
                    JSONObject jsonObject = (JSONObject) response.getValue();
                    this.b.k.paid = jsonObject.getBoolean("paid").booleanValue();
                    if (this.b.k.paid) {
                        if (isFromActivity) {
                            com.meizu.cloud.compaign.a.a(this.b.getActivity()).a(com.meizu.cloud.compaign.a.a(this.b.getActivity()).a());
                        }
                        this.b.v();
                    }
                } else {
                    Log.w("AsyncExecuteFragment", "/public/detail/ return " + response.getMessage());
                }
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(s error) {
                Log.w("AsyncExecuteFragment", "/public/detail/ " + this.a.k.id + " return error");
            }
        });
        this.aY.setParamProvider(com.meizu.cloud.app.utils.param.a.a(getActivity()));
        com.meizu.volley.b.a(getActivity()).a().a(this.aY);
    }

    private void b(final boolean isRefresh) {
        this.aB = true;
        TypeReference typeRef = new TypeReference<ResultModel<DataReultModel<AppCommentItem>>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }
        };
        List paramList = new ArrayList();
        paramList.add(new com.meizu.volley.b.a("start", String.valueOf(0)));
        paramList.add(new com.meizu.volley.b.a("max", String.valueOf(10)));
        paramList.add(new com.meizu.volley.b.a(x.b(this.i) ? "game_id" : "app_id", String.valueOf(this.j)));
        this.aT = new FastJsonRequest(typeRef, RequestConstants.getRuntimeDomainUrl(this.i, RequestConstants.EVALUATE), 0, paramList, new com.android.volley.n.b<ResultModel<DataReultModel<AppCommentItem>>>(this) {
            final /* synthetic */ d b;

            public void a(ResultModel<DataReultModel<AppCommentItem>> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null) {
                    this.b.a("", isRefresh);
                    return;
                }
                this.b.aI = ((DataReultModel) response.getValue()).data;
                this.b.aK = ((DataReultModel) response.getValue()).more;
                this.b.a(null, isRefresh);
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ d b;

            public void a(s error) {
                this.b.a("", isRefresh);
            }
        });
        this.aT.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.i));
        com.meizu.volley.b.a(this.i).a().a(this.aT);
    }

    private void p() {
        this.aD = true;
        this.aU = new FastJsonRequest(new TypeReference<ResultModel<DataReultModel<AppStructItem>>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }
        }, RequestConstants.getRuntimeDomainUrl(this.i, String.format(RequestConstants.RELATED_RECOMMEND, new Object[]{Long.valueOf(this.j)})), new com.android.volley.n.b<ResultModel<DataReultModel<AppStructItem>>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(ResultModel<DataReultModel<AppStructItem>> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null || ((DataReultModel) response.getValue()).data == null || ((DataReultModel) response.getValue()).data.size() <= 0) {
                    this.a.f("");
                    return;
                }
                this.a.aO = ((DataReultModel) response.getValue()).data;
                this.a.aP = ((DataReultModel) response.getValue()).more;
                this.a.p = ((DataReultModel) response.getValue()).recom_type;
                this.a.q = ((DataReultModel) response.getValue()).recom_ver;
                String recomId = "";
                for (int i = 0; i < this.a.aO.size(); i++) {
                    if (i == 0) {
                        recomId = String.valueOf(((AppStructItem) this.a.aO.get(i)).id);
                    } else {
                        recomId = recomId + "," + ((AppStructItem) this.a.aO.get(i)).id;
                    }
                }
                com.meizu.cloud.statistics.b.a().a("recom_pv", "", this.a.a(recomId));
                this.a.f(null);
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(s error) {
                this.a.f("");
            }
        });
        this.aU.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.i));
        com.meizu.volley.b.a(this.i).a().a(this.aU);
    }

    protected Map<String, String> a(String recomId) {
        Map<String, String> map = new HashMap();
        map.put("recom_type", String.valueOf(this.p));
        map.put("source_id", String.valueOf(this.k.id));
        map.put("recom_id", recomId);
        map.put("recom_ver", this.q);
        map.put("proto_ver", String.valueOf(1));
        return map;
    }

    private void q() {
        this.aF = true;
        String developerUrl = "";
        if (x.a(this.i)) {
            developerUrl = RequestConstants.getRuntimeDomainUrl(this.i, String.format(RequestConstants.APP_DEVELOPER_OTHERS, new Object[]{Integer.valueOf(this.k.developer_id)}));
        } else if (x.b(this.i)) {
            developerUrl = RequestConstants.getRuntimeDomainUrl(this.i, String.format(RequestConstants.GAME_DEVELOPER_OTHERS, new Object[]{Integer.valueOf(this.k.developer_id)}));
        }
        List params = new ArrayList();
        params.add(new com.meizu.volley.b.a("start", String.valueOf(0)));
        params.add(new com.meizu.volley.b.a("max", String.valueOf(4)));
        params.add(new com.meizu.volley.b.a("app_id", String.valueOf(this.j)));
        this.aV = new FastJsonRequest(new TypeReference<ResultModel<DataReultModel<AppStructItem>>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }
        }, developerUrl, 0, params, new com.android.volley.n.b<ResultModel<DataReultModel<AppStructItem>>>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(ResultModel<DataReultModel<AppStructItem>> response) {
                if (response == null || response.getCode() != 200 || response.getValue() == null || ((DataReultModel) response.getValue()).data == null || ((DataReultModel) response.getValue()).data.size() <= 0) {
                    this.a.g("");
                    return;
                }
                this.a.aQ = ((DataReultModel) response.getValue()).data;
                this.a.aR = ((DataReultModel) response.getValue()).more;
                this.a.g(null);
            }
        }, new com.android.volley.n.a(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(s error) {
                this.a.g("");
            }
        });
        this.aV.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.i));
        com.meizu.volley.b.a(this.i).a().a(this.aV);
    }

    private void a(boolean success, boolean retry) {
        a(success, retry, null);
    }

    private void a(boolean success, boolean retry, String text) {
        if (this.mRunning && this.i != null) {
            hideProgress();
            if (success) {
                r();
                l();
                this.ay = true;
            } else if (retry) {
                if (text == null) {
                    text = getString(com.meizu.cloud.b.a.i.network_error);
                }
                showEmptyView(text, null, new OnClickListener(this) {
                    final /* synthetic */ d a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View v) {
                        this.a.i();
                    }
                });
            } else {
                if (text == null) {
                    text = getString(com.meizu.cloud.b.a.i.server_error);
                }
                showEmptyView(text, getResources().getDrawable(com.meizu.cloud.b.a.e.ic_error_page, null), null);
            }
            this.az = false;
        }
    }

    private void a(String errInfo, boolean isRefresh) {
        if (this.mRunning && this.i != null) {
            if (errInfo == null) {
                c(isRefresh);
                this.aA = true;
            } else {
                this.V.setVisibility(8);
            }
            if (this.ay) {
                if (this.aC) {
                    this.af.setVisibility(0);
                }
                if (!(this.aD || this.aF)) {
                    this.ah.setVisibility(0);
                }
            }
            this.aB = false;
            if (!this.aB && !this.aD && !this.aF) {
                this.ax.setVisibility(8);
            }
        }
    }

    private void f(String errInfo) {
        if (this.mRunning && this.i != null) {
            if (errInfo == null) {
                C();
                this.aC = true;
            }
            if (!(!this.ay || this.aB || this.aF)) {
                this.ah.setVisibility(0);
            }
            this.aD = false;
            if (!this.aB && !this.aD && !this.aF) {
                this.ax.setVisibility(8);
            }
        }
    }

    private void g(String errInfo) {
        if (this.mRunning && this.i != null) {
            if (errInfo == null) {
                D();
                this.aE = true;
            }
            if (!(!this.ay || this.aB || this.aD)) {
                this.ah.setVisibility(0);
            }
            this.aF = false;
            if (!this.aB && !this.aD && !this.aF) {
                this.ax.setVisibility(8);
            }
        }
    }

    private Bitmap b(Bitmap bitmap) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        this.bj = new com.meizu.flyme.a.a().a(bitmap);
        Bitmap blurredBitmap = new com.meizu.cloud.app.jniutils.a(bitmap).a(70);
        bitmap.recycle();
        ColorFilter colorFilter = new PorterDuffColorFilter(this.bj & -1711276033, Mode.SRC_OVER);
        Canvas canvas = new Canvas(blurredBitmap);
        canvas.save();
        paint.setColorFilter(colorFilter);
        canvas.drawBitmap(blurredBitmap, 0.0f, 0.0f, paint);
        canvas.restore();
        return blurredBitmap;
    }

    private void a(TextView btn, int color) {
        ((GradientDrawable) btn.getBackground()).setColor(color);
    }

    private void a(final int position) {
        new Thread(this) {
            final /* synthetic */ d b;

            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(this.b.i.getApplicationContext()).load(((PreviewImage) this.b.k.images.get(position)).small).get();
                    Bitmap iconBitmap = Picasso.with(this.b.i.getApplicationContext()).load(this.b.k.icon).get();
                    if (iconBitmap != null) {
                        this.b.bk = new com.meizu.flyme.a.a().a(iconBitmap);
                        float[] hsv = new float[3];
                        Color.colorToHSV(this.b.bk, hsv);
                        if (hsv[1] < 0.45f) {
                            hsv[1] = 0.45f;
                        }
                        this.b.bk = Color.HSVToColor(hsv);
                        this.b.runOnUi(new Runnable(this) {
                            final /* synthetic */ AnonymousClass25 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                if (this.a.b.mRunning && this.a.b.getActivity() != null && this.a.b.bk != 0) {
                                    this.a.b.y.setRoundBtnColor(this.a.b.bk);
                                    this.a.b.a(this.a.b.A, this.a.b.bk);
                                    this.a.b.a(this.a.b.B, this.a.b.bk);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    this.b.c(bitmap);
                }
            }
        }.start();
    }

    private void c(Bitmap patBmp) {
        if (patBmp == null) {
            return;
        }
        if (this.bj == 0 || this.bl == null) {
            try {
                int pBmpH;
                int pBmpW;
                int rBmpH = patBmp.getHeight();
                int rBmpW = patBmp.getWidth();
                float rP = rBmpW == 0 ? 1.0f : ((float) rBmpH) / ((float) rBmpW);
                int screenW = com.meizu.cloud.app.utils.d.b();
                int listViewH = this.s.getHeight();
                float pP = screenW == 0 ? 1.0f : ((float) (0 + listViewH)) / ((float) screenW);
                if (pP >= rP) {
                    pBmpH = rBmpH;
                    pBmpW = pP == 0.0f ? rBmpW : (int) (((float) pBmpH) / pP);
                } else {
                    pBmpW = rBmpW;
                    pBmpH = pP == 0.0f ? rBmpH : (int) (((float) pBmpW) * pP);
                }
                Log.i("blurBitmap", "aBarH:" + 0 + "screenW:" + screenW + "listViewH:" + listViewH);
                Bitmap blurBitmap = b(Bitmap.createBitmap(patBmp, 0, 0, pBmpW, pBmpH));
                int aBY = (pBmpH * 0) / (0 + listViewH);
                this.bl = Bitmap.createBitmap(blurBitmap, 0, aBY, pBmpW, pBmpH - aBY);
                blurBitmap.recycle();
                runOnUi(new Runnable(this) {
                    final /* synthetic */ d a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        if (this.a.mRunning && this.a.getActivity() != null) {
                            if (this.a.bk != 0) {
                                this.a.y.setRoundBtnColor(this.a.bk);
                                this.a.a(this.a.A, this.a.bk);
                                this.a.a(this.a.B, this.a.bk);
                            } else {
                                this.a.y.setRoundBtnColor(this.a.bj);
                                this.a.a(this.a.A, this.a.bj);
                                this.a.a(this.a.B, this.a.bj);
                            }
                            this.a.s.setBackground(new BitmapDrawable(this.a.getResources(), this.a.bl));
                            this.a.e();
                            if (!this.a.bw) {
                                this.a.a(this.a.bl);
                            }
                            Log.i("AsyncExecuteFragment", "paletteColor : " + this.a.bj);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void e() {
        ValueAnimator mAnimation = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        mAnimation.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onAnimationUpdate(ValueAnimator aValueAnimator) {
                if (this.a.getActivity() != null) {
                    float alpha = ((Float) aValueAnimator.getAnimatedValue()).floatValue();
                    if (this.a.s != null) {
                        this.a.s.setAlpha(alpha);
                    }
                    if (this.a.bm != null && !this.a.bw) {
                        this.a.bm.setAlpha((int) (255.0f - (alpha * 255.0f)));
                    }
                }
            }
        });
        mAnimation.setDuration(500);
        mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimation.start();
    }

    private void r() {
        com.meizu.cloud.app.core.c compareResult = x.d(this.i).a(this.k.package_name, this.k.version_code);
        if (compareResult != null && compareResult == com.meizu.cloud.app.core.c.BUILD_IN) {
            this.br = true;
        }
        this.r.setVisibility(0);
        v();
        if (this.k.images != null && this.k.images.size() > 0) {
            this.v = new com.meizu.cloud.detail.anim.a(this.i, this.k.images, this.r);
            this.t.setAdapter(this.v);
            this.t.setSelection(1073741823 - (1073741823 % this.k.images.size()));
            a(0);
        }
        B();
        h.a(getActivity(), this.k.icon, this.D);
        this.E.setText(this.k.name);
        this.G.setValue((double) (((float) this.k.star) / 10.0f));
        if (this.k.evaluate_count > 0) {
            this.H.setText(String.format(getString(com.meizu.cloud.b.a.i.comment_count), new Object[]{Integer.valueOf(this.k.evaluate_count)}));
            this.H.setVisibility(0);
        } else {
            this.H.setVisibility(8);
        }
        this.F.setText(String.format(getString(com.meizu.cloud.b.a.i.app_size_and_install_counts), new Object[]{com.meizu.cloud.app.utils.g.a((double) this.k.size, getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit)), com.meizu.cloud.app.utils.g.a(this.i, this.k.download_count)}));
        s();
        z();
        this.ad.setText(String.format(getString(com.meizu.cloud.b.a.i.view_more_comments_text), new Object[]{Integer.valueOf(this.k.evaluate_count)}));
        A();
        if (this.k.version_time > 0) {
            this.am.setText(String.format("%s", new Object[]{com.meizu.common.util.a.a(this.i, this.k.version_time, 7)}));
        } else {
            this.am.setVisibility(8);
            this.an.setVisibility(8);
        }
        if (TextUtils.isEmpty(this.k.version_name)) {
            this.ao.setVisibility(8);
            this.ap.setVisibility(8);
        } else {
            this.ao.setText(String.format("%s", new Object[]{this.k.version_name}));
        }
        if (TextUtils.isEmpty(this.k.publisher)) {
            this.aq.setVisibility(8);
            this.ar.setVisibility(8);
        } else {
            this.aq.setText(String.format("%s", new Object[]{this.k.publisher}));
        }
        int permissionCount = 0;
        if (this.k.permissions != null) {
            permissionCount = this.k.permissions.size();
        }
        if (permissionCount <= 0) {
            this.as.setVisibility(8);
            this.at.setVisibility(8);
            this.au.setVisibility(8);
        }
        this.as.setText(String.format(this.i.getString(com.meizu.cloud.b.a.i.permissions_total_counts), new Object[]{Integer.valueOf(permissionCount)}));
        if (this.k.sources == null || this.k.sources.size() <= 0) {
            this.av.setVisibility(8);
            this.aw.setVisibility(8);
        } else {
            this.av.setText(String.format("%s", new Object[]{((Sources) this.k.sources.get(0)).name}));
        }
        if (!(this.k.notice == null || this.k.notice.image == null)) {
            String type = this.k.notice.type;
            if (PushConstants.INTENT_ACTIVITY_NAME.equals(type) || "special".equals(type) || ((x.a(this.i) && "game_gifts".equals(type)) || ((x.b(this.i) && "gift_details".equals(type)) || "rank".equals(type) || "h5".equals(type)))) {
                h.c(this.i, this.k.notice.image, this.b);
                this.b.setVisibility(0);
            }
        }
        this.C.setVisibility(0);
        this.ax.setVisibility(0);
        Log.e("AsyncExecuteFragment", "updateMainInfoView");
        b(compareResult);
        this.u.setAdapter(null);
    }

    private void s() {
        if (TextUtils.isEmpty(this.k.recommend_desc)) {
            String abridgeDes = this.k.description;
            if (!TextUtils.isEmpty(this.k.update_description)) {
                abridgeDes = abridgeDes + "\n\n\n" + getString(com.meizu.cloud.b.a.i.app_info_update_title) + "\n\n" + this.k.update_description + "\n";
            }
            this.M.setText(abridgeDes);
            this.M.setVisibility(0);
        } else {
            this.K.setVisibility(0);
            this.L.setText(this.k.recommend_desc);
            this.L.post(new Runnable(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void run() {
                    if (this.a.L.getLineCount() > 1) {
                        this.a.L.setGravity(3);
                    }
                }
            });
        }
        if (TextUtils.isEmpty(this.k.description) && TextUtils.isEmpty(this.k.update_description)) {
            this.N.setVisibility(8);
            return;
        }
        this.N.setVisibility(0);
        if (!TextUtils.isEmpty(this.k.description)) {
            if (x.b(this.i)) {
                this.P.setText(com.meizu.cloud.b.a.i.app_details_game_introduction_title);
            } else {
                this.P.setText(com.meizu.cloud.b.a.i.app_details_app_introduction_title);
            }
            this.Q.setText(this.k.description);
            this.P.setVisibility(0);
            this.Q.setVisibility(0);
        }
        if (!TextUtils.isEmpty(this.k.update_description)) {
            this.R.setText(com.meizu.cloud.b.a.i.app_info_update_title);
            this.S.setText(this.k.update_description);
            this.R.setVisibility(0);
            this.S.setVisibility(0);
        }
    }

    private int t() {
        if (getActivity() == null || this.k == null) {
            return 0;
        }
        com.meizu.cloud.app.downlad.e downloadWrapper = com.meizu.cloud.app.downlad.d.a(this.i).b(this.k.package_name);
        if (downloadWrapper == null) {
            return u();
        }
        com.meizu.cloud.app.downlad.f.l anEnum = downloadWrapper.f();
        if (com.meizu.cloud.app.downlad.f.a(anEnum)) {
            return u();
        }
        if (anEnum == com.meizu.cloud.app.downlad.f.c.TASK_PAUSED) {
            return 3;
        }
        return 2;
    }

    private int u() {
        com.meizu.cloud.app.core.c compareResult = x.d(this.i).a(this.k.package_name, this.k.version_code);
        if (compareResult == com.meizu.cloud.app.core.c.NOT_INSTALL) {
            return 0;
        }
        if (compareResult == com.meizu.cloud.app.core.c.UPGRADE) {
            return 1;
        }
        if (compareResult == com.meizu.cloud.app.core.c.BUILD_IN) {
            return 4;
        }
        return 5;
    }

    private void v() {
        if (this.k != null) {
            com.meizu.cloud.app.downlad.e downloadWrapper = com.meizu.cloud.app.downlad.d.a(this.i).b(this.k.package_name);
            if (downloadWrapper == null) {
                w();
            } else {
                c(downloadWrapper);
            }
        }
    }

    private void c(com.meizu.cloud.app.downlad.e downloadWrapper) {
        this.x.setVisibility(0);
        com.meizu.cloud.app.downlad.f.l anEnum = downloadWrapper.f();
        if (downloadWrapper.F()) {
            w();
        } else if (anEnum instanceof com.meizu.cloud.app.downlad.f.n) {
            switch ((com.meizu.cloud.app.downlad.f.n) anEnum) {
                case FETCHING:
                    this.y.setState(1);
                    this.y.setProgressText(getString(com.meizu.cloud.b.a.i.roundbtn_update_downloaded), (float) downloadWrapper.o());
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case SUCCESS:
                    this.y.setProgressText(getString(com.meizu.cloud.b.a.i.roundbtn_update_downloaded), (float) downloadWrapper.o());
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case FAILURE:
                    w();
                    return;
                case CANCEL:
                    this.y.setState(1);
                    this.y.setProgressText(getString(com.meizu.cloud.b.a.i.details_continue), (float) downloadWrapper.o());
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                default:
                    return;
            }
        } else if (anEnum instanceof com.meizu.cloud.app.downlad.f.c) {
            switch ((com.meizu.cloud.app.downlad.f.c) anEnum) {
                case TASK_CREATED:
                case TASK_WAITING:
                case TASK_STARTED:
                case TASK_RESUME:
                    this.y.setState(1);
                    this.y.setProgressText(getString(com.meizu.cloud.b.a.i.roundbtn_update_downloaded), (float) downloadWrapper.o());
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case TASK_COMPLETED:
                    this.y.setState(2);
                    this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.details_installing));
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case TASK_REMOVED:
                    this.y.setState(1);
                    this.y.setProgressText(getString(com.meizu.cloud.b.a.i.details_continue), (float) downloadWrapper.o());
                    return;
                case TASK_PAUSED:
                    this.y.setState(1);
                    this.y.setProgressText(getString(com.meizu.cloud.b.a.i.details_continue), (float) downloadWrapper.o());
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case TASK_ERROR:
                    w();
                    return;
                default:
                    return;
            }
        } else if (anEnum instanceof com.meizu.cloud.app.downlad.f.h) {
            switch ((com.meizu.cloud.app.downlad.f.h) anEnum) {
                case PATCHING:
                    this.y.setState(2);
                    this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.details_installing));
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case PATCHED_FAILURE:
                    w();
                    return;
                case PATCHED_SUCCESS:
                    this.y.setState(2);
                    this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.details_installing));
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                default:
                    return;
            }
        } else if (anEnum instanceof com.meizu.cloud.app.downlad.f.f) {
            switch ((com.meizu.cloud.app.downlad.f.f) anEnum) {
                case INSTALL_START:
                    this.y.setState(2);
                    this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.details_installing));
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case INSTALL_SUCCESS:
                    if (this.k.price <= 0.0d || this.k.paid) {
                        this.y.setState(0);
                        this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.open));
                    } else {
                        this.y.setState(0);
                        this.y.setCurrentText(String.format("￥ %s", new Object[]{com.meizu.cloud.app.utils.g.a(this.k.price)}));
                    }
                    this.z.setVisibility(8);
                    this.y.setVisibility(0);
                    return;
                case INSTALL_FAILURE:
                    w();
                    return;
                case DELETE_START:
                    return;
                case DELETE_SUCCESS:
                case DELETE_FAILURE:
                    w();
                    return;
                default:
                    return;
            }
        } else if (anEnum instanceof j) {
            switch ((j) anEnum) {
                case PAYING:
                case CANCEL:
                    return;
                case SUCCESS:
                case FAILURE:
                    w();
                    return;
                default:
                    return;
            }
        }
    }

    private void w() {
        com.meizu.cloud.app.core.c compareResult = x.d(this.i).a(this.k.package_name, this.k.version_code);
        this.x.setVisibility(0);
        if (this.k.price <= 0.0d || this.k.paid) {
            this.z.setVisibility(8);
            this.y.setVisibility(0);
            a(compareResult);
        } else if (this.k.trial_days <= 0 || x.d(this.i).a(this.k.package_name)) {
            this.z.setVisibility(8);
            this.y.setVisibility(0);
            this.y.setState(0);
            this.y.setCurrentText(String.format("￥ %s", new Object[]{com.meizu.cloud.app.utils.g.a(this.k.price)}));
        } else if (this.bu.booleanValue()) {
            this.y.setVisibility(8);
            this.z.setVisibility(0);
            this.B.setText(String.format("￥ %s", new Object[]{com.meizu.cloud.app.utils.g.a(this.k.price)}));
            this.bu = Boolean.valueOf(false);
        } else {
            F();
        }
    }

    private void a(com.meizu.cloud.app.core.c compareResult) {
        if (compareResult == com.meizu.cloud.app.core.c.NOT_INSTALL) {
            this.y.setState(0);
            this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.install));
        } else if (compareResult == com.meizu.cloud.app.core.c.UPGRADE) {
            this.y.setState(0);
            this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.update));
        } else {
            this.y.setState(0);
            this.y.setCurrentText(getString(com.meizu.cloud.b.a.i.open));
        }
    }

    private void b(com.meizu.cloud.app.core.c compareResult) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String action = bundle.getString("result_app_action");
            if ("download".equals(action)) {
                if (compareResult == com.meizu.cloud.app.core.c.NOT_INSTALL) {
                    x();
                }
            } else if (!"force_download".equals(action)) {
            } else {
                if (compareResult == com.meizu.cloud.app.core.c.NOT_INSTALL) {
                    x();
                } else {
                    y();
                }
            }
        }
    }

    private void x() {
        com.meizu.cloud.app.downlad.e downloadWrapper = com.meizu.cloud.app.downlad.d.a(getActivity()).b(this.k.package_name);
        if (downloadWrapper == null || !(downloadWrapper.f() == com.meizu.cloud.app.downlad.f.c.TASK_STARTED || downloadWrapper.f() == com.meizu.cloud.app.downlad.f.c.TASK_WAITING || downloadWrapper.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_START)) {
            k performAction = new k(this.k);
            performAction.a(new k.a().b(true));
            this.l.a(performAction);
        }
    }

    private void y() {
        com.meizu.cloud.app.downlad.e downloadWrapper = com.meizu.cloud.app.downlad.d.a(getActivity()).b(this.k.package_name);
        if (downloadWrapper == null || !(downloadWrapper.f() == com.meizu.cloud.app.downlad.f.c.TASK_STARTED || downloadWrapper.f() == com.meizu.cloud.app.downlad.f.c.TASK_WAITING || downloadWrapper.f() == com.meizu.cloud.app.downlad.f.f.INSTALL_START)) {
            com.meizu.cloud.app.downlad.d.a(this.i).a(getActivity(), com.meizu.cloud.app.downlad.d.a(this.i).a(this.k, new com.meizu.cloud.app.downlad.g()));
        }
    }

    private void z() {
        if (x.b(this.i) && this.k != null && this.k.layouts != null && this.k.layouts.size() > 0) {
            int i = 0;
            while (i < this.k.layouts.size()) {
                if (i == 0) {
                    this.T.setVisibility(0);
                }
                if (i < this.U.size()) {
                    GameSurroundTrainItem item = (GameSurroundTrainItem) this.k.layouts.get(i);
                    TextView surEntance = (TextView) this.U.get(i);
                    surEntance.setText(item.name);
                    surEntance.setVisibility(0);
                    final BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
                    blockGotoPageInfo.a = item.type;
                    blockGotoPageInfo.c = this.k.name;
                    blockGotoPageInfo.e = this.k.id;
                    blockGotoPageInfo.b = item.url;
                    if ("game_gifts".equals(item.type)) {
                        blockGotoPageInfo.d = this.k;
                    }
                    surEntance.setOnClickListener(new OnClickListener(this) {
                        final /* synthetic */ d b;

                        public void onClick(View v) {
                            this.b.a(this.b.getActivity(), blockGotoPageInfo);
                        }
                    });
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    private void A() {
        if (this.k.app_tags != null && this.k.app_tags.size() > 0) {
            if (this.k.app_tags.get(0) != null) {
                this.e.setText(((AppTags) this.k.app_tags.get(0)).title);
                this.e.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ d a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View v) {
                        BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
                        blockGotoPageInfo.c = ((AppTags) this.a.k.app_tags.get(0)).title;
                        blockGotoPageInfo.b = ((AppTags) this.a.k.app_tags.get(0)).url;
                        blockGotoPageInfo.a = "rank";
                        this.a.a((FragmentActivity) this.a.i, blockGotoPageInfo);
                    }
                });
                this.e.setVisibility(0);
                this.ak.setVisibility(0);
                this.al.setVisibility(0);
            }
            if (this.k.app_tags.size() > 1 && this.k.app_tags.get(1) != null) {
                this.f.setText(((AppTags) this.k.app_tags.get(1)).title);
                this.f.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ d a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View v) {
                        BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
                        blockGotoPageInfo.c = ((AppTags) this.a.k.app_tags.get(1)).title;
                        blockGotoPageInfo.b = ((AppTags) this.a.k.app_tags.get(1)).url;
                        blockGotoPageInfo.a = "rank";
                        this.a.a((FragmentActivity) this.a.i, blockGotoPageInfo);
                    }
                });
                this.f.setVisibility(0);
            }
            if (this.k.app_tags.size() > 2 && this.k.app_tags.get(2) != null) {
                this.g.setText(((AppTags) this.k.app_tags.get(2)).title);
                this.g.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ d a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View v) {
                        BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
                        blockGotoPageInfo.c = ((AppTags) this.a.k.app_tags.get(2)).title;
                        blockGotoPageInfo.b = ((AppTags) this.a.k.app_tags.get(2)).url;
                        blockGotoPageInfo.a = "rank";
                        this.a.a((FragmentActivity) this.a.i, blockGotoPageInfo);
                    }
                });
                this.g.setVisibility(0);
            }
        }
    }

    private void B() {
        if (this.k.rollMsgs != null && this.k.rollMsgs.size() > 0) {
            this.a.setVisibility(0);
            for (int i = 0; i < this.k.rollMsgs.size(); i++) {
                if (this.k.rollMsgs.get(i) != null) {
                    View v = LayoutInflater.from(this.i).inflate(com.meizu.cloud.b.a.g.block_roll_message_layout, null);
                    v.setBackground(new ColorDrawable(Color.parseColor("#cdffffff")));
                    LinearLayout linearLayout = (LinearLayout) v.findViewById(com.meizu.cloud.b.a.f.rolling_message_view);
                    linearLayout.setGravity(17);
                    TextView titleTx = (TextView) v.findViewById(com.meizu.cloud.b.a.f.roll_message_title_name);
                    titleTx.setVisibility(8);
                    TextView desTx = (TextView) v.findViewById(com.meizu.cloud.b.a.f.roll_message_desc);
                    if (i == 0) {
                        desTx.requestFocus();
                    }
                    a(this.i, (RollMessageStructItem) this.k.rollMsgs.get(i), linearLayout, titleTx, desTx);
                    this.a.addView(v, i);
                }
            }
            this.a.setDisplayedChild(0);
            if (this.a.getChildCount() > 1) {
                this.a.setAutoStart(true);
                this.a.setScrollBarFadeDuration(5000);
                this.a.startFlipping();
                return;
            }
            this.a.setAutoStart(false);
        }
    }

    private void a(Context context, final RollMessageStructItem rollMessageStructItem, LinearLayout rollingMsgView, TextView titleTx, TextView desTx) {
        titleTx.setText(rollMessageStructItem.tag);
        desTx.setText(rollMessageStructItem.message);
        rollingMsgView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ d b;

            public void onClick(View v) {
                BlockGotoPageInfo blockGotoPageInfo = new BlockGotoPageInfo();
                blockGotoPageInfo.a = rollMessageStructItem.type;
                blockGotoPageInfo.b = rollMessageStructItem.url;
                blockGotoPageInfo.c = rollMessageStructItem.name;
                blockGotoPageInfo.e = this.b.k.id;
                this.b.a((FragmentActivity) this.b.i, blockGotoPageInfo);
            }
        });
    }

    private void c(boolean isRefresh) {
        if (this.aI == null || this.aI.size() <= 0) {
            this.ae.setVisibility(8);
            this.V.setVisibility(8);
            this.aa.setVisibility(8);
            this.ab.setVisibility(8);
        } else {
            this.ae.setVisibility(0);
            List<AppCommentItem> listData = new ArrayList();
            if (this.aI.size() <= 3) {
                listData = this.aI;
                this.ab.setVisibility(8);
            } else {
                for (int i = 0; i < 3; i++) {
                    listData.add(this.aI.get(i));
                }
                this.ab.setVisibility(0);
            }
            this.ac = new n(this.i, (long) this.k.id, this.k.version_code, listData, null);
            this.ac.a(0, false);
            this.aa.setAdapter(this.ac);
            this.aa.a();
            this.aa.setVisibility(0);
        }
        this.V.setVisibility(0);
        Log.e("AsyncExecuteFragment", "updateCommentView");
        this.u.setAdapter(null);
    }

    private void C() {
        this.aO = a(this.aO);
        if (this.aO == null || this.aO.size() <= 0) {
            this.af.setVisibility(8);
        } else {
            this.ag.setAdapter(new a(this, this.i, this.aO));
            this.af.setVisibility(0);
            if (this.aP) {
                this.c.setVisibility(0);
            }
        }
        Log.e("AsyncExecuteFragment", "updateRecomView");
        this.u.setAdapter(null);
    }

    private void D() {
        this.aQ = a(this.aQ);
        if (this.aQ == null || this.aQ.size() <= 0) {
            this.ai.setVisibility(8);
        } else {
            this.aj.setAdapter(new a(this, this.i, this.aQ));
            this.ai.setVisibility(0);
            if (this.aR) {
                this.d.setVisibility(0);
            }
        }
        Log.e("AsyncExecuteFragment", "updateDeveloView");
        this.u.setAdapter(null);
    }

    private List<AppStructItem> a(List<AppStructItem> listDataRelated) {
        List<AppStructItem> listData = new ArrayList();
        if (listDataRelated == null || listDataRelated.size() <= 0) {
            return listDataRelated;
        }
        for (AppStructItem appStructItem : listDataRelated) {
            if (appStructItem.id == this.k.id) {
                listDataRelated.remove(appStructItem);
                break;
            }
        }
        if (listDataRelated.size() <= 4) {
            return listDataRelated;
        }
        for (int i = 0; i < 4; i++) {
            listData.add(listDataRelated.get(i));
        }
        return listData;
    }

    public void onClick(View v) {
        if (v == this.A) {
            E();
        } else if (v == this.y) {
            this.k.page_info = this.mPageInfo;
            this.l.a(new k(this.k));
        } else if (v == this.B) {
            this.k.page_info = this.mPageInfo;
            this.l.a(new k(this.k));
        } else if (v == this.au) {
            fragment = new b();
            bundle = new Bundle();
            bundle.putParcelable("details_info", this.k);
            fragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment((FragmentActivity) this.i, fragment);
        } else if (v == this.N) {
            if (TextUtils.isEmpty(this.k.recommend_desc)) {
                this.M.a();
                this.N.setVisibility(8);
                return;
            }
            this.N.setVisibility(4);
            this.O.a();
        } else if (v == this.ad) {
            fragment = new c();
            bundle = new Bundle();
            bundle.putParcelable("details_info", this.k);
            bundle.putParcelableArrayList("comment_list_data", (ArrayList) this.aI);
            bundle.putBoolean("more", this.aK);
            bundle.putBoolean("build_in", this.br);
            fragment.setArguments(bundle);
            com.meizu.cloud.base.b.d.startFragment((FragmentActivity) this.i, fragment);
        } else if (v == this.O) {
            this.O.a(this.N);
        }
    }

    private void E() {
        LayoutParams originInstallBtnLP = this.A.getLayoutParams();
        float inStallBtnoriginWidth = getResources().getDimension(com.meizu.cloud.b.a.d.app_info_try_install_btn_width);
        float downloadWidth = getResources().getDimension(com.meizu.cloud.b.a.d.app_info_round_corner_button_width);
        LayoutParams tryInstallBtnLP = this.A.getLayoutParams();
        ValueAnimator toBigAnim = ValueAnimator.ofFloat(new float[]{inStallBtnoriginWidth, downloadWidth});
        final LayoutParams layoutParams = tryInstallBtnLP;
        toBigAnim.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ d b;

            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.width = (int) ((Float) animation.getAnimatedValue()).floatValue();
                this.b.A.setLayoutParams(layoutParams);
            }
        });
        toBigAnim.setDuration(333);
        toBigAnim.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        ScaleAnimation priceScale = new ScaleAnimation(1.0f, 0.8f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        priceScale.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
        final AnimationSet priceAnimationSet = new AnimationSet(true);
        priceAnimationSet.addAnimation(priceScale);
        priceAnimationSet.addAnimation(alphaAnimation);
        priceScale.setDuration(333);
        alphaAnimation.setDuration(250);
        this.B.setAnimation(priceAnimationSet);
        final float orignTx = this.A.getTranslationX();
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(this.A, "translationX", new float[]{0.0f, 60.0f});
        transAnim.setDuration(333);
        transAnim.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        final float originTryAlpha = this.A.getAlpha();
        PathInterpolator animAlphaInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        ObjectAnimator alphaAnim2 = ObjectAnimator.ofFloat(this.A, "alpha", new float[]{1.0f, 0.0f});
        alphaAnim2.setStartDelay(250);
        alphaAnim2.setDuration(83);
        alphaAnim2.setInterpolator(animAlphaInterpolator);
        final float originRoundAlpha = this.y.getAlpha();
        ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(this.y, "alpha", new float[]{0.0f, 1.0f});
        alphaAnim3.setDuration(333);
        alphaAnim3.setInterpolator(animAlphaInterpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{toBigAnim, transAnim, alphaAnim2, alphaAnim3});
        final LayoutParams layoutParams2 = tryInstallBtnLP;
        final float f = inStallBtnoriginWidth;
        animatorSet.addListener(new AnimatorListener(this) {
            final /* synthetic */ d g;

            public void onAnimationStart(Animator animation) {
                this.g.y.setVisibility(0);
                this.g.y.setState(1);
                this.g.y.setProgressText(this.g.getString(com.meizu.cloud.b.a.i.roundbtn_update_downloaded), 0.0f);
                this.g.y.setAlpha(0.0f);
                this.g.B.startAnimation(priceAnimationSet);
                this.g.B.setVisibility(4);
            }

            public void onAnimationEnd(Animator animation) {
                layoutParams2.width = (int) f;
                this.g.A.setLayoutParams(layoutParams2);
                this.g.A.setTranslationX(orignTx);
                this.g.k.page_info = this.g.mPageInfo;
                k performAction = new k(this.g.k);
                performAction.a(new k.a().a(true));
                this.g.l.a(performAction);
                this.g.B.setVisibility(4);
                this.g.A.setVisibility(4);
                this.g.A.setAlpha(originTryAlpha);
                this.g.y.setAlpha(originRoundAlpha);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }

    private void F() {
        float tryButtonWidth = getResources().getDimension(com.meizu.cloud.b.a.d.app_info_try_install_btn_width);
        float downloadBtnWidth = getResources().getDimension(com.meizu.cloud.b.a.d.app_info_round_corner_button_width);
        LayoutParams downLoadInstallBtnOrginLP = this.y.getLayoutParams();
        int downLoadInstallBtnOrginLPWidth = downLoadInstallBtnOrginLP.width;
        LayoutParams downLoadInstallBtnLP = this.y.getLayoutParams();
        ValueAnimator toSmallAnim = ValueAnimator.ofFloat(new float[]{downloadBtnWidth, tryButtonWidth});
        final LayoutParams layoutParams = downLoadInstallBtnLP;
        toSmallAnim.addUpdateListener(new AnimatorUpdateListener(this) {
            final /* synthetic */ d b;

            public void onAnimationUpdate(ValueAnimator animation) {
                layoutParams.width = (int) ((Float) animation.getAnimatedValue()).floatValue();
                this.b.y.setLayoutParams(layoutParams);
            }
        });
        toSmallAnim.setDuration(333);
        toSmallAnim.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        ScaleAnimation priceScale = new ScaleAnimation(0.8f, 1.0f, 1.0f, 1.0f, 1, 1.0f, 1, 0.5f);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        priceScale.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f));
        final AlphaAnimation tryBtnAlphaAnim = new AlphaAnimation(0.0f, 1.0f);
        tryBtnAlphaAnim.setDuration(333);
        this.A.setAnimation(tryBtnAlphaAnim);
        final AnimationSet priceAnimationSet2 = new AnimationSet(true);
        priceAnimationSet2.addAnimation(priceScale);
        priceAnimationSet2.addAnimation(alphaAnimation);
        priceScale.setDuration(333);
        alphaAnimation.setDuration(333);
        this.B.setAnimation(priceAnimationSet2);
        final float roundBtnOriginTranslationX = this.y.getTranslationX();
        ObjectAnimator transAnim = ObjectAnimator.ofFloat(this.y, "translationX", new float[]{0.0f, -225.0f});
        transAnim.setDuration(333);
        transAnim.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
        PathInterpolator animAlphaInterpolator = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        final float roundBtnOriginAlpha = this.y.getAlpha();
        ObjectAnimator alphaAnim3 = ObjectAnimator.ofFloat(this.y, "alpha", new float[]{1.0f, 0.0f});
        alphaAnim3.setDuration(333);
        alphaAnim3.setInterpolator(animAlphaInterpolator);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{toSmallAnim, transAnim, alphaAnim3});
        final LayoutParams layoutParams2 = downLoadInstallBtnOrginLP;
        final int i = downLoadInstallBtnOrginLPWidth;
        animatorSet.addListener(new AnimatorListener(this) {
            final /* synthetic */ d g;

            public void onAnimationStart(Animator animation) {
                this.g.z.setVisibility(0);
                priceAnimationSet2.startNow();
                tryBtnAlphaAnim.startNow();
            }

            public void onAnimationEnd(Animator animation) {
                layoutParams2.width = i;
                this.g.y.setLayoutParams(layoutParams2);
                this.g.y.setTranslationX(roundBtnOriginTranslationX);
                this.g.y.setVisibility(4);
                this.g.y.setAlpha(roundBtnOriginAlpha);
                this.g.y.setVisibility(8);
                this.g.z.setVisibility(0);
                this.g.A.setVisibility(0);
                this.g.B.setVisibility(0);
                this.g.B.setText(String.format("￥ %s", new Object[]{com.meizu.cloud.app.utils.g.a(this.g.k.price)}));
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
    }

    protected void b(String appName) {
    }

    private void d(final boolean isFirst) {
        int i = 1;
        if (!this.aG) {
            this.aG = true;
            String url = RequestConstants.getRuntimeDomainUrl(this.i, RequestConstants.ADD_COMMENT_PERMISSIONS);
            List<com.meizu.volley.b.a> params = new ArrayList();
            if (x.b(this.i)) {
                params.add(new com.meizu.volley.b.a("game_id", String.valueOf(this.k.id)));
            } else {
                params.add(new com.meizu.volley.b.a("app_id", String.valueOf(this.k.id)));
            }
            String str = "build_in";
            if (!this.br) {
                i = 0;
            }
            params.add(new com.meizu.volley.b.a(str, String.valueOf(i)));
            this.aW = new com.meizu.volley.a.b(this.i, new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }
            }, 0, url, params, new com.android.volley.n.b<ResultModel<Object>>(this) {
                final /* synthetic */ d b;

                public void a(ResultModel<Object> response) {
                    if (this.b.mRunning && this.b.getActivity() != null) {
                        this.b.aG = false;
                        if (this.b.bs != null) {
                            this.b.bs.dismiss();
                        }
                        String msg = "";
                        if (response != null) {
                            switch (response.getCode()) {
                                case 200:
                                    if (!isFirst) {
                                        this.b.H();
                                        break;
                                    }
                                    break;
                                case RequestConstants.CODE_APP_NOT_FOUND /*123001*/:
                                case 123100:
                                case 123101:
                                case 123102:
                                case 123103:
                                    msg = response.getMessage();
                                    break;
                                case 123104:
                                    if (!isFirst) {
                                        msg = this.b.getActivity().getString(com.meizu.cloud.b.a.i.add_comment_gag);
                                        break;
                                    }
                                    break;
                                case 123105:
                                    msg = this.b.getActivity().getString(com.meizu.cloud.b.a.i.add_comment_evaluated);
                                    if (response.getValue() != null) {
                                        this.b.aM = (AppCommentItem) JSONUtils.parseJSONObject(response.getValue().toString(), new TypeReference<AppCommentItem>(this) {
                                            final /* synthetic */ AnonymousClass40 a;

                                            {
                                                this.a = r1;
                                            }
                                        });
                                        if (this.b.aM != null) {
                                            this.b.Y.setRating((float) (this.b.aM.star / 10));
                                            this.b.Z.setText(this.b.getString(com.meizu.cloud.b.a.i.details_appinfo_addcomment_title_yes));
                                            this.b.aN = true;
                                            break;
                                        }
                                    }
                                    break;
                                case 123106:
                                    if (!isFirst) {
                                        int state = this.b.t();
                                        if (state != 4 && state != 5) {
                                            if (state != 0) {
                                                if (state != 1) {
                                                    if (state != 3) {
                                                        if (state == 2) {
                                                            msg = this.b.getString(com.meizu.cloud.b.a.i.add_comment_installing_toast);
                                                            break;
                                                        }
                                                    }
                                                    this.b.c(this.b.getString(com.meizu.cloud.b.a.i.install_continue), this.b.getString(com.meizu.cloud.b.a.i.add_comment_install_dialog_title));
                                                    break;
                                                }
                                                this.b.c(this.b.getString(com.meizu.cloud.b.a.i.immediate_update), this.b.getString(com.meizu.cloud.b.a.i.add_comment_update_dialog_title));
                                                break;
                                            }
                                            this.b.c(this.b.getString(com.meizu.cloud.b.a.i.install_at_once), this.b.getString(com.meizu.cloud.b.a.i.add_comment_install_dialog_title));
                                            break;
                                        }
                                        o submiter = x.d(this.b.i).c();
                                        if (submiter != null) {
                                            submiter.b();
                                        }
                                        this.b.H();
                                        break;
                                    }
                                    break;
                                case 198301:
                                    if (!isFirst) {
                                        this.b.G();
                                        break;
                                    }
                                    break;
                                default:
                                    msg = this.b.getActivity().getString(com.meizu.cloud.b.a.i.add_comment_server_error);
                                    break;
                            }
                        }
                        msg = this.b.getActivity().getString(com.meizu.cloud.b.a.i.add_comment_server_error);
                        if (!TextUtils.isEmpty(msg) && !isFirst) {
                            com.meizu.cloud.app.utils.a.a(this.b.i, msg);
                            if (!this.b.aN) {
                                this.b.Y.setRating(0.0f);
                            }
                        }
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ d b;

                public void a(s error) {
                    if (this.b.mRunning && this.b.getActivity() != null) {
                        this.b.aG = false;
                        if (!isFirst) {
                            if (this.b.bs != null) {
                                this.b.bs.dismiss();
                            }
                            if (error == null || !(error instanceof com.android.volley.a)) {
                                com.meizu.cloud.app.utils.a.a(this.b.i, this.b.getString(com.meizu.cloud.b.a.i.add_comment_network_error));
                            } else {
                                this.b.G();
                            }
                        }
                    }
                }
            });
            this.aW.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.i));
            com.meizu.volley.b.a(this.i).a().a(this.aW);
        }
    }

    private void G() {
        if (getActivity() != null && isAdded()) {
            this.bp = new com.meizu.cloud.a.b(this, 0, new com.meizu.cloud.a.a(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(String token, boolean isFromLogin) {
                    if (this.a.getActivity() != null) {
                        Log.i("AsyncExecuteFragment", "login success !");
                        this.a.Y.setRating(0.0f);
                    }
                }

                public void a(int errorCode) {
                    if (this.a.getActivity() != null) {
                        if (errorCode == 1) {
                            com.meizu.cloud.app.utils.a.a(this.a.getActivity(), this.a.getString(com.meizu.cloud.b.a.i.access_account_info_error));
                        } else if (errorCode == 4) {
                            this.a.Y.setRating(0.0f);
                        } else {
                            com.meizu.cloud.app.utils.a.a(this.a.getActivity(), this.a.getString(com.meizu.cloud.b.a.i.access_account_info_out_date));
                        }
                    }
                }
            });
            this.bp.a(true);
        }
    }

    private void H() {
        if (this.aL == null) {
            this.aL = new v(this.i, this.k.id, new com.meizu.cloud.app.fragment.v.a(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void a(int resultCode, AppCommentItem data) {
                    if (this.a.getActivity() != null && !this.a.getActivity().isDestroyed()) {
                        if (resultCode == 0) {
                            this.a.aM = data;
                            if (this.a.aM != null) {
                                this.a.Y.setRating((float) (this.a.aM.star / 10));
                                this.a.Z.setText(this.a.getString(com.meizu.cloud.b.a.i.details_appinfo_addcomment_title_yes));
                                this.a.aN = true;
                            }
                            com.meizu.cloud.compaign.a.a(this.a.getActivity()).a(com.meizu.cloud.compaign.a.a(this.a.getActivity()).a(this.a.k.getAppStructItem().package_name));
                            return;
                        }
                        this.a.Y.setRating(0.0f);
                    }
                }
            });
        }
        this.aL.a(this.aJ);
        this.aL.c();
        this.aL.d();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!(this.bp == null || getActivity() == null)) {
            this.bp.a(requestCode, resultCode, data);
        }
        this.bt = false;
        if (resultCode == -1) {
            switch (requestCode) {
                case 10:
                    if (this.k != null) {
                        com.meizu.cloud.app.share.a packageManagerProxy = new com.meizu.cloud.app.share.a();
                        String pkgName = data.getStringExtra("package");
                        String className = data.getStringExtra("class");
                        com.meizu.cloud.statistics.b.a().a("detail_share_item", this.mPageName, d(pkgName));
                        if (pkgName.equals(WXApp.WXAPP_PACKAGE_NAME)) {
                            if (!packageManagerProxy.a(getActivity(), pkgName)) {
                                b(getResources().getString(com.meizu.cloud.b.a.i.must_install_wechat), pkgName);
                                return;
                            } else if (className.equals("com.tencent.mm.ui.tools.ShareImgUI")) {
                                Log.w("tan", com.meizu.cloud.app.share.b.a(getActivity(), "com.meizu.flyme.gamecenter"));
                                this.bv.a(this.k);
                                this.bv.a(Boolean.valueOf(true));
                                this.bv.a();
                                return;
                            } else if (className.equals("com.tencent.mm.ui.tools.ShareToTimeLineUI")) {
                                if (TextUtils.isEmpty(this.k.h5_detail_url)) {
                                    a(pkgName, className);
                                    return;
                                }
                                this.bv.a(this.k);
                                this.bv.a(Boolean.valueOf(false));
                                this.bv.a();
                                return;
                            }
                        } else if (pkgName.equals("com.tencent.mobileqq")) {
                            if (!packageManagerProxy.a(getActivity(), pkgName)) {
                                b(getResources().getString(com.meizu.cloud.b.a.i.must_install_qq), pkgName);
                                return;
                            }
                        } else if (pkgName.equals("com.qzone")) {
                            if (!packageManagerProxy.a(getActivity(), pkgName)) {
                                b(getResources().getString(com.meizu.cloud.b.a.i.must_install_qqzone), pkgName);
                                return;
                            }
                        } else if (pkgName.equals("com.sina.weibo") && !packageManagerProxy.a(getActivity(), pkgName)) {
                            b(getResources().getString(com.meizu.cloud.b.a.i.must_install_weibo), pkgName);
                            return;
                        }
                        a(pkgName, className);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    protected void a(String pkgName, String className) {
        ComponentName componentName = new ComponentName(pkgName, className);
        Intent shareIntent = new Intent();
        shareIntent.setComponent(componentName);
        boolean suc = h.a(getActivity());
        String bodyContent = "";
        bodyContent = String.format(getResources().getString(com.meizu.cloud.b.a.i.share_to_others_content_appcenter), new Object[]{this.k.name});
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.setType("image/*");
        shareIntent.putExtra("android.intent.extra.SUBJECT", getResources().getString(com.meizu.cloud.b.a.i.sharesoftware_action) + this.k.name);
        shareIntent.putExtra("subject", getResources().getString(com.meizu.cloud.b.a.i.sharesoftware_action) + this.k.name);
        shareIntent.putExtra("sms_body", bodyContent + this.k.web_detail_url);
        if (suc) {
            shareIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + getActivity().getPackageName() + "/" + "TempImage/" + "share_image.jpg")));
        }
        shareIntent.putExtra("android.intent.extra.TEXT", bodyContent + this.k.web_detail_url);
        shareIntent.setFlags(134217728);
        this.i.startActivity(shareIntent);
    }

    protected void b(String text, final String pkgName) {
        new Builder(getActivity()).setMessage(text).setNegativeButton(getResources().getString(17039360), null).setPositiveButton(getResources().getString(com.meizu.cloud.b.a.i.must_intall_dialog_install), new DialogInterface.OnClickListener(this) {
            final /* synthetic */ d b;

            public void onClick(DialogInterface dialog, int which) {
                this.b.c(pkgName);
            }
        }).show();
    }

    protected void c(String pkgName) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://app.meizu.com/apps/public/detail?package_name=" + pkgName));
        if (x.a(getActivity())) {
            intent.putExtra("perform_internal", false);
        }
        intent.setPackage("com.meizu.mstore");
        this.i.startActivity(intent);
    }

    private void c(String positiveBtnStr, String dialogTitleStr) {
        Builder builder = new Builder(this.i);
        View dialogView = LayoutInflater.from(this.i).inflate(com.meizu.cloud.b.a.g.app_details_install_dialogview, null);
        ((TextView) dialogView.findViewById(com.meizu.cloud.b.a.f.add_comment_install_dialog_title)).setText(dialogTitleStr);
        builder.setView(dialogView).setPositiveButton(positiveBtnStr, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                int state = this.a.t();
                if (state == 0 || state == 1 || state == 3) {
                    this.a.l.a(new k(this.a.k));
                }
                this.a.Y.setRating(0.0f);
            }
        }).setNegativeButton(getString(com.meizu.cloud.b.a.i.cancel), new DialogInterface.OnClickListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                this.a.Y.setRating(0.0f);
            }
        }).setOnCancelListener(new OnCancelListener(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void onCancel(DialogInterface dialog) {
                this.a.Y.setRating(0.0f);
            }
        }).create().show();
    }

    public void onFetchStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        if (this.k != null && this.k.package_name.equals(wrapper.g())) {
            Log.i("INSTALL_STATE", "onFetchStateChange-->" + wrapper.f());
            c(wrapper);
        }
    }

    public void b(com.meizu.cloud.app.downlad.e wrapper) {
        if (this.k != null && wrapper.g().equals(this.k.package_name)) {
            Log.i("INSTALL_STATE", "onPatchStateChange-->" + wrapper.f());
            c(wrapper);
        }
    }

    public void onDownloadStateChanged(com.meizu.cloud.app.downlad.e wrapper) {
        if (this.k != null && !TextUtils.isEmpty(this.k.package_name) && this.k.package_name.equals(wrapper.g())) {
            Log.i("INSTALL_STATE", "onDownloadStateChanged-->" + wrapper.f());
            c(wrapper);
        }
    }

    public void onDownloadProgress(com.meizu.cloud.app.downlad.e wrapper) {
        if (this.k != null && this.k.package_name.equals(wrapper.g())) {
            Log.i("INSTALL_STATE", "onDownloadProgress-->" + wrapper.f() + "; progress-->" + wrapper.o());
            if (!wrapper.F()) {
                this.y.setState(1);
                this.y.setProgressText(getString(com.meizu.cloud.b.a.i.roundbtn_update_downloaded), (float) wrapper.o());
            }
        }
    }

    public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        if (this.k != null && wrapper.f() != com.meizu.cloud.app.downlad.f.f.DELETE_SUCCESS && wrapper.f() != com.meizu.cloud.app.downlad.f.f.DELETE_START && wrapper.g().equals(this.k.package_name)) {
            Log.i("INSTALL_STATE", "onInstallStateChange-->" + wrapper.f());
            c(wrapper);
        }
    }

    public void a(com.meizu.cloud.app.downlad.e wrapper) {
        if (!wrapper.F() && this.k != null && wrapper.g().equals(this.k.package_name)) {
            Log.i("INSTALL_STATE", "onPaymentStateChange-->" + wrapper.f());
            c(wrapper);
            if (wrapper.f() == j.SUCCESS && "pay".equals(this.n)) {
                com.meizu.cloud.compaign.a.a(getActivity()).a(com.meizu.cloud.compaign.a.a(getActivity()).a());
            }
        }
    }

    public void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    public void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, h());
    }

    protected Map<String, String> h() {
        Map<String, String> wdmDataMap = new HashMap();
        if (this.k != null) {
            wdmDataMap.put("apkname", this.k.package_name);
            wdmDataMap.put("appid", String.valueOf(this.k.id));
            wdmDataMap.put("appname", this.k.name);
        }
        if (this.bh != null) {
            wdmDataMap.put("source_page", this.bh.f);
            wdmDataMap.put("source_block_id", String.valueOf(this.bh.b));
            wdmDataMap.put("source_block_name", this.bh.c);
            wdmDataMap.put("source_block_type", this.bh.a);
            if (this.bh.g > 0) {
                wdmDataMap.put("source_block_profile_id", String.valueOf(this.bh.g));
            }
            wdmDataMap.put("source_pos", String.valueOf(this.bh.d));
            if (this.bh.e > 0) {
                wdmDataMap.put("source_hor_pos", String.valueOf(this.bh.e));
            }
        } else {
            wdmDataMap.put("source_page", this.bg);
        }
        if (!TextUtils.isEmpty(this.bi)) {
            wdmDataMap.put("search_id", this.bi);
        }
        long pushId = getArguments().getLong("push_message_id", 0);
        if (pushId > 0) {
            wdmDataMap.put("push_id", String.valueOf(pushId));
        }
        return wdmDataMap;
    }
}
