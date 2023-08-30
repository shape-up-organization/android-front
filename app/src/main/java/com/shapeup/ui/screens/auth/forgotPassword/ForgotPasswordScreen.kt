package com.shapeup.ui.screens.auth.forgotPassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.ui.components.FormField
import com.shapeup.ui.components.Header
import com.shapeup.ui.theme.ShapeUpTheme
import com.shapeup.ui.utils.constants.Screen
import com.shapeup.ui.utils.helpers.Navigator

@Preview
@Composable
fun ForgotPasswordPreview() {
    ShapeUpTheme {
        ForgotPasswordScreen(
            navigator = Navigator()
        )
    }
}

@Composable
fun ForgotPasswordScreen(
    navigator: Navigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Header { navigator.navigate(Screen.SignIn) }

        Content()

        Footer { navigator.navigate(Screen.VerificationCode) }
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
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "we will send you a code in your e-mail address",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        FormField(
            label = "E-mail",
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
