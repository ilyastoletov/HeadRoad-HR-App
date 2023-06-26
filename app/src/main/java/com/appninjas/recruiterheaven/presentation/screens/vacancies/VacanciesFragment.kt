package com.appninjas.recruiterheaven.presentation.screens.vacancies

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentVacanciesBinding
import com.appninjas.recruiterheaven.presentation.adapter.VacancyAdapter
import com.appninjas.recruiterheaven.presentation.utils.SHARED_PREFS_NAME
import com.appninjas.recruiterheaven.presentation.utils.USER_ID_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VacanciesFragment : Fragment() {

    private val viewModel: VacanciesViewModel by viewModels()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.getVacancies(getUserId())
        viewModel.vacanciesList.observe(viewLifecycleOwner) {vacancies ->
            val vacanciesAdapter = VacancyAdapter(vacancies)
            binding.rvVacancies.apply {
                adapter = vacanciesAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }

        binding.createVacancyBtn.setOnClickListener { findNavController().navigate(R.id.createVacancyFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserId(): String {
        val sharedPrefs = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getString(USER_ID_KEY, "")!!
    }

    companion object {
        fun newInstance() = VacanciesFragment()
    }
}