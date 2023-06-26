package com.appninjas.recruiterheaven.presentation.screens.vacancies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.usecase.GetUserVacanciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VacanciesViewModel @Inject constructor(private val getUserVacanciesUseCase: GetUserVacanciesUseCase) : ViewModel() {

    private val _vacanciesList: MutableLiveData<ArrayList<Vacancy>> = MutableLiveData()
    val vacanciesList: LiveData<ArrayList<Vacancy>> = _vacanciesList

    fun getVacancies(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _vacanciesList.postValue(getUserVacanciesUseCase.invoke(userId))
        }
    }

}