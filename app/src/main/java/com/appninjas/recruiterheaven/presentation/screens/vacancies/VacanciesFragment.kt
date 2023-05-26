package com.appninjas.recruiterheaven.presentation.screens.vacancies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.appninjas.recruiterheaven.databinding.FragmentVacanciesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VacanciesFragment : Fragment() {

    private val viewModel: VacanciesViewModel by lazy {
        ViewModelProvider(this)[VacanciesViewModel::class.java]
    }

    private var _binding: FragmentVacanciesBinding? = null
    private val binding: FragmentVacanciesBinding
        get() = _binding ?: throw Exception("FragmentCalendarBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = VacanciesFragment()
    }
}