package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import com.meizu.cloud.app.widget.BackTopView.b;

public class BackTopListview extends ListView {
    public Boolean a;
    public BackTopView b;
    float c;
    a d;
    private PointF e;

    public enum a {
        CommonMode,
        BackTopMode
    }

    public BackTopListview(Context context) {
        this(context, null);
        this.e = new PointF();
    }

    public BackTopListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.a = Boolean.valueOf(false);
        this.d = a.CommonMode;
        this.e = new PointF();
    }

    public BackTopListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.a = Boolean.valueOf(false);
        this.d = a.CommonMode;
        this.e = new PointF();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.d == a.BackTopMode && this.b == null && (getChildAt(getChildCount() - 1) instanceof BackTopView)) {
            this.b = (BackTopView) getChildAt(getChildCount() - 1);
            this.b.setVisibility(0);
            setSelection(getCount() - 1);
            this.b.setOnTopPicListener(new b(this) {
                final /* synthetic */ BackTopListview a;

                {
                    this.a = r1;
                }

                public void a(View view) {
                    this.a.setSelection(0);
                    this.a.a = Boolean.valueOf(false);
                }
            });
        }
        if (this.d != a.BackTopMode || !this.a.booleanValue()) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case 0:
                return super.onTouchEvent(ev);
            case 1:
                if (this.b != null) {
                    this.b.setFrameAnimOrigin();
                }
                return super.onTouchEvent(ev);
            case 2:
                this.c = this.e.y - ev.getRawY();
                if (this.c < 0.0f && (this.b == null || this.b.q == BackTopView.a.COMMON)) {
                    return super.onTouchEvent(ev);
                }
                if (this.b != null && this.a.booleanValue()) {
                    this.b.setFrameAnimHeigth(this.c);
                    if (this.b.q == BackTopView.a.OPEN) {
                        setSelection(getBottom());
                    }
                }
                return super.onTouchEvent(ev);
            default:
                return super.onTouchEvent(ev);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                this.e.set(ev.getRawX(), ev.getRawY());
                return super.onInterceptTouchEvent(ev);
            default:
                return super.onInterceptTouchEvent(ev);
        }
    }

    public a getListViewMode() {
        return this.d;
    }

    public void setListViewMode(a listViewMode) {
        this.d = listViewMode;
    }

    public Boolean getmIsBottom() {
        return this.a;
    }

    public void setmIsBottom(Boolean mIsBottom) {
        this.a = mIsBottom;
    }

    public BackTopView getBackTopView() {
        return this.b;
    }

    public void setBackTopView(BackTopView backTopView) {
        this.b = backTopView;
    }
}
