package com.example.blueymoney

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog


class GastosActivity : AppCompatActivity() {

    private lateinit var layoutMovimientos: LinearLayout
    private lateinit var layoutGastos:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finances)

        window.statusBarColor = getColor(R.color.accent_color)

        layoutMovimientos = findViewById(R.id.layoutMovimientos)
        layoutGastos = findViewById(R.id.layoutGastos)

        agregarMovimiento("Renta", "$10,000.00", R.drawable.ic_renta,false)

        val fab = findViewById<FloatingActionButton>(R.id.fabAgregar)
        fab.setOnClickListener {
            mostrarDialogoSeleccionTipo()
        }


    }

    private fun mostrarDialogoSeleccionTipo() {
        val opciones = arrayOf("Agregar Gasto", "Agregar Ingreso")

        AlertDialog.Builder(this)
            .setTitle("¿Qué deseas agregar?")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> mostrarDialogoAgregarMovimiento(esGasto = true)
                    1 -> mostrarDialogoAgregarMovimiento(esGasto = false)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }




    private fun agregarMovimiento(nombre: String, monto: String, icono: Int,gasto:Boolean) {

        if (gasto){
            val item2 = layoutInflater.inflate(R.layout.item_movimiento,layoutGastos)
            val imgIcono2 = item2.findViewById<ImageView>(R.id.imgIcono)
            val tvNombre2 = item2.findViewById<TextView>(R.id.tvNombre)
            val tvMonto2 = item2.findViewById<TextView>(R.id.tvMonto)

            imgIcono2.setImageResource(icono)
            tvNombre2.text = nombre
            tvMonto2.text = monto



        }else{
            val item = layoutInflater.inflate(R.layout.item_movimiento, layoutMovimientos, false)


            val imgIcono = item.findViewById<ImageView>(R.id.imgIcono)
            val tvNombre = item.findViewById<TextView>(R.id.tvNombre)
            val tvMonto = item.findViewById<TextView>(R.id.tvMonto)

            imgIcono.setImageResource(icono)
            tvNombre.text = nombre
            tvMonto.text = monto

            layoutMovimientos.addView(item)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun mostrarDialogoAgregarMovimiento(esGasto: Boolean) {

        if(esGasto){
            val dialogView = layoutInflater.inflate(R.layout.dialog_agregar_gasto, null)
            val etNombre = dialogView.findViewById<EditText>(R.id.etNombreGasto)
            val etMonto = dialogView.findViewById<EditText>(R.id.etMontoGasto)
            val btnIcono = dialogView.findViewById<Button>(R.id.btnSeleccionarIcono)
            var iconoSeleccionado = R.drawable.ic_agregar // Uno por defecto

            btnIcono.setOnClickListener {
                mostrarSelectorIcono { iconoId ->
                    iconoSeleccionado = iconoId
                    btnIcono.text = "Ícono seleccionado ✓"
                }
            }

            AlertDialog.Builder(this)
                .setTitle("Nuevo Gasto")
                .setView(dialogView)
                .setPositiveButton("Aceptar") { _, _ ->
                    val nombre = etNombre.text.toString()
                    val monto = etMonto.text.toString()

                    if (nombre.isNotEmpty() && monto.isNotEmpty()) {
                        agregarMovimiento(nombre, "$$monto", iconoSeleccionado,true)
                    } else {
                        Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }else{
            val dialogView = layoutInflater.inflate(R.layout.dialog_agregar_ingreso, null)
            val etNombre = dialogView.findViewById<EditText>(R.id.etNombreIngreso)
            val etMonto = dialogView.findViewById<EditText>(R.id.etMontoIngreso)
            val btnIcono = dialogView.findViewById<Button>(R.id.btnSeleccionarIcono)
            var iconoSeleccionado = R.drawable.ic_agregar // Uno por defecto

            btnIcono.setOnClickListener {
                mostrarSelectorIcono { iconoId ->
                    iconoSeleccionado = iconoId
                    btnIcono.text = "Ícono seleccionado ✓"
                }
            }

            AlertDialog.Builder(this)
                .setTitle("Nuevo Gasto")
                .setView(dialogView)
                .setPositiveButton("Aceptar") { _, _ ->
                    val nombre = etNombre.text.toString()
                    val monto = etMonto.text.toString()

                    if (nombre.isNotEmpty() && monto.isNotEmpty()) {
                        agregarMovimiento(nombre, "$$monto", iconoSeleccionado,false)
                    } else {
                        Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()

        }






    }

    private fun mostrarSelectorIcono(onIconoSeleccionado: (Int) -> Unit) {

        val iconosDisponibles = listOf(
            R.drawable.ic_food,
            R.drawable.ic_boletos,
            R.drawable.ic_amigos,
            R.drawable.ic_novio,
            R.drawable.ic_renta
        )


        val gridView = GridView(this).apply {
            numColumns = 3
            adapter = IconAdapter(this@GastosActivity, iconosDisponibles)
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("Selecciona un ícono")
            .setView(gridView)
            .setNegativeButton("Cancelar", null)
            .create()

        gridView.setOnItemClickListener { _, _, position, _ ->
            onIconoSeleccionado(iconosDisponibles[position])
            dialog.dismiss()
        }

        dialog.show()
    }




}
