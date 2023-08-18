package com.appninjas.data.network.clients.metrics

import retrofit2.http.GET
import retrofit2.http.Query

interface MetricsApiClient {

    @GET("metrics/build")
    suspend fun buildMetrics(@Query("dateFrom") startDate: String, @Query("dateTo") endDate: String): Map<String, Int>

}