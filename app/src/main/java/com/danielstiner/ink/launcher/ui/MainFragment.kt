package com.danielstiner.ink.launcher.ui

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat.getMediumDateFormat
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

        val packageManager = requireContext().packageManager
        val dateFormat = getMediumDateFormat(requireContext())
        val globalSwipeDetector = GlobalSwipeDetector(requireContext(), findNavController())


        viewModel.localDate.observe(this) { date ->
            binding.dateText.text = dateFormat.format(date)
        }
        binding.dateText.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_CALENDAR
                )
            )
        }
        binding.dateText.setOnTouchListener(globalSwipeDetector)

        viewModel.weather.observe(this) { weather ->
            binding.weatherText.text = weather?.toDisplayString() ?: ""
        }
        binding.weatherText.setOnClickListener {
            startActivity(packageManager.getLaunchIntentForPackage("co.climacell.climacell"))
        }
        binding.weatherText.setOnTouchListener(globalSwipeDetector)

        binding.actionButton1.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.GO.id))
        }
        binding.actionButton1.setOnTouchListener(globalSwipeDetector)

        binding.actionButton2.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToWorkout())
        }
        binding.actionButton2.setOnTouchListener(globalSwipeDetector)

        binding.actionButton3.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.LISTEN.id))
        }
        binding.actionButton3.setOnTouchListener(globalSwipeDetector)

        binding.actionButton4.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.CAPTURE.id))
        }
        binding.actionButton4.setOnTouchListener(globalSwipeDetector)

        binding.bottomLeftButton.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionToCategory(AppCategory.MESSAGE.id))
        }
        binding.bottomLeftButton.setOnTouchListener(globalSwipeDetector)

        binding.bottomCenterButton.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_MAIN,
                    Intent.CATEGORY_APP_MUSIC
                )
            )
        }
        binding.bottomCenterButton.setOnTouchListener(globalSwipeDetector)

        binding.bottomRightButton.setOnClickListener {
            startActivity(
                Intent.makeMainSelectorActivity(
                    Intent.ACTION_DIAL,
                    Intent.CATEGORY_DEFAULT
                )
            )
        }
        binding.bottomRightButton.setOnTouchListener(globalSwipeDetector)

        return root
    }
}
