package com.meizu.cloud.detail.anim;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.meizu.cloud.app.request.model.PreviewImage;
import com.meizu.cloud.app.widget.c;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class a extends ArrayAdapter<PreviewImage> {
    private List<PreviewImage> a = new ArrayList();
    private SparseArray<ImageView> b = new SparseArray();
    private Context c;
    private int d;
    private int e = 186;
    private int f = 255;
    private int g = 338;
    private int h = 640;
    private int i = 186;
    private int j = 235;
    private int k = 338;
    private int l = 85;
    private int m = 12;
    private int n;
    private int o = 20;
    private Drawable p;
    private PullToZoomGroup q;

    public /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public a(Context context, ArrayList<PreviewImage> images, PullToZoomGroup pullToZoomGroup) {
        super(context, 0);
        this.c = context;
        this.a = images;
        if (pullToZoomGroup == null) {
            this.e = (int) TypedValue.applyDimension(1, (float) this.e, getContext().getResources().getDisplayMetrics());
            this.f = (int) TypedValue.applyDimension(1, (float) this.f, getContext().getResources().getDisplayMetrics());
            this.g = (int) TypedValue.applyDimension(1, (float) this.g, getContext().getResources().getDisplayMetrics());
            this.h = (int) TypedValue.applyDimension(1, (float) this.h, getContext().getResources().getDisplayMetrics());
        } else {
            this.e = pullToZoomGroup.getSTATUS_GALLERY0();
            this.f = pullToZoomGroup.getSTATUS_GALLERY1();
            this.g = pullToZoomGroup.getSTATUS_GALLERY2();
            this.h = pullToZoomGroup.getSTATUS3();
        }
        this.i = (int) TypedValue.applyDimension(1, (float) this.i, getContext().getResources().getDisplayMetrics());
        this.j = (int) TypedValue.applyDimension(1, (float) this.j, getContext().getResources().getDisplayMetrics());
        this.k = (int) TypedValue.applyDimension(1, (float) this.k, getContext().getResources().getDisplayMetrics());
        this.n = this.g;
        this.l = (int) TypedValue.applyDimension(1, (float) this.l, getContext().getResources().getDisplayMetrics());
        this.m = (int) TypedValue.applyDimension(1, (float) this.m, getContext().getResources().getDisplayMetrics());
        this.o = (int) TypedValue.applyDimension(1, (float) this.o, getContext().getResources().getDisplayMetrics());
        this.q = pullToZoomGroup;
        b();
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public PreviewImage a(int position) {
        return (PreviewImage) this.a.get(position % this.a.size());
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View img;
        LayoutParams imgParams;
        float roate = 0.0f;
        int realPosition = position % this.a.size();
        PreviewImage imageInfo = (PreviewImage) this.a.get(realPosition);
        if (convertView != null) {
            LinearLayout linearLayout = (LinearLayout) convertView;
            img = (ImageView) linearLayout.getChildAt(0);
            imgParams = (LayoutParams) img.getLayoutParams();
        } else {
            View linearLayout2 = new LinearLayout(this.c);
            linearLayout2.setGravity(80);
            img = new ImageView(this.c);
            img.setId(16908294);
            imgParams = new LayoutParams(-2, -1);
            img.setLayoutParams(imgParams);
            img.setScaleType(ScaleType.FIT_XY);
            img.setAdjustViewBounds(true);
            linearLayout2.addView(img, imgParams);
            linearLayout2.setLayoutParams(new Gallery.LayoutParams(-2, -1));
            convertView = linearLayout2;
        }
        if (imageInfo != null && imageInfo.width > imageInfo.height) {
            roate = 90.0f;
        }
        if (this.a.size() > 0) {
            Picasso.with(this.c.getApplicationContext()).load(imageInfo.small).placeholder(this.p).error(this.p).transform(new c(this.c.getResources().getDimensionPixelSize(d.app_info_gallery_item_image_radius), 0)).rotate(roate).into((ImageView) img);
            Log.e("mGalleryAdapter", "imageInfo.small");
            this.b.put(realPosition, img);
        }
        if (position == this.d) {
            imgParams.height = -1;
            img.setLayoutParams(imgParams);
            linearLayout.setVisibility(0);
        } else {
            if (Math.abs(position - this.d) <= 2 || this.n >= (this.f + this.g) / 2) {
                linearLayout.setVisibility(0);
            } else {
                linearLayout.setVisibility(8);
            }
            a(img, position);
        }
        if (this.a.size() > 0) {
            a(roate, imageInfo.image, img);
        }
        return convertView;
    }

    private void a(final float roate, final String bigUrl, final ImageView img) {
        if (this.q != null && this.q.getGalleryLayout().getHeight() == this.q.getSTATUS3()) {
            Picasso.with(this.c.getApplicationContext()).load(bigUrl).transform(new c(this.c.getResources().getDimensionPixelSize(d.app_info_gallery_item_image_radius), 0)).rotate(roate).fetch(new Callback(this) {
                final /* synthetic */ a d;

                public void onSuccess() {
                    new Thread(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            Bitmap bitmap = null;
                            try {
                                bitmap = Picasso.with(this.a.d.c.getApplicationContext()).load(bigUrl).transform(new c(this.a.d.c.getResources().getDimensionPixelSize(d.app_info_gallery_item_image_radius), 0)).rotate(roate).get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (bitmap != null) {
                                final Bitmap bmp = bitmap;
                                ((Activity) this.a.d.c).runOnUiThread(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass1 b;

                                    public void run() {
                                        if (this.b.a.d.q != null && this.b.a.d.q.getGalleryLayout().getHeight() == this.b.a.d.q.getSTATUS3()) {
                                            img.setImageDrawable(new BitmapDrawable(bmp));
                                        }
                                    }
                                });
                            }
                        }
                    }.start();
                }

                public void onError() {
                }
            });
            Log.e("mGalleryAdapter", "imageInfo.image");
        }
    }

    public void a() {
        for (int i = 0; i < this.b.size(); i++) {
            ((ImageView) this.b.valueAt(i)).setImageBitmap(null);
        }
    }

    public void b() {
        Drawable drawable = this.c.getDrawable(e.image_background);
        Bitmap defaultLoadingBmp = Bitmap.createBitmap(this.c.getResources().getDimensionPixelSize(d.details_content_thumb_width), this.c.getResources().getDimensionPixelSize(d.details_content_thumb_height), Config.ARGB_4444);
        Canvas canvas = new Canvas(defaultLoadingBmp);
        drawable.setBounds(0, 0, this.c.getResources().getDimensionPixelSize(d.details_content_thumb_width), this.c.getResources().getDimensionPixelSize(d.details_content_thumb_height));
        drawable.draw(canvas);
        if (defaultLoadingBmp != null) {
            this.p = new BitmapDrawable(defaultLoadingBmp);
        } else {
            this.p = drawable;
        }
    }

    private void a(View child, int position) {
        LayoutParams params = (LayoutParams) child.getLayoutParams();
        if (this.n < 0 || this.n > this.g) {
            params.height = -1;
        } else if (this.n > this.f && this.n <= this.g) {
            params.height = this.j + (((this.k - this.j) * (this.n - this.f)) / (this.g - this.f));
        } else if (this.n > this.e && this.n <= this.f) {
            params.height = this.i + (((this.j - this.i) * (this.n - this.e)) / (this.f - this.e));
        }
        if (this.n <= this.g && this.n >= this.f) {
            int marginRight = this.m + (((this.n - this.g) * this.l) / (this.g - this.f));
            int paddingTop = ((this.g - this.n) * this.o) / (this.g - this.f);
            if (position < this.d) {
                params.setMargins(0, 0, marginRight, 0);
            } else if (position > this.d) {
                params.setMargins(marginRight, 0, 0, 0);
            }
            if (Math.abs(position - this.d) >= 2) {
                child.setPadding(0, paddingTop, 0, 0);
            }
        } else if (this.n < this.f && this.n > 0) {
            if (position < this.d) {
                params.setMargins(0, 0, this.m - this.l, 0);
            } else if (position > this.d) {
                params.setMargins(this.m - this.l, 0, 0, 0);
            }
            if (Math.abs(position - this.d) >= 2) {
                child.setPadding(0, this.o, 0, 0);
            }
        } else if (this.n < 0 || this.n == this.h) {
            if (position < this.d) {
                params.setMargins(-this.m, 0, this.m, 0);
            } else if (position > this.d) {
                params.setMargins(this.m, 0, -this.m, 0);
            }
        }
        child.setLayoutParams(params);
    }

    public void a(int selectPosition, int imgMaxHeight) {
        this.n = imgMaxHeight;
        this.d = selectPosition;
        notifyDataSetChanged();
    }

    public void b(int mImgMaxHeight) {
        this.n = mImgMaxHeight;
        notifyDataSetChanged();
    }
}
