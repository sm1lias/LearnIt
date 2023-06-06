package com.smilias.learnit.video_screen

import android.content.Context
import android.net.Uri
import android.util.Size

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cottacush.android.hiddencam.CaptureTimeFrequency
import com.cottacush.android.hiddencam.HiddenCam
import com.cottacush.android.hiddencam.OnImageCapturedListener
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smilias.learnit.VideoList
import com.smilias.learnit.menu_screen.LocationLiveData
import com.smilias.learnit.utils.Utils
import com.smilias.learnit.utils.foregroundStartService
import com.smilias.learnit.video_screen.model.PhotoInfo
import com.smilias.learnit.video_screen.service.ImageCaptureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VideoScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    val exoPlayer: ExoPlayer,
    private val hiddenCam: HiddenCam,
    private val captureInfo: PhotoInfo,
    private val locationLiveData: LocationLiveData,
    dataSourceFactory: DataSource.Factory
) : ViewModel() {

    var state by mutableStateOf(VideoScreenState())
        private set

    init {
        locationLiveData.startLocationUpdates()
        state = state.copy(videoUrl = savedStateHandle.get<String>("url")?:"https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                Uri.parse(
                    // Big Buck Bunny from Blender Project
                    state.videoUrl
                )
            )
        getVideoTitleFromUrl()
        exoPlayer.prepare(source)

        exoPlayer.addListener(
            object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    state = state.copy(playing = isPlaying)
                }
            }
        )



    }

    private fun getVideoTitleFromUrl() {
        captureInfo.title = VideoList.getVideoList().stream()
            .filter { it.source == state.videoUrl }
            .findFirst()
            .map { it.title }
            .orElse("")
            .toString()
        captureInfo.location = locationLiveData
    }

    fun onEvent(event: VideoScreenEvent) {
        when (event) {
            is VideoScreenEvent.OnStartCapture -> startCapturing()
            is VideoScreenEvent.OnStopCapture -> stopCapturing()
        }
    }


    override fun onCleared() {
        exoPlayer.playWhenReady = false
        context.foregroundStartService("Exit")
        hiddenCam.stop()
    }

    private fun startCapturing() {
        hiddenCam.start()
    }

    private fun stopCapturing() {
        hiddenCam.stop()
    }


}
