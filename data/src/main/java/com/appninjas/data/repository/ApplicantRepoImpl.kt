package com.appninjas.data.repository

import com.appninjas.data.network.clients.applicant.ApplicantApiClient
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.repository.ApplicantRepository

class ApplicantRepoImpl(private val apiClient: ApplicantApiClient) : ApplicantRepository {

    override suspend fun getApplicantsList(vacancyId: String): Map<String, List<Applicant>> {
        val applicantsList = apiClient.getApplicantsListByVacancyId(vacancyId)
        return mapOf(
            "new" to applicantsList.filter { it.status == ApplicantStatus.NEW }
        )
    }

}