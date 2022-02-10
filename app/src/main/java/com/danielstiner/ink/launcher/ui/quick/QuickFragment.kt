package com.danielstiner.ink.launcher.ui.quick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.danielstiner.ink.launcher.databinding.FragmentQuickBinding
import com.danielstiner.ink.launcher.ui.SharedViewModel
import com.danielstiner.ink.launcher.ui.SharedViewModelFactory
import com.danielstiner.ink.launcher.ui.category.CategoryFragmentDirections
import kotlin.time.ExperimentalTime

@ExperimentalTime
class QuickFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext())
    }

    private var _binding: FragmentQuickBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuickBinding.inflate(inflater, container, false)
        val root = binding.root

        // Show four most used apps, ascending order
        viewModel.mostUsed.observe(this) { mostUsed ->
            mostUsed.getOrNull(0)?.let { app ->
                binding.actionButton4.text = app.label
                binding.actionButton4.setOnClickListener {
                    findNavController().navigate(CategoryFragmentDirections.actionToMain())
                    viewModel.launchApp(app, requireContext())
                }
            }

            mostUsed.getOrNull(1)?.let { app ->
                binding.actionButton3.text = app.label
                binding.actionButton3.setOnClickListener {
                    findNavController().navigate(CategoryFragmentDirections.actionToMain())
                    viewModel.launchApp(app, requireContext())
                }
            }

            mostUsed.getOrNull(2)?.let { app ->
                binding.actionButton2.text = app.label
                binding.actionButton2.setOnClickListener {
                    findNavController().navigate(CategoryFragmentDirections.actionToMain())
                    viewModel.launchApp(app, requireContext())
                }
            }

            mostUsed.getOrNull(3)?.let { app ->
                binding.actionButton1.text = app.label
                binding.actionButton1.setOnClickListener {
                    findNavController().navigate(CategoryFragmentDirections.actionToMain())
                    viewModel.launchApp(app, requireContext())
                }
            }
        }

        return root
    }
}