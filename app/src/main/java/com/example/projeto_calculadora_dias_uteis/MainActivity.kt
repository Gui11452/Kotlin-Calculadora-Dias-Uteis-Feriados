package com.example.projeto_calculadora_dias_uteis

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            gravity = android.view.Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        val titleTextView = TextView(this).apply {
            text = "Bem-vindo à SmartDate. It’s holiday?"
            textSize = 24f
            setTextColor(android.graphics.Color.BLACK)
            gravity = android.view.Gravity.CENTER
            setPadding(0, 0, 0, 32)
        }

        val calculadoraButton = Button(this).apply {
            text = "Iniciar"
            setBackgroundColor(android.graphics.Color.parseColor("#137B7B"))
            setTextColor(android.graphics.Color.WHITE)
            setPadding(32, 16, 32, 16)
            setOnClickListener {
                val intent = Intent(this@MainActivity, CalculadoraActivity::class.java)
                startActivity(intent)
            }
        }

        mainLayout.addView(titleTextView)
        mainLayout.addView(calculadoraButton)

        setContentView(mainLayout)
    }
}
