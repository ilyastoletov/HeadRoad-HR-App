package com.appninjas.domain.model.dto

data class UpdateVacancyDto(
    val department: String,
    val title: String,
    val experience: String,
    val salary: String,
    val requirements: String,
    val conditions: String,
    val job_duties: String,
)
