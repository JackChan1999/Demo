package com.google.demo.http;

import com.google.demo.model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * ============================================================
 * 版 权 ： Google互联网有限公司版权所有 (c) 2016
 * 作 者 : 陈冠杰
 * 版 本 ： 1.0
 * 创建日期 ：2016/7/9 10:51
 * 描 述 ：
 * 修订历史 ：
 * ============================================================
 **/
public interface IUserBiz {

    /**一般的get请求*/
    @GET("users")
    Call<List<User>> getUsers();

    /**
     * 动态的url访问@PATH,用于访问zhy的信息
     * http://192.168.1.102:8080/springmvc_users/user/zhy
     * {username}可以理解为占位符，取值为方法实参username的值
     */
    @GET("{username}")
    Call<User> getUser(@Path("username") String username);

    /**
     * 查询参数的设置@Query
     * http://baseurl/users?sortby=username
     */
    @GET("users")
    Call<List<User>> getUsersBySort(@Query("sortby") String sort);

    /**
     * POST请求体的方式向服务器传入json字符串@Body
     */

    @POST("add")
    Call<List<User>> addUser(@Body User user);

    /**
     * post请求进行提交数据（比如用户注册、登陆等表单类请求）
     */
    @FormUrlEncoded
    @POST("/some/endpoint")
    Call<Response> register(@Field("username") String username);

    @FormUrlEncoded
    @POST("/some/endpoint")
    Call<User> somePointEnd(@FieldMap Map<String,String> names);

    /**
     * 表单的方式传递键值对@FormUrlEncoded
     */
    @POST("login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);

    /**
     * 单文件上传@Multipart
     */
    @POST("register")
    @Multipart
    Call<User> registerUser(@Part MultipartBody.Part photo, @Part("username")RequestBody username,
                            @Part("password") RequestBody password);

   /* @Multipart
    @POST("/api/Accounts/editaccount")
    Call<User> editUser(@Part("file_key\";filename=\"pp.png"), @Part("username") String username);*/

    /**
     * 多文件上传@PartMap
     */
    @Multipart
    @POST("register")
    Call<User> registerUser(@PartMap Map<String, RequestBody> params, @Part("password") RequestBody password);

    /**
     * 下载文件
     */
    @GET("download")
    Call<RequestBody> download();
}
