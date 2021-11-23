package com.smilias.learnit.menu_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.smilias.learnit.Screen

@Composable
fun MenuScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(onClick={
            navController.navigate(Screen.VideoScreen.route)
        }){
            Text(text = "Video Watch")
        }
    }

}