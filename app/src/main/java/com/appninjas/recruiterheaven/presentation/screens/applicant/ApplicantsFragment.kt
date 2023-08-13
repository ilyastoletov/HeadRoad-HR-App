package com.appninjas.recruiterheaven.presentation.screens.applicant

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.model.Applicant
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentApplicantsBinding
import com.appninjas.recruiterheaven.presentation.adapter.ApplicantSearchAdapter
import com.appninjas.recruiterheaven.presentation.adapter.SocialNetworkAdapter
import com.appninjas.recruiterheaven.presentation.adapter.model.SocialNetwork
import com.appninjas.recruiterheaven.presentation.screens.applicant.profile.view_photo.ViewPhotoActivity
import com.appninjas.recruiterheaven.presentation.utils.Utils.setInvisible
import com.appninjas.recruiterheaven.presentation.utils.Utils.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicantsFragment : Fragment() {

    private lateinit var binding: FragmentApplicantsBinding
    private val viewModel: ApplicantsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentApplicantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initFiltersButton()
        setupFiltersMenu()
    }

    private fun initUI() {

        val applicantsSearchAdapter = ApplicantSearchAdapter(profileImageCallback, applicantClickCallback, socialMediaCallback)
        binding.applicantsFoundRecyclerView.apply {
            adapter = applicantsSearchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.foundApplicants.observeForever {applicants ->
            if (applicants != null) {
                applicantsSearchAdapter.setList(applicants)
                binding.applicantsFoundText.text = "Найдено - ${applicants.size}"
            } else {
                applicantsSearchAdapter.setList(listOf())
                binding.applicantsFoundText.text = "Найдено - 0"
            }
        }

        binding.workDayRadio.check(R.id.full_day_radio)
        binding.applicantsSearchBar.addTextChangeListener(applicantsSearchBarTextWatcher)
    }

    private fun initFiltersButton() {
        var isFilterMenuOpened = false
        binding.filtersButton.setOnClickListener {
            with(binding) {
                if (!isFilterMenuOpened) {
                    filtersText.setTextColor(Color.parseColor("#FFFFFF"))
                    filtersButton.setCardBackgroundColor(Color.parseColor("#417BD2"))
                    filterImage.setImageResource(R.drawable.baseline_filter_alt_24_white)
                    applicantsFoundLayout.setInvisible()
                    filtersLayout.setVisible()
                    isFilterMenuOpened = true
                } else {
                    closeFilterMenu()
                    isFilterMenuOpened = false
                }
            }
        }
    }

    private fun setupFiltersMenu() {
        binding.applyFiltersButton.setOnClickListener { closeFilterMenu() }
    }

    private fun closeFilterMenu() {
        with(binding) {
            filtersText.setTextColor(Color.parseColor("#417BD2"))
            filtersButton.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            filterImage.setImageResource(R.drawable.baseline_filter_alt_24)
            filtersLayout.setInvisible()
            applicantsFoundLayout.setVisible()
        }
    }

    private val applicantsSearchBarTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence?, start: Int, cound: Int, after: Int) {}

        override fun afterTextChanged(text: Editable?) {
            viewModel.clearApplicants()
            val filters = getFilters()
            viewModel.searchApplicants(text.toString(), filters["city"].toString(),
                filters["workDayFilter"].toString().toBoolean(),
                filters["salaryBottom"].toString(),
                filters["salaryTop"].toString())
        }
    }

    private fun getFilters(): Map<String, Any?> {
        val cityFilter: String? = binding.citySearchBar.text.ifEmpty { null }
        val workDayFilter: Boolean = when(binding.workDayRadio.checkedRadioButtonId) {
            R.id.full_day_radio -> true
            R.id.not_full_day_radio -> false
            else -> false
        }
        val salaryFilterBottom: String? = binding.wantedSalaryFromEditText.text.toString().ifEmpty { null }
        val salaryFilterTop: String? = binding.wantedSalaryTopEditText.text.toString().ifEmpty { null }
        return mapOf(
            "city" to cityFilter,
            "workDayFilter" to workDayFilter,
            "salaryBottom" to salaryFilterBottom,
            "salaryTop" to salaryFilterTop
        )
    }

    private val profileImageCallback = object : ApplicantSearchAdapter.ApplicantProfileImageCallback {
        override fun onClick(model: Applicant) {
            val resizeActivityIntent = Intent(requireContext(), ViewPhotoActivity::class.java)
            resizeActivityIntent.putExtra("imageUrl", model.photo_url)
            startActivity(resizeActivityIntent)
        }
    }

    private val applicantClickCallback = object : ApplicantSearchAdapter.ApplicantSearchButtonCallback {
        override fun onClick(model: Applicant) {
            val navigationBundle = Bundle()
            navigationBundle.putString("applicantId", model.applicantId)
            findNavController().navigate(R.id.applicantProfileFragment, navigationBundle)
        }
    }

    private val socialMediaCallback = object : SocialNetworkAdapter.SocialNetworkClickCallback {
        override fun onClick(model: SocialNetwork) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(model.link))
            startActivity(browserIntent)
        }
    }

}