package com.appninjas.recruiterheaven.presentation.screens.applicant.profile

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentApplicantProfileBinding
import com.appninjas.recruiterheaven.presentation.adapter.SocialNetworkAdapter
import com.appninjas.recruiterheaven.presentation.adapter.model.SocialNetwork
import com.appninjas.recruiterheaven.presentation.screens.applicant.profile.view_photo.ViewPhotoActivity
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

// TODO("Profile image resizing feature")
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
                applicantEmailTv.text = applicant.email
                Picasso.get().load(applicant.photo_url).into(applicantImage)
                applicantExperienceText.text = applicant.experience
                applicantWantedSalaryText.text = applicant.wanted_salary
                applicantResumeUrlText.text = applicant.resume_url

                applicantPhoneNumberTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                applicantEmailTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
                applicantResumeUrlText.paintFlags = Paint.UNDERLINE_TEXT_FLAG

                viewModel.getVacancyTitle(applicant.appliedVacancyId)
                viewModel.vacancyTitle.observe(viewLifecycleOwner) {vacancyTitle ->
                    applicantAppliedVacancyTitle.text = vacancyTitle
                }

                applicantPhoneNumberTv.setOnClickListener {
                    val phoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${applicant.phone}"))
                    startActivity(phoneIntent)
                }

                applicantResumeUrlText.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(applicant.resume_url))
                    startActivity(browserIntent)
                }

                applicantImage.setOnClickListener {
                    val resizeActivityIntent = Intent(requireContext(), ViewPhotoActivity::class.java)
                    resizeActivityIntent.putExtra("imageUrl", applicant.photo_url)
                    startActivity(resizeActivityIntent)
                }

                val socialNetworkAdapter = SocialNetworkAdapter(applicant.social_media_links.map { SocialNetwork(link = it) }, socialNetworkCallback)
                applicantSocialLinksRv.apply {
                    adapter = socialNetworkAdapter
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }

        binding.applicantChangeStatusButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("applicantId", applicantId)
            findNavController().navigate(R.id.applicantSetStatusFragment, bundle)
        }

        binding.backToApplicantsListButton.setOnClickListener { requireActivity().supportFragmentManager.popBackStackImmediate() }

    }

    private val socialNetworkCallback = object : SocialNetworkAdapter.SocialNetworkClickCallback {
        override fun onClick(model: SocialNetwork) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.link))
            startActivity(browserIntent)
        }
    }

}