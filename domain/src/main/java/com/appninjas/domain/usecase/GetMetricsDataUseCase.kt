package com.appninjas.domain.usecase

import com.appninjas.domain.repository.MetricsRepository

class GetMetricsDataUseCase(private val repository: MetricsRepository) {
    suspend fun invoke(startDate: String, endDate: String): Map<String, Int> = repository.buildMetrics(startDate, endDate)
}