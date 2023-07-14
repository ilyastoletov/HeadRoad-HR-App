package com.appninjas.domain.usecase

import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.repository.ApplicantRepository

class ApplicantChangeStatusUseCase(private val applicantRepository: ApplicantRepository) {
    suspend fun invoke(applicantId: String, status: ApplicantStatus) = applicantRepository.applicantChangeStatus(applicantId, status)
}