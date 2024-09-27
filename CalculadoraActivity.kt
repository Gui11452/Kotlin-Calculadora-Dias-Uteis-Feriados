package com.example.projeto_calculadora_dias_uteis

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import java.util.*

class CalculadoraActivity : AppCompatActivity() {

    private val viewModel: CalculadoraViewModel by viewModels()

    private lateinit var diasUteisTextView: TextView
    private lateinit var diasCorridosTextView: TextView
    private lateinit var sabadosTextView: TextView
    private lateinit var domingosTextView: TextView
    private lateinit var feriadosNacionaisTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(0, 0, 0, 0)
            gravity = Gravity.TOP
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val headerLayout = createHeaderLayout()
        mainLayout.addView(headerLayout)

        val containerLayout = createContainerLayout()
        mainLayout.addView(containerLayout)

        val resultadosLayout = createResultadosLayout()
        mainLayout.addView(resultadosLayout)

        val backButton = Button(this).apply {
            text = "Voltar para Página Inicial"
            setBackgroundColor(Color.parseColor("#137B7B"))
            setTextColor(Color.WHITE)
            setPadding(32, 16, 32, 16)
            setOnClickListener {
                val intent = Intent(this@CalculadoraActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        mainLayout.addView(backButton)

        val footerLayout = createFooterLayout()
        mainLayout.addView(footerLayout)

        setContentView(mainLayout)

        viewModel.diasUteis.observe(this, Observer { diasUteis ->
            diasUteisTextView.text = diasUteis?.toString() ?: "0"
        })

        viewModel.diasCorridos.observe(this, Observer { diasCorridos ->
            diasCorridosTextView.text = diasCorridos?.toString() ?: "0"
        })

        viewModel.sabados.observe(this, Observer { sabados ->
            sabadosTextView.text = sabados?.toString() ?: "0"
        })

        viewModel.domingos.observe(this, Observer { domingos ->
            domingosTextView.text = domingos?.toString() ?: "0"
        })

        viewModel.feriadosNacionais.observe(this, Observer { feriados ->
            feriadosNacionaisTextView.text = feriados?.toString() ?: "0"
        })
    }

    private fun createHeaderLayout(): LinearLayout {
        val headerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(32, 16, 32, 16)
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#212121"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150
            )
        }

        val imageView = ImageView(this).apply {
            setImageResource(R.drawable.calculator)
            layoutParams = LinearLayout.LayoutParams(
                100,
                100
            ).apply {
                marginEnd = 16
            }
        }

        val titleTextView = TextView(this).apply {
            text = "SmartDate. It’s holiday?"
            textSize = 24f
            setTextColor(Color.WHITE)
            setPadding(8, 0, 0, 0)
        }

        headerLayout.addView(imageView)
        headerLayout.addView(titleTextView)

        return headerLayout
    }

    private fun createContainerLayout(): LinearLayout {
        val containerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val containerTitleTextView = TextView(this).apply {
            text = "Descubra o poder do nosso cálculo"
            textSize = 24f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
        }

        val containerDescriptionTextView = TextView(this).apply {
            text = """
                Descubra online e gratuitamente quantos feriados nacionais, fins de semana, 
                dias corridos há entre duas datas utilizando a nossa Calculadora de Dias Úteis.
                Preencha os dois campos abaixo para obter o resultado do cálculo de dias úteis.
            """.trimIndent()
            textSize = 16f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 16)
        }

        val formLayout = createFormLayout()
        containerLayout.addView(containerTitleTextView)
        containerLayout.addView(containerDescriptionTextView)
        containerLayout.addView(formLayout)

