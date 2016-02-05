package com.example.admin.retrotrain;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Created by Admin on 31/01/2016.
 */
public interface UserApi {
    @GET("/users")
    Call<Users> getUsers();
}
