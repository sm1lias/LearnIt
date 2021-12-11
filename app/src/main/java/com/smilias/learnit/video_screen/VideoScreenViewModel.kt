package com.smilias.learnit.video_screen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.smilias.learnit.utils.foregroundStartService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VideoScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

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
}
