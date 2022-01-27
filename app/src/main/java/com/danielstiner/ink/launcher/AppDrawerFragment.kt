package com.danielstiner.ink.launcher

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danielstiner.ink.launcher.placeholder.PlaceholderContent

import android.content.Intent
import com.danielstiner.ink.launcher.databinding.FragmentAppDrawerBinding
import com.danielstiner.ink.launcher.databinding.FragmentAppDrawerListBinding


/**
 * A fragment representing a list of Items.
 */
class AppDrawerFragment : Fragment() {

    private var _binding: FragmentAppDrawerListBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppDrawerListBinding.inflate(inflater, container, false)
        val root: RecyclerView = binding.root

        val pManager = requireContext().packageManager

        val appsList = ArrayList<PlaceholderContent.PlaceholderItem>()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps = pManager.queryIntentActivities(i, 0)

        for (ri in allApps) {
            appsList.add(
                PlaceholderContent.PlaceholderItem(
                    label = ri.loadLabel(pManager),
                    packageName = ri.activityInfo.packageName,
                    icon = ri.activityInfo.loadIcon(pManager),
                )
            )
        }

        appsList.sortBy { app -> app.label.toString() }

        with(root) {
            layoutManager = LinearLayoutManager(context)
            adapter = AppRecyclerViewAdapter(appsList)
        }
        return root
    }
}