package com.appninjas.data.network.clients.vacancy

import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VacancyApiClient {

    @GET("/vacancy/getAll")
    suspend fun getAllUserVacancies(@Query("user_id") userId: String): Array<Vacancy>

    @POST("/vacancy/create")
    suspend fun createVacancy(@Body vacancy: CreateVacancyDto)

    @GET("/vacancy/getById")
    suspend fun getVacancyDetailsById(@Query("vacancyId") vacancyId: String): Vacancy

}