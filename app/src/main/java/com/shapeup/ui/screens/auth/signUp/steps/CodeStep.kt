package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.viewModels.auth.SignUpFormHandlers
import com.shapeup.ui.viewModels.auth.signUpFormHandlersMock
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun VerificationCodeStepPreview() {
    ShapeUpTheme {
        CodeStep(
            data = CodeStepFormData(
                email = mutableStateOf("example@gmail.com"),
                code = mutableStateOf(""),
            ),
            hasError = mutableStateOf(false),
            handlers = signUpFormHandlersMock
        )
    }
}

class CodeStepFormData(
    val email: MutableState<String>,
    val code: MutableState<String>
)

@Composable
fun CodeStep(
    data: CodeStepFormData,
    hasError: MutableState<Boolean>,
    handlers: SignUpFormHandlers
) {
    var isLoading by remember { mutableStateOf(true) }
    var codeError by remember { mutableStateOf("") }

    val coroutine = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    fun confirmEmail() {
        coroutine.launch {
            isLoading = true
            val response = handlers.confirmEmail()
            isLoading = false

            println(response)

            codeError = ""
//            codeError = when (response.status) {
//                HttpStatusCode.OK -> ""
//
//                else -> context.getString(R.string.txt_errors_invalid)
//            }
        }
    }

    fun sendEmailCode() {
        coroutine.launch {
            isLoading = true
            val response = handlers.sendEmailCode()
            isLoading = false

            println(response)

            codeError = when (response.status) {
                HttpStatusCode.OK -> ""

                else -> context.getString(R.string.txt_errors_send_code)
            }
        }
    }

    LaunchedEffect(Unit) {
        val areAllFieldsFilled = data.code.value.isNotBlank()

        hasError.value = !areAllFieldsFilled
        sendEmailCode()
    }

    fun validateAll() {
        val hasAnyError = codeError.isNotBlank()

        hasError.value = hasAnyError
    }

    fun validateCode(): String {
        codeError = when {
            data.code.value.isBlank() ->
                context.getString(R.string.txt_errors_required)

            data.code.value.length != 6 ->
                context.getString(R.string.txt_errors_invalid_format)

            else -> ""
        }
        validateAll()
        return codeError
    }

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
                        append(data.email.value)
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
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            label = stringResource(R.string.txt_sign_up_screen_verification_code_label),
            onValueChange = {
                if (it.length > 6) return@FormField
                data.code.value = it
                validateCode()

                if (it.length == 6) {
                    confirmEmail()
                }
            },
            supportingText = "",
            value = data.code.value
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
