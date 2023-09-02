package com.shapeup.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.PhoneNumberVisualTransformation

@Composable
fun FormField(
    focusManager: FocusManager? = null,
    iconDescription: String = "",
    isPasswordHidden: MutableState<Boolean> = remember { mutableStateOf(true) },
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = { focusManager?.clearFocus() },
        onSearch = { focusManager?.clearFocus() },
        onSend = { focusManager?.clearFocus() }
    ),
    keyboardOptions: KeyboardOptions? = null,
    label: String,
    maxLines: Int = 1,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    supportingText: String = "",
    trailingIcon: Icon? = null,
    type: FormFieldType = FormFieldType.DEFAULT,
    value: String = ""
) {
    val isDateType = type == FormFieldType.DATE
    val isPasswordType = type == FormFieldType.PASSWORD
    val isPhoneType = type == FormFieldType.PHONE

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        keyboardActions = keyboardActions,
        keyboardOptions = when {
            keyboardOptions != null -> keyboardOptions

            isPasswordType -> KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )

            isPhoneType -> KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            )

            else -> KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        },
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        onValueChange = onValueChange,
        readOnly = readOnly,
        shape = RoundedCornerShape(16.dp),
        supportingText = { Text(supportingText) },
        trailingIcon = {
            when {
                isDateType -> {
                    Icon(
                        contentDescription = "Calendar icon",
                        painter = painterResource(id = Icon.Calendar.value)
                    )
                }

                isPasswordType -> {
                    val icon = when {
                        (isPasswordHidden.value) -> Icon.EyeOpen
                        else -> Icon.EyeClosed
                    }

                    val contentDescription = buildString {
                        append("Password ")
                        append(
                            when {
                                (isPasswordHidden.value) -> "hidden"
                                else -> "visible"
                            }
                        )
                    }

                    IconButton(onClick = {
                        isPasswordHidden.value = !isPasswordHidden.value
                    }) {
                        Icon(
                            contentDescription = contentDescription,
                            painter = painterResource(id = icon.value)
                        )
                    }
                }

                isPhoneType -> {
                    Icon(
                        contentDescription = "Phone field",
                        painter = painterResource(id = Icon.Phone.value)
                    )
                }

                else -> {
                    trailingIcon?.let { icon ->
                        Icon(
                            painter = painterResource(id = icon.value),
                            contentDescription = iconDescription
                        )
                    }
                }
            }
        },
        value = value,
        maxLines = maxLines,
        visualTransformation = when {
            isPasswordType && isPasswordHidden.value -> {
                PasswordVisualTransformation()
            }

            isPhoneType -> {
                PhoneNumberVisualTransformation()
            }

            else -> VisualTransformation.None
        }
    )
}

enum class FormFieldType {
    DATE,
    DEFAULT,
    PASSWORD,
    PHONE
}
