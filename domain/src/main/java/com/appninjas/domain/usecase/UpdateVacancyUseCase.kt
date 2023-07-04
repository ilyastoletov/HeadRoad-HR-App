package com.appninjas.domain.usecase

import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.model.dto.UpdateVacancyDto
import com.appninjas.domain.repository.VacancyRepository

class UpdateVacancyUseCase(private val repository: VacancyRepository) {
    suspend fun invoke(vacancyId: String, updatingVacancy: UpdateVacancyDto) = repository.updateVacancyData(vacancyId, updatingVacancy)
}