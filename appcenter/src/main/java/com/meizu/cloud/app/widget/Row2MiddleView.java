package com.meizu.cloud.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.AppUpdateStructItem;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;

public class Row2MiddleView extends MiddleView {
    public TextView a;
    public TextView b;
    public TagView c;
    public TextView d;
    public TextView e;

    public Row2MiddleView(Context context, ViewGroup parent) {
        super(context);
        a(context, parent);
    }

    private void a(Context context, ViewGroup parent) {
        View rootView = LayoutInflater.from(context).inflate(g.middle_view_row2, parent, true);
        this.a = (TextView) rootView.findViewById(f.txt_index);
        this.b = (TextView) rootView.findViewById(f.txt_title);
        this.c = (TagView) rootView.findViewById(f.tagView);
        this.d = (TextView) rootView.findViewById(f.txt_desc1);
        this.e = (TextView) rootView.findViewById(f.txt_desc2);
    }

    public void a(AppUpdateStructItem appStructItem, int index, t viewController) {
        a(index, viewController);
        this.b.setText(appStructItem.name);
        this.c.setTags(appStructItem.name, appStructItem.tags);
        com.meizu.cloud.app.utils.g.a(getContext(), appStructItem, this.d);
        com.meizu.cloud.app.utils.g.b(getContext(), appStructItem, this.e);
    }

    private void a(int index, t viewController) {
        Typeface tf2 = null;
        if (!(viewController == null || viewController.c() == null || !viewController.c().b)) {
            Typeface tf1 = viewController.c().d;
            tf2 = viewController.c().e;
            this.a.setVisibility(0);
            if (index <= 3) {
                switch (index) {
                    case 1:
                        this.a.setTextColor(getResources().getColor(c.rank_index_first));
                        if (tf1 != null) {
                            this.a.setTypeface(tf1);
                            break;
                        }
                        break;
                    case 2:
                        this.a.setTextColor(getResources().getColor(c.rank_index_second));
                        if (tf1 != null) {
                            this.a.setTypeface(tf1);
                            break;
                        }
                        break;
                    case 3:
                        this.a.setTextColor(getResources().getColor(c.rank_index_third));
                        if (tf1 != null) {
                            this.a.setTypeface(tf1);
                            break;
                        }
                        break;
                }
            }
            this.a.setTextColor(getResources().getColor(c.app_info_app_name_color));
            this.a.getPaint().setFakeBoldText(false);
        }
        this.a.setText(String.format("%d. ", new Object[]{Integer.valueOf(index)}));
        if (tf2 != null) {
            this.a.setTypeface(tf2);
        }
    }
}
