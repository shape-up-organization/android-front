package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.FormFieldType
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PhoneStepPreview() {
    ShapeUpTheme {
        PhoneStep(
            data = PhoneStepFormData(
                phone = mutableStateOf("")
            )
        )
    }
}

class PhoneStepFormData(
    val phone: MutableState<String>
)

@Composable
fun PhoneStep(data: PhoneStepFormData) {
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        label = "Phone number",
        onValueChange = { data.phone.value = it },
        supportingText = "",
        type = FormFieldType.PHONE,
        value = data.phone.value
    )
}
