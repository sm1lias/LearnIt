package com.smilias.learnit.video_screen

import com.google.android.exoplayer2.ExoPlayer

data class VideoScreenState(
    val videoUrl: String = "",
    val playing: Boolean = false
)
