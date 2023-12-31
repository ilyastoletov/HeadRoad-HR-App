package com.appninjas.data.repository

import com.appninjas.data.network.clients.vacancy.VacancyApiClient
import com.appninjas.domain.enums.VacancyStatus
import com.appninjas.domain.model.Vacancy
import com.appninjas.domain.model.dto.CreateVacancyDto
import com.appninjas.domain.model.dto.UpdateVacancyDto
import com.appninjas.domain.repository.VacancyRepository

class VacancyRepoImpl(private val vacancyApiClient: VacancyApiClient) : VacancyRepository {

    override suspend fun getVacancies(userId: String): ArrayList<Vacancy> {
        return vacancyApiClient.getAllUserVacancies(userId).toCollection(ArrayList())
    }

    override suspend fun createVacancy(vacancy: CreateVacancyDto) {
        vacancyApiClient.createVacancy(vacancy)
    }

    override suspend fun getVacancyDetails(vacancyId: String): Vacancy {
        return vacancyApiClient.getVacancyDetailsById(vacancyId)
    }

    override suspend fun updateVacancyData(vacancyId: String, updatingVacancy: UpdateVacancyDto) {
        vacancyApiClient.updateVacancyData(vacancyId, updatingVacancy)
    }

    override suspend fun deleteVacancy(vacancyId: String) {
        vacancyApiClient.deleteVacancy(vacancyId)
    }

    override suspend fun changeVacancyStatus(vacancyId: String, status: VacancyStatus) {
        vacancyApiClient.changeVacancyStatus(vacancyId, status.toString())
    }

}