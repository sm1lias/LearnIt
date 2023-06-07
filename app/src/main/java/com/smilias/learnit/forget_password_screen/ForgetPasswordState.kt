package com.smilias.learnit.forget_password_screen

import com.google.firebase.auth.FirebaseUser
import com.smilias.learnit.utils.UiEvent
import kotlinx.coroutines.channels.Channel

data class ForgetPasswordState(
    val email: String = "",
    val uiEvent: Channel<UiEvent> = Channel<UiEvent>()
)
