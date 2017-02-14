package com.meizu.cloud.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Space;
import com.meizu.cloud.app.downlad.f;
import com.meizu.cloud.app.downlad.f.h;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.g;
import com.meizu.common.widget.CustomButton;

public class SmartPriceButton extends RelativeLayout {
    final int[] a = new int[]{c.theme_color, c.btn_operation_open, c.btn_operation_info_cancel};
    private View b;
    private CustomButton c;
    private Button d;
    private ImageView e;
    private Space f;
    private a g = a.a();

    public static class a {
        boolean a;
        boolean b;
        boolean c;
        l d;
        boolean e;
        com.meizu.cloud.app.core.c f;
        b g;
        boolean h;

        public static a a() {
            a animationAid = new a();
            animationAid.a = true;
            animationAid.b = false;
            animationAid.c = false;
            animationAid.e = true;
            animationAid.f = com.meizu.cloud.app.core.c.NOT_INSTALL;
            animationAid.g = a(animationAid);
            animationAid.h = true;
            return animationAid;
        }

        private static b a(a animationAid) {
            l state = animationAid.d;
            if (state instanceof n) {
                if (state == n.FETCHING || state == n.SUCCESS) {
                    return b.START;
                }
                return b(animationAid);
            } else if (state instanceof j) {
                if (state == j.SUCCESS) {
                    return b.START;
                }
                return b(animationAid);
            } else if (state instanceof f.c) {
                if (state == f.c.TASK_COMPLETED) {
                    return b.WAIT;
                }
                if (state == f.c.TASK_CREATED || state == f.c.TASK_WAITING || state == f.c.TASK_STARTED || state == f.c.TASK_PAUSED) {
                    return b.START;
                }
                return b(animationAid);
            } else if (state instanceof h) {
                if (state == h.PATCHING || state == h.PATCHED_SUCCESS) {
                    return b.WAIT;
                }
                return b(animationAid);
            } else if (!(state instanceof f.f)) {
                return b(animationAid);
            } else {
                if (state == f.f.INSTALL_START || state == f.f.DELETE_START) {
                    return b.WAIT;
                }
                return b(animationAid);
            }
        }

        private static b b(a animationAid) {
            com.meizu.cloud.app.core.c compareResult = animationAid.f;
            if (compareResult == com.meizu.cloud.app.core.c.UPGRADE) {
                return b.NOMAL;
            }
            if (compareResult == com.meizu.cloud.app.core.c.OPEN || compareResult == com.meizu.cloud.app.core.c.BUILD_IN || compareResult == com.meizu.cloud.app.core.c.DOWNGRADE) {
                return b.INSTALLED;
            }
            if (animationAid.a) {
                return b.NOMAL;
            }
            if (animationAid.b) {
                return b.NOMAL;
            }
            return b.NOMAL_FEE;
        }
    }

    public enum b {
        WAIT,
        START,
        INSTALLED,
        PAUSE,
        NOMAL,
        NOMAL_FEE
    }

    public SmartPriceButton(Context context) {
        super(context);
        a(context);
    }

    public SmartPriceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        a(context);
    }

    public SmartPriceButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        a(context);
    }

    private void a(Context context) {
        this.b = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(g.btn_smart_price_button, this);
        this.c = (CustomButton) this.b.findViewById(com.meizu.cloud.b.a.f.tryBtn);
        this.d = (Button) this.b.findViewById(com.meizu.cloud.b.a.f.PriceBackground);
        this.f = (Space) this.b.findViewById(com.meizu.cloud.b.a.f.dummy_space);
        this.e = (ImageView) this.b.findViewById(com.meizu.cloud.b.a.f.bottom_more);
    }

    public synchronized void setDefaultAnimationAid(a animationAid) {
        this.g = animationAid;
    }

    public b getButtonEnum() {
        return this.g.g;
    }
}
