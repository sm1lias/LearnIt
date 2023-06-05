package com.smilias.learnit.di

import android.app.Application
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.smilias.learnit.domain.use_case.ValidateEmailPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideValidateEmailPasswordUseCase(): ValidateEmailPasswordUseCase {
        return ValidateEmailPasswordUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideExoPlayer(context: Application): ExoPlayer{
        return SimpleExoPlayer.Builder(context).build()
    }
}