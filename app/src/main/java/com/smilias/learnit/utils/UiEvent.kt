package com.smilias.learnit.utils

sealed class UiEvent{
    data class Navigate(val route: String): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message:UiText): UiEvent()
}
