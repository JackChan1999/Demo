package com.meizu.cloud.app.share;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.meizu.cloud.app.utils.r;
import com.meizu.cloud.b.a.d;
import com.meizu.cloud.b.a.e;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.common.widget.LimitedWHLinearLayout;
import com.tencent.mm.sdk.constants.ConstantsAPI.WXApp;
import java.util.ArrayList;
import java.util.List;

public class ResolverActivity extends Activity {
    private BroadcastReceiver a;
    private ArrayList<a> b = new ArrayList();
    private String c;
    private String d = "";

    class a {
        int a;
        int b;
        String c;
        String d;
        final /* synthetic */ ResolverActivity e;

        public a(ResolverActivity resolverActivity, int name, int icon, String pkg, String clazz) {
            this.e = resolverActivity;
            this.a = name;
            this.b = icon;
            this.c = pkg;
            this.d = clazz;
        }
    }

    class b extends BaseAdapter {
        final /* synthetic */ ResolverActivity a;
        private PackageManager b;
        private int c;
        private List<ResolveInfo> d;
        private Intent e;

        class a {
            public TextView a;
            public ImageView b;
            final /* synthetic */ b c;

            public a(b bVar, View view) {
                this.c = bVar;
                this.a = (TextView) view.findViewById(f.text1);
                this.b = (ImageView) view.findViewById(f.icon);
            }
        }

