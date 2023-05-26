package com.appninjas.domain.repository

import com.appninjas.domain.enums.UserStatus
import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials

interface UserRepository {
    //suspend fun getUserStatus(): UserStatus
    suspend fun loginUser(credentials: UserCredentials): User?
}