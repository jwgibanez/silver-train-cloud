package io.github.jwgibanez.contacts.view

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import io.github.jwgibanez.contacts.data.model.User
import io.github.jwgibanez.contacts.databinding.ListItemBinding

class ItemViewHolder private constructor(
    private val binding: ListItemBinding,
    private val onItemClick: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.name.text = user.name
        binding.progressBar.visibility = View.GONE
        itemView.setOnClickListener { onItemClick(user) }
    }

    companion object {
        fun create(parent: ViewGroup, onItemClick: (User) -> Unit): ItemViewHolder {
            return ItemViewHolder(
                ListItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ),
                onItemClick
            )
        }
    }
}