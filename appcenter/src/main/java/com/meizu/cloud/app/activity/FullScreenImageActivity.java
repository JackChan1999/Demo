package com.meizu.cloud.app.activity;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ad;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.meizu.cloud.app.request.model.PreviewImage;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.FullScreenImageView;
import com.meizu.cloud.app.widget.TabScrollerLayout;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.common.util.d;
import com.meizu.common.widget.LoadingView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullScreenImageActivity extends FragmentActivity {
    private GestureDetector A;
    private Map<Integer, Boolean> B = new HashMap();
    private Runnable C = new Runnable(this) {
        final /* synthetic */ FullScreenImageActivity a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.x.setAnimation(AnimationUtils.loadAnimation(this.a, 17432577));
            this.a.x.setVisibility(4);
        }
    };
    protected Drawable j;
    protected float k;
    protected float l;
    protected float m;
    protected float n;
    protected Bundle o;
    public Handler p = new Handler(this) {
        final /* synthetic */ FullScreenImageActivity a;

        {
            this.a = r1;
        }

        public void handleMessage(Message msg) {
            List<Object> data;
            if (msg.what == 256) {
                data = msg.obj;
                ((ImageView) data.get(1)).setImageBitmap((Bitmap) data.get(0));
            } else if (msg.what == 257) {
                data = (List) msg.obj;
                ImageView ivThumb = (ImageView) data.get(0);
                LoadingView progressBar = (LoadingView) data.get(1);
                progressBar.setVisibility(8);
                progressBar.b();
                ivThumb.setVisibility(8);
            }
        }
    };
    private List<Parcelable> q;
    private int r;
    private ViewPager s;
    private b t;
    private int u;
    private int v;
    private LayoutInflater w;
    private TabScrollerLayout x;
    private d y;
    private final ArrayList<RelativeLayout> z = new ArrayList();

    class a extends SimpleOnGestureListener {
        final /* synthetic */ FullScreenImageActivity a;

        a(FullScreenImageActivity fullScreenImageActivity) {
            this.a = fullScreenImageActivity;
        }

        public boolean onSingleTapUp(MotionEvent e) {
            if (this.a != null) {
                this.a.i();
            }
            return true;
        }
    }

    private class b extends ad {
        final /* synthetic */ FullScreenImageActivity a;

        public b(FullScreenImageActivity fullScreenImageActivity) {
            this.a = fullScreenImageActivity;
            fullScreenImageActivity.w = LayoutInflater.from(fullScreenImageActivity);
        }

        public int b() {
            return this.a.q.size();
        }

        public boolean a(View view, Object object) {
            return view == object;
        }

        public void a(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public int a(Object object) {
            return super.a(object);
        }

        public Object a(ViewGroup container, int position) {
            PreviewImage imageInfo = (PreviewImage) this.a.q.get(position);
            View view = this.a.w.inflate(g.fullscreen_image_pager_item, null);
            ImageView ivThumb = (ImageView) view.findViewById(f.image_item_thumb);
            View iv = (FullScreenImageView) view.findViewById(f.image_item);
            LoadingView mProgressBar = (LoadingView) view.findViewById(f.loading_progressbar);
            mProgressBar.setVisibility(0);
            mProgressBar.a();
            int width = 0;
            int height = 0;
            int thumbH = imageInfo.height;
            int thumbW = imageInfo.width;
            if (thumbH <= 0 || thumbW <= 0) {
                height = this.a.u;
                width = this.a.v;
            } else if (thumbH > thumbW) {
                height = this.a.u;
                width = (thumbW * height) / thumbH;
                if (width > this.a.v) {
                    width = this.a.v;
                    height = (thumbH * width) / thumbW;
                }
            } else if (thumbH <= thumbW) {
                width = this.a.v;
                height = (thumbH * width) / thumbW;
                if (height > this.a.u) {
                    height = this.a.u;
                    width = (thumbW * height) / thumbH;
                }
            }
            ivThumb.setLayoutParams(new LayoutParams(width, height));
            ivThumb.setScaleType(ScaleType.FIT_CENTER);
            iv.setImageViewAndLoadingView(ivThumb, mProgressBar, thumbW, thumbH, width, height);
            ViewGroup.LayoutParams layoutParams = (LayoutParams) iv.getLayoutParams();
            layoutParams.width = width;
            layoutParams.height = height;
            if (thumbW > thumbH) {
                iv.setScaleType(ScaleType.FIT_CENTER);
                layoutParams.alignWithParent = true;
            } else {
                iv.setScaleType(ScaleType.FIT_XY);
            }
            iv.setLayoutParams(layoutParams);
            view.setTag(Integer.valueOf(position));
            if (this.a.a(false, iv, imageInfo.image, thumbW, thumbH, width, height)) {
                mProgressBar.setVisibility(8);
                mProgressBar.b();
            } else {
                this.a.a(true, iv, imageInfo.small, thumbW, thumbH, width, height);
            }
            if (this.a.o == null && this.a.r == position) {
                this.a.a(iv, imageInfo);
            }
            view.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    this.a.a.A.onTouchEvent(motionEvent);
                    return true;
                }
            });
            container.addView(view);
            return view;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        this.j = new ColorDrawable(-16777216);
        super.onCreate(savedInstanceState);
        setContentView(g.fullscreen_viewpager_layout);
        this.o = savedInstanceState;
        Bundle bundle = getIntent().getExtras();
        this.q = bundle.getParcelableArrayList("ExtraImageArray");
        this.r = bundle.getInt("ExtraAppImageIndex");
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(p);
        this.u = p.y;
        this.v = p.x;
        g();
        this.A = new GestureDetector(this, new a(this));
    }

    private void g() {
        this.s = (ViewPager) findViewById(f.fullscreen_viewpager);
        this.x = (TabScrollerLayout) findViewById(f.fullscreen_pager_scroller);
        this.y = this.x.getTabScroller();
        this.y.a(getResources().getDrawable(e.mz_full_screen_picture_scrollbar));
        this.s.setOnPageChangeListener(new ViewPager.f(this) {
            final /* synthetic */ FullScreenImageActivity a;

            {
                this.a = r1;
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                this.a.r = position;
                if (this.a.y != null) {
                    this.a.y.a(position, positionOffset);
                    if (this.a.x.getVisibility() != 0) {
                        this.a.x.clearAnimation();
                        this.a.x.setVisibility(0);
                    }
                    this.a.x.removeCallbacks(this.a.C);
                    this.a.x.postDelayed(this.a.C, 500);
                }
            }

            public void a(int position) {
                Log.d(this.a.getClass().getSimpleName(), "onPageSelected position=" + position);
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        this.t = new b(this);
        h();
        this.s.setOffscreenPageLimit(0);
        this.s.setAdapter(this.t);
        if (this.o != null) {
            this.s.setBackground(this.j);
        }
        this.s.setCurrentItem(this.r);
        this.y.a(this.r);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void finish() {
        if (this.B != null && this.B.size() > 0) {
            this.B.clear();
        }
        super.finish();
    }

    public void onBackPressed() {
        i();
    }

    private void h() {
        for (int i = 0; i < this.q.size(); i++) {
            View frameLayout = new RelativeLayout(this);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1.0f));
            this.x.addView(frameLayout);
            this.z.add(frameLayout);
            this.y.a(frameLayout);
        }
    }

    private void i() {
        int current = this.s.getCurrentItem();
        int count = this.s.getChildCount();
        int i = 0;
        while (i < count) {
            View v = this.s.getChildAt(i);
            Integer tag = v.getTag();
            if (tag == null || tag.intValue() != current) {
                i++;
            } else {
                PreviewImage screenShotInfo = (PreviewImage) this.q.get(current);
                a((FullScreenImageView) v.findViewById(f.image_item), screenShotInfo.x, screenShotInfo.y, screenShotInfo.width, screenShotInfo.height);
                return;
            }
        }
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a("detail_big_image");
    }

    protected void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a("detail_big_image", null);
    }

    protected boolean a(boolean loadThumb, ImageView ivThumb, String url, int width, int height, int bigWidth, int bigHeight) {
        if (!TextUtils.isEmpty(url)) {
            try {
                Picasso picasso = Picasso.with(getApplicationContext());
                RequestCreator requestCreator = picasso.load(url).fit();
                Bitmap bitmap = h.a(picasso, requestCreator, width, height);
                if (bitmap != null) {
                    ivThumb.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                    return true;
                } else if (loadThumb) {
                    Bitmap bigCache = h.a(picasso, requestCreator, bigWidth, bigHeight);
                    if (bigCache != null) {
                        ivThumb.setImageDrawable(new BitmapDrawable(getResources(), bigCache));
                    }
                } else {
                    requestCreator.into(ivThumb);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Picasso.with(getApplicationContext()).load(url).fit().placeholder(e.image_background).error(e.image_background).into(ivThumb);
            }
        }
        return false;
    }

    public void a(final View v, final PreviewImage imageInfo) {
        v.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener(this) {
            final /* synthetic */ FullScreenImageActivity c;

            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);
                this.c.a(v, imageInfo.x, imageInfo.y, imageInfo.width, imageInfo.height, new Runnable(this) {
                    final /* synthetic */ AnonymousClass4 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.c.s.setBackground(this.a.c.j);
                    }
                });
                return true;
            }
        });
    }

    public void a(View iv, int fromX, int fromY, int thumbWidth, int thumbHeight, Runnable runnable) {
        int imagePadding = (int) getResources().getDimension(com.meizu.cloud.b.a.d.details_content_thumb_padding);
        float scaleX = ((float) (thumbWidth - (imagePadding * 2))) / ((float) iv.getWidth());
        float scaleY = ((float) (thumbHeight - (imagePadding * 2))) / ((float) iv.getHeight());
        int[] location = new int[2];
        iv.getLocationOnScreen(location);
        float translationX = (float) ((fromX - location[0]) + imagePadding);
        float translationY = (float) ((fromY - location[1]) + imagePadding);
        iv.setTranslationX(translationX);
        iv.setTranslationY(translationY);
        iv.setPivotX(0.0f);
        iv.setPivotY(0.0f);
        iv.setScaleX(scaleX);
        iv.setScaleY(scaleY);
        iv.setAlpha(0.0f);
        this.m = scaleX;
        this.n = scaleY;
        this.k = translationX;
        this.l = translationY;
        iv.animate().alpha(1.0f).setDuration(300).scaleX(1.0f).scaleY(1.0f).translationX(0.0f).translationY(0.0f).setInterpolator(new DecelerateInterpolator(2.5f)).withEndAction(runnable);
        if (thumbHeight < thumbWidth) {
            this.s.setBackground(this.j);
            ObjectAnimator bgAnim = ObjectAnimator.ofInt(this.j, "alpha", new int[]{0, 255});
            bgAnim.setInterpolator(new DecelerateInterpolator(1.5f));
            bgAnim.setDuration(300);
            bgAnim.start();
        }
    }

    public void a(View iv, int fromX, int fromY, int thumbWidth, int thumbHeight) {
        if (fromX == 0 && fromY == 0) {
            finish();
            overridePendingTransition(-1, com.meizu.cloud.b.a.a.shrink_fade_out_center);
            return;
        }
        iv.getLocationOnScreen(new int[2]);
        iv.animate().alpha(0.0f).setDuration(300).scaleX(this.m).scaleY(this.n).translationX(this.k).translationY(this.l).setInterpolator(new DecelerateInterpolator(2.5f)).withEndAction(new Runnable(this) {
            final /* synthetic */ FullScreenImageActivity a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.finish();
            }
        });
        ObjectAnimator bgAnim = ObjectAnimator.ofInt(this.j, "alpha", new int[]{0, 0});
        bgAnim.setInterpolator(new DecelerateInterpolator(1.5f));
        bgAnim.setDuration(300);
        bgAnim.start();
    }
}
