package com.appninjas.data.network.clients.applicant

import com.appninjas.domain.model.Applicant
import retrofit2.http.GET
import retrofit2.http.Query

interface ApplicantApiClient {

    @GET("/applicant/getAll")
    suspend fun getApplicantsListByVacancyId(@Query("vacancyId") vacancyId: String): Array<Applicant>

}