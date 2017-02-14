package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.VideoCol1Item;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.utils.u;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.io.IOException;

public class VideoCol1Layout extends AbsBlockLayout<VideoCol1Item> {
    public ImageView imageView;
    public View itemView;
    public LinearLayout layoutText;
    private ViewGroup mParent;
    public TextView txtDesc;
    public TextView txtTilte;

    public VideoCol1Layout(Context context, VideoCol1Item item) {
        super(context, item);
    }

    public VideoCol1Layout(ViewGroup parent) {
        this.mParent = parent;
    }

    public View createView(Context context, VideoCol1Item item) {
        CardView rootView = (CardView) LayoutInflater.from(context).inflate(g.block_video_col1_layout, this.mParent, false);
        this.itemView = rootView;
        this.imageView = (ImageView) rootView.findViewById(f.image);
        this.layoutText = (LinearLayout) rootView.findViewById(f.layout_text);
        this.txtTilte = (TextView) rootView.findViewById(f.txt_title);
        this.txtDesc = (TextView) rootView.findViewById(f.txt_desc);
        return rootView;
    }

    public void updateView(final Context context, final VideoCol1Item item, t viewController, final int position) {
        if (item.gameArticleInfo != null) {
            h.a(context, item.gameArticleInfo.thumb_image[0], this.imageView, new Callback() {
                public void onSuccess() {
                    VideoCol1Layout.this.setTextLayoutBackground(context, item.gameArticleInfo.thumb_image[0]);
                }

                public void onError() {
                }
            });
            this.txtTilte.setText(item.gameArticleInfo.title);
            if (TextUtils.isEmpty(item.gameArticleInfo.keywords)) {
                this.txtDesc.setVisibility(8);
            } else {
                this.txtDesc.setText(item.gameArticleInfo.keywords);
                this.txtDesc.setVisibility(0);
            }
            this.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (VideoCol1Layout.this.mOnChildClickListener != null) {
                        VideoCol1Layout.this.mOnChildClickListener.onClickConts(item.gameArticleInfo, null, position, 0);
                    }
                }
            });
        }
    }

    private void setTextLayoutBackground(final Context context, final String picassoUrl) {
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
                    int clipHeight = (VideoCol1Layout.this.layoutText.getHeight() * height) / VideoCol1Layout.this.itemView.getHeight();
                    Bitmap bmp = Bitmap.createBitmap(bitmap, 0, height - clipHeight, width, clipHeight);
                    bitmap.recycle();
                    final Drawable bg = new BitmapDrawable(u.a(bmp));
                    VideoCol1Layout.this.layoutText.postOnAnimation(new Runnable() {
                        public void run() {
                            VideoCol1Layout.this.layoutText.setBackground(bg);
                        }
                    });
                }
            }
        }.start();
    }

    protected void updateLayoutMargins(Context context, VideoCol1Item item) {
    }
}
