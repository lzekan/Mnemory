package com.example.mnemory;

import com.example.mnemory.User.User;
import com.example.mnemory.User.UserDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<ResponseBody> getSentenceFromDictionary(@Query("userWords")List<String> userWords, @Query("wordTypes") List<String> wordTypes,
                                                 @Query("userPreferences") List<String> userPreferences);

    @GET("preference/allPreferences")
    Call<ResponseBody> getAllPreferences();

    @POST("preference/addPreferences")
    Call<ResponseBody> addPreferencesToUser(@Query("idUser") int idUser, @Query("preferences") List<String> preferences);

    @GET("preference/getPreferencesById")
    Call<ResponseBody> getPreferencesById(@Query("idUser") int idUser);

    @POST("user/update")
    Call<ResponseBody> updateUser(@Body User user);


}
