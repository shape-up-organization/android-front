package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            )
        )
    }
}

class PasswordStepFormData(
    val password: MutableState<String>,
    val passwordConfirmation: MutableState<String>
)

@Composable
fun PasswordStep(data: PasswordStepFormData) {
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Password
        ),
        label = "Password",
        maxLines = 2,
        onValueChange = { data.password.value = it },
        supportingText = "",
        type = FormFieldType.PASSWORD,
        value = data.password.value
    )

    Spacer(modifier = Modifier.height(8.dp))

    FormField(
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        label = "Confirm your password",
        maxLines = 2,
        onValueChange = { data.passwordConfirmation.value = it },
        supportingText = "",
        type = FormFieldType.PASSWORD,
        value = data.passwordConfirmation.value
    )
}
