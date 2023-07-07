package com.appninjas.data.mapper

import com.appninjas.data.network.clients.vacancy.dto.GetAllVacanciesDto
import com.appninjas.domain.model.Vacancy

class VacancyMapper {
    fun mapAllVacanciesDtoToList(dto: GetAllVacanciesDto): ArrayList<Vacancy> = dto.vacancy.map { Vacancy(
        id = it.id,
        vacancyId = it.vacancyId,
        vacancyStatus = it.vacancyStatus,
        createdAt = it.createdAt,
        department = it.department,
        title = it.title,
        experience = it.experience,
        salary = it.salary,
        requirements = it.requirements,
        conditions = it.conditions,
        job_duties = it.job_duties,
        authorId = it.authorId,
        newApplicantsCount = dto.newApplicantsCount,
        responderIds = it.responderIds
    ) }.toCollection(ArrayList())
}