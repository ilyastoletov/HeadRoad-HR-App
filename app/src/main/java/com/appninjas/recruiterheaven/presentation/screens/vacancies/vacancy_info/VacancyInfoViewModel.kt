package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.usecase.GetVacancyDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VacancyInfoViewModel @Inject constructor(private val getVacancyDetailsUseCase: GetVacancyDetailsUseCase): ViewModel() {

    private val _vacancyTitle: MutableLiveData<String> = MutableLiveData()
    val vacancyTitle: LiveData<String> = _vacancyTitle

    fun getVacancyTitle(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val vacancyDetails = getVacancyDetailsUseCase.invoke(vacancyId)
            _vacancyTitle.postValue(vacancyDetails.title)
        }
    }

}