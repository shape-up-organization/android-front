package com.shapeup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon

@Composable
fun Header(
    navigateTo: () -> Unit,
    text: String? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = navigateTo) {
            Icon(
                contentDescription = stringResource(R.string.icon_arrow_back),
                painter = painterResource(Icon.ArrowBack.value),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
            text = text ?: ""
        )
    }
}
