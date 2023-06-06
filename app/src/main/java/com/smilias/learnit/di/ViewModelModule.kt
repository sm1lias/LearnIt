package com.smilias.learnit.di

import android.app.Application
import android.util.Size
import com.cottacush.android.hiddencam.CaptureTimeFrequency
import com.cottacush.android.hiddencam.HiddenCam
import com.cottacush.android.hiddencam.OnImageCapturedListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.auth.FirebaseAuth
import com.smilias.learnit.domain.use_case.ValidateEmailPasswordUseCase
import com.smilias.learnit.menu_screen.LocationLiveData
import com.smilias.learnit.utils.Utils
import com.smilias.learnit.video_screen.model.PhotoInfo
import com.smilias.learnit.video_screen.service.ImageCaptureModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.File

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
        return SimpleExoPlayer.Builder(context).build().apply {
            this.prepare()
            this.playWhenReady = true
        }
    }

    @Provides
    @ViewModelScoped
    fun providePhotoInfo(auth: FirebaseAuth, exoPlayer: ExoPlayer): PhotoInfo{
        return PhotoInfo(auth.currentUser, exoPlayer)
    }

    @Provides
    @ViewModelScoped
    fun provideOnImageCapturedListener(context: Application, captureInfo: PhotoInfo): OnImageCapturedListener {
        return ImageCaptureModel(context, captureInfo)
    }


    @Provides
    @ViewModelScoped
    fun provideHiddenCam(context: Application, folder: File, imageCapture: OnImageCapturedListener): HiddenCam {
        return HiddenCam(
            context, folder, imageCapture,
            CaptureTimeFrequency.Recurring(10 * 2000L),
            targetResolution = Size(1080, 1920),
        )
    }

    @Provides
    @ViewModelScoped
    fun provideLocationLiveData(context: Application): LocationLiveData{
        return LocationLiveData(context)
    }
}