package com.shubham.bookshelf.di

import com.shubham.domain.common.Logger
import com.shubham.domain.repository.BookRepository
import com.shubham.domain.usecase.GetBookByIdUseCase
import com.shubham.domain.usecase.GetBooksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBookUseCases(repository: BookRepository, logger: Logger): GetBooksUseCase =
        GetBooksUseCase(repository, logger)

    @Provides
    @Singleton
    fun provideGetBookByIdsUseCases(repository: BookRepository, logger: Logger): GetBookByIdUseCase =
        GetBookByIdUseCase(repository, logger)

}