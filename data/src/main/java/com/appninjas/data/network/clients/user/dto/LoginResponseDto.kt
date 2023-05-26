package com.appninjas.data.network.clients.user.dto

import com.appninjas.domain.model.User

data class LoginResponseDto(
    val message: String,
    val user_data: User
)
