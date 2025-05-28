package com.example.blueymoney.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blueymoney.R
import com.example.blueymoney.fragments.ShoppingItem

class ShoppingListAdapter(
    private val items: List<ShoppingItem>,
    private val onItemLongClick: (Int) -> Unit
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icono: ImageView = view.findViewById(R.id.imgIcono)
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val monto: TextView = view.findViewById(R.id.tvMonto)

        init {
            view.setOnLongClickListener {
                onItemLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ViewHolder(vista)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.icono.setImageResource(R.drawable.ic_palomita) // Usa cualquier ícono que desees
        holder.nombre.text = item.name
        holder.monto.text = String.format("$%.2f", item.price)
    }

    override fun getItemCount(): Int = items.size
}

