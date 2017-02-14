package com.google.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BannerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TextView mTextView;
    private LinearLayout mContainer;

    private int mPreviousPos;
    private int[] imgs = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private String[] title = {"巩俐不低俗，我就不能低俗", "朴树又回来啦！再唱经典老歌引万人大合唱",
            "揭秘北京电影如何升级", "乐视网TV版大派送", "热血屌丝的反杀"};

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int currentItem = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(++currentItem);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_banner);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTextView = (TextView) findViewById(R.id.tvTitle);
        mContainer = (LinearLayout) findViewById(R.id.llcontainer);

        mViewPager.setAdapter(new Adapter());
        int middle = Integer.MAX_VALUE / 2;
        int extra = middle % imgs.length;
        int currentItem = middle - extra;
        mViewPager.setCurrentItem(currentItem);
        mHandler.sendEmptyMessageDelayed(0, 2000);

        for (int i = 0; i < imgs.length; i++) {
            ImageView img = new ImageView(this);
            img.setImageResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i != 0){
                params.leftMargin = 10;
                img.setLayoutParams(params);
                img.setEnabled(false);
            }

            mContainer.addView(img);
        }
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % imgs.length;
                mTextView.setText(title[pos]);
                mContainer.getChildAt(pos).setEnabled(true);
                mContainer.getChildAt(mPreviousPos).setEnabled(false);
                mPreviousPos = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        mHandler.sendEmptyMessageDelayed(0, 2000);
                        break;
                }
                return false;
            }
        });
    }

    class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (imgs.length != 0) {
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int pos = position % imgs.length;
            ImageView img = new ImageView(BannerActivity.this);
            img.setBackgroundResource(imgs[pos]);
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
