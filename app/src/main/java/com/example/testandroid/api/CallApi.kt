package com.example.testandroid.api

import com.example.testandroid.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CallApi {
    @GET("users")
    suspend fun fetchUserList(@Query("since") since: Int, @Query("per_page") perPage: Int): List<User>
    @GET("users/{loginId}")
    suspend fun fetchUserProfile(@Path("loginId") loginId: String): User

}