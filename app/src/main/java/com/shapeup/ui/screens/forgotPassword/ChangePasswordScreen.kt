package com.shapeup.ui.screens.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.components.FormFieldWithIcon
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun ChangePasswordPreview() {
    ShapeUpTheme {
        ChangePasswordScreen(
            navigateToVerificationCode = {}
        )
    }
}

@Composable
fun ChangePasswordScreen(
    navigateToVerificationCode: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(navigateToVerificationCode)

        Content()

        Footer()
    }
}

@Composable
private fun Content() {
    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            text = "Change Password"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            FormFieldWithIcon(
                labelText = "New Password",
                value = "",
                supportingText = "",
                onValueChange = {},
                trailingIcon = R.drawable.icon_open_eye,
                iconDescription = "Show password icon"
            )
            FormFieldWithIcon(
                labelText = "Confirm Password",
                value = "",
                supportingText = "",
                onValueChange = {},
                trailingIcon = R.drawable.icon_open_eye,
                iconDescription = "Show password icon"
            )
        }
    }
}

@Composable
private fun Footer() {
    Button(
        onClick = { /*TODO*/ },
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Finish",
            Modifier
                .padding(vertical = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
