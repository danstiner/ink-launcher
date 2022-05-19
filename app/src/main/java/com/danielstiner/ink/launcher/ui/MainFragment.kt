package com.danielstiner.ink.launcher.ui

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.danielstiner.ink.launcher.data.model.AppCategory
import com.danielstiner.ink.launcher.databinding.FragmentMainBinding
import kotlin.time.ExperimentalTime

@ExperimentalTime
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

        val globalSwipeDetector = GlobalSwipeDetector(requireContext(), findNavController())

        viewModel.localDate.observe(viewLifecycleOwner) { date ->
            binding.dateText.text = DateFormat.format("EE, MMM d", date)
        }
        binding.dateText.setOnClickListener {
            viewModel.launchSelector(
                Intent.ACTION_MAIN,
                Intent.CATEGORY_APP_CALENDAR,
                requireContext()
            )
        }
        binding.dateText.setOnTouchListener(globalSwipeDetector)

        viewModel.weather.observe(viewLifecycleOwner) { weather ->
            binding.weatherText.text = weather?.toDisplayString() ?: ""
        }
        binding.weatherText.setOnClickListener {
            viewModel.launchPackage("co.climacell.climacell", requireContext())
        }
        binding.weatherText.setOnTouchListener(globalSwipeDetector)

        binding.actionButton1.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.PLAN.id))
        }
        binding.actionButton1.setOnTouchListener(globalSwipeDetector)

        binding.actionButton2.setOnClickListener {
            viewModel.launchPackage("com.google.android.keep", requireContext())
        }
        binding.actionButton2.setOnTouchListener(globalSwipeDetector)

        binding.actionButton3.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.CHAT.id))
        }
        binding.actionButton3.setOnTouchListener(globalSwipeDetector)

        binding.actionButton4.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToWorkout())
        }
        binding.actionButton4.setOnTouchListener(globalSwipeDetector)

        binding.bottomLeftButton.setOnClickListener {
            viewModel.launchPackage("com.google.android.apps.maps", requireContext())
        }
        binding.bottomLeftButton.setOnTouchListener(globalSwipeDetector)

        binding.bottomCenterButton.setOnClickListener {
            viewModel.launchPackage("com.todoist", requireContext())
        }
        binding.bottomCenterButton.setOnTouchListener(globalSwipeDetector)

        binding.bottomRightButton.setOnClickListener {
            viewModel.launchSelector(
                Intent.ACTION_MAIN,
                Intent.CATEGORY_APP_MUSIC,
                requireContext()
            )
        }
        binding.bottomRightButton.setOnTouchListener(globalSwipeDetector)

        return root
    }
}
