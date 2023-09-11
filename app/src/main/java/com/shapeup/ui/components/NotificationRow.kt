package com.shapeup.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shapeup.R
import com.shapeup.ui.theme.GradientBrush
import com.shapeup.ui.utils.helpers.TextHelper

@Composable
fun NotificationRow(
    userFullName: String,
    action: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.height(88.dp)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.person_picture),
            contentDescription = "Woman photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(45.dp)
                .background(
                    brush = GradientBrush,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(1.6.dp)
        )

        val notificationUserFullName = TextHelper(
            text = userFullName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            paddingStart = 10,
            paddingTop = 2
        )

        val notificationUserAction = TextHelper(
            text = stringResource(action),
            paddingStart = 10,
            paddingTop = 2
        )

        val notificationTextInfos = listOf(
            notificationUserFullName,
            notificationUserAction
        )

        TextListComponent(texts = notificationTextInfos)

        Spacer(modifier = Modifier.height(24.dp))
    }
}
