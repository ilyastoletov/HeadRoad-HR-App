package com.appninjas.recruiterheaven.presentation.screens.vacancies.vacancy_info.info_subfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.usecase.ChangeVacancyStatusUseCase
import com.appninjas.domain.usecase.GetVacancyDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val getVacancyDetailsUseCase: GetVacancyDetailsUseCase,
                                        private val changeVacancyStatusUseCase: ChangeVacancyStatusUseCase): ViewModel() {

    private val _vacancyDetails: MutableLiveData<Vacancy> = MutableLiveData()
    val vacancyDetails: LiveData<Vacancy> = _vacancyDetails

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val vacancyDetailsResult = getVacancyDetailsUseCase.invoke(vacancyId)
            _vacancyDetails.postValue(vacancyDetailsResult)
        }
    }

    fun changeVacancyStatus(vacancyId: String, status: VacancyStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            changeVacancyStatusUseCase.invoke(vacancyId, status)
        }
    }

}