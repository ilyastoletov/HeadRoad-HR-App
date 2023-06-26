package com.appninjas.data.network.clients.vacancy

import com.appninjas.domain.model.Vacancy
import retrofit2.http.GET
import retrofit2.http.Query

interface VacancyApiClient {

    @GET("/vacancy/getAll")
    suspend fun getAllUserVacancies(@Query("user_id") userId: String): Array<Vacancy>

}