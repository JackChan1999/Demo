package com.meizu.cloud.app.core;

import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class k {
    private AppStructItem[] a;
    private ServerUpdateAppInfo[] b;
    private VersionItem c;
    private a d = new a();

    public static class a {
        public boolean a = false;
        public boolean b = false;
        public boolean c;
        public boolean d = false;

        public a a(boolean isTry) {
            this.a = isTry;
            return this;
        }

        public a b(boolean isVoice) {
            this.b = isVoice;
            return this;
        }

        public a c(boolean isWizardActivity) {
            this.c = isWizardActivity;
            return this;
        }

        public a a(Boolean isFromPlugin) {
            this.d = isFromPlugin.booleanValue();
            return this;
        }

        public String toString() {
            return "IsTry:" + this.a + "IsVoice:" + this.b + "IsFromPlugin" + this.d;
        }
    }

    public k(AppStructItem... items) {
        this.a = items;
    }

    public k(ServerUpdateAppInfo... updateItems) {
        this.b = updateItems;
    }

    public k(AppStructItem item, VersionItem historyItem) {
        this.a = new AppStructItem[]{item};
        this.c = historyItem;
    }

    public void a(a performOption) {
        this.d = performOption;
    }

    public a a() {
        return this.d;
    }

    public AppStructItem[] b() {
        return this.a;
    }

    public List<AppStructItem> a(List<AppStructItem> filtration) {
        List<AppStructItem> repeatItems = new ArrayList();
        for (AppStructItem item : filtration) {
            for (AppStructItem itemList : this.a) {
                if (item.package_name.equals(itemList.package_name)) {
                    repeatItems.add(item);
                }
            }
        }
        ArrayList<AppStructItem> itemLists = new ArrayList(this.a.length);
        itemLists.addAll(Arrays.asList(this.a));
        itemLists.removeAll(repeatItems);
        return itemLists;
    }

    public ServerUpdateAppInfo[] c() {
        return this.b;
    }

    public List<ServerUpdateAppInfo> b(List<ServerUpdateAppInfo> filtration) {
        List<ServerUpdateAppInfo> repeatItems = new ArrayList();
        for (ServerUpdateAppInfo item : filtration) {
            for (ServerUpdateAppInfo itemList : this.b) {
                if (item.package_name.equals(itemList.package_name)) {
                    repeatItems.add(item);
                }
            }
        }
        ArrayList<ServerUpdateAppInfo> itemLists = new ArrayList(this.b.length);
        itemLists.addAll(Arrays.asList(this.b));
        itemLists.removeAll(repeatItems);
        return itemLists;
    }

    public VersionItem d() {
        return this.c;
    }

    public int e() {
        if (this.a != null) {
            if (this.a.length > 1) {
                return 4;
            }
            if (this.c != null) {
                return 3;
            }
            return 1;
        } else if (this.b == null) {
            return -1;
        } else {
            if (this.b.length > 1) {
                return 5;
            }
            return 2;
        }
    }

    public String toString() {
        return "ActionType:" + e() + ", Items" + this.a + ", UpdateItems" + this.b + ", HistoryItem" + this.c + ", PeformOption" + this.d.toString();
    }
}
