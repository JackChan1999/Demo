package com.meizu.cloud.app.block.structlayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.app.block.structitem.CategoryTag6Item;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.request.structitem.CategoryStructItem;
import com.meizu.cloud.app.request.structitem.PropertyTag;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CategoryTag6Layout extends AbsBlockLayout<CategoryTag6Item> {
    public ImageView icon;
    public LinearLayout layoutTitle;
    private ViewGroup mParent;
    public TextView[] txtArray;
    public TextView txtTitile;

    public CategoryTag6Layout(ViewGroup parent) {
        this.mParent = parent;
    }

    public View createView(Context context, CategoryTag6Item item) {
        View rootView = inflate(context, g.category_item_view, this.mParent, false);
        this.layoutTitle = (LinearLayout) rootView.findViewById(f.layoutTitle);
        this.icon = (ImageView) rootView.findViewById(f.icon);
        this.txtTitile = (TextView) rootView.findViewById(f.txtTitle);
        TextView txt1 = (TextView) rootView.findViewById(f.txt1);
        TextView txt2 = (TextView) rootView.findViewById(f.txt2);
        TextView txt3 = (TextView) rootView.findViewById(f.txt3);
        TextView txt4 = (TextView) rootView.findViewById(f.txt4);
        TextView txt5 = (TextView) rootView.findViewById(f.txt5);
        TextView txt6 = (TextView) rootView.findViewById(f.txt6);
        this.txtArray = new TextView[]{txt1, txt2, txt3, txt4, txt5, txt6};
        return rootView;
    }

    public void updateView(Context context, CategoryTag6Item item, t viewController, final int position) {
        if (item != null && item.categoryStruct != null && !TextUtils.isEmpty(item.categoryStruct.name)) {
            final CategoryStructItem itemData = item.categoryStruct;
            if (!TextUtils.isEmpty(item.categoryStruct.icon)) {
                h.a(context, itemData.icon, this.icon);
            }
            this.txtTitile.setText(itemData.name);
            this.layoutTitle.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (CategoryTag6Layout.this.mOnChildClickListener != null) {
                        CategoryTag6Layout.this.mOnChildClickListener.onClickConts(itemData, null, position, 0);
                    }
                }
            });
            List<PropertyTag> displayList = new ArrayList();
            if (item.categoryStruct.property_tags != null) {
                Iterator i$ = itemData.property_tags.iterator();
                while (i$.hasNext()) {
                    PropertyTag tag = (PropertyTag) i$.next();
                    if (!tag.hide) {
                        displayList.add(tag);
                    }
                }
            }
            int i = 0;
            while (i < this.txtArray.length) {
                if (i >= displayList.size() || displayList.get(i) == null || ((PropertyTag) displayList.get(i)).name == null) {
                    this.txtArray[i].setText("");
                    this.txtArray[i].setOnClickListener(null);
                    this.txtArray[i].setBackground(null);
                } else {
                    final int horPosition = i + 1;
                    final String name = ((PropertyTag) displayList.get(i)).name;
                    this.txtArray[i].setText(name);
                    final int i2 = position;
                    this.txtArray[i].setOnClickListener(new OnClickListener() {
                        public void onClick(View v) {
                            if (CategoryTag6Layout.this.mOnChildClickListener != null) {
                                CategoryTag6Layout.this.mOnChildClickListener.onClickConts(itemData, name, i2, horPosition);
                            }
                        }
                    });
                }
                i++;
            }
        }
    }

    protected void updateLayoutMargins(Context context, CategoryTag6Item item) {
    }
}
