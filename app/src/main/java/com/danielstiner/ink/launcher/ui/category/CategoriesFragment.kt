package com.danielstiner.ink.launcher.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.data.model.AppCategory
import com.danielstiner.ink.launcher.databinding.FragmentCategoryBinding
import com.danielstiner.ink.launcher.ui.SharedViewModel
import com.danielstiner.ink.launcher.ui.SharedViewModelFactory

class CategoriesFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels {
        SharedViewModelFactory(requireContext())
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

        val adapter = CategoryAdapter { category ->
            findNavController().navigate(CategoriesFragmentDirections.actionToCategory(category.id))
        }
        adapter.submitList(AppCategory.ALL)
        list.adapter = adapter

        return root
    }
}