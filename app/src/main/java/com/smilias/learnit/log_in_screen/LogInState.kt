package com.smilias.learnit.log_in_screen

import com.google.firebase.auth.FirebaseUser
import com.smilias.learnit.utils.UiEvent
import kotlinx.coroutines.channels.Channel

data class LogInState(
    val user: FirebaseUser? = null,
    val email: String = "",
    val password: String = "",
    val uiEvent: Channel<UiEvent> = Channel<UiEvent>()
)
