package com.appninjas.recruiterheaven.presentation.screens.metrics.create_metrics

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.dto.CreateMetricsDto
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentCreateMetricsBinding
import com.appninjas.recruiterheaven.presentation.utils.NetworkChecker
import com.appninjas.recruiterheaven.presentation.utils.SHARED_PREFS_NAME
import com.appninjas.recruiterheaven.presentation.utils.USER_ID_KEY
import com.appninjas.recruiterheaven.presentation.utils.Utils.setInvisible
import com.appninjas.recruiterheaven.presentation.utils.Utils.setVisible
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@AndroidEntryPoint
class CreateMetricsFragment : Fragment() {

    private lateinit var binding: FragmentCreateMetricsBinding
    private val viewModel: CreateMetricsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateMetricsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initApplicantCategorySpinner()
        initChooseVacancySpinner()
        initDatePicker()
    }

    private fun initButtons() {

        binding.createMetricButton.setOnClickListener {
            if (validateFields()) {
                if (NetworkChecker.checkIsNetworkAvailable(requireContext())) {
                    val startDate = binding.metricsFromEditText.text.toString()
                    val endDate = binding.metricsToEditText.text.toString()
                    viewModel.getMetricsData(startDate, endDate)
                    viewModel.metricsData.observe(viewLifecycleOwner) {metricsMap ->
                        val metricsDto = CreateMetricsDto(
                            label = binding.metricsTitleEditText.text.toString(),
                            sortingType = binding.applicantStatusCategorySpinner.selectedItem.toString(),
                            vacancyTitle = binding.chooseVacancySpinner.selectedItem.toString(),
                            dateRange = "с $startDate до $endDate",
                            metricEntries = metricsMap
                        )
                        viewModel.saveMetricsToDatabase(metricsDto)
                        Toast.makeText(requireContext(), "Метрика создана", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.navMetrics)
                    }
                }
            }
        }

        binding.createMetricsButtonBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun initApplicantCategorySpinner() {

        fun hideAllMetricsCategoryLayouts() {
            with(binding) {
                metricsCategoryCard.setVisible()
                responsesMetrics.setInvisible()
                testTaskMetrics.setInvisible()
                interviewMetrics.setInvisible()
                onboardingMetrics.setInvisible()
                declineMetrics.setInvisible()
            }
        }

        val statusItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    binding.metricsCategoryCard.setInvisible()
                } else {
                    hideAllMetricsCategoryLayouts()
                    when(position) {
                        1 -> binding.responsesMetrics.setVisible()
                        2 -> binding.testTaskMetrics.setVisible()
                        3 -> binding.interviewMetrics.setVisible()
                        4 -> binding.onboardingMetrics.setVisible()
                        5 -> binding.declineMetrics.setVisible()
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        binding.applicantStatusCategorySpinner.onItemSelectedListener = statusItemSelectedListener
    }

    private fun initChooseVacancySpinner() {
        val userId = requireContext().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .getString(USER_ID_KEY, "")
        viewModel.getUserVacancies(userId!!)
        viewModel.userVacancies.observe(viewLifecycleOwner) {vacancies ->
            val vacanciesSpinnerEntries: List<String> = vacancies.map { vacancy -> vacancy.title }
            binding.chooseVacancySpinner.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, vacanciesSpinnerEntries)
        }
    }

    private fun initDatePicker() {
        val calendarInstance = Calendar.getInstance()
        val firstDatePickedListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                binding.metricsFromEditText.setText(formatDate(year, month + 1, dayOfMonth), TextView.BufferType.EDITABLE)
            }

        val secondDatePickedListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                binding.metricsToEditText.setText(formatDate(year, month + 1, dayOfMonth), TextView.BufferType.EDITABLE)
            }

        binding.metricsFromCalendarButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                firstDatePickedListener,
                calendarInstance.get(Calendar.YEAR),
                calendarInstance.get(Calendar.MONTH),
                calendarInstance.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.metricsToCalendarButton.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                secondDatePickedListener,
                calendarInstance.get(Calendar.YEAR),
                calendarInstance.get(Calendar.MONTH),
                calendarInstance.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    // TODO("Сделать так, чтобы пользователь не мог выбрать конечной дату больше чем сегодняшее число")
    /*private fun validateFields(): Boolean {
        return if (binding.applicantStatusCategorySpinner.selectedItemPosition == 0) {
            Toast.makeText(requireContext(), "Выберите категорию соискателей, по которой будет составлена метрика", Toast.LENGTH_SHORT).show()
            false
        } else if (binding.metricsToEditText.text.isEmpty() || binding.metricsFromEditText.text.isEmpty()){
            Toast.makeText(requireContext(), "Введите даты границ метрики", Toast.LENGTH_SHORT).show()
            false
        } else if (binding.metricsTitleEditText.text.isEmpty()) {
            Toast.makeText(requireContext(), "Введите имя метрики", Toast.LENGTH_SHORT).show()
            false
        } else if (binding.metricsCategoryCard.visibility == View.VISIBLE) {
            when (binding.applicantStatusCategorySpinner.selectedItemPosition) {
                1 -> !binding.responsesRadioGroup.isEmpty()
                2 -> !binding.testTaskRadioGroup.isEmpty()
                3 -> !binding.interviewRadioGroup.isEmpty()
                4 -> !binding.onboardingRadioGroup.isEmpty()
                5 -> !binding.declinesRadioGroup.isEmpty()
                else -> true
            }
        } else {
            true
        }
    }*/

    private fun validateFields(): Boolean = when {
        binding.applicantStatusCategorySpinner.selectedItemPosition == 0 -> {Toast.makeText(requireContext(), "Выберите категорию соискателей, по которой будет составлена метрика", Toast.LENGTH_SHORT).show(); false}
        binding.metricsToEditText.text.isEmpty() || binding.metricsFromEditText.text.isEmpty() -> {Toast.makeText(requireContext(), "Введите даты границ метрики", Toast.LENGTH_SHORT).show(); false}
        !isDateGreaterThanToday(binding.metricsToEditText.text.toString()) -> {Toast.makeText(requireContext(), "Дата конца метрики не должна быть больше сегодняшней даты", Toast.LENGTH_SHORT).show(); false}
        binding.metricsTitleEditText.text.isEmpty() -> {Toast.makeText(requireContext(), "Введите имя метрики", Toast.LENGTH_SHORT).show(); false}
        binding.metricsCategoryCard.visibility == View.VISIBLE -> {
            when (binding.applicantStatusCategorySpinner.selectedItemPosition) {
                1 -> !binding.responsesRadioGroup.isEmpty()
                2 -> !binding.testTaskRadioGroup.isEmpty()
                3 -> !binding.interviewRadioGroup.isEmpty()
                4 -> !binding.onboardingRadioGroup.isEmpty()
                5 -> !binding.declinesRadioGroup.isEmpty()
                else -> true
            }
        }
        else -> true
    }

    private fun isDateGreaterThanToday(dateStr: String): Boolean {
        val date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val todayDate = LocalDate.now()
        return if (date.isBefore(todayDate)) {
            true
        } else date.isEqual(todayDate)
    }

    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val monthStr = if (month < 10) "0$month" else month
        val dayOfMonthStr = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth
        return "$year-$monthStr-$dayOfMonthStr"
    }

}
