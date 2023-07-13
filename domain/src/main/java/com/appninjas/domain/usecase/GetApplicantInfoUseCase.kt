package com.appninjas.domain.usecase

import com.appninjas.domain.repository.ApplicantRepository

class GetApplicantInfoUseCase(private val repository: ApplicantRepository) {
    suspend fun invoke(applicantId: String) = repository.getApplicantInfo(applicantId)
}