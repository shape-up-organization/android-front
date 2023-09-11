package com.shapeup.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shapeup.ui.utils.helpers.TextHelper

@Composable
fun TextListComponent(texts: List<TextHelper>) {
    LazyColumn(
        verticalArrangement = Arrangement.Center
    ) {
        items(texts.size) {
            Text(
                text = texts[it].text,
                color = texts[it].color ?: MaterialTheme.colorScheme.onBackground,
                style = texts[it].style ?: MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    start = texts[it].paddingStart?.dp ?: 0.dp,
                    top = texts[it].paddingTop?.dp ?: 0.dp,
                    end = texts[it].paddingEnd?.dp ?: 0.dp
                )
            )
        }
    }
}
