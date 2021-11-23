package com.smilias.learnit.log_in_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class LogInScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val auth = Firebase.auth
    var user = mutableStateOf<FirebaseUser?>(null)

    fun signIn(email: String, password: String) {
        if (checkCredentials(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.value = auth.currentUser
                    } else {
                        Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(context, "Email and password can't be empty", Toast.LENGTH_LONG).show()
        }

    }

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    user.value = auth.currentUser
                } else {
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun checkCredentials(email: String, password: String): Boolean =
        !(email.trim().isEmpty() || password.trim().isEmpty())
}