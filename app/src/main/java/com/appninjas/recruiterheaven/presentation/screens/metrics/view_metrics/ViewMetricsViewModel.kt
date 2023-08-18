package com.appninjas.recruiterheaven.presentation.screens.metrics.view_metrics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appninjas.domain.model.Metrics
import com.appninjas.domain.usecase.DeleteMetricsFromDatabaseUseCase
import com.appninjas.domain.usecase.GetMetricsFromDatabaseByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewMetricsViewModel @Inject constructor(private val getMetricsFromDatabaseByIdUseCase: GetMetricsFromDatabaseByIdUseCase,
                                               private val deleteMetricsFromDatabaseUseCase: DeleteMetricsFromDatabaseUseCase): ViewModel() {

    private val _metricsInfo: MutableLiveData<Metrics> = MutableLiveData()
    val metricsInfo: LiveData<Metrics> = _metricsInfo

    fun getMetricsById(metricsId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getMetricsFromDatabaseByIdUseCase.invoke(metricsId)
            _metricsInfo.postValue(result)
        }
    }

    fun deleteMetrics(metricsId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMetricsFromDatabaseUseCase.invoke(metricsId)
        }
    }

}