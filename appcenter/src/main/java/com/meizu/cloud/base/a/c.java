package com.meizu.cloud.base.a;

import android.view.View;
import android.widget.TextView;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.base.a.e.d;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class c<T extends Blockable> extends e<T> {
    protected b e = new b(this);

    public class a extends com.meizu.cloud.base.a.e.a {
        public int a;
        public TextView b;
        public TextView c;
        public View d;
        final /* synthetic */ c e;

        public a(c cVar, View itemView, int viewType) {
            this.e = cVar;
            super(cVar, itemView, false);
            this.a = viewType;
        }
    }

    public class b extends d {
        protected final LinkedHashSet<Class<Blockable>> a = new LinkedHashSet();
        final /* synthetic */ c b;

        public b(c cVar) {
            this.b = cVar;
        }

        public boolean a(Class<Blockable> clz) {
            return this.a.add(clz);
        }

        public int b(Class<Blockable> clz) {
            int type = 0;
            Iterator<Class<Blockable>> iterator = this.a.iterator();
            while (iterator.hasNext() && ((Class) iterator.next()) != clz) {
                type++;
            }
            return type;
        }

        public Class<Blockable> a(int viewType) {
            List<Class<Blockable>> blockables = new ArrayList();
            blockables.addAll(this.a);
            return (Class) blockables.get(viewType);
        }
    }

    public int getItemViewType(int position) {
        if (this.i && position == 0) {
            return -1;
        }
        if (this.j && position == getItemCount() - 1) {
            return -2;
        }
        Blockable blockable = (Blockable) b(position);
        this.e.a(blockable.getBlockClass());
        return this.e.b(blockable.getBlockClass());
    }
}
