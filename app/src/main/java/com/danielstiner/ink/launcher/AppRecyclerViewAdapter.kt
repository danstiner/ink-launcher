package com.danielstiner.ink.launcher

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.danielstiner.ink.launcher.placeholder.PlaceholderContent.PlaceholderItem
import com.danielstiner.ink.launcher.databinding.FragmentAppDrawerBinding
import android.widget.Toast

import android.content.Intent




/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class AppRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<AppRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentAppDrawerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.label
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentAppDrawerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        init {
            binding.root.setOnClickListener { view ->
                val pos = bindingAdapterPosition
                val context = view.context

                val launchIntent: Intent? = context.packageManager
                    .getLaunchIntentForPackage(values[pos].packageName.toString())
                context.startActivity(launchIntent)
            }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}