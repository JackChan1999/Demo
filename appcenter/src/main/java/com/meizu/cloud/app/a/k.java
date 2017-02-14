package com.meizu.cloud.app.a;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import com.meizu.cloud.app.block.requestitem.AppAdStructItem;
import com.meizu.cloud.app.block.requestitem.RollingPlayStructItem;
import com.meizu.cloud.app.block.structbuilder.BlockItemBuilder;
import com.meizu.cloud.app.block.structitem.AbsBlockItem;
import com.meizu.cloud.app.block.structitem.AdAppBigItem;
import com.meizu.cloud.app.block.structitem.AdBigItem;
import com.meizu.cloud.app.block.structitem.AdvertiseItem;
import com.meizu.cloud.app.block.structitem.ChannelCol5Item;
import com.meizu.cloud.app.block.structitem.ContsRow1Col4Item;
import com.meizu.cloud.app.block.structitem.GameQualityItem;
import com.meizu.cloud.app.block.structitem.RecommendAppItem;
import com.meizu.cloud.app.block.structitem.RollMessageItem;
import com.meizu.cloud.app.block.structitem.RollingPlayItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppItem;
import com.meizu.cloud.app.block.structitem.Row1Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col3AppVerItem;
import com.meizu.cloud.app.block.structitem.Row1Col4AppVerItem;
import com.meizu.cloud.app.block.structitem.Row2Col2AppVerItem;
import com.meizu.cloud.app.block.structitem.SingleRowAppItem;
import com.meizu.cloud.app.block.structitem.TitleItem;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout;
import com.meizu.cloud.app.block.structlayout.AbsBlockLayout.OnChildClickListener;
import com.meizu.cloud.app.block.structlayout.AdvertiseBlockLayout;
import com.meizu.cloud.app.block.structlayout.RollingPlayLayout;
import com.meizu.cloud.app.core.t;
import com.meizu.cloud.app.core.w;
import com.meizu.cloud.app.downlad.f;
import com.meizu.cloud.app.downlad.f.c;
import com.meizu.cloud.app.downlad.f.d;
import com.meizu.cloud.app.downlad.f.e;
import com.meizu.cloud.app.downlad.f.g;
import com.meizu.cloud.app.downlad.f.i;
import com.meizu.cloud.app.request.structitem.AbstractStrcutItem;
import com.meizu.cloud.app.request.structitem.AppStructItem;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.base.a.b;
import java.util.ArrayList;
import java.util.List;

public abstract class k extends b<AbsBlockItem> implements OnChildClickListener, f.b, d, e, g, i {
    protected com.meizu.cloud.base.b.d a;
    protected AbsListView b;
    protected int[] c = this.f.a();

    public static class a {
        public AbsBlockLayout a;
    }

    public /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public k(Context context, com.meizu.cloud.base.b.d fragment, AbsListView absListView, ArrayList<AbsBlockItem> items, t viewController) {
        super(context, items, viewController);
        this.a = fragment;
        this.b = absListView;
    }

    public AbsBlockItem a(int i) {
        return (AbsBlockItem) this.d.get(i);
    }

    public int getViewTypeCount() {
        return 25;
    }

    public int getItemViewType(int position) {
        return a(position).style;
    }

    public boolean isEnabled(int position) {
        return false;
    }

    protected void a(View view, Context context, int position, AbsBlockItem data) {
        a holder = (a) view.getTag();
        if (holder != null && holder.a != null) {
            a(data, position);
            holder.a.updateView(this.e, data, this.f, position);
            if (((holder.a instanceof RollingPlayLayout) || (holder.a instanceof AdvertiseBlockLayout)) && (holder.a instanceof RollingPlayLayout) && (this.a instanceof com.meizu.cloud.app.fragment.e)) {
                ((com.meizu.cloud.app.fragment.e) this.a).a((RollingPlayLayout) holder.a);
            }
        }
    }

