package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;

public class DivergingLightImageView extends ImageView {
    private AbsoluteLayout a;
    private WindowManager b;
    private b c;
    private boolean d;
    private int e;
    private int f;
    private int g;
    private boolean h = true;
    private int i = e.ic_praise_on;
    private long j = 1000;
    private float[] k = new float[]{1.0f, 0.88404197f, 0.7459967f, 0.6366648f, 0.54776365f, 0.47321922f, 0.41027057f, 0.35560465f, 0.30811706f, 0.2661513f, 0.22970735f, 0.19657648f, 0.16896743f, 0.14411928f, 0.122032024f, 0.10270569f, 0.08614025f, 0.07178354f, 0.05908338f, 0.048591938f, 0.04030922f, 0.033683047f, 0.028161237f, 0.023743788f, 0.01987852f, 0.016565433f, 0.013252347f, 0.010491441f, 0.008282717f, 0.006073992f, 0.003865268f, 0.002760906f, 0.001656543f, 5.52181E-4f, 0.0f, 0.0f, 0.0f, 5.52181E-4f, 0.001104362f, 0.002208724f, 0.003865268f, 0.006073992f, 0.008282717f, 0.011043622f, 0.014356709f, 0.018221976f, 0.022639425f, 0.028161237f, 0.036443952f, 0.050800662f, 0.07067918f, 0.097183876f, 0.13031474f, 0.17062396f, 0.21811154f, 0.274434f, 0.3390392f, 0.41413584f, 0.49917173f, 0.5858642f};
    private float[] l = new float[]{0.0f, 1.2f, 2.9f, 4.7f, 6.7f, 9.0f, 11.4f, 14.1f, 17.1f, 20.4f, 24.0f, 28.1f, 32.7f, 37.6f, 42.5f, 47.3f, 52.0f, 56.7f, 61.4f, 66.0f, 70.6f, 75.1f, 79.6f, 84.1f, 88.5f, 92.9f, 97.2f, 101.6f, 105.9f, 110.1f, 114.1f, 118.6f, 122.9f, 127.1f, 131.3f, 135.6f, 139.8f, 144.0f, 148.0f, 151.6f, 154.9f, 157.8f, 160.5f, 162.9f, 165.2f, 167.3f, 169.1f, 170.9f, 172.5f, 174.0f, 175.3f, 176.5f, 177.6f, 178.5f, 179.4f, 180.0f, 180.0f, 180.0f, 180.0f, 180.0f};
    private float[] m = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.99f, 0.98f, 0.96f, 0.93f, 0.89f, 0.84f, 0.77f, 0.68f, 0.55f, 0.36f, 0.0f};
    private a n;

    public interface a {
        void a();

        void b();
    }

    public DivergingLightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context, attrs);
    }

    private void a(Context context, AttributeSet attrs) {
        this.e = e.ic_praise_on;
        this.b = (WindowManager) context.getSystemService("window");
        this.g = getResources().getDimensionPixelOffset(d.like_anim_height);
        this.c = new b(null, this.k, null, this.m, this.l, 0.5f, 0.5f, true, true);
        this.c.setDuration(this.j);
        this.c.setFillAfter(true);
        this.c.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ DivergingLightImageView a;

            {
                this.a = r1;
            }

            public void onAnimationStart(Animation animation) {
                if (this.a.n != null) {
                    this.a.n.a();
                }
                this.a.d = true;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (this.a.n != null) {
                    this.a.n.b();
                }
                this.a.setVisibility(0);
                this.a.a();
                this.a.d = false;
            }
        });
    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (this.h) {
            this.h = false;
        } else {
            this.f = resId;
        }
    }

    public void setAnimationImageRes(int resId) {
        this.i = resId;
    }

    protected void a() {
        this.b.removeView(this.a);
    }

    public void setAnimateListener(a listener) {
        this.n = listener;
    }
}
