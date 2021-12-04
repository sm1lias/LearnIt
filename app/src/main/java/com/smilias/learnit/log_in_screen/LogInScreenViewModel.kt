package com.smilias.learnit.log_in_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val auth = Firebase.auth
    var user by mutableStateOf<FirebaseUser?>(null)
    private set
    var email by mutableStateOf("")
    private set
    var password by mutableStateOf("")
    private set



    fun signIn() {
        if (email.isBlank() || password.isBlank()){
            Toast.makeText(context, "Cant be null", Toast.LENGTH_LONG).show()
            return
        }
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user = auth.currentUser
                    } else {
                        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG).show()
                    }
                }
        }


    }

    fun signUp() {
        if (email.isBlank() || password.isBlank()){
            Toast.makeText(context, "Cant be null", Toast.LENGTH_LONG).show()
            return
        }
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Signed up successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    fun onEmailChange(value: String){
        email=value
    }

    fun onPasswordChange(value:String){
        password=value
    }

}