package com.shapeup.ui.screens.auth.signUp

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.components.Header
import com.shapeup.ui.components.Loading
import com.shapeup.ui.components.SnackbarHelper
import com.shapeup.ui.components.SnackbarType
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator
import com.shapeup.ui.viewModels.auth.AuthHandlers
import com.shapeup.ui.viewModels.auth.authHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun AccountVerificationScreenPreview() {
    ShapeUpTheme {
        AccountVerificationScreen(
            code = mutableStateOf(""),
            email = mutableStateOf(""),
            handlers = authHandlersMock,
            navigator = Navigator()
        )
    }
}

@Composable
fun AccountVerificationScreen(
    code: MutableState<String>,
    email: MutableState<String>,
    handlers: AuthHandlers,
    navigator: Navigator
) {
    val CODE_MAX_CHARS = 4

    var isLoading by remember { mutableStateOf(false) }
    var codeError by remember { mutableStateOf("") }
    var openSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    fun confirmEmail() {
        coroutine.launch {
            isLoading = true
            val response = handlers.confirmEmail()
            isLoading = false

            println(response)

            when (response.status) {
                HttpStatusCode.OK -> {
                    handlers.clearFormData()
                    navigator.navigateClean(Screen.SignIn)
                }

                else -> {
                    openSnackbar = true
                    snackbarMessage = context.getString(R.string.txt_errors_generic)
                    codeError = context.getString(R.string.txt_errors_invalid)
                }
            }
        }
    }

    fun sendEmailCode() {
        coroutine.launch {
            isLoading = true
            val response = handlers.sendEmailCode()
            isLoading = false

            println(response)

            codeError = when (response.status) {
                HttpStatusCode.OK -> {
                    code.value = ""
                    ""
                }

                else -> context.getString(R.string.txt_errors_send_code)
            }
        }
    }

    fun validateCode(): String {
        codeError = when {
            code.value.isBlank() ->
                context.getString(R.string.txt_errors_required)

            code.value.length != CODE_MAX_CHARS ->
                context.getString(R.string.txt_errors_invalid_format)

            else -> ""
        }
        return codeError
    }

    BackHandler {
        handlers.clearFormData()
        navigator.navigateClean(Screen.SignUp)
    }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(
            navigateTo = {
                handlers.clearFormData()
                navigator.navigateClean(Screen.SignUp)
            },
            text = stringResource(R.string.txt_sign_up_screen_account_verification)
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clip(shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .padding(all = 24.dp)
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
                        text = buildAnnotatedString {
                            append(stringResource(R.string.txt_sign_up_screen_send_code_warning))
                            withStyle(
                                style = SpanStyle(
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    fontWeight = MaterialTheme.typography.labelLarge.fontWeight
                                )
                            ) {
                                append(email.value)
                            }
                            append(".")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    enabled = !isLoading,
                    errorText = codeError,
                    focusManager = focusManager,
                    label = stringResource(R.string.txt_sign_up_screen_verification_code_label),
                    onValueChange = {
                        if (it.length > CODE_MAX_CHARS) return@FormField
                        code.value = it
                        validateCode()
                    },
                    supportingText = "",
                    type = FormFieldType.NUMBER,
                    value = code.value
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    enabled = !isLoading,
                    onClick = { sendEmailCode() }) {
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = stringResource(R.string.txt_sign_in_verification_code_screen_resend_code)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            enabled = codeError.isBlank() && !isLoading,
            modifier = Modifier.fillMaxWidth(),
            onClick = { confirmEmail() }
        ) {
            if (isLoading) {
                Loading()
            } else {
                Text(
                    modifier = Modifier
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    text = stringResource(R.string.txt_sign_up_screen_activate_account)
                )
            }
        }
    }

    SnackbarHelper(
        message = snackbarMessage,
        open = openSnackbar,
        openSnackbar = { openSnackbar = it },
        type = SnackbarType.ERROR
    )
}