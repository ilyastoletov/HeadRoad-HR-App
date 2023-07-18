package com.appninjas.domain.usecase

import com.appninjas.domain.enums.ApplicantStatus
import com.appninjas.domain.repository.ApplicantRepository

class GetApplicantsListByPageUseCase(private val repository: ApplicantRepository) {
    suspend fun invoke(vacancyId: String, pageNumber: Int, status: ApplicantStatus) = repository.getApplicantsByPage(vacancyId, pageNumber, status)
}