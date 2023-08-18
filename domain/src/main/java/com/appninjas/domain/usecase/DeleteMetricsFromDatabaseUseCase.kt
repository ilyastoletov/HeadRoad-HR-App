package com.appninjas.domain.usecase

import com.appninjas.domain.repository.MetricsRepository

class DeleteMetricsFromDatabaseUseCase(private val repository: MetricsRepository) {
    suspend fun invoke(metricsId: Int) = repository.deleteMetricsFromDatabase(metricsId)
}