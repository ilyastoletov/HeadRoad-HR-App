package com.appninjas.recruiterheaven.presentation.screens.vacancies.create_vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.model.dto.UpdateVacancyDto
import com.appninjas.domain.usecase.CreateVacancyUseCase
import com.appninjas.domain.usecase.GetVacancyDetailsUseCase
import com.appninjas.domain.usecase.UpdateVacancyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateVacancyViewModel @Inject constructor(private val createVacancyUseCase: CreateVacancyUseCase,
                                                 private val getVacancyDetailsUseCase: GetVacancyDetailsUseCase,
                                                 private val updateVacancyUseCase: UpdateVacancyUseCase): ViewModel() {

    private val _vacancyDetails: MutableLiveData<Vacancy> = MutableLiveData()
    val vacancyDetails: LiveData<Vacancy> = _vacancyDetails

    fun createVacancy(vacancyDto: CreateVacancyDto) {
        viewModelScope.launch(Dispatchers.IO) {
            createVacancyUseCase.invoke(vacancyDto)
        }
    }

    fun getVacancyDetails(vacancyId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getVacancyDetailsUseCase.invoke(vacancyId)
            _vacancyDetails.postValue(result)
        }
    }

    fun updateVacancy(vacancyId: String, vacancyData: UpdateVacancyDto) {
        viewModelScope.launch(Dispatchers.IO) {
            updateVacancyUseCase.invoke(vacancyId, vacancyData)
        }
    }

}