package com.meizu.cloud.detail.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class GalleryFlow extends Gallery {
    private float a;
    private float b;
    private float c;
    private float d;
    private boolean e;
    private PullToZoomGroup f;
    private int g = 640;
    private int h;

    public GalleryFlow(Context context) {
        super(context);
        a();
    }

    public GalleryFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        a();
    }

    public GalleryFlow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        a();
    }

    protected boolean getChildStaticTransformation(View child, Transformation t) {
        super.getChildStaticTransformation(child, t);
        t.clear();
        return true;
    }

    private void a() {
        this.g = (int) TypedValue.applyDimension(1, (float) this.g, getContext().getResources().getDisplayMetrics());
        post(new Runnable(this) {
            final /* synthetic */ GalleryFlow a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.f = (PullToZoomGroup) this.a.getParent().getParent();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.f == null) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
                this.a = event.getY();
                this.c = event.getX();
                this.e = false;
                this.h = getSelectedItemPosition();
                break;
            case 2:
                this.b = event.getY();
                this.d = event.getX();
                if (Math.abs(this.b - this.a) > Math.abs(this.d - this.c) && Math.abs(this.b - this.a) > 10.0f && !this.e) {
                    if (getHeight() >= this.g && this.b > this.a) {
                        this.a = event.getY();
                        break;
                    }
                    this.f.setGroupState(1);
                    return false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (Math.abs(distanceX) > 10.0f && this.f.getGroupState() == 2) {
            this.e = true;
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
    }

    private boolean a(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        this.e = true;
        if (getSelectedItemPosition() == this.h) {
            int kEvent;
            if (a(e1, e2)) {
                kEvent = 21;
            } else {
                kEvent = 22;
            }
            onKeyDown(kEvent, null);
        }
        return true;
    }
}
