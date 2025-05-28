package com.example.blueymoney.fragments.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.PieEntry

class MovimientoViewModel : ViewModel() {

    private val _movimientos = MutableLiveData<MutableList<PieEntry>>(mutableListOf())
    private val _totalText = MutableLiveData<String>()
    val totalText: LiveData<String> = _totalText
    val movimientos: LiveData<MutableList<PieEntry>> = _movimientos


    fun actualizarBalance(balance: String){
        _totalText.value = balance

    }

    fun agregarMovimiento(entry: PieEntry) {
        _movimientos.value?.add(entry)
        _movimientos.value = _movimientos.value // Trigger para observers
    }

    fun eliminarMovimiento(entry: PieEntry) {
        val listaActual = _movimientos.value?.toMutableList() ?: mutableListOf()
        val nuevaLista = listaActual.filterNot {
            it.label == entry.label && it.value == entry.value
        }
        _movimientos.value = nuevaLista as MutableList<PieEntry>?
    }

}

