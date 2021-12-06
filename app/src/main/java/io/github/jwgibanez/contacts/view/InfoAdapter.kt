package io.github.jwgibanez.contacts.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.jwgibanez.contacts.databinding.ListItemValuePairBinding
import io.github.jwgibanez.contacts.io.github.jwgibanez.contacts.utils.toast
import android.content.ClipData
import android.content.ClipboardManager
import androidx.core.content.ContextCompat.getSystemService

class InfoAdapter : RecyclerView.Adapter<InfoAdapter.ItemViewHolder>() {

    private val values = ArrayList<ValuePair>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            ListItemValuePairBinding.inflate(
                inflater,
                parent,
                false
            )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(values[position])
    }

    fun set(newValues: ArrayList<ValuePair>) {
        values.apply {
            clear()
            addAll(newValues)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = values.size

    inner class ItemViewHolder(binding: ListItemValuePairBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val name: TextView = binding.name
        private val value: TextView = binding.value

        fun bind(item: ValuePair) {
            val n = item.name ?: ""
            val v = item.value ?: ""
            name.text = n
            value.text = v
            itemView.setOnClickListener {
                val clipboard: ClipboardManager? =
                    getSystemService(it.context, ClipboardManager::class.java)
                val clip = ClipData.newPlainText(n, v)
                clipboard?.setPrimaryClip(clip)
                toast(it.context, "Copied: ${item.value}")
            }
        }
    }

    data class ValuePair(
        var name: String?,
        var value: String?
    )
}