package com.smilias.learnit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LogInScreen(navController: NavController) {
        Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 70.dp)

            ) {
                var usernameText by remember {
                    mutableStateOf("")
                }
                var passwordText by remember {
                    mutableStateOf("")
                }
                var passwordVisibility by remember {
                    mutableStateOf(false)
                }
                OutlinedTextField(value = usernameText,
                    onValueChange = { usernameText = it },
                    label = { Text(stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
                HeightSpacer(10)
                OutlinedTextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
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
                    Button(onClick = {}) {
                        Text(text = stringResource(R.string.sign_in))
                    }
                    Button(onClick = {}) {
                        Text(text = stringResource(R.string.sign_up))
                    }
                }
                HeightSpacer(30)
                Button(onClick = {}){
                    Text(stringResource(R.string.forgot_password))
                }

            }
        }


}

