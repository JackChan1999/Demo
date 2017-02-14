package com.qq.demo.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/20 19:37
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class RainView extends View implements Runnable {
    private int width, height;
    private char[][] charset; // 随机字符集合
    private int[] pos; // 列的起始位置
    private int[] colors = new int[30]; // 列的渐变颜色

    public RainView(Context context) {
        super(context);
        init();
    }

    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        Random rand = new Random();
        charset = new char[width / 10][height / 10];
        for (int i = 0; i < charset.length; i++) {
            for (int j = 0; j < charset[i].length; j++) {
                charset[i][j] = (char) (rand.nextInt(96) + 48);
            }
        }
        // 随机化列起始位置
        pos = new int[charset.length];
        for (int i = 0; i < pos.length; i++) {
            pos[i] = rand.nextInt(pos.length);
        }
        // 生成从黑色到绿色的渐变颜色，最后一个保持为白色
        for (int i = 0; i < colors.length - 1; i++) {
            colors[i] = Color.rgb(0, 255 / colors.length * (i + 1), 0);
        }
        colors[colors.length - 1] = Color.rgb(255, 255, 255);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRain(canvas);
    }

    public void startRain() {
        new Thread(this).start();
    }

    public void drawRain(Canvas canvas) {
        Random rand = new Random();
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        for (int i = 0; i < charset.length; i++) {
            int speed = rand.nextInt(3);
            for (int j = 0; j < colors.length; j++) {
                int index = (pos[i] + j) % charset[i].length;
                paint.setColor(colors[j]);
                canvas.drawText(charset[i], index, charset[i].length, i * 100, index * 100, paint);
            }
            pos[i] = (pos[i] + 1) % charset[i].length;
        }
    }

    public void run() {
        while (true) {
            postInvalidate();
            SystemClock.sleep(50);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }
}
