package com.andromesh.movieslistassignment.di

import com.andromesh.movieslist.api.MoviesService
import com.andromesh.movieslist.di.CoroutineScropeIO
import com.andromesh.movieslist.di.CoroutineScropeSupervisor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getRetroServiceInstance(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }

    @Singleton
    @Provides
    fun getRetroInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MoviesService.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

    @CoroutineScropeSupervisor
    @Provides
    fun provideCoroutineScropeSupervisor() = CoroutineScope(SupervisorJob())

}
