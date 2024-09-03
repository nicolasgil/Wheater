package com.nicolas.weatherapp.data.di

import com.nicolas.weatherapp.data.datasources.local.SearchHistoryDataSource
import com.nicolas.weatherapp.data.datasources.local.SearchHistoryDataSourceImpl
import com.nicolas.weatherapp.data.datasources.remote.LocationRemoteDataSourceImpl
import com.nicolas.weatherapp.data.datasources.remote.RemoteDataSource
import com.nicolas.weatherapp.data.repositories.LocationRepository
import com.nicolas.weatherapp.data.repositories.LocationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocationRemoteDataSource(locationRemoteDataSourceImpl: LocationRemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindSearchHistoryDataSource(searchHistoryDataSourceImpl: SearchHistoryDataSourceImpl): SearchHistoryDataSource

    @Binds
    abstract fun bindLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository

}