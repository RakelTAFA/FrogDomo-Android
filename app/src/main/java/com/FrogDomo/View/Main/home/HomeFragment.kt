package com.FrogDomo.View.Main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.FrogDomo.R
import com.FrogDomo.View.login.LoginViewModel
import com.FrogDomo.databinding.FragmentHomeBinding
import com.FrogDomo.repository.UserRepository
import kotlin.math.log

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel by viewModels<HomeViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        val alarmCheck = binding.StateImage
        val alarmLayout = binding.AlarmLayout

        bulbSwitch.setOnCheckedChangeListener { _, isChecked ->
            sendSwitchStateToAPI("bulb", isChecked)
        }

        fanSwitch.setOnCheckedChangeListener { _, isChecked ->
            sendSwitchStateToAPI("fan", isChecked)
        }

        portalSwitch.setOnCheckedChangeListener { _, isChecked ->
            sendSwitchStateToAPI("portal", isChecked)
        }

        alarmLayout.setOnClickListener {
            if (UserRepository.currentUser.value?.alarm?.active == false){
                alarmCheck.setImageResource(R.drawable.baseline_close_24)
            }
            else if (UserRepository.currentUser.value?.alarm?.active == true) {
                alarmCheck.setImageResource(R.drawable.done_fill0_wght400_grad0_opsz24)
            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendSwitchStateToAPI(element: String, isChecked: Boolean) {
        Log.e("test", isChecked.toString())
        homeViewModel.UpdateState(element,isChecked)
    }

}