        b(ResolverActivity resolverActivity, Intent intent) {
            this.a = resolverActivity;
            this.b = resolverActivity.getApplication().getPackageManager();
            this.e = intent;
            this.c = ((ActivityManager) resolverActivity.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getLauncherLargeIconDensity();
            b();
        }

        private void b() {
            this.d = this.b.queryIntentActivities(this.e, Surrogate.UCS4_MIN);
            a packageManagerProxy = new a();
            boolean isGuestMode = packageManagerProxy.a();
            for (int i = this.d.size() - 1; i >= 0; i--) {
                ResolveInfo ri = (ResolveInfo) this.d.get(i);
                if (r.a(ri.activityInfo.permission, Process.myUid(), ri.activityInfo.applicationInfo.uid, ri.activityInfo.exported) != 0) {
                    this.d.remove(ri);
                } else if (isGuestMode && packageManagerProxy.a(ri.activityInfo.applicationInfo.packageName)) {
                    this.d.remove(ri);
                } else {
                    if (ri.activityInfo.name.contains(WXApp.WXAPP_PACKAGE_NAME) || ri.activityInfo.name.contains("com.tencent.mobileqq") || ri.activityInfo.name.contains("com.qzone")) {
                        this.d.remove(ri);
                    }
                    if (ri.activityInfo.name.contains("com.sina.weibo")) {
                        this.a.c = ri.activityInfo.name;
                        if (TextUtils.isEmpty(this.a.d)) {
                            this.a.b.set(3, new a(this.a, i.sina_weibo, e.share_weibo, "com.sina.weibo", ri.activityInfo.name));
                        } else {
                            this.a.b.set(4, new a(this.a, i.sina_weibo, e.share_weibo, "com.sina.weibo", ri.activityInfo.name));
                        }
                        this.d.remove(ri);
                    }
                }
            }
        }

        public int getCount() {
            return this.a.b.size() + this.d.size();
        }

        public Object getItem(int i) {
            if (i < this.a.b.size()) {
                return this.a.b.get(i);
            }
            return this.d.get(i - this.a.b.size());
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int i, View convertView, ViewGroup viewGroup) {
            View view;
            if (convertView == null) {
                view = this.a.getLayoutInflater().inflate(g.resolve_list_item, null);
                view.setTag(new a(this, view));
            } else {
                view = convertView;
            }
            a(view, getItem(i));
            return view;
        }

        void a(View view, Object obj) {
            a holder = (a) view.getTag();
            if (obj instanceof a) {
                a item = (a) obj;
                holder.a.setText(item.a);
                holder.b.setImageDrawable(a(this.a, this.a.getResources().getDrawable(item.b)));
            } else if (obj instanceof ResolveInfo) {
                ResolveInfo ri = (ResolveInfo) obj;
                holder.a.setText(ri.loadLabel(this.b).toString().replaceFirst("^(((发送|分享|添加|保存)((至|到|给)?))|(发(至|到|给)))", ""));
                holder.b.setImageDrawable(a(ri));
            }
        }

        Drawable a(ResolveInfo ri) {
            Drawable dr = ri.loadIcon(this.b);
            if (dr != null) {
                return dr;
            }
            try {
                if (!(ri.resolvePackageName == null || ri.icon == 0)) {
                    dr = a(this.b.getResourcesForApplication(ri.resolvePackageName), ri.icon);
                    if (dr != null) {
                        return dr;
                    }
                }
                int iconRes = ri.getIconResource();
                if (iconRes != 0) {
                    dr = a(this.b.getResourcesForApplication(ri.activityInfo.packageName), iconRes);
                    if (dr != null) {
                        return dr;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("GridAdapter", "Couldn't find resources for package");
            }
            return ri.loadIcon(this.b);
        }

        Drawable a(Resources res, int resId) {
            try {
                return res.getDrawableForDensity(resId, this.c);
            } catch (NotFoundException e) {
                e.printStackTrace();
                Log.v("GridAdapter", "Couldn't find resources for package in getIcon");
                return null;
            }
        }

        private Drawable a(Context context, Drawable drawable) {
            if (drawable == null || context == null) {
                return drawable;
            }
            try {
                Bitmap BitmapOrg = ((BitmapDrawable) drawable).getBitmap();
                int width = BitmapOrg.getWidth();
                int height = BitmapOrg.getHeight();
                int newWidth = context.getResources().getDimensionPixelSize(d.mz_resolver_third_icon_size);
                int newHeight = newWidth;
                int systemIconSize = context.getResources().getDimensionPixelSize(d.mz_resolver_icon_size);
                if ((systemIconSize == width || width == 240) && (systemIconSize == height || width == 240)) {
                    return drawable;
                }
                Matrix matrix = new Matrix();
                matrix.postScale(((float) newWidth) / ((float) width), ((float) newHeight) / ((float) height));
                return new BitmapDrawable(context.getResources(), Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true));
            } catch (Exception e) {
                e.printStackTrace();
                return drawable;
            }
        }

        public ComponentName a(int position) {
            if (position < this.a.b.size()) {
                a item = (a) this.a.b.get(position);
                return new ComponentName(item.c, item.d);
            }
            ActivityInfo ai = ((ResolveInfo) this.d.get(position - this.a.b.size())).activityInfo;
            return new ComponentName(ai.applicationInfo.packageName, ai.name);
        }

        public void a() {
            if (this.d != null) {
                this.d.clear();
                this.d = null;
            }
            b();
            notifyDataSetChanged();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getIntent = getIntent();
        if (getIntent != null) {
            Bundle bundle = getIntent.getExtras();
            if (bundle != null) {
                this.d = bundle.getString("wechat_shareurl", "");
            }
        }
        a();
        setContentView(g.share_resolver);
        ((LimitedWHLinearLayout) findViewById(f.root)).setMaxHeight(getResources().getDimensionPixelSize(d.resolver_max_height));
        GridView gridView = (GridView) findViewById(f.share_list);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", Uri.fromFile(getCacheDir()));
        final b mAdapter = new b(this, intent);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ ResolverActivity b;

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ComponentName componentName = mAdapter.a(position);
                Intent intent = new Intent();
                intent.putExtra("package", componentName.getPackageName());
                intent.putExtra("class", componentName.getClassName());
                this.b.setResult(-1, intent);
                this.b.overridePendingTransition(0, 0);
                this.b.finish();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addDataScheme("package");
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        this.a = new BroadcastReceiver(this) {
            final /* synthetic */ ResolverActivity b;

            public void onReceive(Context context, Intent intent) {
                if (mAdapter != null) {
                    mAdapter.a();
                }
            }
        };
        registerReceiver(this.a, intentFilter);
    }

    private void a() {
        if (!TextUtils.isEmpty(this.d)) {
            this.b.add(new a(this, i.wechat_friend, e.share_wechat, WXApp.WXAPP_PACKAGE_NAME, "com.tencent.mm.ui.tools.ShareImgUI"));
        }
        this.b.add(new a(this, i.wechat_moment, e.share_wechat_moment, WXApp.WXAPP_PACKAGE_NAME, "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        this.b.add(new a(this, i.qq_friend, e.share_qqfriend, "com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        this.b.add(new a(this, i.qq_zone, e.share_qqzone, "com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity"));
        this.b.add(new a(this, i.sina_weibo, e.share_weibo, "com.sina.weibo", "com.sina.weibo.composerinde.ComposerDispatchActivity"));
    }

    protected void onDestroy() {
        super.onDestroy();
        overridePendingTransition(0, 0);
        if (this.a != null) {
            unregisterReceiver(this.a);
            this.a = null;
        }
    }
}
