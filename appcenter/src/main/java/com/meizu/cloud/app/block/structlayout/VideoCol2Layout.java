package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.VideoCol2Item;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class VideoCol2Layout extends AbsBlockLayout<VideoCol2Item> {
    public ImageView imageView1;
    public ImageView imageView2;
    public View itemView1;
    public View itemView2;
    public LinearLayout layoutText1;
    public LinearLayout layoutText2;
    public int mImageHeight;
    private ViewGroup mParent;
    public TextView txtDesc1;
    public TextView txtDesc2;
    public TextView txtTilte1;
    public TextView txtTilte2;

    public VideoCol2Layout(ViewGroup parent) {
        this.mParent = parent;
    }

    public VideoCol2Layout(Context context, VideoCol2Item item) {
        super(context, item);
    }

    public View createView(Context context, VideoCol2Item item) {
        View rootView = LayoutInflater.from(context).inflate(g.block_video_col2_layout, this.mParent, false);
        this.itemView1 = rootView.findViewById(f.layout_item1);
        this.imageView1 = (ImageView) this.itemView1.findViewById(f.image);
        this.layoutText1 = (LinearLayout) this.itemView1.findViewById(f.layout_text);
        this.txtTilte1 = (TextView) this.itemView1.findViewById(f.txt_title);
        this.txtDesc1 = (TextView) this.itemView1.findViewById(f.txt_desc);
        this.itemView2 = rootView.findViewById(f.layout_item2);
        this.imageView2 = (ImageView) this.itemView2.findViewById(f.image);
        this.layoutText2 = (LinearLayout) this.itemView2.findViewById(f.layout_text);
        this.txtTilte2 = (TextView) this.itemView2.findViewById(f.txt_title);
        this.txtDesc2 = (TextView) this.itemView2.findViewById(f.txt_desc);
        this.mImageHeight = context.getResources().getDimensionPixelSize(d.video_small_image_height);
        return rootView;
    }

    public void updateView(final Context context, final VideoCol2Item item, t viewController, final int position) {
        if (item.gameInfo1 != null) {
            h.a(context, item.gameInfo1.thumb_image[0], this.imageView1, new Callback() {
                public void onSuccess() {
                    VideoCol2Layout.this.setTextLayoutBackground(context, item.gameInfo1.thumb_image[0], VideoCol2Layout.this.layoutText1);
                }

                public void onError() {
                }
            });
            this.txtTilte1.setText(item.gameInfo1.title);
            if (TextUtils.isEmpty(item.gameInfo1.keywords)) {
                this.txtDesc1.setVisibility(8);
            } else {
                this.txtDesc1.setText(item.gameInfo1.keywords);
                this.txtDesc1.setVisibility(0);
            }
            this.itemView1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (VideoCol2Layout.this.mOnChildClickListener != null) {
                        VideoCol2Layout.this.mOnChildClickListener.onClickConts(item.gameInfo1, null, position, 0);
                    }
                }
            });
        }
        if (item.gameInfo2 != null) {
            this.itemView2.setVisibility(0);
            h.a(context, item.gameInfo2.thumb_image[0], this.imageView2, new Callback() {
                public void onSuccess() {
                    VideoCol2Layout.this.setTextLayoutBackground(context, item.gameInfo2.thumb_image[0], VideoCol2Layout.this.layoutText2);
                }

                public void onError() {
                }
            });
            this.txtTilte2.setText(item.gameInfo2.title);
            if (TextUtils.isEmpty(item.gameInfo2.keywords)) {
                this.txtDesc2.setVisibility(8);
            } else {
                this.txtDesc2.setText(item.gameInfo2.keywords);
                this.txtDesc2.setVisibility(0);
            }
            this.itemView2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (VideoCol2Layout.this.mOnChildClickListener != null) {
                        VideoCol2Layout.this.mOnChildClickListener.onClickConts(item.gameInfo2, null, position, 1);
                    }
                }
            });
            return;
        }
        this.itemView2.setVisibility(4);
    }

    private void setTextLayoutBackground(final Context context, final String picassoUrl, final View view) {
        new Thread() {
            public void run() {
                Bitmap bitmap = null;
                try {
                    bitmap = Picasso.with(context.getApplicationContext()).load(picassoUrl).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int clipHeight = (view.getMeasuredHeight() * height) / VideoCol2Layout.this.mImageHeight;
                    Bitmap bmp = Bitmap.createBitmap(bitmap, 0, height - clipHeight, width, clipHeight);
                    bitmap.recycle();
                    final Drawable bg = new BitmapDrawable(u.a(bmp));
                    view.postOnAnimation(new Runnable() {
                        public void run() {
                            view.setBackground(bg);
                        }
                    });
                }
            }
        }.start();
    }

    protected void updateLayoutMargins(Context context, VideoCol2Item item) {
    }
}
