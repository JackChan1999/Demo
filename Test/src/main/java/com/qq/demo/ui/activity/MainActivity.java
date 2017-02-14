package com.qq.demo.ui.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qq.demo.R;
import com.qq.demo.bean.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<View> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initView();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initView() throws PackageManager.NameNotFoundException {
        mList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            TextView textView = new TextView(this);
            textView.setText("page" + i);
            mList.add(textView);
        }

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(new MyAdapter(mList));
        mTabLayout.setupWithViewPager(mViewPager);

        MyOnTabSelectedListener listener = new MyOnTabSelectedListener();
        mTabLayout.setOnTabSelectedListener(listener);
        listener.onTabSelected(mTabLayout.getTabAt(0));
       /* Context context;
        String url;
        ImageView imageView;
        int resId = R.mipmap.ic_launcher;
        File file;
        Uri uri;
        Glide.with(context).load(url).into(imageView);
        Glide.with(context).load(resId).into(imageView);
        Glide.with(context).load(file).into(imageView);
        Glide.with(context).load(uri).into(imageView);*/
       /* Bitmap bitmap;
        SoftReference<Bitmap> softReference;
        ReferenceQueue referenceQueue;
        WeakReference<Bitmap> weakReference = new WeakReference<Bitmap>();
        PhantomReference<Bitmap> phantomReference;*/




        Intent intent = new Intent();
        intent.setClass(this,CallListenerService.class);
       // bindService(intent,null);
        unbindService(null);

        CallListenerService service;
        //service.stopService(intent);


    }

    private class CallListenerService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }

    private class LocalService extends Service{
        private LocalBinder mBinder = new LocalBinder();

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return mBinder;
        }

        public class LocalBinder extends Binder{
            LocalService getService(){
                return LocalService.this;
            }
        }

        public void doSomething(){

        }
    }

    private class SmsListenerReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String resultData = getResultData();//获取数据
            setResultData(resultData + "xx");//将修改后的数据设置出去
            if (isOrderedBroadcast()){//判断是否是有序广播
                abortBroadcast();//终止广播
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        //menu.add(groupId,itemId,order,title);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.menu.menu_main:
                //do something
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public HashMap<Integer,User> sortHashMap(HashMap<Integer,User> hashMap){
        LinkedHashMap<Integer, User> linkedHashMap = new LinkedHashMap<>();

        Set<Map.Entry<Integer, User>> entries = hashMap.entrySet();
        ArrayList<Map.Entry<Integer, User>> arrayList = new ArrayList<>(entries);
        Collections.sort(arrayList, new Comparator<Map.Entry<Integer, User>>() {
            @Override
            public int compare(Map.Entry<Integer, User> lhs, Map.Entry<Integer, User> rhs) {
                return rhs.getValue().age - lhs.getValue().age;
            }
        });

        for (Map.Entry<Integer, User> entry : arrayList){
            linkedHashMap.put(entry.getKey(),entry.getValue());
        }

        return linkedHashMap;
    }


    class MyAdapter extends PagerAdapter {

        String[] title = {"viewpager1", "viewpager2", "viewpager3", "viewpager4"};

        List<View> list;

        public MyAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

    class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            System.out.println("tab"+tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }


}
