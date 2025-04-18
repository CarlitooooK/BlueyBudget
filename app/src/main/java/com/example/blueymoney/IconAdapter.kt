package com.example.blueymoney

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class IconAdapter (private val context: Context, private val icons: List<Int>) : BaseAdapter() {
    override fun getCount() = icons.size
    override fun getItem(position: Int) = icons[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_icono, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imgIcono)
        imageView.setImageResource(icons[position])

        return view
    }
}
