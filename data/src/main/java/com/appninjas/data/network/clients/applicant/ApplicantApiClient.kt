package com.appninjas.data.network.clients.applicant

import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface ApplicantApiClient {

    @GET("/applicant/getAll")
    suspend fun getApplicantsListByVacancyId(@Query("vacancyId") vacancyId: String): Array<Applicant>

    @GET("/applicant/getById")
    suspend fun getApplicantInfo(@Query("applicantId") applicantId: String): Applicant

    @PATCH("/applicant/changeStatus")
    suspend fun applicantChangeStatus(@Query("applicantId") applicantId: String, @Query("status") status: String)

    @GET("/applicant/getByPage")
    suspend fun getApplicantsByPage(@Query("vacancyId") vacancyId: String, @Query("page") page: Int, @Query("status") status: ApplicantStatus): Array<Applicant>

}