package com.appninjas.domain.usecase

import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.domain.repository.VacancyRepository

class ChangeVacancyStatusUseCase(private val repository: VacancyRepository) {
    suspend fun invoke(vacancyId: String, status: VacancyStatus) = repository.changeVacancyStatus(vacancyId, status)
}