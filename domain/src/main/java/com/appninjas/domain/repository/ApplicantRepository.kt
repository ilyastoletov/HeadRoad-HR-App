package com.appninjas.domain.repository

import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant

interface ApplicantRepository {
    suspend fun getApplicantsList(vacancyId: String): Map<String, List<Applicant>>
    suspend fun getApplicantInfo(applicantId: String): Applicant
    suspend fun applicantChangeStatus(applicantId: String, status: ApplicantStatus)
}