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
fun UsernameStepPreview() {
    ShapeUpTheme {
        UsernameStep(
            data = UsernameStepFormData(
                username = mutableStateOf("")
            ),
            hasError = mutableStateOf(false)
        )
    }
}

class UsernameStepFormData(
    val username: MutableState<String>
)

@Composable
fun UsernameStep(
    data: UsernameStepFormData,
    hasError: MutableState<Boolean>
) {
    var usernameError by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val areAllFieldsFilled = data.username.value.isNotBlank()

        hasError.value = !areAllFieldsFilled
    }

    fun validateAll() {
        val hasAnyError = usernameError.isNotBlank()

        hasError.value = hasAnyError
    }

    fun validateUsername(): String {
        usernameError = when {
            data.username.value.isBlank() -> context.getString(R.string.txt_errors_required)
            data.username.value.length < 2 -> context.getString(R.string.txt_errors_invalid)

            else -> ""
        }
        validateAll()
        return usernameError
    }

    FormField(
        errorText = usernameError,
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        label = stringResource(R.string.txt_sign_up_screen_username_label),
        onValueChange = {
            data.username.value = it
            validateUsername()
        },
        supportingText = "",
        value = data.username.value
    )
}
