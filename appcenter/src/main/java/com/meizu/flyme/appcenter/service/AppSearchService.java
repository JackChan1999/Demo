package com.meizu.flyme.appcenter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.l;
import com.android.volley.n.b;
import com.android.volley.s;
import com.meizu.cloud.app.request.FastJsonRequest;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.DataReultModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.flyme.appcenter.aidl.AppItem;
import java.util.ArrayList;
import java.util.List;

public class AppSearchService extends Service {
    public static final String a = AppSearchService.class.getSimpleName();
    com.meizu.flyme.appcenter.aidl.b.a b = new com.meizu.flyme.appcenter.aidl.b.a(this) {
        final /* synthetic */ AppSearchService a;

        {
            this.a = r1;
        }

        public void b(com.meizu.flyme.appcenter.aidl.a callback) throws RemoteException {
            if (callback != null) {
                this.a.c.unregister(callback);
            }
        }

        public void a(boolean fullMatch, String keyWord) throws RemoteException {
            this.a.a(fullMatch, keyWord);
        }

        public void a(com.meizu.flyme.appcenter.aidl.a callback) throws RemoteException {
            if (callback != null) {
                this.a.c.register(callback);
            }
        }
    };
    private RemoteCallbackList<com.meizu.flyme.appcenter.aidl.a> c = new RemoteCallbackList();
    private com.android.volley.n.a d = new com.android.volley.n.a(this) {
        final /* synthetic */ AppSearchService a;

        {
            this.a = r1;
        }

        public void a(s error) {
            error.printStackTrace();
            if (error.a != null) {
                this.a.a(error.a.a, error.getMessage());
            } else {
                this.a.a(-1, error.getMessage());
            }
        }
    };

    abstract class a<T> implements b<T> {
        String b;
        boolean c;
        final /* synthetic */ AppSearchService d;

        public a(AppSearchService appSearchService, String keyword, boolean isFullMatch) {
            this.d = appSearchService;
            this.b = keyword;
            this.c = isFullMatch;
        }
    }

    public IBinder onBind(Intent intent) {
        return this.b;
    }

    public void onCreate() {
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && "com.meizu.flyme.appcenter.searchapp".equals(intent.getAction())) {
            a(intent.getBooleanExtra("FULLMATCH", false), intent.getStringExtra("KEYWORD"));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void a(boolean fullMatch, String keyWord) {
        b searchResultLintener = new a<ResultModel<DataReultModel<AppStructItem>>>(this, keyWord, fullMatch) {
            final /* synthetic */ AppSearchService a;

            public void a(ResultModel<DataReultModel<AppStructItem>> response) {
                if (response == null || response.getValue() == null || ((DataReultModel) response.getValue()).data == null) {
                    this.a.a(-1, "Result is null");
                } else {
                    this.a.a(this.a.a(((DataReultModel) response.getValue()).data, this.c, this.b));
                }
            }
        };
        TypeReference typeReference = new TypeReference<ResultModel<DataReultModel<AppStructItem>>>(this) {
            final /* synthetic */ AppSearchService a;

            {
                this.a = r1;
            }
        };
        List paramPairs = new ArrayList();
        paramPairs.add(new com.meizu.volley.b.a("q", keyWord));
        l jsonRequest = new FastJsonRequest(typeReference, 0, RequestConstants.getRuntimeDomainUrl(this, RequestConstants.SEARCH), paramPairs, searchResultLintener, this.d);
        jsonRequest.setParamProvider(com.meizu.cloud.app.utils.param.a.a(this));
        com.meizu.volley.b.a(this).a().a(jsonRequest);
    }

    private void a(int errorCode, String errorMsg) {
        int n = this.c.beginBroadcast();
        int i = 0;
        while (i < n) {
            try {
                ((com.meizu.flyme.appcenter.aidl.a) this.c.getBroadcastItem(i)).a(errorCode, errorMsg);
                i++;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            } finally {
                this.c.finishBroadcast();
                stopSelf();
            }
        }
        this.c.finishBroadcast();
        stopSelf();
    }

    private void a(List<AppItem> appItems) {
        int n = this.c.beginBroadcast();
        int i = 0;
        while (i < n) {
            try {
                ((com.meizu.flyme.appcenter.aidl.a) this.c.getBroadcastItem(i)).a(appItems);
                i++;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            } finally {
                this.c.finishBroadcast();
                stopSelf();
            }
        }
        this.c.finishBroadcast();
        stopSelf();
    }

    private List<AppItem> a(List<AppStructItem> appStructItems, boolean isFullMatch, String keyword) {
        List<AppItem> result = new ArrayList();
        for (AppStructItem item : appStructItems) {
            AppItem appItem = new AppItem();
            appItem.c = item.name;
            appItem.d = item.icon;
            appItem.b = item.package_name;
            appItem.e = item.publisher;
            appItem.f = item.url;
            appItem.a = (long) item.id;
            if (!isFullMatch) {
                result.add(appItem);
            } else if (keyword.equalsIgnoreCase(appItem.c)) {
                result.add(appItem);
            }
        }
        return result;
    }
}
