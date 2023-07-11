package com.appninjas.domain.usecase

import com.appninjas.domain.model.Applicant
import com.appninjas.domain.repository.ApplicantRepository

class GetApplicantListUseCase(private val repository: ApplicantRepository) {
    suspend fun invoke(vacancyId: String): Map<String, List<Applicant>> = repository.getApplicantsList(vacancyId)
}