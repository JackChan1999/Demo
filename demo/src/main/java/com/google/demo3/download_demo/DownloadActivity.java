package com.google.demo3.download_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.demo3.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/5/14 13:17
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public class DownloadActivity extends Activity {

    private static final String filename = "QQ.exe";
    private static final String url = "http://360.com" + filename;

    private int mThreadCount = 3;
    private int mFinishedThread = 0;
    private int mCurrentProgress;

    private ProgressBar mPb;
    private TextView mTv;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mTv.setText((long)mPb.getProgress()*100 / mPb.getMax() + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_download);

    }

    public void click(View view) {
        ThreadPoolFactory.getNormalProxy().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpURLConnection connection = getHttpURLConnection(url);
                    //获取下载文件的长度
                    if (connection.getResponseCode() == 200) {
                        int length = connection.getContentLength();
                        mPb.setMax(length);

                        //生成临时文件
                        File file = new File(Environment.getExternalStorageDirectory(), filename);
                        RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                        raf.setLength(length);
                        raf.close();

                        //计算下载区间
                        int size = length / mThreadCount;
                        for (int i = 0; i < mThreadCount; i++) {
                            int startIndex = i * size;
                            int endIndex = (i + 1) * size - 1;
                            if (i == mThreadCount - 1) {
                                endIndex = length - 1;
                            }
                            ThreadPoolFactory.getDownloadProxy().execute(new DownloadTask
                                    (startIndex, endIndex, i));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class DownloadTask implements Runnable {

        private int startIndex;
        private int endIndex;
        private int threadId;

        public DownloadTask(int startIndex, int endIndex, int threadId) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.threadId = threadId;
        }

        @Override
        public void run() {
            try {
                //获取临时下载进度
                File progressFile = new File(Environment.getExternalStorageDirectory(), threadId
                        + ".txt");
                if (progressFile.exists()){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new
                            FileInputStream(progressFile)));
                    int lastIndex = Integer.parseInt(reader.readLine());
                    startIndex += lastIndex;
                    mCurrentProgress += lastIndex;
                    mPb.setProgress(mCurrentProgress);
                    mHandler.sendEmptyMessage(0);
                    reader.close();
                }

                HttpURLConnection connection = getHttpURLConnection(url);
                connection.setRequestProperty("Range", "byte=" + startIndex + "-" + endIndex);
                if (connection.getResponseCode() == 206) {
                    InputStream is = connection.getInputStream();
                    byte[] b = new byte[1024];
                    int len = 0;
                    int total = 0;

                    File file = new File(Environment.getExternalStorageDirectory(), filename);
                    RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                    raf.seek(startIndex);

                    while ((len = is.read(b)) != -1) {
                        raf.write(b, 0, len);
                        mCurrentProgress += len;
                        mPb.setProgress(mCurrentProgress);
                        mHandler.sendEmptyMessage(0);
                        total += len;

                        //记录下载进度
                        RandomAccessFile progressRaf = new RandomAccessFile(progressFile, "rwd");
                        progressRaf.write((total + "").getBytes());
                        progressRaf.close();
                    }

                    raf.close();
                    mFinishedThread++;
                    if (mFinishedThread == mThreadCount) {
                        synchronized (url) {
                            for (int i = 0; i < mThreadCount; i++) {
                                File tempfile = new File(Environment.getExternalStorageDirectory
                                        (), i + ".txt");
                                tempfile.delete();
                            }
                            mFinishedThread = 0 ;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private HttpURLConnection getHttpURLConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5 * 1000);
        connection.setReadTimeout(5 * 1000);
        return connection;
    }
}
