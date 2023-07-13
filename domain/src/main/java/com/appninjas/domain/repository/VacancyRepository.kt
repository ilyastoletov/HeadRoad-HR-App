package com.appninjas.domain.repository

import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.model.dto.UpdateVacancyDto

interface VacancyRepository {
    suspend fun getVacancies(userId: String): ArrayList<Vacancy>
    suspend fun createVacancy(vacancy: CreateVacancyDto)
    suspend fun getVacancyDetails(vacancyId: String): Vacancy
    suspend fun updateVacancyData(vacancyId: String, updatingVacancy: UpdateVacancyDto)
    suspend fun deleteVacancy(vacancyId: String)
    suspend fun changeVacancyStatus(vacancyId: String, status: VacancyStatus)
}