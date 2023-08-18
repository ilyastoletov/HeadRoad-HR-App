package com.appninjas.data.repository

import com.appninjas.data.mapper.MetricsMapper
import com.appninjas.data.network.clients.metrics.MetricsApiClient
import com.appninjas.data.storage.dao.metrics.MetricsDao
import com.appninjas.domain.model.Metrics
import com.appninjas.domain.model.dto.CreateMetricsDto
import com.appninjas.domain.repository.MetricsRepository

class MetricsRepoImpl(private val metricsApiClient: MetricsApiClient,
                      private val metricsDao: MetricsDao) : MetricsRepository {

    override suspend fun buildMetrics(startDate: String, endDate: String): Map<String, Int> {
        return metricsApiClient.buildMetrics(startDate, endDate)
    }

    override suspend fun saveMetricsToDatabase(model: CreateMetricsDto) {
        metricsDao.insertMetrics(MetricsMapper.mapDtoToEntity(model))
    }

    override suspend fun deleteMetricsFromDatabase(metricsId: Int) {
        metricsDao.deleteMetrics(metricsId)
    }

    override suspend fun getMetricsFromDatabaseById(metricsId: Int): Metrics {
        return MetricsMapper.schemeToModel(metricsDao.getMetrics(metricsId))
    }

    override suspend fun getAllMetricsFromDatabase(): List<Metrics> {
        return MetricsMapper.schemeListToModelList(metricsDao.getAllMetrics())
    }

}