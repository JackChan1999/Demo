package com.meizu.cloud.app.a;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.n.b;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.AppCommentItem;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.utils.d;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.l;
import com.meizu.cloud.app.utils.s;
import com.meizu.cloud.app.widget.CommentListItemView;
import com.meizu.cloud.b.a;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.common.widget.PraiseView;
import com.meizu.common.widget.f;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class n extends f<AppCommentItem> {
    private Context a;
    private Set<String> h;
    private long i;
    private boolean j = false;
    private int k;

    public n(Context context, long appId, int versionCode, List<AppCommentItem>... lists) {
        super(context, lists);
        this.a = context;
        this.i = appId;
        this.k = versionCode;
        d();
    }

    private void d() {
        this.h = s.c(this.a, String.valueOf(this.i));
    }

    protected View a(Context context, int position, int partitionIndex, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(g.block_item_container_v2, parent, false);
    }

    protected void a(View v, Context context, int position, int partitionIndex) {
        TextView title = (TextView) v.findViewById(16908308);
        ImageView more = (ImageView) v.findViewById(a.f.more);
        ((TextView) v.findViewById(16908309)).setVisibility(8);
        more.setVisibility(8);
        title.setSingleLine();
        if (partitionIndex == 0) {
            title.setText(this.a.getString(i.details_comment_hot_title_name));
        } else {
            title.setText(this.a.getString(i.details_comment_new_title_name));
        }
    }

    protected View a(Context context, int position, int partitionIndex, AppCommentItem remoteAppInfo, int positionInGroup, int i4, ViewGroup parent) {
        return positionInGroup == 0 ? new CommentListItemView(context) : new CommentListItemView(context);
    }

    protected void a(View view, Context context, int position, int partitionIndex, AppCommentItem data, int positionInGroup, int i4) {
        boolean z = true;
        if (view instanceof CommentListItemView) {
            boolean z2;
            CommentListItemView itemView = (CommentListItemView) view;
            itemView.setCommentText(data.comment);
            itemView.setDateText(com.meizu.common.util.a.a(this.a, data.create_time, 6));
            itemView.setName(data.user_name);
            itemView.setRateStar((double) data.star);
            if (data.version_code < this.k) {
                z2 = true;
            } else {
                z2 = false;
            }
            itemView.setHistoryVersion(z2);
            h.b(context, data.user_icon, itemView.getmUserIcon());
            a(itemView, data);
            if (data.reply == null) {
                z = false;
            }
            itemView.setReplyLayout(z);
            if (data.reply != null) {
                itemView.setReplyText(data.reply.comment);
                itemView.setReplyDate(com.meizu.common.util.a.a(this.a, data.reply.create_time, 6));
            }
        }
    }

    private void a(CommentListItemView itemView, final AppCommentItem data) {
        final PraiseView iconPraise = (PraiseView) itemView.findViewById(a.f.details_comment_praise_icon);
        final TextView praiseCount = (TextView) itemView.findViewById(a.f.details_comment_praise_count);
        iconPraise.setBackgroundResId(e.ic_praise_on, e.ic_praise_nm);
        praiseCount.setText(String.valueOf(data.like));
        if (this.h == null || !this.h.contains(String.valueOf(data.id))) {
            praiseCount.setTextColor(Integer.MIN_VALUE);
            iconPraise.setBackgroundDrawable(this.a.getResources().getDrawable(e.ic_praise_nm));
        } else {
            praiseCount.setTextColor(-16777216);
            iconPraise.setBackgroundDrawable(this.a.getResources().getDrawable(e.ic_praise_on));
        }
        iconPraise.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ n d;

            public void onClick(View v) {
                if (this.d.h == null || !this.d.h.contains(String.valueOf(data.id))) {
                    iconPraise.setEnabled(false);
                    this.d.a(data, iconPraise, praiseCount);
                    return;
                }
                iconPraise.setEnabled(false);
                com.meizu.cloud.app.utils.a.a(this.d.a, this.d.a.getString(i.details_comment_praise_reclick_remind));
                new Handler().postDelayed(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        iconPraise.setEnabled(true);
                    }
                }, 3000);
            }
        });
    }

    private void a(final AppCommentItem data, final PraiseView iconPraise, final TextView praiseCount) {
        if (!this.j) {
            this.j = true;
            String url = RequestConstants.getRuntimeDomainUrl(this.a, RequestConstants.DETAILS_COMMENT_ADD_PRAISE + data.id);
            List<com.meizu.volley.b.a> params = new ArrayList();
            long time = System.currentTimeMillis();
            String signKey = l.a(data.id + d.a(this.a) + time + new com.meizu.cloud.app.utils.n(com.meizu.cloud.app.fragment.d.m).toString());
            params.add(new com.meizu.volley.b.a("timestamp", String.valueOf(time)));
            params.add(new com.meizu.volley.b.a("sign", signKey));
            com.android.volley.l fastJsonRequest = new FastJsonRequest(new TypeReference<ResultModel<Object>>(this) {
                final /* synthetic */ n a;

                {
                    this.a = r1;
                }
            }, url, params, new b<ResultModel<Object>>(this) {
                final /* synthetic */ n d;

                public void a(ResultModel<Object> response) {
                    if (response == null || response.getCode() != 200) {
                        Log.w("CommentsListAdapter", "Details comment add praised failed");
                    } else {
                        iconPraise.setBackgroundDrawable(this.d.a.getResources().getDrawable(e.ic_praise_on));
                        iconPraise.setState(PraiseView.b.CANCEL);
                        praiseCount.setText(String.valueOf(data.like + 1));
                        praiseCount.setTextColor(-16777216);
                        iconPraise.setEnabled(true);
                        AppCommentItem appCommentItem = data;
                        appCommentItem.like++;
                        if (this.d.h == null) {
                            this.d.h = new HashSet();
                        }
                        this.d.h.add(String.valueOf(data.id));
                        s.a(this.d.a, String.valueOf(this.d.i), this.d.h);
                    }
                    this.d.j = false;
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ n a;

                {
                    this.a = r1;
                }

                public void a(com.android.volley.s error) {
                    Log.w("CommentsListAdapter", "Details comment add praised failed");
                    this.a.j = false;
                }
            });
            fastJsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this.a));
            com.meizu.volley.b.a(this.a).a().a(fastJsonRequest);
        }
    }
}
