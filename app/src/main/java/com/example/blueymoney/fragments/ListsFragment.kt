package com.example.blueymoney.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blueymoney.R
import com.example.blueymoney.adapters.ShoppingListAdapter
data class ShoppingItem(val name: String, val price: Double)
class ListsFragment : Fragment() {

    private lateinit var editTextItem: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerViewItems: RecyclerView
    private lateinit var textViewTotal: TextView
    private lateinit var adapter: ShoppingListAdapter
    private val itemList = mutableListOf<ShoppingItem>()
    private var totalAmount: Double = 0.0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        editTextItem = view.findViewById(R.id.editTextItem)
        editTextPrice = view.findViewById(R.id.editTextPrice)
        buttonAdd = view.findViewById(R.id.buttonAdd)
        recyclerViewItems = view.findViewById(R.id.recyclerViewItems)
        textViewTotal = view.findViewById(R.id.textViewTotal)

        adapter = ShoppingListAdapter(itemList) { position ->
            removeItem(position)
        }
        recyclerViewItems.adapter = adapter
        recyclerViewItems.layoutManager = LinearLayoutManager(context)

        buttonAdd.setOnClickListener {
            addItem()
        }
    }

    private fun addItem() {
        val itemName = editTextItem.text.toString()
        val itemPrice = editTextPrice.text.toString().toDoubleOrNull()

        if (itemName.isNotEmpty() && itemPrice != null) {
            val newItem = ShoppingItem(itemName, itemPrice)
            itemList.add(newItem)
            totalAmount += itemPrice
            adapter.notifyItemInserted(itemList.size - 1)
            updateTotal()
            editTextItem.text.clear() // Limpiar el campo de texto
            editTextPrice.text.clear() // Limpiar el campo de precio
        }
    }

    private fun removeItem(position: Int) {
        val item = itemList[position]
        totalAmount -= item.price
        itemList.removeAt(position)
        adapter.notifyItemRemoved(position)
        updateTotal()
    }

    @SuppressLint("DefaultLocale")
    private fun updateTotal() {
        textViewTotal.text = String.format("Total: $%.2f", totalAmount)
    }

}