    protected View a(Context context, int position, List<AbsBlockItem> list) {
        int viewType = getItemViewType(position);
        AbsBlockItem item = a(position);
        a holder = new a();
        AbsBlockLayout layout = BlockItemBuilder.build(this.e, item);
        View v = layout.createView(context, item);
        layout.setOnChildClickListener(this);
        holder.a = layout;
        v.setTag(holder);
        return v;
    }

    private AppStructItem a(AbsBlockItem item, int id, String apkName) {
        if (item != null) {
            if (item instanceof SingleRowAppItem) {
                SingleRowAppItem singleRowAppItem = (SingleRowAppItem) item;
                if (a(singleRowAppItem.app, id, apkName)) {
                    return singleRowAppItem.app;
                }
            } else if (item instanceof RecommendAppItem) {
                RecommendAppItem recommendAppItem = (RecommendAppItem) item;
                if (a(recommendAppItem.app, id, apkName)) {
                    return recommendAppItem.app;
                }
            } else if (item instanceof Row1Col2AppItem) {
                Row1Col2AppItem row1Col2AppItem = (Row1Col2AppItem) item;
                if (a(row1Col2AppItem.app1, id, apkName)) {
                    return row1Col2AppItem.app1;
                }
                if (a(row1Col2AppItem.app2, id, apkName)) {
                    return row1Col2AppItem.app2;
                }
            } else if (item instanceof AdAppBigItem) {
                AdAppBigItem adAppBigItem = (AdAppBigItem) item;
                if (a(adAppBigItem.mAppAdBigStructItem, id, apkName)) {
                    return adAppBigItem.mAppAdBigStructItem;
                }
            } else if (item instanceof Row1Col2AppVerItem) {
                Row1Col2AppVerItem row1Col2AppVerItem = (Row1Col2AppVerItem) item;
                if (a(row1Col2AppVerItem.mAppStructItem1, id, apkName)) {
                    return row1Col2AppVerItem.mAppStructItem1;
                }
                if (a(row1Col2AppVerItem.mAppStructItem2, id, apkName)) {
                    return row1Col2AppVerItem.mAppStructItem2;
                }
            } else if (item instanceof Row1Col3AppVerItem) {
                Row1Col3AppVerItem row1Col3AppVerItem = (Row1Col3AppVerItem) item;
                if (a(row1Col3AppVerItem.mAppStructItem1, id, apkName)) {
                    return row1Col3AppVerItem.mAppStructItem1;
                }
                if (a(row1Col3AppVerItem.mAppStructItem2, id, apkName)) {
                    return row1Col3AppVerItem.mAppStructItem2;
                }
                if (a(row1Col3AppVerItem.mAppStructItem3, id, apkName)) {
                    return row1Col3AppVerItem.mAppStructItem3;
                }
            } else if (item instanceof Row1Col4AppVerItem) {
                Row1Col4AppVerItem row1Col4AppVerItem = (Row1Col4AppVerItem) item;
                if (a(row1Col4AppVerItem.mAppStructItem1, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem1;
                }
                if (a(row1Col4AppVerItem.mAppStructItem2, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem2;
                }
                if (a(row1Col4AppVerItem.mAppStructItem3, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem3;
                }
                if (a(row1Col4AppVerItem.mAppStructItem4, id, apkName)) {
                    return row1Col4AppVerItem.mAppStructItem4;
                }
            } else if (item instanceof GameQualityItem) {
                GameQualityItem gameQualityItem = (GameQualityItem) item;
                if (a(gameQualityItem.mGameQualityStructItem, id, apkName)) {
                    return gameQualityItem.mGameQualityStructItem;
                }
            } else if (item instanceof Row2Col2AppVerItem) {
                Row2Col2AppVerItem row2Col2AppVerItem = (Row2Col2AppVerItem) item;
                if (a(row2Col2AppVerItem.mAppStructItem1, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem1;
                }
                if (a(row2Col2AppVerItem.mAppStructItem2, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem2;
                }
                if (a(row2Col2AppVerItem.mAppStructItem3, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem3;
                }
                if (a(row2Col2AppVerItem.mAppStructItem4, id, apkName)) {
                    return row2Col2AppVerItem.mAppStructItem4;
                }
            }
        }
        return null;
    }

    private boolean a(AppStructItem item, int id, String apkName) {
        if (item == null) {
            return false;
        }
        if (id > 0) {
            if (item.id == id) {
                return true;
            }
            return false;
        } else if (TextUtils.isEmpty(apkName)) {
            return false;
        } else {
            return item.package_name.equals(apkName);
        }
    }

    public void onInstallStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        a(wrapper, true);
    }

    public void onFetchStateChange(com.meizu.cloud.app.downlad.e wrapper) {
        a(wrapper, false);
    }

    public void a(com.meizu.cloud.app.downlad.e wrapper) {
        a(wrapper, false);
    }

    public void b(com.meizu.cloud.app.downlad.e wrapper) {
        a(wrapper, false);
    }

    public void onDownloadProgress(com.meizu.cloud.app.downlad.e wrapper) {
        a(wrapper, false);
    }

    public void onDownloadStateChanged(com.meizu.cloud.app.downlad.e wrapper) {
        if (wrapper != null && wrapper.f() != c.TASK_STARTED) {
            a(wrapper, false);
        }
    }

    private void a(com.meizu.cloud.app.downlad.e wrapper, boolean isInstalled) {
        if (wrapper != null && wrapper.i() > 0 && this.b != null && this.d != null && this.f != null && !wrapper.j().d()) {
            View view;
            int firstVisiblePos = this.b.getFirstVisiblePosition();
            int lastVisiblePos = this.b.getLastVisiblePosition();
            List<View> sameTagView = null;
            String packageName = null;
            int i = firstVisiblePos;
            while (i <= lastVisiblePos) {
                if (this.d.size() > i) {
                    AppStructItem appStructItem = a((AbsBlockItem) this.d.get(i), wrapper.i(), "");
                    if (appStructItem != null) {
                        packageName = appStructItem.package_name;
                        view = this.b.findViewWithTag(packageName);
                        if (view != null) {
                            if (sameTagView == null) {
                                sameTagView = new ArrayList();
                            }
                            if (view instanceof LinearLayout) {
                                view.setTag(null);
                                sameTagView.add(view);
                                CirProButton btn = (CirProButton) view.findViewWithTag(Integer.valueOf(appStructItem.id));
                                if (isInstalled) {
                                    w displayConfig = new w();
                                    displayConfig.a = com.meizu.cloud.b.a.c.btn_default;
                                    displayConfig.c = 17170443;
                                    displayConfig.b = -1;
                                    displayConfig.d = com.meizu.cloud.b.a.c.btn_operation_downloading_text;
                                    displayConfig.e = 17170443;
                                    displayConfig.f = 17170443;
                                    this.f.a(displayConfig);
                                    this.f.a(wrapper, btn);
                                    this.f.a(null);
                                } else {
                                    this.f.a(wrapper, btn);
                                }
                            } else if (view instanceof CirProButton) {
                                view.setTag(null);
                                sameTagView.add(view);
                                this.f.a(wrapper, (CirProButton) view);
                            }
                        } else {
                            notifyDataSetChanged();
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
            if (!TextUtils.isEmpty(packageName) && sameTagView != null && sameTagView.size() > 0) {
                for (View view2 : sameTagView) {
                    if (view2 != null) {
                        view2.setTag(packageName);
                    }
                }
                sameTagView.clear();
            }
        }
    }

    public void a(String apkName) {
        if (!TextUtils.isEmpty(apkName) && this.b != null && this.d != null && this.f != null) {
            View view;
            int firstVisiblePos = this.b.getFirstVisiblePosition();
            int lastVisiblePos = this.b.getLastVisiblePosition();
            List<View> sameTagView = null;
            int i = firstVisiblePos;
            while (i <= lastVisiblePos) {
                if (this.d.size() > i) {
                    com.meizu.cloud.app.core.t.a appStructItem = a((AbsBlockItem) this.d.get(i), 0, apkName);
                    if (appStructItem != null) {
                        view = this.b.findViewWithTag(appStructItem.package_name);
                        if (view != null) {
                            CirProButton btn = null;
                            if (sameTagView == null) {
                                sameTagView = new ArrayList();
                            }
                            if (view instanceof LinearLayout) {
                                view.setTag(null);
                                sameTagView.add(view);
                                btn = (CirProButton) view.findViewWithTag(Integer.valueOf(appStructItem.id));
                            } else if (view instanceof CirProButton) {
                                view.setTag(null);
                                sameTagView.add(view);
                                btn = (CirProButton) view;
                            }
                            this.f.a(appStructItem, null, true, btn);
                        } else {
                            notifyDataSetChanged();
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
            if (sameTagView != null && sameTagView.size() > 0) {
                for (View view2 : sameTagView) {
                    if (view2 != null) {
                        view2.setTag(apkName);
                    }
                }
                sameTagView.clear();
            }
        }
    }

    private void a(AbsBlockItem absBlockItem, int position) {
        if (absBlockItem != null) {
            com.meizu.cloud.statistics.a.a(this.e).a(absBlockItem);
            switch (absBlockItem.style) {
                case 0:
                    if ((absBlockItem instanceof TitleItem) && !((TitleItem) absBlockItem).is_uxip_exposured) {
                        a((TitleItem) absBlockItem, position);
                        return;
                    }
                    return;
                case 1:
                    if ((absBlockItem instanceof RollingPlayItem) && ((RollingPlayItem) absBlockItem).rollingPlayItem != null) {
                        RollingPlayStructItem rollingPlayStructItem = ((RollingPlayItem) absBlockItem).rollingPlayItem;
                        if (rollingPlayStructItem.mSubItems != null && rollingPlayStructItem.mSubItems.size() > 0) {
                            for (int i = 0; i < rollingPlayStructItem.mSubItems.size(); i++) {
                                AbstractStrcutItem abstractStrcutItem = (AbstractStrcutItem) rollingPlayStructItem.mSubItems.get(i);
                                if (abstractStrcutItem != null && (abstractStrcutItem instanceof AppAdStructItem)) {
                                    abstractStrcutItem.pos_ver = position + 1;
                                    abstractStrcutItem.cur_page = this.f.b();
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    if (absBlockItem instanceof AdvertiseItem) {
                        if (!(((AdvertiseItem) absBlockItem).advertise1 == null || ((AdvertiseItem) absBlockItem).advertise1.is_uxip_exposured)) {
                            a(((AdvertiseItem) absBlockItem).advertise1, position);
                        }
                        if (((AdvertiseItem) absBlockItem).advertise2 != null && !((AdvertiseItem) absBlockItem).advertise2.is_uxip_exposured) {
                            a(((AdvertiseItem) absBlockItem).advertise2, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 3:
                    if (absBlockItem instanceof Row1Col2AppItem) {
                        if (!(((Row1Col2AppItem) absBlockItem).app1 == null || ((Row1Col2AppItem) absBlockItem).app1.is_uxip_exposured)) {
                            a(((Row1Col2AppItem) absBlockItem).app1, position);
                        }
                        if (((Row1Col2AppItem) absBlockItem).app2 != null && !((Row1Col2AppItem) absBlockItem).app2.is_uxip_exposured) {
                            a(((Row1Col2AppItem) absBlockItem).app2, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 4:
                    if ((absBlockItem instanceof SingleRowAppItem) && ((SingleRowAppItem) absBlockItem).app != null && !((SingleRowAppItem) absBlockItem).app.is_uxip_exposured) {
                        a(((SingleRowAppItem) absBlockItem).app, position);
                        return;
                    }
                    return;
                case 5:
                    if ((absBlockItem instanceof RecommendAppItem) && ((RecommendAppItem) absBlockItem).app != null && !((RecommendAppItem) absBlockItem).app.is_uxip_exposured) {
                        a(((RecommendAppItem) absBlockItem).app, position);
                        return;
                    }
                    return;
                case 8:
                    if (absBlockItem instanceof ContsRow1Col4Item) {
                        if (!(((ContsRow1Col4Item) absBlockItem).item1 == null || ((ContsRow1Col4Item) absBlockItem).item1.is_uxip_exposured)) {
                            a(((ContsRow1Col4Item) absBlockItem).item1, position);
                        }
                        if (!(((ContsRow1Col4Item) absBlockItem).item2 == null || ((ContsRow1Col4Item) absBlockItem).item2.is_uxip_exposured)) {
                            a(((ContsRow1Col4Item) absBlockItem).item2, position);
                        }
                        if (!(((ContsRow1Col4Item) absBlockItem).item3 == null || ((ContsRow1Col4Item) absBlockItem).item3.is_uxip_exposured)) {
                            a(((ContsRow1Col4Item) absBlockItem).item3, position);
                        }
                        if (((ContsRow1Col4Item) absBlockItem).item4 != null && !((ContsRow1Col4Item) absBlockItem).item4.is_uxip_exposured) {
                            a(((ContsRow1Col4Item) absBlockItem).item4, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 9:
                    if ((absBlockItem instanceof AdBigItem) && ((AdBigItem) absBlockItem).mAdBigStructItem != null && !((AdBigItem) absBlockItem).mAdBigStructItem.is_uxip_exposured) {
                        a(((AdBigItem) absBlockItem).mAdBigStructItem, position);
                        return;
                    }
                    return;
                case 10:
                    if ((absBlockItem instanceof AdAppBigItem) && ((AdAppBigItem) absBlockItem).mAppAdBigStructItem != null && !((AdAppBigItem) absBlockItem).mAppAdBigStructItem.is_uxip_exposured) {
                        a(((AdAppBigItem) absBlockItem).mAppAdBigStructItem, position);
                        return;
                    }
                    return;
                case 11:
                    if (absBlockItem instanceof Row1Col4AppVerItem) {
                        if (!(((Row1Col4AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row1Col4AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (!(((Row1Col4AppVerItem) absBlockItem).mAppStructItem2 == null || ((Row1Col4AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured)) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem2, position);
                        }
                        if (!(((Row1Col4AppVerItem) absBlockItem).mAppStructItem3 == null || ((Row1Col4AppVerItem) absBlockItem).mAppStructItem3.is_uxip_exposured)) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem3, position);
                        }
                        if (((Row1Col4AppVerItem) absBlockItem).mAppStructItem4 != null && !((Row1Col4AppVerItem) absBlockItem).mAppStructItem4.is_uxip_exposured) {
                            a(((Row1Col4AppVerItem) absBlockItem).mAppStructItem4, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 12:
                    if (absBlockItem instanceof ChannelCol5Item) {
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem1 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem1.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem1, position);
                        }
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem2 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem2.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem2, position);
                        }
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem3 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem3.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem3, position);
                        }
                        if (!(((ChannelCol5Item) absBlockItem).mChannelStructItem4 == null || ((ChannelCol5Item) absBlockItem).mChannelStructItem4.is_uxip_exposured)) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem4, position);
                        }
                        if (((ChannelCol5Item) absBlockItem).mChannelStructItem5 != null && !((ChannelCol5Item) absBlockItem).mChannelStructItem5.is_uxip_exposured) {
                            a(((ChannelCol5Item) absBlockItem).mChannelStructItem5, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 13:
                    if ((absBlockItem instanceof RollMessageItem) && ((RollMessageItem) absBlockItem).mRollMessageStructItem != null && ((RollMessageItem) absBlockItem).mRollMessageStructItem.size() > 0) {
                        for (AbstractStrcutItem item : ((RollMessageItem) absBlockItem).mRollMessageStructItem) {
                            if (!(item == null || item.is_uxip_exposured)) {
                                a(item, position);
                            }
                        }
                        return;
                    }
                    return;
                case 14:
                    if ((absBlockItem instanceof GameQualityItem) && ((GameQualityItem) absBlockItem).mGameQualityStructItem != null && !((GameQualityItem) absBlockItem).mGameQualityStructItem.is_uxip_exposured) {
                        a(((GameQualityItem) absBlockItem).mGameQualityStructItem, position);
                        return;
                    }
                    return;
                case 15:
                    return;
                case 16:
                    if (absBlockItem instanceof Row1Col2AppVerItem) {
                        if (!(((Row1Col2AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row1Col2AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row1Col2AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (((Row1Col2AppVerItem) absBlockItem).mAppStructItem2 != null && !((Row1Col2AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured) {
                            a(((Row1Col2AppVerItem) absBlockItem).mAppStructItem2, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 17:
                    if (absBlockItem instanceof Row1Col3AppVerItem) {
                        if (!(((Row1Col3AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row1Col3AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row1Col3AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (!(((Row1Col3AppVerItem) absBlockItem).mAppStructItem2 == null || ((Row1Col3AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured)) {
                            a(((Row1Col3AppVerItem) absBlockItem).mAppStructItem2, position);
                        }
                        if (((Row1Col3AppVerItem) absBlockItem).mAppStructItem3 != null && !((Row1Col3AppVerItem) absBlockItem).mAppStructItem3.is_uxip_exposured) {
                            a(((Row1Col3AppVerItem) absBlockItem).mAppStructItem3, position);
                            return;
                        }
                        return;
                    }
                    return;
                case 22:
                    if (absBlockItem instanceof Row2Col2AppVerItem) {
                        if (!(((Row2Col2AppVerItem) absBlockItem).mAppStructItem1 == null || ((Row2Col2AppVerItem) absBlockItem).mAppStructItem1.is_uxip_exposured)) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem1, position);
                        }
                        if (!(((Row2Col2AppVerItem) absBlockItem).mAppStructItem2 == null || ((Row2Col2AppVerItem) absBlockItem).mAppStructItem2.is_uxip_exposured)) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem2, position);
                        }
                        if (!(((Row2Col2AppVerItem) absBlockItem).mAppStructItem3 == null || ((Row2Col2AppVerItem) absBlockItem).mAppStructItem3.is_uxip_exposured)) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem3, position);
                        }
                        if (((Row2Col2AppVerItem) absBlockItem).mAppStructItem4 != null && !((Row2Col2AppVerItem) absBlockItem).mAppStructItem4.is_uxip_exposured) {
                            a(((Row2Col2AppVerItem) absBlockItem).mAppStructItem4, position);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void a(AppAdStructItem appAdStructItem, int position) {
        appAdStructItem.pos_ver = position + 1;
        appAdStructItem.cur_page = this.f.b();
        com.meizu.cloud.statistics.b.a().a("block_exposure", appAdStructItem.cur_page, com.meizu.cloud.statistics.c.a(appAdStructItem));
        appAdStructItem.is_uxip_exposured = true;
    }

    private void a(TitleItem titleItem, int position) {
        titleItem.pos_ver = position + 1;
        titleItem.cur_page = this.f.b();
        com.meizu.cloud.statistics.b.a().a("block_exposure", titleItem.cur_page, com.meizu.cloud.statistics.c.a(titleItem));
        titleItem.is_uxip_exposured = true;
    }

    private void a(AppStructItem appStructItem, int position) {
        appStructItem.pos_ver = position + 1;
        appStructItem.cur_page = this.f.b();
        com.meizu.cloud.statistics.b.a().a("block_exposure", appStructItem.cur_page, com.meizu.cloud.statistics.c.c(appStructItem));
        appStructItem.is_uxip_exposured = true;
    }

    private void a(AbstractStrcutItem abstractStrcutItem, int position) {
        abstractStrcutItem.pos_ver = position + 1;
        abstractStrcutItem.cur_page = this.f.b();
        com.meizu.cloud.statistics.b.a().a("block_exposure", abstractStrcutItem.cur_page, com.meizu.cloud.statistics.c.a(abstractStrcutItem));
        abstractStrcutItem.is_uxip_exposured = true;
    }
}
