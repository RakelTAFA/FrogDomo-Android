package com.FrogDomo.View. Main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.FrogDomo.api.ApiClient
import com.FrogDomo.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    fun UpdateState(element: String, state: Boolean) {
        viewModelScope.launch {
            try{
                val user = UserRepository.currentUser.value
                if (user != null) {
                    when (element) {
                        "fan" -> { user.fan.active = state
                            user.fan.speed = user.fan.speed
                        }
                        "bulb" -> { user.light_bulb.active = state
                            user.light_bulb.color = user.light_bulb.color
                        }
                        "portal" -> { user.portal = kotlin.math.abs(user.portal - 90)+1
                        }

                    }

                    Log.e("user", user._id)
                }
                val response = user?.let { ApiClient.apiService.updateConfig(it) }
                if (response != null) {
                    if (response.isSuccessful){
                        Log.e("success", response.body().toString())
                    }
                }
            } catch (e: Exception) {
                Log.e("exception", e.toString())
            }
        }
    }
    fun GetUser(){
        viewModelScope.launch {
            try {
                UserRepository.currentUser.postValue(
                    UserRepository.currentUser.value?.let {
                        ApiClient.apiService.getUserById(
                            it._id
                        ).body()
                    }
                )
            } catch (e: Exception) {
                Log.e("test", e.toString())
            }
        }
    }

}