package com.example.calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.calculator.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Clear Button
        binding.buttoncleayer.setOnClickListener {
            binding.input.text = ""
            binding.output.text = ""
        }

        // Number Buttons
        val numberButtons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3, binding.button4,
            binding.button5, binding.button6, binding.button7, binding.button8, binding.button9
        )
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                binding.input.text = addToInputText(index.toString())
            }
        }

        // Operators
        binding.buttonBreaketlift.setOnClickListener { binding.input.text = addToInputText("(") }
        binding.buttonBreaketright.setOnClickListener { binding.input.text = addToInputText(")") }
        binding.buttonDot.setOnClickListener { binding.input.text = addToInputText(".") }
        binding.buttonDevision.setOnClickListener { binding.input.text = addToInputText("/") }
        binding.buttonMultiple.setOnClickListener { binding.input.text = addToInputText("*") }
        binding.buttonSubtraction.setOnClickListener { binding.input.text = addToInputText("-") }
        binding.buttonAddition.setOnClickListener { binding.input.text = addToInputText("+") }


        // Equals Button
        binding.buttonEquals.setOnClickListener { showResult() }
    }

    private fun addToInputText(buttonValue: String): String {
        return "${binding.input.text}$buttonValue"
    }

    private fun showResult() {

        kotlin.runCatching {
            val expression = binding.input.text.toString()
            val result = Expression(expression).calculate()

            if (result.isNaN()) {
                binding.output.text = "Invalid Expression"
                binding.output.setTextColor(ContextCompat.getColor(this, R.color.red))
            } else {
                binding.output.text = DecimalFormat("#,###.##").format(result)
                binding.output.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
        }.onFailure {
            binding.output.text = "Error"
            binding.output.setTextColor(ContextCompat.getColor(this, R.color.red))
        }


    }}
