package com.appninjas.recruiterheaven.presentation.screens.metrics.create_metrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Metrics
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateMetricsDto
import com.appninjas.domain.usecase.GetAllMetricsFromDatabaseUseCase
import com.appninjas.domain.usecase.GetMetricsDataUseCase
import com.appninjas.domain.usecase.GetUserVacanciesUseCase
import com.appninjas.domain.usecase.SaveMetricsToDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMetricsViewModel @Inject constructor(private val getUserVacanciesUseCase: GetUserVacanciesUseCase,
                                                 private val getMetricsDataUseCase: GetMetricsDataUseCase,
                                                 private val saveMetricsToDatabaseUseCase: SaveMetricsToDatabaseUseCase) : ViewModel() {

    private val _userVacancies: MutableLiveData<List<Vacancy>> = MutableLiveData()
    val userVacancies: LiveData<List<Vacancy>> = _userVacancies

    private val _metricsData: MutableLiveData<Map<String, Int>> = MutableLiveData()
    val metricsData: LiveData<Map<String, Int>> = _metricsData

    fun getUserVacancies(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUserVacanciesUseCase.invoke(userId)
            _userVacancies.postValue(result)
        }
    }

    fun getMetricsData(startDate: String, endDate: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getMetricsDataUseCase.invoke(startDate, endDate)
            _metricsData.postValue(result)
        }
    }

    fun saveMetricsToDatabase(model: CreateMetricsDto) {
        viewModelScope.launch(Dispatchers.IO) {
            saveMetricsToDatabaseUseCase.invoke(model)
        }
    }

}