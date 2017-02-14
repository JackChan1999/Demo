package com.meizu.cloud.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.s;
import com.meizu.cloud.app.a.h;
import com.meizu.cloud.app.core.l;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f.k;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.DataReultModel;
import com.meizu.cloud.app.request.model.RankPageInfo.RankPageType;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.model.SearchHotModel;
import com.meizu.cloud.app.request.model.SearchSuggestModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.app.request.structitem.SearchHotItem;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.FlowLayout;
import com.meizu.cloud.app.widget.GradientButtom;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.b.n;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.statistics.b;
import com.meizu.cloud.thread.c;
import flyme.support.v7.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class p extends n implements OnClickListener, OnItemClickListener {
    public static final String CLEAR_TASK = "clear_task";
    public static final String SEARCH_TAG = "search";
    public static final String SHOW_KEYBOARD = "show_keyboard";
    protected a mAdapterSearchTip;
    private ImageView mCleanTextBtn;
    private View mCustomView;
    private ArrayList<SearchHotItem> mDataHot = new ArrayList();
    protected ArrayList<Object> mDataSearchTip = new ArrayList();
    private EditText mEditText;
    protected FlowLayout mHotFlowLayout;
    protected LinearLayout mHotLayout;
    protected int mHotStart = 0;
    private c mHotTask;
    protected boolean mIsInHotSearchPage = true;
    protected boolean mIsInLenovoResultPage = false;
    protected boolean mIsInSearchResultPage = false;
    protected boolean mIsKeyboardLenove = false;
    protected String mKey;
    protected String mMimeString;
    protected String mQuixeySearchId;
    protected String mQuixeySessionId;
    private TextView mSearchBtn;
    private c mSearchTask;
    private k mStateCallBack = new k(this) {
        final /* synthetic */ p a;

        {
            this.a = r1;
        }

        public void onDownloadStateChanged(e wrapper) {
            this.a.notifyStateChange(wrapper);
        }

        public void onDownloadProgress(e wrapper) {
            this.a.notifyStateChange(wrapper);
        }

        public void onFetchStateChange(e wrapper) {
            this.a.notifyStateChange(wrapper);
        }

        public void onInstallStateChange(e wrapper) {
            this.a.notifyStateChange(wrapper);
        }

        public void b(e wrapper) {
            this.a.notifyStateChange(wrapper);
        }

        public void a(e wrapper) {
            this.a.notifyStateChange(wrapper);
        }
    };
    protected ListView mTipListView;
    protected View mTipRefresh;
    protected View mTipRefreshText;
    private c mTipTask;
    protected t mViewControler;
    private com.meizu.g.a.a mVoiceCallback = new com.meizu.g.a.a.a(this) {
        final /* synthetic */ p a;

        {
            this.a = r1;
        }

        public void a(Intent rlt) throws RemoteException {
            if (this.a.mVoiceHelper != null && this.a.mVoiceHelper.a() && this.a.isAdded()) {
                String app_name = rlt.getStringExtra("result_app_object");
                final String action = rlt.getStringExtra("result_app_action");
                String rawtext = rlt.getStringExtra("result_rawtext");
                String searchKey = app_name;
                if (TextUtils.isEmpty(searchKey) && "launch".equals(action)) {
                    searchKey = rawtext;
                }
                if (!TextUtils.isEmpty(searchKey)) {
                    final String finalSearchKey = searchKey;
                    this.a.getActivity().runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass7 c;

                        public void run() {
                            this.c.a.mEditText.setEnabled(false);
                            this.c.a.mEditText.setText(finalSearchKey);
                            this.c.a.mVoiceTip.setVisibility(8);
                            if (this.c.a.mVoiceHelper != null && this.c.a.mVoiceHelper.a()) {
                                this.c.a.mVoiceHelper.f();
                            }
                            this.c.a.swapData(null);
                            this.c.a.doSearch(finalSearchKey, action);
                        }
                    });
                }
                Log.w("VoiceHelper", "app_name:" + app_name + ",action:" + action + ",rawtext:" + rawtext);
            }
        }

        public void b(Intent rlt) throws RemoteException {
            int errorCode = rlt.getIntExtra("error_code", -1);
            String errorMsg = rlt.getStringExtra("error_msg");
            Log.e("VoiceHelper", "errorCode:" + errorCode + ",errorMsg" + errorMsg);
            if (this.a.isAdded()) {
                boolean isShowNotice = false;
                if (errorCode == 20006 || errorCode == 20009) {
                    errorMsg = this.a.getActivity().getString(i.voice_occupation);
                    isShowNotice = true;
                } else if (errorCode == 800001) {
                    errorMsg = this.a.getActivity().getString(i.voice_error);
                    isShowNotice = true;
                }
                if (isShowNotice) {
                    Looper.prepare();
                    com.meizu.cloud.app.utils.a.a(this.a.getActivity(), errorMsg);
                    Looper.loop();
                }
            }
        }
    };
    private com.meizu.cloud.app.d.a mVoiceHelper;
    private RelativeLayout mVoiceTip;
    private ImageView mVoiceView;
    protected Map<String, String> mWdmDataMap = new HashMap();
    private boolean mbGotHot = false;
    private boolean mbInit = false;
    protected boolean mbMime = false;

    protected abstract class a extends BaseAdapter implements OnClickListener {
        protected Context a;
        protected ArrayList<Object> b;
        final /* synthetic */ p c;

        protected abstract View a(View view, AppUpdateStructItem appUpdateStructItem);

        public a(p pVar, Context context, ArrayList<Object> mItemData) {
            this.c = pVar;
            this.a = context;
            this.b = mItemData;
        }

        public ArrayList<Object> a() {
            return this.b;
        }

        public int getCount() {
            if (this.b != null) {
                return this.b.size();
            }
            return 0;
        }

        public Object getItem(int position) {
            return this.b == null ? null : this.b.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public int getViewTypeCount() {
            return 2;
        }

        public int getItemViewType(int position) {
            return a(position);
        }

        protected int a(int position) {
            Object o = this.b.get(position);
            if (o instanceof AppStructItem) {
                return 0;
            }
            if (o instanceof String) {
                return 1;
            }
            return -1;
        }

        public void onClick(View v) {
            String tag = v.getTag();
            if (tag != null && (tag instanceof String) && !TextUtils.isEmpty(tag)) {
                int start = this.c.mTipListView.getFirstVisiblePosition();
                for (int i = start; i <= this.c.mTipListView.getLastVisiblePosition(); i++) {
                    AppStructItem appStructItem = (AppStructItem) getItem(i);
                }
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            int type = a(position);
            if (type == 0) {
                AppUpdateStructItem appStructItem = (AppUpdateStructItem) this.b.get(position);
                appStructItem.click_pos = position + 1;
                View view = a(convertView, appStructItem);
                view.setClickable(false);
                return view;
            }
            if (convertView == null) {
                convertView = LayoutInflater.from(this.c.getActivity()).inflate(g.simple_list_item, null);
            }
            ImageView imageView = (ImageView) convertView.findViewById(f.leftIcon);
            TextView tv = (TextView) convertView.findViewById(16908308);
            if (type == 1) {
                imageView.setVisibility(0);
                tv.setText((String) this.b.get(position));
            }
            return convertView;
        }
    }

    public abstract void clickKeyGoto(SearchHotItem searchHotItem);

    public abstract Fragment createDetailFragment();

    public abstract h createRankAdapter(FragmentActivity fragmentActivity, t tVar);

    protected abstract a createTipArrayAdapter();

    protected abstract void doVioceAction(List<AppUpdateStructItem> list, String str, String str2);

    protected abstract void filterSearchHotItem(List<SearchHotItem> list);

    protected abstract String getSearchHotJson();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.mVoiceHelper == null) {
            this.mVoiceHelper = new com.meizu.cloud.app.d.a(getActivity(), this.mVoiceCallback);
            this.mVoiceHelper.b();
        }
        this.mbInitLoad = true;
        this.mViewControler = new t(getActivity(), new u());
        l resource = new l();
        resource.c = RankPageType.SEARCH;
        this.mViewControler.a(resource);
        d.a(getActivity()).a(this.mStateCallBack, new com.meizu.cloud.app.downlad.g());
        a.a.a.c.a().a((Object) this);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(f.search_menu);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        getActivity().onBackPressed();
        return true;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!this.mbInit) {
            if (TextUtils.isEmpty(this.mMimeString) && TextUtils.isEmpty(this.mKey)) {
                stopSearch();
                hideEmptyView();
                hideProgress();
            } else if (TextUtils.isEmpty(this.mMimeString)) {
                doSearch(this.mKey);
            } else {
                doSearch(this.mMimeString);
            }
            this.mbInit = true;
        }
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.base_search_fragment, container, false);
    }

    protected void initView(final View rootView) {
        int i = 0;
        super.initView(rootView);
        this.mHotLayout = (LinearLayout) rootView.findViewById(f.hot_layout);
        this.mHotLayout.setVisibility(8);
        this.mHotFlowLayout = (FlowLayout) rootView.findViewById(f.search_hot_flow);
        this.mHotFlowLayout.setPadding(this.mHotFlowLayout.getPaddingLeft(), (int) getResources().getDimension(com.meizu.cloud.b.a.d.search_hot_margin_top), this.mHotFlowLayout.getPaddingRight(), this.mHotFlowLayout.getPaddingBottom());
        com.meizu.cloud.app.utils.d.b(getActivity(), this.mHotLayout);
        this.mTipRefresh = rootView.findViewById(f.refresh_tip);
        this.mTipRefreshText = rootView.findViewById(f.refresh_tip_text);
        this.mTipRefresh.setOnClickListener(this);
        this.mTipRefreshText.setOnClickListener(this);
        this.mAdapterSearchTip = createTipArrayAdapter();
        this.mTipListView = (ListView) rootView.findViewById(f.list_tip);
        com.meizu.cloud.app.utils.d.b(getActivity(), this.mTipListView);
        this.mTipListView.setAdapter(this.mAdapterSearchTip);
        this.mTipListView.setDivider(null);
        OnTouchListener touchListener = new OnTouchListener(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.a.hideImm();
                return false;
            }
        };
        getRecyclerView().setOnTouchListener(touchListener);
        this.mHotLayout.setOnTouchListener(touchListener);
        this.mHotFlowLayout.setOnTouchListener(touchListener);
        this.mTipListView.setOnTouchListener(touchListener);
        this.mTipListView.setOnItemClickListener(this);
        this.mCustomView = getActivity().getLayoutInflater().inflate(g.search_bar, null);
        this.mSearchBtn = (TextView) this.mCustomView.findViewById(f.mc_search_textView);
        this.mSearchBtn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                if (this.a.mEditText.getText().toString().trim().length() > 0) {
                    this.a.doSearch(this.a.mEditText.getText().toString().trim());
                }
            }
        });
        this.mEditText = (EditText) this.mCustomView.findViewById(f.mc_search_edit);
        this.mEditText.setImeOptions(33554435);
        this.mEditText.setHint(getString(17039372));
        this.mEditText.setFilters(new InputFilter[]{new LengthFilter(32)});
        this.mSearchBtn.setEnabled(this.mEditText.getText().toString().trim().length() > 0);
        this.mCleanTextBtn = (ImageView) this.mCustomView.findViewById(f.mc_search_icon_input_clear);
        this.mCleanTextBtn.setVisibility(8);
        this.mCleanTextBtn.setOnClickListener(this);
        this.mEditText.setOnEditorActionListener(new OnEditorActionListener(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 3) {
                    return false;
                }
                if (v.getText().length() > 0) {
                    this.a.mVoiceView.setVisibility(8);
                    this.a.mCleanTextBtn.setVisibility(0);
                    if (v.getText().toString().trim().length() > 0) {
                        this.a.doSearch(v.getText().toString().trim());
                    }
                } else {
                    this.a.mCleanTextBtn.setVisibility(8);
                    this.a.mVoiceView.setVisibility(0);
                }
                return true;
            }
        });
        this.mEditText.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean z;
                this.a.mSearchBtn.setEnabled(s.length() > 0);
                if (s.length() > 0) {
                    this.a.mCleanTextBtn.setVisibility(0);
                    this.a.mVoiceView.setVisibility(8);
                } else {
                    this.a.mCleanTextBtn.setVisibility(8);
                    this.a.mVoiceView.setVisibility(0);
                }
                TextView access$300 = this.a.mSearchBtn;
                if (this.a.mEditText.getText().toString().trim().length() > 0) {
                    z = true;
                } else {
                    z = false;
                }
                access$300.setEnabled(z);
                if (this.a.mEditText.isEnabled()) {
                    this.a.setSearchTip(s.toString().trim(), false);
                } else {
                    this.a.mEditText.setEnabled(true);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        this.mEditText.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public boolean onTouch(View v, MotionEvent event) {
                this.a.showImm();
                return false;
            }
        });
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ p b;

            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                if (rootView.getRootView().getHeight() - (r.bottom - r.top) > 100 && this.b.mVoiceHelper != null && this.b.mVoiceHelper.a() && this.b.mVoiceView.isEnabled()) {
                    this.b.setVoiceTipsVisible(false);
                    this.b.mVoiceHelper.f();
                    if (this.b.getRecyclerView().getVisibility() != 0) {
                        this.b.setSearchTip(this.b.mEditText.getText().toString().trim(), true);
                    }
                }
            }
        });
        this.mVoiceTip = (RelativeLayout) rootView.findViewById(f.voice_tips);
        this.mVoiceView = (ImageView) this.mCustomView.findViewById(f.mc_voice_icon);
        this.mVoiceView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                if (this.a.mVoiceHelper != null) {
                    this.a.hideImm();
                    this.a.mEditText.getText().clear();
                    this.a.setVoiceTipsVisible(true);
                    this.a.mVoiceHelper.e();
                    this.a.mVoiceView.setEnabled(false);
                    this.a.mUiHandler.postDelayed(new Runnable(this) {
                        final /* synthetic */ AnonymousClass13 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            this.a.a.mVoiceView.setEnabled(true);
                        }
                    }, 50);
                }
            }
        });
        this.mVoiceView.setVisibility(this.mEditText.getText().toString().trim().length() == 0 ? 0 : 8);
        ImageView imageView = this.mCleanTextBtn;
        if (this.mEditText.getText().toString().trim().length() <= 0) {
            i = 8;
        }
        imageView.setVisibility(i);
    }

    public void hideImm() {
        try {
            InputMethodManager mImm = (InputMethodManager) getActivity().getSystemService("input_method");
            this.mEditText.clearFocus();
            mImm.hideSoftInputFromWindow(this.mEditText.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showImm() {
        if (isResumed()) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService("input_method");
            imm.toggleSoftInput(2, 2);
            imm.showSoftInput(this.mEditText, 2);
        }
    }

    public void onClick(View v) {
        if (v.getId() == f.mc_search_icon_input_clear) {
            stopSearchAndClear();
        } else if (v.getId() == f.refresh_tip || v.getId() == f.refresh_tip_text) {
            if (!(this.mHotTask == null || this.mHotTask.a())) {
                this.mHotTask.b();
            }
            this.mDataHot.clear();
            this.mHotFlowLayout.removeAllViews();
            this.mbGotHot = false;
            getHotSearch();
        }
    }

    protected void stopSearchAndClear() {
        stopSearch();
        showImm();
        hideEmptyView();
        hideProgress();
        getRecyclerView().setVisibility(8);
        swapData(null);
        this.mEditText.setText("");
        this.mCleanTextBtn.setVisibility(8);
        this.mVoiceView.setVisibility(0);
    }

    public void onResume() {
        super.onResume();
        if (!this.mbGotHot) {
            getHotSearch();
        }
        if (getArguments() != null && getArguments().getBoolean(SHOW_KEYBOARD, false)) {
            getArguments().remove(SHOW_KEYBOARD);
            showImm();
        }
        if (this.mIsInSearchResultPage) {
            b.a().a("searchResultHand");
            refreshViewConWdmPage("searchResultHand");
        } else if (this.mIsInHotSearchPage) {
            b.a().a(SEARCH_TAG);
            refreshViewConWdmPage(SEARCH_TAG);
        } else {
            b.a().a("searchResultAuto");
            refreshViewConWdmPage("searchResultAuto");
        }
    }

    public void onPause() {
        this.mVoiceView.setEnabled(false);
        this.mUiHandler.postDelayed(new Runnable(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.mVoiceView.setEnabled(true);
            }
        }, 50);
        if (this.mVoiceHelper != null) {
            if (getRecyclerViewAdapter().d() <= 0) {
                if (this.mSearchTask == null) {
                    setVoiceTipsVisible(false);
                } else if (!this.mSearchTask.a()) {
                    this.mEditText.getText().clear();
                    hideProgress();
                    this.mSearchTask.b();
                    setVoiceTipsVisible(false);
                } else if (this.mVoiceTip.getVisibility() == 0) {
                    this.mEditText.getText().clear();
                    hideProgress();
                    swapData(null);
                    setVoiceTipsVisible(false);
                }
            }
            this.mVoiceHelper.f();
        }
        hideImm();
        super.onPause();
        if (this.mIsInSearchResultPage) {
            b.a().a("searchResultHand", this.mWdmDataMap);
        } else if (this.mIsInHotSearchPage) {
            b.a().a(SEARCH_TAG, this.mWdmDataMap);
        } else {
            b.a().a("searchResultAuto", this.mWdmDataMap);
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActionBar().a(this.mCustomView, new LayoutParams(-2, -2));
    }

    protected void setupActionBar() {
        super.setupActionBar();
        ActionBar bar = getActionBar();
        bar.a(false);
        bar.f(false);
        bar.c(false);
        bar.e(true);
    }

    public void onDestroyView() {
        hideImm();
        this.mVoiceHelper.f();
        super.onDestroyView();
    }

    public void onDestroy() {
        d.a(getActivity()).b(this.mStateCallBack);
        a.a.a.c.a().c(this);
        if (this.mTipTask != null) {
            this.mTipTask.b();
            this.mTipTask = null;
        }
        if (this.mSearchTask != null) {
            this.mSearchTask.b();
            this.mSearchTask = null;
        }
        if (this.mHotTask != null) {
            this.mHotTask.b();
            this.mHotTask = null;
        }
        if (this.mVoiceHelper != null) {
            this.mVoiceHelper.d();
        }
        super.onDestroy();
    }

    private void getHotSearch() {
        if (!this.mbGotHot && this.mDataHot.size() <= 0) {
            if (this.mHotTask == null || this.mHotTask.a()) {
                this.mHotTask = asyncExec(new Runnable(this) {
                    final /* synthetic */ p a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        Process.setThreadPriority(10);
                        String json = this.a.getSearchHotJson();
                        if (json != null) {
                            ResultModel<SearchHotModel<SearchHotItem>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<SearchHotModel<SearchHotItem>>>(this) {
                                final /* synthetic */ AnonymousClass15 a;

                                {
                                    this.a = r1;
                                }
                            });
                            if (resultModel != null && resultModel.getCode() == 200 && resultModel.getValue() != null) {
                                final List<SearchHotItem> searchHotItems = ((SearchHotModel) resultModel.getValue()).data == null ? new ArrayList() : ((SearchHotModel) resultModel.getValue()).data;
                                if (!Thread.currentThread().isInterrupted()) {
                                    this.a.filterSearchHotItem(searchHotItems);
                                    for (SearchHotItem searchHotItem : searchHotItems) {
                                        searchHotItem.generateBlockGotoPageInfo();
                                    }
                                    if (((SearchHotModel) resultModel.getValue()).more) {
                                        p pVar = this.a;
                                        pVar.mHotStart += searchHotItems.size();
                                    } else {
                                        this.a.mHotStart = 0;
                                    }
                                    this.a.runOnUi(new Runnable(this) {
                                        final /* synthetic */ AnonymousClass15 b;

                                        public void run() {
                                            if (this.b.a.mRunning) {
                                                this.b.a.mbGotHot = true;
                                                if (searchHotItems.size() > 0) {
                                                    this.b.a.mDataHot.clear();
                                                    this.b.a.mDataHot.addAll(searchHotItems);
                                                }
                                                if (this.b.a.mEditText.getText().toString().trim().length() == 0) {
                                                    this.b.a.mDataSearchTip.clear();
                                                    this.b.a.mTipListView.setVisibility(8);
                                                }
                                                if (this.b.a.mDataHot.size() > 0) {
                                                    this.b.a.mHotFlowLayout.removeAllViews();
                                                    Iterator i$ = this.b.a.mDataHot.iterator();
                                                    while (i$.hasNext()) {
                                                        final SearchHotItem searchHotItem = (SearchHotItem) i$.next();
                                                        GradientButtom button = (GradientButtom) LayoutInflater.from(this.b.a.getActivity()).inflate(g.search_hot_item, null);
                                                        int height = this.b.a.getResources().getDimensionPixelOffset(com.meizu.cloud.b.a.d.search_hot_item_height);
                                                        FlowLayout.LayoutParams layoutParams = new FlowLayout.LayoutParams(-2, height);
                                                        int buttonMargin = this.b.a.getResources().getDimensionPixelOffset(com.meizu.cloud.b.a.d.search_hot_item_margin);
                                                        int buttonMarginTop = this.b.a.getResources().getDimensionPixelOffset(com.meizu.cloud.b.a.d.search_hot_item_margin_top);
                                                        layoutParams.setMargins(buttonMargin, buttonMarginTop, buttonMargin, buttonMarginTop);
                                                        button.setLayoutParams(layoutParams);
                                                        if (!x.a(this.b.a.getActivity())) {
                                                            p.updateTagViewGameCenter(button, searchHotItem, (float) (height / 2));
                                                        } else if (searchHotItem.tag != null) {
                                                            p.updateTagView(button, searchHotItem.title, TextUtils.isEmpty(searchHotItem.fontColor) ? searchHotItem.tag.bg_color : searchHotItem.fontColor, (float) (height / 2));
                                                        } else {
                                                            p.updateTagView(button, searchHotItem.title, searchHotItem.fontColor, (float) (height / 2));
                                                        }
                                                        button.setOnClickListener(new OnClickListener(this) {
                                                            final /* synthetic */ AnonymousClass2 b;

                                                            public void onClick(View v) {
                                                                if (searchHotItem.type == 2) {
                                                                    this.b.b.a.doSearch(searchHotItem.title);
                                                                    b.a().a("recom_click", p.SEARCH_TAG, com.meizu.cloud.statistics.c.a(searchHotItem));
                                                                    return;
                                                                }
                                                                this.b.b.a.clickKeyGoto(searchHotItem);
                                                            }
                                                        });
                                                        this.b.a.mHotFlowLayout.addView(button);
                                                    }
                                                    if (this.b.a.mEditText.getText().toString().trim().length() != 0) {
                                                        return;
                                                    }
                                                    if (this.b.a.mVoiceHelper == null || !this.b.a.mVoiceHelper.a()) {
                                                        this.b.a.mHotLayout.setVisibility(0);
                                                        return;
                                                    }
                                                    return;
                                                }
                                                this.b.a.mHotLayout.setVisibility(8);
                                            }
                                        }
                                    });
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        Log.w("AsyncExecuteFragment", "search hot url return null");
                    }
                });
            }
        }
    }

    public static void updateTagView(GradientButtom view, String name, String strColor, float radius) {
        Context context = view.getContext();
        Resources resources = context.getResources();
        GradientDrawable normalDrawable = new GradientDrawable();
        GradientDrawable pressDrawable = new GradientDrawable();
        int bgcolor = 0;
        if (!TextUtils.isEmpty(strColor)) {
            while (strColor.length() < 7) {
                strColor = strColor + "0";
            }
            try {
                bgcolor = Color.parseColor(strColor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (bgcolor != 0) {
            normalDrawable.setStroke(2, bgcolor);
            pressDrawable.setColor(bgcolor);
            view.setSelecorText(bgcolor, context.getResources().getColor(17170443));
        } else {
            int lineColor = resources.getColor(com.meizu.cloud.b.a.c.search_hot_line);
            normalDrawable.setStroke(2, lineColor);
            pressDrawable.setColor(lineColor);
            view.setSelecorText(resources.getColor(com.meizu.cloud.b.a.c.search_hot_text), context.getResources().getColor(17170443));
        }
        normalDrawable.setCornerRadius(radius);
        pressDrawable.setCornerRadius(radius);
        view.setText(name);
        view.setBackgroundSelecorDrawable(normalDrawable, pressDrawable);
    }

    public static void updateTagViewGameCenter(GradientButtom view, SearchHotItem searchHotItem, float radius) {
        Context context = view.getContext();
        Resources resources = context.getResources();
        GradientDrawable normalDrawable = new GradientDrawable();
        GradientDrawable pressDrawable = new GradientDrawable();
        int bgcolor = 0;
        if (!TextUtils.isEmpty(searchHotItem.bgColor)) {
            while (searchHotItem.bgColor.length() < 7) {
                searchHotItem.bgColor += "0";
            }
            try {
                bgcolor = Color.parseColor(searchHotItem.bgColor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int fontColor = 0;
        if (!TextUtils.isEmpty(searchHotItem.fontColor)) {
            while (searchHotItem.fontColor.length() < 7) {
                searchHotItem.fontColor += "0";
            }
            try {
                fontColor = Color.parseColor(searchHotItem.fontColor);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (bgcolor != 0) {
            normalDrawable.setStroke(2, bgcolor);
            pressDrawable.setColor(bgcolor);
            if (fontColor == 0) {
                fontColor = bgcolor;
            }
            view.setSelecorText(fontColor, context.getResources().getColor(17170443));
        } else {
            int lineColor = resources.getColor(com.meizu.cloud.b.a.c.search_hot_line);
            normalDrawable.setStroke(2, lineColor);
            pressDrawable.setColor(lineColor);
            view.setSelecorText(resources.getColor(com.meizu.cloud.b.a.c.search_hot_text), context.getResources().getColor(17170443));
        }
        normalDrawable.setCornerRadius(radius);
        pressDrawable.setCornerRadius(radius);
        view.setText(searchHotItem.title);
        view.setBackgroundSelecorDrawable(normalDrawable, pressDrawable);
        if (searchHotItem.tag != null) {
            view.setTag(searchHotItem.tag.text, searchHotItem.tag.bg_color);
        }
    }

    private void setVoiceTipsVisible(boolean isVisible) {
        if (isVisible) {
            this.mHotLayout.setVisibility(8);
            this.mVoiceTip.setVisibility(0);
            swapData(null);
            return;
        }
        if (getItemCount() > 0 || this.mEditText.getText().toString().trim().length() != 0 || this.mDataHot.size() <= 0) {
            this.mHotLayout.setVisibility(8);
        } else {
            this.mHotLayout.setVisibility(0);
        }
        this.mVoiceTip.setVisibility(8);
    }

    protected String getEmptyTextString() {
        if (m.b(getActivity())) {
            return getString(i.search_none);
        }
        return getString(i.network_error);
    }

    private boolean isVoiceServiceExist() {
        if (getActivity().getPackageManager().queryIntentServices(new Intent("com.meizu.voiceassistant.support.IVoiceAssistantService"), 4).size() > 0) {
            return true;
        }
        return false;
    }

    public com.meizu.cloud.base.a.d createRecyclerAdapter() {
        int[] pageInfo = new int[3];
        pageInfo[1] = 10;
        this.mViewControler.a(pageInfo);
        final h adapter = createRankAdapter(getActivity(), this.mViewControler);
        adapter.a(true);
        adapter.a(new com.meizu.cloud.base.a.d.b(this) {
            final /* synthetic */ p b;

            public void onItemClick(View itemView, int position) {
                AppStructItem appStructItem = (AppStructItem) adapter.c(position);
                if (appStructItem != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("url", appStructItem.url);
                    bundle.putString("title_name", appStructItem.name);
                    bundle.putInt("source_page_id", 10);
                    bundle.putString("search_id", this.b.getWdmSearchId());
                    bundle.putString("source_page", "searchResultHand");
                    if (com.meizu.cloud.statistics.a.c(appStructItem)) {
                        bundle.putInt("positionId", appStructItem.position_id);
                        bundle.putInt("unitId", appStructItem.unit_id);
                        bundle.putString("requestId", appStructItem.request_id);
                        bundle.putString("version", appStructItem.version);
                        bundle.putString("kw", appStructItem.kw);
                        bundle.putLong("kwId", appStructItem.kw_id);
                        bundle.putInt("trackerType", appStructItem.tracker_type);
                    }
                    Fragment detailFragment = this.b.createDetailFragment();
                    detailFragment.setArguments(bundle);
                    com.meizu.cloud.base.b.d.startFragment(this.b.getActivity(), detailFragment);
                    appStructItem.click_pos = position + 1;
                    b.a().b("Serp_Click", "", this.b.buildQuixeyMap("serp", "click", appStructItem.package_name, position + 1, ""));
                    b.a().a("item", "searchResultHand", com.meizu.cloud.statistics.c.a(appStructItem, this.b.getWdmSearchId()));
                    com.meizu.cloud.statistics.a.a(this.b.getActivity()).b(appStructItem);
                }
            }
        });
        adapter.a(new h.d(this) {
            final /* synthetic */ p a;

            {
                this.a = r1;
            }

            public void a(AppStructItem appStructItem) {
                if (appStructItem != null && this.a.getActivity() != null) {
                    appStructItem.search_id = this.a.getWdmSearchId();
                    com.meizu.cloud.app.core.c compareResult = x.d(this.a.getActivity()).a(appStructItem.package_name, appStructItem.version_code);
                    if (com.meizu.cloud.statistics.c.a(this.a.getActivity(), appStructItem.package_name, appStructItem.version_code)) {
                        if (compareResult == com.meizu.cloud.app.core.c.UPGRADE) {
                            b.a().b("Serp_ButtonClick", "", this.a.buildQuixeyMap("serp", "update", appStructItem.package_name, appStructItem.click_pos, ""));
                        } else {
                            b.a().b("Serp_ButtonClick", "", this.a.buildQuixeyMap("serp", "install", appStructItem.package_name, appStructItem.click_pos, ""));
                        }
                    } else if (compareResult == com.meizu.cloud.app.core.c.OPEN || compareResult == com.meizu.cloud.app.core.c.BUILD_IN) {
                        b.a().b("Serp_ButtonClick", "", this.a.buildQuixeyMap("serp", "open", appStructItem.package_name, appStructItem.click_pos, ""));
                    }
                }
            }
        });
        return adapter;
    }

    protected String getSearchRequestUrl() {
        return RequestConstants.getRuntimeDomainUrl(getActivity(), this.mbMime ? RequestConstants.MIME_SEARCH : RequestConstants.SEARCH);
    }

    protected String getSuggestRequestUrl() {
        return RequestConstants.getRuntimeDomainUrl(getActivity(), RequestConstants.SEARCH_SUGGEST);
    }

    protected String getSearchHotUrlV2() {
        return RequestConstants.getRuntimeDomainUrl(getActivity(), RequestConstants.SEARCH_HOT_V2);
    }

    protected void onRequestData() {
    }

    protected boolean onResponse(Object response) {
        hideProgress();
        return true;
    }

    protected void onErrorResponse(s error) {
        hideProgress();
    }

    private void stopSearch() {
        if (this.mSearchTask != null && !this.mSearchTask.a()) {
            this.mSearchTask.b();
        }
    }

    protected void doSearch(String text) {
        doSearch(text, null);
    }

    protected void doSearch(String text, String action) {
        if (!TextUtils.isEmpty(text)) {
            this.mbMime = false;
            if (!TextUtils.isEmpty(this.mMimeString) && text.equals(this.mMimeString)) {
                this.mbMime = true;
            }
            stopSearch();
            this.mEditText.setEnabled(false);
            this.mEditText.setText(text);
            this.mEditText.setSelection(this.mEditText.getText().length());
            hideImm();
            this.mTipListView.setVisibility(8);
            this.mHotLayout.setVisibility(8);
            this.mVoiceTip.setVisibility(8);
            if (this.mVoiceHelper != null && this.mVoiceHelper.a()) {
                this.mVoiceHelper.f();
            }
            swapData(null);
            doSearchBase(text, action);
        }
    }

    private void doSearchBase(final String key, final String action) {
        if (!(this.mSearchTask == null || this.mSearchTask.a())) {
            this.mSearchTask.b();
        }
        getRecyclerView().scrollToPosition(0);
        getRecyclerView().setVisibility(8);
        showProgress();
        this.mSearchTask = asyncExec(new Runnable(this) {
            final /* synthetic */ p c;

            public void run() {
                Process.setThreadPriority(10);
                this.c.mQuixeySearchId = this.c.getQuixeySearchId(this.c.getActivity());
                this.c.mQuixeySessionId = this.c.getQuixeySessionId(this.c.getActivity());
                b.a().b("Search_quixey", "", this.c.buildQuixeyMap(p.SEARCH_TAG, "Click", p.SEARCH_TAG, 0, key));
                ResultModel<DataReultModel<AppUpdateStructItem>> resultModel = JSONUtils.parseResultModel(RequestManager.getInstance(this.c.getActivity()).search(this.c.getSearchRequestUrl(), key, this.c.mQuixeySearchId, this.c.mQuixeySessionId), new TypeReference<ResultModel<DataReultModel<AppUpdateStructItem>>>(this) {
                    final /* synthetic */ AnonymousClass4 a;

                    {
                        this.a = r1;
                    }
                });
                if (!(resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null)) {
                    final List<AppUpdateStructItem> appStructItems = ((DataReultModel) resultModel.getValue()).data;
                    for (AppUpdateStructItem appUpdateStructItem : appStructItems) {
                        com.meizu.cloud.app.core.b.a(this.c.getActivity(), appUpdateStructItem);
                    }
                    if (this.c.mRunning && !Thread.currentThread().isInterrupted()) {
                        this.c.runOnUi(new Runnable(this) {
                            final /* synthetic */ AnonymousClass4 b;

                            public void run() {
                                List list = null;
                                if (this.b.c.mRunning) {
                                    this.b.c.handleEnterResultPage(key, action);
                                    this.b.c.hideProgress();
                                    if (appStructItems == null || appStructItems.size() == 0) {
                                        this.b.c.getRecyclerView().setVisibility(8);
                                        this.b.c.showEmptyView(this.b.c.getEmptyTextString(), null, null);
                                        return;
                                    }
                                    this.b.c.getRecyclerView().setVisibility(0);
                                    this.b.c.hideEmptyView();
                                    p pVar = this.b.c;
                                    if (appStructItems != null) {
                                        list = appStructItems;
                                    }
                                    pVar.swapData(list);
                                    this.b.c.doVioceAction(appStructItems, action, key);
                                }
                            }
                        });
                    }
                }
                if (!m.b(this.c.getActivity()) && this.c.mRunning && !Thread.currentThread().isInterrupted()) {
                    this.c.runOnUi(new Runnable(this) {
                        final /* synthetic */ AnonymousClass4 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            if (!this.a.c.isDetached()) {
                                this.a.c.showEmptyView(this.a.c.getString(i.network_error), null, new OnClickListener(this) {
                                    final /* synthetic */ AnonymousClass3 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void onClick(View v) {
                                        this.a.a.c.doSearchBase(key, action);
                                    }
                                });
                                this.a.c.getRecyclerView().setVisibility(8);
                            }
                        }
                    });
                }
            }
        });
    }

    private void setSearchTip(final String text, boolean isVoiceSearch) {
        swapData(null);
        hideEmptyView();
        hideProgress();
        getRecyclerView().setVisibility(8);
        stopSearch();
        this.mDataSearchTip.clear();
        if (!(this.mTipTask == null || this.mTipTask.a())) {
            this.mTipTask.b();
        }
        if (TextUtils.isEmpty(text)) {
            if (this.mDataHot.size() > 0) {
                this.mHotLayout.setVisibility(0);
            }
            this.mTipListView.setVisibility(8);
            this.mAdapterSearchTip.a().clear();
            this.mAdapterSearchTip.notifyDataSetChanged();
            handleBackToHotPage(isVoiceSearch);
            return;
        }
        this.mHotLayout.setVisibility(8);
        this.mTipListView.setVisibility(0);
        this.mAdapterSearchTip.a().clear();
        this.mAdapterSearchTip.notifyDataSetChanged();
        this.mTipTask = asyncExec(new Runnable(this) {
            final /* synthetic */ p b;

            public void run() {
                Process.setThreadPriority(10);
                String json = RequestManager.getInstance(this.b.getActivity()).search(this.b.getSuggestRequestUrl(), text);
                if (json != null) {
                    ResultModel<String> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<String>>(this) {
                        final /* synthetic */ AnonymousClass5 a;

                        {
                            this.a = r1;
                        }
                    });
                    JSONObject jsonObject = JSON.parseObject((String) resultModel.getValue());
                    SearchSuggestModel<AppUpdateStructItem> suggestModel = (SearchSuggestModel) JSONUtils.parseJSONObject(jsonObject.getString("data"), new TypeReference<SearchSuggestModel<AppUpdateStructItem>>(this) {
                        final /* synthetic */ AnonymousClass5 a;

                        {
                            this.a = r1;
                        }
                    });
                    if (resultModel.getCode() == 200) {
                        final List<String> keys = new ArrayList();
                        if (suggestModel.words != null) {
                            keys.addAll(suggestModel.words);
                        }
                        final List<AppUpdateStructItem> appStructItems = new ArrayList();
                        if (suggestModel.apps != null && suggestModel.apps.size() > 0) {
                            appStructItems.addAll(suggestModel.apps);
                        } else if (suggestModel.games != null && suggestModel.games.size() > 0) {
                            appStructItems.addAll(suggestModel.games);
                        }
                        for (AppUpdateStructItem appUpdateStructItem : appStructItems) {
                            com.meizu.cloud.app.core.b.a(this.b.getActivity(), appUpdateStructItem);
                        }
                        if (!Thread.currentThread().isInterrupted()) {
                            this.b.runOnUi(new Runnable(this) {
                                final /* synthetic */ AnonymousClass5 c;

                                public void run() {
                                    if (this.c.b.mRunning) {
                                        this.c.b.handleEnterLenovePage(text);
                                        if (appStructItems.size() > 0) {
                                            this.c.b.mDataSearchTip.clear();
                                            this.c.b.mDataSearchTip.addAll(appStructItems);
                                        }
                                        if (keys.size() > 0) {
                                            this.c.b.mDataSearchTip.addAll(keys);
                                        }
                                        this.c.b.mAdapterSearchTip.notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    protected void handleBackToHotPage(boolean isVoiceSearch) {
        if (this.mIsInHotSearchPage) {
            if (!isVoiceSearch) {
                b.a().a(SEARCH_TAG, null);
            }
        } else if (this.mIsInSearchResultPage) {
            b.a().a("searchResultHand", this.mWdmDataMap);
        } else {
            b.a().a("searchResultAuto", this.mWdmDataMap);
        }
        this.mIsInLenovoResultPage = false;
        this.mIsInHotSearchPage = true;
        this.mIsInSearchResultPage = false;
        this.mIsKeyboardLenove = false;
        if (this.mIsInSearchResultPage) {
            b.a().a("cleanSearchHistory", "searchResultHand", this.mWdmDataMap);
        } else if (this.mIsInLenovoResultPage) {
            b.a().a("cleanSearchHistory", "searchResultAuto", this.mWdmDataMap);
        }
        this.mWdmDataMap.clear();
        if (!isVoiceSearch) {
            b.a().a(SEARCH_TAG);
            refreshViewConWdmPage(SEARCH_TAG);
        }
    }

    protected void handleEnterLenovePage(String key) {
        if (this.mIsInHotSearchPage) {
            b.a().a(SEARCH_TAG, null);
        } else if (this.mIsInSearchResultPage) {
            b.a().a("searchResultHand", this.mWdmDataMap);
        } else {
            b.a().a("searchResultAuto", this.mWdmDataMap);
        }
        this.mIsInLenovoResultPage = true;
        this.mIsInHotSearchPage = false;
        this.mIsInSearchResultPage = false;
        this.mIsKeyboardLenove = false;
        this.mWdmDataMap.put("keyword", key);
        this.mWdmDataMap.put("search_id", com.meizu.cloud.statistics.c.a());
        b.a().a("searchResultAuto");
        refreshViewConWdmPage("searchResultAuto");
    }

    protected void handleEnterResultPage(String key, String action) {
        if (this.mIsInHotSearchPage) {
            b.a().a(SEARCH_TAG, null);
        } else if (this.mIsInSearchResultPage) {
            b.a().a("searchResultHand", this.mWdmDataMap);
        } else {
            b.a().a("searchResultAuto", this.mWdmDataMap);
        }
        this.mWdmDataMap.put("keyword", key);
        this.mWdmDataMap.put("search_id", com.meizu.cloud.statistics.c.a());
        if (this.mIsKeyboardLenove && TextUtils.isEmpty(action)) {
            this.mWdmDataMap.put("from", "relate");
            b.a().a("keyboard", "searchResultAuto", this.mWdmDataMap);
        }
        this.mIsInLenovoResultPage = false;
        this.mIsInHotSearchPage = false;
        this.mIsInSearchResultPage = true;
        this.mIsKeyboardLenove = false;
        if (this.mWdmDataMap.containsKey("from")) {
            this.mWdmDataMap.remove("from");
        }
        b.a().a("searchResultHand");
        refreshViewConWdmPage("searchResultHand");
        b.a().a("search_btn_active", "", null);
    }

    protected String getWdmSearchId() {
        return this.mWdmDataMap.containsKey("search_id") ? (String) this.mWdmDataMap.get("search_id") : "";
    }

    protected String getQuixeySessionId(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        long lastTime = preferences.getLong("quixey_timestamp", 0);
        long nowTime = System.currentTimeMillis();
        String sessionId = "";
        if (lastTime > 0 && nowTime - lastTime <= 1800000) {
            return preferences.getString("quixey_sessionid", "");
        }
        sessionId = getQuixeySearchId(context);
        preferences.edit().putString("quixey_sessionid", sessionId).putLong("quixey_timestamp", nowTime).apply();
        return sessionId;
    }

    protected String getQuixeySearchId(Context context) {
        String md5Str = com.meizu.cloud.app.utils.l.a(com.meizu.cloud.app.utils.d.a(context) + System.currentTimeMillis());
        String[] hexDigits = new String[]{"0", PushConstants.CLICK_TYPE_ACTIVITY, PushConstants.CLICK_TYPE_WEB, "3", "4", "5", "6", "7", "8", "9", "a", "cancel", "c", "d", "e", "f"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append(hexDigits[new Random().nextInt(hexDigits.length)]);
        }
        return md5Str + builder.toString();
    }

    protected Map<String, String> buildQuixeyMap(String category, String action, String label, int position, String query) {
        Map<String, String> dataMap = new HashMap();
        dataMap.put("search_id", this.mQuixeySearchId);
        dataMap.put("session_id", this.mQuixeySessionId);
        dataMap.put("category", category);
        dataMap.put("action", action);
        dataMap.put("label", label);
        dataMap.put("user_agent", com.meizu.cloud.app.utils.d.i());
        if (position > 0) {
            dataMap.put("position", String.valueOf(position));
        }
        if (!TextUtils.isEmpty(query)) {
            dataMap.put("query", query);
        }
        return dataMap;
    }

    protected void refreshViewConWdmPage(String wdmPage) {
        if (this.mViewControler != null) {
            this.mViewControler.a(wdmPage);
        }
    }

    protected void notifyStateChange(e wrapper) {
        int i;
        AppStructItem item;
        CirProButton btn;
        if (getRecyclerView() == null || getRecyclerView().getVisibility() != 0) {
            if (this.mTipListView != null && this.mTipListView.getVisibility() == 0) {
                int count = this.mAdapterSearchTip.getCount();
                for (i = 0; i < count; i++) {
                    if (this.mAdapterSearchTip.getItem(i) instanceof AppStructItem) {
                        item = (AppStructItem) this.mAdapterSearchTip.getItem(i);
                        if (item.id == wrapper.i()) {
                            btn = (CirProButton) this.mTipListView.findViewWithTag(item.package_name);
                            if (btn != null) {
                                this.mViewControler.a(wrapper, btn);
                                return;
                            } else {
                                this.mAdapterSearchTip.notifyDataSetChanged();
                                return;
                            }
                        }
                    }
                }
            }
        } else if (getRecyclerView().getLayoutManager() != null) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            com.meizu.cloud.app.a.a appListAdapter = (com.meizu.cloud.app.a.a) getRecyclerViewAdapter();
            i = firstVisiblePosition;
            while (i <= lastVisiblePosition) {
                item = (AppStructItem) appListAdapter.c(i);
                if (item == null || TextUtils.isEmpty(item.package_name) || !item.package_name.equals(wrapper.g())) {
                    i++;
                } else {
                    btn = (CirProButton) getRecyclerView().findViewWithTag(item.package_name);
                    if (btn != null && wrapper != null) {
                        this.mViewControler.a(wrapper, btn);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        notiyStateChange(appStateChangeEvent.b);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.b appUpdateCheckEvent) {
        if (appUpdateCheckEvent.c) {
            for (String pkg : appUpdateCheckEvent.a) {
                notiyStateChange(pkg);
            }
        }
    }

    private void notiyStateChange(String packageName) {
        int i;
        t.a item;
        CirProButton btn;
        if (getRecyclerView() == null || getRecyclerView().getVisibility() != 0) {
            if (this.mTipListView != null && this.mTipListView.getVisibility() == 0) {
                int count = this.mAdapterSearchTip.getCount();
                for (i = 0; i < count; i++) {
                    if (this.mAdapterSearchTip.getItem(i) instanceof AppStructItem) {
                        item = (AppStructItem) this.mAdapterSearchTip.getItem(i);
                        if (!(item == null || TextUtils.isEmpty(item.package_name) || !item.package_name.equals(packageName))) {
                            btn = (CirProButton) this.mTipListView.findViewWithTag(item.package_name);
                            if (btn != null) {
                                this.mViewControler.a(item, null, false, btn);
                                return;
                            } else {
                                this.mAdapterSearchTip.notifyDataSetChanged();
                                return;
                            }
                        }
                    }
                }
            }
        } else if (getRecyclerView().getLayoutManager() != null) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getRecyclerView().getLayoutManager();
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            com.meizu.cloud.app.a.a appListAdapter = (com.meizu.cloud.app.a.a) getRecyclerViewAdapter();
            i = firstVisiblePosition;
            while (i <= lastVisiblePosition) {
                item = (AppStructItem) appListAdapter.c(i);
                if (item == null || TextUtils.isEmpty(item.package_name) || !item.package_name.equals(packageName)) {
                    i++;
                } else {
                    btn = (CirProButton) getRecyclerView().findViewWithTag(item.package_name);
                    if (btn != null) {
                        this.mViewControler.a(item, null, false, btn);
                        return;
                    }
                    return;
                }
            }
        }
    }
}
