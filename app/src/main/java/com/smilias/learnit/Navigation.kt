package com.smilias.learnit

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.smilias.learnit.log_in_screen.LogInScreen
import com.smilias.learnit.log_in_screen.LogInScreenViewModel
import com.smilias.learnit.menu_screen.MenuScreen
import com.smilias.learnit.navigation.navigate
import com.smilias.learnit.video_screen.VideoScreen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.LogInScreen.route
        ) {
            composable(route = Screen.LogInScreen.route) {
                val viewmodel: LogInScreenViewModel = hiltViewModel()
                LogInScreen(
                    scaffoldState = scaffoldState,
                    state = viewmodel.state,
                    onNavigate = navController::navigate,
                    onEvent = viewmodel::onEvent
                )
            }
            composable(
                route = Screen.MenuScreen.route,
            ) {
                MenuScreen(navController = navController)
            }
            composable(
                route = Screen.VideoScreen.route + "/{url}",
                arguments = listOf(navArgument("url") {
                    type = NavType.StringType
                })
            ) {
                val url = it.arguments?.getString("url")
                if (url != null) {
                    VideoScreen(sourceUrl = url.replace("\\", "/"))
                } else
                    VideoScreen()
            }
        }
    }

}