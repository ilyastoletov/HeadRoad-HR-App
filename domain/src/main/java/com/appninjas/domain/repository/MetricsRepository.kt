package com.appninjas.domain.repository

import com.appninjas.domain.model.Metrics
import com.appninjas.domain.model.dto.CreateMetricsDto

interface MetricsRepository {
    suspend fun buildMetrics(startDate: String, endDate: String): Map<String, Int>
    suspend fun saveMetricsToDatabase(model: CreateMetricsDto)
    suspend fun deleteMetricsFromDatabase(metricsId: Int)
    suspend fun getMetricsFromDatabaseById(metricsId: Int): Metrics
    suspend fun getAllMetricsFromDatabase(): List<Metrics>
}