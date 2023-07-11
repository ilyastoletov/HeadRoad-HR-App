package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.applicants_list_subfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import com.appninjas.recruiterheaven.databinding.FragmentSubApplicantsListBinding
import com.appninjas.recruiterheaven.presentation.adapter.ApplicantStatusAdapter
import com.appninjas.recruiterheaven.presentation.adapter.model.ApplicantParentItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicantsListSubFragment(private val vacancyId: String) : Fragment() {

    private lateinit var binding: FragmentSubApplicantsListBinding
    private val viewModel: ApplicantsListViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSubApplicantsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        val applicantsListAdapter = ApplicantStatusAdapter()
        binding.applicantsStatusRv.apply {
            adapter = applicantsListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getApplicantsList(vacancyId)
        viewModel.applicantsList.observe(viewLifecycleOwner) {applicantsMap ->
            applicantsListAdapter.setList(convertApplicantMapToList(applicantsMap))
        }
    }

    private fun convertApplicantMapToList(applicantsMap: Map<String, List<Applicant>>): ArrayList<ApplicantParentItem> {
        var applicantsList: ArrayList<ApplicantParentItem> = arrayListOf()
        for(applicantCategory in applicantsMap.keys) {
            applicantsList.add(ApplicantParentItem(title = applicantCategory, applicantsList = applicantsMap[applicantCategory]!!))
        }
        return applicantsList
    }

}