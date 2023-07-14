package com.appninjas.recruiterheaven.presentation.screens.vacancies

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.model.Vacancy
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentVacanciesBinding
import com.appninjas.recruiterheaven.presentation.adapter.VacancyAdapter
import com.appninjas.recruiterheaven.presentation.utils.NetworkChecker
import com.appninjas.recruiterheaven.presentation.utils.SHARED_PREFS_NAME
import com.appninjas.recruiterheaven.presentation.utils.USER_ID_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VacanciesFragment : Fragment() {

    private val viewModel: VacanciesViewModel by viewModels()

    private var _binding: FragmentVacanciesBinding? = null
    private val binding: FragmentVacanciesBinding
        get() = _binding ?: throw Exception("FragmentCalendarBinding = null")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentVacanciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val networkAvailable: Boolean = NetworkChecker.checkIsNetworkAvailable(requireContext())
        val vacanciesAdapter = VacancyAdapter(hasInternet = networkAvailable,
            vacancyCardListener = vacanciesClickListener)
        binding.rvVacancies.apply {
            adapter = vacanciesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        loadVacanciesList(vacanciesAdapter)

        with(binding.vacanciesSwipeToRefreshLayout) {
            setOnRefreshListener {
                isRefreshing = true
                loadVacanciesList(vacanciesAdapter)
                isRefreshing = false
            }
        }

        binding.createVacancyBtn.setOnClickListener { findNavController().navigate(R.id.createVacancyFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadVacanciesList(vacanciesAdapter: VacancyAdapter) {
        val networkAvailable: Boolean = NetworkChecker.checkIsNetworkAvailable(requireContext())
        if (networkAvailable) {
            viewModel.getVacancies(getUserId())
            viewModel.vacanciesList.observe(viewLifecycleOwner) { vacancies ->
                vacanciesAdapter.setList(vacancies.sortedBy { it.vacancyStatus })
                initSpinner(vacancies, vacanciesAdapter)
            }
        }
    }

    private fun getUserId(): String {
        val sharedPrefs = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getString(USER_ID_KEY, "")!!
    }

    private fun initSpinner(vacanciesList: List<Vacancy>, adapter: VacancyAdapter) {
        val spinnerItemChangeListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                Log.d("TAG", "Random message")
            }

            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sortedVacanciesList = when(position) {
                    0 -> vacanciesList.sortedBy { it.vacancyStatus }
                    1 -> vacanciesList.sortedByDescending { it.responderIds.size }
                    2 -> vacanciesList.sortedByDescending { it.createdAt }
                    else -> vacanciesList
                }
                adapter.setList(sortedVacanciesList)
            }
        }
        binding.vacancyFilterSpinner.onItemSelectedListener = spinnerItemChangeListener
    }

    private val vacanciesClickListener = object : VacancyAdapter.VacancyClickListener {
        override fun onClick(vacancyId: String) {
            val navigationBundle = Bundle()
            navigationBundle.putString("vacancyId", vacancyId)
            findNavController().navigate(R.id.vacancyInfoFragment, navigationBundle)
        }
    }


}