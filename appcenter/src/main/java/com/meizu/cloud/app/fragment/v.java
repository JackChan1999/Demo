package com.meizu.cloud.app.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.s;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.FastJsonParseRequest;
import com.meizu.cloud.app.request.JSONUtils;
import com.meizu.cloud.app.request.ParseListener;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.RequestManager;
import com.meizu.cloud.app.request.model.AppCommentItem;
import com.meizu.cloud.app.request.model.CommentCatItem;
import com.meizu.cloud.app.request.model.CommentCategoryInfo;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo.Columns;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.utils.t;
import com.meizu.cloud.app.widget.GridLayoutForGridView;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.download.c.h;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class v implements OnRatingBarChangeListener, com.android.volley.n.a, com.android.volley.n.b {
    private boolean A = false;
    private List<String> B;
    private Handler C = new Handler(this) {
        final /* synthetic */ v a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    if (m.b(this.a.a)) {
                        Toast.makeText(this.a.a, this.a.a.getString(i.add_comment_server_error), 0).show();
                    } else {
                        com.meizu.cloud.app.utils.a.a(this.a.a, this.a.a.getString(i.nonetwork));
                    }
                    this.a.s.a(2, null);
                    return;
                case 1:
                    int code = msg.arg1;
                    String message = msg.obj;
                    if (code == 200) {
                        AppCommentItem appCommentItem = new AppCommentItem();
                        appCommentItem.star = this.a.g;
                        this.a.s.a(0, appCommentItem);
                        return;
                    } else if (code == RequestConstants.CODE_APP_NOT_FOUND || code == 123100 || code == 123101 || code == 123102 || code == 123103) {
                        Toast.makeText(this.a.a, message, 0).show();
                        this.a.s.a(2, null);
                        return;
                    } else if (code == 123104) {
                        Toast.makeText(this.a.a, this.a.a.getString(i.add_comment_gag), 0).show();
                        this.a.s.a(2, null);
                        return;
                    } else if (code == 123105) {
                        Toast.makeText(this.a.a, this.a.a.getString(i.add_comment_evaluated), 0).show();
                        this.a.s.a(2, null);
                        return;
                    } else if (code == 123106) {
                        Toast.makeText(this.a.a, this.a.a.getString(i.add_comment_uninstall), 0).show();
                        this.a.s.a(2, null);
                        return;
                    } else {
                        Toast.makeText(this.a.a, this.a.a.getString(i.add_comment_server_error), 0).show();
                        this.a.s.a(2, null);
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private Context a;
    private RatingBar b;
    private TextView c;
    private GridLayoutForGridView d;
    private EditText e;
    private int f;
    private int g = 0;
    private String h = "";
    private final int i = 1;
    private final int j = -1;
    private AlertDialog k;
    private Pattern l;
    private boolean m = false;
    private View n;
    private long o = 0;
    private PopupWindow p;
    private boolean q = false;
    private Button r;
    private a s;
    private float t;
    private List<CommentCatItem> u;
    private boolean v;
    private boolean w;
    private int x;
    private boolean y = false;
    private boolean z = false;

    public interface a {
        void a(int i, AppCommentItem appCommentItem);
    }

    public interface c {
        void a(int i, CommentCatItem commentCatItem, View view);
    }

    public class b extends BaseAdapter {
        final /* synthetic */ v a;
        private Context b;
        private List<CommentCatItem> c;
        private TextView d;
        private TextView e;
        private int f = -1;
        private c g;

        public b(v vVar, Context context, List<CommentCatItem> listData) {
            this.a = vVar;
            this.b = context;
            this.c = listData;
        }

        public void a(c onOneStarSelectedListener) {
            this.g = onOneStarSelectedListener;
        }

        public int getCount() {
            if (this.c != null) {
                return this.c.size();
            }
            return 0;
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

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(this.b).inflate(g.comment_onestar_itemview, null);
            }
            TextView textView = (TextView) convertView.findViewById(f.comment_onestar_resaon);
            textView.setTextColor(this.b.getResources().getColor(com.meizu.cloud.b.a.c.comment_onestar_text_unchecked));
            textView.setText(((CommentCatItem) this.c.get(position)).title);
            textView.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ b b;

                public void onClick(View v) {
                    this.b.e = (TextView) v;
                    this.b.f = position;
                    if (this.b.d != null) {
                        this.b.d.setTextColor(this.b.b.getResources().getColor(com.meizu.cloud.b.a.c.comment_onestar_text_unchecked));
                        this.b.d.setSelected(false);
                    }
                    this.b.e.setTextColor(this.b.b.getResources().getColor(com.meizu.cloud.b.a.c.comment_onestar_text_checked));
                    this.b.e.setSelected(true);
                    this.b.d = this.b.e;
                    if (this.b.g != null) {
                        this.b.g.a(position, (CommentCatItem) this.b.a.u.get(position), v);
                    }
                }
            });
            return convertView;
        }
    }

    public void a(Object response) {
        ResultModel model = (ResultModel) response;
        this.C.sendMessage(this.C.obtainMessage(1, model.getCode(), 0, model.getMessage()));
        this.q = false;
    }

    public void a(s error) {
        this.C.sendMessage(this.C.obtainMessage(-1));
        this.q = false;
    }

    public v(Context context, int app_id, a commentResultListener) {
        this.a = context;
        this.f = app_id;
        this.s = commentResultListener;
    }

    public void a(float rating) {
        this.t = rating;
        if (this.b != null) {
            this.b.setRating(rating);
        }
    }

    public static ProgressDialog a(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(0);
        progressDialog.setMessage(context.getString(i.wait_dialog_remind));
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public void a() {
        if (!((FragmentActivity) this.a).isDestroyed()) {
            this.n = ((FragmentActivity) this.a).getLayoutInflater().inflate(g.add_comment_layout, null);
            this.c = (TextView) this.n.findViewById(f.comment_text);
            this.d = (GridLayoutForGridView) this.n.findViewById(f.comment_onestar_gridview);
            this.b = (RatingBar) this.n.findViewById(f.RatingBar01);
            this.e = (EditText) this.n.findViewById(f.CommentEdit);
            if (this.u != null) {
                b gridViewAdapter = new b(this, this.a, this.u);
                gridViewAdapter.a(new c(this) {
                    final /* synthetic */ v a;

                    {
                        this.a = r1;
                    }

                    public void a(int position, CommentCatItem commentCatItem, View v) {
                        if (commentCatItem != null) {
                            this.a.x = commentCatItem.id;
                            this.a.a(true);
                            this.a.y = true;
                        }
                    }
                });
                this.d.setAdapter(gridViewAdapter);
                this.d.a();
            }
            Builder builder = new Builder(this.a);
            this.e.setFilters(new InputFilter[]{new InputFilter(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }

                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    if (dest.length() >= 120) {
                        return "";
                    }
                    if (!this.a.m || source == null || source.length() <= 0) {
                        return source;
                    }
                    if (this.a.o == 0) {
                        this.a.o = System.currentTimeMillis();
                        t.a(this.a.a, this.a.a.getString(i.add_comment_sensitive_words_disable_input), 0, 0);
                    } else if (System.currentTimeMillis() - this.a.o > 3000) {
                        this.a.o = System.currentTimeMillis();
                        t.a(this.a.a, this.a.a.getString(i.add_comment_sensitive_words_disable_input), 0, 0);
                    }
                    return "";
                }
            }});
            this.e.addTextChangedListener(new TextWatcher(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() <= 0 || this.a.g <= 0) {
                        this.a.a(false);
                    } else if (s.toString().trim().length() > 0) {
                        this.a.a(true);
                    } else {
                        this.a.a(false);
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(final Editable s) {
                    if (this.a.l != null) {
                        long startTime = System.currentTimeMillis();
                        final String inputStr = s.toString();
                        final Matcher matcher = this.a.l.matcher(inputStr);
                        s.setSpan(new ForegroundColorSpan(this.a.a.getResources().getColor(com.meizu.cloud.b.a.c.add_comment_edittext_color)), 0, s.length(), 33);
                        this.a.m = false;
                        if (!this.a.A) {
                            this.a.A = true;
                            new Thread(this) {
                                final /* synthetic */ AnonymousClass12 d;

                                public void run() {
                                    if (this.d.a.a == null || this.d.a.k == null || !this.d.a.k.isShowing()) {
                                        this.d.a.A = false;
                                        return;
                                    }
                                    if (this.d.a.B != null) {
                                        this.d.a.B.clear();
                                    }
                                    while (matcher.find()) {
                                        String senWord = matcher.group();
                                        if (!TextUtils.isEmpty(senWord)) {
                                            if (this.d.a.B == null) {
                                                this.d.a.B = new ArrayList();
                                            }
                                            this.d.a.B.add(senWord);
                                        } else {
                                            return;
                                        }
                                    }
                                    ((FragmentActivity) this.d.a.a).runOnUiThread(new Runnable(this) {
                                        final /* synthetic */ AnonymousClass1 a;

                                        {
                                            this.a = r1;
                                        }

                                        public void run() {
                                            if (this.a.d.a.a == null || this.a.d.a.k == null || !this.a.d.a.k.isShowing()) {
                                                this.a.d.a.A = false;
                                            } else if (this.a.d.a.B == null || this.a.d.a.B.size() <= 0) {
                                                this.a.d.a.A = false;
                                            } else {
                                                ArrayList<String> currentList = new ArrayList();
                                                currentList.addAll(this.a.d.a.B);
                                                if (currentList.size() > 0) {
                                                    Iterator i$ = currentList.iterator();
                                                    while (i$.hasNext()) {
                                                        String senWord = (String) i$.next();
                                                        int st = inputStr.lastIndexOf(senWord);
                                                        int ed = st + senWord.length();
                                                        try {
                                                            if (s.length() < ed) {
                                                                ed = s.length();
                                                            }
                                                            s.setSpan(new ForegroundColorSpan(-65536), st, ed, 33);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        this.a.d.a.a(false);
                                                        this.a.d.a.m = true;
                                                        TextPaint textPaint = this.a.d.a.e.getPaint();
                                                        int lineCount = this.a.d.a.e.getLineCount();
                                                        int lineIndex = this.a.d.a.e.getLayout().getLineForOffset(st);
                                                        int lineOffset = this.a.d.a.e.getLayout().getOffsetForHorizontal(lineIndex, 0.0f);
                                                        float moveWidth = textPaint.measureText(inputStr, lineOffset, (senWord.length() / 2) + st);
                                                        float lineH = (float) this.a.d.a.e.getLineHeight();
                                                        int startIndex = 0;
                                                        if (lineCount > 3) {
                                                            startIndex = lineCount - 3;
                                                        }
                                                        int lineAddHeight = 0;
                                                        if (lineIndex - startIndex > 0) {
                                                            lineAddHeight = (int) (((float) (lineIndex - startIndex)) * lineH);
                                                        }
                                                        if (this.a.d.a.p == null) {
                                                            this.a.d.a.e();
                                                            this.a.d.a.p.showAtLocation(this.a.d.a.n, 0, 0, 0);
                                                        }
                                                        final int popX = (int) (((float) this.a.d.a.a.getResources().getDimensionPixelOffset(d.comment_sensitive_remind_view_marginleft)) + moveWidth);
                                                        final int popY = (((int) (this.a.d.a.e.getY() + ((float) this.a.d.a.a.getResources().getDimensionPixelSize(d.add_comment_edittext_margin_top)))) + this.a.d.a.a.getResources().getDimensionPixelOffset(d.comment_sensitive_remind_view_marginTop)) + lineAddHeight;
                                                        this.a.d.a.p.getContentView().post(new Runnable(this) {
                                                            final /* synthetic */ AnonymousClass1 c;

                                                            public void run() {
                                                                if (this.c.a.d.a.p.isShowing()) {
                                                                    this.c.a.d.a.p.dismiss();
                                                                }
                                                                this.c.a.d.a.p.showAtLocation(this.c.a.d.a.n, 0, popX - (this.c.a.d.a.p.getContentView().getWidth() / 2), popY);
                                                            }
                                                        });
                                                    }
                                                }
                                                if (!(this.a.d.a.p == null || !this.a.d.a.p.isShowing() || this.a.d.a.m)) {
                                                    this.a.d.a.p.dismiss();
                                                }
                                                this.a.d.a.A = false;
                                            }
                                        }
                                    });
                                }
                            }.start();
                        }
                        if (!(this.a.p == null || !this.a.p.isShowing() || this.a.m)) {
                            this.a.p.dismiss();
                        }
                        Log.i("Matcher", "find time: " + (System.currentTimeMillis() - startTime));
                    }
                }
            });
            builder.setView(this.n);
            builder.setPositiveButton(this.a.getString(i.submit), null);
            builder.setNegativeButton(this.a.getString(i.cancel), new DialogInterface.OnClickListener(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }

                public void onClick(DialogInterface dialog, int whichButton) {
                    this.a.k.cancel();
                }
            });
            builder.setOnCancelListener(new OnCancelListener(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }

                public void onCancel(DialogInterface dialogInterface) {
                    this.a.s.a(1, null);
                }
            });
            this.b.setOnRatingBarChangeListener(this);
            this.b.setRating(this.t);
            this.k = builder.show();
            this.r = this.k.getButton(-1);
            try {
                com.meizu.cloud.c.b.a.c.a().a(this.k.getClass(), "setButtonTextColor", Integer.TYPE, Integer.TYPE).invoke(this.k, new Object[]{Integer.valueOf(-1), Integer.valueOf(com.meizu.cloud.b.a.c.theme_color)});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            } catch (IllegalAccessException e3) {
                e3.printStackTrace();
            }
            a(false);
            this.r.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.h = this.a.e.getText().toString().replaceAll("\n", " ");
                    if (this.a.z || this.a.g != 10 || this.a.u == null || this.a.u.size() <= 0) {
                        if (this.a.l != null) {
                            String endStr = this.a.e.getText().toString();
                            if (this.a.l.matcher(endStr).find()) {
                                this.a.e.setText(endStr);
                                this.a.e.setSelection(endStr.length());
                                return;
                            }
                        }
                        this.a.b();
                        this.a.k.dismiss();
                        return;
                    }
                    this.a.z = true;
                    this.a.a(this.a.a.getString(i.submit));
                    if (TextUtils.isEmpty(this.a.h)) {
                        this.a.a(false);
                    } else {
                        this.a.a(true);
                    }
                    this.a.d.setVisibility(8);
                    this.a.e.setVisibility(0);
                }
            });
        }
    }

    private void e() {
        this.p = new PopupWindow(((FragmentActivity) this.a).getLayoutInflater().inflate(g.sensitive_words_remind_popview, null), -2, (int) this.a.getResources().getDimension(d.comment_sensitive_remind_view_height), false);
    }

    private void a(boolean enabled) {
        if (this.r != null) {
            Button button = this.r;
            boolean z = enabled && this.b.getRating() > 0.0f;
            button.setEnabled(z);
        }
    }

    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        b(rating);
        this.z = false;
        this.g = (int) (10.0f * rating);
        if (this.g != 10 || this.u == null || this.u.size() <= 0) {
            if (this.e.getText().length() <= 0 || this.g <= 0) {
                a(false);
            } else if (this.e.getText().toString().trim().length() <= 0 || this.m) {
                a(false);
            } else {
                a(true);
            }
        } else if (this.y) {
            a(true);
        } else {
            a(false);
        }
    }

    private void a(String str) {
        if (this.r != null) {
            this.r.setText(str);
        }
    }

    private void b(float rating) {
        switch ((int) rating) {
            case 1:
                this.c.setText(i.add_comment_onestar_content);
                if (this.u == null || this.u.size() <= 0) {
                    this.e.setVisibility(0);
                    this.d.setVisibility(8);
                    a(this.a.getString(i.submit));
                    return;
                }
                this.e.setVisibility(8);
                this.d.setVisibility(0);
                a(this.a.getString(i.next));
                return;
            case 2:
                this.c.setText(i.add_comment_twostar_content);
                a(this.a.getString(i.submit));
                this.e.setVisibility(0);
                this.d.setVisibility(8);
                return;
            case 3:
                this.c.setText(i.add_comment_threestar_content);
                a(this.a.getString(i.submit));
                this.e.setVisibility(0);
                this.d.setVisibility(8);
                return;
            case 4:
                this.c.setText(i.add_comment_fourstar_content);
                a(this.a.getString(i.submit));
                this.e.setVisibility(0);
                this.d.setVisibility(8);
                return;
            case 5:
                this.c.setText(i.add_comment_fivestar_content);
                a(this.a.getString(i.submit));
                this.e.setVisibility(0);
                this.d.setVisibility(8);
                return;
            default:
                this.c.setText(i.add_comment_fivestar_content);
                a(this.a.getString(i.submit));
                this.e.setVisibility(0);
                this.d.setVisibility(8);
                return;
        }
    }

    public void b() {
        if (!this.q) {
            this.q = true;
            String addCommentUrl = RequestConstants.getRuntimeDomainUrl(this.a, RequestConstants.ADD_COMMENTS);
            List<com.meizu.volley.b.a> params = new ArrayList();
            if (x.b(this.a)) {
                params.add(new com.meizu.volley.b.a("game_id", "" + this.f));
            } else {
                params.add(new com.meizu.volley.b.a("app_id", "" + this.f));
            }
            params.add(new com.meizu.volley.b.a("star", "" + this.g));
            params.add(new com.meizu.volley.b.a("comment", "" + this.h));
            params.add(new com.meizu.volley.b.a(RequestManager.FIRMWARE, "" + VERSION.RELEASE));
            if (this.g == 10) {
                params.add(new com.meizu.volley.b.a(Columns.CATEGORY_ID, String.valueOf(this.x)));
            }
            l request = new com.meizu.volley.a.b(this.a, new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }
            }, addCommentUrl, params, this, this);
            request.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(request);
        }
    }

    public void c() {
        if (this.l == null) {
            final String url = RequestConstants.getRuntimeDomainUrl(this.a, RequestConstants.SENSITIVE_WORDS);
            if (com.meizu.cloud.app.utils.s.a(this.a, url, System.currentTimeMillis())) {
                l fastJsonParseRequest = new FastJsonParseRequest(url, new ParseListener(this) {
                    final /* synthetic */ v b;

                    public Object onParseResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            ResultModel<Object> resultModel = JSONUtils.parseResultModel(response, new TypeReference<ResultModel<Object>>(this) {
                                final /* synthetic */ AnonymousClass17 a;

                                {
                                    this.a = r1;
                                }
                            });
                            if (!(resultModel.getCode() != 200 || resultModel.getValue() == null || h.c((String) resultModel.getValue()))) {
                                com.meizu.cloud.app.utils.s.a(this.b.a, url, response, System.currentTimeMillis());
                                return resultModel;
                            }
                        }
                        return null;
                    }
                }, new com.android.volley.n.b<ResultModel<Object>>(this) {
                    final /* synthetic */ v b;

                    public void a(ResultModel<Object> response) {
                        if (response == null) {
                            this.b.b(url);
                        } else if (response.getCode() != 200 || response.getValue() == null) {
                            this.b.b(url);
                        } else {
                            String sensitiveWords = (String) response.getValue();
                            if (h.c(sensitiveWords)) {
                                this.b.b(url);
                                return;
                            }
                            this.b.l = Pattern.compile(sensitiveWords);
                            this.b.v = true;
                            if (this.b.w) {
                                this.b.a();
                            }
                        }
                    }
                }, new com.android.volley.n.a(this) {
                    final /* synthetic */ v b;

                    public void a(s error) {
                        this.b.b(url);
                    }
                });
                fastJsonParseRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
                com.meizu.volley.b.a(this.a).a().a(fastJsonParseRequest);
                return;
            }
            b(url);
        } else if (this.k != null) {
            this.k.show();
        }
    }

    private void b(String url) {
        String json = com.meizu.cloud.app.utils.s.b(this.a, url);
        if (TextUtils.isEmpty(json)) {
            Log.w("CommentAppManager", "load sensitive words failed !");
        } else {
            ResultModel<Object> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }
            });
            if (resultModel.getCode() != 200 || resultModel.getValue() == null) {
                Log.w("CommentAppManager", "load sensitive words failed !");
            } else {
                String sensitiveWords = (String) resultModel.getValue();
                if (h.c(sensitiveWords)) {
                    Log.w("CommentAppManager", "load sensitive words failed !");
                } else {
                    this.l = Pattern.compile(sensitiveWords);
                }
            }
        }
        this.v = true;
        if (this.w) {
            a();
        }
    }

    public void d() {
        if (this.u == null) {
            final String url = RequestConstants.getRuntimeDomainUrl(this.a, RequestConstants.COMMENT_ONESTAR_CATEGORY);
            if (com.meizu.cloud.app.utils.s.a(this.a, url, 0, 6, System.currentTimeMillis())) {
                TypeReference<ResultModel<CommentCategoryInfo<CommentCatItem>>> typef = new TypeReference<ResultModel<CommentCategoryInfo<CommentCatItem>>>(this) {
                    final /* synthetic */ v a;

                    {
                        this.a = r1;
                    }
                };
                List<com.meizu.volley.b.a> params = new ArrayList();
                params.add(new com.meizu.volley.b.a("start", String.valueOf(0)));
                params.add(new com.meizu.volley.b.a("max", String.valueOf(6)));
                l fastJsonParseRequest = new FastJsonParseRequest(url, 0, (List) params, new ParseListener(this) {
                    final /* synthetic */ v b;

                    public Object onParseResponse(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            ResultModel<CommentCategoryInfo<CommentCatItem>> resultModel = JSONUtils.parseResultModel(response, new TypeReference<ResultModel<CommentCategoryInfo<CommentCatItem>>>(this) {
                                final /* synthetic */ AnonymousClass6 a;

                                {
                                    this.a = r1;
                                }
                            });
                            if (!(resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null || ((CommentCategoryInfo) resultModel.getValue()).data == null)) {
                                com.meizu.cloud.app.utils.s.a(this.b.a, url, response, 0, 6, System.currentTimeMillis());
                                return resultModel;
                            }
                        }
                        return null;
                    }
                }, new com.android.volley.n.b<ResultModel<CommentCategoryInfo<CommentCatItem>>>(this) {
                    final /* synthetic */ v b;

                    public void a(ResultModel<CommentCategoryInfo<CommentCatItem>> response) {
                        if (response == null) {
                            this.b.a(url, 0, 6);
                        } else if (response == null || response.getCode() != 200 || response.getValue() == null || ((CommentCategoryInfo) response.getValue()).data == null) {
                            this.b.a(url, 0, 6);
                        } else {
                            this.b.u = ((CommentCategoryInfo) response.getValue()).data;
                            this.b.w = true;
                            if (this.b.v) {
                                this.b.a();
                            }
                        }
                    }
                }, new com.android.volley.n.a(this) {
                    final /* synthetic */ v b;

                    public void a(s error) {
                        this.b.a(url, 0, 6);
                    }
                });
                fastJsonParseRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
                com.meizu.volley.b.a(this.a).a().a(fastJsonParseRequest);
                return;
            }
            a(url, 0, 6);
        } else if (this.k != null) {
            this.k.show();
        }
    }

    private void a(String url, int start, int max) {
        String json = com.meizu.cloud.app.utils.s.a(this.a, url, start, max);
        if (TextUtils.isEmpty(json)) {
            Log.w("CommentAppManager", "load comment one star category failed !");
        } else {
            ResultModel<CommentCategoryInfo<CommentCatItem>> resultModel = JSONUtils.parseResultModel(json, new TypeReference<ResultModel<CommentCategoryInfo<CommentCatItem>>>(this) {
                final /* synthetic */ v a;

                {
                    this.a = r1;
                }
            });
            if (resultModel == null || resultModel.getCode() != 200 || resultModel.getValue() == null || ((CommentCategoryInfo) resultModel.getValue()).data == null) {
                Log.w("CommentAppManager", "load comment one star category failed !");
            } else {
                this.u = ((CommentCategoryInfo) resultModel.getValue()).data;
            }
        }
        this.w = true;
        if (this.v) {
            a();
        }
    }
}
