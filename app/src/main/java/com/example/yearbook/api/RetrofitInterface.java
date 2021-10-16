package com.example.yearbook.api;

import com.example.yearbook.api.model.Notification;
import com.example.yearbook.api.model.Post;
import com.example.yearbook.api.model.Student_Profile;
import com.example.yearbook.api.response.HomeAPI;
import com.example.yearbook.api.response.LoginResponse;
import com.example.yearbook.api.response.RecommendationAPI;
import com.example.yearbook.api.response.ScrollResponse;
import com.example.yearbook.api.response.SearchBar;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface  {
    static final String auth_header = "authorization: yearbook2021_app_sarc";

    @GET("../andro.json")
    Call<JsonObject> getLatestVersion();

    @Headers(auth_header)
    @GET("/api/home/")
    Call<HomeAPI> getCurrentProfile();

    @Headers(auth_header)
    @GET("/api/recommendations/")
    Call<RecommendationAPI> getRecommendations();

    @Headers(auth_header)
    @GET("/api/search")
    Call<SearchBar> getSearchResults(@Query("search_string") String search_string);

    @Headers(auth_header)
    @GET("/api/scroll")
    Call<ScrollResponse> getScrollFeed(@Query("start") int start, @Query("end") int end);

    @Headers(auth_header)
    @GET("/api/profile/{roll_number}")
    Call<Student_Profile> getStudentProfile(@Path("sso_id") String sso_id);

    @GET("/")
    Call<LoginResponse> login(@Query("code") String authCode, @Query("redirect_uri") String redirectUri, @Query("fcm_id") String fcmID);

    @GET("/")
    Call<LoginResponse> login(@Query("code") String authCode, @Query("redirect_uri") String redirectUri);

    @GET("/api/scroll/")
    Call<List<Post>> getNextPost(@Query("start") int from, @Query("end") int num);

//    @GET("/api/post/next")
//    Call<List<Post>> getNextPost(@Header("Cookie") String sessionID, @Query("from") int from, @Query("num") int num, @Query("query") String query);

    @GET
    Call<Void> logout(String sessionIDHeader);

    @GET("notifications")
    Call<List<Notification>> getNotifications(@Header("Cookie") String sessionID);

    @GET("notifications/read/{notificationID}")
    Call<Void> markNotificationRead(@Header("Cookie") String sessionID, @Path("notificationID") String notificationID);

    @GET("notifications/read/{notificationID}?delete=1")
    Call<Void> markNotificationDeleted(@Header("Cookie") String sessionID, @Path("notificationID") String notificationID);

}
