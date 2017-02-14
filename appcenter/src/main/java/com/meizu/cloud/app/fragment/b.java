package com.meizu.cloud.app.fragment;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.meizu.cloud.app.request.model.AppStructDetailsItem;
import com.meizu.cloud.app.request.model.PermissionInfos;
import com.meizu.cloud.app.utils.h;
import com.meizu.cloud.b.a.f;
import com.meizu.cloud.b.a.g;
import com.meizu.cloud.b.a.i;
import com.meizu.cloud.base.b.d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class b extends d {
    private Context a;
    private AppStructDetailsItem b;
    private List<c> c;
    private ImageView d;
    private TextView e;
    private TextView f;
    private ListView g;

    private class a extends BaseAdapter {
        final /* synthetic */ b a;
        private Context b;
        private List<c> c;

        public a(b bVar, Context context, List<c> listData) {
            this.a = bVar;
            this.b = context;
            this.c = listData;
        }

        public int getCount() {
            if (this.c != null) {
                return this.c.size();
            }
            return 0;
        }

        public Object getItem(int position) {
            if (this.c == null || this.c.size() <= 0) {
                return null;
            }
            return this.c.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            b viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.b).inflate(g.app_permissions_list_item, null);
                viewHolder = new b();
                viewHolder.a = (TextView) convertView.findViewById(f.app_permission_grouplabel);
                viewHolder.b = (TextView) convertView.findViewById(f.app_permission_labels);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (b) convertView.getTag();
            }
            viewHolder.a.setText(((c) this.c.get(position)).a);
            viewHolder.b.setText(((c) this.c.get(position)).b);
            return convertView;
        }
    }

    private class b {
        TextView a;
        TextView b;
        final /* synthetic */ b c;

        private b(b bVar) {
            this.c = bVar;
        }
    }

    private class c {
        public String a;
        public String b;
        public String c;
        final /* synthetic */ b d;

        private c(b bVar) {
            this.d = bVar;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.a = getActivity();
        this.b = (AppStructDetailsItem) getArguments().getParcelable("details_info");
    }

    protected void setupActionBar() {
        super.setupActionBar();
        getActionBar().a(i.app_permission_details_fragment_title);
    }

    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(g.app_permissions_list_layout, container, false);
    }

    protected void initView(View rootView) {
        this.g = (ListView) rootView.findViewById(f.app_permissions_listview);
        com.meizu.cloud.app.utils.d.a(this.a, this.g);
        View headerView = LayoutInflater.from(this.a).inflate(g.app_permissions_list_header, null);
        this.d = (ImageView) headerView.findViewById(f.app_permissions_appicon);
        this.e = (TextView) headerView.findViewById(f.app_permissions_appname);
        this.f = (TextView) headerView.findViewById(f.app_permissions_appver);
        headerView.setEnabled(false);
        this.g.addHeaderView(headerView);
        if (this.b != null) {
            h.a(this.a, this.b.icon, this.d);
            this.e.setText(this.b.name);
            this.f.setText(String.format(getString(i.version_format), new Object[]{this.b.version_name}));
            a(this.b.permissions);
            a permiListAdapter = new a(this, this.a, this.c);
            this.g.setOnItemClickListener(new OnItemClickListener(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }

                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    int pos = position - this.a.g.getHeaderViewsCount();
                    if (pos >= 0 && pos < this.a.c.size()) {
                        View dialogView = LayoutInflater.from(this.a.a).inflate(g.app_permissions_list_dialogview, null);
                        ((TextView) dialogView.findViewById(f.app_permissions_list_dialogtv)).setText(((c) this.a.c.get(pos)).c);
                        new Builder(this.a.a).setView(dialogView).show();
                    }
                }
            });
            this.g.setAdapter(permiListAdapter);
        }
    }

    private void a(List<PermissionInfos> listData) {
        if (listData != null && listData.size() > 0) {
            c permissionsListItem;
            Map<String, String> linkedHashMap = new LinkedHashMap();
            Map<String, String> groupDescMap = new HashMap();
            PackageManager pm = this.a.getPackageManager();
            String otherPermissions = "";
            String permissionCode = "";
            PermissionInfo permissionInfo = null;
            PermissionGroupInfo permissionGroupInfo = null;
            CharSequence permissionLabel = "";
            CharSequence permissionGroupLabel = "";
            CharSequence permissionGroupDesc = "";
            for (int i = 0; i < listData.size(); i++) {
                try {
                    permissionInfo = pm.getPermissionInfo(((PermissionInfos) listData.get(i)).permission_code, 128);
                    if (!(permissionInfo == null || TextUtils.isEmpty(permissionInfo.group))) {
                        permissionGroupInfo = pm.getPermissionGroupInfo(permissionInfo.group, 128);
                    }
                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (permissionInfo != null) {
                    permissionLabel = permissionInfo.loadLabel(pm).toString();
                    if (!TextUtils.isEmpty(permissionLabel)) {
                        if (permissionGroupInfo != null) {
                            permissionGroupLabel = permissionGroupInfo.loadLabel(pm);
                            permissionGroupDesc = permissionGroupInfo.loadDescription(pm);
                            if (TextUtils.isEmpty(permissionGroupLabel)) {
                                if (TextUtils.isEmpty(otherPermissions)) {
                                    otherPermissions = permissionLabel.toString();
                                } else {
                                    otherPermissions = otherPermissions + "\n" + permissionLabel;
                                }
                            } else if (linkedHashMap.containsKey(permissionGroupLabel)) {
                                linkedHashMap.put(permissionGroupLabel.toString(), ((String) linkedHashMap.get(permissionGroupLabel)) + "\n" + permissionLabel.toString());
                            } else {
                                linkedHashMap.put(permissionGroupLabel.toString(), permissionLabel.toString());
                                groupDescMap.put(permissionGroupLabel.toString(), permissionGroupDesc.toString());
                            }
                        } else if (TextUtils.isEmpty(otherPermissions)) {
                            otherPermissions = permissionLabel.toString();
                        } else {
                            otherPermissions = otherPermissions + "\n" + permissionLabel.toString();
                        }
                    }
                }
                permissionCode = "";
                permissionInfo = null;
                permissionGroupInfo = null;
                permissionLabel = "";
                permissionGroupLabel = "";
                permissionGroupDesc = "";
            }
            List<c> permissionsListItems = new ArrayList();
            for (Entry<String, String> entry : linkedHashMap.entrySet()) {
                permissionsListItem = new c();
                permissionsListItem.a = (String) entry.getKey();
                permissionsListItem.b = (String) entry.getValue();
                if (groupDescMap.containsKey(entry.getKey())) {
                    permissionsListItem.c = (String) groupDescMap.get(entry.getKey());
                }
                permissionsListItems.add(permissionsListItem);
            }
            if (listData.size() > 0 && !TextUtils.isEmpty(otherPermissions)) {
                permissionsListItem = new c();
                permissionsListItem.a = getString(i.other);
                permissionsListItem.b = otherPermissions;
                permissionsListItem.c = getString(i.other);
                permissionsListItems.add(permissionsListItem);
            }
            this.c = permissionsListItems;
        }
    }

    public void onStart() {
        super.onStart();
        com.meizu.cloud.statistics.b.a().a("permissions");
    }

    public void onStop() {
        super.onStop();
        com.meizu.cloud.statistics.b.a().a("permissions", null);
    }
}
