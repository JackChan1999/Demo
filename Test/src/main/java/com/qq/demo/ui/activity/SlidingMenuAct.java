package com.qq.demo.ui.activity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.qq.demo.R;
import com.qq.demo.ui.widget.CircleProgressBar;
import com.qq.demo.ui.widget.CircularProgressButton;
import com.qq.demo.ui.widget.ProgressTextButtonView;
import com.qq.demo.ui.widget.RainView;
import com.qq.demo.ui.widget.SlidingMenu;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/12 13:51
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class SlidingMenuAct extends AppCompatActivity {

    SlidingMenu slidingMenu;
    CircleProgressBar mProgressBar;
    CircularProgressButton cpb;
    ProgressTextButtonView mTextButtonView;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
         /*mProgressBar = (CircleProgressBar) findViewById(R.id.pb);
        mTextButtonView = (ProgressTextButtonView) findViewById(R.id.ptbv);
        cpb = mTextButtonView.getButton();
        tv = mTextButtonView.getTextView();

        new Task().execute();*/
        /* slidingMenu = (SlidingMenu) findViewById(R.id.slidingMenu);
        slidingMenu.setContent(R.layout.layout_content);
        slidingMenu.setMenu(R.layout.layout_menu);*/

        RainView rainView = (RainView) findViewById(R.id.rain);
        rainView.setBackgroundColor(Color.BLACK);
        rainView.startRain();
    }

    class Task extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {

            for (int i=0; i<=100; i++){
                SystemClock.sleep(100);
                publishProgress(i);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int progress = values[0];
            mProgressBar.setProgress(progress);
            cpb.setProgress(progress);
            tv.setText(progress + "%");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
