package com.meizu.cloud.app.core;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.b;
import android.text.TextUtils;
import android.util.Pair;
import com.meizu.cloud.app.block.requestitem.SubpagePageConfigsInfo;
import com.meizu.cloud.app.downlad.d;
import com.meizu.cloud.app.downlad.e;
import com.meizu.cloud.app.downlad.f;
import com.meizu.cloud.app.downlad.f.h;
import com.meizu.cloud.app.downlad.f.j;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.downlad.f.n;
import com.meizu.cloud.app.request.model.ServerUpdateAppInfo;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem;
import com.meizu.cloud.app.utils.g;
import com.meizu.cloud.app.utils.m;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.CircularProgressButton;
import com.meizu.cloud.b.a.c;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.app.BaseCommonActivity;
import com.meizu.cloud.compaign.task.BaseTask;
import java.util.ArrayList;
import java.util.List;

public class t {
    private static final String a = t.class.getSimpleName();
    private FragmentActivity b;
    private d c;
    private u d;
    private w e;
    private int[] f = new int[3];
    private int g = -1;
    private String h;
    private l i;
    private AlertDialog j;
    private b k;
    private SubpagePageConfigsInfo l;

    public interface a {
    }

    public t(FragmentActivity activity, u viewControllerPageInfo) {
        this.b = activity;
        this.d = viewControllerPageInfo;
        this.c = d.a(this.b.getApplicationContext());
        this.e = e();
    }

    public void a(w displayConfig) {
        if (displayConfig != null) {
            this.e = displayConfig;
        } else {
            this.e = e();
        }
    }

    public final <T extends a> v a(T data, VersionItem versionItem, boolean isBindView, CirProButton view) {
        int id = -1;
        AppStructItem appStructItem = null;
        if (data instanceof AppStructItem) {
            appStructItem = (AppStructItem) data;
            id = appStructItem.id;
        } else if (data instanceof ServerUpdateAppInfo) {
            appStructItem = ((ServerUpdateAppInfo) data).getAppStructItem();
            id = appStructItem.id;
        } else if (data instanceof e) {
            appStructItem = ((e) data).m();
            id = appStructItem.id;
        }
        if (id == -1) {
            return null;
        }
        e downloadWrapper = a(id);
        view.setChargeAnim(!isBindView);
        if (downloadWrapper == null) {
            return a(appStructItem, versionItem, view);
        }
        return a(downloadWrapper, versionItem, view);
    }

    public final v a(e wrapper, CirProButton view) {
        view.setChargeAnim(true);
        return a(wrapper, null, view);
    }

    public static final com.meizu.cloud.app.downlad.f.a a(Context context, String packageName) {
        if (!TextUtils.isEmpty(packageName)) {
            for (Pair<String, Integer> appInfo : x.d(context.getApplicationContext()).a()) {
                if (packageName.equals(appInfo.first)) {
                    return com.meizu.cloud.app.downlad.f.a.INSTALLED;
                }
            }
        }
        return com.meizu.cloud.app.downlad.f.a.NOT_INSTALL;
    }

    private final e a(int appId) {
        for (e wrapper : this.c.a(true, 0)) {
            if (appId == wrapper.i() && !wrapper.j().d()) {
                return wrapper;
            }
        }
        return null;
    }

    private w e() {
        w displayConfig = new w();
        if (this.d.d()) {
            displayConfig.a = com.meizu.cloud.b.a.e.btn_operation_download_selector;
            displayConfig.c = 17170443;
            displayConfig.b = com.meizu.cloud.b.a.e.btn_operation_cancel_selector;
            displayConfig.d = 17170443;
            displayConfig.e = com.meizu.cloud.b.a.e.btn_operation_open_selector;
            displayConfig.f = 17170443;
        } else {
            displayConfig.a = c.btn_default;
            displayConfig.c = 17170443;
            displayConfig.b = -1;
            displayConfig.d = c.btn_operation_downloading_text;
            displayConfig.e = c.btn_default;
            displayConfig.f = c.btn_default;
        }
        return displayConfig;
    }

    private void a(v viewDisplay) {
        if (viewDisplay.a().getButton().getWidth() == 0) {
            viewDisplay.a().setChargeAnim(false);
        }
        viewDisplay.a(false);
        viewDisplay.a(CircularProgressButton.c.IDLE, this.e.f(this.b));
        viewDisplay.a(CircularProgressButton.c.IDLE, this.e.a(this.b), this.e.b(this.b));
        viewDisplay.a(CircularProgressButton.c.IDLE);
    }

    private void b(v viewDisplay) {
        if (viewDisplay.a().getButton().getWidth() == 0) {
            viewDisplay.a().setChargeAnim(false);
        }
        viewDisplay.a(false);
        viewDisplay.a(CircularProgressButton.c.COMPLETE, this.e.g(this.b));
        viewDisplay.a(CircularProgressButton.c.COMPLETE, this.e.d(this.b), this.e.e(this.b));
        viewDisplay.a(CircularProgressButton.c.COMPLETE);
    }

