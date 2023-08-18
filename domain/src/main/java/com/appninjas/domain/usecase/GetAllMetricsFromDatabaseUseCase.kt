package com.appninjas.domain.usecase

import com.appninjas.domain.repository.MetricsRepository

class GetAllMetricsFromDatabaseUseCase(private val repository: MetricsRepository) {
    suspend fun invoke() = repository.getAllMetricsFromDatabase()
}