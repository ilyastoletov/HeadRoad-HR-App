package com.appninjas.data.storage.model.metrics

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metrics")
data class MetricsScheme(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo
    val label: String = "",

    @ColumnInfo
    val sortingType: String = "",

    @ColumnInfo
    val vacancyTitle: String = "",

    @ColumnInfo
    val dateRange: String = "",

    @ColumnInfo
    val metricEntries: String = ""
)
