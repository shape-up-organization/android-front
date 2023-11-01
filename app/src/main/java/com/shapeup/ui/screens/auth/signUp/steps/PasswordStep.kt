package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PasswordStepPreview() {
    ShapeUpTheme {
        PasswordStep(
            data = PasswordStepFormData(
                password = mutableStateOf(""),
                passwordConfirmation = mutableStateOf("")
            ),
            hasError = mutableStateOf(false)
        )
    }
}

class PasswordStepFormData(
    val password: MutableState<String>,
    val passwordConfirmation: MutableState<String>
)

@Composable
fun PasswordStep(
    data: PasswordStepFormData,
    hasError: MutableState<Boolean>
) {
    var passwordError by remember { mutableStateOf("") }
    var passwordConfirmationError by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val areAllFieldsFilled = data.password.value.isNotBlank() &&
                data.passwordConfirmation.value.isNotBlank()

        hasError.value = !areAllFieldsFilled
    }

    fun validateAll() {
        val hasAnyError = passwordError.isNotBlank() ||
                passwordConfirmationError.isNotBlank()

        hasError.value = hasAnyError
    }

    fun validatePassword(): String {
        passwordError = when {
            data.password.value.isBlank() -> context.getString(R.string.txt_errors_required)
            data.password.value.length < 2 -> context.getString(R.string.txt_errors_invalid)
//            !data.password.value.matches(
//                Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).{8,}\$")
//            ) -> context.getString(R.string.txt_errors_invalid_format)

            else -> ""
        }
        validateAll()
        return passwordError
    }

    fun validatePasswordConfirmation(): String {
        passwordConfirmationError = when {
            data.passwordConfirmation.value.isBlank() ->
                context.getString(R.string.txt_errors_required)
            data.password.value != data.passwordConfirmation.value ->
                context.getString(R.string.txt_errors_invalid)


            else -> ""
        }
        validateAll()
        return passwordConfirmationError
    }

    FormField(
        errorText = passwordError,
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        label = stringResource(R.string.txt_sign_up_screen_password_label),
        maxLines = 1,
        onValueChange = {
            data.password.value = it
            validatePassword()
        },
        supportingText = "",
        type = FormFieldType.PASSWORD,
        value = data.password.value
    )

    Spacer(modifier = Modifier.height(8.dp))

    FormField(
        errorText = passwordConfirmationError,
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        label = stringResource(R.string.txt_sign_up_screen_password_confirmation_label),
        maxLines = 1,
        onValueChange = {
            data.passwordConfirmation.value = it
            validatePasswordConfirmation()
        },
        supportingText = "",
        type = FormFieldType.PASSWORD,
        value = data.passwordConfirmation.value
    )
}
