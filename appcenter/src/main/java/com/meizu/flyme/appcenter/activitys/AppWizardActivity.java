package com.meizu.flyme.appcenter.activitys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.meizu.cloud.app.request.structitem.RecommendAppStructItem;
import com.meizu.cloud.app.request.structitem.RecommendRowItem;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.base.app.BaseWizardActivityV2;
import com.meizu.flyme.appcenter.widget.AppCheckableAppItemView;
import com.meizu.flyme.appcenter.widget.TextViewVertical;
import com.meizu.mstore.R;
import flyme.support.v7.widget.RecyclerView.LayoutParams;

public class AppWizardActivity extends BaseWizardActivityV2 {

    private class a extends BaseWizardActivityV2.a {
        final /* synthetic */ AppWizardActivity b;

        public class a extends com.meizu.cloud.base.a.d.a {
            public TextViewVertical a;
            public AppCheckableAppItemView[] b;
            final /* synthetic */ a c;

            public a(a aVar, View itemView, boolean itemClickable) {
                this.c = aVar;
                super(aVar, itemView, itemClickable);
            }
        }

        public /* synthetic */ com.meizu.cloud.base.a.d.a a(ViewGroup viewGroup, int i) {
            return c(viewGroup, i);
        }

        public a(AppWizardActivity appWizardActivity, Context context) {
            this.b = appWizardActivity;
            super(context);
        }

        public a c(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wizard_activity_row_item, parent, false);
            TextViewVertical txtTitle = (TextViewVertical) rootView.findViewById(R.id.textTitle);
            AppCheckableAppItemView itemView1 = (AppCheckableAppItemView) rootView.findViewById(R.id.appItem1);
            AppCheckableAppItemView itemView2 = (AppCheckableAppItemView) rootView.findViewById(R.id.appItem2);
            AppCheckableAppItemView itemView3 = (AppCheckableAppItemView) rootView.findViewById(R.id.appItem3);
            a holder = new a(this, rootView, false);
            holder.a = txtTitle;
            holder.b = new AppCheckableAppItemView[]{itemView1, itemView2, itemView3};
            return holder;
        }

        public void a(com.meizu.cloud.base.a.d.a holder, int position) {
            if (holder instanceof a) {
                a rowViewHolder = (a) holder;
                RecommendRowItem rowItem = (RecommendRowItem) c(position);
                if (rowItem == null || rowItem.data == null || rowItem.data.size() <= 0) {
                    rowViewHolder.a.setVisibility(8);
                    return;
                }
                int dimensionPixelSize;
                rowViewHolder.a.setText(rowItem.name);
                rowViewHolder.a.setTextColor(this.b.getResources().getColor(R.color.wizard_title_line));
                LayoutParams layoutParams = (LayoutParams) rowViewHolder.itemView.getLayoutParams();
                if (position == 0) {
                    dimensionPixelSize = this.b.getResources().getDimensionPixelSize(R.dimen.wizard_list_margin_top);
                } else {
                    dimensionPixelSize = 0;
                }
                layoutParams.topMargin = dimensionPixelSize;
                rowViewHolder.itemView.setLayoutParams(layoutParams);
                int i = 0;
                while (i < rowViewHolder.b.length) {
                    final AppCheckableAppItemView appItemView = rowViewHolder.b[i];
                    if (i >= rowItem.data.size() || rowItem.data.get(i) == null) {
                        appItemView.setVisibility(4);
                    } else {
                        final RecommendAppStructItem appStructItem = (RecommendAppStructItem) rowItem.data.get(i);
                        appItemView.setVisibility(0);
                        appItemView.setIconUrl(appStructItem.icon);
                        appItemView.setTitle(appStructItem.name);
                        appItemView.setDesc(g.a((double) appStructItem.size, this.a.getResources().getStringArray(R.array.sizeUnit)));
                        appItemView.setCheck(appStructItem.isChecked);
                        appItemView.setOnClickListener(new OnClickListener(this) {
                            final /* synthetic */ a c;

                            public void onClick(View v) {
                                boolean z;
                                boolean z2 = true;
                                AppCheckableAppItemView appCheckableAppItemView = appItemView;
                                if (appStructItem.isChecked) {
                                    z = false;
                                } else {
                                    z = true;
                                }
                                appCheckableAppItemView.setCheck(z);
                                RecommendAppStructItem recommendAppStructItem = appStructItem;
                                if (appStructItem.isChecked) {
                                    z = false;
                                } else {
                                    z = true;
                                }
                                recommendAppStructItem.isChecked = z;
                                AppWizardActivity appWizardActivity = this.c.b;
                                if (this.c.c().size() <= 0) {
                                    z2 = false;
                                }
                                appWizardActivity.b(z2);
                            }
                        });
                    }
                    i++;
                }
            }
        }
    }

    protected void h() {
        View header = findViewById(R.id.header);
        ((TextView) header.findViewById(R.id.welcome_title)).setText(getResources().getString(R.string.app_welcome_title));
        ((TextView) header.findViewById(R.id.welcome)).setText(getResources().getString(R.string.app_welcome));
    }

    protected String k() {
        return "http://api-app.meizu.com/apps/public/guider";
    }

    protected BaseWizardActivityV2.a l() {
        return new a(this, this);
    }

    protected void i() {
        this.m.b();
        this.m.setVisibility(8);
        TextView textView = (TextView) findViewById(R.id.txt_empty);
        textView.setVisibility(0);
        textView.setText(getString(R.string.wizard_noapp_text));
        this.l.setVisibility(0);
        findViewById(R.id.imageView2).setVisibility(8);
        findViewById(R.id.btnInstall).setVisibility(8);
        Button skyButton = (Button) findViewById(R.id.btnSkip);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) skyButton.getLayoutParams();
        layoutParams.width = -1;
        skyButton.setLayoutParams(layoutParams);
    }
}
