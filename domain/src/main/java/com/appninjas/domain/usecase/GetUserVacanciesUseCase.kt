package com.appninjas.domain.usecase

import com.appninjas.domain.repository.VacancyRepository

class GetUserVacanciesUseCase(private val repository: VacancyRepository) {
    suspend fun invoke(userId: String) = repository.getVacancies(userId)
}
