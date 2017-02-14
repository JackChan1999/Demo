package com.meizu.cloud.base.a;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import flyme.support.v7.widget.RecyclerView.Adapter;
import flyme.support.v7.widget.RecyclerView.ViewHolder;
import java.util.List;

public abstract class d<D> extends Adapter<a> {
    private List<D> a;
    protected b f;
    protected c g;
    protected boolean h;
    protected boolean i;

    public class a extends ViewHolder implements OnClickListener, OnLongClickListener {
        public int e;
        final /* synthetic */ d f;

        public a(d dVar, View itemView) {
            this.f = dVar;
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public a(d dVar, View itemView, boolean itemClickable) {
            this.f = dVar;
            super(itemView);
            if (itemClickable) {
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }
        }

        public void onClick(View v) {
            if (this.f.f != null) {
                this.f.f.onItemClick(v, this.e);
            }
        }

        public boolean onLongClick(View v) {
            if (this.f.g != null) {
                this.f.g.a(v, this.e);
            }
            return false;
        }
    }

    public interface b {
        void onItemClick(View view, int i);
    }

    public interface c {
        boolean a(View view, int i);
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

    public void b(List<D> dataList) {
        if (this.a == null) {
            this.a = dataList;
        } else {
            this.a.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public void a(b listener) {
        this.f = listener;
    }

    public int getItemCount() {
        int count = 0;
        if (this.h) {
            count = 0 + 1;
        }
        if (this.i) {
            count++;
        }
        if (this.a != null) {
            return count + this.a.size();
        }
        return count;
    }

    public int getItemViewType(int position) {
        if (this.h && position == 0) {
            return -1;
        }
        if (this.i && position == getItemCount() - 1) {
            return -2;
        }
        return 0;
    }

    public int b(int position) {
        if (this.h) {
            return position - 1;
        }
        return position;
    }

    private D a(int index) {
        if (this.a == null || index < 0 || index >= this.a.size()) {
            return null;
        }
        return this.a.get(index);
    }

    public D c(int position) {
        return a(b(position));
    }

    public int d() {
        if (this.a != null) {
            return this.a.size();
        }
        return 0;
    }

    public List<D> e() {
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
        holder.e = position;
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

    public void f() {
        this.i = true;
        notifyDataSetChanged();
    }

    public void b() {
        this.i = false;
        notifyDataSetChanged();
    }

    public void g() {
        if (this.h) {
            notifyItemChanged(0);
        }
    }
}
