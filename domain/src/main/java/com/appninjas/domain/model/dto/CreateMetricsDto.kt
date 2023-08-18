package com.appninjas.domain.model.dto

data class CreateMetricsDto(
    val label: String = "",
    val sortingType: String = "",
    val vacancyTitle: String = "",
    val dateRange: String = "",
    val metricEntries: Map<String, Int>
)