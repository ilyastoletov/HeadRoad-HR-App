package com.appninjas.domain.model

import com.appninjas.domain.enums.VacancyStatus

data class Vacancy(
    val vacancyId: String,
    val vacancyStatus: VacancyStatus,
    val createdAt: String,
    val department: String,
    val title: String,
    val experience: String,
    val salary: String,
    val requirements: String,
    val conditions: String,
    val job_duties: String,
    val authorId: String,
    val responderIds: List<String>
)
