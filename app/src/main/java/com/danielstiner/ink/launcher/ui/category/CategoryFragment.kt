package com.danielstiner.ink.launcher.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.ui.MainViewModel
import com.danielstiner.ink.launcher.ui.MainViewModelFactory
import com.danielstiner.ink.launcher.databinding.FragmentAppDrawerBinding
import com.danielstiner.ink.launcher.databinding.FragmentCategoryBinding
import com.danielstiner.ink.launcher.ui.AppAdapter

class CategoryFragment : Fragment() {

    val args: CategoryFragmentArgs by navArgs()

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(requireContext())
    }

    private var _binding: FragmentCategoryBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        val root = binding.root
        val list: RecyclerView = binding.list

        val adapter = AppAdapter()
        list.adapter = adapter

        mainViewModel.apps.observe(viewLifecycleOwner, { apps ->
            adapter.submitList(apps.filter { app -> app.category.value == args.category })
        })

        return root
    }
}