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
import com.shapeup.R

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
                contentDescription = "Icon arrow back",
                painter = painterResource(id = R.drawable.icon_arrow_back),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
