package com.appninjas.recruiterheaven.presentation.di

import com.appninjas.domain.repository.UserRepository
import com.appninjas.domain.repository.VacancyRepository
import com.appninjas.domain.usecase.CreateVacancyUseCase
import com.appninjas.domain.usecase.DeleteVacancyUseCase
import com.appninjas.domain.usecase.GetUserVacanciesUseCase
import com.appninjas.domain.usecase.GetVacancyDetailsUseCase
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

}