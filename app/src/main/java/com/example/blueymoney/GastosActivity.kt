package com.example.blueymoney

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.blueymoney.adapters.FragmentPageAdapter
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class GastosActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: FragmentPageAdapter
    private lateinit var btnSubirData: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_finances)

        btnSubirData = findViewById<ImageButton>(R.id.btnUploadData)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)

        viewPager2.adapter = adapter


        tabLayout.addOnTabSelectedListener(object: OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })


        btnSubirData.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmación")
            builder.setMessage("¿Segura de subir los datos a la base?")

            builder.setPositiveButton("Sí") { dialog, _ ->
                // Aquí haces la acción de subir datos
                Toast.makeText(this, "Subiendo datos a la base...", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancelar") { dialog, _ ->
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }


    }


}


