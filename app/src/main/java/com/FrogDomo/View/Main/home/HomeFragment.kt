package com.FrogDomo.View.Main.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.FrogDomo.R
import com.FrogDomo.View.login.LoginViewModel
import com.FrogDomo.api.ApiClient
import com.FrogDomo.databinding.FragmentHomeBinding
import com.FrogDomo.repository.UserRepository
import kotlinx.coroutines.launch
import kotlin.math.log



class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val handler = Handler()

    lateinit var alarmCheck : ImageView
    lateinit var alarmLayout: ConstraintLayout

    private val updateRunnable = object : Runnable {
        override fun run() {
            homeViewModel.GetUser()
            updateIcons(alarmCheck, alarmLayout)
            handler.postDelayed(this, 3000)
        }
    }

    private fun startPeriodicUpdates() {
        handler.postDelayed(updateRunnable, 0)
    }

    private fun stopPeriodicUpdates() {
        handler.removeCallbacks(updateRunnable)
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val bulbSwitch = binding.BulbSwitch
        val fanSwitch = binding.FanSwitch
        val portalSwitch = binding.PortalSwitch
        alarmCheck = binding.StateImage
        alarmLayout = binding.AlarmLayout
        val btnUser = binding.button2

        btnUser.setOnClickListener {
            homeViewModel.GetUser()
            updateIcons(alarmCheck, alarmLayout)
        }

        bulbSwitch.setOnCheckedChangeListener { _, isChecked ->
            sendSwitchStateToAPI("bulb", isChecked)
        }

        fanSwitch.setOnCheckedChangeListener { _, isChecked ->
            sendSwitchStateToAPI("fan", isChecked)
        }

        portalSwitch.setOnCheckedChangeListener { _, isChecked ->
            sendSwitchStateToAPI("portal", isChecked)
        }

        startPeriodicUpdates()



        return root
    }

    @SuppressLint("ResourceAsColor")
    fun updateIcons(alarmCheck: ImageView, alarmLayout: ConstraintLayout){
        if (UserRepository.currentUser.value?.alarm?.active == true){
            alarmCheck.setImageResource(R.drawable.done_fill0_wght400_grad0_opsz24)
            if (UserRepository.currentUser.value?.alarm?.rung == true){
                alarmLayout.setBackgroundColor(Color.parseColor("#FF0000"))
            }
            else if (UserRepository.currentUser.value?.alarm?.rung == false){
                alarmLayout.setBackgroundColor(Color.parseColor("#DDE6C6"))
            }
        }
        else if (UserRepository.currentUser.value?.alarm?.active == false){
            alarmCheck.setImageResource(R.drawable.baseline_close_24)
            if (UserRepository.currentUser.value?.alarm?.rung == false){
                alarmLayout.setBackgroundColor(Color.parseColor("#DDE6C6"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopPeriodicUpdates()
        _binding = null
    }

    private fun sendSwitchStateToAPI(element: String, isChecked: Boolean) {
        Log.e("test", isChecked.toString())
        homeViewModel.UpdateState(element,isChecked)
    }



}