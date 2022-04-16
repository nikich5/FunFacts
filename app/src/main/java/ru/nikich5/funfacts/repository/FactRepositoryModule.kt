package ru.nikich5.funfacts.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface FactRepositoryModule {

    @Singleton
    @Binds
    fun bindFactRepository(impl: FactRepositoryImpl): FactRepository
}