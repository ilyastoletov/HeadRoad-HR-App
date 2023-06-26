package com.appninjas.domain.model

import com.appninjas.domain.enums.ApplicantStatus

data class Applicant(
    val applicantId: String,
    val appliedVacancyId: String,
    val appliedAt: String,
    val name: String,
    val experience: String,
    val education: String,
    val wanted_salary: String,
    val city: String,
    val status: ApplicantStatus,
    val photo_url: String,
    val resume_url: String,
    val social_media_links: List<String>
)
