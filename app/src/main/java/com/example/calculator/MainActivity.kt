package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView

    private var firstNumber = 0.0
    private var secondNumber = 0.0

    private var operator = ""
    private var newNumber = true

    private val decimalFormat = DecimalFormat("#.##########")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Agar layout tidak tertutup status bar / navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars())

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // ==========================
        // DISPLAY
        // ==========================

        tvDisplay = findViewById(R.id.tvDisplay)


        // ==========================
        // TOMBOL ANGKA
        // ==========================

        val btn0 = findViewById<Button>(R.id.btn0)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btn7 = findViewById<Button>(R.id.btn7)
        val btn8 = findViewById<Button>(R.id.btn8)
        val btn9 = findViewById<Button>(R.id.btn9)


        // ==========================
        // TOMBOL OPERATOR
        // ==========================

        val btnPlus = findViewById<Button>(R.id.btnPlus)
        val btnMinus = findViewById<Button>(R.id.btnMinus)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)
        val btnEquals = findViewById<Button>(R.id.btnEquals)


        // ==========================
        // TOMBOL LAIN
        // ==========================

        val btnAC = findViewById<Button>(R.id.btnAC)
        val btnDecimal = findViewById<Button>(R.id.btnDecimal)
        val btnPercent = findViewById<Button>(R.id.btnPercent)
        val btnPlusMinus = findViewById<Button>(R.id.btnPlusMinus)


        // ==========================
        // EVENT TOMBOL ANGKA
        // ==========================

        btn0.setOnClickListener { inputNumber("0") }
        btn1.setOnClickListener { inputNumber("1") }
        btn2.setOnClickListener { inputNumber("2") }
        btn3.setOnClickListener { inputNumber("3") }
        btn4.setOnClickListener { inputNumber("4") }
        btn5.setOnClickListener { inputNumber("5") }
        btn6.setOnClickListener { inputNumber("6") }
        btn7.setOnClickListener { inputNumber("7") }
        btn8.setOnClickListener { inputNumber("8") }
        btn9.setOnClickListener { inputNumber("9") }


        // ==========================
        // EVENT OPERATOR
        // ==========================

        btnPlus.setOnClickListener {
            setOperator("+")
        }

        btnMinus.setOnClickListener {
            setOperator("-")
        }

        btnMultiply.setOnClickListener {
            setOperator("*")
        }

        btnDivide.setOnClickListener {
            setOperator("/")
        }


        // ==========================
        // TOMBOL =
        // ==========================

        btnEquals.setOnClickListener {
            calculate()
        }


        // ==========================
        // TOMBOL AC
        // ==========================

        btnAC.setOnClickListener {
            clearCalculator()
        }


        // ==========================
        // TOMBOL DESIMAL
        // ==========================

        btnDecimal.setOnClickListener {

            if (newNumber) {

                tvDisplay.text = "0."
                newNumber = false

            } else {

                val current = tvDisplay.text.toString()

                if (!current.contains(".")) {
                    tvDisplay.append(".")
                }
            }
        }


        // ==========================
        // TOMBOL +/-
        // ==========================

        btnPlusMinus.setOnClickListener {

            try {

                var value =
                    tvDisplay.text.toString().toDouble()

                value *= -1

                tvDisplay.text =
                    formatNumber(value)

            } catch (e: Exception) {

                tvDisplay.text = "0"
            }
        }


        // ==========================
        // TOMBOL %
        // ==========================

        btnPercent.setOnClickListener {

            try {

                var value =
                    tvDisplay.text.toString().toDouble()

                value /= 100

                tvDisplay.text =
                    formatNumber(value)

            } catch (e: Exception) {

                tvDisplay.text = "0"
            }
        }
    }


    // ==========================================
    // INPUT ANGKA
    // ==========================================

    private fun inputNumber(number: String) {

        if (newNumber) {

            tvDisplay.text = number
            newNumber = false

        } else {

            val current =
                tvDisplay.text.toString()

            if (current == "0") {

                tvDisplay.text = number

            } else {

                tvDisplay.append(number)
            }
        }
    }


    // ==========================================
    // PILIH OPERATOR
    // ==========================================

    private fun setOperator(selectedOperator: String) {

        try {

            // Jika sebelumnya sudah ada operasi
            // misalnya 5 + 5 + ...
            // maka hitung dulu
            if (operator.isNotEmpty() && !newNumber) {
                calculate()
            }

            firstNumber =
                tvDisplay.text.toString().toDouble()

            operator =
                selectedOperator

            newNumber = true

        } catch (e: Exception) {

            tvDisplay.text = "0"
        }
    }


    // ==========================================
    // PROSES PERHITUNGAN
    // ==========================================

    private fun calculate() {

        if (operator.isEmpty()) {
            return
        }

        try {

            secondNumber =
                tvDisplay.text.toString().toDouble()

            val result: Double

            when (operator) {

                "+" -> {
                    result =
                        firstNumber + secondNumber
                }

                "-" -> {
                    result =
                        firstNumber - secondNumber
                }

                "*" -> {
                    result =
                        firstNumber * secondNumber
                }

                "/" -> {

                    if (secondNumber == 0.0) {

                        tvDisplay.text = "Error"

                        operator = ""
                        newNumber = true

                        return
                    }

                    result =
                        firstNumber / secondNumber
                }

                else -> {
                    return
                }
            }

            tvDisplay.text =
                formatNumber(result)

            firstNumber = result

            operator = ""

            newNumber = true

        } catch (e: Exception) {

            tvDisplay.text = "Error"

            operator = ""

            newNumber = true
        }
    }


    // ==========================================
    // RESET CALCULATOR
    // ==========================================

    private fun clearCalculator() {

        firstNumber = 0.0

        secondNumber = 0.0

        operator = ""

        newNumber = true

        tvDisplay.text = "0"
    }


    // ==========================================
    // FORMAT ANGKA
    // ==========================================

    private fun formatNumber(number: Double): String {

        val fixedNumber =
            if (number == -0.0) 0.0 else number

        return decimalFormat.format(fixedNumber)
    }
}