package com.example.blueymoney.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.blueymoney.R
import com.example.blueymoney.IconAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.Serializable
import java.util.Calendar

//Varaible General para la gráfica
val values:ArrayList<Float> = ArrayList()




class BalanceFragment : Fragment() {

    private lateinit var layoutMovimientos: LinearLayout
    private lateinit var layoutGastos: LinearLayout
    private lateinit var tvTotalIngresos: TextView
    private lateinit var tvTotalGastos: TextView
    private lateinit var tvSaldo: TextView
    private lateinit var fabAgregar: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_balance, container, false)

        layoutMovimientos = view.findViewById(R.id.layoutMovimientos)
        layoutGastos = view.findViewById(R.id.layoutGastos)
        tvTotalIngresos = view.findViewById(R.id.ingresoTotal_Tv)
        tvTotalGastos = view.findViewById(R.id.gastoTotal_tv)
        tvSaldo = view.findViewById(R.id.saldoTotal_Tv)
        fabAgregar = view.findViewById(R.id.fabAgregar)

        agregarMovimiento("Mesada", "800.00", R.drawable.ic_renta, false)
        agregarMovimiento("Renta", "2000.00", R.drawable.ic_renta, false)

        fabAgregar.setOnClickListener {
            mostrarDialogoSeleccionTipo()
        }

        return view
    }

    private fun mostrarDialogoSeleccionTipo() {
        val opciones = arrayOf("Agregar Gasto", "Agregar Ingreso")

        AlertDialog.Builder(requireContext())
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

    @SuppressLint("SetTextI18n", "InflateParams")
     fun agregarMovimiento(nombre: String, monto: String, icono: Int, gasto: Boolean) {
        val item = layoutInflater.inflate(R.layout.item_movimiento, null)
        val imgIcono = item.findViewById<ImageView>(R.id.imgIcono)
        val tvNombre = item.findViewById<TextView>(R.id.tvNombre)
        val tvMonto = item.findViewById<TextView>(R.id.tvMonto)



        imgIcono.setImageResource(icono)
        tvNombre.text = nombre
        tvMonto.text = "$$monto"

        if (gasto) {
            layoutGastos.addView(item)
        } else {
            layoutMovimientos.addView(item)
        }

        item.setOnLongClickListener{
            AlertDialog.Builder(requireContext())
                .setTitle("Eliminar Movimiento")
                .setMessage("Mi Amor\n¿Deseas eliminar este movimiento?")
                .setPositiveButton("Si") {_,_ ->
                    (item.parent as ViewGroup).removeView(item)
                    actualizarTotales()
                }
                .setNegativeButton("No",null)
                .show()
            true
        }

        actualizarTotales()
    }
    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun actualizarTotales() {
        val totalIngresos = sumarMontos(layoutMovimientos)
        val totalGastos = sumarMontos(layoutGastos)
        val saldo = totalIngresos - totalGastos

        tvTotalIngresos.text = "$" + String.format("%.2f", totalIngresos)
        tvTotalGastos.text = "$" + String.format("%.2f", totalGastos)
        tvSaldo.text = "$" + String.format("%.2f", saldo)
    }

    private fun sumarMontos(layout: LinearLayout): Double {
        var total = 0.0
        for (i in 0 until layout.childCount) {
            val item = layout.getChildAt(i)
            val tvMonto = item.findViewById<TextView>(R.id.tvMonto)
            val montoTexto = tvMonto.text.toString().replace("$", "").replace(",", "")
            values.add(montoTexto.toFloat())
            total += montoTexto.toDoubleOrNull() ?: 0.0
        }
        return total
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    private fun mostrarDialogoAgregarMovimiento(esGasto: Boolean) {
        val dialogLayout = if (esGasto) R.layout.dialog_agregar_gasto else R.layout.dialog_agregar_ingreso
        val dialogView = layoutInflater.inflate(dialogLayout, null)



        val etNombre = dialogView.findViewById<EditText>(
            if (esGasto) R.id.etNombreGasto else R.id.etNombreIngreso
        )
        val etMonto = dialogView.findViewById<EditText>(
            if (esGasto) R.id.etMontoGasto else R.id.etMontoIngreso
        )
        val btnIcono = dialogView.findViewById<Button>(R.id.btnSeleccionarIcono)
        var iconoSeleccionado = R.drawable.ic_agregar

        btnIcono.setOnClickListener {
            mostrarSelectorIcono { iconoId ->
                iconoSeleccionado = iconoId
                btnIcono.text = "Ícono seleccionado ✓"
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle(if (esGasto) "Nuevo Gasto" else "Nuevo Ingreso")
            .setView(dialogView)
            .setPositiveButton("Aceptar") { _, _ ->
                val nombre = etNombre.text.toString()
                val monto = etMonto.text.toString()

                if (nombre.isNotEmpty() && monto.isNotEmpty()) {
                    agregarMovimiento(nombre, monto, iconoSeleccionado, esGasto)
                } else {
                    Toast.makeText(requireContext(), "Llena todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun mostrarSelectorIcono(onIconoSeleccionado: (Int) -> Unit) {
        val iconosDisponibles = listOf(
            R.drawable.ic_food,
            R.drawable.ic_boletos,
            R.drawable.ic_amigos,
            R.drawable.ic_novio,
            R.drawable.ic_renta
        )

        val gridView = GridView(requireContext()).apply {
            numColumns = 3
            adapter = IconAdapter(requireContext(), iconosDisponibles)
        }

        val dialog = AlertDialog.Builder(requireContext())
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
