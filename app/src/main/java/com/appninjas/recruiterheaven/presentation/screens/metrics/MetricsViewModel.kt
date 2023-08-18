package com.appninjas.recruiterheaven.presentation.screens.metrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Metrics
import com.appninjas.domain.usecase.GetAllMetricsFromDatabaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetricsViewModel @Inject constructor(private val getAllMetricsFromDatabaseUseCase: GetAllMetricsFromDatabaseUseCase): ViewModel() {

    private val _metricsList: MutableLiveData<List<Metrics>> = MutableLiveData()
    val metricsList: LiveData<List<Metrics>> = _metricsList

    fun getMetrics() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAllMetricsFromDatabaseUseCase.invoke()
            _metricsList.postValue(result)
        }
    }

}