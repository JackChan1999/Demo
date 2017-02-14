package com.google.demo.http;

import com.google.demo.model.news;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/8 19:43
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public interface GitHubService {

    @GET("users/{user}/repos")
    Call<List<news>> listRepos(@Path("user") String user);
}
