package com.shapeup.ui.screens.auth

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.SignInFormData
import com.shapeup.ui.viewModels.auth.SignInFormHandlers
import com.shapeup.ui.viewModels.auth.signInFormDataMock
import com.shapeup.ui.viewModels.auth.signInFormHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@Preview
@Composable
fun SignInPreview() {
    ShapeUpTheme {
        SignInScreen(
            data = signInFormDataMock,
            handlers = signInFormHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun SignInScreen(
    data: SignInFormData,
    handlers: SignInFormHandlers,
    navigator: Navigator
) {
    var isLoading by remember { mutableStateOf(false) }
    var wasFormOnceSubmitted by remember { mutableStateOf(false) }
    var emailFormFieldError by remember { mutableStateOf<String?>(null) }
    var passwordFormFieldError by remember { mutableStateOf<String?>(null) }
    val isButtonEnabled by rememberUpdatedState(
        !wasFormOnceSubmitted ||
                (emailFormFieldError.isNullOrEmpty() &&
                        passwordFormFieldError.isNullOrEmpty() && !isLoading)
    )


    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()

    fun validateEmail(): String? {
        emailFormFieldError = when {
            wasFormOnceSubmitted && data.email.value.isEmpty() -> context.getString(R.string.txt_sign_in_required)
            wasFormOnceSubmitted && !data.email.value.contains("@") -> context.getString(R.string.txt_sign_in_invalid_format)

            else -> null
        }

        return emailFormFieldError
    }

    fun validatePassword(): String? {
        passwordFormFieldError = when {
            wasFormOnceSubmitted && data.password.value.isEmpty() -> context.getString(R.string.txt_sign_in_required)

            else -> null
        }

        return passwordFormFieldError
    }

    suspend fun signIn() {
        wasFormOnceSubmitted = true

        val emailError = validateEmail()
        val passwordError = validatePassword()

        if (!emailError.isNullOrEmpty() || !passwordError.isNullOrEmpty()) {
            return
        }

        isLoading = true
        val response = handlers.signIn()
        isLoading = false

        println(response)

        when (response.status) {
            HttpStatusCode.OK -> navigator.navigate(Screen.Feed)

            else -> {
                emailFormFieldError = context.getString(R.string.txt_sign_in_invalid)
                passwordFormFieldError = context.getString(R.string.txt_sign_in_invalid)
            }
        }
    }

    BackHandler {
        handlers.clearFormData()
        navigator.navigateBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(
            navigateTo = {
                handlers.clearFormData()
                navigator.navigateBack()
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineMedium,
                text = stringResource(R.string.txt_sign_in_screen_title)
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                FormField(
                    errorText = emailFormFieldError,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email
                    ),
                    label = stringResource(R.string.txt_sign_in_screen_email_input_label),
                    onValueChange = {
                        data.email.value = it
                        validateEmail()
                    },
                    supportingText = "",
                    value = data.email.value
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormField(
                    errorText = passwordFormFieldError,
                    focusManager = focusManager,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password
                    ),
                    label = stringResource(R.string.txt_sign_in_screen_password_input_label),
                    onValueChange = {
                        data.password.value = it
                        validatePassword()
                    },
                    supportingText = "",
                    type = FormFieldType.PASSWORD,
                    value = data.password.value
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (false) {
                    TextButton(onClick = { navigator.navigate(Screen.ForgotPassword) }) {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = stringResource(R.string.txt_sign_in_screen_forgot_password)
                        )
                    }
                }
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Button(
                enabled = isButtonEnabled,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutine.launch {
                        signIn()
                    }
                }
            ) {
                if (isLoading) {
                    Loading()
                } else {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 12.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        text = stringResource(R.string.txt_sign_in_screen_sign_in)
                    )
                }
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
                    text = stringResource(R.string.txt_sign_in_screen_sign_up)
                )
            }
        }
    }
}
