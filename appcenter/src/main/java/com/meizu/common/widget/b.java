package com.meizu.common.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class b extends a {
    private boolean a = true;
    protected final Context b;
    protected int c;
    protected int d;
    protected int e;
    protected a[] f;
    protected boolean g;
    private boolean h;

    public static class a {
        boolean a;
        boolean b;
        int c;
        int d;
        int e;
        int f;
        int g;
        ArrayList<b> h = new ArrayList();
        ArrayList<b> i = new ArrayList();

        public a(boolean showIfEmpty, boolean hasHeader, int itemCount) {
            this.a = showIfEmpty;
            this.b = hasHeader;
            this.e = itemCount;
        }
    }

    public class b {
        public View a;
        public Object b;
        public boolean c;
    }

    protected abstract View a(int i, int i2, int i3, int i4, View view, ViewGroup viewGroup);

    protected abstract Object f(int i, int i2);

    protected abstract long g(int i, int i2);

    public b(Context context, int initialCapacity) {
        this.b = context;
        this.f = new a[initialCapacity];
    }

    protected int a(a partition) {
        if (this.e >= this.f.length) {
            a[] newAdapters = new a[(this.e + 10)];
            System.arraycopy(this.f, 0, newAdapters, 0, this.e);
            this.f = newAdapters;
        }
        a[] aVarArr = this.f;
        int i = this.e;
        this.e = i + 1;
        aVarArr[i] = partition;
        c_();
        notifyDataSetChanged();
        return this.e - 1;
    }

    public a a(int partitionIndex) {
        if (partitionIndex < this.e) {
            return this.f[partitionIndex];
        }
        throw new ArrayIndexOutOfBoundsException(partitionIndex);
    }

    public void a(int partitionIndex, boolean hasHeader) {
        this.f[partitionIndex].b = hasHeader;
        c_();
    }

    protected void c_() {
        this.g = false;
    }

    protected void b() {
        if (!this.g) {
            this.c = 0;
            this.d = 0;
            int i = 0;
            while (i < this.e) {
                this.f[i].f = this.f[i].h.size();
                this.f[i].g = this.f[i].i.size();
                this.f[i].d = (this.f[i].f + this.f[i].e) + this.f[i].g;
                int size = this.f[i].d;
                if (this.f[i].b && (size != 0 || this.f[i].a)) {
                    size++;
                }
                this.f[i].c = size;
                this.c += size;
                this.d += this.f[i].e;
                i++;
            }
            this.g = true;
        }
    }

    public int getCount() {
        b();
        return this.c;
    }

    protected boolean a(int partitionIndex, int offset) {
        if (offset < 0 || offset >= this.f[partitionIndex].f) {
            return false;
        }
        return true;
    }

    protected boolean b(int partitionIndex, int offset) {
        if (offset >= this.f[partitionIndex].d - this.f[partitionIndex].g) {
            return true;
        }
        return false;
    }

    public int getViewTypeCount() {
        return c() + 1;
    }

    public int c() {
        return 1;
    }

    public int getItemViewType(int position) {
        b();
        int start = 0;
        int i = 0;
        while (i < this.e) {
            int end = start + this.f[i].c;
            if (position < start || position >= end) {
                start = end;
                i++;
            } else {
                int offset = position - start;
                if (this.f[i].b) {
                    offset--;
                }
                if (offset == -1) {
                    return 0;
                }
                if (a(i, offset) || b(i, offset)) {
                    return -2;
                }
                return c(i, position);
            }
        }
        throw new ArrayIndexOutOfBoundsException(position);
    }

    protected int c(int partitionIndex, int position) {
        return 1;
    }

    protected int d(int partitionIndex, int offset) {
        if (offset == -1) {
            return 0;
        }
        if (offset == 0 && this.f[partitionIndex].d == 1) {
            return 4;
        }
        if (offset == 0) {
            return 1;
        }
        if (offset == this.f[partitionIndex].d - 1) {
            return 3;
        }
        return 2;
    }

    private boolean a(ArrayList<b> infos) {
        Iterator i$ = infos.iterator();
        while (i$.hasNext()) {
            if (!((b) i$.next()).c) {
                return false;
            }
        }
        return true;
    }

    public boolean areAllItemsEnabled() {
        int i = 0;
        while (i < this.e) {
            if (this.f[i].b || !a(this.f[i].h) || !a(this.f[i].i)) {
                return false;
            }
            i++;
        }
        return true;
    }

    public boolean isEnabled(int position) {
        b();
        int start = 0;
        int i = 0;
        while (i < this.e) {
            int end = start + this.f[i].c;
            if (position < start || position >= end) {
                start = end;
                i++;
            } else {
                int offset = position - start;
                if (this.f[i].b) {
                    offset--;
                }
                if (offset == -1) {
                    return false;
                }
                if (a(i, offset)) {
                    return ((b) this.f[i].h.get(offset)).c;
                }
                if (!b(i, offset)) {
                    return e(i, offset);
                }
                return ((b) this.f[i].i.get(offset - (this.f[i].d - this.f[i].g))).c;
            }
        }
        return false;
    }

    protected boolean e(int partitionIndex, int offset) {
        return true;
    }

    public Object getItem(int position) {
        b();
        int start = 0;
        int i = 0;
        while (i < this.e) {
            int end = start + this.f[i].c;
            if (position < start || position >= end) {
                start = end;
                i++;
            } else {
                int offset = position - start;
                if (this.f[i].b) {
                    offset--;
                }
                if (offset == -1) {
                    return null;
                }
                if (a(i, offset)) {
                    return ((b) this.f[i].h.get(offset)).b;
                }
                if (!b(i, offset)) {
                    return f(i, offset);
                }
                return ((b) this.f[i].i.get(offset - (this.f[i].d - this.f[i].g))).b;
            }
        }
        return null;
    }

    public long getItemId(int position) {
        b();
        int start = 0;
        int i = 0;
        while (i < this.e) {
            int end = start + this.f[i].c;
            if (position < start || position >= end) {
                start = end;
                i++;
            } else {
                int offset = position - start;
                if (this.f[i].b) {
                    offset--;
                }
                if (offset == -1) {
                    return 0;
                }
                if (a(i, offset) || b(i, offset)) {
                    return -1;
                }
                return g(i, offset);
            }
        }
        return 0;
    }

    public void a(boolean enabled) {
        this.a = enabled;
        if (enabled && this.h) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        if (this.a) {
            this.h = false;
            super.notifyDataSetChanged();
            return;
        }
        this.h = true;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        b();
        int start = 0;
        int i = 0;
        while (i < this.e) {
            int end = start + this.f[i].c;
            if (position < start || position >= end) {
                start = end;
                i++;
            } else {
                View view;
                int offset = position - start;
                if (this.f[i].b) {
                    offset--;
                }
                int itemBgType = d(i, offset);
                if (offset == -1) {
                    view = a(position, i, convertView, parent);
                } else if (a(i, offset)) {
                    view = ((b) this.f[i].h.get(offset)).a;
                } else if (b(i, offset)) {
                    view = ((b) this.f[i].i.get(offset - (this.f[i].d - this.f[i].g))).a;
                } else {
                    view = a(position, i, offset, itemBgType, convertView, parent);
                }
                if (view != null) {
                    return view;
                }
                throw new NullPointerException("View should not be null, partition: " + i + " position: " + position);
            }
        }
        throw new ArrayIndexOutOfBoundsException(position);
    }

    protected View a(int position, int partitionIndex, View convertView, ViewGroup parent) {
        View view = convertView != null ? convertView : a(this.b, position, partitionIndex, parent);
        a(view, this.b, position, partitionIndex);
        return view;
    }

    protected View a(Context context, int position, int partitionIndex, ViewGroup parent) {
        return null;
    }

    protected void a(View v, Context context, int position, int partitionIndex) {
    }
}
