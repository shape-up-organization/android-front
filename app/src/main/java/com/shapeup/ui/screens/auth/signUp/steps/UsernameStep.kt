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
fun UsernameStepPreview() {
    ShapeUpTheme {
        UsernameStep(
            data = UsernameStepFormData(
                username = mutableStateOf("")
            )
        )
    }
}

class UsernameStepFormData(
    val username: MutableState<String>
)

@Composable
fun UsernameStep(data: UsernameStepFormData) {
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        label = stringResource(R.string.txt_sign_up_screen_username_label),
        onValueChange = { data.username.value = it },
        supportingText = "",
        value = data.username.value
    )
}
