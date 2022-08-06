@file:OptIn(ExperimentalPermissionsApi::class)

package com.smilias.learnit.log_in_screen

import android.Manifest
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.smilias.learnit.HeightSpacer
import com.smilias.learnit.Navigation
import com.smilias.learnit.R
import com.smilias.learnit.Screen
import kotlinx.coroutines.delay

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: LogInScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val permissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    )
    Sample(permissionsState)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)

        ) {
            var passwordVisibility by remember {
                mutableStateOf(false)
            }
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text(stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            HeightSpacer(10)
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = viewModel::onPasswordChange,
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
            HeightSpacer(30)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick =  {if (permissionsState.allPermissionsGranted) {
                    viewModel.signIn()
                } else {
                    Toast.makeText(
                        context,
                        "You can't proceed without required permissions",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }) {
                    Text(text = stringResource(R.string.sign_in))
                    LaunchedEffect(key1 = viewModel.user) {
                        viewModel.user?.let {
                            navController.navigate(Screen.MenuScreen.route) {
                                popUpTo(Screen.LogInScreen.route) {
                                    inclusive = true
                                }

                            }
                        }
                    }
                }
                Button(
                    onClick = viewModel::signUp
                ) {
                    Text(text = stringResource(R.string.sign_up))
                }
            }
            HeightSpacer(30)
            Button(onClick = {}) {
                Text(stringResource(R.string.forgot_password))
            }

        }

    }


}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun Sample(multiplePermissionsState: MultiplePermissionsState) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
//        Text("Camera and Read storage permissions Granted! Thank you!")

    } else {
        Column {
            Text(
                getTextToShowGivenPermissions(
                    multiplePermissionsState.revokedPermissions,
                    multiplePermissionsState.shouldShowRationale
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permissions")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant all of them for the app to function properly."
        } else {
            " denied. The app cannot function without them."
//                this.finish();
        }
    )
    return textToShow.toString()
}



