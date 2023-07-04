package com.appninjas.domain.repository

import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto

interface VacancyRepository {
    suspend fun getVacancies(userId: String): ArrayList<Vacancy>
    suspend fun createVacancy(vacancy: CreateVacancyDto)
    suspend fun getVacancyDetails(vacancyId: String): Vacancy
}