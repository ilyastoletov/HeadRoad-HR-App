package com.appninjas.recruiterheaven.presentation.screens.vacancies.create_vacancy

import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentCreateVacancyBinding

class CreateVacancyFragment : Fragment() {

    private lateinit var binding: FragmentCreateVacancyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.backToVacanciesButton.setOnClickListener { findNavController().navigate(R.id.navVacancies) }
        binding.salaryEditText.addTextChangedListener(salaryTextWatcher)
    }

    private val salaryTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            Log.d("Just useless log", "Hey! Look at this: $text")
        }

        override fun onTextChanged(text: CharSequence?, start: Int, end: Int, count: Int) {
            Log.d("Just useless log", "Hey! Look at this: $text")
        }

        override fun afterTextChanged(editableString: Editable?) {
            val formatter = NumberFormat.getCurrencyInstance()
            formatter.apply {
                maximumFractionDigits = 0
                currency = Currency.getInstance("RUB")
            }
            val newText = formatter.format(editableString.toString())
            Log.d("Some useless log", "$newText")
            binding.salaryEditText.setText(newText, TextView.BufferType.EDITABLE)
        }
    }

}