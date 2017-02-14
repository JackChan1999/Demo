package com.meizu.common.widget;

import android.util.SparseIntArray;
import android.widget.SectionIndexer;

public abstract class i extends j implements SectionIndexer {
    private SectionIndexer a;
    private int h;
    private boolean i;
    private boolean j;
    private SparseIntArray k;
    private a l;

    public static final class a {
        public boolean a;
        public boolean b;
        public String c;
        private int d;
    }

    public boolean d() {
        return this.i;
    }

    public Object[] getSections() {
        if (this.a != null) {
            return this.a.getSections();
        }
        return new String[]{" "};
    }

    public int getPositionForSection(int sectionIndex) {
        if (this.a == null) {
            return -1;
        }
        if (sectionIndex < 0) {
            return 0;
        }
        int pos = this.a.getPositionForSection(sectionIndex) + this.f[this.h].f;
        if (!this.j) {
            return pos;
        }
        for (int i = 0; i < sectionIndex; i++) {
            if (this.k.indexOfKey(i) >= 0) {
                pos++;
            }
        }
        return pos;
    }

    public int getSectionForPosition(int position) {
        if (this.a == null) {
            return -1;
        }
        if (b(this.h, position) || position > this.f[this.h].d - 1) {
            return getSections().length - 1;
        }
        int pos = position - this.f[this.h].f;
        if (pos < 0) {
            return -1;
        }
        if (this.j) {
            int i = 0;
            while (i < this.k.size() && this.k.valueAt(i) < position) {
                pos--;
                i++;
            }
        }
        return this.a.getSectionForPosition(pos);
    }

    public a b(int position) {
        if (this.l.d == position) {
            return this.l;
        }
        this.l.d = position;
        if (d()) {
            boolean z;
            int section = getSectionForPosition(position);
            if (section == -1 || getPositionForSection(section) != position) {
                this.l.a = false;
                this.l.c = null;
            } else {
                this.l.a = true;
                this.l.c = (String) getSections()[section];
            }
            a aVar = this.l;
            if (getPositionForSection(section + 1) - 1 == position) {
                z = true;
            } else {
                z = false;
            }
            aVar.b = z;
        } else {
            this.l.a = false;
            this.l.b = false;
            this.l.c = null;
        }
        return this.l;
    }

    protected void b() {
        if (!this.g) {
            super.b();
            e();
        }
    }

    private void e() {
        this.k.clear();
        if (this.j && this.a != null && this.f[this.h].e > 0) {
            int hvCount = this.f[this.h].f;
            int section = -1;
            int pos = 0;
            while (pos < this.f[this.h].e) {
                int nextSection = this.a.getSectionForPosition(pos);
                if (section == nextSection) {
                    break;
                }
                section = nextSection;
                this.k.put(section, (pos + hvCount) + this.k.size());
                int nextPos = this.a.getPositionForSection(section + 1);
                if (pos == nextPos) {
                    break;
                }
                pos = nextPos;
            }
            int headerCount = this.k.size();
            b.a aVar = this.f[this.h];
            aVar.d += headerCount;
            aVar = this.f[this.h];
            aVar.c += headerCount;
            this.c += headerCount;
        }
    }

    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    public boolean areAllItemsEnabled() {
        b();
        if (!this.j || this.k.size() <= 0) {
            return super.areAllItemsEnabled();
        }
        return false;
    }
}
