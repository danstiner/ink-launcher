package com.danielstiner.ink.launcher.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.danielstiner.ink.launcher.data.model.AppCategory
import com.danielstiner.ink.launcher.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext())
    }

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val packageManager = requireContext().packageManager

        // TODO refresh when date changes
        binding.dateText.text =
            android.text.format.DateFormat.getMediumDateFormat(requireContext()).format(Date())
        binding.dateText.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_CALENDAR
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }

        viewModel.weather.observe(this) {
            binding.weatherText.text = it
        }
        binding.weatherText.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("co.climacell.climacell"))
        }

        binding.actionButton1.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.GO.id))
        }

        binding.actionButton2.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToWorkout())
        }

        binding.actionButton3.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.LISTEN.id))
        }

        binding.actionButton4.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.CAPTURE.id))
        }

        binding.bottomLeftButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.MESSAGE.id))
        }

        binding.bottomCenterButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategories())
        }

        binding.bottomRightButton.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_DIAL,
                    Intent.CATEGORY_DEFAULT
                ).apply {
                    addFlags(FLAG_ACTIVITY_NEW_TASK)
                })
        }

        return root
    }
}