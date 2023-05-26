package com.appninjas.domain.usecase

import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials
import com.appninjas.domain.repository.UserRepository

class LoginUseCase(private val repository: UserRepository) {
    suspend fun invoke(userCredentials: UserCredentials): User? = repository.loginUser(userCredentials)
}