package com.appninjas.recruiterheaven.presentation.screens.applicant.set_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.usecase.ApplicantChangeStatusUseCase
import com.appninjas.domain.usecase.GetApplicantInfoUseCase
import com.appninjas.domain.usecase.GetVacancyTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantSetStatusViewModel @Inject constructor(private val getApplicantInfoUseCase: GetApplicantInfoUseCase,
                                                      private val getVacancyTitleUseCase: GetVacancyTitleUseCase,
                                                      private val applicantChangeStatusUseCase: ApplicantChangeStatusUseCase) : ViewModel() {

    private val _applicantDetails: MutableLiveData<Applicant> = MutableLiveData()
    val applicantDetails: LiveData<Applicant> = _applicantDetails

    private val _vacancyTitle: MutableLiveData<String> = MutableLiveData()
    val vacancyTitle: LiveData<String> = _vacancyTitle

    fun getApplicantDetails(applicantId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getApplicantInfoUseCase.invoke(applicantId)
            _applicantDetails.postValue(result)
        }
    }

    fun getVacancyTitle(appliedVacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getVacancyTitleUseCase.invoke(appliedVacancyId)
            _vacancyTitle.postValue(result)
        }
    }

    fun applicantChangeStatus(applicantId: String, status: ApplicantStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            applicantChangeStatusUseCase.invoke(applicantId, status)
        }
    }

}