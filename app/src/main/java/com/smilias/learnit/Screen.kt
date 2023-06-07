package com.smilias.learnit

sealed class Screen(val route:String){
    object LogInScreen: Screen("logInScreen")
    object ForgetPasswordScreen: Screen("forgetPasswordScreen")
    object MenuScreen: Screen("menuScreen")
    object VideoScreen: Screen("videoScreen")

}
