package com.appninjas.domain.usecase

import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.repository.VacancyRepository

class CreateVacancyUseCase(private val repository: VacancyRepository) {
    suspend fun invoke(vacancyDto: CreateVacancyDto) = repository.createVacancy(vacancyDto)
}