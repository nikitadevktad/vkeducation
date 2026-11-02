package com.nikitadevktad.vkeducation.di

import com.nikitadevktad.vkeducation.data.repository.AppRepositoryImpl
import com.nikitadevktad.vkeducation.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAppRepository(implementation: AppRepositoryImpl): AppRepository
}
