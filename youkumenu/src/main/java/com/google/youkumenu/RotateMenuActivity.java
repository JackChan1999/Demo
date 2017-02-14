package com.google.youkumenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RotateMenuActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView mIv_home;
    private ImageView mIv_menu;
    private RelativeLayout mLevel1;
    private RelativeLayout mLevel2;
    private RelativeLayout mLevel3;

    private boolean isShowlevel2 = true;
    private boolean isShowlevel3 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        setContentView(R.layout.activity_rotate_menu);
        mIv_home = (ImageView) findViewById(R.id.iv_home);
        mIv_menu = (ImageView) findViewById(R.id.iv_menu);
        mLevel1 = (RelativeLayout) findViewById(R.id.rl_level1);
        mLevel2 = (RelativeLayout) findViewById(R.id.rl_level2);
        mLevel3 = (RelativeLayout) findViewById(R.id.rl_level3);
    }

    private void initListener() {
        mIv_home.setOnClickListener(this);
        mIv_menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_home:
                if (AnimUtil.animCount != 0){
                    return;
                }
                if (isShowlevel2){
                    int startOffset = 0;
                    if (isShowlevel3){
                        AnimUtil.closeMenu(mLevel3,startOffset);
                        startOffset += 200;
                        isShowlevel3 = false;
                    }
                    AnimUtil.closeMenu(mLevel2,startOffset);
                }else {
                    AnimUtil.openMenu(mLevel2,0);
                }
                isShowlevel2 = !isShowlevel2;
                break;
            case R.id.iv_menu:
                if (AnimUtil.animCount != 0){
                    return;
                }
                if (isShowlevel3){
                    AnimUtil.closeMenu(mLevel3,0);
                }else {
                    AnimUtil.openMenu(mLevel3,0);
                }
                isShowlevel3 = !isShowlevel3;
                break;
        }
    }
}
