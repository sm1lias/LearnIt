package com.smilias.learnit.video_screen.model

import androidx.compose.runtime.MutableState
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.auth.FirebaseUser

data class PhotoInfo(val user: FirebaseUser?, val exoPlayer: MutableState<SimpleExoPlayer>)
