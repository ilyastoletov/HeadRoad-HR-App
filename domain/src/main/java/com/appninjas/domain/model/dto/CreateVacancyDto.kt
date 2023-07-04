package com.appninjas.domain.model.dto

data class CreateVacancyDto(
    val createdAt: String,
    val department: String,
    val title: String,
    val experience: String,
    val salary: String,
    val requirements: String,
    val conditions: String,
    val job_duties: String,
    val authorId: String
)