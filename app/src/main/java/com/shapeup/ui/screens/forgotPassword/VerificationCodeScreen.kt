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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun VerificationCodePreview() {
    ShapeUpTheme {
        VerificationCodeScreen(
            navigateToForgotPassword = {},
            navigateToChangePassword = {}
        )
    }
}

@Composable
fun VerificationCodeScreen(
    navigateToForgotPassword: () -> Unit,
    navigateToChangePassword: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(navigateToForgotPassword)

        Content()

        Footer(navigateToChangePassword)
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
            text = "Verification Code"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            FormField(
                labelText = "Verification Code",
                value = "",
                supportingText = "",
                onValueChange = {}
            )

            TextButton(onClick = {}) {
                Text(
                    text = "Resend the code",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun Footer(
    navigateToChangePassword: () -> Unit
) {
    Button(
        onClick = navigateToChangePassword,
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Send",
            Modifier
                .padding(vertical = 12.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
