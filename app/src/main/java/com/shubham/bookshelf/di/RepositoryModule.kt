package com.shubham.bookshelf.di

import com.shubham.data.remote.api.GutendexApi
import com.shubham.data.repository.BookRepositoryImpl
import com.shubham.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBookRepository(api: GutendexApi): BookRepository = BookRepositoryImpl(api)
}