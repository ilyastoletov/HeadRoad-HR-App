package com.appninjas.domain.usecase

import com.appninjas.domain.repository.VacancyRepository

class DeleteVacancyUseCase(private val repository: VacancyRepository) {
    suspend fun invoke(vacancyId: String) = repository.deleteVacancy(vacancyId)
}