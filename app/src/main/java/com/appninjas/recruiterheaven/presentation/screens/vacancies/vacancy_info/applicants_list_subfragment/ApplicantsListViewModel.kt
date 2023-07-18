package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.applicants_list_subfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.usecase.GetApplicantListUseCase
import com.appninjas.domain.usecase.GetApplicantsListByPageUseCase
import com.appninjas.recruiterheaven.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantsListViewModel @Inject constructor(private val getApplicantListUseCase: GetApplicantListUseCase,
                                                  private val getApplicantsListByPageUseCase: GetApplicantsListByPageUseCase): ViewModel() {

    private val _applicantsList: MutableLiveData<Map<String, List<Applicant>>> = MutableLiveData()
    val applicantsList: LiveData<Map<String, List<Applicant>>> = _applicantsList

    private val _pagedApplicantsList: SingleLiveEvent<List<Applicant>> = SingleLiveEvent()
    val pagedApplicantsList: LiveData<List<Applicant>> = _pagedApplicantsList

    fun getApplicantsList(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getApplicantListUseCase.invoke(vacancyId)
            _applicantsList.postValue(result)
        }
    }

    fun getApplicantsByPage(vacancyId: String, pageNumber: Int, status: ApplicantStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getApplicantsListByPageUseCase.invoke(vacancyId, pageNumber, status)
            _pagedApplicantsList.postValue(result)
        }
    }

    fun clearPagedApplicantsData() {
        _pagedApplicantsList.call()
    }

}