package com.appninjas.domain.repository

import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.model.Applicant

interface ApplicantRepository {
    suspend fun getApplicantsList(vacancyId: String): Map<String, List<Applicant>>
    suspend fun getApplicantInfo(applicantId: String): Applicant
    suspend fun applicantChangeStatus(applicantId: String, status: ApplicantStatus)
    suspend fun getApplicantsByPage(vacancyID: String, pageNumber: Int, status: ApplicantStatus): List<Applicant>
    suspend fun searchApplicants(searchQuery: String, city: String?, fullWorkDay: Boolean, wantedSalaryBottom: String?, wantedSalaryTop: String?): List<Applicant>?
}