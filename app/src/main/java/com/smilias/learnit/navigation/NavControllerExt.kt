package com.smilias.learnit.navigation

import androidx.navigation.NavController
import com.smilias.learnit.utils.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}