package com.meizu.cloud.app.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.h;
import android.text.TextUtils;
import android.view.View;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.n;
import com.android.volley.s;
import com.meizu.cloud.a.b;
import com.meizu.cloud.a.c;
import com.meizu.cloud.app.core.x;
import com.meizu.cloud.app.request.RequestConstants;
import com.meizu.cloud.app.request.model.AccountBalanceModel;
import com.meizu.cloud.app.request.model.AccountInfoModel;
import com.meizu.cloud.app.request.model.AccountScoreModel;
import com.meizu.cloud.app.request.model.ResultModel;
import com.meizu.cloud.b.a.i;
import java.util.List;

public abstract class k<D extends List> extends com.meizu.cloud.base.b.k<D> implements OnAccountsUpdateListener {
    protected AccountInfoModel a;
    protected AccountBalanceModel b;
    protected AccountScoreModel c;
    protected b d;
    private com.meizu.volley.a.b<AccountInfoModel> h;
    private com.meizu.volley.a.b<AccountBalanceModel> i;
    private com.meizu.volley.a.b<AccountScoreModel> j;
    private a k;

    public interface a {
        void a();

        void a(boolean z);

        void b();

        void c();

        void d();

        void e();

        void f();
    }

    public void a(a accountStateListener) {
        this.k = accountStateListener;
        c();
    }

    protected void initView(View rootView) {
        super.initView(rootView);
        showProgress();
        this.e.setSelector(new ColorDrawable(getResources().getColor(17170445)));
    }

    public void a(h<D> loader, D d) {
        super.a(loader, d);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPageName = "myapp";
        registerPagerScrollStateListener();
        AccountManager.get(getActivity()).addOnAccountsUpdatedListener(this, null, true);
    }

    public void onDestroy() {
        unregisterPagerScrollStateListener();
        if (this.h != null) {
            this.h.cancel();
        }
        if (this.i != null) {
            this.i.cancel();
        }
        AccountManager.get(getActivity()).removeOnAccountsUpdatedListener(this);
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        if (e() != null) {
            c();
        }
    }

    public void onAccountsUpdated(Account[] accounts) {
        if (getActivity() != null) {
            String uId = c.c(getActivity());
            if ((this.a != null || TextUtils.isEmpty(uId)) && TextUtils.isEmpty(uId)) {
                this.a = null;
                this.b = null;
                this.c = null;
            }
            if (this.k != null) {
                this.k.a(!TextUtils.isEmpty(uId));
            }
        }
    }

    protected void c() {
        if (!TextUtils.isEmpty(c.c(getActivity()))) {
            if (this.a != null && this.a.isFromServer) {
                if (!x.a(getActivity())) {
                    if (!(this.b == null || this.c == null)) {
                        return;
                    }
                }
                return;
            }
            this.h = new com.meizu.volley.a.b(getActivity(), new TypeReference<ResultModel<AccountInfoModel>>(this) {
                final /* synthetic */ k a;

                {
                    this.a = r1;
                }
            }, RequestConstants.MEMBER_ACCOUNT_INFO, null, new n.b<ResultModel<AccountInfoModel>>(this) {
                final /* synthetic */ k a;

                {
                    this.a = r1;
                }

                public void a(ResultModel<AccountInfoModel> response) {
                    if (response != null && response.getCode() == 200) {
                        AccountInfoModel accountInfoModel = (AccountInfoModel) response.getValue();
                        if (accountInfoModel != null) {
                            this.a.a = accountInfoModel;
                            this.a.a.isFromServer = true;
                            if (this.a.k != null) {
                                this.a.k.a();
                            }
                        }
                    }
                }
            }, new com.android.volley.n.a(this) {
                final /* synthetic */ k a;

                {
                    this.a = r1;
                }

                public void a(s error) {
                    if (this.a.isAdded() && this.a.getActivity() != null) {
                        this.a.a = c.e(this.a.getActivity());
                        if (this.a.a != null) {
                            this.a.a.isFromServer = false;
                            if (this.a.k != null) {
                                this.a.k.a();
                                this.a.k.d();
                            }
                        } else if (this.a.k != null) {
                            this.a.k.b();
                        }
                    }
                }
            });
            com.meizu.volley.b.a(getActivity()).a().a(this.h);
            if (x.b(getActivity())) {
                this.i = new com.meizu.volley.a.b(getActivity(), new TypeReference<ResultModel<AccountBalanceModel>>(this) {
                    final /* synthetic */ k a;

                    {
                        this.a = r1;
                    }
                }, RequestConstants.PAY_ACCOUNT_BALANCE, null, new n.b<ResultModel<AccountBalanceModel>>(this) {
                    final /* synthetic */ k a;

                    {
                        this.a = r1;
                    }

                    public void a(ResultModel<AccountBalanceModel> response) {
                        if (response != null && response.getCode() == 200) {
                            AccountBalanceModel accountBalanceModel = (AccountBalanceModel) response.getValue();
                            if (accountBalanceModel != null) {
                                this.a.b = accountBalanceModel;
                                if (this.a.k != null) {
                                    this.a.k.c();
                                }
                            }
                        }
                    }
                }, new com.android.volley.n.a(this) {
                    final /* synthetic */ k a;

                    {
                        this.a = r1;
                    }

                    public void a(s error) {
                        if (this.a.k != null) {
                            this.a.k.d();
                        }
                    }
                });
                com.meizu.volley.b.a(getActivity()).a().a(this.i);
            }
            if (x.b(getActivity())) {
                this.j = new com.meizu.volley.a.b(getActivity(), new TypeReference<ResultModel<AccountScoreModel>>(this) {
                    final /* synthetic */ k a;

                    {
                        this.a = r1;
                    }
                }, RequestConstants.ACCOUNT_SCORE, null, new n.b<ResultModel<AccountScoreModel>>(this) {
                    final /* synthetic */ k a;

                    {
                        this.a = r1;
                    }

                    public void a(ResultModel<AccountScoreModel> response) {
                        if (response != null && response.getCode() == 200) {
                            AccountScoreModel accountScoreModel = (AccountScoreModel) response.getValue();
                            if (accountScoreModel != null) {
                                this.a.c = accountScoreModel;
                                if (this.a.k != null) {
                                    this.a.k.e();
                                }
                            }
                        }
                    }
                }, new com.android.volley.n.a(this) {
                    final /* synthetic */ k a;

                    {
                        this.a = r1;
                    }

                    public void a(s error) {
                        if (this.a.k != null) {
                            this.a.k.f();
                        }
                    }
                });
                com.meizu.volley.b.a(getActivity()).a().a(this.j);
            }
        }
    }

    protected <T extends Fragment> Fragment a(Class<T> clazz, String url) {
        try {
            Fragment fragment = (Fragment) clazz.newInstance();
            if (url == null) {
                return fragment;
            }
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            fragment.setArguments(bundle);
            return fragment;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    protected void a(int errorCode) {
        if (!isAdded()) {
            return;
        }
        if (errorCode == 1) {
            com.meizu.cloud.app.utils.a.a(getActivity(), getString(i.access_account_info_error));
        } else if (errorCode != 4) {
            com.meizu.cloud.app.utils.a.a(getActivity(), getString(i.access_account_info_out_date));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isAdded()) {
            switch (requestCode) {
                case 0:
                    this.d.a(requestCode, resultCode, data);
                    return;
                default:
                    return;
            }
        }
    }

    protected void onRealPageStart() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName);
    }

    protected void onRealPageStop() {
        com.meizu.cloud.statistics.b.a().a(this.mPageName, null);
    }

    protected String b() {
        return "";
    }
}
