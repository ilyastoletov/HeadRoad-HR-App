package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.applicants_list_subfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import com.appninjas.recruiterheaven.R
import com.appninjas.recruiterheaven.databinding.FragmentSubApplicantsListBinding
import com.appninjas.recruiterheaven.presentation.adapter.ApplicantChildListAdapter
import com.appninjas.recruiterheaven.presentation.adapter.ApplicantStatusAdapter
import com.appninjas.recruiterheaven.presentation.adapter.model.ApplicantParentItem
import com.appninjas.recruiterheaven.presentation.utils.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

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
        val applicantsListAdapter = ApplicantStatusAdapter(applicantProfileCallback, applicantsListPageControlCallback)
        binding.applicantsStatusRv.apply {
            adapter = applicantsListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        loadApplicantsList(applicantsListAdapter)

        with(binding.applicantsListSwipeRefreshLayout) {
            setOnRefreshListener {
                isRefreshing = true
                loadApplicantsList(applicantsListAdapter)
                isRefreshing = false
            }
        }
    }

    private fun convertApplicantMapToList(applicantsMap: Map<String, List<Applicant>>): ArrayList<ApplicantParentItem> {
        var applicantsList: ArrayList<ApplicantParentItem> = arrayListOf()
        for(applicantCategory in applicantsMap.keys) {
            applicantsList.add(ApplicantParentItem(title = applicantCategory,
                applicantsList = applicantsMap[applicantCategory]!!,
                pagesCount = (applicantsMap[applicantCategory]!!.size.toDouble() / 10.0).roundToInt()))
        }
        return applicantsList
    }

    private fun loadApplicantsList(adapter: ApplicantStatusAdapter) {
        val hasNetwork = NetworkChecker.checkIsNetworkAvailable(requireContext())
        if (hasNetwork) {
            viewModel.getApplicantsList(vacancyId)
            viewModel.applicantsList.observe(viewLifecycleOwner) { applicantsMap ->
                adapter.setList(convertApplicantMapToList(applicantsMap))
            }
        }
    }

    private val applicantProfileCallback = object : ApplicantChildListAdapter.ApplicantProfileCallback {
        override fun onClick(model: Applicant) {
            val bundle = Bundle()
            bundle.putString("applicantId", model.applicantId)
            findNavController().navigate(R.id.applicantProfileFragment, bundle)
        }
    }

    private val applicantsListPageControlCallback = object : ApplicantStatusAdapter.PageControlCallback {
        override fun loadFirstPage(model: ApplicantParentItem, childAdapter: ApplicantChildListAdapter) {
            setPagedApplicantsList(
                status = stringStatusToEnum(model.title),
                pageCount = model.currentPage,
                childListAdapter = childAdapter
            )
        }

        override fun onPreviousPagePressed(model: ApplicantParentItem, childAdapter: ApplicantChildListAdapter) {
            model.currentPage--
            setPagedApplicantsList(
                status = stringStatusToEnum(model.title),
                pageCount = model.currentPage,
                childListAdapter = childAdapter
            )
        }

        override fun onNextPagePressed(model: ApplicantParentItem, childAdapter: ApplicantChildListAdapter) {
            model.currentPage++
            setPagedApplicantsList(
                status = stringStatusToEnum(model.title),
                pageCount = model.currentPage,
                childListAdapter = childAdapter
            )
        }
    }
    
    // Удалить если все получится без этой шняги
    /*private fun getApplicantsByPage(applicantList: List<Applicant>, page: Int): List<Applicant> {
        Log.d("TAG", "${applicantList.size}")
        var slicedApplicantsList: List<Applicant> = if (applicantList.size >= 10 || applicantList.size >= page * 10) {
            applicantList.slice(page * 10 - 10 until page * 10)
        } else {
            applicantList.slice(page * 10 - 10 until applicantList.lastIndex)
        }
        Log.d("TAG", slicedApplicantsList.toString())
        return slicedApplicantsList
    }*/

    private fun setPagedApplicantsList(status: ApplicantStatus, pageCount: Int, childListAdapter: ApplicantChildListAdapter) {
        Log.d("TAG", "${viewModel.pagedApplicantsList.hasObservers()}, ${viewModel.pagedApplicantsList.hasActiveObservers()}")
        viewModel.getApplicantsByPage(vacancyId, pageCount, status)
        viewModel.pagedApplicantsList.observe(viewLifecycleOwner) {applicantsList ->
            childListAdapter.setList(applicantsList)
            viewModel.pagedApplicantsList.removeObservers(viewLifecycleOwner)
        }
    }
    
    private fun stringStatusToEnum(status: String): ApplicantStatus = when(status) {
        "Новые" -> ApplicantStatus.NEW
        "Тестовое задание" -> ApplicantStatus.TEST_TASK
        "Предвар. интервью" -> ApplicantStatus.PHONE_INTERVIEW
        "Тех. интервью" -> ApplicantStatus.TECH_INTERVIEW
        "Оффер" -> ApplicantStatus.OFFER
        "Онбординг" -> ApplicantStatus.ONBOARDING
        "Отказ кандидата" -> ApplicantStatus.APPLICANT_DECLINE
        "Отказ рекрутера" -> ApplicantStatus.RECRUITER_DECLINE
        else -> ApplicantStatus.NEW
    }

}