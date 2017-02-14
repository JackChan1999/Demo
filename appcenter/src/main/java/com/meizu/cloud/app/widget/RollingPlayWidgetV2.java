package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager.f;
import android.support.v4.view.ad;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.RollingPlayStructItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.g;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class RollingPlayWidgetV2 extends RelativeLayout {
    ChildViewPager a;
    View b;
    b c;
    private Handler d = null;
    private Runnable e = null;
    private boolean f = false;
    private int g = 0;
    private int h;
    private SoftReference<RollingPlayStructItem> i;
    private a j;

    public interface a {
        void onClickAd(AppAdStructItem appAdStructItem);
    }

    class b extends ad implements f {
        Context a;
        ArrayList<AbstractStrcutItem> b = new ArrayList();
        int c = -1;
        final /* synthetic */ RollingPlayWidgetV2 d;

        public void b(int height) {
            this.c = height;
        }

        public b(RollingPlayWidgetV2 rollingPlayWidgetV2, Context context, ArrayList<AbstractStrcutItem> items) {
            this.d = rollingPlayWidgetV2;
            this.a = context;
            if (items != null) {
                this.b = items;
            }
        }

        public void a(ArrayList<AbstractStrcutItem> items) {
            if (items == null) {
                return;
            }
            if (items.size() > 9) {
                this.b = new ArrayList();
                for (int i = 0; i < 9; i++) {
                    this.b.add(items.get(i));
                }
                return;
            }
            this.b = items;
        }

        public int b() {
            if (this.b.size() == 1) {
                return 1;
            }
            return 5040;
        }

        public boolean a(View view, Object object) {
            return view == object;
        }

        public void a(ViewGroup container, int position, Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
            }
        }

        public Object a(ViewGroup container, int position) {
            View v = LayoutInflater.from(this.a).inflate(g.rollingplay_item, null);
            v.setTag(Integer.valueOf(position));
            position %= this.b.size();
            ImageView image = (ImageView) v.findViewById(com.meizu.cloud.b.a.f.image);
            if (this.c > 0) {
                image.getLayoutParams().height = this.c;
            }
            final AbstractStrcutItem item = (AbstractStrcutItem) this.b.get(position);
            TextView tagView = (TextView) v.findViewById(com.meizu.cloud.b.a.f.image_tag);
            if (!(item instanceof AppAdStructItem) || TextUtils.isEmpty(((AppAdStructItem) item).tag) || TextUtils.isEmpty(((AppAdStructItem) item).tag_color)) {
                tagView.setVisibility(8);
            } else {
                tagView.setText(((AppAdStructItem) item).tag);
                int color = this.a.getResources().getColor(c.theme_color);
                try {
                    color = Color.parseColor(((AppAdStructItem) item).tag_color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tagView.setBackgroundColor(color);
                tagView.setVisibility(0);
            }
            ImageView rippleView = (ImageView) v.findViewById(com.meizu.cloud.b.a.f.image_bg);
            if (VERSION.SDK_INT >= 21) {
                rippleView.setBackgroundResource(e.mz_item_image_background);
            }
            rippleView.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ b b;

                public void onClick(View v) {
                    if (this.b.d.j != null) {
                        this.b.d.j.onClickAd((AppAdStructItem) item);
                    }
                }
            });
            rippleView.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }

                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 0) {
                        this.a.d.a();
                    } else if (event.getAction() == 1 || event.getAction() == 3) {
                        this.a.d.b();
                    }
                    return false;
                }
            });
            h.a(this.d.getContext(), item.coverUrl, image, this.d.getContext().getResources().getDimensionPixelSize(d.block_rolling_play_item_image_radius), 0);
            container.addView(v);
            return v;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void a(int position) {
            if (b() > 1) {
                this.d.g = position;
                if (!(this.d.i == null || this.d.i.get() == null)) {
                    ((RollingPlayStructItem) this.d.i.get()).setPosition(this.d.g);
                }
                this.d.h = position % this.b.size();
                if (position == 0) {
                    this.d.a(b() / 2, 250, false);
                } else if (position == b() - 1) {
                    this.d.a((b() / 2) - 1, 250, false);
                } else {
                    this.d.a(position + 1, 5000, true);
                }
            }
            AbstractStrcutItem abstractStrcutItem = (AbstractStrcutItem) this.b.get(position % this.b.size());
            if (abstractStrcutItem != null && (abstractStrcutItem instanceof AppAdStructItem) && !abstractStrcutItem.is_uxip_exposured) {
                com.meizu.cloud.statistics.b.a().a("block_exposure", abstractStrcutItem.cur_page, com.meizu.cloud.statistics.c.a((AppAdStructItem) abstractStrcutItem));
                abstractStrcutItem.is_uxip_exposured = true;
            }
        }

        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                this.d.b();
            }
            if (state == 1) {
                this.d.a();
            }
        }

        public void b(View container, int position, Object object) {
            ((View) object).setId(position);
        }
    }

    public RollingPlayWidgetV2(Context context, ArrayList<AbstractStrcutItem> items) {
        super(context);
        a((ArrayList) items);
    }

    public RollingPlayWidgetV2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void a(ArrayList<AbstractStrcutItem> items) {
        this.d = new Handler(Looper.getMainLooper());
        Context context = getContext();
        View v = LayoutInflater.from(context).inflate(g.rollingplay_widget_layout, this, true);
        ChildViewPager viewPager = (ChildViewPager) v.findViewById(com.meizu.cloud.b.a.f.viewPager);
        this.b = v.findViewById(com.meizu.cloud.b.a.f.tab_container_background);
        this.a = viewPager;
        int size = items.size();
        this.a.setOffscreenPageLimit(3);
        this.a.setPageMargin((int) context.getResources().getDimension(d.rolling_play_item_margin));
        b((ArrayList) items);
        this.a.setInterpolator(android.support.v4.view.b.c.a(0.33f, 0.0f, 0.2f, 1.0f));
    }

    public void a(int position) {
        if (this.c != null && this.c.b() > 1) {
            this.a.setCurrentItem(position, false);
            if (position == 0) {
                this.c.a(position);
            }
        }
    }

    public void a() {
        if (this.e != null) {
            this.d.removeCallbacks(this.e);
        }
    }

    public void b() {
        this.d.postDelayed(this.e, 5000);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.e != null) {
            this.d.removeCallbacks(this.e);
        }
        this.e = null;
    }

    private void b(ArrayList<AbstractStrcutItem> items) {
        Context context = getContext();
        if (items != null && items.size() > 0) {
            int screenWidth = (int) context.getResources().getDimension(d.block_rolling_play_item_width);
            AbstractStrcutItem temp = (AbstractStrcutItem) items.get(0);
            if (temp instanceof AppAdStructItem) {
                float percent;
                AppAdStructItem item = (AppAdStructItem) temp;
                int itemWidth = item.img_width;
                int itemHeight = item.img_height;
                if (itemWidth >= screenWidth) {
                    percent = (float) (screenWidth / itemWidth);
                } else {
                    percent = (float) (itemWidth / screenWidth);
                }
                int height = (int) (((float) itemHeight) * percent);
                if (height > 0 && this.c != null) {
                    ((LayoutParams) this.a.getLayoutParams()).height = height;
                    this.c.b(height);
                }
            }
        }
    }

    private void a(final int position, long mills, final boolean soomth) {
        if (this.e != null) {
            this.d.removeCallbacks(this.e);
        }
        this.e = new Runnable(this) {
            final /* synthetic */ RollingPlayWidgetV2 c;

            public void run() {
                if (this.c.a != null && this.c.c != null) {
                    int count = this.c.c.b();
                    int pos = position % count;
                    if (pos == this.c.a.getCurrentItem()) {
                        pos = (pos + 1) % count;
                    }
                    Log.d(RollingPlayWidgetV2.class.getSimpleName(), "pos=" + pos);
                    if (soomth) {
                        this.c.a.setCurrentItem(pos, 500);
                    } else {
                        this.c.a.setCurrentItem(pos, false);
                    }
                }
            }
        };
        if (this.d != null) {
            this.d.postDelayed(this.e, mills);
        }
    }

    public void setOnAdItemClickListener(a onAdItemClickListener) {
        this.j = onAdItemClickListener;
    }

    public void a(Context context, RollingPlayItem item, ArrayList<AbstractStrcutItem> items) {
        int position = 0;
        if (this.c == null) {
            this.c = new b(this, context, items);
        } else if (this.a != null) {
            this.c.a((ArrayList) items);
            this.i = new SoftReference(item.rollingPlayItem);
            position = item.getPosition();
        }
        this.a.setOnPageChangeListener(this.c);
        this.a.setAdapter(this.c);
        b((ArrayList) items);
        a(position);
    }

    public int getCurrentPosition() {
        return this.g;
    }

    public int getCurrentSimplePos() {
        return this.h;
    }

    public void b(int arg0) {
        if (arg0 == 1) {
            a();
        }
        if (arg0 == 0 && this.a != null) {
            this.a.startFadeIn();
            b();
        }
    }

    public void a(int position, float positionOffset, int positionOffsetPixels) {
        Log.i("liunianprint:", "onMainPageScrolled position>" + position + ";positionOffset >" + positionOffset + ";positionPixels >" + positionOffsetPixels);
        if (positionOffset > 0.01f) {
            this.a.startFadeOut();
        } else if (this.a != null) {
            this.a.startFadeIn();
            b();
        }
    }
}
