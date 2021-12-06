package io.github.jwgibanez.contacts.view

import androidx.recyclerview.widget.DiffUtil

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import io.github.jwgibanez.contacts.data.model.User

class ListAdapter(
    private val onItemClick: (User) -> Unit,
    diffCallback: DiffUtil.ItemCallback<User>
) : ListAdapter<User, ItemViewHolder?>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.create(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current: User = getItem(position)
        holder.bind(current)
    }

    internal class Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}