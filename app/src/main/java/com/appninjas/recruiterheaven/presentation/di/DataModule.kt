package com.appninjas.recruiterheaven.presentation.di

import android.content.Context
import com.appninjas.data.network.clients.applicant.ApplicantApiClient
import com.appninjas.data.network.clients.user.UserApiClient
import com.appninjas.data.network.clients.vacancy.VacancyApiClient
import com.appninjas.data.network.interceptor.LoggingInterceptor
import com.appninjas.data.network.interceptor.TimeoutInterceptor
import com.appninjas.data.repository.ApplicantRepoImpl
import com.appninjas.data.repository.UserRepoImpl
import com.appninjas.data.repository.VacancyRepoImpl
import com.appninjas.domain.repository.ApplicantRepository
import com.appninjas.domain.repository.UserRepository
import com.appninjas.domain.repository.VacancyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(LoggingInterceptor())
            .addInterceptor(TimeoutInterceptor())
            .readTimeout(30, TimeUnit.MINUTES)
            .connectTimeout(30, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.MINUTES)
            .build()

        return Retrofit.Builder()
            .baseUrl("http://95.163.236.27:3000/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiClient = retrofit.create(UserApiClient::class.java)

    @Provides
    @Singleton
    fun provideVacanciesApiService(retrofit: Retrofit): VacancyApiClient = retrofit.create(VacancyApiClient::class.java)

    @Provides
    @Singleton
    fun provideApplicantApiService(retrofit: Retrofit): ApplicantApiClient = retrofit.create(ApplicantApiClient::class.java)

    @Provides
    @Singleton
    fun provideUserRepository(@ApplicationContext appContext: Context, userApiClient: UserApiClient): UserRepository {
        return UserRepoImpl(context = appContext, userApiClient = userApiClient)
    }

    @Provides
    @Singleton
    fun provideVacancyRepository(vacancyApiClient: VacancyApiClient): VacancyRepository = VacancyRepoImpl(vacancyApiClient = vacancyApiClient)

    @Provides
    @Singleton
    fun provideApplicantRepository(applicantApiClient: ApplicantApiClient): ApplicantRepository = ApplicantRepoImpl(applicantApiClient)

}