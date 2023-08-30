package com.shapeup.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.shapeup.ui.utils.constants.Icon

@Composable
fun FormField(
    iconDescription: String = "",
    isPasswordHidden: MutableState<Boolean> = remember { mutableStateOf(true) },
    label: String,
    onValueChange: (String) -> Unit = {},
    supportingText: String = "",
    trailingIcon: Icon? = null,
    type: FormFieldType = FormFieldType.DEFAULT,
    value: String = ""
) {
    val isPasswordType = type == FormFieldType.PASSWORD
    val visualTransformation = when {
        isPasswordType && isPasswordHidden.value -> {
            PasswordVisualTransformation()
        }

        else -> VisualTransformation.None
    }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.primaryContainer,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth(),
        onValueChange = onValueChange,
        shape = RoundedCornerShape(16.dp),
        supportingText = { Text(supportingText) },
        trailingIcon = {
            when {
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
        visualTransformation = visualTransformation
    )
}

enum class FormFieldType {
    DEFAULT,
    PASSWORD
}
