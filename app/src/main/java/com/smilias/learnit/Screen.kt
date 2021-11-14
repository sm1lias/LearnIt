package com.smilias.learnit

sealed class Screen(val route:String){
    object LogInScreen: Screen("logInScreen")

}
