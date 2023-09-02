package com.shapeup.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    label: String = "",
    onDateSelected: (String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val datePickerState = rememberDatePickerState()

    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }

    fun closeDialog() {
        showDatePickerDialog = false
    }

    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { closeDialog() },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState
                            .selectedDateMillis?.let { millis ->
                                selectedDate = millis.toBrazilianDateFormat()
                            }

                        onDateSelected(selectedDate)

                        closeDialog()
                    }
                ) {
                    Text(text = "Pick data")
                }
            }
        ) {
            DatePicker(
                showModeToggle = false,
                state = datePickerState
            )
        }
    }

    FormField(
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
        ),
        label = label,
        modifier = Modifier
            .onFocusEvent {
                if (it.isFocused) {
                    showDatePickerDialog = true
                    focusManager.clearFocus(force = true)
                }
            },
        readOnly = true,
        type = FormFieldType.DATE,
        value = selectedDate
    )
}

fun Long.toBrazilianDateFormat(
    locale: String = "pt-br",
    pattern: String = "dd/MM/yyyy"
): String {
    val formatter = SimpleDateFormat(pattern, Locale(locale)).apply {
        timeZone = TimeZone.getTimeZone("GMT")
    }

    return formatter.format(Date(this))
}
