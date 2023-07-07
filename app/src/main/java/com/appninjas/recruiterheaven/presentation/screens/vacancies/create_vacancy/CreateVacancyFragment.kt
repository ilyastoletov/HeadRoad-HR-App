package com.appninjas.recruiterheaven.presentation.screens.vacancies.create_vacancy

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.model.dto.UpdateVacancyDto
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentCreateVacancyBinding
import com.appninjas.recruiterheaven.presentation.utils.NetworkChecker
import com.appninjas.recruiterheaven.presentation.utils.SHARED_PREFS_NAME
import com.appninjas.recruiterheaven.presentation.utils.USER_ID_KEY
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.regex.Pattern

@AndroidEntryPoint
class CreateVacancyFragment : Fragment() {

    private lateinit var binding: FragmentCreateVacancyBinding
    private val viewModel: CreateVacancyViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCreateVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) initEditUI() else initUI()
    }

    private fun initUI() {
        binding.backToVacanciesButton.setOnClickListener { findNavController().navigate(R.id.navVacancies) }
        binding.buttonCreateVacancy.setOnClickListener {
            if (validateInputFields()) {
                if (hasNetwork()) {
                    viewModel.createVacancy(createDtoFromInputFields())
                    findNavController().navigate(R.id.navVacancies)
                    Toast.makeText(requireContext(), "Вакансия создана", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Отсутствует соединение с интернетом", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initEditUI() {
        viewModel.getVacancyDetails(getVacancyId())
        viewModel.vacancyDetails.observe(viewLifecycleOwner) {vacancyData ->
            binding.apply {
                departmentEditText.setText(vacancyData.department)
                vacancyPositionEditText.setText(vacancyData.title)
                experienceEditText.setText(vacancyData.experience)
                salaryEditText.setText(vacancyData.salary)
                requirementsEditText.setText(vacancyData.requirements)
                conditionsEditText.setText(vacancyData.requirements)
                jobDutiesEditText.setText(vacancyData.job_duties)
            }
        }
        binding.buttonCreateVacancy.setOnClickListener {
            if (validateInputFields()) {
                if (hasNetwork()) {
                    val updateDto = UpdateVacancyDto(
                        department = binding.departmentEditText.text.toString(),
                        title = binding.vacancyPositionEditText.text.toString(),
                        experience = binding.experienceEditText.text.toString(),
                        salary = binding.salaryEditText.text.toString() + binding.currencyChooseSpinner.selectedItem.toString(),
                        requirements = binding.requirementsEditText.text.toString(),
                        conditions = binding.conditionsEditText.text.toString(),
                        job_duties = binding.jobDutiesEditText.text.toString()
                    )
                    viewModel.updateVacancy(getVacancyId(), updateDto)
                    navigateToVacanciesInfo()
                } else {
                    Toast.makeText(requireContext(), "Отсутствует соединение с интернетом", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createDtoFromInputFields(): CreateVacancyDto {
        return CreateVacancyDto(
            createdAt = getTodayDate(),
            department = binding.departmentEditText.text.toString(),
            title = binding.vacancyPositionEditText.text.toString(),
            experience = binding.experienceEditText.text.toString(),
            salary = binding.salaryEditText.text.toString() + binding.currencyChooseSpinner.selectedItem.toString(),
            requirements = binding.requirementsEditText.text.toString(),
            conditions = binding.conditionsEditText.text.toString(),
            job_duties = binding.jobDutiesEditText.text.toString(),
            authorId = getAuthorId()
        )
    }

    private fun navigateToVacanciesInfo() {
        val navigationBundle = Bundle()
        navigationBundle.putString("vacancyId", getVacancyId())
        findNavController().navigate(R.id.vacancyInfoFragment, navigationBundle)
    }

    private fun validateInputFields(): Boolean {
        with(binding) {
            if (vacancyPositionEditText.text.isEmpty() || departmentEditText.text.isEmpty() || salaryEditText.text.isEmpty() ||
                    jobDutiesEditText.text.isEmpty() || conditionsEditText.text.isEmpty() || vacancyDescriptionEditText.text.isEmpty() ||
                    experienceEditText.text.isEmpty() || requirementsEditText.text.isEmpty()) {
                Toast.makeText(requireContext(), "Вы заполнили не все поля", Toast.LENGTH_SHORT).show()
                return false
            } else {
                return true
            }
        }
    }

    private fun getTodayDate(): String {
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        return sdf.format(Date())
    }

    private fun getVacancyId(): String = requireArguments().getString("vacancyId")!!

    private fun getAuthorId(): String {
        val sharedPrefsInstance = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefsInstance.getString(USER_ID_KEY, "")!!
    }

    private fun hasNetwork(): Boolean = NetworkChecker.checkIsNetworkAvailable(requireContext())

}