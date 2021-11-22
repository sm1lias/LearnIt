package com.smilias.learnit

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smilias.learnit.log_in_screen.LogInScreen
import com.smilias.learnit.menu_screen.MenuScreen

@Composable
fun Navigation() {
        val navController= rememberNavController()
        NavHost(navController = navController, startDestination = Screen.LogInScreen.route){
                composable(route=Screen.LogInScreen.route){
                        LogInScreen(navController = navController)
                }
                composable(route=Screen.MenuScreen.route){
                        MenuScreen(navController = navController)
                }
        }
}