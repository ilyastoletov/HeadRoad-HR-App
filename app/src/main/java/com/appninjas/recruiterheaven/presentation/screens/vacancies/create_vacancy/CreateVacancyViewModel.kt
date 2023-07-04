package com.appninjas.recruiterheaven.presentation.screens.vacancies.create_vacancy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.usecase.CreateVacancyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateVacancyViewModel @Inject constructor(private val createVacancyUseCase: CreateVacancyUseCase): ViewModel() {

    fun createVacancy(vacancyDto: CreateVacancyDto) {
        viewModelScope.launch(Dispatchers.IO) {
            createVacancyUseCase.invoke(vacancyDto)
        }
    }

}