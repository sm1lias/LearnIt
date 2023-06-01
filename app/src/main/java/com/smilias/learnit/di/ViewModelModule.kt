package com.smilias.learnit.di

import com.smilias.learnit.domain.use_case.ValidateEmailPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideValidateEmailPasswordUseCase(): ValidateEmailPasswordUseCase {
        return ValidateEmailPasswordUseCase()
    }
}