        return containerLayout
    }

    private fun createFormLayout(): LinearLayout {
        val formLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 16)
            }
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = 16f
                setStroke(5, Color.parseColor("#137B7B"))
            }
        }

        val startDateLabel = TextView(this).apply {
            text = "Data de início"
            textSize = 16f
            setTextColor(Color.BLACK)
        }

        val startDateInput = EditText(this).apply {
            hint = "Selecione a data de início"
            isFocusable = false
            setPadding(16, 16, 16, 16)
            setBackgroundColor(Color.parseColor("#F0F0F0"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                showDatePickerDialog { date -> setText(date) }
            }
        }

        val endDateLabel = TextView(this).apply {
            text = "Data final"
            textSize = 16f
            setTextColor(Color.BLACK)
            setPadding(0, 16, 0, 0)
        }

        val endDateInput = EditText(this).apply {
            hint = "Selecione a data final"
            isFocusable = false
            setPadding(16, 16, 16, 16)
            setBackgroundColor(Color.parseColor("#F0F0F0"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                showDatePickerDialog { date -> setText(date) }
            }
        }

        val calculateButton = Button(this).apply {
            text = "Calcular"
            setBackgroundColor(Color.parseColor("#137B7B"))
            setTextColor(Color.WHITE)
            setPadding(32, 16, 32, 16)
            setOnClickListener {
                viewModel.calcularResultados(
                    startDateInput.text.toString(),
                    endDateInput.text.toString()
                )
            }
        }

        formLayout.addView(startDateLabel)
        formLayout.addView(startDateInput)
        formLayout.addView(endDateLabel)
        formLayout.addView(endDateInput)
        formLayout.addView(calculateButton)

        return formLayout
    }

    private fun createResultadosLayout(): LinearLayout {
        val resultadosLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 16, 16, 16)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val resultadosTitleTextView = TextView(this).apply {
            text = "Resultado"
            textSize = 24f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 16)
        }

        resultadosLayout.addView(resultadosTitleTextView)

        val resultadosTabelaLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 16, 16, 16)
            background = GradientDrawable().apply {
                setColor(Color.TRANSPARENT)
                setStroke(2, Color.parseColor("#137B7B"))
            }
        }

        fun createTableRow(leftText: String, rightText: String): Pair<LinearLayout, Pair<TextView, TextView>> {
            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
                setPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            val leftTextView = TextView(this).apply {
                text = leftText
                textSize = 16f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
                setPadding(16, 16, 16, 16)
                background = GradientDrawable().apply {
                    setColor(Color.WHITE)
                    setStroke(2, Color.parseColor("#137B7B"))
                }
            }

            val rightTextView = TextView(this).apply {
                text = rightText
                textSize = 16f
                setTextColor(Color.BLACK)
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
                )
                setPadding(16, 16, 16, 16)
                background = GradientDrawable().apply {
                    setColor(Color.WHITE)
                    setStroke(2, Color.parseColor("#137B7B"))
                }
            }

            rowLayout.addView(leftTextView)
            rowLayout.addView(rightTextView)

            return rowLayout to (leftTextView to rightTextView)
        }

        val (diasUteisLayout, diasUteisPair) = createTableRow("Dias úteis", "")
        val (diasCorridosLayout, diasCorridosPair) = createTableRow("Dias corridos", "")
        val (sabadosLayout, sabadosPair) = createTableRow("Sábados", "")
        val (domingosLayout, domingosPair) = createTableRow("Domingos", "")
        val (feriadosLayout, feriadosPair) = createTableRow("Feriados Nacionais", "")

        resultadosTabelaLayout.addView(diasUteisLayout)
        resultadosTabelaLayout.addView(diasCorridosLayout)
        resultadosTabelaLayout.addView(sabadosLayout)
        resultadosTabelaLayout.addView(domingosLayout)
        resultadosTabelaLayout.addView(feriadosLayout)

        diasUteisTextView = diasUteisPair.second
        diasCorridosTextView = diasCorridosPair.second
        sabadosTextView = sabadosPair.second
        domingosTextView = domingosPair.second
        feriadosNacionaisTextView = feriadosPair.second

        resultadosLayout.addView(resultadosTabelaLayout)

        val observacaoTextView = TextView(this).apply {
            text = "Atenção! a calculadora não considera feriados estaduais e municipais para cálculo de dias úteis, apenas feriados nacionais."
            textSize = 12f
            setTextColor(Color.RED)
            gravity = Gravity.CENTER
            setPadding(0, 16, 0, 16)
        }

        resultadosLayout.addView(observacaoTextView)

        return resultadosLayout
    }

    private fun createFooterLayout(): LinearLayout {
        val footerLayout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(16, 16, 16, 16)
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#212121"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val footerTextView = TextView(this).apply {
            text = "© 2024 SmartDate. It’s holiday? - Copyright"
            textSize = 16f
            setTextColor(Color.WHITE)
        }

        footerLayout.addView(footerTextView)

        return footerLayout
    }

    private fun showDatePickerDialog(onDateSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            onDateSelected(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }
}