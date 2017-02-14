package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;

public class CommentListItemView extends FrameLayout {
    private TextView a;
    private TextView b;
    private TextView c;
    private BaseStarRateWidget d;
    private TextView e;
    private LinearLayout f;
    private TextView g;
    private TextView h;
    private CircleImageView i;

    public CommentListItemView(Context context) {
        super(context);
        a(context);
    }

    public CommentListItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    private void a(Context context) {
        View v = LayoutInflater.from(context).inflate(g.comment_item_layout, this);
        this.a = (TextView) v.findViewById(f.Author);
        this.c = (TextView) v.findViewById(f.SendDate);
        this.b = (TextView) v.findViewById(f.historyVersion);
        this.d = (BaseStarRateWidget) v.findViewById(f.starRate);
        this.e = (TextView) v.findViewById(f.CommentText);
        this.h = (TextView) v.findViewById(f.reply_time);
        this.g = (TextView) v.findViewById(f.reply_text);
        this.f = (LinearLayout) v.findViewById(f.reply_layout);
        this.i = (CircleImageView) v.findViewById(f.usericon);
    }

    public void setName(String name) {
        this.a.setText(name);
    }

    public void setDateText(String date) {
        this.c.setText(date);
    }

    public void setHistoryVersion(boolean bHistoryVersion) {
        this.b.setVisibility(bHistoryVersion ? 0 : 8);
    }

    public void setRateStar(double level) {
        this.d.setValue(level / 10.0d);
    }

    public void setCommentText(String commentText) {
        this.e.setText(commentText);
    }

    public void setReplyLayout(boolean bHadReply) {
        this.f.setVisibility(bHadReply ? 0 : 8);
    }

    public void setReplyText(String replyText) {
        this.g.setText(replyText);
    }

    public void setReplyDate(String replyDate) {
        this.h.setText(String.format(getContext().getString(i.developer_reply), new Object[]{replyDate}));
    }

    public CircleImageView getmUserIcon() {
        return this.i;
    }
}
