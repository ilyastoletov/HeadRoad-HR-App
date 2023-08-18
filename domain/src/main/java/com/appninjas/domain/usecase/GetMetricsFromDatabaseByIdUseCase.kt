package com.appninjas.domain.usecase

import com.appninjas.domain.repository.MetricsRepository

class GetMetricsFromDatabaseByIdUseCase(private val repository: MetricsRepository) {
    suspend fun invoke(metricsId: Int) = repository.getMetricsFromDatabaseById(metricsId)
}