package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
            )
        )
    }
}

class EmailStepFormData(
    val email: MutableState<String>
)

@Composable
fun EmailStep(data: EmailStepFormData) {
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Email
        ),
        label = stringResource(R.string.txt_sign_up_screen_email_label),
        onValueChange = { data.email.value = it },
        supportingText = "",
        value = data.email.value
    )
}
