package com.appninjas.recruiterheaven.presentation.di

import com.appninjas.domain.repository.UserRepository
import com.appninjas.domain.repository.VacancyRepository
import com.appninjas.domain.usecase.GetUserVacanciesUseCase
import com.appninjas.domain.usecase.LoginUseCase
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

}