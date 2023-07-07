package com.appninjas.data.network.clients.vacancy.dto

import com.appninjas.domain.model.Vacancy

data class GetAllVacanciesDto(
    val vacancy: List<Vacancy>,
    val newApplicantsCount: Int
)
