package com.smilias.learnit.log_in_screen

sealed class LogInScreenEvent{
    data class OnEmailEnter(val email: String): LogInScreenEvent()
    data class OnPasswordEnter(val password: String): LogInScreenEvent()
    object OnSignUp: LogInScreenEvent()
    object OnSignIn: LogInScreenEvent()
}
