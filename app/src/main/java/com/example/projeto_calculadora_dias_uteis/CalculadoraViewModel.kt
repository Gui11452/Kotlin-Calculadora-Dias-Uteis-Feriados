package com.example.projeto_calculadora_dias_uteis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class CalculadoraViewModel : ViewModel() {
    private val _diasUteis = MutableLiveData<Int>()
    val diasUteis: LiveData<Int> = _diasUteis
    private val _diasCorridos = MutableLiveData<Int>()
    val diasCorridos: LiveData<Int> = _diasCorridos
    private val _sabados = MutableLiveData<Int>()
    val sabados: LiveData<Int> = _sabados
    private val _domingos = MutableLiveData<Int>()
    val domingos: LiveData<Int> = _domingos
    private val _feriadosNacionais = MutableLiveData<Int>()
    val feriadosNacionais: LiveData<Int> = _feriadosNacionais
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    fun calcularResultados(dataInicioStr: String, dataFimStr: String) {
       val dataInicio = dateFormat.parse(dataInicioStr)
       val dataFim = dateFormat.parse(dataFimStr)
       if (dataInicio == null || dataFim == null || dataInicio.after(dataFim)) {
           return
       }
       val diferencaDias = ((dataFim.time - dataInicio.time) / (1000 * 60 * 60 * 24)).toInt() + 1
       var domingosQTD = 0
       var sabadosQTD = 0
       var diasUteisQTD = 0
       var feriadosQTD = 0
       val calendar = Calendar.getInstance()
       calendar.time = dataInicio
       while (!calendar.time.after(dataFim)) {
           val anoAtual = calendar.get(Calendar.YEAR)
           val pascoa = calcularPascoa(anoAtual)
           val carnaval = calcularCarnaval(pascoa)
           val corpusChristi = calcularCorpusChristi(pascoa)
           val feriadosMoveis = setOf(
               Pair(carnaval.get(Calendar.MONTH) + 1, carnaval.get(Calendar.DAY_OF_MONTH)),
               Pair(pascoa.get(Calendar.MONTH) + 1, pascoa.get(Calendar.DAY_OF_MONTH)),
               Pair(corpusChristi.get(Calendar.MONTH) + 1, corpusChristi.get(Calendar.DAY_OF_MONTH))
           )
           val feriadosFixos = setOf(
               Pair(1, 1),
               Pair(4, 21),
               Pair(5, 1),
               Pair(9, 7),
               Pair(10, 12),
               Pair(11, 2),
               Pair(11, 15),
               Pair(12, 25)
           )
           val diaSemana = calendar.get(Calendar.DAY_OF_WEEK)
           val diaTemporario = calendar.get(Calendar.DAY_OF_MONTH)
           val mesTemporario = calendar.get(Calendar.MONTH) + 1
           val dataAtual = Pair(mesTemporario, diaTemporario)
           if (feriadosFixos.contains(dataAtual) || feriadosMoveis.contains(dataAtual)) {
               feriadosQTD++
           } else if (diaSemana == Calendar.SATURDAY) {
               sabadosQTD++
           } else if (diaSemana == Calendar.SUNDAY) {
               domingosQTD++
           } else {
               diasUteisQTD++
           }
           calendar.add(Calendar.DAY_OF_MONTH, 1)
       }
       _diasUteis.value = diasUteisQTD
       _diasCorridos.value = diferencaDias
       _sabados.value = sabadosQTD
       _domingos.value = domingosQTD
       _feriadosNacionais.value = feriadosQTD
   }
    
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
