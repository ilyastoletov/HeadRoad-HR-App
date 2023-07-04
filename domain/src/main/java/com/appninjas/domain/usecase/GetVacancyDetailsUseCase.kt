package com.appninjas.domain.usecase

import com.appninjas.domain.repository.VacancyRepository

class GetVacancyDetailsUseCase(private val repository: VacancyRepository) {
    suspend fun invoke(vacancyId: String) = repository.getVacancyDetails(vacancyId)
}