package com.appninjas.recruiterheaven.presentation.screens.applicant.profile

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
                Picasso.get().load(applicant.photo_url).into(applicantImage)
            }
        }
        binding.backToApplicantsListButton.setOnClickListener { requireActivity().supportFragmentManager.popBackStackImmediate() }
    }

}