package com.appninjas.domain.usecase

import com.appninjas.domain.repository.ApplicantRepository

class SearchApplicantsUseCase(private val repository: ApplicantRepository) {
    suspend fun invoke(query: String, city: String?, fullWorkDay: Boolean, wantedSalaryBottom: String?, wantedSalaryTop: String?) = repository.searchApplicants(query, city, fullWorkDay, wantedSalaryBottom, wantedSalaryTop)
}