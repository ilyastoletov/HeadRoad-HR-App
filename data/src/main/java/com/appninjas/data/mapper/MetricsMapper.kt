package com.appninjas.data.mapper

import com.appninjas.data.storage.model.metrics.MetricsScheme
import com.appninjas.domain.model.Metrics
import com.appninjas.domain.model.dto.CreateMetricsDto

object MetricsMapper {
    fun mapDtoToEntity(dto: CreateMetricsDto): MetricsScheme = MetricsScheme(
        label = dto.label,
        sortingType = dto.sortingType,
        vacancyTitle = dto.vacancyTitle,
        dateRange = dto.dateRange,
        metricEntries = MetricsMapConverter.mapToString(dto.metricEntries)
    )

    fun schemeToModel(scheme: MetricsScheme): Metrics = Metrics(
        id = scheme.id,
        label = scheme.label,
        sortingType = scheme.sortingType,
        vacancyTitle = scheme.vacancyTitle,
        dateRange = scheme.dateRange,
        metricEntries = MetricsMapConverter.stringToMap(scheme.metricEntries)
    )

    fun schemeListToModelList(schemeList: List<MetricsScheme>): List<Metrics> {
        return schemeList.map { scheme -> Metrics(
            id = scheme.id,
            label = scheme.label,
            sortingType = scheme.sortingType,
            vacancyTitle = scheme.vacancyTitle,
            dateRange = scheme.dateRange,
            metricEntries = MetricsMapConverter.stringToMap(scheme.metricEntries)
        ) }
    }
}