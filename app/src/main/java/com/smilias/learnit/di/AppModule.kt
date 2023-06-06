package com.smilias.learnit.di

import android.app.Application
import android.content.Context
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smilias.learnit.LearnItApplication
import com.smilias.learnit.domain.use_case.ValidateEmailPasswordUseCase
import com.smilias.learnit.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun providesDataSourceFactory(context: Application): DataSource.Factory{
        return DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

    }

    @Singleton
    @Provides
    fun provideAppFile(context: Application): File {
        return Utils.createBaseFolder(context)
    }
}