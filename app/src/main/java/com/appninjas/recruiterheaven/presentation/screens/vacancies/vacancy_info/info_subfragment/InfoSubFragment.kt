package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.info_subfragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentSubInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoSubFragment(private val vacancyId: String) : Fragment() {

    private lateinit var binding: FragmentSubInfoBinding
    private val viewModel: InfoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSubInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initSpinner()
    }

    private fun initUI() {
        viewModel.getVacancyDetails(vacancyId)
        viewModel.vacancyDetails.observe(viewLifecycleOwner) {vacancy ->
            binding.apply {
                vacancyTitle.text = vacancy.title
                vacancyDepartment.text = vacancy.department
                Log.d("TAGEX", "${vacancy.toString()}")
                vacancyStatusChangeSpinner.setSelection(when(vacancy.vacancyStatus) {
                    VacancyStatus.OPEN -> 0
                    VacancyStatus.PAUSED -> 1
                    VacancyStatus.STOPPED -> 2
                })
                vacancyCreationDate.text = "Создана:\n${vacancy.createdAt}"
                salaryText.text = vacancy.salary
                experienceTextView.text = vacancy.experience
                requirementsText.text = vacancy.requirements
                conditionsText.text = vacancy.conditions
                jobDutiesText.text= vacancy.job_duties
                // TODO(ДОБАВИТЬ DESCRIPTION повсюду)
                vacancyDescriptionText.text = "В разработке"
            }
        }
    }

    private fun initSpinner() {
        val spinner = binding.vacancyStatusChangeSpinner
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.vacancy_status, R.layout.info_spinner_layout)
        adapter.setDropDownViewResource(R.layout.info_custom_dropdown_spinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = vacancyStatusItemSelectListener
    }

    private val vacancyStatusItemSelectListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(p0: AdapterView<*>?) {
            Log.d("RANDOM TAg", "sPINNER Vacancy Info nothing selected, yay")
        }

        override fun onItemSelected(parentView: AdapterView<*>?, View: View?, position: Int, id: Long) = when(position) {
            0 -> { binding.vacancyStatusCard.setCardBackgroundColor(Color.parseColor("#44C879")) }
            1 -> { binding.vacancyStatusCard.setCardBackgroundColor(Color.parseColor("#f2e018")) }
            2 -> { binding.vacancyStatusCard.setCardBackgroundColor(Color.parseColor("#f24918")) }
            else -> {}
        }
    }

}