package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import com.shapeup.R
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
                cellPhone = mutableStateOf("")
            ),
            hasError = mutableStateOf(false)
        )
    }
}

class PhoneStepFormData(

    val cellPhone: MutableState<String>
)

@Composable
fun PhoneStep(
    data: PhoneStepFormData,
    hasError: MutableState<Boolean>
) {
    var phoneError by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val areAllFieldsFilled = data.cellPhone.value.isNotBlank()

        hasError.value = !areAllFieldsFilled
    }

    fun validateAll() {
        val hasAnyError = phoneError.isNotBlank()

        hasError.value = hasAnyError
    }

    fun validatePhone(): String {
        phoneError = when {
            data.cellPhone.value.isBlank() -> context.getString(R.string.txt_errors_required)
            data.cellPhone.value.length < 3 -> context.getString(R.string.txt_errors_invalid)

            else -> ""
        }
        validateAll()
        return phoneError
    }

    FormField(
        errorText = phoneError,
        focusManager = focusManager,
        label = stringResource(R.string.txt_sign_up_screen_phone_number_label),
        onValueChange = {
            data.cellPhone.value = it
            validatePhone()
        },
        supportingText = "",
        type = FormFieldType.PHONE,
        value = data.cellPhone.value
    )
}
