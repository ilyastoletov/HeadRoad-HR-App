package com.appninjas.data.storage.database.metrics

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appninjas.data.storage.dao.metrics.MetricsDao
import com.appninjas.data.storage.model.metrics.MetricsScheme

@Database(entities = [MetricsScheme::class], version = 1, exportSchema = false)
abstract class MetricsDatabase : RoomDatabase() {

    abstract fun getMetricsDao(): MetricsDao

    companion object {
        private var metricsDatabase: MetricsDatabase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): MetricsDatabase = if (metricsDatabase == null) {
            metricsDatabase = Room.databaseBuilder(context, MetricsDatabase::class.java, "metrics_db").fallbackToDestructiveMigration().build()
            metricsDatabase as MetricsDatabase
        } else {
            metricsDatabase as MetricsDatabase
        }
    }

}