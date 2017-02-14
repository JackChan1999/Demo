package com.meizu.cloud.base.a;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import flyme.support.v7.widget.RecyclerView.Adapter;
import flyme.support.v7.widget.RecyclerView.ViewHolder;
import java.util.List;

public abstract class e<D> extends Adapter<a> {
    private List<D> a;
    protected b g;
    protected c h;
    protected boolean i;
    protected boolean j;

    public class a extends ViewHolder implements OnClickListener, OnLongClickListener {
        public D l;
        final /* synthetic */ e m;

        public a(e eVar, View itemView) {
            this.m = eVar;
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public a(e eVar, View itemView, boolean itemClickable) {
            this.m = eVar;
            super(itemView);
            if (itemClickable) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }
        }

        public void onClick(View v) {
            if (this.m.g != null) {
                this.m.g.a(v, this.l);
            }
        }

        public boolean onLongClick(View v) {
            if (this.m.h != null) {
                this.m.h.a_(v, this.l);
            }
            return false;
        }
    }

    public interface b<D> {
        void a(View view, D d);
    }

    public interface c<D> {
        boolean a_(View view, D d);
    }

    public static class d {
    }

    public abstract a a(ViewGroup viewGroup, int i);

    public abstract void a(a aVar, int i);

    public /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i) {
        b((a) viewHolder, i);
    }

    public /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return b(viewGroup, i);
    }

    public void a(List<D> dataList) {
        this.a = dataList;
        notifyDataSetChanged();
    }

    public void a(b<D> listener) {
        this.g = listener;
    }

    public void a(c<D> listener) {
        this.h = listener;
    }

    public int getItemCount() {
        int count = 0;
        if (this.i) {
            count = 0 + 1;
        }
        if (this.j) {
            count++;
        }
        if (this.a != null) {
            return count + this.a.size();
        }
        return count;
    }

    public int getItemViewType(int position) {
        if (this.i && position == 0) {
            return -1;
        }
        if (this.j && position == getItemCount() - 1) {
            return -2;
        }
        return 0;
    }

    public int a(int position) {
        if (this.i) {
            return position - 1;
        }
        return position;
    }

    private D c(int index) {
        if (this.a == null || index < 0 || index >= this.a.size()) {
            return null;
        }
        return this.a.get(index);
    }

    public D b(int position) {
        return c(a(position));
    }

    public int b() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public List<D> c() {
        return this.a;
    }

    public a b(ViewGroup parent, int viewType) {
        a holder = null;
        if (viewType >= 0) {
            holder = a(parent, viewType);
        } else if (viewType == -2) {
            holder = b(parent);
        } else if (viewType == -1) {
            holder = a(parent);
        }
        if (holder != null) {
            return holder;
        }
        throw new NullPointerException("holder == null: " + getClass() + " viewType: " + viewType);
    }

    public void b(a holder, int position) {
        holder.l = b(position);
        int viewType = getItemViewType(position);
        if (viewType >= 0) {
            a(holder, position);
        } else if (viewType == -2) {
            b(holder);
        } else if (viewType == -1) {
            a(holder);
        }
    }

    public a a(ViewGroup parent) {
        return new a(this, new View(parent.getContext()));
    }

    public a b(ViewGroup parent) {
        return new a(this, new View(parent.getContext()));
    }

    public void a(a aVar) {
    }

    public void b(a aVar) {
    }
}
