package com.example.projeto_calculadora_dias_uteis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class CalculadoraViewModel : ViewModel() {

    
    private fun calcularPascoa(ano: Int): Calendar {
        val a = ano % 19
        val b = ano / 100
        val c = ano % 100
        val d = b / 4
        val e = b % 4
        val f = (b + 8) / 25
        val g = (b - f + 1) / 3
        val h = (19 * a + b - d - g + 15) % 30
        val i = c / 4
        val k = c % 4
        val l = (32 + 2 * e + 2 * i - h - k) % 7
        val m = (a + 11 * h + 22 * l) / 451
        val mes = (h + l - 7 * m + 114) / 31
        val dia = ((h + l - 7 * m + 114) % 31) + 1
        val pascoa = Calendar.getInstance()
        pascoa.set(ano, mes - 1, dia)
        return pascoa
    }

    private fun calcularCarnaval(pascoa: Calendar): Calendar {
        val carnaval = pascoa.clone() as Calendar
        carnaval.add(Calendar.DAY_OF_MONTH, -47)
        return carnaval
    }

    private fun calcularCorpusChristi(pascoa: Calendar): Calendar {
        val corpusChristi = pascoa.clone() as Calendar
        corpusChristi.add(Calendar.DAY_OF_MONTH, 60)
        return corpusChristi
    }
}
