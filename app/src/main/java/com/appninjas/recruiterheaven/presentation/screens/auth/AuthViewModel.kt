package com.appninjas.recruiterheaven.presentation.screens.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.User
import com.appninjas.domain.model.UserCredentials
import com.appninjas.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loggedUser: MutableLiveData<User?> = MutableLiveData()
    val loggedUser: LiveData<User?> = _loggedUser

    fun loginUser(credentials: UserCredentials) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = loginUseCase.invoke(credentials)
            _loggedUser.postValue(result)
        }
    }

}