package com.google.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        Info info = new Info(Parcel.obtain());

        Intent intent = new Intent(this,TestActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("key",info);
        intent.putExtra("name", bundle);

    }
}
