package com.appninjas.data.storage.dao.metrics

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appninjas.data.storage.model.metrics.MetricsScheme

@Dao
interface MetricsDao {

    @Insert(entity = MetricsScheme::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetrics(model: MetricsScheme)

    @Query("SELECT * FROM metrics WHERE id = :metricsId")
    suspend fun getMetrics(metricsId: Int): MetricsScheme

    @Query("DELETE FROM metrics WHERE id = :metricsId")
    suspend fun deleteMetrics(metricsId: Int)

    @Query("SELECT * FROM metrics")
    suspend fun getAllMetrics(): List<MetricsScheme>

}