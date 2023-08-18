package com.appninjas.domain.usecase

import com.appninjas.domain.model.dto.CreateMetricsDto
import com.appninjas.domain.repository.MetricsRepository

class SaveMetricsToDatabaseUseCase(private val repository: MetricsRepository) {
    suspend fun invoke(model: CreateMetricsDto) = repository.saveMetricsToDatabase(model)
}