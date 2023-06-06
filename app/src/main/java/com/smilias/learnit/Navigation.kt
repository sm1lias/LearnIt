package com.smilias.learnit

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.smilias.learnit.log_in_screen.LogInScreen
import com.smilias.learnit.log_in_screen.LogInScreenViewModel
import com.smilias.learnit.menu_screen.MenuScreen
import com.smilias.learnit.navigation.navigate
import com.smilias.learnit.video_screen.VideoScreen
import com.smilias.learnit.video_screen.VideoScreenState
import com.smilias.learnit.video_screen.VideoScreenViewModel

@OptIn(ExperimentalPermissionsApi::class)
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
                val permissionsState = rememberMultiplePermissionsState(
                    permissions = listOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    )
                )
                PermissionsRequest(permissionsState)
                LogInScreen(
                    scaffoldState = scaffoldState,
                    state = viewmodel.state,
                    permissionsState = permissionsState,
                    onNavigate = navController::navigate,
                    onEvent = viewmodel::onEvent
                )
            }
            composable(
                route = Screen.MenuScreen.route,
            ) {
                MenuScreen(onNavigate = navController::navigate)
            }
            composable(
                route = Screen.VideoScreen.route + "/{url}",
                arguments = listOf(navArgument("url") {
                    type = NavType.StringType
                })
            ) {
                val viewModel: VideoScreenViewModel = hiltViewModel()
                VideoScreen(state = viewModel.state, onEvent = viewModel::onEvent)
            }
        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionsRequest(multiplePermissionsState: MultiplePermissionsState) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
//        Text("Camera and Read storage permissions Granted! Thank you!")

    } else {
        LaunchedEffect(key1 = multiplePermissionsState.allPermissionsGranted) {
            multiplePermissionsState.launchMultiplePermissionRequest()
        }
    }
}