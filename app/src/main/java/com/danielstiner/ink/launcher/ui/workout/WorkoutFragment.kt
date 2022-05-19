package com.danielstiner.ink.launcher.ui.workout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.danielstiner.ink.launcher.databinding.FragmentWorkoutBinding
import com.danielstiner.ink.launcher.ui.SharedViewModel
import com.danielstiner.ink.launcher.ui.SharedViewModelFactory
import kotlin.time.ExperimentalTime

@ExperimentalTime
class WorkoutFragment : Fragment() {

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

        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            binding.weatherText.text = weather?.toDisplayString() ?: ""
        }
        binding.weatherText.setOnClickListener {
            viewModel.launchPackage("co.climacell.climacell", requireContext())
        }

        binding.actionButton1.setOnClickListener {
            viewModel.launchPackage("com.spotify.music", requireContext())
        }

        binding.actionButton2.setOnClickListener {
            viewModel.launchPackage("com.audible.application", requireContext())
        }

        binding.actionButton3.setOnClickListener {
            viewModel.launchPackage("com.synology.DSaudio", requireContext())
        }

        binding.actionButton4.setOnClickListener {
            viewModel.launchPackage("com.strava", requireContext())
        }

        return root
    }
}