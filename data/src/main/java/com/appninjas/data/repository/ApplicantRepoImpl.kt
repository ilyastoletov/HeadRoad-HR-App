package com.appninjas.data.repository

import com.appninjas.data.network.clients.applicant.ApplicantApiClient
import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant
import com.appninjas.domain.repository.ApplicantRepository

class ApplicantRepoImpl(private val apiClient: ApplicantApiClient) : ApplicantRepository {

    override suspend fun getApplicantsList(vacancyId: String): Map<String, List<Applicant>> {
        val applicantsList = apiClient.getApplicantsListByVacancyId(vacancyId)
        return mapOf(
            "Новые" to applicantsList.filter { it.status == ApplicantStatus.NEW },
            "Тестовое задание" to applicantsList.filter { it.status == ApplicantStatus.TEST_TASK },
            "Предвар. интервью" to applicantsList.filter { it.status == ApplicantStatus.PHONE_INTERVIEW },
            "Тех. интервью" to applicantsList.filter { it.status == ApplicantStatus.TECH_INTERVIEW },
            "Оффер" to applicantsList.filter { it.status == ApplicantStatus.OFFER },
            "Онбординг" to applicantsList.filter { it.status == ApplicantStatus.ONBOARDING },
            "Отказ кандидата" to applicantsList.filter { it.status == ApplicantStatus.APPLICANT_DECLINE },
            "Отказ рекрутера" to applicantsList.filter { it.status == ApplicantStatus.RECRUITER_DECLINE }
        )
    }


    override suspend fun getApplicantInfo(applicantId: String): Applicant {
        return apiClient.getApplicantInfo(applicantId)
    }

    override suspend fun applicantChangeStatus(applicantId: String, status: ApplicantStatus) {
        apiClient.applicantChangeStatus(applicantId, status.toString())
    }

}