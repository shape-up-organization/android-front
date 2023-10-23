package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.DatePicker
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PersonalDataStepPreview() {
    ShapeUpTheme {
        Column {
            PersonalDataStep(
                data = PersonalDataStepFormData(
                    birth = mutableStateOf(""),
                    name = mutableStateOf(""),
                    lastName = mutableStateOf("")
                ),
                hasError = mutableStateOf(false),
            )
        }
    }
}

class PersonalDataStepFormData(
    val birth: MutableState<String>,
    val name: MutableState<String>,
    val lastName: MutableState<String>
)

@Composable
fun PersonalDataStep(
    data: PersonalDataStepFormData,
    hasError: MutableState<Boolean>
) {
    var firstNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var birthdayError by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val areAllFieldsFilled = data.name.value.isNotBlank() &&
                data.lastName.value.isNotBlank() &&
                data.birth.value.isNotBlank()

        hasError.value = !areAllFieldsFilled
    }

    fun validateAll() {
        val areAllFieldsFilled = data.name.value.isNotBlank() &&
                data.lastName.value.isNotBlank() &&
                data.birth.value.isNotBlank()

        val hasAnyError = (firstNameError.isNotBlank() ||
                lastNameError.isNotBlank() ||
                birthdayError.isNotBlank()) || !areAllFieldsFilled

        hasError.value = hasAnyError
    }

    fun validateFirstName(): String {
        firstNameError = when {
            data.name.value.isBlank() -> context.getString(R.string.txt_errors_required)
            data.name.value.length < 2 -> context.getString(R.string.txt_errors_invalid)

            else -> ""
        }
        validateAll()
        return firstNameError
    }

    fun validateLastName(): String {
        lastNameError = when {
            data.lastName.value.isBlank() -> context.getString(R.string.txt_errors_required)
            data.lastName.value.length < 2 -> context.getString(R.string.txt_errors_invalid)

            else -> ""
        }
        validateAll()
        return lastNameError
    }

    fun validateBirthday(): String {
        birthdayError = when {
            data.birth.value.isBlank() -> context.getString(R.string.txt_errors_required)

            else -> ""
        }
        validateAll()
        return birthdayError
    }

    FormField(
        errorText = firstNameError,
        focusManager = focusManager,
        label = stringResource(R.string.txt_sign_up_screen_first_name_label),
        onValueChange = {
            data.name.value = it
            validateFirstName()
        },
        supportingText = "",
        value = data.name.value
    )

    Spacer(modifier = Modifier.height(8.dp))

    FormField(
        errorText = lastNameError,
        focusManager = focusManager,
        label = stringResource(R.string.txt_sign_up_screen_last_name_label),
        maxLines = 2,
        onValueChange = {
            data.lastName.value = it
            validateLastName()
        },
        supportingText = "",
        value = data.lastName.value
    )

    Spacer(modifier = Modifier.height(8.dp))

    DatePicker(
        errorText = birthdayError,
        label = stringResource(R.string.txt_sign_up_screen_birthday_label),
        onDateSelected = {
            data.birth.value = it
            validateBirthday()
        },
        supportingText = "",
        value = data.birth.value
    )
}
