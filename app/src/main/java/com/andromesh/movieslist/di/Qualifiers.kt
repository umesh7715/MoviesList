package com.andromesh.movieslist.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MovieAPI

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeIO

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class CoroutineScropeSupervisor

