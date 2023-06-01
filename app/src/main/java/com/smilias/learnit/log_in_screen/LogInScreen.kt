
package com.smilias.learnit.log_in_screen

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.smilias.learnit.HeightSpacer
import com.smilias.learnit.R
import com.smilias.learnit.utils.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LogInScreen(
    scaffoldState: ScaffoldState,
    state: LogInState,
    onNavigate: (UiEvent.Navigate) -> Unit,
    onEvent: (LogInScreenEvent) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        state.uiEvent.receiveAsFlow().collect() { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }

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
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 70.dp)

    ) {
        var passwordVisibility by remember {
            mutableStateOf(false)
        }
        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(LogInScreenEvent.OnEmailEnter(it)) },
            label = { Text(stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        HeightSpacer(10)
        OutlinedTextField(
            value = state.password,
            onValueChange = { onEvent(LogInScreenEvent.OnPasswordEnter(it)) },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image =
                    if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, "")
                }
            }
        )
        Text(text = stringResource(R.string.forgot_password), modifier = Modifier.clickable {  })

        HeightSpacer(30)
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                if (permissionsState.allPermissionsGranted) {
                    onEvent(LogInScreenEvent.OnSignIn)
                } else {
                    Toast.makeText(
                        context,
                        "You can't proceed without required permissions",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {
                Text(text = stringResource(R.string.sign_in))
//                LaunchedEffect(key1 = viewModel.user) {
//                    viewModel.user?.let {
//                        navController.navigate(Screen.MenuScreen.route) {
//                            popUpTo(Screen.LogInScreen.route) {
//                                inclusive = true
//                            }
//
//                        }
//                    }
//                }
            }
            Button(
                onClick = { onEvent(LogInScreenEvent.OnSignUp) }
            ) {
                Text(text = stringResource(R.string.sign_up))
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




