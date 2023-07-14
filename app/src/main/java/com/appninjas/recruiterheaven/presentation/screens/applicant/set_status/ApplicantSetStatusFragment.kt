package com.appninjas.recruiterheaven.presentation.screens.applicant.set_status

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentApplicantSetStatusBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicantSetStatusFragment : Fragment() {

    private lateinit var binding: FragmentApplicantSetStatusBinding
    private lateinit var applicantId: String

    private val viewModel: ApplicantSetStatusViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApplicantSetStatusBinding.inflate(inflater, container, false)
        applicantId = requireArguments().getString("applicantId", "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.getApplicantDetails(applicantId)
        viewModel.applicantDetails.observe(viewLifecycleOwner) {applicant ->

            viewModel.getVacancyTitle(applicant.appliedVacancyId)
            viewModel.vacancyTitle.observe(viewLifecycleOwner) {vacancyTitle ->
                binding.applicantStatusRadioGroup.setOnCheckedChangeListener { _, id ->
                    val letterText = when(id) {
                        R.id.test_task_status_radio -> "Здравствуйте! Вы прошли отборочный тур на вакансию $vacancyTitle, поэтому мы высылаем вам тестовое задание. Срок его завршения 00.00.0000, вышлите полученый результат на этот же адрес электронной почты. Желаем удачи! С уважением, команда Google"
                        R.id.phone_interview_status_radio -> "Ещё раз здравствуйте! Ваше тестовое задание проверено нашими программистами, мы готовы сказать что приглашаем вас на предварительное интервью по телефону. Пожалуйста, будьте готовы принять звонок 00.00.0000 в промежуток с 00.00 до 00.00. Удачи на интервью"
                        R.id.technical_interview_status_radio -> "Привет! Телефонное интервью успешно пройдено, мы приглашаем вас на техническое интервью. Вам на почту придет ссылка для подключения 00.00.0000. Удачи."
                        R.id.offer_status_radio -> "Ураа! Мы готовы выслать вам оффер на работу, конкурентная зарплата 0 $ до вычета налогов. Welcome abroad, ${applicant.name}"
                        else -> ""
                    }
                    binding.applicantLetterEditText.setText(letterText, TextView.BufferType.EDITABLE)
                }
            }

            binding.applicantStatusRadioGroup.check(when(applicant.status) {
                ApplicantStatus.TEST_TASK -> R.id.test_task_status_radio
                ApplicantStatus.PHONE_INTERVIEW -> R.id.phone_interview_status_radio
                ApplicantStatus.TECH_INTERVIEW -> R.id.technical_interview_status_radio
                ApplicantStatus.OFFER -> R.id.offer_status_radio
                ApplicantStatus.ONBOARDING -> R.id.onboarding_status_radio
                ApplicantStatus.APPLICANT_DECLINE -> R.id.applicant_decline_status_radio
                ApplicantStatus.RECRUITER_DECLINE -> R.id.recruiter_decline_status_radio
                ApplicantStatus.NEW -> R.id.dummy_radio
                ApplicantStatus.DELETED -> R.id.dummy_radio
            })
        }

        binding.applicantStatusConfirmButton.setOnClickListener {
            val infoDialog = AlertDialog.Builder(requireContext())
                .setTitle("Информация")
                .setMessage("Статус кандидата успешно сменен. Обновите список, потянув его вверх, чтобы кандидат перешел в нужную категорию")
                .setPositiveButton("Ок") { dialog, _ -> dialog.cancel() }
                .setCancelable(true)
                .create()
            infoDialog.show()
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }

        binding.backToApplicantInfoButton.setOnClickListener { requireActivity().supportFragmentManager.popBackStackImmediate() }
    }

}