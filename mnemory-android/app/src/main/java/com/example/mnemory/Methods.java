package com.example.mnemory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Methods {
    @GET("userByUsername")
    Call<ResponseBody> findUserByUsername(@Query("username") String username);

    @GET("userByEmail")
    Call<ResponseBody> findUserByEmail(@Query("email") String email);

    @POST("user/add")
    Call<ResponseBody> addNewUser(@Body UserDTO userDTO);

    @GET("template")
    Call<ResponseBody> getTemplateByLength(@Query("length") int length);

    @GET("dictionary")
    Call<ResponseBody> getSentenceFromDictionary(@Query("userWords")List<String> userWords, @Query("wordTypes") List<String> wordTypes);

    @GET("preference/allPreferences")
    Call<ResponseBody> getAllPreferences();

    @POST("preference/addPreferences")
    Call<ResponseBody> addPreferencesToUser(@Query("idUser") int idUser, @Query("preferences") List<String> preferences);


}
