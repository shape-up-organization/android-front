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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon
import com.shapeup.ui.utils.helpers.PhoneNumberVisualTransformation

@Composable
fun FormField(
    enabled: Boolean = true,
    errorText: String? = null,
    focusManager: FocusManager? = null,
    isError: Boolean = false,
    isPasswordHidden: MutableState<Boolean> = remember { mutableStateOf(true) },
    keyboardActions: KeyboardActions = KeyboardActions(
        onDone = { focusManager?.clearFocus() },
        onGo = { focusManager?.clearFocus() },
        onSearch = { focusManager?.clearFocus() },
        onSend = { focusManager?.clearFocus() }
    ),
    keyboardOptions: KeyboardOptions? = null,
    label: String,
    leadingIcon: Icon? = null,
    maxLines: Int = 1,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
    onValueChange: (value: String) -> Unit = {},
    readOnly: Boolean = false,
    supportingText: String? = null,
    trailingIcon: Icon? = null,
    type: FormFieldType = FormFieldType.DEFAULT,
    value: String = ""
) {
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        enabled = enabled,
        isError = isError || !errorText.isNullOrEmpty(),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions ?: when(type) {
            FormFieldType.NUMBER -> KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            )

            FormFieldType.PASSWORD -> KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            )

            FormFieldType.PHONE -> KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            )

            FormFieldType.SEARCH -> KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            )

            else -> KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            )
        },
        label = {
            Text(
                color = MaterialTheme.colorScheme.tertiary,
                text = label
            )
        },
        leadingIcon = when {
            leadingIcon != null -> {
                {
                    Icon(
                        contentDescription = stringResource(leadingIcon.description),
                        painter = painterResource(leadingIcon.value),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            type == FormFieldType.SEARCH -> {
                {
                    Icon(
                        contentDescription = stringResource(Icon.Search.description),
                        painter = painterResource(Icon.Search.value),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            else -> null
        },
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        onValueChange = onValueChange,
        readOnly = readOnly,
        shape = RoundedCornerShape(16.dp),
        supportingText = errorText?.let { { Text(it) } } ?: supportingText?.let { { Text(it) } },
        trailingIcon = {
            when {
                trailingIcon != null -> {
                    Icon(
                        contentDescription = stringResource(trailingIcon.description),
                        painter = painterResource(trailingIcon.value)
                    )
                }

                type == FormFieldType.DATE -> {
                    Icon(
                        contentDescription = stringResource(Icon.Calendar.description),
                        painter = painterResource(Icon.Calendar.value)
                    )
                }

                type == FormFieldType.PASSWORD -> {
                    val icon = when {
                        (isPasswordHidden.value) -> Icon.EyeOpen
                        else -> Icon.EyeClosed
                    }

                    val contentDescription = buildString {
                        append(R.string.txt_form_field_password_icon)
                        append(
                            when {
                                (isPasswordHidden.value) -> stringResource(
                                    Icon.EyeClosed.description
                                )

                                else -> stringResource(Icon.EyeOpen.description)
                            }
                        )
                    }

                    IconButton(onClick = {
                        isPasswordHidden.value = !isPasswordHidden.value
                    }) {
                        Icon(
                            contentDescription = contentDescription,
                            painter = painterResource(icon.value)
                        )
                    }
                }

                type == FormFieldType.PHONE -> {
                    Icon(
                        contentDescription = stringResource(Icon.Phone.description),
                        painter = painterResource(Icon.Phone.value)
                    )
                }
            }
        },
        value = value,
        maxLines = maxLines,
        visualTransformation = when {
            type == FormFieldType.PASSWORD && isPasswordHidden.value -> {
                PasswordVisualTransformation()
            }

            type == FormFieldType.PHONE -> {
                PhoneNumberVisualTransformation()
            }

            else -> VisualTransformation.None
        }
    )
}

enum class FormFieldType {
    DATE,
    DEFAULT,
    NUMBER,
    PASSWORD,
    PHONE,
    SEARCH
}
