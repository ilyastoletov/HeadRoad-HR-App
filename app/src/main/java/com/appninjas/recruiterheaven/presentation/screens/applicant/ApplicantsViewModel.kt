package com.appninjas.recruiterheaven.presentation.screens.applicant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.usecase.SearchApplicantsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApplicantsViewModel @Inject constructor(private val searchApplicantsUseCase: SearchApplicantsUseCase): ViewModel() {

    private val _foundApplicants: MutableLiveData<List<Applicant>?> = MutableLiveData()
    val foundApplicants: LiveData<List<Applicant>?> = _foundApplicants

    fun searchApplicants(searchQuery: String, city: String?, fullWorkDay: Boolean, wantedSalaryBottom: String?, wantedSalaryTop: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = searchApplicantsUseCase.invoke(searchQuery, city, fullWorkDay, wantedSalaryBottom, wantedSalaryTop)
            _foundApplicants.postValue(result)
        }
    }

}