package com.appninjas.recruiterheaven.presentation.screens.applicant.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.usecase.GetApplicantInfoUseCase
import com.appninjas.domain.usecase.GetVacancyDetailsUseCase
import com.appninjas.domain.usecase.GetVacancyTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantProfileViewModel @Inject constructor(private val getApplicantInfoUseCase: GetApplicantInfoUseCase,
                                                    private val getVacancyTitleUseCase: GetVacancyTitleUseCase): ViewModel() {

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

    fun getVacancyTitle(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getVacancyTitleUseCase.invoke(vacancyId)
            _vacancyTitle.postValue(result)
        }
    }

}