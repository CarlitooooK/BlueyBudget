package com.example.blueymoney.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blueymoney.fragments.ShoppingItem

class ShoppingListAdapter(
    private val items: MutableList<ShoppingItem>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewName: TextView = itemView.findViewById(android.R.id.text1)
        private val textViewPrice: TextView = itemView.findViewById(android.R.id.text2)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        @SuppressLint("DefaultLocale")
        fun bind(item: ShoppingItem) {
            textViewName.text = item.name
            textViewPrice.text = String.format("$%.2f", item.price)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
