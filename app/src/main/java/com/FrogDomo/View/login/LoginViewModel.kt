package com.FrogDomo.View.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.FrogDomo.Model.User
import com.FrogDomo.api.ApiClient
import com.FrogDomo.api.LoginRequest
import com.FrogDomo.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> get() = _loginResult
    val user: LiveData<User> get() = user

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.login(LoginRequest(email, password))
                // Traitez la réponse en fonction de ce que votre API renvoie
                if (response.isSuccessful) {
                    val id = response.body().toString()
                    Log.e("id",id)
                    val user = ApiClient.apiService.getUserById(id)
                    UserRepository.currentUser.postValue(user.body())
                    _loginResult.postValue(true)
                } else {
                    _loginResult.postValue(false)
                }
            } catch (e: Exception) {
                Log.e("test", e.toString())
                _loginResult.postValue(false)
            }
        }
    }
}