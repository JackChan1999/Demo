package com.meizu.cloud.detail.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class PullToZoomListView extends ListView {
    private PullToZoomGroup a;
    private float b;

    public PullToZoomListView(Context context) {
        super(context);
        a();
    }

    public PullToZoomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public PullToZoomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    private void a() {
        post(new Runnable(this) {
            final /* synthetic */ PullToZoomListView a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a = (PullToZoomGroup) this.a.getParent();
            }
        });
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == 0) {
            this.b = ev.getY();
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (this.a == null) {
            return false;
        }
        float yMove = ev.getY();
        switch (ev.getAction()) {
            case 2:
                if (getTranslationY() == 0.0f && getChildCount() > 0 && getChildAt(0).getTop() < 0) {
                    this.a.setGroupState(-1);
                    break;
                } else if (yMove - this.b != 0.0f && getTranslationY() > 0.0f) {
                    this.a.setGroupState(1);
                    return false;
                } else if (yMove - this.b > 0.0f && getTranslationY() >= 0.0f && getChildCount() > 0 && getChildAt(0).getTop() == 0) {
                    getChildAt(0).setTop(0);
                    if (this.a.getGroupState() == -1 || getChildCount() <= 0) {
                        return false;
                    }
                    this.a.setGroupState(1);
                    return false;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
