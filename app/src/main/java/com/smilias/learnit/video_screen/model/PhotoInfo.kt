package com.smilias.learnit.video_screen.model

import androidx.compose.runtime.MutableState
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.auth.FirebaseUser
import com.smilias.learnit.menu_screen.LocationLiveData

data class PhotoInfo(
    val user: FirebaseUser?,
    val exoPlayer: Player,
    var title: String = "",
    var location: LocationLiveData? = null
)
