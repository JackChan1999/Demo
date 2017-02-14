package com.qq.demo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qq.demo.R;
import com.qq.demo.ui.widget.TextWave;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/25 14:41
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class TextWaveAct extends AppCompatActivity {

    private TextWave mTextWave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_textwave);
        mTextWave = (TextWave) findViewById(R.id.textwave);
    }

}
