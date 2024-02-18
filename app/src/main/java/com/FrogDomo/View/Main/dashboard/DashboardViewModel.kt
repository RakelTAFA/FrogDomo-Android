package com.FrogDomo.View.Main.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.FrogDomo.api.ApiClient
import com.FrogDomo.repository.UserRepository
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    fun setColor(bulblight: String) {
        viewModelScope.launch {
            try{
                val user = UserRepository.currentUser.value
                if (user != null) {
                    user.light_bulb.active = true
                    user.light_bulb.color = bulblight
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

    fun setFanspeed(speed: Int) {
        viewModelScope.launch {
            try{
                val user = UserRepository.currentUser.value
                if (user != null) {
                    user.fan.active = true
                    user.fan.speed = speed
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
}