package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem;
import com.meizu.cloud.app.block.requestitem.GameQualityStructItem.GameLayout;
import com.meizu.cloud.app.block.structitem.GameQualityItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.t.a;
import com.meizu.cloud.app.core.w;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.Tags.Name;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.TagView;
import com.meizu.cloud.b.a.b;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import java.util.List;

public class GameQualityBlockLayout extends AbsBlockLayout<GameQualityItem> {
    CirProButton mBtnInstall;
    TextView mDesc;
    ImageView mIcon;
    ImageView mImageTag;
    LinearLayout mInstallLinearLayout;
    ImageView mQualityBgView;
    TextView mRecomDes;
    TextView mTab1;
    TextView mTab2;
    TextView mTab3;
    View mTabDivider1;
    View mTabDivider2;
    TextView mTag1;
    TextView mTag2;
    TextView mText;

    public GameQualityBlockLayout(Context context, GameQualityItem gameQualityItem) {
        super(context, gameQualityItem);
    }

    public View createView(Context context, GameQualityItem item) {
        View v = inflate(context, g.block_game_quality_layout);
        this.mQualityBgView = (ImageView) v.findViewById(f.game_quality_bg_view);
        this.mIcon = (ImageView) v.findViewById(f.icon);
        this.mInstallLinearLayout = (LinearLayout) v.findViewById(f.game_quality_appinfo_view);
        this.mText = (TextView) v.findViewById(f.text);
        this.mDesc = (TextView) v.findViewById(f.desc);
        this.mRecomDes = (TextView) v.findViewById(f.game_quality_descriptions);
        this.mBtnInstall = (CirProButton) v.findViewById(f.btnInstall);
        this.mTag1 = (TextView) v.findViewById(f.tag1);
        this.mTag2 = (TextView) v.findViewById(f.tag2);
        this.mImageTag = (ImageView) v.findViewById(f.imageTag);
        this.mTab1 = (TextView) v.findViewById(f.game_quality_tab1);
        this.mTab2 = (TextView) v.findViewById(f.game_quality_tab2);
        this.mTab3 = (TextView) v.findViewById(f.game_quality_tab3);
        this.mTabDivider1 = v.findViewById(f.game_quality_tab_divider1);
        this.mTabDivider2 = v.findViewById(f.game_quality_tab_divider2);
        return v;
    }

    public void updateView(Context context, GameQualityItem item, t viewController, int position) {
        if (item.mGameQualityStructItem != null) {
            item.mGameQualityStructItem.click_pos = position;
            final a recommendStructItem = item.mGameQualityStructItem;
            h.c(context, recommendStructItem.feed_img, this.mQualityBgView);
            h.a(context, recommendStructItem.icon, this.mIcon);
            this.mText.setText(recommendStructItem.name);
            TextView textView = this.mDesc;
            String string = context.getString(i.app_size_and_install_counts);
            Object[] objArr = new Object[2];
            objArr[0] = com.meizu.cloud.app.utils.g.a((double) recommendStructItem.size, context.getResources().getStringArray(b.sizeUnit));
            objArr[1] = com.meizu.cloud.app.utils.g.a(context, recommendStructItem.download_count);
            textView.setText(String.format(string, objArr));
            this.mRecomDes.setText(recommendStructItem.recommend_desc);
            final int i = position;
            this.mView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (GameQualityBlockLayout.this.mOnChildClickListener != null) {
                        GameQualityBlockLayout.this.mOnChildClickListener.onClickApp(recommendStructItem, i, 0);
                    }
                }
            });
            i = position;
            this.mBtnInstall.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (GameQualityBlockLayout.this.mOnChildClickListener != null) {
                        GameQualityBlockLayout.this.mOnChildClickListener.onDownload(recommendStructItem, view, i, 0);
                    }
                }
            });
            this.mInstallLinearLayout.setTag(recommendStructItem.package_name);
            this.mBtnInstall.setTag(Integer.valueOf(recommendStructItem.id));
            w displayConfig = new w();
            displayConfig.a = c.btn_default;
            displayConfig.c = 17170443;
            displayConfig.b = -1;
            displayConfig.d = c.btn_operation_downloading_text;
            displayConfig.e = 17170443;
            displayConfig.f = 17170443;
            viewController.a(displayConfig);
            viewController.a(recommendStructItem, null, true, this.mBtnInstall);
            viewController.a(null);
            updateTags(context, recommendStructItem);
            List<GameLayout> gameLayoutList = item.mGameQualityStructItem.game_layout;
            if (gameLayoutList != null && gameLayoutList.size() > 0) {
                int color = context.getResources().getColor(c.theme_color);
                try {
                    color = Color.parseColor(item.mGameQualityStructItem.desc_color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mTabDivider1.setBackgroundColor(color);
                this.mTabDivider2.setBackgroundColor(color);
                initTabView(context, this.mTab1, color, (GameLayout) gameLayoutList.get(0), item.mGameQualityStructItem);
                if (gameLayoutList.size() >= 2) {
                    initTabView(context, this.mTab2, color, (GameLayout) gameLayoutList.get(1), item.mGameQualityStructItem);
                    if (gameLayoutList.size() >= 3) {
                        initTabView(context, this.mTab3, color, (GameLayout) gameLayoutList.get(2), item.mGameQualityStructItem);
                    }
                }
            }
        }
    }

    protected void updateLayoutMargins(Context context, GameQualityItem item) {
    }

    private void updateTags(Context context, AppStructItem appStructItem) {
        if (appStructItem.tags != null) {
            if (appStructItem.tags.names == null || appStructItem.tags.names.size() <= 0) {
                this.mTag1.setVisibility(8);
                this.mTag2.setVisibility(8);
            } else {
                try {
                    Name name;
                    if (appStructItem.name.getBytes("GB18030").length <= 22) {
                        int size = appStructItem.tags.names.size();
                        if (size > 0) {
                            name = (Name) appStructItem.tags.names.get(0);
                            this.mTag1.setVisibility(0);
                            TagView.a(this.mTag1, name.text, name.bg_color);
                        } else {
                            this.mTag1.setVisibility(8);
                        }
                        if (size > 1) {
                            name = (Name) appStructItem.tags.names.get(1);
                            this.mTag2.setVisibility(0);
                            TagView.a(this.mTag2, name.text, name.bg_color);
                        } else {
                            this.mTag2.setVisibility(8);
                        }
                    } else {
                        name = (Name) appStructItem.tags.names.get(0);
                        this.mTag1.setVisibility(0);
                        TagView.a(this.mTag1, name.text, name.bg_color);
                        this.mTag2.setVisibility(8);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (appStructItem.tags.hasgift) {
                this.mImageTag.setVisibility(8);
                h.a(context, appStructItem.tags.icon, this.mImageTag);
                return;
            }
            this.mImageTag.setVisibility(8);
            return;
        }
        this.mImageTag.setVisibility(8);
        this.mTag1.setVisibility(8);
        this.mTag2.setVisibility(8);
    }

    private void initTabView(Context context, TextView tab, int color, final GameLayout gameLayout, final GameQualityStructItem gameQualityStructItem) {
        if ("bbs".equals(gameLayout.type)) {
            tab.setText(String.format("%s", new Object[]{gameLayout.name}));
        } else {
            tab.setText(String.format("%s(%d)", new Object[]{gameLayout.name, Integer.valueOf(gameLayout.count)}));
        }
        tab.setTextColor(color);
        tab.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GameQualityBlockLayout.this.mOnChildClickListener.onTabClick(gameLayout, gameQualityStructItem);
            }
        });
    }
}
