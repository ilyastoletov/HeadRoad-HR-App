package com.appninjas.data.network.clients.vacancy

import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.model.dto.UpdateVacancyDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface VacancyApiClient {

    @GET("/vacancy/getAll")
    suspend fun getAllUserVacancies(@Query("user_id") userId: String): Array<Vacancy>

    @POST("/vacancy/create")
    suspend fun createVacancy(@Body vacancy: CreateVacancyDto)

    @GET("/vacancy/getById")
    suspend fun getVacancyDetailsById(@Query("vacancyId") vacancyId: String): Vacancy

    @PATCH("/vacancy/update")
    suspend fun updateVacancyData(@Query("vacancyId") vacancyId: String, @Body updatingVacancy: UpdateVacancyDto)

    @DELETE("/vacancy/delete")
    suspend fun deleteVacancy(@Query("vacancyId") vacancyId: String)

}