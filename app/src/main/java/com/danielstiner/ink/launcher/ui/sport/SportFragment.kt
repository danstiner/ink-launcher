package com.danielstiner.ink.launcher.ui.sport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.ui.MainViewModel
import com.danielstiner.ink.launcher.ui.MainViewModelFactory
import com.danielstiner.ink.launcher.databinding.FragmentAppDrawerBinding
import com.danielstiner.ink.launcher.ui.AppAdapter

class SportFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(requireContext())
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

        val adapter = AppAdapter()
        list.adapter = adapter

        mainViewModel.apps.observe(viewLifecycleOwner, { apps ->
            adapter.submitList(apps)
        })

        return root
    }
}