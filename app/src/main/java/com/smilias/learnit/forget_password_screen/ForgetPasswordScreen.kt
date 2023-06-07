package com.smilias.learnit.forget_password_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.smilias.learnit.R
import com.smilias.learnit.utils.UiEvent
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun ForgetPasswordScreen(
    scaffoldState: ScaffoldState,
    state: ForgetPasswordState,
    onEmailEnter: (String) -> Unit,
    onResetClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        state.uiEvent.receiveAsFlow().collect() { event ->
            when (event) {
                is UiEvent.NavigateUp -> onResetClick()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }

                else -> Unit
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Top) {

        OutlinedTextField(
            value = state.email,
            onValueChange = { onEmailEnter(it) },
            label = { Text(stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Button(onClick = onResetClick ) {
            Text(text = "Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgetPasswordScreenPreview() {
    ForgetPasswordScreen(
        scaffoldState = rememberScaffoldState(),
        state = ForgetPasswordState(),
        onEmailEnter = {},
        onResetClick = {}
    )
}