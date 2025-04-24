package com.example.blueymoney.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blueymoney.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate



class GraphicFragment : Fragment() {


  private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {

        val view = inflater.inflate(R.layout.fragment_graphic, container, false)
        pieChart = view.findViewById(R.id.chart)

        val list:ArrayList<PieEntry> =ArrayList()

        list.add(PieEntry(100f,"Salida"))
        list.add(PieEntry(1250f,"Renta"))
        list.add(PieEntry(245f,"Dairy Queen"))
        list.add(PieEntry(368f,"Cine"))

        val pieDataSet = PieDataSet(list,"Ingresos Y Gastos")

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)


        pieDataSet.valueTextSize = 15f

        pieDataSet.valueTextColor= Color.BLACK

        val pieData = PieData(pieDataSet)


        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.centerText = "Ingresos Y Gastos"
        pieChart.animateY(2000)
        pieChart.invalidate()







        return view
    }


}