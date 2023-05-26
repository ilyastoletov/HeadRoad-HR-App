package com.appninjas.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.appninjas.data.network.clients.user.UserApiClient
import com.appninjas.data.network.clients.user.dto.LoginResponseDto
import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials
import com.appninjas.domain.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepoImpl(private val context: Context, private val userApiClient: UserApiClient) : UserRepository {

    override suspend fun loginUser(credentials: UserCredentials): User? {
        val userLoginCall = userApiClient.loginUser(credentials)
        var userObject: User? = null

        if (userLoginCall.isSuccessful) {
            userObject = userLoginCall.body()!!.user_data
            Log.d("бялва", "${userLoginCall.body()}")
            val sharedPreferences = context.getSharedPreferences("main", Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putBoolean("user_logged_in", true)
                putString("user_id", userObject!!.authorId)
                putString("user_status", userObject!!.role.toString())
            }.apply()
        } else {
            when(userLoginCall.code()) {
                404 -> Toast.makeText(context, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                500 -> Toast.makeText(context, "Серверная ошибка ${userLoginCall.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        return userObject
    }

}