package com.nikitadevktad.vkeducation.di

import com.nikitadevktad.vkeducation.data.repository.GreetingRepository
import com.nikitadevktad.vkeducation.data.repository.GreetingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGreetingRepository(impl: GreetingRepositoryImpl): GreetingRepository
}
