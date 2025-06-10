package com.shubham.bookshelf.di

import com.shubham.data.logger.DebugLogger
import com.shubham.domain.common.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger {
         return DebugLogger()
    }
}