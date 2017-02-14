package com.meizu.cloud.app.a;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.meizu.cloud.app.block.structbuilder.BlockItemBuilder;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout.OnChildClickListener;
import com.meizu.cloud.app.core.t;

public class e extends f<AbsBlockItem> {
    protected OnChildClickListener a;
    private t b;

    protected class a extends com.meizu.cloud.base.a.d.a {
        public AbsBlockLayout<AbsBlockItem> a;
        final /* synthetic */ e b;

        public a(e eVar, View itemView) {
            this.b = eVar;
            super(eVar, itemView);
        }
    }

    public e(Context context, t viewController, OnChildClickListener onChildClickListener) {
        super(context);
        this.b = viewController;
        this.a = onChildClickListener;
    }

    public int getItemViewType(int position) {
        if (this.h && position == 0) {
            return -1;
        }
        if (this.i && position == getItemCount() - 1) {
            return -2;
        }
        AbsBlockItem item = (AbsBlockItem) c(position);
        if (item != null) {
            return item.style;
        }
        return Integer.MIN_VALUE;
    }

    public com.meizu.cloud.base.a.d.a a(ViewGroup parent, int viewType) {
        View view;
        AbsBlockLayout absBlockLayout = BlockItemBuilder.build(viewType, parent);
        if (absBlockLayout != null) {
            view = absBlockLayout.createView(this.d, null);
            absBlockLayout.setOnChildClickListener(this.a);
        } else {
            view = new View(this.d);
        }
        a holder = new a(this, view);
        holder.a = absBlockLayout;
        return holder;
    }

    public void a(com.meizu.cloud.base.a.d.a holder, int position) {
        a absBlockHolder = (a) holder;
        if (absBlockHolder.a != null) {
            absBlockHolder.a.setOnChildClickListener(this.a);
            if (this.a != null) {
                this.a.onBlockExposure((AbsBlockItem) c(position), position);
            }
            absBlockHolder.a.updateView(this.d, c(position), this.b, position);
        }
    }
}
