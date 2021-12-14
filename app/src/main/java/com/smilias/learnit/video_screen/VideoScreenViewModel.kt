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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smilias.learnit.utils.Utils
import com.smilias.learnit.utils.foregroundStartService
import com.smilias.learnit.video_screen.model.PhotoInfo
import com.smilias.learnit.video_screen.service.ImageCaptureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VideoScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    val exoPlayer = mutableStateOf(
        SimpleExoPlayer.Builder(context).build().apply {
            this.prepare()
            this.playWhenReady = true
            addListener(
                object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        playing = isPlaying
                    }
                }
            )
        }
    )
    private val captureInfo:PhotoInfo= PhotoInfo(Firebase.auth.currentUser, exoPlayer)

    private val imageCapture: OnImageCapturedListener = ImageCaptureModel(context, captureInfo)

    private val hiddenCam = HiddenCam(
        context, Utils.createBaseFolder(context), imageCapture,
        CaptureTimeFrequency.Recurring(10 * 2000L),
        targetResolution = Size(1080, 1920),
//            targetRotation = Surface.ROTATION_180
    )




    var playing by mutableStateOf(false)
        private set







    override fun onCleared() {
        exoPlayer.value.playWhenReady = false
        context.foregroundStartService("Exit")
    }

    fun startCapturing() {
        hiddenCam.start()
    }

    fun stopCapturing() {
        hiddenCam.stop()
    }


}
