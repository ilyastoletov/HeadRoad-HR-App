package com.appninjas.domain.repository

import com.appninjas.domain.model.Vacancy

interface VacancyRepository {
    suspend fun getVacancies(userId: String): ArrayList<Vacancy>
}