    private final v a(AppStructItem structItem, VersionItem versionItem, CirProButton view) {
        c compareResult;
        if (!view.a()) {
            view.c();
        }
        if (versionItem == null) {
            compareResult = x.d(this.b.getApplicationContext()).a(structItem.package_name, structItem.version_code);
        } else {
            compareResult = x.d(this.b.getApplicationContext()).a(structItem.package_name, versionItem.version_code);
        }
        l anEnum = a(this.b, structItem.package_name);
        v viewDisplay = new v();
        viewDisplay.a(view);
        viewDisplay.c(true);
        viewDisplay.d(true);
        viewDisplay.a(anEnum);
        switch (compareResult) {
            case NOT_INSTALL:
                a(viewDisplay);
                break;
            case OPEN:
                if (structItem.price > 0.0d) {
                    if (!structItem.paid) {
                        a(viewDisplay);
                        break;
                    }
                    b(viewDisplay);
                    break;
                }
                b(viewDisplay);
                break;
            case BUILD_IN:
                if (this.d.b()) {
                    if (versionItem != null) {
                        a(viewDisplay);
                        break;
                    }
                    b(viewDisplay);
                    break;
                }
                b(viewDisplay);
                break;
            case UPGRADE:
                a(viewDisplay);
                break;
            case DOWNGRADE:
                if (versionItem == null) {
                    if (structItem.price > 0.0d && !structItem.paid) {
                        a(viewDisplay);
                        break;
                    }
                    b(viewDisplay);
                    break;
                }
                a(viewDisplay);
                break;
                break;
        }
        viewDisplay.a(a(structItem, versionItem, compareResult));
        return viewDisplay;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private String a(com.meizu.cloud.app.request.structitem.AppStructItem r11, com.meizu.cloud.app.request.structitem.HistoryVersions.VersionItem r12, com.meizu.cloud.app.downlad.f.l r13, com.meizu.cloud.app.core.c r14) {
        /*
        r10 = this;
        r5 = com.meizu.cloud.app.downlad.f.c(r13);
        if (r5 == 0) goto L_0x0017;
    L_0x0006:
        r5 = r10.d;
        r5 = r5.c();
        if (r5 == 0) goto L_0x0017;
    L_0x000e:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.retry;
        r5 = r5.getString(r6);
    L_0x0016:
        return r5;
    L_0x0017:
        r5 = com.meizu.cloud.app.downlad.f.cancel(r13);
        if (r5 != 0) goto L_0x0163;
    L_0x001d:
        r5 = r13 instanceof com.meizu.cloud.app.downlad.f.n;
        if (r5 == 0) goto L_0x0040;
    L_0x0021:
        r4 = r13;
        r4 = (com.meizu.cloud.app.downlad.f.n) r4;
        r5 = com.meizu.cloud.app.core.t.AnonymousClass11.cancel;
        r6 = r4.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x0034;
            case 2: goto L_0x0037;
            default: goto L_0x002f;
        };
    L_0x002f:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x0034:
        r5 = "";
        goto L_0x0016;
    L_0x0037:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.waiting_download;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x0040:
        r5 = r13 instanceof com.meizu.cloud.app.downlad.f.c;
        if (r5 == 0) goto L_0x00ab;
    L_0x0044:
        r0 = r13;
        r0 = (com.meizu.cloud.app.downlad.f.c) r0;
        r5 = com.meizu.cloud.app.core.t.AnonymousClass11.c;
        r6 = r0.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x0057;
            case 2: goto L_0x0057;
            case 3: goto L_0x0060;
            case 4: goto L_0x0060;
            case 5: goto L_0x0063;
            case 6: goto L_0x00a1;
            default: goto L_0x0052;
        };
    L_0x0052:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x0057:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.waiting_download;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x0060:
        r5 = "";
        goto L_0x0016;
    L_0x0063:
        r5 = com.meizu.cloud.app.core.c.BUILD_IN;
        if (r14 != r5) goto L_0x0085;
    L_0x0067:
        r5 = r10.d;
        r5 = r5.cancel();
        if (r5 != 0) goto L_0x0077;
    L_0x006f:
        r5 = r10.d;
        r5 = r5.c();
        if (r5 == 0) goto L_0x0080;
    L_0x0077:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.Continue;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x0080:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x0085:
        r5 = com.meizu.cloud.app.core.c.OPEN;
        if (r14 != r5) goto L_0x0091;
    L_0x0089:
        r5 = r10.d;
        r5 = r5.c();
        if (r5 == 0) goto L_0x009b;
    L_0x0091:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.Continue;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x009b:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x00a1:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.waiting_install;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x00ab:
        r5 = r13 instanceof com.meizu.cloud.app.downlad.f.h;
        if (r5 == 0) goto L_0x00db;
    L_0x00af:
        r2 = r13;
        r2 = (com.meizu.cloud.app.downlad.f.h) r2;
        r5 = com.meizu.cloud.app.core.t.AnonymousClass11.d;
        r6 = r2.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x00c3;
            case 2: goto L_0x00c3;
            default: goto L_0x00bd;
        };
    L_0x00bd:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x00c3:
        r5 = com.meizu.cloud.app.core.c.UPGRADE;
        if (r14 != r5) goto L_0x00d1;
    L_0x00c7:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.updating;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x00d1:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.installing;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x00db:
        r5 = r13 instanceof com.meizu.cloud.app.downlad.f.f;
        if (r5 == 0) goto L_0x0151;
    L_0x00df:
        r1 = r13;
        r1 = (com.meizu.cloud.app.downlad.f.f) r1;
        r5 = com.meizu.cloud.app.core.t.AnonymousClass11.e;
        r6 = r1.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x00f3;
            case 2: goto L_0x010b;
            case 3: goto L_0x011d;
            case 4: goto L_0x0143;
            default: goto L_0x00ed;
        };
    L_0x00ed:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x00f3:
        r5 = com.meizu.cloud.app.core.c.UPGRADE;
        if (r14 != r5) goto L_0x0101;
    L_0x00f7:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.updating;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x0101:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.installing;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x010b:
        r5 = r10.d;
        r5 = r5.cancel();
        if (r5 != 0) goto L_0x011d;
    L_0x0113:
        r5 = r10.cancel;
        r6 = com.meizu.cloud.cancel.a.i.remove_start;
        r5 = r5.getString(r6);
        goto L_0x0016;
    L_0x011d:
        r5 = r11.paid;
        if (r5 != 0) goto L_0x013d;
    L_0x0121:
        r6 = r11.price;
        r8 = 0;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 <= 0) goto L_0x013d;
    L_0x0129:
        r5 = "¥ %s";
        r6 = 1;
        r6 = new java.lang.Object[r6];
        r7 = 0;
        r8 = r11.price;
        r8 = com.meizu.cloud.app.utils.g.a(r8);
        r6[r7] = r8;
        r5 = java.lang.String.format(r5, r6);
        goto L_0x0016;
    L_0x013d:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x0143:
        r5 = r10.d;
        r5 = r5.cancel();
        if (r5 != 0) goto L_0x00ed;
    L_0x014b:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
    L_0x0151:
        r5 = r13 instanceof com.meizu.cloud.app.downlad.f.RequeseParams;
        if (r5 == 0) goto L_0x0163;
    L_0x0155:
        r3 = r13;
        r3 = (com.meizu.cloud.app.downlad.f.RequeseParams) r3;
        r5 = com.meizu.cloud.app.core.t.AnonymousClass11.f;
        r6 = r3.ordinal();
        r5 = r5[r6];
        switch(r5) {
            case 1: goto L_0x0163;
            case 2: goto L_0x0163;
            default: goto L_0x0163;
        };
    L_0x0163:
        r5 = r10.a(r11, r12, r14);
        goto L_0x0016;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.app.core.t.a(com.meizu.cloud.app.request.structitem.AppStructItem, com.meizu.cloud.app.request.structitem.HistoryVersions$VersionItem, com.meizu.cloud.app.downlad.f$l, com.meizu.cloud.app.core.c):java.lang.String");
    }

    private String a(AppStructItem structItem, VersionItem versionItem, c compareResult) {
        if (compareResult == c.BUILD_IN) {
            if (!this.d.b()) {
                return this.b.getString(i.open);
            }
            if (versionItem == null) {
                return this.b.getString(i.open);
            }
            return this.b.getString(i.downgrade);
        } else if (compareResult == c.OPEN) {
            if (structItem.price <= 0.0d || structItem.paid) {
                return this.b.getString(i.open);
            }
            return String.format("¥ %s", new Object[]{g.a(structItem.price)});
        } else if (compareResult == c.UPGRADE) {
            if (this.d.a() || this.d.c()) {
                return this.b.getString(i.open);
            }
            return this.b.getString(i.update);
        } else if (compareResult == c.DOWNGRADE) {
            if (this.d.b()) {
                return this.b.getString(i.downgrade);
            }
            return this.b.getString(i.open);
        } else if (structItem.price <= 0.0d) {
            return this.b.getString(i.install);
        } else {
            return String.format("¥ %s", new Object[]{g.a(structItem.price)});
        }
    }

    private boolean a(e wrapper, VersionItem versionItem) {
        return versionItem != null ? wrapper.G() == versionItem.version_code : !wrapper.F();
    }

    private final v a(e wrapper, VersionItem versionItem, CirProButton view) {
        c compareResult;
        if (!view.a()) {
            view.c();
        }
        boolean isChangeSelf = true;
        if (this.d.b() || (!this.d.c() && wrapper.F())) {
            isChangeSelf = a(wrapper, versionItem);
        }
        l anEnum = wrapper.f();
        if (versionItem == null) {
            compareResult = x.d(this.b.getApplicationContext()).a(wrapper.g(), wrapper.h());
        } else {
            compareResult = x.d(this.b.getApplicationContext()).a(wrapper.g(), versionItem.version_code);
        }
        v viewDisplay = new v();
        viewDisplay.a(view);
        viewDisplay.c(true);
        viewDisplay.d(true);
        viewDisplay.a(anEnum);
        if (f.b(anEnum)) {
            a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
            if (compareResult == c.BUILD_IN) {
                if (!this.d.b() && !this.d.c()) {
                    viewDisplay.a(a(wrapper.m(), versionItem, compareResult));
                } else if (isChangeSelf) {
                    viewDisplay.a(a(wrapper.m(), versionItem, anEnum, compareResult));
                } else {
                    viewDisplay.a(a(wrapper.m(), versionItem, compareResult));
                }
            } else if (isChangeSelf) {
                viewDisplay.a(a(wrapper.m(), versionItem, anEnum, compareResult));
            } else {
                viewDisplay.a(a(wrapper.m(), versionItem, compareResult));
            }
        } else {
            if (!(anEnum instanceof n)) {
                if (!(anEnum instanceof f.c)) {
                    if (!(anEnum instanceof h)) {
                        if (!(anEnum instanceof f.f)) {
                            if (anEnum instanceof j) {
                                switch ((j) anEnum) {
                                    case PAYING:
                                        viewDisplay.a(false);
                                        viewDisplay.c(this.e.c(this.b));
                                        viewDisplay.b(true);
                                        viewDisplay.d(false);
                                        break;
                                    case SUCCESS:
                                        if (compareResult == c.OPEN) {
                                            b(viewDisplay);
                                            viewDisplay.a(this.b.getString(i.open));
                                            break;
                                        }
                                        break;
                                    default:
                                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                        break;
                                }
                            }
                        }
                        switch ((f.f) anEnum) {
                            case INSTALL_START:
                                if (!isChangeSelf) {
                                    a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                    viewDisplay.d(true);
                                    break;
                                }
                                viewDisplay.a(true);
                                viewDisplay.d(false);
                                break;
                            case DELETE_START:
                                if (!this.d.b()) {
                                    if (!isChangeSelf) {
                                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                        viewDisplay.d(true);
                                        break;
                                    }
                                    viewDisplay.a(true);
                                    viewDisplay.d(false);
                                    break;
                                }
                                break;
                            case INSTALL_SUCCESS:
                                if (compareResult != c.OPEN) {
                                    if (compareResult != c.BUILD_IN) {
                                        if (compareResult != c.UPGRADE) {
                                            if (compareResult == c.DOWNGRADE) {
                                                if (!this.d.b()) {
                                                    b(viewDisplay);
                                                    break;
                                                }
                                                a(viewDisplay);
                                                break;
                                            }
                                            a(viewDisplay);
                                            break;
                                        } else if (!this.d.a() && !this.d.c()) {
                                            a(viewDisplay);
                                            break;
                                        } else {
                                            b(viewDisplay);
                                            break;
                                        }
                                    }
                                    b(viewDisplay);
                                    break;
                                } else if (wrapper.E()) {
                                    if (!wrapper.N()) {
                                        a(viewDisplay);
                                        break;
                                    }
                                    b(viewDisplay);
                                    break;
                                } else {
                                    b(viewDisplay);
                                    break;
                                }
                                break;
                            case DELETE_SUCCESS:
                                if (!this.d.b()) {
                                    a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                    break;
                                }
                                break;
                            default:
                                a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                break;
                        }
                    }
                    switch ((h) anEnum) {
                        case PATCHING:
                            if (!isChangeSelf) {
                                a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                viewDisplay.d(true);
                                break;
                            }
                            viewDisplay.a(true);
                            viewDisplay.d(false);
                            break;
                        case PATCHED_SUCCESS:
                            if (!isChangeSelf) {
                                a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                                viewDisplay.d(true);
                                break;
                            }
                            viewDisplay.a(true);
                            viewDisplay.d(false);
                            break;
                        default:
                            a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                            break;
                    }
                }
                switch ((f.c) anEnum) {
                    case TASK_CREATED:
                        if (isChangeSelf) {
                            if (this.c.d() < 2) {
                                viewDisplay.a(false);
                                viewDisplay.c(this.e.c(this.b));
                                viewDisplay.b(true);
                                break;
                            }
                            viewDisplay.a(true);
                            break;
                        }
                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                        break;
                    case TASK_WAITING:
                        if (isChangeSelf) {
                            if (this.c.d() < 2) {
                                viewDisplay.a(false);
                                viewDisplay.c(this.e.c(this.b));
                                viewDisplay.b(true);
                                break;
                            }
                            viewDisplay.a(true);
                            break;
                        }
                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                        break;
                    case TASK_STARTED:
                    case TASK_RESUME:
                        if (!isChangeSelf) {
                            a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                            break;
                        }
                        if (view.getButton().getWidth() == 0) {
                            view.setChargeAnim(false);
                        }
                        viewDisplay.a(false);
                        viewDisplay.c(this.e.c(this.b));
                        viewDisplay.b(false);
                        viewDisplay.c(this.e.c(this.b));
                        viewDisplay.a(wrapper.o());
                        break;
                    case TASK_PAUSED:
                        if (this.d.c()) {
                            if (isChangeSelf) {
                                if (!wrapper.T()) {
                                    b(viewDisplay);
                                    break;
                                }
                                a(viewDisplay);
                                break;
                            }
                            a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                            break;
                        }
                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                        break;
                    case TASK_COMPLETED:
                        if (!isChangeSelf) {
                            a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                            viewDisplay.d(true);
                            break;
                        }
                        viewDisplay.a(true);
                        viewDisplay.d(false);
                        break;
                    default:
                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                        break;
                }
            }
            switch ((n) anEnum) {
                case FETCHING:
                    if (!isChangeSelf) {
                        a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                        break;
                    }
                    viewDisplay.a(false);
                    viewDisplay.c(this.e.c(this.b));
                    viewDisplay.b(true);
                    break;
                case SUCCESS:
                    if (isChangeSelf) {
                        if (this.c.d() < 2) {
                            viewDisplay.a(false);
                            viewDisplay.c(this.e.c(this.b));
                            viewDisplay.b(true);
                            break;
                        }
                        viewDisplay.a(true);
                        break;
                    }
                    a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                    break;
                default:
                    a(wrapper, versionItem, isChangeSelf, viewDisplay, compareResult);
                    break;
            }
            if (!isChangeSelf) {
                viewDisplay.a(a(wrapper.m(), versionItem, compareResult));
            } else if (anEnum != f.c.TASK_PAUSED || wrapper.T()) {
                viewDisplay.a(a(wrapper.m(), versionItem, anEnum, compareResult));
            } else {
                viewDisplay.a(this.b.getString(i.pre_install));
            }
        }
        return viewDisplay;
    }

    private void a(e wrapper, VersionItem versionItem, boolean isChangeSelf, v viewDisplay, c compareResult) {
        if (isChangeSelf) {
            if (compareResult == c.BUILD_IN) {
                a(viewDisplay);
            } else if (compareResult == c.OPEN) {
                if (f.c(wrapper.f())) {
                    a(viewDisplay);
                } else if (!wrapper.E()) {
                    b(viewDisplay);
                } else if (wrapper.N()) {
                    b(viewDisplay);
                } else {
                    a(viewDisplay);
                }
            } else if (wrapper.T()) {
                a(viewDisplay);
            } else if (wrapper.f() == f.c.TASK_PAUSED) {
                b(viewDisplay);
            } else {
                a(viewDisplay);
            }
        } else if (compareResult == c.BUILD_IN) {
            if (versionItem == null) {
                b(viewDisplay);
            } else {
                a(viewDisplay);
            }
        } else if (compareResult == c.OPEN) {
            b(viewDisplay);
        } else {
            a(viewDisplay);
        }
    }

    public void a(k performAction) {
        final k kVar;
        final e downloadWrapper;
        switch (performAction.e()) {
            case 1:
                if (performAction.b().length <= 0) {
                    return;
                }
                if (!this.c.g(performAction.b()[0].package_name) || !b(this.c.h(performAction.b()[0].package_name))) {
                    long size1 = performAction.b()[0].size;
                    if (d.a(this.b.getApplication()).f(performAction.b()[0].package_name) || m.a(this.b) || size1 < 10000000) {
                        if (performAction.a().c) {
                            a.a.a.c.a().d(new com.meizu.cloud.app.c.e(-1));
                        }
                        a(performAction, -1, null);
                        return;
                    }
                    c compareResult = x.d(this.b).a(performAction.b()[0].package_name, performAction.b()[0].version_code);
                    if ((compareResult == c.OPEN || compareResult == c.BUILD_IN || compareResult == c.DOWNGRADE) && (performAction.b()[0].price <= 0.0d || performAction.b()[0].paid)) {
                        a(performAction.b()[0].package_name, this.b);
                        if (!performAction.b()[0].is_fromPlugin) {
                            com.meizu.cloud.statistics.b.a().a("open", this.h, com.meizu.cloud.statistics.c.b(performAction.b()[0]));
                            return;
                        }
                        return;
                    }
                    if (this.k != null && this.k.isShowing()) {
                        this.k.cancel();
                    }
                    kVar = performAction;
                    this.k = a(size1, (OnClickListener) new OnClickListener(this) {
                        final /* synthetic */ t b;

                        public void onClick(DialogInterface dialog, int which) {
                            if (kVar.a().c) {
                                a.a.a.c.a().d(new com.meizu.cloud.app.c.e(which));
                            }
                            this.b.a(kVar, which, null);
                        }
                    });
                    this.k.show();
                    return;
                }
                return;
            case 2:
                if (performAction.c().length > 0) {
                    if (this.c.g(performAction.c()[0].package_name)) {
                        downloadWrapper = this.c.h(performAction.c()[0].package_name);
                        if (!(downloadWrapper == null || !d.a(downloadWrapper.y()) || i.g(this.b, downloadWrapper.g()))) {
                            if (this.j != null && this.j.isShowing()) {
                                this.j.cancel();
                            }
                            this.j = e(downloadWrapper);
                            this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
                                final /* synthetic */ t b;

                                public void onClick(DialogInterface dialog, int which) {
                                    this.b.c.a(downloadWrapper);
                                }
                            });
                            this.j.show();
                            return;
                        }
                    }
                    long size2 = performAction.c()[0].existDeltaUpdate() ? performAction.c()[0].version_patch_size : performAction.c()[0].size;
                    if (d.a(this.b.getApplication()).f(performAction.c()[0].package_name) || m.a(this.b) || size2 < 10000000) {
                        b(performAction, -1, null);
                        return;
                    }
                    if (this.k != null && this.k.isShowing()) {
                        this.k.cancel();
                    }
                    kVar = performAction;
                    this.k = a(size2, (OnClickListener) new OnClickListener(this) {
                        final /* synthetic */ t b;

                        public void onClick(DialogInterface dialog, int which) {
                            this.b.b(kVar, which, null);
                        }
                    });
                    this.k.show();
                    return;
                }
                return;
            case 3:
                if (performAction.b().length <= 0) {
                    return;
                }
                if (!this.c.g(performAction.b()[0].package_name) || !b(this.c.h(performAction.b()[0].package_name))) {
                    long size3 = (long) performAction.d().size;
                    boolean isInProcess3 = false;
                    if (d.a(this.b.getApplication()).f(performAction.b()[0].package_name)) {
                        downloadWrapper = d.a(this.b.getApplication()).b(performAction.b()[0].package_name);
                        if (downloadWrapper.F() && performAction.d().version_code == downloadWrapper.G()) {
                            isInProcess3 = true;
                        }
                    }
                    if (isInProcess3 || m.a(this.b) || size3 < 10000000) {
                        a(performAction, isInProcess3);
                        return;
                    }
                    if (this.k != null && this.k.isShowing()) {
                        this.k.cancel();
                    }
                    kVar = performAction;
                    this.k = a(size3, (OnClickListener) new OnClickListener(this) {
                        final /* synthetic */ t b;

                        public void onClick(DialogInterface dialog, int which) {
                            this.b.a(kVar, false);
                        }
                    });
                    this.k.show();
                    return;
                }
                return;
            case 4:
                int size1or4 = 0;
                final List inProcesses1 = new ArrayList();
                for (AppStructItem appStructItem : performAction.b()) {
                    if (d.a(this.b.getApplication()).f(appStructItem.package_name)) {
                        inProcesses1.add(appStructItem);
                    } else {
                        size1or4 = (int) (((long) size1or4) + appStructItem.size);
                    }
                }
                if (m.a(this.b) || ((long) size1or4) < 10000000) {
                    if (performAction.a().c) {
                        a.a.a.c.a().d(new com.meizu.cloud.app.c.e(-1));
                    }
                    a(performAction, -1, inProcesses1);
                    return;
                }
                if (this.k != null && this.k.isShowing()) {
                    this.k.cancel();
                }
                kVar = performAction;
                this.k = a((long) size1or4, (OnClickListener) new OnClickListener(this) {
                    final /* synthetic */ t c;

                    public void onClick(DialogInterface dialog, int which) {
                        if (kVar.a().c) {
                            a.a.a.c.a().d(new com.meizu.cloud.app.c.e(which));
                        }
                        this.c.a(kVar, which, inProcesses1);
                    }
                });
                this.k.show();
                return;
            case 5:
                int size2or5 = 0;
                final List<ServerUpdateAppInfo> inProcesses2 = new ArrayList();
                for (ServerUpdateAppInfo updateAppInfo : performAction.c()) {
                    if (d.a(this.b.getApplication()).f(updateAppInfo.package_name)) {
                        inProcesses2.add(updateAppInfo);
                    } else if (!com.meizu.cloud.app.downlad.a.b(updateAppInfo.package_name, updateAppInfo.version_code)) {
                        size2or5 = (int) ((updateAppInfo.existDeltaUpdate() ? updateAppInfo.version_patch_size : updateAppInfo.size) + ((long) size2or5));
                    }
                }
                if (m.a(this.b) || ((long) size2or5) < 10000000) {
                    b(performAction, -1, inProcesses2);
                    return;
                }
                if (this.k != null && this.k.isShowing()) {
                    this.k.cancel();
                }
                kVar = performAction;
                this.k = a((long) size2or5, (OnClickListener) new OnClickListener(this) {
                    final /* synthetic */ t c;

                    public void onClick(DialogInterface dialog, int which) {
                        this.c.b(kVar, which, inProcesses2);
                    }
                });
                this.k.show();
                return;
            default:
                return;
        }
    }

    private void a(k performAction, boolean isInProcess) {
        AppStructItem item = performAction.b()[0];
        VersionItem versionItem = performAction.d();
        c compareResult = x.d(this.b).a(item.package_name, versionItem.version_code);
        if (compareResult == c.OPEN || !(compareResult != c.BUILD_IN || this.d.b() || this.d.c())) {
            a(item.package_name, this.b);
            if (!item.is_fromPlugin) {
                com.meizu.cloud.statistics.b.a().a("open", this.h, com.meizu.cloud.statistics.c.b(item));
            }
        } else if (c(item.package_name)) {
            int origin = compareResult == c.DOWNGRADE ? 20 : compareResult == c.UPGRADE ? 8 : item.price > 0.0d ? 4 : 2;
            int[] iArr = new int[1];
            iArr[0] = performAction.a().d ? 3 : 1;
            final e downloadWrapper = this.c.a(item, versionItem, new com.meizu.cloud.app.downlad.g(origin, iArr));
            if (isInProcess) {
                c(downloadWrapper);
                return;
            }
            e wrapper = this.c.b(downloadWrapper.g());
            final l stateEnum = wrapper == null ? downloadWrapper.f() : wrapper.f();
            if (compareResult == c.DOWNGRADE || compareResult == c.BUILD_IN) {
                if (this.j != null && this.j.isShowing()) {
                    this.j.cancel();
                }
                if (!f.d(stateEnum)) {
                    this.j = g(downloadWrapper);
                    this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
                        final /* synthetic */ t a;

                        {
                            this.a = r1;
                        }

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    this.j.show();
                } else if (this.d.c() && d.a(this.b.getApplicationContext()).g(performAction.b()[0].package_name)) {
                    b(downloadWrapper.g());
                    this.c.a(this.b, downloadWrapper);
                } else {
                    this.j = a(downloadWrapper, compareResult);
                    this.j.setButton(-1, this.b.getString(i.Continue), new OnClickListener(this) {
                        final /* synthetic */ t c;

                        public void onClick(DialogInterface dialog, int which) {
                            if (f.d(stateEnum)) {
                                this.c.b(downloadWrapper.g());
                                this.c.c.a(this.c.b, downloadWrapper);
                                com.meizu.cloud.statistics.b.a().a("install_lower_version", this.c.h, com.meizu.cloud.statistics.c.b(downloadWrapper.m()));
                            }
                        }
                    });
                    this.j.show();
                }
            } else if (f.d(stateEnum)) {
                c(downloadWrapper);
            } else {
                this.j = g(downloadWrapper);
                this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
                    final /* synthetic */ t a;

                    {
                        this.a = r1;
                    }

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                this.j.show();
            }
        }
    }

    private void a(k performAction, int which, List<AppStructItem> filtration) {
        AppStructItem[] loopList;
        if (filtration == null) {
            loopList = performAction.b();
        } else {
            List<AppStructItem> itemList = performAction.a((List) filtration);
            loopList = (AppStructItem[]) itemList.toArray(new AppStructItem[itemList.size()]);
        }
        for (AppStructItem appStructItem : loopList) {
            c compareResult = x.d(this.b).a(appStructItem.package_name, appStructItem.version_code);
            if ((compareResult == c.OPEN || compareResult == c.BUILD_IN || compareResult == c.DOWNGRADE) && (appStructItem.price <= 0.0d || appStructItem.paid)) {
                a(appStructItem.package_name, this.b);
                if (!appStructItem.is_fromPlugin) {
                    com.meizu.cloud.statistics.b.a().a("open", this.h, com.meizu.cloud.statistics.c.b(appStructItem));
                }
            } else if (c(appStructItem.package_name)) {
                e wrapper = d(appStructItem.package_name);
                if (wrapper == null) {
                    e downloadWrapper;
                    if (performAction.a().a) {
                        int[] iArr = new int[1];
                        iArr[0] = performAction.a().d ? 3 : 1;
                        downloadWrapper = this.c.b(appStructItem, new com.meizu.cloud.app.downlad.g(6, iArr));
                    } else {
                        int origin = compareResult == c.UPGRADE ? 8 : performAction.a().b ? 18 : appStructItem.price > 0.0d ? 4 : 2;
                        int[] iArr2 = new int[1];
                        iArr2[0] = performAction.a().d ? 3 : 1;
                        downloadWrapper = this.c.a(appStructItem, new com.meizu.cloud.app.downlad.g(origin, iArr2));
                    }
                    if (which == 1) {
                        downloadWrapper.e(m.a(this.b));
                    }
                    c(downloadWrapper);
                } else {
                    if (this.j != null && this.j.isShowing()) {
                        this.j.cancel();
                    }
                    this.j = g(wrapper);
                    this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
                        final /* synthetic */ t a;

                        {
                            this.a = r1;
                        }

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    this.j.show();
                }
            }
        }
    }

    private void b(k performAction, int which, List<ServerUpdateAppInfo> filtration) {
        ServerUpdateAppInfo[] loopList;
        if (filtration == null) {
            loopList = performAction.c();
        } else {
            List<ServerUpdateAppInfo> itemList = performAction.b(filtration);
            loopList = (ServerUpdateAppInfo[]) itemList.toArray(new ServerUpdateAppInfo[itemList.size()]);
        }
        for (ServerUpdateAppInfo serverUpdateAppInfo : loopList) {
            c compareResult = x.d(this.b).a(serverUpdateAppInfo.package_name, serverUpdateAppInfo.version_code);
            if (compareResult == c.OPEN || compareResult == c.BUILD_IN || compareResult == c.DOWNGRADE) {
                a(serverUpdateAppInfo.package_name, this.b);
                if (!serverUpdateAppInfo.getAppStructItem().is_fromPlugin) {
                    com.meizu.cloud.statistics.b.a().a("open", this.h, com.meizu.cloud.statistics.c.b(serverUpdateAppInfo.getAppStructItem()));
                }
            } else {
                e wrapper = d(serverUpdateAppInfo.package_name);
                if (wrapper == null) {
                    int[] iArr = new int[1];
                    iArr[0] = performAction.a().d ? 3 : 1;
                    e downloadWrapper = this.c.a(serverUpdateAppInfo, new com.meizu.cloud.app.downlad.g(8, iArr));
                    if (which == 1) {
                        downloadWrapper.e(m.a(this.b));
                    }
                    c(downloadWrapper);
                } else {
                    if (this.j != null && this.j.isShowing()) {
                        this.j.cancel();
                    }
                    this.j = g(wrapper);
                    this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
                        final /* synthetic */ t a;

                        {
                            this.a = r1;
                        }

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    this.j.show();
                }
            }
        }
    }

    private boolean b(final e downloadWrapper) {
        if (downloadWrapper == null || !d.a(downloadWrapper.y()) || i.g(this.b, downloadWrapper.g())) {
            return false;
        }
        if (this.j != null && this.j.isShowing()) {
            this.j.cancel();
        }
        this.j = e(downloadWrapper);
        this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
            final /* synthetic */ t b;

            public void onClick(DialogInterface dialog, int which) {
                this.b.c.a(downloadWrapper);
            }
        });
        this.j.show();
        return true;
    }

    private void c(final e downloadWrapper) {
        if (downloadWrapper.R()) {
            String actionName = "";
            int opType = -1;
            switch (downloadWrapper.j().f()) {
                case 2:
                case 4:
                case 6:
                case 18:
                    actionName = "install";
                    opType = 0;
                    break;
                case 8:
                    actionName = "update";
                    opType = 4;
                    if (com.meizu.cloud.app.downlad.a.b(downloadWrapper.g(), downloadWrapper.h())) {
                        actionName = "savetrafficupdate";
                        break;
                    }
                    break;
                case 20:
                    actionName = "install_lower_version";
                    break;
            }
            if (!downloadWrapper.m().is_fromPlugin) {
                if (opType != -1) {
                    com.meizu.cloud.statistics.a.a(this.b).a(downloadWrapper.m(), opType);
                }
                com.meizu.cloud.statistics.d.a(this.b).a(downloadWrapper.m());
                com.meizu.cloud.statistics.b.a().a(actionName, this.h, com.meizu.cloud.statistics.c.b(downloadWrapper.m()));
            }
        } else {
            l stateEnum = d(downloadWrapper);
            if (stateEnum != null) {
                if (stateEnum == f.c.TASK_PAUSED) {
                    this.c.k(downloadWrapper.g());
                    if (!downloadWrapper.m().is_fromPlugin) {
                        com.meizu.cloud.statistics.b.a().a("pause", this.h, com.meizu.cloud.statistics.c.b(downloadWrapper.m()));
                        return;
                    }
                    return;
                }
                if (downloadWrapper.T()) {
                    downloadWrapper.a(stateEnum, downloadWrapper.S());
                    this.c.a(this.b, downloadWrapper);
                } else {
                    if (this.j != null && this.j.isShowing()) {
                        this.j.cancel();
                    }
                    this.j = f(downloadWrapper);
                    this.j.setButton(-1, this.b.getString(i.confirm), new OnClickListener(this) {
                        final /* synthetic */ t b;

                        public void onClick(DialogInterface dialog, int which) {
                            this.b.c.a(downloadWrapper.g());
                        }
                    });
                    this.j.show();
                }
                if (stateEnum == n.CANCEL || stateEnum == f.c.TASK_REMOVED) {
                    if (!downloadWrapper.m().is_fromPlugin) {
                        com.meizu.cloud.statistics.b.a().a("cancel", this.h, com.meizu.cloud.statistics.c.b(downloadWrapper.m()));
                        return;
                    }
                    return;
                } else if (stateEnum == f.c.TASK_RESUME && !downloadWrapper.m().is_fromPlugin) {
                    com.meizu.cloud.statistics.b.a().a("continue", this.h, com.meizu.cloud.statistics.c.b(downloadWrapper.m()));
                    return;
                } else {
                    return;
                }
            }
        }
        b(downloadWrapper.g());
        this.c.a(this.b, downloadWrapper);
    }

    private l d(e downloadWrapper) {
        l anEnum = downloadWrapper.f();
        if (anEnum instanceof n) {
            if (anEnum == n.FETCHING) {
                return n.CANCEL;
            }
        } else if (anEnum instanceof f.c) {
            if (anEnum == f.c.TASK_WAITING || anEnum == f.c.TASK_CREATED || anEnum == f.c.TASK_STARTED) {
                return f.c.TASK_PAUSED;
            }
            if (anEnum == f.c.TASK_PAUSED) {
                return f.c.TASK_RESUME;
            }
        } else if ((anEnum instanceof j) && anEnum == j.PAYING) {
            return j.CANCEL;
        }
        return null;
    }

    public static l a(e downloadWrapper) {
        l anEnum = downloadWrapper.f();
        if (anEnum instanceof n) {
            if (anEnum == n.FETCHING) {
                return n.CANCEL;
            }
        } else if (anEnum instanceof f.c) {
            if (anEnum == f.c.TASK_WAITING || anEnum == f.c.TASK_CREATED || anEnum == f.c.TASK_STARTED || anEnum == f.c.TASK_PAUSED) {
                return f.c.TASK_REMOVED;
            }
        } else if ((anEnum instanceof j) && anEnum == j.PAYING) {
            return j.CANCEL;
        }
        return null;
    }

    private boolean c(String packageName) {
        if (!m.b(this.b)) {
            if (this.b instanceof BaseCommonActivity) {
                ((BaseCommonActivity) this.b).l();
            }
            return false;
        } else if (!TextUtils.isEmpty(packageName)) {
            return true;
        } else {
            com.meizu.cloud.app.utils.a.a(this.b, this.b.getString(i.server_error));
            com.meizu.cloud.app.utils.j.a("ViewController", "package_name is null!");
            return false;
        }
    }

    private e d(String packageName) {
        for (e downloadWrapper : d.a(this.b.getApplicationContext()).j(1, 0, 3)) {
            if (downloadWrapper.g().equals(packageName)) {
                return downloadWrapper;
            }
        }
        return null;
    }

    public static void a(String packageName, FragmentActivity activity) {
        if (-1 == a(activity, packageName)) {
            com.meizu.cloud.app.utils.a.a(activity, activity.getString(i.open_app_failed));
        }
    }

    public static int a(FragmentActivity context, String packageName) {
        return b(context, packageName);
    }

    private static int b(FragmentActivity context, String packageName) {
        Intent intent = i.d(context, packageName);
        if (intent == null) {
            return -1;
        }
        BaseTask launchTask = com.meizu.cloud.compaign.a.a((Context) context).b(packageName);
        if (launchTask != null) {
            com.meizu.cloud.compaign.a.a((Context) context).a(launchTask);
        }
        intent.addFlags(268435456);
        intent.addFlags(2097152);
        context.startActivity(intent);
        return 1;
    }

    public void a(int[] pageId) {
        this.f = pageId;
    }

    public int[] a() {
        return this.f;
    }

    public void a(String wdmPageName) {
        this.h = wdmPageName;
    }

    public String b() {
        return this.h;
    }

    private b a(long size, OnClickListener onClickListener) {
        android.support.v7.app.b.a builder = new android.support.v7.app.b.a(this.b, com.meizu.cloud.b.a.j.Dialog_Alert_ShowAtBottom);
        String sizeFormat = g.a((double) size, this.b.getResources().getStringArray(com.meizu.cloud.b.a.b.sizeUnit));
        CharSequence[] charSequenceArr = new CharSequence[2];
        charSequenceArr[0] = this.b.getResources().getString(i.download_over_mobile, new Object[]{sizeFormat});
        charSequenceArr[1] = this.b.getResources().getString(i.download_over_wlan);
        builder.a(charSequenceArr, onClickListener, true);
        builder.a(this.b.getString(i.cancel), new OnClickListener(this) {
            final /* synthetic */ t a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.b();
    }

    private AlertDialog e(e downloadWrapper) {
        Builder builder = new Builder(this.b);
        builder.setMessage(String.format(this.b.getString(i.reinstall_tips_message), new Object[]{downloadWrapper.k()}));
        builder.setNegativeButton(this.b.getString(i.cancel), new OnClickListener(this) {
            final /* synthetic */ t a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog f(e downloadWrapper) {
        Builder builder = new Builder(this.b);
        builder.setMessage(this.b.getString(i.mobile_network_download_tips_message));
        builder.setNegativeButton(this.b.getString(i.cancel), new OnClickListener(this) {
            final /* synthetic */ t a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog a(e downloadWrapper, c compareResult) {
        Builder builder = new Builder(this.b);
        String string = this.b.getString(i.downgrade_install_tips_message);
        Object[] objArr = new Object[1];
        objArr[0] = compareResult == c.BUILD_IN ? downloadWrapper.l() : i.e(this.b, downloadWrapper.g());
        builder.setMessage(String.format(string, objArr));
        builder.setNegativeButton(this.b.getString(i.cancel), new OnClickListener(this) {
            final /* synthetic */ t a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    private AlertDialog g(e downloadWrapper) {
        e wrapper = d.a(this.b.getApplication()).b(downloadWrapper.g());
        Builder builder = new Builder(this.b);
        String string = this.b.getString(i.installing_tips_message);
        Object[] objArr = new Object[1];
        objArr[0] = wrapper.F() ? wrapper.H() : wrapper.l();
        builder.setMessage(String.format(string, objArr));
        builder.setPositiveButton(this.b.getString(i.confirm), new OnClickListener(this) {
            final /* synthetic */ t a;

            {
                this.a = r1;
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }

    public void b(String packageName) {
        e wrapper = this.c.b(packageName);
        if (wrapper != null) {
            l stateEnum = a(wrapper);
            if (stateEnum != null) {
                wrapper.a(stateEnum, wrapper.S());
                this.c.a(null, wrapper);
            }
        }
    }

    public l c() {
        return this.i;
    }

    public void a(l mRankViewResource) {
        this.i = mRankViewResource;
    }

    public void a(SubpagePageConfigsInfo configInfo) {
        if (configInfo != null) {
            this.l = configInfo;
        }
    }

    public SubpagePageConfigsInfo d() {
        return this.l;
    }
}
