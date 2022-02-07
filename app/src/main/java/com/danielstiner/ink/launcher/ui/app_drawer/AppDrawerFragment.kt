package com.danielstiner.ink.launcher.ui.app_drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.databinding.FragmentAppDrawerBinding
import com.danielstiner.ink.launcher.ui.AppAdapter
import com.danielstiner.ink.launcher.ui.SharedViewModel
import com.danielstiner.ink.launcher.ui.SharedViewModelFactory
import com.danielstiner.ink.launcher.ui.category.CategoryFragmentDirections
import kotlin.time.ExperimentalTime

@ExperimentalTime
class AppDrawerFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext())
    }

    private var _binding: FragmentAppDrawerBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppDrawerBinding.inflate(inflater, container, false)
        val root = binding.root
        val list: RecyclerView = binding.list

        val adapter = AppAdapter { appItem ->
            findNavController().navigate(CategoryFragmentDirections.actionToMain())
            viewModel.launchApp(appItem, requireContext())
        }
        list.adapter = adapter

        viewModel.apps.observe(viewLifecycleOwner, { apps ->
            adapter.submitList(apps)
        })

        return root
    }
}