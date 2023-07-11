package com.appninjas.domain.repository

import com.appninjas.domain.model.Applicant

interface ApplicantRepository {
    suspend fun getApplicantsList(vacancyId: String): Map<String, List<Applicant>>
}