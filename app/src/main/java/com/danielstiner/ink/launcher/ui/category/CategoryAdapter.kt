package com.danielstiner.ink.launcher.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielstiner.ink.launcher.data.model.AppCategory
import com.danielstiner.ink.launcher.databinding.CategoryItemBinding

class CategoryAdapter(private val onClick: (AppCategory) -> Unit) :
    ListAdapter<AppCategory, CategoryAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick,
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(binding: CategoryItemBinding, val onClick: (AppCategory) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private val contentView: TextView = binding.content
        private var currentItem: AppCategory? = null

        init {
            binding.root.setOnClickListener { onClick(currentItem!!) }
        }

        fun bind(item: AppCategory) {
            currentItem = item
            contentView.text = item.label
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<AppCategory>() {
        override fun areItemsTheSame(oldItem: AppCategory, newItem: AppCategory) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: AppCategory, newItem: AppCategory) =
            oldItem.id == newItem.id
    }
}
