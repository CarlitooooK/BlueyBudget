package com.example.blueymoney.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.blueymoney.R
import com.example.blueymoney.fragments.viewModel.MovimientoViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate



class GraphicFragment : Fragment() {

    private lateinit var movimientoViewModel: MovimientoViewModel
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_graphic, container, false)
        val textViewTotal = view.findViewById<TextView>(R.id.sueldoTotal)
        pieChart = view.findViewById(R.id.chart)


        movimientoViewModel = ViewModelProvider(requireActivity())[MovimientoViewModel::class.java]

        movimientoViewModel.movimientos.observe(viewLifecycleOwner) { lista ->
            actualizarGrafica(lista)
        }

        movimientoViewModel.totalText.observe(viewLifecycleOwner){
                texto -> textViewTotal.text = texto
        }

        return view
    }

    private fun actualizarGrafica(lista: List<PieEntry>) {
        val pieDataSet = PieDataSet(lista, "Ingresos y Gastos")
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS, 255)
        pieDataSet.valueTextSize = 14f
        pieDataSet.valueTextColor = Color.BLACK
        pieDataSet.valueFormatter = PercentFormatter(pieChart)

        val pieData = PieData(pieDataSet)

        pieChart.setUsePercentValues(true)
        pieChart.data = pieData
        pieChart.centerText = "Balance"
        pieChart.setCenterTextSize(20f)
        pieChart.setCenterTextColor(Color.BLACK)

        val legend = pieChart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.textSize = 16f
        legend.textColor = Color.BLACK

        pieChart.description.isEnabled = false
        pieChart.animateY(1400)
        pieChart.invalidate()

    }


}