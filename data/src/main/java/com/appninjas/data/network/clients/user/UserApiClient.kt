package com.appninjas.data.network.clients.user

import com.appninjas.data.network.clients.user.dto.LoginResponseDto
import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiClient {

    @POST("users/login")
    suspend fun loginUser(@Body credentials: UserCredentials): Response<LoginResponseDto>

}