package com.mtfuji.sakura.openlibtest.domain.usecase

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class GetCurrentlyReadingBooksUseCaseModule {
    @Binds
    abstract fun bindGetCurrentlyReadingBooksUseCase(
        getCurrentlyReadingBooksUseCaseImpl: GetCurrentlyReadingBooksUseCaseImpl
    ): GetCurrentlyReadingBooksUseCase
}