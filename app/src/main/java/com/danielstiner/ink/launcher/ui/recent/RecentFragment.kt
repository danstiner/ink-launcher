package com.danielstiner.ink.launcher.ui.recent

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.danielstiner.ink.launcher.databinding.FragmentWorkoutBinding
import com.danielstiner.ink.launcher.ui.SharedViewModel
import com.danielstiner.ink.launcher.ui.SharedViewModelFactory

class RecentFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext())
    }

    private var _binding: FragmentWorkoutBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root = binding.root

        val packageManager = requireContext().packageManager

        viewModel.weather.observe(this) {
            binding.weatherText.text = it
        }
        binding.weatherText.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("co.climacell.climacell"))
        }

        binding.actionButton1.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MUSIC
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
        }

        binding.actionButton2.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("com.audible.application"))
        }

        binding.actionButton3.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("com.strava"))
        }

        return root
    }
}