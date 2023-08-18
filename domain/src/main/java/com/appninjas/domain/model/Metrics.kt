package com.appninjas.domain.model

data class Metrics(
    val id: Int,
    val label: String,
    val sortingType: String,
    val vacancyTitle: String,
    val dateRange: String,
    val metricEntries: Map<String, Int>
)
