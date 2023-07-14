package com.appninjas.recruiterheaven.presentation.di

import com.appninjas.domain.repository.ApplicantRepository
import com.appninjas.domain.repository.UserRepository
import com.appninjas.domain.repository.VacancyRepository
import com.appninjas.domain.usecase.ApplicantChangeStatusUseCase
import com.appninjas.domain.usecase.ChangeVacancyStatusUseCase
import com.appninjas.domain.usecase.CreateVacancyUseCase
import com.appninjas.domain.usecase.DeleteVacancyUseCase
import com.appninjas.domain.usecase.GetApplicantInfoUseCase
import com.appninjas.domain.usecase.GetApplicantListUseCase
import com.appninjas.domain.usecase.GetUserVacanciesUseCase
import com.appninjas.domain.usecase.GetVacancyDetailsUseCase
import com.appninjas.domain.usecase.GetVacancyTitleUseCase
import com.appninjas.domain.usecase.LoginUseCase
import com.appninjas.domain.usecase.UpdateVacancyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideLoginUseCase(userRepository: UserRepository): LoginUseCase {
        return LoginUseCase(userRepository)
    }

    @Provides
    fun provideGetVacanciesUseCase(vacancyRepository: VacancyRepository): GetUserVacanciesUseCase {
        return GetUserVacanciesUseCase(repository = vacancyRepository)
    }

    @Provides
    fun provideCreateVacancyUseCase(vacancyRepository: VacancyRepository): CreateVacancyUseCase {
        return CreateVacancyUseCase(vacancyRepository)
    }

    @Provides
    fun provideGetVacancyDetailsUseCase(vacancyRepository: VacancyRepository): GetVacancyDetailsUseCase {
        return GetVacancyDetailsUseCase(vacancyRepository)
    }

    @Provides
    fun provideUpdateVacancyUseCase(vacancyRepository: VacancyRepository): UpdateVacancyUseCase {
        return UpdateVacancyUseCase(vacancyRepository)
    }

    @Provides
    fun provideDeleteVacancyUseCase(vacancyRepository: VacancyRepository): DeleteVacancyUseCase {
        return DeleteVacancyUseCase(vacancyRepository)
    }

    @Provides
    fun provideChangeVacancyStatusUseCase(vacancyRepository: VacancyRepository): ChangeVacancyStatusUseCase {
        return ChangeVacancyStatusUseCase(vacancyRepository)
    }

    @Provides
    fun provideGetApplicantsListUseCase(applicantRepository: ApplicantRepository): GetApplicantListUseCase {
        return GetApplicantListUseCase(applicantRepository)
    }

    @Provides
    fun provideGetApplicantInfoUseCase(applicantRepository: ApplicantRepository): GetApplicantInfoUseCase {
        return GetApplicantInfoUseCase(applicantRepository)
    }

    @Provides
    fun provideGetVacancyTitleUseCase(vacancyRepository: VacancyRepository): GetVacancyTitleUseCase {
        return GetVacancyTitleUseCase(vacancyRepository)
    }

    @Provides
    fun provideApplicantChangeStatusUseCase(applicantRepository: ApplicantRepository): ApplicantChangeStatusUseCase {
        return ApplicantChangeStatusUseCase(applicantRepository)
    }

}