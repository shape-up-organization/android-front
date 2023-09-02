package com.shapeup.ui.screens.auth.signUp.steps

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.DatePicker
import com.shapeup.ui.components.FormField
import com.shapeup.ui.theme.ShapeUpTheme

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PersonalDataStepPreview() {
    ShapeUpTheme {
        PersonalDataStep(
            data = PersonalDataStepFormData(
                birthday = mutableStateOf(""),
                firstName = mutableStateOf(""),
                lastName = mutableStateOf("")
            )
        )
    }
}

class PersonalDataStepFormData(
    val birthday: MutableState<String>,
    val firstName: MutableState<String>,
    val lastName: MutableState<String>
)

@Composable
fun PersonalDataStep(data: PersonalDataStepFormData) {
    val focusManager = LocalFocusManager.current

    FormField(
        focusManager = focusManager,
        label = "First name",
        onValueChange = { data.firstName.value = it },
        supportingText = "",
        value = data.firstName.value
    )

    Spacer(modifier = Modifier.height(8.dp))

    FormField(
        focusManager = focusManager,
        label = "Last name",
        maxLines = 2,
        onValueChange = { data.lastName.value = it },
        supportingText = "",
        value = data.lastName.value
    )

    Spacer(modifier = Modifier.height(8.dp))

    DatePicker(
        label = "Birthday",
        onDateSelected = { data.birthday.value = it }
    )
}
