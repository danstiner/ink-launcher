package com.danielstiner.ink.launcher.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.model.AppItem
import com.danielstiner.ink.launcher.databinding.AppItemBinding

class AppAdapter : ListAdapter<AppItem, AppAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AppItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(binding: AppItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val contentView: TextView = binding.content
        private var currentItem: AppItem? = null

        init {
            binding.root.setOnClickListener { view ->
                val context = view.context
                context.startActivity(
                    context.packageManager
                        .getLaunchIntentForPackage(currentItem!!.packageName.toString())
                )
            }
        }

        fun bind(item: AppItem) {
            currentItem = item
            contentView.text = item.label
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<AppItem>() {
        override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem) = oldItem === newItem
        override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem) =
            oldItem.packageName == newItem.packageName
    }
}
