package com.google.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.demo.http.GitHubService;
import com.google.demo.model.news;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    String API = "https://api.github.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("").build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<List<news>> call = service.listRepos("");
        call.enqueue(new Callback<List<news>>() {
            @Override
            public void onResponse(Call<List<news>> call, Response<List<news>> response) {
                List<news> newsList = response.body();
            }

            @Override
            public void onFailure(Call<List<news>> call, Throwable t) {

            }
        });

    }

}
