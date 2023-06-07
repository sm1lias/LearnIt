package com.smilias.learnit.forget_password_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.smilias.learnit.R
import com.smilias.learnit.utils.UiEvent
import com.smilias.learnit.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {

    var state by mutableStateOf(ForgetPasswordState())
        private set

    fun onEmailEnter(text: String) {
        state = state.copy(email = text)
    }

    fun onResetClick() {
        auth.sendPasswordResetEmail(state.email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewModelScope.launch {
                    state.uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.check_your_email)))
                }
            } else {
                viewModelScope.launch {
                    state.uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.reset_password_unsuccessfull)))
                }
            }

        }
    }
}