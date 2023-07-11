package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.applicants_list_subfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.usecase.GetApplicantListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantsListViewModel @Inject constructor(private val getApplicantListUseCase: GetApplicantListUseCase): ViewModel() {

    private val _applicantsList: MutableLiveData<Map<String, List<Applicant>>> = MutableLiveData()
    val applicantsList: LiveData<Map<String, List<Applicant>>> = _applicantsList

    fun getApplicantsList(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getApplicantListUseCase.invoke(vacancyId)
            _applicantsList.postValue(result)
        }
    }

}