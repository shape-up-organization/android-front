package com.shapeup.ui.screens.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun ForgotPasswordPreview() {
    ShapeUpTheme {
        ForgotPasswordScreen(
            navigateToSignIn = {},
            navigateToVerificationCode = {}
        )
    }
}

@Composable
fun ForgotPasswordScreen(
    navigateToSignIn: () -> Unit,
    navigateToVerificationCode: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header(navigateToSignIn)

        Content()

        Footer(navigateToVerificationCode)
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
            text = "Forgot Password?"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "we will send you a code in your e-mail adress",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        FormField(
            labelText = "E-mail",
            value = "",
            supportingText = "",
            onValueChange = {}
        )
    }
}

@Composable
private fun Footer(
    navigateToVerificationCode: () -> Unit
) {
    Button(
        onClick = navigateToVerificationCode,
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
