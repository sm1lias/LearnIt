package com.smilias.learnit.video_screen

import android.content.Context
import android.util.Size

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.cottacush.android.hiddencam.CaptureTimeFrequency
import com.cottacush.android.hiddencam.HiddenCam
import com.cottacush.android.hiddencam.OnImageCapturedListener
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.smilias.learnit.MainActivity
import com.smilias.learnit.utils.foregroundStartService
import com.smilias.learnit.video_screen.service.ImageCaptureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VideoScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val hiddenCam: HiddenCam
    init {
        val baseStorageFolder = File(context.getExternalFilesDir(null), "HiddenCam").apply {
            if (exists()) deleteRecursively()
            mkdir()
        }

        val imageCapture: OnImageCapturedListener =ImageCaptureModel(context)

        hiddenCam = HiddenCam(
            context, baseStorageFolder,imageCapture,
            CaptureTimeFrequency.Recurring(10 * 2000L),
            targetResolution = Size(1080, 1920)
        )
    }


    var playing by mutableStateOf(false)
        private set
    val exoPlayer = mutableStateOf(
        SimpleExoPlayer.Builder(context).build().apply {
            this.prepare()
            this.playWhenReady = true
            addListener(
                object: Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        playing = isPlaying
                    }
                }
            )
        }
    )

    override fun onCleared() {
        exoPlayer.value.playWhenReady=false
        context.foregroundStartService("Exit")
    }

    fun captureImage(){
        hiddenCam.start()
    }

    fun stopCapturing(){
        hiddenCam.stop()
    }


}
