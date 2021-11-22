package com.smilias.learnit.di

import android.content.Context
import com.smilias.learnit.LearnItApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object AppModule {

    @Provides
    @ActivityScoped
    fun provideContext(@ApplicationContext app: Context): LearnItApplication {
        return app as LearnItApplication }
}