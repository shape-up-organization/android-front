package com.shapeup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.shapeup.R
import com.shapeup.ui.utils.constants.Icon

@Composable
fun Header(
    navigateTo: () -> Unit
) {
    Row(
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
    }
}
