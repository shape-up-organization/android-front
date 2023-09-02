package com.shapeup.ui.screens.auth.signUp.steps

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.theme.ShapeUpTheme

@Preview
@Composable
fun TermsAndPrivacyStepPreview() {
    ShapeUpTheme {
        TermsAndPrivacyStep()
    }
}

@Composable
fun TermsAndPrivacyStep() {
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(id = R.string.terms_title)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodySmall,
        text = stringResource(id = R.string.terms_body)
    )

    Spacer(modifier = Modifier.height(24.dp))

    Text(
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodyLarge,
        text = stringResource(id = R.string.privacy_title)
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.bodySmall,
        text = stringResource(id = R.string.privacy_body)
    )
}
