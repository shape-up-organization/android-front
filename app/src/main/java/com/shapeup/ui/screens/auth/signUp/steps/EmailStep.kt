package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.R
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun EmailStepPreview() {
    ShapeUpTheme {
        EmailStep(
            data = EmailStepFormData(
                email = mutableStateOf("")
            ),
            hasError = mutableStateOf(false)
        )
    }
}

class EmailStepFormData(
    val email: MutableState<String>
)

@Composable
fun EmailStep(
    data: EmailStepFormData,
    hasError: MutableState<Boolean>
) {
    var emailError by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val areAllFieldsFilled = data.email.value.isNotBlank()

        hasError.value = !areAllFieldsFilled
    }

    fun validateAll() {
        val hasAnyError = emailError.isNotBlank()

        hasError.value = hasAnyError
    }

    fun validateEmail(): String {
        emailError = when {
            data.email.value.isBlank() -> context.getString(R.string.txt_errors_required)
            !data.email.value.contains("@") -> context.getString(R.string.txt_errors_invalid_format)

            else -> ""
        }
        validateAll()
        return emailError
    }

    FormField(
        errorText = emailError,
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        label = stringResource(R.string.txt_sign_up_screen_email_label),
        onValueChange = {
            data.email.value = it
            validateEmail()
        },
        supportingText = "",
        value = data.email.value
    )
}
