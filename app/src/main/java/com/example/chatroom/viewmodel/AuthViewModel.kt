package com.example.chatroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatroom.data.Result
import com.example.chatroom.data.UserRepository
import com.example.chatroom.ui.theme.Injection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel() : ViewModel() {
    private val userRepository: UserRepository
    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
    }
    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: LiveData<Result<Boolean>> get() = _authResult

    fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String ) {
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(firstName, lastName, email, password, confirmPassword )
        }
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            _authResult.value = userRepository.login(email,password)
        }
    }
}