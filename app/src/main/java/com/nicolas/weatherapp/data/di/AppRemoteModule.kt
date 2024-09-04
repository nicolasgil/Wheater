package com.nicolas.weatherapp.data.di


import com.nicolas.weatherapp.data.datasources.remote.LocationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @ApiUrl
    fun provideBaseUrl(): String = "https://api.weatherapi.com/v1/"

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder().addInterceptor(this).addInterceptor(MyInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@ApiUrl baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): LocationService {
        return retrofit.create(LocationService::class.java)
    }
}