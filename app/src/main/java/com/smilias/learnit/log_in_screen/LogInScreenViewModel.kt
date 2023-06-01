package com.smilias.learnit.log_in_screen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.smilias.learnit.R
import com.smilias.learnit.Screen
import com.smilias.learnit.domain.use_case.ValidateEmailPasswordUseCase
import com.smilias.learnit.utils.UiEvent
import com.smilias.learnit.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val validateEmailPasswordUseCase: ValidateEmailPasswordUseCase
) : ViewModel() {
    var state by mutableStateOf(LogInState())
        private set

    fun onEvent(event: LogInScreenEvent) {
        when (event) {
            is LogInScreenEvent.OnEmailEnter -> state = state.copy(email = event.email)
            is LogInScreenEvent.OnSignIn -> {
                signIn()
            }

            is LogInScreenEvent.OnPasswordEnter -> state = state.copy(password = event.password)
            is LogInScreenEvent.OnSignUp -> signUp()
        }
    }

    private fun signUp() {
        val result = validateEmailPasswordUseCase(
            email = state.email,
            password = state.password
        )
        when (result) {
            is ValidateEmailPasswordUseCase.Result.Error -> {
                viewModelScope.launch {
                    state.uiEvent.send(UiEvent.ShowSnackbar(result.message))
                }
            }

            is ValidateEmailPasswordUseCase.Result.Success -> {
                auth.createUserWithEmailAndPassword(state.email, state.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            viewModelScope.launch {
                                state.uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.sign_up_success)))
                            }
                        } else {
                            viewModelScope.launch {
                                state.uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.auth_failed)))
                            }
                        }
                    }
                viewModelScope.launch {
                    state.uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.auth_success)))
                }
            }
        }
    }

    private fun signIn() {
        val result = validateEmailPasswordUseCase(
            email = state.email,
            password = state.password
        )
        when (result) {
            is ValidateEmailPasswordUseCase.Result.Error -> {
                viewModelScope.launch {
                    state.uiEvent.send(UiEvent.ShowSnackbar(result.message))
                }
            }

            is ValidateEmailPasswordUseCase.Result.Success -> {
                auth.signInWithEmailAndPassword(state.email, state.password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            viewModelScope.launch {
                                state.uiEvent.send(UiEvent.Navigate(Screen.MenuScreen.route))
                            }
                        } else {
                            viewModelScope.launch {
                                state.uiEvent.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.auth_failed)))
                            }
                        }
                    }
            }
        }
    }

}