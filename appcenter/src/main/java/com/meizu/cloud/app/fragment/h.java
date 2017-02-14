package com.meizu.cloud.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.meizu.cloud.app.a.c.b;
import com.meizu.cloud.app.block.Blockable;
import com.meizu.cloud.app.block.customblock.PartitionItem;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.u;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.f;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.a.e;
import com.meizu.cloud.base.a.e.c;
import com.meizu.cloud.base.b.k;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class h extends k<LinkedList<Blockable>> implements b, e.b<Blockable>, c<Blockable> {
    public static final Comparator<com.meizu.cloud.app.downlad.e> b = new Comparator<com.meizu.cloud.app.downlad.e>() {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((com.meizu.cloud.app.downlad.e) obj, (com.meizu.cloud.app.downlad.e) obj2);
        }

        public int a(com.meizu.cloud.app.downlad.e lhs, com.meizu.cloud.app.downlad.e rhs) {
            if (lhs.v() == rhs.v()) {
                return 0;
            }
            return lhs.v() - rhs.v() < 0 ? 1 : -1;
        }
    };
    protected t a;
    private a c;
    private com.meizu.cloud.app.a.c d;
    private List<PartitionItem> h;
    private LinkedList<com.meizu.cloud.app.downlad.e> i;
    private LinkedList<com.meizu.cloud.app.downlad.e> j;
    private LinkedList<com.meizu.cloud.app.downlad.e> k;
    private LinkedList<com.meizu.cloud.app.downlad.e> l;
    private LinkedList<com.meizu.cloud.app.downlad.e> m;
    private List<PartitionItem> n;
    private LinkedList<com.meizu.cloud.app.downlad.e> o;
    private f.k p = new f.k(this) {
        final /* synthetic */ h a;

        {
            this.a = r1;
        }

        public void onDownloadStateChanged(com.meizu.cloud.app.downlad.e wrapper) {
            if (this.a.d != null) {
                this.a.b(wrapper);
            }
        }

        public void onDownloadProgress(com.meizu.cloud.app.downlad.e wrapper) {
            if (this.a.d != null) {
                this.a.c(wrapper);
            }
        }

        public void onFetchStateChange(com.meizu.cloud.app.downlad.e wrapper) {
            if (this.a.d != null) {
                this.a.b(wrapper);
            }
        }

        public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
            if (this.a.d != null) {
                this.a.b(wrapper);
            }
        }

        public void b(com.meizu.cloud.app.downlad.e wrapper) {
            if (this.a.d != null) {
                this.a.b(wrapper);
            }
        }

        public void a(com.meizu.cloud.app.downlad.e wrapper) {
            if (this.a.d != null) {
                this.a.b(wrapper);
            }
        }
    };

    public static class a extends android.support.v4.content.a<LinkedList<Blockable>> {
        private LinkedList<Blockable> o;
        private d p;
        private List<PartitionItem> q;
        private LinkedList<com.meizu.cloud.app.downlad.e> r;
        private LinkedList<com.meizu.cloud.app.downlad.e> s;
        private LinkedList<com.meizu.cloud.app.downlad.e> t;
        private LinkedList<com.meizu.cloud.app.downlad.e> u;
        private LinkedList<com.meizu.cloud.app.downlad.e> v;
        private List<PartitionItem> w;
        private LinkedList<com.meizu.cloud.app.downlad.e> x;

        public /* synthetic */ void a(Object obj) {
            b((LinkedList) obj);
        }

        public /* synthetic */ void b(Object obj) {
            a((LinkedList) obj);
        }

        public /* synthetic */ Object d() {
            return z();
        }

        public a(Context context, List<PartitionItem> downloadingPartitionBlockItem, LinkedList<com.meizu.cloud.app.downlad.e> downloadingBlockItem, LinkedList<com.meizu.cloud.app.downlad.e> waittingBlockItem, LinkedList<com.meizu.cloud.app.downlad.e> pauseBlockItem, LinkedList<com.meizu.cloud.app.downlad.e> installingBlockItem, LinkedList<com.meizu.cloud.app.downlad.e> errorBlockItem, List<PartitionItem> installedPartitionBlockItem, LinkedList<com.meizu.cloud.app.downlad.e> installedBlockItem) {
            super(context);
            this.p = d.a(context);
            this.q = downloadingPartitionBlockItem;
            this.r = downloadingBlockItem;
            this.s = waittingBlockItem;
            this.t = pauseBlockItem;
            this.u = installingBlockItem;
            this.v = errorBlockItem;
            this.w = installedPartitionBlockItem;
            this.x = installedBlockItem;
        }

        public LinkedList<Blockable> z() {
            if (this.o == null) {
                this.o = new LinkedList();
            } else {
                this.o.clear();
            }
            this.o.addAll(A());
            this.o.addAll(B());
            if (this.o.size() > 0) {
                this.o.addAll(0, this.q);
            }
            List<com.meizu.cloud.app.downlad.e> completeTask = C();
            if (completeTask.size() > 0) {
                this.o.addAll(this.w);
            }
            this.o.addAll(completeTask);
            return this.o;
        }

        public void a(LinkedList<Blockable> data) {
            if (k() && data != null) {
                c(data);
            }
            LinkedList<Blockable> oldApps = this.o;
            this.o = data;
            if (i()) {
                super.b((Object) data);
            }
            if (oldApps != null) {
                c(oldApps);
            }
        }

        protected void m() {
            if (this.o != null) {
                a(this.o);
            }
            if (v() || this.o == null) {
                o();
            }
        }

        protected void q() {
            n();
        }

        public void b(LinkedList<Blockable> data) {
            super.a(data);
            c(data);
        }

        protected void u() {
            super.u();
            q();
            if (this.o != null) {
                c(this.o);
                this.o = null;
            }
        }

        protected void c(LinkedList<Blockable> linkedList) {
        }

        private synchronized List<com.meizu.cloud.app.downlad.e> A() {
            List<com.meizu.cloud.app.downlad.e> sortedAppList;
            sortedAppList = new ArrayList();
            this.r.clear();
            this.r.addAll(this.p.a(1, 3));
            sortedAppList.addAll(this.r);
            this.s.clear();
            this.s.addAll(this.p.b(1, 3));
            sortedAppList.addAll(this.s);
            this.t.clear();
            this.t.addAll(this.p.f(1, 3));
            sortedAppList.addAll(this.t);
            this.u.clear();
            this.u.addAll(this.p.j(1, 3));
            sortedAppList.addAll(this.u);
            return sortedAppList;
        }

        private synchronized List<com.meizu.cloud.app.downlad.e> B() {
            List<com.meizu.cloud.app.downlad.e> errorList = this.p.g();
            Collections.sort(errorList, h.b);
            this.v.clear();
            this.v.addAll(errorList);
            return this.v;
        }

        private synchronized List<com.meizu.cloud.app.downlad.e> C() {
            List<com.meizu.cloud.app.downlad.e> completeTaskList = this.p.f();
            Collections.sort(completeTaskList, h.b);
            this.x.clear();
            this.x.addAll(completeTaskList);
            return this.x;
        }
    }

    public /* synthetic */ boolean a_(View view, Object obj) {
        return a(view, (Blockable) obj);
    }

    public void onEventMainThread(com.meizu.cloud.app.c.a appStateChangeEvent) {
        if (appStateChangeEvent.c == -1) {
            Iterator i$ = this.o.iterator();
            while (i$.hasNext()) {
                com.meizu.cloud.app.downlad.e downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                if (downloadWrapper.g().equals(appStateChangeEvent.b)) {
                    a(downloadWrapper, this.o);
                    return;
                }
            }
        }
    }

    public void onEventMainThread(com.meizu.cloud.app.c.f downloadTaskEvent) {
        Iterator i$ = this.o.iterator();
        while (i$.hasNext()) {
            com.meizu.cloud.app.downlad.e downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
            if (downloadWrapper.g().equals(downloadTaskEvent.a)) {
                a(downloadWrapper, this.o);
                break;
            }
        }
        i$ = this.i.iterator();
        while (i$.hasNext()) {
            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
            if (downloadWrapper.g().equals(downloadTaskEvent.a)) {
                a(downloadWrapper, this.i);
                break;
            }
        }
        i$ = this.m.iterator();
        while (i$.hasNext()) {
            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
            if (downloadWrapper.g().equals(downloadTaskEvent.a)) {
                a(downloadWrapper, this.m);
                return;
            }
        }
    }

    protected l a(l anEnum) {
        if (f.d(anEnum)) {
            if (anEnum instanceof n) {
                return n.CANCEL;
            }
            if (anEnum instanceof f.c) {
                return f.c.TASK_REMOVED;
            }
            if (anEnum instanceof j) {
                return j.CANCEL;
            }
        }
        return null;
    }

    public void a(View v, PartitionItem partitionItem) {
        int delayTime = (int) (((double) this.i.size()) + (((double) this.j.size()) * 0.8d));
        if (partitionItem.id == com.meizu.cloud.b.a.f.partition_downloading) {
            if (((Boolean) v.getTag(partitionItem.id)).booleanValue()) {
                ((TextView) v).setText(partitionItem.btnText2);
                v.setTag(partitionItem.id, Boolean.valueOf(false));
                partitionItem.tag = Boolean.valueOf(false);
                d.a(getActivity()).a(1);
                com.meizu.cloud.statistics.b.a().a("pause_all", this.mPageName, null);
            } else {
                ((TextView) v).setText(partitionItem.btnText);
                v.setTag(partitionItem.id, Boolean.valueOf(true));
                partitionItem.tag = Boolean.valueOf(true);
                d.a(getActivity()).k(1, 3);
                com.meizu.cloud.statistics.b.a().a("start_all", this.mPageName, null);
            }
        } else if (partitionItem.id == com.meizu.cloud.b.a.f.partition_finished) {
            d.a(getActivity()).b();
            this.o.clear();
            List<Blockable> blockables = this.d.c();
            int endIndex = e().c().size();
            List<Blockable> dataList = new ArrayList(e().b());
            dataList.addAll(blockables);
            int position = blockables.indexOf(partitionItem);
            blockables.removeAll(dataList.subList(position, endIndex));
            this.d.notifyItemRangeRemoved(position, endIndex);
        }
        v.setEnabled(false);
        final View view = v;
        v.postDelayed(new Runnable(this) {
            final /* synthetic */ h b;

            public void run() {
                view.setEnabled(true);
            }
        }, (long) (delayTime * 200));
    }

    public void a(View v, com.meizu.cloud.app.downlad.e wrapper) {
        wrapper.m().install_page = this.mPageName;
        wrapper.m().page_info = this.mPageInfo;
        if (wrapper.f() == f.f.INSTALL_SUCCESS) {
            t.a(getActivity(), wrapper.g());
            com.meizu.cloud.statistics.b.a().a("open", this.mPageName, com.meizu.cloud.statistics.c.b(wrapper.m()));
            return;
        }
        this.a.a(wrapper.U());
    }

    private synchronized void b(com.meizu.cloud.app.downlad.e wrapper) {
        boolean z = true;
        synchronized (this) {
            l anEnum = wrapper.f();
            if (anEnum == n.CANCEL || anEnum == j.CANCEL) {
                a(wrapper, this.j);
            } else if (anEnum == f.c.TASK_REMOVED) {
                if (this.i.contains(wrapper)) {
                    a(wrapper, this.i);
                } else if (this.j.contains(wrapper)) {
                    a(wrapper, this.j);
                } else if (this.k.contains(wrapper)) {
                    a(wrapper, this.k);
                } else if (this.m.contains(wrapper)) {
                    a(wrapper, this.m);
                }
            } else if (anEnum == n.FETCHING || anEnum == j.PAYING) {
                int index = this.d.c().indexOf(this.h.get(0));
                LinkedList linkedList = this.j;
                if (index == -1) {
                    z = false;
                }
                a(wrapper, linkedList, z, true);
            } else if (anEnum == n.FAILURE || anEnum == f.c.TASK_ERROR || anEnum == com.meizu.cloud.app.downlad.f.h.PATCHED_FAILURE || anEnum == f.f.INSTALL_FAILURE || anEnum == j.FAILURE) {
                b(wrapper, this.m);
            } else if (anEnum == f.c.TASK_STARTED) {
                c(wrapper);
            } else if (anEnum == f.c.TASK_COMPLETED) {
                b(wrapper, this.l);
            } else if (anEnum == f.f.INSTALL_SUCCESS) {
                b(wrapper, this.o);
            } else if (anEnum != f.f.DELETE_SUCCESS) {
                c(wrapper);
            } else if (!wrapper.F()) {
                a(wrapper, this.o);
            }
        }
    }

    private synchronized void c(com.meizu.cloud.app.downlad.e wrapper) {
        if (this.d != null && d() != null) {
            for (int i = 0; i <= this.d.getItemCount(); i++) {
                Blockable item = (Blockable) this.d.b(i);
                if (item != null && (item instanceof com.meizu.cloud.app.downlad.e)) {
                    com.meizu.cloud.app.downlad.e downloadWrapper = (com.meizu.cloud.app.downlad.e) item;
                    if (!TextUtils.isEmpty(downloadWrapper.g()) && downloadWrapper.g().equals(wrapper.g())) {
                        d(wrapper);
                        break;
                    }
                }
            }
        }
    }

    private synchronized void d(com.meizu.cloud.app.downlad.e wrapper) {
        View rootView = d().findViewWithTag(wrapper.g());
        if (rootView != null) {
            TextView description = (TextView) rootView.findViewById(com.meizu.cloud.b.a.f.description);
            this.a.a(wrapper, (CirProButton) rootView.findViewById(com.meizu.cloud.b.a.f.btnInstall));
            if (wrapper.f() == f.f.INSTALL_SUCCESS) {
                description.setText(this.d.a(wrapper));
            } else {
                description.setText(this.d.a(wrapper));
            }
        } else {
            List<Blockable> blockables = e().c();
            int index = blockables.indexOf(wrapper);
            if (index == -1) {
                for (int i = 0; i < blockables.size(); i++) {
                    Blockable blockable = (Blockable) blockables.get(i);
                    if ((blockable instanceof com.meizu.cloud.app.downlad.e) && ((com.meizu.cloud.app.downlad.e) blockable).g().equals(wrapper.g())) {
                        index = i;
                        break;
                    }
                }
            }
            e().notifyItemChanged(index);
        }
    }

    private synchronized int a(com.meizu.cloud.app.downlad.e wrapper, LinkedList<com.meizu.cloud.app.downlad.e> toDataList, boolean isHeaderExist, boolean isNotifyChange) {
        int insertIndex;
        boolean z = false;
        synchronized (this) {
            com.meizu.cloud.app.downlad.e downloadWrapper;
            LinkedList<Blockable> blockables = (LinkedList) this.d.c();
            if (isNotifyChange) {
                Iterator i$;
                boolean isRemoveSuccess = false;
                if (!this.i.contains(wrapper)) {
                    i$ = this.i.iterator();
                    while (i$.hasNext()) {
                        downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                        if (downloadWrapper.g().equals(wrapper.g())) {
                            isRemoveSuccess = this.i.remove(downloadWrapper);
                            break;
                        }
                    }
                }
                isRemoveSuccess = this.i.remove(wrapper);
                if (!isRemoveSuccess) {
                    if (!this.j.contains(wrapper)) {
                        i$ = this.j.iterator();
                        while (i$.hasNext()) {
                            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                            if (downloadWrapper.g().equals(wrapper.g())) {
                                isRemoveSuccess = this.j.remove(downloadWrapper);
                                break;
                            }
                        }
                    }
                    isRemoveSuccess = this.j.remove(wrapper);
                }
                if (!isRemoveSuccess) {
                    if (!this.k.contains(wrapper)) {
                        i$ = this.k.iterator();
                        while (i$.hasNext()) {
                            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                            if (downloadWrapper.g().equals(wrapper.g())) {
                                isRemoveSuccess = this.k.remove(downloadWrapper);
                                break;
                            }
                        }
                    }
                    isRemoveSuccess = this.k.remove(wrapper);
                }
                if (!isRemoveSuccess) {
                    if (!this.l.contains(wrapper)) {
                        i$ = this.l.iterator();
                        while (i$.hasNext()) {
                            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                            if (downloadWrapper.g().equals(wrapper.g())) {
                                isRemoveSuccess = this.l.remove(downloadWrapper);
                                break;
                            }
                        }
                    }
                    isRemoveSuccess = this.l.remove(wrapper);
                }
                if (!isRemoveSuccess) {
                    if (!this.m.contains(wrapper)) {
                        i$ = this.m.iterator();
                        while (i$.hasNext()) {
                            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                            if (downloadWrapper.g().equals(wrapper.g())) {
                                isRemoveSuccess = this.m.remove(downloadWrapper);
                                break;
                            }
                        }
                    }
                    isRemoveSuccess = this.m.remove(wrapper);
                }
                if (!isRemoveSuccess) {
                    if (!this.o.contains(wrapper)) {
                        i$ = this.o.iterator();
                        while (i$.hasNext()) {
                            downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                            if (downloadWrapper.g().equals(wrapper.g())) {
                                isRemoveSuccess = this.o.remove(downloadWrapper);
                                break;
                            }
                        }
                    }
                    isRemoveSuccess = this.o.remove(wrapper);
                }
            }
            insertIndex = -1;
            int insertCount = 1;
            boolean move2First = false;
            if (toDataList == this.o) {
                if (!isHeaderExist) {
                    toDataList.addFirst(wrapper);
                    blockables.add(wrapper);
                    if (toDataList == this.o) {
                        z = true;
                    }
                    insertIndex = b(z);
                    insertCount = 1 + 1;
                } else if (toDataList.size() != 0) {
                    downloadWrapper = (com.meizu.cloud.app.downlad.e) toDataList.getFirst();
                    toDataList.addFirst(wrapper);
                    insertIndex = blockables.indexOf(downloadWrapper);
                    blockables.add(insertIndex, wrapper);
                } else {
                    toDataList.addFirst(wrapper);
                    blockables.addLast(wrapper);
                    insertIndex = blockables.indexOf(wrapper);
                }
            } else if (!isHeaderExist) {
                toDataList.addFirst(wrapper);
                blockables.addFirst(wrapper);
                if (toDataList == this.o) {
                    z = true;
                }
                insertIndex = b(z);
                insertCount = 1 + 1;
                move2First = true;
            } else if (toDataList.size() != 0) {
                downloadWrapper = (com.meizu.cloud.app.downlad.e) toDataList.getLast();
                toDataList.addLast(wrapper);
                insertIndex = blockables.indexOf(downloadWrapper) + 1;
                blockables.add(insertIndex, wrapper);
            } else if (toDataList == this.i) {
                this.i.add(wrapper);
                insertIndex = blockables.indexOf(this.h.get(0)) + 1;
                blockables.add(insertIndex, wrapper);
            } else if (toDataList == this.j) {
                this.j.add(wrapper);
                if (this.i.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.i.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (isNotifyChange && !blockables.contains(wrapper)) {
                    insertIndex = blockables.indexOf(this.h.get(0)) + 1;
                    blockables.add(insertIndex, wrapper);
                }
            } else if (toDataList == this.k) {
                this.k.add(wrapper);
                if (this.j.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.j.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (this.i.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.i.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (isNotifyChange && !blockables.contains(wrapper)) {
                    insertIndex = blockables.indexOf(this.h.get(0)) + 1;
                    blockables.add(insertIndex, wrapper);
                }
            } else if (toDataList == this.l) {
                this.l.add(wrapper);
                if (this.k.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.k.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (this.j.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.j.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (this.i.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.i.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (isNotifyChange && !blockables.contains(wrapper)) {
                    insertIndex = blockables.indexOf(this.h.get(0)) + 1;
                    blockables.add(insertIndex, wrapper);
                }
            } else if (toDataList == this.m) {
                this.m.add(wrapper);
                if (this.l.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.l.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (this.k.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.k.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (this.j.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.j.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (this.i.size() > 0) {
                    insertIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) this.i.getLast()) + 1;
                    blockables.add(insertIndex, wrapper);
                } else if (isNotifyChange && !blockables.contains(wrapper)) {
                    insertIndex = blockables.indexOf(this.h.get(0)) + 1;
                    blockables.add(insertIndex, wrapper);
                }
            }
            if (isNotifyChange && insertIndex != -1) {
                if (insertCount == 1) {
                    this.d.notifyItemInserted(insertIndex);
                } else {
                    this.d.notifyItemRangeInserted(insertIndex, insertCount);
                }
                if (move2First) {
                    this.e.getLayoutManager().scrollToPosition(0);
                }
            }
        }
        return insertIndex;
    }

    private synchronized void a(com.meizu.cloud.app.downlad.e wrapper, LinkedList<com.meizu.cloud.app.downlad.e> fromDataList) {
        LinkedList<Blockable> blockables = (LinkedList) this.d.c();
        fromDataList.remove(wrapper);
        int removeIndex = blockables.indexOf(wrapper);
        if (removeIndex != -1) {
            blockables.remove(removeIndex);
            this.d.notifyItemRemoved(removeIndex);
        }
        int titleIndex = a(fromDataList == this.o);
        if (titleIndex != -1) {
            this.d.notifyItemRemoved(titleIndex);
        }
    }

    private int a(boolean isInstalledPartition) {
        int titleIndex = -1;
        LinkedList<Blockable> blockables = (LinkedList) this.d.c();
        if (blockables.size() > 0) {
            if (isInstalledPartition) {
                if (this.o.size() == 0) {
                    titleIndex = blockables.indexOf(this.n.get(0));
                    if (titleIndex != -1) {
                        blockables.remove(titleIndex);
                    }
                }
            } else if ((((this.i.size() + this.j.size()) + this.k.size()) + this.l.size()) + this.m.size() == 0) {
                titleIndex = blockables.indexOf(this.h.get(0));
                if (titleIndex != -1) {
                    blockables.remove(titleIndex);
                }
            }
        }
        return titleIndex;
    }

    private int b(boolean isInstalledPartition) {
        LinkedList<Blockable> blockables = (LinkedList) this.d.c();
        if (blockables.size() <= 0) {
            return -1;
        }
        int titleIndex;
        if (isInstalledPartition) {
            titleIndex = blockables.indexOf(this.n.get(0));
            if (titleIndex != -1 || this.o.size() <= 0) {
                return titleIndex;
            }
            titleIndex = blockables.lastIndexOf((com.meizu.cloud.app.downlad.e) this.o.getFirst());
            blockables.add(titleIndex, this.n.get(0));
            return titleIndex;
        }
        titleIndex = blockables.indexOf(this.h.get(0));
        if (titleIndex != -1 || (((this.i.size() + this.j.size()) + this.k.size()) + this.l.size()) + this.m.size() <= 0) {
            return titleIndex;
        }
        blockables.add(0, this.h.get(0));
        return 0;
    }

    private synchronized void b(com.meizu.cloud.app.downlad.e wrapper, LinkedList<com.meizu.cloud.app.downlad.e> toDataList) {
        Iterator i$;
        com.meizu.cloud.app.downlad.e downloadWrapper;
        LinkedList<Blockable> blockables = (LinkedList) this.d.c();
        boolean isRemoveSuccess = false;
        boolean isRemoveFromPackageName = false;
        if (!this.i.contains(wrapper)) {
            i$ = this.i.iterator();
            while (i$.hasNext()) {
                downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                if (downloadWrapper.g().equals(wrapper.g())) {
                    isRemoveSuccess = this.i.remove(downloadWrapper);
                    isRemoveFromPackageName = true;
                    break;
                }
            }
        }
        isRemoveSuccess = this.i.remove(wrapper);
        if (!isRemoveSuccess) {
            if (!this.j.contains(wrapper)) {
                i$ = this.j.iterator();
                while (i$.hasNext()) {
                    downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                    if (downloadWrapper.g().equals(wrapper.g())) {
                        isRemoveSuccess = this.j.remove(downloadWrapper);
                        isRemoveFromPackageName = true;
                        break;
                    }
                }
            }
            isRemoveSuccess = this.j.remove(wrapper);
        }
        if (!isRemoveSuccess) {
            if (!this.k.contains(wrapper)) {
                i$ = this.k.iterator();
                while (i$.hasNext()) {
                    downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                    if (downloadWrapper.g().equals(wrapper.g())) {
                        isRemoveSuccess = this.k.remove(downloadWrapper);
                        isRemoveFromPackageName = true;
                        break;
                    }
                }
            }
            isRemoveSuccess = this.k.remove(wrapper);
        }
        if (!isRemoveSuccess) {
            if (!this.l.contains(wrapper)) {
                i$ = this.l.iterator();
                while (i$.hasNext()) {
                    downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                    if (downloadWrapper.g().equals(wrapper.g())) {
                        isRemoveSuccess = this.l.remove(downloadWrapper);
                        isRemoveFromPackageName = true;
                        break;
                    }
                }
            }
            isRemoveSuccess = this.l.remove(wrapper);
        }
        if (!isRemoveSuccess) {
            if (!this.m.contains(wrapper)) {
                i$ = this.m.iterator();
                while (i$.hasNext()) {
                    downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                    if (downloadWrapper.g().equals(wrapper.g())) {
                        isRemoveSuccess = this.m.remove(downloadWrapper);
                        isRemoveFromPackageName = true;
                        break;
                    }
                }
            }
            isRemoveSuccess = this.m.remove(wrapper);
        }
        if (!isRemoveSuccess) {
            if (!this.o.contains(wrapper)) {
                i$ = this.o.iterator();
                while (i$.hasNext()) {
                    downloadWrapper = (com.meizu.cloud.app.downlad.e) i$.next();
                    if (downloadWrapper.g().equals(wrapper.g())) {
                        isRemoveSuccess = this.o.remove(downloadWrapper);
                        isRemoveFromPackageName = true;
                        break;
                    }
                }
            }
            isRemoveSuccess = this.o.remove(wrapper);
        }
        if (isRemoveSuccess) {
            int fromIndex = -1;
            if (isRemoveFromPackageName) {
                i$ = blockables.iterator();
                while (i$.hasNext()) {
                    Blockable blockable = (Blockable) i$.next();
                    if (blockable instanceof com.meizu.cloud.app.downlad.e) {
                        downloadWrapper = (com.meizu.cloud.app.downlad.e) blockable;
                        if (downloadWrapper.g().equals(wrapper.g())) {
                            fromIndex = blockables.indexOf(downloadWrapper);
                            break;
                        }
                    }
                }
            } else {
                fromIndex = blockables.indexOf(wrapper);
            }
            if (fromIndex != -1) {
                int toIndex;
                if (toDataList == this.o) {
                    if (toDataList.size() > 0) {
                        toIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) toDataList.getFirst());
                        if (toIndex != -1) {
                            toDataList.addFirst(wrapper);
                            blockables.add(toIndex, wrapper);
                        }
                    } else {
                        toIndex = a(wrapper, toDataList, this.d.c().indexOf(this.n.get(0)) != -1, false);
                    }
                } else if (toDataList.size() > 0) {
                    toIndex = blockables.indexOf((com.meizu.cloud.app.downlad.e) toDataList.getLast());
                    if (toIndex != -1) {
                        toDataList.addLast(wrapper);
                        toIndex++;
                        blockables.add(toIndex, wrapper);
                    }
                } else {
                    toIndex = a(wrapper, toDataList, this.d.c().indexOf(this.h.get(0)) != -1, false);
                }
                int titleIndex;
                if (toIndex != -1 && fromIndex != toIndex) {
                    this.d.notifyItemInserted(toIndex);
                    blockables.remove(fromIndex);
                    this.d.notifyItemRemoved(fromIndex);
                    if (toDataList == this.o) {
                        titleIndex = a(false);
                        if (titleIndex != -1) {
                            this.d.notifyItemRemoved(titleIndex);
                        }
                    }
                } else if (fromIndex == toIndex) {
                    blockables.remove(fromIndex);
                    titleIndex = a(toDataList != this.o);
                    if (titleIndex != -1) {
                        this.d.notifyItemChanged(titleIndex);
                    }
                    this.d.notifyItemChanged(fromIndex);
                } else {
                    this.d.notifyItemChanged(fromIndex);
                }
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.h = new ArrayList(1);
        this.h.add(new PartitionItem(getString(i.downloading), getString(i.all_pause), getString(i.all_start), true, com.meizu.cloud.b.a.f.partition_downloading));
        this.i = new LinkedList();
        this.j = new LinkedList();
        this.k = new LinkedList();
        this.l = new LinkedList();
        this.m = new LinkedList();
        this.n = new ArrayList(1);
        this.n.add(new PartitionItem(getString(i.installed), getString(i.clear_history), true, com.meizu.cloud.b.a.f.partition_finished));
        this.o = new LinkedList();
        d.a(getActivity().getApplicationContext()).a(this.p);
        a.a.a.c.a().a((Object) this);
        super.onCreate(savedInstanceState);
        this.mPageName = "myapp_download";
        u viewControllerPageInfo = new u();
        this.mPageInfo[1] = 26;
        viewControllerPageInfo.b(true);
        this.a = new t(getActivity(), viewControllerPageInfo);
        this.a.a(this.mPageName);
    }

    public void onDestroy() {
        d.a(getActivity().getApplicationContext()).b(this.p);
        a.a.a.c.a().c(this);
        super.onDestroy();
    }

    protected void setupActionBar() {
        super.setupActionBar();
        getActionBar().a(getString(i.game_download_manage));
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        showProgress();
        this.e.setSelector(new ColorDrawable(getResources().getColor(17170445)));
    }

    public e a() {
        this.d = new com.meizu.cloud.app.a.c(getActivity(), this.a);
        this.d.a((e.b) this);
        this.d.a((c) this);
        this.d.a((b) this);
        return this.d;
    }

    public android.support.v4.content.h<LinkedList<Blockable>> a(int id, Bundle args) {
        this.c = new a(getActivity().getApplicationContext(), this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o);
        return this.c;
    }

    public void a(android.support.v4.content.h<LinkedList<Blockable>> loader, LinkedList<Blockable> data) {
        super.a(loader, data);
        a((List) data);
        if (data.size() <= 0) {
            showEmptyView(getString(i.dowmload_manage_no_data_remind_text), getResources().getDrawable(com.meizu.cloud.b.a.e.ic_error_page, null), null);
        }
        hideProgress();
    }

    public void a(android.support.v4.content.h<LinkedList<Blockable>> hVar) {
    }

    public void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    public void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }

    protected boolean a(final com.meizu.cloud.app.downlad.e chioceItem) {
        if (!f.d(chioceItem.f()) || chioceItem.f() == f.f.INSTALL_SUCCESS) {
            return false;
        }
        new android.support.v7.app.b.a(getActivity(), com.meizu.cloud.b.a.j.Dialog_Alert_ShowAtBottom).a(new CharSequence[]{getString(i.cancel_current_task)}, new OnClickListener(this) {
            final /* synthetic */ h b;

            public void onClick(DialogInterface dialog, int which) {
                if (which != 0) {
                    return;
                }
                if (f.c(chioceItem.f())) {
                    d.a(this.b.getActivity()).c(chioceItem.g());
                    this.b.a(chioceItem, this.b.m);
                    return;
                }
                l stateEnum = this.b.a(chioceItem.f());
                if (stateEnum != null) {
                    chioceItem.a(stateEnum, chioceItem.S());
                    d.a(this.b.getActivity()).a(null, chioceItem);
                }
            }
        }, true).a(i.cancel, new OnClickListener(this) {
            final /* synthetic */ h a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).b().show();
        return true;
    }

    public boolean a(View itemView, Blockable blockable) {
        if (!(blockable instanceof com.meizu.cloud.app.downlad.e)) {
            return false;
        }
        a((com.meizu.cloud.app.downlad.e) blockable);
        return true;
    }

    protected String b() {
        return getString(i.dowmload_manage_no_data_remind_text);
    }
}
