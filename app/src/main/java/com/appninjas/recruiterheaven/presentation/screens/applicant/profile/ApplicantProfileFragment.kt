package com.appninjas.recruiterheaven.presentation.screens.applicant.profile

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appninjas.recruiterheaven.databinding.FragmentApplicantProfileBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ApplicantProfileFragment : Fragment() {

    private lateinit var binding: FragmentApplicantProfileBinding
    private lateinit var applicantId: String

    private val viewModel: ApplicantProfileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApplicantProfileBinding.inflate(inflater, container, false)
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
            with(binding) {
                applicantNameText.text = applicant.name
                ageAndCityText.text = "${applicant.age}, ${applicant.city}"
                applicantEducationText.text = applicant.education
                applicantPhoneNumberTv.text = applicant.phone
                applicantPhoneNumberTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                applicantEmailTv.text = applicant.email
                applicantEmailTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                Picasso.get().load(applicant.photo_url).into(applicantImage)
                applicantExperienceText.text = applicant.experience

                viewModel.getVacancyTitle(applicant.appliedVacancyId)
                viewModel.vacancyTitle.observe(viewLifecycleOwner) {vacancyTitle ->
                    binding.applicantAppliedVacancyTitle.text = vacancyTitle
                }
            }
        }
        binding.backToApplicantsListButton.setOnClickListener { requireActivity().supportFragmentManager.popBackStackImmediate() }
    }

}