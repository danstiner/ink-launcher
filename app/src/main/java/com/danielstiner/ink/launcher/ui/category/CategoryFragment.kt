package com.danielstiner.ink.launcher.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.databinding.FragmentCategoryBinding
import com.danielstiner.ink.launcher.ui.AppAdapter
import com.danielstiner.ink.launcher.ui.SharedViewModel
import com.danielstiner.ink.launcher.ui.SharedViewModelFactory
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CategoryFragment : Fragment() {

    val args: CategoryFragmentArgs by navArgs()

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

        val adapter = AppAdapter { appItem ->
            findNavController().navigate(CategoryFragmentDirections.actionToMain())
            requireContext().startActivity(
                requireContext().packageManager
                    .getLaunchIntentForPackage(appItem.packageName.toString())
            )
        }
        list.adapter = adapter

        sharedViewModel.apps.observe(viewLifecycleOwner, { apps ->
            adapter.submitList(apps.filter { app -> app.category.id == args.category })
        })

        return root
    }
}