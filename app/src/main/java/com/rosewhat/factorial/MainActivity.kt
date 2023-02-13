package com.rosewhat.factorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.rosewhat.factorial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeViewModel()
        binding.buttonCalculate.setOnClickListener {
            viewModel.calculate(binding.editTextNumber.text.toString())
        }
    }

    private fun observeViewModel() {

        viewModel.state.observe(this) {
            binding.buttonCalculate.isEnabled = true
            binding.progressBarLoading.visibility = View.GONE
            when (it) {
                is Error -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                is Progress -> {
                    binding.buttonCalculate.isEnabled = false
                    binding.progressBarLoading.visibility = View.VISIBLE
                }

                is Result -> {
                    binding.textViewFactorial.text = it.factorial

                }
            }

        }
    }
}