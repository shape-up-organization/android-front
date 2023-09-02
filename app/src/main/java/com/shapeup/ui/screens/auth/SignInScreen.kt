package com.shapeup.ui.screens.auth

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.SignInFormData
import com.shapeup.ui.viewModels.auth.SignInFormHandlers

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun SignInPreview() {
    ShapeUpTheme {
        SignInScreen(
            navigator = Navigator(),
            data = SignInFormData(
                email = mutableStateOf(""),
                password = mutableStateOf("")
            ),
            handlers = SignInFormHandlers(
                signIn = {}
            )
        )
    }
}

@Composable
fun SignInScreen(
    data: SignInFormData,
    handlers: SignInFormHandlers,
    navigator: Navigator
) {
    val focusManager = LocalFocusManager.current

    BackHandler {
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header {
            navigator.navigateClean(Screen.Welcome)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                text = "Welcome Back"
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    label = "E-mail",
                    onValueChange = { data.email.value = it },
                    supportingText = "",
                    value = data.email.value
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormField(
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    label = "Password",
                    onValueChange = { data.password.value = it },
                    supportingText = "",
                    type = FormFieldType.PASSWORD,
                    value = data.password.value
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { navigator.navigate(Screen.ForgotPassword) }) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "Forgot password?"
                    )
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = handlers.signIn
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    text = "Sign in"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedButton(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { navigator.navigate(Screen.SignUp) }
            ) {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    text = "Sign up"
                )
            }
        }
    }
}
